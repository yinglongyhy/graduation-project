<template>
  <div class="app-container">
    <el-card style="width: 100%; margin-bottom: 20px;">
      <el-form :inline="true" :model="params" style=" float: left;">
        <el-form-item label="地址">
          <el-select
            v-model="params.address"
            filterable
            remote
            reserve-keyword
            placeholder="请输入关键词"
            clearable="true"
            :remote-method="remoteMethod"
            :loading="loading"
            style="width: 100%"
          >
            <el-option
              v-for="item in addressOptions"
              :key="item.code"
              :label="item.fullname"
              :value="item.code"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="价格区间">
          <el-input v-model="params.rentMin" style="width: 100px;" />
          -
          <el-input v-model="params.rentMax" style="width: 100px;" />
        </el-form-item>
        <el-form-item label="租赁状态">
          <el-select v-model="params.rented" placeholder="全部" style="width: 100px;" clearable="true">
            <el-option label="已租" value="1" />
            <el-option label="未租" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID">
        <template slot-scope="scope">
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="房主">
        <template slot-scope="scope">
          {{ scope.row.owner }}
        </template>
      </el-table-column>
      <el-table-column label="区域地址" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.address.fullname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="详细地址" align="center">
        <template slot-scope="scope">
          {{ scope.row.detailedAddress }}
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center">
        <template slot-scope="scope">
          {{ scope.row.description }}
        </template>
      </el-table-column>
      <el-table-column label="面积(平米)" align="center">
        <template slot-scope="scope">
          {{ scope.row.area }}
        </template>
      </el-table-column>
      <el-table-column label="租金(元/月)" align="center">
        <template slot-scope="scope">
          {{ scope.row.rent }}
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="出租标记" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.rented | statusFilter">{{
            (scope.row.rented == 0 && "未出租") || "已出租"
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="创建时间">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="修改时间">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.modifiedTime }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Page ref="page" :url="url" :params="params" @refreshList="refreshList" />

    <el-dialog title="编辑" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="房主" :label-width="formLabelWidth">
          <el-input v-model="form.owner" autocomplete="off" />
        </el-form-item>
        <el-form-item label="区域地址" :label-width="formLabelWidth">
          <el-input v-model="form.address" autocomplete="off" />
        </el-form-item>
        <el-form-item label="详细地址" :label-width="formLabelWidth">
          <el-input v-model="form.detailedAddress" autocomplete="off" />
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="form.description" autocomplete="off" />
        </el-form-item>
        <el-form-item label="面积(平米)" :label-width="formLabelWidth">
          <el-input v-model="form.area" autocomplete="off" />
        </el-form-item>
        <el-form-item label="租金(元/月)" :label-width="formLabelWidth">
          <el-input v-model="form.rent" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="update()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import Page from '@/components/Page'
import { deleteById } from '@/utils/delete'
import { editById } from '@/utils/edit'
import axios from 'axios'

export default {
  components: { Page },
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: 'success',
        1: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      dialogTableVisible: false,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      params: {
        address: null,
        rentMin: null,
        rentMax: null
      },
      addressOptions: [],
      form: {
        id: null,
        owner: null,
        address: null,
        detailedAddress: null,
        description: null,
        area: null,
        rent: null
      },
      url: '/api/houseInfo/page',
      list: null,
      listLoading: true
    }
  },
  created() {
    // this.fetchData(this.currentPage, this.pageSize);
  },
  methods: {
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true
        setTimeout(() => {
          axios
            .get('/api/address/query?name=' + query, {
              headers: { token: this.$store.getters.token }
            })
            .then((res) => {
              console.log(res)
              this.loading = false
              this.addressOptions = res.data
            })
            .catch((error) => {
              console.log(error)
            })
        }, 200)
      } else {
        this.options = []
      }
    },
    onSubmit() {
      this.$refs.page.clear()
      this.$refs.page.refresh()
    },
    refreshList(list) {
      this.list = list
      this.listLoading = false
    },
    update() {
      var id = this.form.id
      var row = this.list.find(function(obj) {
        return obj.id === id
      })
      editById('/api/admin/houseInfo', this.form).then((response) => {
        row.id = this.form.id
        row.owner = this.form.owner
        row.address = this.form.owner
        row.detailedAddress = this.form.detailedAddress
        row.description = this.form.description
        row.area = this.form.area
        row.rent = this.form.rent
      })
      this.dialogFormVisible = true
      this.dialogFormVisible = false
    },
    handleEdit(index, row) {
      this.form.id = row.id
      this.form.owner = row.owner
      this.form.address = row.owner
      this.form.detailedAddress = row.detailedAddress
      this.form.description = row.description
      this.form.area = row.area
      this.form.rent = row.rent
      this.dialogFormVisible = true
    },
    handleDelete(index, row) {
      deleteById('/api/admin/houseInfo/' + row.id).then((response) => {
        this.$refs.page.refresh()
      })
    }
  }
}
</script>

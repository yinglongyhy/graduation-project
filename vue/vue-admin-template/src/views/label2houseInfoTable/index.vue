<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      @cell-click="cellClick"
    >
      <el-table-column align="center" label="ID">
        <template slot-scope="scope">
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="标签编号">
        <template slot-scope="scope">
          {{ scope.row.label }}
        </template>
      </el-table-column>
      <el-table-column label="房源编号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.houseInfo }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column class-name="status-col" label="删除标记" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.deleted | statusFilter">{{
            (scope.row.deleted == 0 && "未删除") || "已删除"
          }}</el-tag>
        </template>
      </el-table-column> -->
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
        <el-form-item label="标签编号" :label-width="formLabelWidth">
          <el-input v-model="form.label" autocomplete="off" />
        </el-form-item>
        <el-form-item label="房源编号" :label-width="formLabelWidth">
          <el-input v-model="form.houseInfo" autocomplete="off" />
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
      form: {
        id: null,
        label: null,
        houseInfo: null
      },
      params: {},
      url: '/api/admin/label2houseInfo/page',
      list: null,
      listLoading: true
    }
  },
  created() {
    // this.fetchData(this.currentPage, this.pageSize);
  },
  methods: {
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
      editById('/api/admin/label2houseInfo', this.form).then((response) => {
        row.id = this.form.id
        row.label = this.form.label
        row.houseInfo = this.form.houseInfo
      })
      this.dialogFormVisible = true
      this.dialogFormVisible = false
    },
    handleEdit(index, row) {
      this.form.id = row.id
      this.form.label = row.label
      this.form.houseInfo = row.houseInfo
      this.dialogFormVisible = true
    },
    handleDelete(index, row) {
      deleteById('/api/admin/label2houseInfo/' + row.id).then((response) => {
        this.$refs.page.refresh()
      })
    },
    // 单元格点击事件
    cellClick(row, column, cell, event) {
      console.log(row)
      console.log(column)
      console.log(cell)
      console.log(event)
    }
  }
}
</script>

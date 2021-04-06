<template>
  <div class="app-container">
    <el-card style="width: 100%; margin-bottom: 20px;">
      <el-button type="primary" @click="addUser">新增用户</el-button>
    </el-card>
    <el-card style="width: 100%; margin-bottom: 20px;">
      <el-form :inline="true" :model="params" style=" float: left;">
        <el-form-item label="姓名">
          <el-input v-model="params.name" style="width: 200px;" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="params.phoneNum" style="width: 200px;" />
        </el-form-item>
        <el-form-item label="角色">
          <el-input v-model="params.role" style="width: 300px;" />
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
      <el-table-column label="姓名">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.phoneNum }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色" align="center">
        <template slot-scope="scope">
          {{ scope.row.role }}
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
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="form.password" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="update()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="新增用户" :visible.sync="userDialogFormVisible">
      <el-form :model="addForm">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="addForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="addForm.phoneNum" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="addForm.password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="角色" :label-width="formLabelWidth">
          <el-select v-model="addForm.role" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add()">确 定</el-button>
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
      options: [{
        value: 'user',
        label: '用户'
      }, {
        value: 'admin',
        label: '管理员'
      }],
      dialogTableVisible: false,
      dialogFormVisible: false,
      userDialogFormVisible: false,
      formLabelWidth: '120px',
      url: '/api/user/page',
      params: {
        id: null,
        name: null,
        password: null
      },
      form: {
        name: null,
        phoneNum: null,
        password: null
      },
      addForm: {
        name: null,
        phoneNum: null,
        password: null,
        role: null
      },
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
      editById('/api/user/save', this.form).then((response) => {
        row.id = this.form.id
        row.name = this.form.name
        row.password = this.form.password
      })
      this.dialogFormVisible = true
      this.dialogFormVisible = false
    },
    handleEdit(index, row) {
      this.form.id = row.id
      this.form.name = row.name
      this.form.phoneNum = row.phoneNum
      this.form.password = null
      this.dialogFormVisible = true
    },
    handleDelete(index, row) {
      deleteById('/api/user/' + row.id).then((response) => {
        this.$refs.page.refresh()
      })
    },
    addUser() {
      this.addForm.name = null
      this.addForm.phoneNum = null
      this.addForm.password = null
      this.addForm.role = 'user'
      this.userDialogFormVisible = true
    },
    add() {
      axios
        .post('/api/user', this.addForm, {
          headers: { token: this.$store.getters.token }
        })
        .then((res) => {
          console.log(res)
        })
        .catch((error) => {
          console.log(error)
          alert('新增用户错误')
        })
      this.userDialogFormVisible = true
      this.userDialogFormVisible = false
    }
  }
}
</script>

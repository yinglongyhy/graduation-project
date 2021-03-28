<template>
  <div class="app-container">
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
      <el-table-column label="中国地区编码">
        <template slot-scope="scope">
          {{ scope.row.code }}
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="全名" align="center">
        <template slot-scope="scope">
          {{ scope.row.fullname }}
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

      <!-- <el-table-column label="操作" align="center">
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
      </el-table-column> -->
    </el-table>
    <Page :url="url" @refreshList="refreshList" />
  </div>
</template>

<script>
import Page from '@/components/Page'

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
      url: '/api/admin/address/page',
      list: null,
      listLoading: true
    }
  },
  created() {
    // this.fetchData(this.currentPage, this.pageSize);
  },
  methods: {
    refreshList(list) {
      this.list = list
      this.listLoading = false
    },
    handleEdit(index, row) {

    },
    handleDelete(index, row) {

    }
  }
}
</script>

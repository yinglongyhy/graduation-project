<template>
  <div class="page">
    <el-pagination
      :current-page="currentPage"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { getList } from '@/api/page'

export default {
  // eslint-disable-next-line vue/require-prop-types
  props: ['url', 'params'],
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  created() {
    console.log(this.url)
    this.fetchData(this.url, {
      pageNumber: this.currentPage,
      pageSize: this.pageSize
    })
  },
  methods: {
    clear() {
      this.currentPage = 1
      this.pageSize = 10
    },
    refresh() {
      this.params.pageNumber = this.currentPage
      this.params.pageSize = this.pageSize
      console.log(this.params)
      this.fetchData(this.url, this.params)
    },
    fetchData(url, params) {
      getList(url, params).then((response) => {
        console.log(response)
        this.total = response.total
        this.$emit('refreshList', response.records)
      })
    },
    handleSizeChange(val) {
      this.currentPage = 1
      this.pageSize = val
      this.refresh()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.refresh()
    }
  }
}
</script>

<style>
.page {
  text-align: center
}
</style>

<template>
  <div>
    <el-menu
      :default-active="this.$route.path"
      class="el-menu-demo"
      mode="horizontal"
      text-color="black"
      router="true"
    >
      <!-- <el-menu-item index="/" @click="exit">退出登录</el-menu-item>
      <el-menu-item index="/editUsers">编辑用户信息</el-menu-item>
      <el-menu-item index="/editAdmins">编辑管理员信息</el-menu-item>
      <el-menu-item index="/addBooks">新增书籍</el-menu-item>
      <el-menu-item index="/addCategory">新增书籍种类</el-menu-item> -->
      <el-menu-item index="/login" style="float: right">登陆</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Header',
  methods: {
    exit () {
    //   this.$store.commit('changeaLogin', null)
    //   this.$store.commit('changeAdmins', null)
    //   this.$store.commit('changeBooks', [])
    }
  }
}
</script>

<style lang="stylus" scoped>

</style>

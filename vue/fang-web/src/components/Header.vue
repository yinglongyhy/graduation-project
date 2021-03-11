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
      <el-menu-item index="/login" v-if="this.$store.state.token === ''" style="float: right">登陆</el-menu-item>
      <el-submenu index="2" v-if="this.$store.state.token !== ''" style="float: right">
        <template slot="title">杨某</template>
        <el-menu-item index="2-1">个人信息</el-menu-item>
        <el-menu-item index="2-2">住房管理</el-menu-item>
        <el-menu-item index="2-3">房源管理</el-menu-item>
        <el-menu-item :index="this.$route.path" @click="exit">登出</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
    };
  },
  methods: {
    exit() {
      this.$store.commit('clearToken')
      // todo 用户信息的注销
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style lang="stylus" scoped></style>

<template>
  <div>
    <el-menu
      :default-active="this.$route.path"
      class="el-menu-demo"
      mode="horizontal"
      text-color="black"
      router="true"
    >
      <el-menu-item index="/" style="float: left">首页</el-menu-item>
      <el-menu-item index="/login" v-if="token === null" style="float: right"
        >登陆</el-menu-item
      >
      <el-submenu index="2" v-if="token !== null" style="float: right">
        <template slot="title">{{ userInfo.name }}</template>
        <el-menu-item index="/userInfo">个人信息</el-menu-item>
        <el-menu-item index="/tenantManagerment">租房管理</el-menu-item>
        <el-menu-item index="/ownerManagerment">房源管理</el-menu-item>
        <el-menu-item :index="this.$route.path" @click="exit"
          >登出</el-menu-item
        >
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      token: window.localStorage.getItem("token"),
      userInfo: JSON.parse(window.localStorage.getItem("userInfo")),
    };
  },
  methods: {
    exit() {
      window.localStorage.removeItem("token");
      window.localStorage.removeItem("userInfo");
      // todo 用户信息的注销

      window.location.href = "/";
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style lang="stylus" scoped></style>

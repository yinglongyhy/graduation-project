<template>
  <div class="form">
    <header class="header">登录</header>
    <div class="contain">
      <el-form
        :label-position="labelPosition"
        label-width="60px"
        :model="formLabelAlign"
      >
        <el-form-item label="账号">
          <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="formLabelAlign.password" show-password></el-input>
        </el-form-item>
        <!-- <el-form-item>
          <el-radio v-model="radio" label="1">用户</el-radio>
          <el-radio v-model="radio" label="2" @click="handleClick">管理员</el-radio>
        </el-form-item> -->
        <el-form-item>
          <el-button-group>
            <el-button @click="onSubmit" type="primary">登录</el-button>
            <router-link to="/">
              <el-button>取消</el-button>
            </router-link>
          </el-button-group>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Login",
  data() {
    return {
      labelPosition: "left",
      userToken: null,
      userInfo: null,
      formLabelAlign: {
        id: null,
        name: null,
        phoneNum: null,
        password: null,
        role: null,
      },
    };
  },
  methods: {
    onSubmit() {
      console.log("submit");
      this.formLabelAlign.role = 'user'

      if (
        this.formLabelAlign.name === null ||
        this.formLabelAlign.password === null
      ) {
        alert("账号或密码不能为空");
      } else {
        console.log(this.formLabelAlign);
        // 用户登录
        axios
          .post("/api/user/login", this.formLabelAlign, {headers:{'token': window.localStorage.getItem('token')}})
          .then((res) => {
            console.log(res);
            this.userToken = res.headers.token;
            this.userInfo = res.data;
            console.log(this.userToken);
            console.log(this.userInfo);

            window.localStorage.setItem('token', this.userToken)
            window.localStorage.setItem('userInfo', JSON.stringify(this.userInfo))

            // 将用户token保存到vuex中, 并设置跳转  yinglongyhy
            // this.changeLogin({ Authorization: _this.userToken })
            // this.$router.push("/");
            window.location.href = "/";
          })
          .catch((error) => {
            alert("账号或密码错误");
            console.log(error);
          });
      }
    },
  },
};
</script>

<style lang="stylus" scoped>
.form {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  height: 500px;

  .contain {
    width: 300px;
    margin: 50px auto;
  }

  .header {
    text-align: center;
    width: 200px;
    margin: 100px auto 0;
    font-weight: bold;
    font-size: 25px;
  }
}
</style>

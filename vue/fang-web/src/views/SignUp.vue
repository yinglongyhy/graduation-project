<template>
  <div class="form">
    <header class="header">注册</header>
    <div class="contain">
      <el-form
        :label-position="labelPosition"
        label-width="60px"
        :model="formLabelAlign"
      >
        <el-form-item label="用户名">
          <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="formLabelAlign.phoneNum"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="formLabelAlign.password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button-group>
            <el-button @click="onSubmit" type="primary">注册</el-button>
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
  name: "SignUp",
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
        this.formLabelAlign.phoneNum === null ||
        this.formLabelAlign.password === null
      ) {
        alert("账号、手机号或密码不能为空");
      } else {
        console.log(this.formLabelAlign);
        // 用户登录
        axios
          .post("/api/user", this.formLabelAlign)
          .then((res) => {
            console.log(res);
            window.location.href = "/";
          })
          .catch((error) => {
            alert("用户名或手机号已存在，注册失败");
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

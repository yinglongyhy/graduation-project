<template>
  <div class="form">
    <header class="header">用户信息</header>
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
          <el-input v-model="formLabelAlign.phoneNum" disabled></el-input>
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
            <el-button @click="onSubmit" type="primary">确认</el-button>
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
  name: "UserInfo",
  data() {
    return {
      labelPosition: "left",
      formLabelAlign: {
        name: JSON.parse(window.localStorage.getItem('userInfo')).name,
        phoneNum: JSON.parse(window.localStorage.getItem('userInfo')).phoneNum,
        password: null,
      },
      userInfo: {
        id: null,
        name: null,
        phoneNum: null,
        role: 'user'
      }
    };
  },
  methods: {
    onSubmit() {
      console.log("submit");
      if (this.formLabelAlign.name === JSON.parse(window.localStorage.getItem('userInfo')).name && this.formLabelAlign.password === null) {
            this.$router.push("/");
      } else {
        console.log(this.formLabelAlign);
        // 用户修改
        axios
          .put("/api/user", this.formLabelAlign, {
            headers: { token: window.localStorage.getItem('token') },
          })
          .then((res) => {
            console.log(res)
            this.userInfo.id = JSON.parse(window.localStorage.getItem('userInfo')).id;
            this.userInfo.name = this.formLabelAlign.name;
            this.userInfo.phoneNum = this.formLabelAlign.phoneNum;
            window.localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
            // 将用户token保存到vuex中, 并设置跳转  yinglongyhy
            // this.changeLogin({ Authorization: _this.userToken })
            this.$router.push("/");

          })
          .catch((error) => {
            alert("修改失败");
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

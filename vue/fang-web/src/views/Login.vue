<template>
  <div class="form">
    <header class="header">登录</header>
    <div class="contain">
      <el-form :label-position="labelPosition" label-width="60px" :model="formLabelAlign">
        <el-form-item label="账号">
          <el-input v-model="formLabelAlign.account"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="formLabelAlign.password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-radio v-model="radio" label="1">用户</el-radio>
          <el-radio v-model="radio" label="2" @click="handleClick">管理员</el-radio>
        </el-form-item>
        <el-form-item>
          <el-button-group>
            <el-button @click="onSubmit" type="primary">登录</el-button>
            <router-link to="/about">
              <el-button>取消</el-button>
            </router-link>
          </el-button-group>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data () {
    return {
      labelPosition: 'left',
      userToken: null,
      formLabelAlign: {
        account: null,
        password: null
      },
      radio: '1',
      sign: '1'
    }
  },
  methods: {
    onSubmit () {
      console.log('submit')

      if (this.formLabelAlign.account === null || this.formLabelAlign.password === null) {
        alert('账号或密码不能为空')
      } else {
        console.log(this.formLabelAlign)
        // 用户和管理员请求路径不同，跳转url不同
        if (this.radio === '1') { // 用户
          axios.post('/api/users/login', this.formLabelAlign)
            .then(res => {
              console.log(res)
              this.userToken = res.headers.authorization
              console.log(this.userToken)

              this.$store.commit('changeuLogin', this.userToken)
              console.log(this.$store.state.userToken)
              // 将用户token保存到vuex中, 并设置跳转  yinglongyhy
              // this.changeLogin({ Authorization: _this.userToken })
              this.$router.push('/about')

              alert('登陆成功')
            })
            .catch(error => {
              alert('账号或密码错误')
              console.log(error)
            })
        } else { // 管理员
          axios.post('/api/admins/login', this.formLabelAlign)
            .then(res => {
              console.log(res)
              this.adminToken = res.headers.authorization
              console.log(this.adminToken)

              this.$store.commit('changeaLogin', this.adminToken)
              console.log(this.$store.state.adminToken)
              // 将用户token保存到vuex中, 并设置跳转  yinglongyhy
              // this.changeLogin({ Authorization: _this.userToken })
              this.$router.push('/about')

              alert('登陆成功')
            })
            .catch(error => {
              alert('账号或密码错误')
              console.log(error)
            })
        }
      }
    }
  }
}
</script>

<style lang="stylus" scoped>
  .form
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.3)
    position absolute
    top 50%
    left 50%
    transform: translate(-50%,-50%)
    width 400px
    height 500px
    .contain
      width 300px
      margin 50px auto
    .header
      text-align center
      width 200px
      margin 100px auto 0
      font-weight bold
      font-size 25px
</style>

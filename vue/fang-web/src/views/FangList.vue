<template>
  <div id="fangList">
    <el-card class="box-card">
      <el-radio-group v-model="address" size="medium">
        <el-radio-button name="0001" label="上海"></el-radio-button>
        <el-radio-button name="0002" label="北京"></el-radio-button>
        <el-radio-button name="0003" label="广州"></el-radio-button>
        <el-radio-button name="0004" label="深圳"></el-radio-button>
      </el-radio-group>
    </el-card>
    <el-main></el-main>
  </div>
</template>

<script>
export default {
  name: "FangList",
  data() {
    return {
      address: "上海"
    };
  },
  methods: {}
};
</script>

<style lang="stylus" scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 40%;
  margin-top: 30px;
  margin-left: 30%;
}
</style>
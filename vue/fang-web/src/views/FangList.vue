<template>
  <div id="fangList">
    <el-card class="box-card">
      <el-form
        ref="form"
        label-position="left"
        :model="form"
        label-width="80px"
        style="text-align: left"
      >
        <el-form-item label="区域">
          <el-radio-group v-model="form.address" size="mini">
            <el-radio-button
              v-for="addr in addresses"
              :label="addr"
              :key="addr"
            ></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="租金">
          <el-radio-group v-model="form.price" size="mini">
            <el-radio-button
              v-for="price in prices"
              :label="price"
              :key="price"
            ></el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </el-card>
    <el-main class="wrap">
      <HouseList :items="this.items"></HouseList>
    </el-main>
    <el-main>
      <el-pagination layout="prev, pager, next" :total="items.length"> </el-pagination>
    </el-main>
  </div>
</template>

<script>
import HouseList from "@/components/HouseList.vue";
export default {
  name: "FangList",
  components: {
    HouseList,
  },
  data() {
    return {
      items: [
        {
          id: 1,
          detailUrl: "//#",
          lables: ["离地铁近", "近珠江", "楼下超市"],
          address: "荔湾区",
          price: 55.5,
          titleText: "test",
          img:
            "https://img30.360buyimg.com/popWaterMark/jfs/t3361/148/2155093456/104676/68d1dbcf/5843abb6N2aa9852c.jpg",
        },
        {
          id: 2,
          detailUrl: "//#",
          lables: ["离地铁近", "近珠江", "楼下超市"],
          address: "荔湾区",
          price: 55.5,
          titleText: "test",
          img:
            "https://img30.360buyimg.com/popWaterMark/jfs/t3361/148/2155093456/104676/68d1dbcf/5843abb6N2aa9852c.jpg",
        },
      ],
      addresses: [
        "不限",
        "增城",
        "番禺",
        "南沙",
        "花都",
        "白云",
        "海珠",
        "越秀",
      ],
      prices: [
        "不限",
        "500元以下",
        "500-1000元",
        "2000-3000元",
        "3000-5000元",
        "5000-8000元",
        "8000元以上",
      ],
      form: {
        address: "不限",
        price: "不限",
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
    };
  },
  methods: {},
};
</script>

<style lang="stylus" scoped>
.text {
  font-size: 15px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 40%;
  margin-top: 30px;
  margin-left: 30%;
}

.wrap {
  width: 1200px;
  margin: 0 auto;
}
</style>
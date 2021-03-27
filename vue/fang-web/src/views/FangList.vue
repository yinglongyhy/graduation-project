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
          <el-radio-group v-model="form.price" size="mini" 
              @change="submit()">
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
      <el-pagination layout="prev, pager, next" :total="this.total">
      </el-pagination>
    </el-main>
  </div>
</template>

<script>
import axios from "axios";
import HouseList from "@/components/HouseList.vue";
export default {
  name: "FangList",
  components: {
    HouseList,
  },
  data() {
    return {
      items: null,
      total: 0,
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
  created() {
    this.init();
  },
  methods: {
    init() {
      var params = "";
      switch (this.form.price) {
        case "500元以下":
          params += '&rentMax=500';
          break;
        case "500-1000元":
          params += '&rentMin=500&rentMax=1000';
          break;
        case "2000-3000元":
          params += '&rentMin=2000&rentMax=3000';
          break;
        case "3000-5000元":
          params += '&rentMin=3000&rentMax=5000';
          break;
        case "5000-8000元":
          params += '&rentMin=5000&rentMax=8000';
          break;
        case "8000元以上":
          params += '&rentMin=8000';
          break;
        default:
          break;
      }

      axios
        .get("/api/houseInfo/page?rented=0" + params, {
          headers: { token: window.localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          this.items = res.data.records;
          this.total = res.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    submit(label) {
      console.log(label)
      this.init();
    }
  },
};
</script>

<style lang="stylus" scoped>
#fangList {
  margin-left: 30%;
  width: 40%;
}

.text {
  font-size: 15px;
}

.item {
  padding: 18px 0;
}

.box-card {
  margin-top: 30px;
}

.wrap {
  width: 100%;
  margin: 0 auto;
}
</style>
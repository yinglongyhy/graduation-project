<template>
  <div class="houseInfo">
    <el-row>
      <el-col :span="12">
        <div class="img-box">
          <el-carousel :interval="4000" type="card" height="360px" width="100%">
            <el-carousel-item
              v-for="item in this.formLabelAlign.pictureList"
              :key="item.id"
            >
              <img :src="item.url" height="360px" alt="" />
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card" style="height: 360px">
          <p style="color: red; font-size: 50px; float: left; width: 100%">
            {{ this.formLabelAlign.rent }} 元/月
          </p>
          <p style="color: gray; font-size: 30px; float: left; width: 100%">
            建筑面积：{{ this.formLabelAlign.area }}平米
          </p>
          <p style="color: gray; font-size: 30px; float: left; width: 100%">
            {{ this.formLabelAlign.address.fullname + this.formLabelAlign.detailedAddress }}
          </p>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <p style="float: left; color: gray; font-size: 30px">
          房源描述：{{ this.formLabelAlign.description }}
        </p>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4">
        <p style="float: left; color: gray; font-size: 30px;">
        亮点：
        </p>
      </el-col>
      <el-col :span="20">
        <el-tag
          style="float: left"
          v-for="item in this.formLabelAlign.labelList"
          :key="item"
          >{{ item }}</el-tag
        >
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import axios from "axios";

export default {
  name: "HouseInfo",
  data() {
    return {
      addressOptions: [],
      dialogImageUrl: "",
      dialogVisible: false,
      headers: {
        token: window.localStorage.getItem("token"),
      },
      labelPosition: "left",
      userToken: null,
      userInfo: null,
      formLabelAlign: {
        id: null,
        description: null,
        address: null,
        detailedAddress: null,
        area: null,
        rent: null,
        labelList: [],
        pictureList: [],
      },
    };
  },
  created() {
    if (this.$route.params.row !== null) {
      this.init(this.$route.params.row);
    }
  },
  methods: {
    init(row) {
      this.formLabelAlign.id = row.id;
      this.formLabelAlign.description = row.description;
      this.addressOptions.push(row.address);
      this.formLabelAlign.detailedAddress = row.detailedAddress;
      this.formLabelAlign.address = row.address;
      this.formLabelAlign.area = row.area;
      this.formLabelAlign.rent = row.rent;
      this.formLabelAlign.labelList = row.labelList;
      for (var i = 0; i < row.pictureList.length; i++) {
        var name = row.pictureList[i];
        this.formLabelAlign.pictureList.push({
          id: i,
          name: name,
          url: "http://localhost:8080/images/" + name,
        });
      }
    },
  },
};
</script>

<style lang="stylus" scoped>
.houseInfo {
  margin-left: 20%;
  margin-top: 10%;
  width: 60%;
}

.img-box {
}

.info {
  margin-left: 0;
}
</style>

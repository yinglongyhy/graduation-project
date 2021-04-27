<template>
  <div class="contain">
    <div>
      <el-carousel :interval="4000" type="card" width="100%">
        <el-carousel-item
          v-for="item in this.formLabelAlign.pictureList"
          :key="item.id"
        >
          <!-- <img :src="item.url" height="360px" alt="" /> -->
          <el-image
            style=""
            :src="item.url"
            :fit="scale-down"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div>
      <el-row>
        <el-col :span="24">
          <el-tag
            style="float: left; margin: 10px"
            v-for="item in this.formLabelAlign.labelList"
            :key="item"
            >{{ item }}
          </el-tag>
       </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <p style="float: left; color: black; font-size: 30px">
            <i class="el-icon-info"></i>
            房源描述：{{this.formLabelAlign.description }}
          </p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <p style="float: left; color: black; font-size: 20px">
            <i class="el-icon-location"></i>
            {{ this.formLabelAlign.address.fullname + this.formLabelAlign.detailedAddress }}
          </p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <p style="float: left; color: black; font-size: 30px">
            <i class="el-icon-s-home"></i>
            <span style="color: red">     {{ this.formLabelAlign.area }}</span> 平米
          </p>
        </el-col>
        <el-col :span="12">
          <p style="float: left; color: black; font-size: 30px">
            <i class="el-icon-money"></i>
            <span style="color: red">     {{ this.formLabelAlign.rent }}</span> 元/月
          </p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <p style="float: left; color: black; font-size: 30px">
            <i class="el-icon-user"></i>
            <span>     {{ this.formLabelAlign.owner }}</span>
          </p>
        </el-col>
        <el-col :span="12">
          <p style="float: left; color: black; font-size: 30px">
            <i class="el-icon-phone"></i>
            <span>     {{ this.formLabelAlign.phoneNum }}</span>
          </p>
        </el-col>
      </el-row>
    </div>
    <div>
      <el-row>
        <el-col :span="24">
          <p style="float: left; color: black; font-size: 30px">
            图片详情
          </p>
        </el-col>
      </el-row>
      <el-row v-for="(item, index) in pictureListForTwo" :key="index">
        <el-col :span="12">
          <el-image
            style="margin: 10px;"
            :src="item[0].url"
            :fit="scale-down"></el-image>
        </el-col>
        <el-col :span="12">
          <el-image  
            v-if="item[1] != undefined"
            style="margin: 10px;"
            :src="item[1].url"
            :fit="scale-down"></el-image>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from "axios";

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
        owner: null,
        phoneNum: null,
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
      this.init(this.$route.params.id);
    }
  },
  computed: {
    pictureListForTwo: function () {
      // `this` 指向 vm 实例
      var result = [];
      for(var i=0,len=this.formLabelAlign.pictureList.length;i<len;i+=2){
        result.push(this.formLabelAlign.pictureList.slice(i,i+2));
      }
      return result
    }
  },
  methods: {
    init(id) {
      axios
        .get(
          "/api/houseInfo/" +
            id,
          {
            headers: { token: window.localStorage.getItem("token") },
          }
        )
        .then((res) => {
          console.log(res);
          var row = res.data;
          this.formLabelAlign.id = row.id;
          this.formLabelAlign.description = row.description;
          this.addressOptions.push(row.address);
          this.formLabelAlign.detailedAddress = row.detailedAddress;
          this.formLabelAlign.address = row.address;
          this.formLabelAlign.area = row.area;
          this.formLabelAlign.rent = row.rent;
          this.formLabelAlign.labelList = row.labelList;
          this.formLabelAlign.owner = row.owner;
          this.formLabelAlign.phoneNum = row.phoneNum;
          for (var i = 0; i < row.pictureList.length; i++) {
            var name = row.pictureList[i];
            this.formLabelAlign.pictureList.push({
              id: i,
              name: name,
              url: "/api/images/" + name,
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style lang="stylus" scoped>

.contain {
  width: 60%;
  margin-left: 20%;
}

</style>

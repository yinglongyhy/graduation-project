<template>
  <div>
    <div class="form">
    <header class="header">发布房源</header>
    <div class="contain">
      <el-form
        :label-position="labelPosition"
        label-width="80px"
        :model="formLabelAlign"
      >
        <el-form-item label="描述">
          <el-input v-model="formLabelAlign.description"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-select
            v-model="formLabelAlign.address"
            filterable
            remote
            reserve-keyword
            placeholder="请输入关键词"
            :remote-method="remoteMethod"
            :loading="loading"
            style="width: 100%"
          >
            <el-option
              v-for="item in addressOptions"
              :key="item.code"
              :label="item.fullname"
              :value="item.code"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input v-model="formLabelAlign.detailedAddress"></el-input>
        </el-form-item>
        <el-form-item
          label="面积"
          prop="area"
          :rules="[
            { required: true, message: '面积不能为空' },
            { type: 'number', message: '面积必须为数字值' },
          ]"
        >
          <el-input type="area" v-model.number="formLabelAlign.area"></el-input>
        </el-form-item>
        <el-form-item
          label="租金"
          prop="rent"
          :rules="[
            { required: true, message: '租金不能为空' },
            { type: 'number', message: '租金必须为数字值' },
          ]"
        >
          <el-input type="rent" v-model.number="formLabelAlign.rent"></el-input>
        </el-form-item>
        <el-form-item label="标签">
          <el-input
            v-model="formLabelAlign.labelList"
            placeholder="使用英文逗号分隔"
          ></el-input>
        </el-form-item>

        <el-form-item label="图片上传">
          <el-upload
            :action="'/api/picture/upload'"
            list-type="picture-card"
            :on-success="
              (response, file, fileList) =>
                uploadSuccess(response, file, fileList)
            "
            :on-preview="imgPreview"
            :on-remove="(file, fileList) => imgRemove(file, fileList)"
            :file-list="formLabelAlign.pictureList"
            :headers="headers"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt />
          </el-dialog>
        </el-form-item>

        <el-form-item>
          <el-button-group>
            <el-button @click="onSubmit" type="primary">发布</el-button>
            <router-link to="/ownerManagerment">
              <el-button>取消</el-button>
            </router-link>
          </el-button-group>
        </el-form-item>
      </el-form>
    </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SaveHouseInfo",
  data() {
    return {
      addressOptions: [],
      dialogImageUrl: "",
      dialogVisible: false,
      headers: {
        token: window.localStorage.getItem('token'),
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
      this.formLabelAlign.address = row.address.code;
      this.formLabelAlign.area = row.area;
      this.formLabelAlign.rent = row.rent;
      this.formLabelAlign.labelList = row.labelList.join(",");
      for (var i = 0; i < row.pictureList.length; i++) {
        var name = row.pictureList[i];
        this.formLabelAlign.pictureList.push({
          name: name.substring(name.lastIndexOf('/') + 1),
          url: name,
        });
      }
    },
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          axios
            .get("/api/address/query?name=" + query, {
              headers: { token: window.localStorage.getItem('token') },
            })
            .then((res) => {
              console.log(res);
          this.loading = false;
              this.addressOptions = res.data;
            })
            .catch((error) => {
              console.log(error);
            });
        }, 200);
      } else {
        this.options = [];
      }
    },
    onSubmit() {
      console.log("submit");
      this.formLabelAlign.labelList = this.formLabelAlign.labelList
        .toString()
        .split(",");
      this.formLabelAlign.pictureList = this.formLabelAlign.pictureList.map(
        (o) => o.name
      );
      console.log(this.formLabelAlign);
      axios
        .post("/api/houseInfo/save", this.formLabelAlign, {
          headers: { token: window.localStorage.getItem('token') },
        })
        .then((res) => {
          console.log(res);
          this.$router.push("/ownerManagerment");
        })
        .catch((error) => {
          alert("保存错误");
          console.log(error);
        });
    },
    imgPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    imgRemove(file, fileList) {
      console.log(file);
      console.log(fileList);
      var name;
      if (file.response == undefined) {
        name = file.name;
      } else {
        name = file.response;
      }
      console.log(name);
      for (var i = 0; i < this.formLabelAlign.pictureList.length; i++) {
        if (this.formLabelAlign.pictureList[i].name == name) {
          this.formLabelAlign.pictureList.splice(i, 1);
        }
      }
    },
    uploadSuccess(response, file, fileList) {
      console.log(file);
      console.log(fileList);
      this.formLabelAlign.pictureList.push({
        name: file.response,
        url: "/api/images/" + file.response,
      });
      // var imageName = response;
      // fileList.push({
      //   name: imageName,
      //   url: "/api/images/" + imageName,
      // });
      // console.log(fileList);
    },
  },
};
</script>

<style lang="stylus" scoped>
.form {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.3);
  padding: 1px;
  margin: 0 20vw;
  width: 60vw;
}
  .contain {
    width: 700px;
    margin: 50px auto;
  }

  .header {
    text-align: center;
    width: 200px;
    margin: 100px auto 0;
    font-weight: bold;
    font-size: 25px;
  }

</style>

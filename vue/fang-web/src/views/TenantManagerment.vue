<template>
  <div class="contain">
    <el-card style="width: 100%; margin-bottom: 20px;">
      <el-form :inline="true" :model="params" style=" float: left;">
        <el-form-item label="地址">
          <el-select
            v-model="params.address"
            filterable
            remote
            reserve-keyword
            placeholder="请输入关键词"
            clearable=true
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
        <el-form-item label="价格区间">
          <el-input v-model="params.rentMin" style="width: 100px;"></el-input>
           - 
          <el-input v-model="params.rentMax" style="width: 100px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="图片" width="180">
        <template slot-scope="scope">
          <el-image 
            style="width: 100px; height: 100px"
            :src="scope.row.pictureList[0]" 
            :preview-src-list="scope.row.pictureList">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="280">
      </el-table-column>
      <el-table-column
        :formatter="formatAddress"
        prop="address"
        label="地址"
        width="280"
      >
      </el-table-column>
      <el-table-column prop="leaseRent" label="租金" width="100">
      </el-table-column>      
      <el-table-column prop="owner" label="房主" width="100">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleShow(scope.$index, scope.row)"
            >查看</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="this.total">
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "TenantManagerment",
  data() {
    return {
      addressOptions: [],
      tableData: null,
      total: 0,
      params: {
        address: null,
        rentMin: null, 
        rentMax: null
      }
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      axios
        .get(
          "/api/houseInfo/page?tenant=" +
            JSON.parse(window.localStorage.getItem("userInfo")).id,
          {
            headers: { token: window.localStorage.getItem("token") },
          }
        )
        .then((res) => {
          console.log(res);
          this.tableData = res.data.records;
          for (var i = 0; i < this.tableData.length; i++) {
            this.tableData[i].pictureList = this.tableData[i].pictureList.map(function(el) { return 'http://localhost:8080/images/' + el } );
          }
          this.total = res.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    onSubmit() {
      var query = ''
      if (this.params.address !== null) query = query + '&address=' + this.params.address
      if (this.params.rentMin !== null) query = query + '&rentMin=' + this.params.rentMin
      if (this.params.rentMax !== null) query = query + '&rentMax=' + this.params.rentMax
      axios
        .get(
          "/api/houseInfo/page?rentType=1&tenant=" +
            JSON.parse(window.localStorage.getItem("userInfo")).id + query,
          {
            headers: { token: window.localStorage.getItem("token") },
          }
        )
        .then((res) => {
          console.log(res);
          this.tableData = res.data.records;
          for (var i = 0; i < this.tableData.length; i++) {
            this.tableData[i].pictureList = this.tableData[i].pictureList.map(function(el) { return 'http://localhost:8080/images/' + el } );
          }
          this.total = res.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    formatAddress(row) {
      return row.address.fullname + row.detailedAddress;
    },
    handleShow(index, row) {
      console.log(index, row);
      this.$router.push({
        name: "HouseInfo",
        params: {
          id: row.id,
        },
      });
    },
    handleEdit(index, row) {
      console.log(index, row);
      this.$router.push({
        name: "SaveHouseInfo",
        params: {
          row: row,
        },
      });
    },
    handleDelete(index, row) {
      if (row.rented === 1) {
        alert("房屋已出租，不能删除");
        return;
      }
      axios
        .delete("/api/houseInfo/" + row.id, {
          headers: { token: window.localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          this.init();
        })
        .catch((error) => {
          console.log(error);
        });
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
  },
};
</script>

<style lang="stylus" scoped>
.contain {
  width: 60%;
  margin-left: 20%;
}
</style>

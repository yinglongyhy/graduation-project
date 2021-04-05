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
    <!-- <el-main class="wrap">
      <HouseList :items="this.items"></HouseList>
    </el-main> -->
    <el-table :data="tableData" class="table" @cell-click="houseInfo">
      <el-table-column width="180">
        <template slot-scope="scope">
          <el-image 
            style="width: 100px; height: 100px"
            :src="scope.row.pictureList[0]" 
            :preview-src-list="scope.row.pictureList">
          </el-image>
        </template> 
      </el-table-column>
      <el-table-column width="680">
        <template slot-scope="scope">
          <p style="margin: 5px; text-align: center;">
            <span class="description">{{ scope.row.description }}</span>
          </p>
          <p style="text-align: center;">
            <span v-for="(lable, index) in scope.row.labelList" :key="lable">
              {{
                (index === scope.row.labelList.length - 1 && lable) || lable + " | "
              }}
            </span>
          </p>
          <p style="margin: 5px; text-align: center;">
            {{ scope.row.address.fullname + scope.row.detailedAddress }}
          </p>
        </template>
      </el-table-column>
      <el-table-column width="180">
        <template slot-scope="scope">
          <p style="margin: 5px; text-align: center;">
            <span class="price">{{ scope.row.rent }}</span
            >元/月
          </p>
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination
        :current-page="currentPage"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import axios from "axios";
// import HouseList from "@/components/HouseList.vue";
export default {
  name: "FangList",
  components: {
    // HouseList,
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: null,
      addressOptions: [],
      params: {
        address: null,
        rentMin: null, 
        rentMax: null
      },
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.onSubmit()
    },
    onSubmit() {
      var query = ''
      query = query + '&pageNumber=' + this.currentPage
      query = query + '&pageSize=' + this.pageSize
      if (this.params.address !== null) query = query + '&address=' + this.params.address
      if (this.params.rentMin !== null) query = query + '&rentMin=' + this.params.rentMin
      if (this.params.rentMax !== null) query = query + '&rentMax=' + this.params.rentMax
      this.getList(query)
    },
    getList(query) {
      axios
        .get("/api/houseInfo/page?rented=0" + query, {
          headers: { token: window.localStorage.getItem("token") },
        })
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
    houseInfo(row, column, cell, event) {
      if (column.id === "el-table_1_column_1") {
        return
      }
      console.log(row, column, cell, event);
      this.$router.push({
        name: "HouseInfo",
        params: {
          id: row.id,
        },
      });
    },
    handleSizeChange(val) {
      this.currentPage = 1
      this.pageSize = val
      this.onSubmit()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.onSubmit()
    }
  },
};
</script>

<style lang="stylus" scoped>

.contain {
  width: 60%;
  margin-left: 20%;
}

.table {
  width: 100%;
}

.item {
  padding: 18px 0;
}

.box-card {
  margin-top: 30px;
}

.description {
  font-size: 20px;
  font-weight: 500;
  color: #000000;
  padding-right: 4px;
}

.price {
  font-size: 28px;
  font-weight: 700;
  color: #f32e2e;
  padding-right: 4px;
}

.page {
  text-align: center
}
</style>
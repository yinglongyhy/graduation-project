<template>
  <div class="contain">
    <el-card style="width: 100%; margin-bottom: 20px;">
      <el-form :model="params" class="choice">
        <!-- <el-form-item label="地址">
          <el-select
            v-model="params.address"
            filterable
            remote
            reserve-keyword
            placeholder="请输入关键词"
            :clearable="true"
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
        </el-form-item> -->
        <el-form-item label="区域">
          <el-link v-for="item in area" :key="item.value" style="margin: 0 20px;" :type="item.type" @click="areaClick(item)">
            {{item.key}}
          </el-link>
        </el-form-item>
        <el-form-item label="特点">
          <el-link v-for="item in homeLabel" :key="item.value" style="margin: 0 20px;" :type="item.type" @click="labelClick(item)">
            {{item.key}}
          </el-link>
        </el-form-item>
        <el-form-item label="价格区间">
          <el-input v-model="params.rentMin" style="width: 100px;" size="small"></el-input>
           - 
          <el-input v-model="params.rentMax" style="width: 100px;" size="small"></el-input>
          <el-button type="primary" @click="onSubmit" style="margin-left: 10px;" size="small">查询</el-button>
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
        label: null,
        rentMin: null, 
        rentMax: null
      },
      area: [
        {key: '不限', value: null, type: ''},
        {key: '荔湾', value: 440103, type: ''},
        {key: '越秀', value: 440104, type: ''},
        {key: '海珠', value: 440105, type: ''},
        {key: '天河', value: 440106, type: ''},
        {key: '白云', value: 440111, type: ''},
        {key: '黄埔', value: 440112, type: ''},
        {key: '番禺', value: 440113, type: ''},
        {key: '花都', value: 440114, type: ''},
        {key: '南沙', value: 440115, type: ''},
        {key: '从化', value: 440117, type: ''},
        {key: '增城', value: 440118, type: ''},
      ],
      homeLabel: [
        {key: '不限', value: null, type: ''},
        {key: '南北通透', value: 5, type: ''},
        {key: '交通方便', value: 6, type: ''},
        {key: '阳光充足', value: 7, type: ''},
        {key: '配套设施完善', value: 8, type: ''},
        {key: '小区绿化好', value: 9, type: ''},
        {key: '安静', value: 10, type: ''},
        {key: '阳光好', value: 11, type: ''},
        {key: '中层', value: 12, type: ''},
      ],
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
      if (this.params.label !== null) query = query + '&label=' + this.params.label
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
            this.tableData[i].pictureList = this.tableData[i].pictureList.map(function(el) { return '/api/images/' + el } );
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
    areaClick(item) {
      this.params.address = item.value;
      for(let i = 0; i < this.area.length; i++) {
        this.area[i].type = '';
      }
      item.type = 'danger';
    },
    labelClick(item) {
      this.params.label = item.value;
      for(let i = 0; i < this.homeLabel.length; i++) {
        this.homeLabel[i].type = '';
      }
      item.type = 'danger';
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

.choice {
  text-align: left;
}
</style>
<template>
  <div class="contain">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="description" label="描述" width="280">
      </el-table-column>
      <el-table-column
        :formatter="formatAddress"
        prop="address"
        label="地址"
        width="280"
      >
      </el-table-column>
      <el-table-column prop="leaseRent" label="租金" width="180">
      </el-table-column>      
      <el-table-column prop="owner" label="房主" width="180">
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
      tableData: null,
      total: 0,
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
          row: row,
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
  },
};
</script>

<style lang="stylus" scoped>
.contain {
  width: 60%;
  margin-left: 20%;
}
</style>

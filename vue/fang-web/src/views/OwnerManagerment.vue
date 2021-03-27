<template>
  <div class="contain">
    <el-button
      type="primary"
      style="float: left"
      @click="handleEdit(null, null)"
      >添加房源</el-button
    >
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
      <el-table-column
        :formatter="formatRented"
        prop="rented"
        label="是否出租"
        width="180"
      >
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleShow(scope.$index, scope.row)"
            >查看</el-button
          >
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button
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
  name: "OwnerManagerment",
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
          "/api/houseInfo/page?owner=" +
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
    formatRented(row) {
      return row.rented === 0 ? "未出租" : "已出租";
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

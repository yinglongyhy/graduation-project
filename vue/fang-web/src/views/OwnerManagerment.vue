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
    <el-button
      type="primary"
      style="float: left; margin-bottom: 20px"
      @click="handleEdit(null, null)"
      >添加房源</el-button
    >
    <el-table :data="tableData" class="table">
      <el-table-column label="图片" width="180">
        <template slot-scope="scope">
          <el-image 
            style="width: 100px; height: 100px"
            :src="scope.row.pictureList[0]" 
            :preview-src-list="scope.row.pictureList">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="350">
      </el-table-column>
      <el-table-column
        :formatter="formatAddress"
        prop="address"
        label="地址"
        width="350"
      >
      </el-table-column>
      <el-table-column
        label="是否出租"
        width="180"
      >
      <template slot-scope="scope">
        <el-button @click="leaseInfo(scope.row)">{{scope.row.rented == 1 && '已出租' || '未出租'}}</el-button>
      </template>
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
    <el-dialog title="租约信息" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="租客用户名" :label-width="formLabelWidth">
          <el-input v-model="form.tenantName" autocomplete="off" :disabled="isDisable"/>
        </el-form-item>
        <el-form-item label="租客租房密钥" :label-width="formLabelWidth">
          <el-input v-model="form.leaseKey" autocomplete="off"  :disabled="isDisable"/>
        </el-form-item>
        <el-form-item label="租金" :label-width="formLabelWidth">
          <el-input v-model="form.rent" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div v-if="form.leaseId !== null" slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateLease()">确 定</el-button>
        <el-button @click="deleteLease()">解除租约</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
      <div v-if="form.leaseId === null" slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addLease()">确 定</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>
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
      addressOptions: [],
      isDisable: true,
      row: null,
      dialogTableVisible: false,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      form: {
        leaseId: null,
        houseInfoId: null,
        tenantName: null,
        leaseKey: null,
        rent: null
      },
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
          "/api/houseInfo/page?owner=" +
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
          console.log(this.tableData)
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
          "/api/houseInfo/page?rentType=0&owner=" +
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
    leaseInfo(row) {
      this.row = row
      this.isDisable = row.rented === 1
      this.form.houseInfoId = row.id
      if (row.rented === 1) {
        this.form.leaseId = row.leaseId
        this.form.tenantName = row.tenant
        this.form.leaseKey = row.leaseKey
        this.form.rent = row.leaseRent
      }
      this.dialogFormVisible = true
    },
    addLease() {
      console.log(this.form)
      axios
        .post("/api/lease", this.form, {
          headers: { token: window.localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res)
          this.row.rented = 1
          this.row.tenant = this.form.tenantName
          this.row.leaseRent = this.form.rent
          this.row.leaseKey = this.form.leaseKey
          this.row.leaseId = res.data.id
          this.form.leaseId = this.row.leaseId
          this.dialogFormVisible = false
        })
        .catch((error) => {
          console.log(error);
          alert("租户名或租户租房密钥错误")
        });
    },
    updateLease() {
      if (this.row.leaseRent === this.form.rent) {
        this.dialogFormVisible = false
        return
      }
      axios
        .put("/api/lease", this.form, {
          headers: { token: window.localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res)
          this.row.leaseRent = this.form.rent
          this.dialogFormVisible = false
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteLease() {
      axios
        .delete("/api/lease/" + this.form.leaseId, {
          headers: { token: window.localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res)
          this.row.rented = 0
          this.row.leaseId = null
          this.row.tenant = null
          this.row.leaseKey = null
          this.row.leaseRent = null
          this.cleanForm()
          this.dialogFormVisible = false
        })
        .catch((error) => {
          console.log(error);
        });
    },
    cleanForm() {
      this.form.leaseId = null
      this.form.houseInfoId = null
      this.form.tenantName = null
      this.form.leaseKey = null
      this.form.rent = null
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
  width: 70%;
  margin-left: 15%;
}
.table {
  width: 100%;
}
</style>

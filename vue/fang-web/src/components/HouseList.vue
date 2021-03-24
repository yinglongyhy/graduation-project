/* eslint-disable vue/no-parsing-error */
<template>
  <div id="houseList" class="houseList">
    <dl v-for="item in items" :key="item.id" class="list">
      <dt class="img rel floatl">
        <a target="_blank" :href="item.detailUrl">
          <img :src="item.img" />
        </a>
      </dt>
      <dd class="info rel">
        <p class="title">
          <a :href="item.detailUrl" target="_blank" :title="item.titleText">{{
            item.titleText
          }}</a>
        </p>
        <p class="font15 mt12 bold">
          <span v-for="(lable, index) in item.lables" :key="lable">
            {{ (index === item.lables.length - 1 && lable) || lable + " | " }}
          </span>
        </p>
        <p class="gray6 mt12">
          {{ item.address }}
        </p>
        <p class="gray6 mt15" style="line-height: 16px"></p>
        <p class="mt12"></p>
        <div class="moreInfo">
          <p class="mt5 alingC">
            <span class="price">{{ item.price }}</span
            >元/月
          </p>
        </div>
      </dd>
    </dl>
  </div>
</template>

<script>
export default {
  name: "HouseList",
  props: {
    items: { type: Array, default: () => [] },
  },
  data() {
    return {
      splitline: '<span class="splitline">|</span>',
    };
  },
  methods: {},
};
</script>

<style>
.houseList .list {
  border-bottom: 1px solid #f4f4f4;
  padding: 23px 0;
  height: 166px;
}
img,
a img {
  border: 0;
  margin: 0;
  padding: 0;
}
.img img {
  width: 220px;
  height: 166px;
}
.img {
  position: relative;
}
.rel {
  position: relative;
}
dt {
  display: block;
}
.floatl {
  float: left;
}
.info {
  padding-left: 40px;
  margin-left: 240px;
  position: relative;
}
.title {
  font-size: 20px;
  font-weight: 700;
}
.font15 {
  font-size: 15px;
}
.mt12 {
  margin-top: 12px;
}
.bold {
  font-weight: 700;
}
.splitline {
  color: #d7d7d7;
  font-weight: 400;
  padding: 0 16px;
}
.gray6 {
  color: #666;
}
.houseList .list .info .moreInfo {
  width: auto;
  right: 0;
  text-align: right;
}
.houseList .list .info .moreInfo {
  position: absolute;
  top: 54px;
  right: 54px;
  width: 208px;
}
.mt5,
.mtie5 {
  margin-top: 5px;
}
.alingC {
  text-align: center;
}
p {
  display: block;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  margin: 0;
  padding: 0;
  border: 0;
}
#houseList {
  float: left;
  width: 970px;
}
.price {
  font-size: 28px;
  font-weight: 700;
  color: #f32e2e;
  padding-right: 4px;
}
dd {
  display: block;
  margin-inline-start: 40px;
}
</style>
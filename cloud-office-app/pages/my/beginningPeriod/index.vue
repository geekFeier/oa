<template>
  <view>
    <u-navbar :is-back="true" title="期初设置" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
    </u-navbar>
    <view class="flex flex-je">
      <view class="common-header-r" @click="isShowPopop = true">
        <text>{{accountText}} </text>
        <u-icon name="arrow-down-fill"></u-icon>
      </view>
    </view>

    <view class="headerBox">
      <view class="searchBox">
        <u-search input-align="center" style="padding: 0px 20px" placeholder="搜索编码或关键字查找" bg-color="#fff" v-model="keyword" :height="65" @search="searchEvent" :show-action="false" @clear="clearBtn">
        </u-search>
      </view>
    </view>

    <u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="30" active-color="#4396F7" :active-item-style="{'color':'#4396F7'}" inactive-color="#7A7C94" :current="swiperCurrent" @change="tabsChange" :list="tabList" :is-scroll="false" :bar-style="barStyle">
    </u-tabs-swiper>

    <view class="mainBox">
      <view class="main-item" v-for="(item,index) in listData" :key="index">
        <view class="main-item-l" @click="editBtn(item.id,item)">
          {{item.serial}} {{item.name}}
        </view>
        <view class="main-item-r">
          <view class="main-item-r-status" :style="{background:item.balance_status == -1 ? '#12D592' : '#FF253D'}">
            {{item.balance_status == -1 ? "借" : "贷"}}
          </view>
          <view class="main-item-r-info">
            <view class="main-item-r-info-num">
              年初：{{item.years_balance}}
            </view>
            <view class="main-item-r-info-num">
              借方：{{item.debit_balance}}
            </view>
            <view class="main-item-r-info-num">
              贷方：{{item.creditor_balance}}
            </view>
            <view class="main-item-r-info-num">
              期初：{{item.initial_balance}}
            </view>

          </view>
        </view>
      </view>

    </view>
    <button type="default" class="sure_btn" @click="loginBtn">试算平衡</button>

    <u-popup v-model="isShowPopop" mode="bottom" border-radius="56">
      <view class="popup-main">
        <view class="popup-common-title">
          — 选择账套 —
        </view>
        <view class="financial-popup-main">
          <view class="financial-popup-item " v-for="(item,index) in zhangtaoListData" @click="selectAccount(item.id,item.name)" :key="index">
            <view class="popup-item-l">
              <view class="financial-popup-item-hd">
                <text class="financial-popup-hd-txt">{{item.name}}</text>
                <view class="financial-popup-hd-status" v-if="item.id == defaultId">
                  进行中
                </view>
              </view>
              <view class="financial-popup-item-bd">
                启用时间：{{ item.start_time | filterTime}} {{item.cate.name}}
              </view>
            </view>
            <view class="popup-item-r">
              <image :src="cuttnetId == item.id ? selecta : select" style="width: 40rpx;height: 40rpx;" mode=""></image>
            </view>
          </view>
        </view>
        <view class="popup-common-btnGroup">
          <view class="popup-common-btn" @click="isShowPopop = false">
            取消
          </view>
          <view class="popup-common-btn active" @click="sureAccountBtn">
            确定
          </view>
        </view>
      </view>
    </u-popup>

  </view>
</template>

<script>
import dayjs from '@/utils/dayjs';
import {
  mapState
} from "vuex"
export default {
  data() {
    return {
      category_id: "",
      listData: [],
      page: 1,
      limit: 50,
      currentFy: {
        currentPage: 1,
        currentSize: 99999
      },
      tabList: [],
      barStyle: {
        textAlign: "center",
      },
      swiperCurrent: 0,
      keyword: "",
      background: {
        backgroundColor: "#FFFFFF",
      },
      selecta: "../../../static/image/tab2/selecta.png",
      select: "../../../static/image/tab2/select.png",
      cuttnetId: 0,
      defaultId: "",
      isShowPopop: false,
      zhangtaoListData: [],
      accountText: '选择账套', // 账套获取
      cuttnetName: ""
    };
  },
  filters: {
    filterTime(val) {
      return dayjs(val).format("YYYY年MM月")
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo
    })
  },
  onLoad() {
    this.getCate();
    this.getZhangtaoListData();
  },
  watch: {
    keyword(val) {
      if (val === "") {
        this.currentFy.page = 1;
        this.getListData();
      }
    }
  },
  methods: {
    clearBtn() {
      this.currentFy.page = 1;
      this.getListData();
    },
    searchEvent() {
      this.currentFy.page = 1;
      this.getListData();
    },
    getCate() {
      let params = {
        page: this.page,
        limit: this.limit,
        offset: (this.page - 1) * this.limit,
      }
      this.$http("enterprise.Subject/getKmeuCate", params, "post").then(res => {
        if (res.data.code == 1) {
          this.tabList = res.data.data;
          this.category_id = this.tabList[0].id || ""
          this.getListData();
        }
      })
    },
    getListData() {
      let params = {
        page: this.currentFy.currentPage,
        limit: this.currentFy.currentSize,
        offset: (this.currentFy.currentPage - 1) * this.currentFy.currentSize,
        category_id: this.category_id,
        search: this.keyword,
        sort: "serial",
        order: "asc",
        loading_type: "init_set"
      }
      this.$http("enterprise.Subject/getIndex", params, "get").then(res => {
        if (res.data.code == 1) {
          this.listData = this.treeToArray(res.data.data.rows);
        }
      })
    },
    treeToArray(tree) {
      let arr = [];
      const expanded = datas => {
        if (datas && datas.length > 0) {
          datas.forEach(e => {
            arr.push(e);
            expanded(e.children);
          })
        }
      };
      expanded(tree);
      return arr;
    },
    tabsChange(index) {
      this.swiperCurrent = index;
      this.category_id = this.tabList[index].id;
      this.currentFy.currentPage = 1;
      this.getListData()
    },
    loginBtn() {
      uni.navigateTo({
        url: "/pages/my/beginningPeriod/trialBalancing"
      })
    },
    editBtn(id, item) {
      if (item.children.length || item.children.length > 0) {
        uni.showToast({
          title: "无法编辑期初的父级科目",
          icon: "none"
        })
      } else {
        this.$navigateTo({
          url: "/pages/my/beginningPeriod/editPage?id=" + id
        }).then(res => {
          this.currentFy.currentPage = 1;
          this.getListData();
        })
      }

    },
    sureAccountBtn() {
      let params = {
        akid: this.cuttnetId
      }
      this.accountText = this.cuttnetName
      this.defaultId = this.cuttnetId
      uni.showLoading({
        title: '切换中'
      })

      this.$http("enterprise.Account_books/booksChanges", params, "post").then(res => {
        uni.hideLoading()

        if (res.data.code == 1) {
          this.isShowPopop = false;
          this.getListData()
        }
      })
    },
    getZhangtaoListData() {
      let params = {
        offset: 0,
        page: 1,
        limit: 50
      }
      this.$http("enterprise.Account_books/index", params, "post").then(res => {
        if (res.data.code == 1) {
          this.zhangtaoListData = res.data.data.rows;
          this.cuttnetId = this.userInfo.jobs.account_books_id;
          this.defaultId = this.userInfo.jobs.account_books_id;
          if (this.userInfo.jobs.account_books_id) {
            this.accountText = this.zhangtaoListData.find(item => Number(item.id) === this.userInfo.jobs.account_books_id);
            this.accountText = this.accountText ? this.accountText.name : "请选择"
          } else {
            if (this.userInfo.is_admin == 'staff') {
              this.accountText = this.zhangtaoListData.find(item => Number(item.id) === this.userInfo.jobs.account_books_id);
              this.accountText = this.accountText ? this.accountText.name : "请选择"
            } else {
              this.accountText = "请选择";
            }
          }
        } else {
          this.zhangtaoListData = [];

        }
      })
    },
    selectAccount(id, name) {
      this.cuttnetId = id;
      this.cuttnetName = name
    },
  }

}
</script>

<style lang="scss" scoped>
.flex {
  align-items: center;
}
.flex1 {
  flex: 1;
}
.flex2 {
  flex: 2;
}
.flex-je {
  justify-content: flex-end;
}
page {
  color: #150e33;
}

.sure_btn {
  background: #4396f7;
  border-radius: 49px;
  width: 90%;
  color: #fff;
  position: fixed;
  bottom: 32rpx;
  left: 50%;
  transform: translateX(-50%);
}

.headerBox {
  height: 102rpx;
  background: #fbfcff;
  line-height: 102rpx;
}

.mainBox {
  height: 69vh;
  overflow-y: auto;

  .main-item {
    display: flex;
    align-items: center;
    padding: 16rpx 32rpx;
    border-bottom: 1px solid #eef2ff;
    padding-right: 100rpx;
    justify-content: space-between;

    .main-item-l {
      font-size: 32rpx;
      font-weight: bold;
      width: 42%;
    }

    .main-item-r {
      width: 50%;
      // margin-left: 120rpx;
      display: flex;
      align-items: center;
    }

    .main-item-r-status {
      width: 40rpx;
      height: 40rpx;
      border-radius: 50%;
      line-height: 40rpx;
      text-align: center;
      color: #fff;
    }

    .main-item-r-info {
      margin-left: 30rpx;
      font-size: 30rpx;

      .main-item-r-info-num {
        margin-bottom: 12rpx;
      }

      &::nth-last-child(1) {
        margin-bottom: 0;
      }
    }
  }
}

.pul {
  background-color: #915ff2;
}

.red {
  background-color: #ff253d;
}

.blue {
  background-color: #4396f7;
}

.orange {
  background-color: #fa7443;
}

.green {
  background-color: #12d592;
}

.common-header-r {
  padding-top: 15rpx;
  padding-bottom: 15rpx;
  padding-right: 30rpx;
}
.popup-main {
  .popup-common-title {
    font-size: 36rpx;
    color: #150e33;
    text-align: center;
    margin-top: 56rpx;
  }

  .active {
    background: #4396f7 !important;
    color: #ffffff !important;
  }

  .popup-common-btnGroup {
    display: flex;
    padding: 0 50rpx;
    justify-content: space-between;
    font-size: 30rpx;
    margin-top: 50rpx;
    margin-bottom: 32rpx;

    .popup-common-btn {
      width: 260rpx;
      height: 80rpx;
      background: #f6f9fe;
      border-radius: 12rpx;
      color: #7a7c94;
      text-align: center;
      line-height: 80rpx;
    }
  }

  .financial-popup-main {
    padding: 0 32rpx;

    .financial-popup-item {
      margin-top: 32rpx;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid #eef2ff;
      padding-bottom: 32rpx;

      .financial-popup-item-hd {
        display: flex;
        align-items: center;

        .financial-popup-hd-status {
          background: #ff253d;
          border-radius: 48rpx;
          padding: 4rpx 8rpx;
          color: #fff;
          font-size: 24rpx;
          margin-left: 16rpx;
        }

        .financial-popup-hd-txt {
          font-size: 32rpx;
          color: #150e33;
          font-weight: bold;
        }
      }

      .financial-popup-item-bd {
        color: #7a7c94;
        font-size: 28rpx;
        margin-top: 30rpx;
      }
    }
  }
}
</style>
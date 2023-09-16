<template>
  <view class="statementBox">
    <view class="statementMain" v-if="!['2','4'].includes(personType)">
      <view class="common-title">
        提示设置
      </view>
      <u-grid :col="4" :border="false" style="margin-top: 12rpx;">
        <u-grid-item @click="jumpTo('pushSetting')">
          <image class="grid-icon" style="width: 54rpx;height: 54rpx;" src="../../../../static/image/tab2/ts.png" mode="">
          </image>
          <view class="grid-text">推送设置</view>
        </u-grid-item>
        <u-grid-item @click="jumpTo('gonggao')">
          <image class="grid-icon" style="width: 54rpx;height: 54rpx;" src="../../../../static/image/my/setting/gg.png" mode="">
          </image>
          <view class="grid-text">单位公告</view>
        </u-grid-item>
        <u-grid-item @click="goPieChat">
          <image class="grid-icon" style="width: 54rpx;height: 54rpx;" src="../../../../static/image/my/setting/bzt.png" mode="">
          </image>
          <view class="grid-text" style="white-space:nowrap">图形取数设置</view>
        </u-grid-item>
      </u-grid>

    </view>

    <u-popup v-model="isShowPopop" mode="bottom" border-radius="56">
      <view class="popup-main">
        <view class="popup-common-title">
          — 选择账套 —
        </view>
        <view class="financial-popup-main">
          <view class="financial-popup-item " v-for="(item,index) in listData" @click="selectAccount(item.id,index)" :key="index">
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
              <!-- <image src="../../../static/image/tab2/select.png" mode=""></image> -->
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
import { mapState } from "vuex"
import dayjs from '@/utils/dayjs';
export default {
  data() {
    return {
      selecta: "../../../static/image/tab2/selecta.png",
      select: "../../../static/image/tab2/select.png",
      detailData: {},
      isShowPopop: false,
      cuttnetId: 0,
      defaultId: "",
      enterprice: {},
      accountText: "",
      currentPage: {
        page: 1,
        limit: 10
      },
      userInfo:{},
      listData: [],
      cuttnetName: ""
    }
  },
  filters: {
    filterTime(val) {
      return dayjs(val).format("YYYY年MM月")
    }
  },
  computed: {
    ...mapState({
      personType: state => state.user.personType,
    })
  },
  mounted() {
    this.getUserInfo();
    this.getList();
  },
  methods: {

    jumpTo(val) {
      uni.navigateTo({
        url: `/pages/my/${val}/index`
      })
    },
    goPieChat() {
      this.isShowPopop = true;
      return false
      uni.navigateTo({
        url: "/pages/my/pieChatSet/index"
      })
    },
    getUserInfo() {
      this.$http("/User/getUser", {}, "post").then(res => {
        if (res.data.code == 1) {
          this.userInfo = res.data.data

          this.$store.dispatch("user/GET_USER_INFO", res.data.data);
          this.defaultId = this.userInfo ? this.userInfo.account_books_id : "";
          this.cuttnetId = this.userInfo ? this.userInfo.account_books_id : "";
        }
      })
    },
    sureBtn() {
      let params = {
        key: this.detailData.id,
        status: 1
      }
      this.$http("enterprise.applyfor.Pay_apply/operaction", params, "post").then(res => {
        if (res.data.code == 1) {
          uni.showToast({
            title: "成功",
            icon: "none"
          })
        }
      })
    },
    getList() {
      let params = {
        offset: (this.page - 1) * 10,
        page: this.page,
        limit: this.limit
      }
      console.log(111111111111111111111111111111111111111)
      this.$http("enterprise.Account_books/index", params, "post").then(res => {
        if (res.data.code == 1) {
          this.listData = res.data.data.rows;
          this.cuttnetId = this.userInfo.account_books_id;
          this.defaultId = this.userInfo.account_books_id;

          if (this.userInfo.account_books_id) {
            this.accountText = this.listData.find(item => Number(item.id) === this.userInfo.account_books_id);
            this.accountText = this.accountText ? this.accountText.name : "请选择"
          } else {
            if (this.userInfo.is_admin == 'staff') {
              this.accountText = this.listData.find(item => Number(item.id) === this.userInfo.account_books_id);
              this.accountText = this.accountText ? this.accountText.name : "请选择"
            } else {
              this.accountText = "请选择";
            }
          }
        } else {
          this.listData = [];
          this.accountText = "请选择";
        }
      })
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

<style scoped lang="scss">
.common-title {
  font-size: 34rpx;
  color: #150e33;
  font-weight: bold;
}
.statementBox {
  padding: 0 32rpx;
  margin-top: 24rpx;

  .statementMain {
    padding: 32rpx 24rpx;
    background: #fff;
    box-sizing: border-box;
    border-radius: 24rpx;

    .grid-text {
      color: #150e33;
      font-size: 26rpx;
      margin-top: 22rpx;
      width: 130rpx;
      text-align: center;
    }
  }
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

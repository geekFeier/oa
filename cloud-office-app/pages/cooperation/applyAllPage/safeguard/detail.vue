<template>
  <view>
    <u-navbar :is-back="true" title="查看报账" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
    </u-navbar>
    <view class="header-div">
      <view class="header-t">{{detailData.user.username}}提交的报账申请</view>
      <view class="header-m">{{detailData.enterprise.enterprice_name}}</view>
      <view class="header-b" :class="detailData.status | filterStatus">
        {{detailData.status == 0 ? "审核中" : (detailData.status == 1? "审核成功" : "审核失败") }}
      </view>
    </view>

    <view class="main-box">
      <view class="mainBox">
        <view class="form-item">
          <view class="form-item-title">
            审批编号
          </view>
          <view class="form-item-value">{{detailData.serial}}</view>
        </view>
        <view class="form-item">
          <view class="form-item-title">
            报账事由
          </view>
          <view class="form-item-value">{{detailData.content}}</view>
        </view>

        <view class="form-item">
          <view class="form-item-title">
            报账金额
          </view>
          <view class="form-item-value">{{detailData.money}}</view>
        </view>

        <view class="form-item">
          <view class="form-item-title">
            收款账号
          </view>
          <view class="form-item-value u-line-2">{{detailData.receivi_account}}</view>
        </view>
        <view class="form-item">
          <view class="form-item-title">
            备注
          </view>
          <view class="form-item-value">{{detailData.remarks}}</view>
        </view>
        <view class="form-item">
          <view class="form-item-title">
            凭证/电子发票
          </view>
          <view class="form-item-img-list">
            <image v-for="(item,index) in imgData" :src="item" class="form-item-img-list-i" mode="" @click="previewBtn"></image>
          </view>
        </view>
      </view>

      <view class="flowBox">
        <view class="flowBox-title">
          流程
        </view>
        <view class="flowBox-con">
          <view class="flowBox-item">
            <view class="flowBox-item-l">
              <view class="flowBox-item-l-div">
                <image :src="detailData.user.avatar" class="flowBox-item-l-img" mode=""></image>
                <image src="../../../../static/image/tab1/success.png" class="flowBox-item-l-status" mode=""></image>
              </view>

              <view class="flowBox-item-l-info">
                <view class="flowBox-item-l-info-hd">
                  <text class="u-info1">发起申请</text>
                  <!-- <text class="u-info2">07.06 14:54</text> -->
                </view>
                <view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">
                  {{detailData.user.id == userInfo.id ? "我" : detailData.user.username}}
                </view>
              </view>
            </view>
          </view>

          <view class="hrDiv">
          </view>
          <view class="" v-for="(item,index) in detailData.audit.receivi_persion" :key="index">
            <view class="flowBox-item">
              <view class="flowBox-item-l">
                <view class="flowBox-item-l-div">
                  <image :src="item.avatar" class="flowBox-item-l-img" mode=""></image>
                  <image src="../../../../static/image/tab2/dd.png" class="flowBox-item-l-status" v-if="item.status==0" mode="">
                  </image>
                  <image src="../../../../static/image/tab1/success.png" class="flowBox-item-l-status" v-if="item.status == 1" mode=""></image>
                </view>

                <view class="flowBox-item-l-info">
                  <view class="flowBox-item-l-info-hd">
                    <text class="u-info1">审批人</text>
                    <!-- <text class="u-info2">07.06 14:54</text> -->
                  </view>
                  <view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">
                    {{item.username}}({{item.status == 0 ? "等待审核" : (item.status == 1? "同意" : "拒绝") }})
                  </view>
                </view>
              </view>
            </view>

            <view class="hrDiv">

            </view>
          </view>

          <view class="flowBox-item">
            <view class="flowBox-item-l">
              <view class="flowBox-item-l-div text-center" style="background-color: #289CFF;border-radius: 8rpx;">
                <image src="../../../../static/image/tongzhi.png" mode="" style="width: 60rpx;height: 60rpx;margin-top: 12rpx;"></image>
                <!-- 	<image src="../../../../static/image/launch_icon.png" class="flowBox-item-l-img"
									mode=""></image> -->
                <!-- 		<image src="../../../../static/image/tab1/success.png" class="flowBox-item-l-status"
									mode=""></image> -->
              </view>

              <view class="flowBox-item-l-info">
                <view class="flowBox-item-l-info-hd">
                  <text class="u-info1">抄送人</text>
                  <!-- <text class="u-info2">07.06 14:54</text> -->
                </view>
                <view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">
                  已抄送{{detailData.audit.cc_persion &&detailData.audit.cc_persion.length}}人
                </view>
                <view class="flowBox-item-l-info-bd-list flex" style="margin-top: 8rpx;">
                  <view class="flowBox-item-l-info-bd-item flex flex-direction flex-wrap justify-center align-center" v-for="(item,index) in detailData.audit.cc_persion" style="margin-right: 12rpx;margin-bottom: 8rpx;">
                    <view class="" style="position: relative;">
                      <image style="height: 60rpx;width: 60rpx;" :src="item.avatar" mode="">
                      </image>
                      <image v-if="item.status==1" style="width: 20rpx;height: 20rpx;position: absolute;top: 0;right: 0;" src="../../../../static/image/tab1/success.png" mode=""></image>
                    </view>
                    <text style="margin-top: 8rpx;color: #7A7C94;">{{item.username}}</text>
                  </view>
                </view>
              </view>
            </view>
          </view>
        </view>
      </view>

      <view class="mainBox" style="padding:0; margin: 30rpx 0;" v-if="personType == 3">
        <view class="form-item">
          <view class="form-item-title">
            选择账套
          </view>
          <view style=" display: flex; justify-content: space-between;width:100%; margin-bottom: 10rpx">
            <view class="form-item-value" style="width: 200rpx;" @click="openPopup">
              <text>{{accountText}} </text>
              <u-icon name="arrow-down-fill" style="margin-left: 8rpx;"></u-icon>
            </view>
            <view class="btn3" @click="toVoucher">
              新增凭证
            </view>
          </view>

        </view>
      </view>

      <view class="btnGroup" v-if="detailData.status == 0 && detailData.user_id !== userInfo.id ">
        <view class="btn1" @click="refuseBtn">
          拒绝
        </view>
        <view class="btn2" @click="sureBtn">
          同意
        </view>
      </view>
    </view>

    <u-popup v-model="isShowPopop" mode="bottom" border-radius="56">
      <view class="popup-main">
        <view class="popup-common-title">
          — 选择账套 —
        </view>
        <view class="financial-popup-main">
          <view class="financial-popup-item " v-for="(item,index) in listData" @click="selectAccount(item.id,item.name)" :key="index">
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
import {
  mapState
} from "vuex"
import dayjs from '@/utils/dayjs';
export default {
  data() {
    return {
      selecta: "../../../../static/image/tab2/selecta.png",
      select: "../../../../static/image/tab2/select.png",
      imgData: [],
      detailData: {},
      background: {
        backgroundColor: "#FFFFFF",
      },
      isShowPopop: false,
      cuttnetId: 0,
      defaultId: "",
      personType: "",
      enterprice: {},
      accountText: "",
      currentPage: {
        page: 1,
        limit: 10
      },
      userInfo: {},
      listData: [],
      cuttnetName: ""
    };
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo
    })
  },
  filters: {
    filterStatus(val) {
      if (val == 0) {
        return "blue"
      } else if (val == 1) {
        return "green"
      } else if (val == 1) {
        return "red"
      }
    },
    filterTime(val) {
      return dayjs(val).format("YYYY年MM月")
    }
  },
  onLoad(e) {
    this.detailData = JSON.parse(decodeURIComponent(e.data));
    this.imgData = this.detailData.images ? this.detailData.images.split(",") : []

    this.getUserInfo();
    this.personType = uni.getStorageSync('personType')
    this.enterprice = uni.getStorageSync('enterprise')

    if (this.personType == 1 || this.personType == 3) {
      this.defaultId = this.userInfo ? this.userInfo.account_books_id : "";
      this.cuttnetId = this.userInfo ? this.userInfo.account_books_id : "";
      this.getList();
    }
    this.getList();

  },
  methods: {
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
    getList() {
      let params = {
        offset: (this.page - 1) * 10,
        page: this.page,
        limit: this.limit
      }
      this.$http("enterprise.Account_books/index", params, "post").then(res => {
        if (res.data.code == 1) {
          this.listData = res.data.data.rows;
          this.cuttnetId = this.userInfo.jobs.account_books_id;
          this.defaultId = this.userInfo.jobs.account_books_id;
          if (this.userInfo.jobs.account_books_id) {
            this.accountText = this.listData.find(item => Number(item.id) === this.userInfo.jobs.account_books_id);
            this.accountText = this.accountText ? this.accountText.name : "请选择"

          } else {
            if (this.userInfo.is_admin == 'staff') {
              this.accountText = this.listData.find(item => Number(item.id) === this.userInfo.jobs.account_books_id);
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
    refuseBtn() {
      let params = {
        key: this.detailData.id,
        status: 2
      }
      this.$http("enterprise.applyfor.Baozhang/operaction", params, "post").then(res => {
        if (res.data.code == 1) {
          uni.showToast({
            title: "成功",
            icon: "none"
          })
          setTimeout(() => {
            this.$navigateBack(true)
          }, 500)
        }
      })
    },
    sureBtn() {
      let params = {
        key: this.detailData.id,
        status: 1
      }
      this.$http("enterprise.applyfor.Baozhang/operaction", params, "post").then(res => {
        if (res.data.code == 1) {
          uni.showToast({
            title: "成功",
            icon: "none"
          })
          setTimeout(() => {
            this.$navigateBack(true)
          }, 500)
        }
      })
    },
    toVoucher() {
      if (this.imgData.length) {
        uni.navigateTo({
          url: "/pages/workbench/voucher/addPage?flag=1&imgData=" + JSON.stringify(this.imgData)
        })
      } else {
        uni.navigateTo({
          url: "/pages/workbench/voucher/addPage?flag=1"
        })
      }
    },
    previewBtn() {
      uni.previewImage({
        urls: this.imgData
      })
    },
    openPopup() {
      this.isShowPopop = true;
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
          this.getUserInfo()
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
.btnGroup {
  display: flex;
  justify-content: space-between;
  margin: 30rpx 0;

  .btn1 {
    width: 200rpx;
    height: 80rpx;
    line-height: 80rpx;
    background: #f6f9fe;
    border-radius: 12rpx;
    font-size: 30rpx;
    text-align: center;
    color: #7a7c94;
  }

  .btn2 {
    width: 462rpx;
    height: 80rpx;
    background: #4396f7;
    border-radius: 12rpx;
    color: #ffffff;
    font-size: 30rpx;
    text-align: center;
    margin-left: 24rpx;
    line-height: 80rpx;
  }
}
.btn3 {
  width: 462rpx;
  height: 80rpx;
  background: #4396f7;
  border-radius: 12rpx;
  color: #ffffff;
  font-size: 30rpx;
  text-align: center;
  line-height: 80rpx;
  margin-left: 24rpx;
}
.hrDiv {
  width: 0;
  height: 60rpx;
  border: 1px solid #b5bfda;
  margin-left: 36rpx;
  margin-top: 8rpx;
  margin-bottom: 8rpx;
}

page {
  color: #150e33;
  background: #fbfcff;
}

.flowBox {
  padding: 32rpx 24rpx;
  margin: 32rpx 0;
  border-radius: 18rpx;
  background-color: #fff;

  .flowBox-title {
    font-size: 32rpx;
    font-weight: bold;
    margin-bottom: 16rpx;
  }

  .flowBox-con {
    padding: 24rpx;

    .flowBox-item {
      .flowBox-item-l {
        display: flex;

        .flowBox-item-l-div {
          position: relative;
          width: 85rpx;
          height: 85rpx;

          .flowBox-item-l-img {
            width: 85rpx;
            height: 85rpx;
          }

          .flowBox-item-l-status {
            width: 20rpx;
            height: 20rpx;
            position: absolute;
            right: 0;
            bottom: 0;
          }
        }

        .flowBox-item-l-info {
          margin-left: 16rpx;
          flex: 1;

          .flowBox-item-l-info-hd {
            display: flex;
            justify-content: space-between;
            align-items: center;
          }

          .u-info1 {
            font-size: 28rpx;
          }

          .u-info2 {
            color: #7a7c94;
            font-size: 26rpx;
          }
        }
      }
    }
  }
}

.form-item {
  padding-bottom: 32rpx;
  border-bottom: 1px solid #eef2ff;

  .form-item-title {
    font-size: 30rpx;
    color: #7a7c94;
  }
}

.header-div {
  padding: 32rpx;
  background-color: #fff;

  .header-t {
    font-weight: bold;
    font-size: 32rpx;
  }

  .header-m {
    color: #7a7c94;
    font-size: 28rpx;
    margin: 24rpx 0;
  }

  .header-b {
    font-size: 28rpx;
  }
}

.main-box {
  padding: 0 32rpx;
  margin-top: 20rpx;

  .mainBox {
    border-radius: 18rpx;
    padding: 32rpx 24rpx;
    padding-bottom: 0;
    background-color: #fff;

    .form-item {
      margin-bottom: 30rpx;

      .form-item-value {
        margin-top: 20rpx;
        font-size: 32rpx;
      }

      .form-item-img-list {
        margin-top: 20rpx;
        display: flex;
        flex-wrap: wrap;
        margin-right: -25rpx;

        .form-item-img-list-i {
          margin-bottom: 20rpx;
          width: 200rpx;
          height: 200rpx;
          margin-right: 20rpx;
        }
      }
    }
  }
}

.pul {
  color: #915ff2;
}

.red {
  color: #ff253d;
}

.blue {
  color: #4396f7;
}

.orange {
  color: #fa7443;
}

.green {
  color: #12d592;
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

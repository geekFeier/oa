<template>
  <view>
    <u-navbar :is-back="true" title='结转模板列表' :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
      <view class="content" slot="right" @click="rightbtn()">
        添加模板
      </view>
    </u-navbar>
    <!-- @click="gdetail(item.id)" -->
    <view class="mainBox">
      <view class="may" v-for="(item,index) in credentials" :key="index">
        <view class="view-flx">
          <view>{{item.name}}</view>
        </view>
        <view class="view-flxa">
          <view></view>
          <view class="view-one">
            <!-- <view @click.stop="jz(item.id)" class="view-jz">结转</view> -->
            <view @click.stop="bj(item.id)" class="view-bj">编辑</view>
            <view @click.stop="deletae(item.id)">删除</view>
          </view>
        </view>
      </view>
    </view>
    <view class="yjjz" v-if="credentials.length > 0" @click="yjza()">
      一键结转
    </view>
    <u-modal v-model="modal.show" :title="modal.title" width="300px" confirmText="结账" :showCancelButton="true" @confirm="confirm">
      <view class="slot-content" slot="default">
        <ul class="modal-ul">
          <li>断号凭证检查
            <span class="green">&#10003;</span>
          </li>
          <li>生成结转凭证
            <span class="green">&#10003;</span>
          </li>
          <li>试算平衡检查
            <span class="green" v-if="modal.is_ping">&#10003;</span>
            <span class="red" v-else-if="modal.is_ping === false">&#10007;</span>
            <span class="blue" v-else>
              <div class="loader"></div>
            </span>
          </li>
          <li>进入下个月份</li>
        </ul>
      </view>
    </u-modal>
  </view>
</template>

<script>
export default {
  data() {
    return {
      modal: {
        show: false,
        is_ping: null,
        title: '',
      },
      remarks: '',//名称
      anchildreb: '请选择',//公式取值
      anchildren: '请选择',//自动平衡
      credentials: [],
      years: '',
      month: '',
      background: {
        backgroundColor: "#FFFFFF",
      },
    };
  },
  onLoad(e) {
    this.years = e.years
    this.month = e.month
  },
  onShow() {
    this.getlist()
  },
  methods: {
    //
    yjza() {
      this.modal.show = true
      this.$http("enterprise.Subject/scale", "get").then(res => {
        if (res.data.code == 1) {
          let { qc, lj, zc } = res.data.data
          this.modal.is_ping = qc.is_ping && lj.is_ping && zc.is_ping
        }
      })
    },
    //结转按钮
    jz() {

    },
    //编辑按钮
    bj(id) {
      uni.navigateTo({
        url: './bjjz?id=' + id
      })
    },
    //删除按钮
    deletae(id) {
      let param = {
        pro_id: id
      }
      this.$http("enterprise.Date_query/TemplateDel", param, "post").then(res => {
        if (res.data.code == 1) {
          uni.showToast({
            title: "删除成功",
            icon: "none"
          })
          setTimeout(() => {
            this.getlist()
          }, 1000)
        }
      })
    },
    //新赠模板
    rightbtn() {
      uni.navigateTo({
        url: './list'
      })
    },
    //列表
    getlist() {
      this.$http("enterprise.Date_query/TemplateList", "post").then(res => {
        if (res.data.code == 1) {
          this.credentials = res.data.data
        }
      })
    },
    //详情
    gdetail(id) {
      uni.navigateTo({
        url: 'fdetail?id=' + id
      })
    },
    confirm() {
      if(!this.modal.is_ping){
        uni.showToast({
          title: "试算不平衡无法继续结账",
          icon: "none"
        })
        return
      }
      let param = {
        years: this.years,
        month: this.month
      }
      this.$http("enterprise.Date_query/NextCheck", param, "get").then(res => {
        if (res.data.code == 1) {
          this.modal.show = false
          uni.showToast({
            title: "一键结转成功",
            icon: "none"
          })
          setTimeout(() => {
            uni.navigateTo({
              url: "pages/workbench/finalBill/index"
            })
          }, 1000);
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
page {
  background-color: #fff;
}
.yjjz {
  width: 200rpx;
  height: 66rpx;
  background-color: red;
  color: #fff;
  border-radius: 30rpx;
  line-height: 66rpx;
  text-align: center;
  margin: 66rpx auto;
}
.content {
  margin-right: 22rpx;
}
.mainBox {
  padding: 0 30rpx;
  .may {
    border-bottom: 1px solid #e0e0e0;
  }
  .view-flx {
    height: 88rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 30rpx;
    font-weight: bolder;
  }
  .view-flxa {
    height: 66rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .view-one {
      display: flex;
      justify-content: space-between;
      align-items: center;
      .view-jz {
        background-color: #67c23a;
      }
      .view-bj {
        background-color: #e6a23c;
      }
      view {
        padding: 10rpx 30rpx;
        background-color: red;
        color: #fff;
        border-radius: 16rpx;
        margin-right: 12rpx;
      }
    }
  }
}
.loader {
  border: 4px solid rgba(255, 255, 255, 0.3);
  border-top: 4px solid #3498db;
  border-radius: 50%;
  width: 10px;
  height: 10px;
  animation: spin 2s linear infinite;
  float: left;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
.green {
  color: green;
}
.red {
  color: red;
}
.blue {
  color: blue;
}
.modal-ul li {
  line-height: 15px;
  padding: 10px 0;
}
</style>

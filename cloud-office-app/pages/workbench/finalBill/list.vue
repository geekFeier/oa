<template>
  <view>
    <!-- TODO: -->
    <u-navbar :is-back="true" :title="flag==1 ? '新增结转'  : '编辑结转'" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
    </u-navbar>
      <button type="default" class="charu-btn" @click="sureVoucherBtn1(1)" v-if="!goodlist.length">插入行</button>

    <view>
      <u-form ref="uForm">
        <view style=" padding:0 20rpx 10rpx;">
          <u-form-item label="摘要名称" label-width="150" :border-bottom="false">
            <u-input v-model="remarks" type="input" placeholder="请输入摘要名称" />
          </u-form-item>
        </view>
      </u-form>
    </view>
    <view class="view-list">
      <view class="flex1">选择科目</view>
      <view class="flex1">选择方向</view>
      <view class="flex1">选择借贷差</view>
    </view>
    <view v-for="(item,index) in goodlist" :key="index">
      <view class="view-lista">
        <view class="flex1"><u-input disabled v-model="item.xxx1" @click="goKeMu(index)" type="input" placeholder="请选择科目" /></view>
        <view class="flex flex-ac flex1">
          <view class="pr-5">{{ item.xxx2 ? '借' : '贷' }}</view>
          <u-switch space="2" v-model="item.xxx2" size="30" inactiveColor="rgb(245, 108, 108)" activeColor="rgb(90, 199, 37) ">
          </u-switch>
        </view>
        <view class="flex flex1">
          <view class="flex2"><u-input disabled v-model="item.xxx3" @click="goQushu(index)" type="input" placeholder="请选择" /></view>
          <view class="flex1">
            <image src="../../../static/image/tab1/caiwu/menuicon.png" style="width: 25rpx;height: 20rpx;" mode="" @click="showDialog1(index)"></image>
          </view>
        </view>
      </view>
    </view>
    <button type="default" class="sure_btn" @click="sureBtn">保存</button>

    <u-popup v-model="isShowDialog1" mode="bottom" border-radius="56">
      <view class="popup-main">
        <view class="popup-common-title">
          — 结转行 —
        </view>

        <picker-view class="picker-view" style="height: 380rpx;" :value="digestValue1" @change="changePicker1">
          <picker-view-column>
            <view class="item" v-for="(item,index) in list1" :key="index">{{item.name}}</view>
          </picker-view-column>
        </picker-view>

        <view class="popup-common-btnGroup">
          <view class="popup-common-btn" @click="isShowDialog1 = false">
            取消
          </view>
          <view class="popup-common-btn active" @click="sureVoucherBtn1()">
            确定
          </view>
        </view>
      </view>
    </u-popup>
  </view>
</template>

<script>
export default {
  data() {
    return {
      remarks: '', //名称
      // subject_headings:'',//
      anchildreb: '请选择', //公式取值
      anchildren: '请选择', //自动平衡
      credentials: [],
      flag: 1,
      background: {
        backgroundColor: "#FFFFFF",
      },
      formData: {
        recording_time: "",
        operation: "add",
        documents_images: "",
        years: "",
        month: "",
        day: "",
        key: "",
        remarks: ""
      },
      goodlist: [{
        kemu_id: '',
        // xxx1: "",
        // xxx2: "",
        // xxx3: '',
      }],
      isShowDialog1: false,
      currentIndex: '',
      digestValue1: [0],
      list1: [{
        name: "插入行",
        id: 1
      }, {
        name: "删除行",
        id: 2
      }],
    };
  },
  onLoad(e) {
    this.flag = e.flag ? e.flag : 1;
  },
  methods: {
    //按公式取值
    formula() {
      this.$navigateTo({
        url: "./set"
      }).then(res => {
        console.log(res, "RRRRRRRRRRRRRRRRR")
        this.anchildreb = '已选择'
        if (res.direction) {
          res.direction = 1
        } else {
          res.direction = -1
        }
        this.credentials[1] = res
        console.log(this.credentials, "AAAAAAAAAAAA")
      })
    },
    //自动平衡
    autbalance() {
      this.$navigateTo({
        url: "./autobalance"
      }).then(res => {
        this.anchildren = '已选择'
        if (res.direction) {
          res.direction = 1
        } else {
          res.direction = -1
        }
        this.credentials[0] = res
        console.log(this.credentials, "AAAAAAAAAAAA")
      })
    },
    showDialog1(index) {
      this.currentIndex = index;
      this.isShowDialog1 = true;
    },
    goQushu(index) {
      this.$navigateTo({
        url: "/pages/my/zcfzlist"
      }).then(res => {
        // TODO:
        // this.goodlist[index].kemu_id = res.id;
        // this.goodlist[index].subject_headings = res.name;
        // this.goodlist[index].balance_status = res.balance_status == -1 ? true : false;
        // let dataArr = res.hesuan_list.map(item => {
        //   return {
        //     name: item.name,
        //     hs_id: item.id,
        //     memberName: ""
        //   }
        // })
        console.log('aaaa', res)
        this.goodlist[index].xxx3 = 'xxx';
        this.$forceUpdate()
      })
    },
    goKeMu(index) {
      this.$navigateTo({
        url: "/pages/my/subject/selectPage"
      }).then(res => {
        // TODO:
        this.goodlist[index].kemu_id = res.id;
        // this.goodlist[index].subject_headings = res.name;
        // this.goodlist[index].balance_status = res.balance_status == -1 ? true : false;
        // let dataArr = res.hesuan_list.map(item => {
        //   return {
        //     name: item.name,
        //     hs_id: item.id,
        //     memberName: ""
        //   }
        // })
        console.log('aaaa', res)
        this.goodlist[index].xxx1 = res.name;
        this.$forceUpdate()
      })
    },
    sureVoucherBtn1(id) {
      this.isShowDialog1 = false;
      let index = id || this.list1[this.digestValue1.join()].id;
      if (index == 2) {
        if(!this.currentIndex) this.currentIndex = 0;
        this.goodlist.splice(this.currentIndex, 1)
      } else if (index == 1) {
        // TODO:
        this.goodlist.push({
          kemu_id: '',
          // xxx1: "",
          // xxx2: "",
          // xxx3: '',
        })
      }
      this.$forceUpdate()
    },
    changePicker1(e) {
      this.digestValue1 = e.detail.value;
    },
    sureBtn() {
      if (this.remarks == '') {
        uni.showToast({
          title: "请输入摘要名称",
          icon: "none"
        })
        return
      }
      // if (this.anchildreb == '请选择') {
      //   uni.showToast({
      //     title: "请添加按公式取值",
      //     icon: "none"
      //   })
      //   return
      // }
      // if (this.anchildren == '请选择') {
      //   uni.showToast({
      //     title: "请添加自动平衡",
      //     icon: "none"
      //   })
      //   return
      // }
      // // console.log(this.credentials,"GGGGGGGGGGGGGGGGGGGGG")
      // let param = {
      //   name: this.remarks,
      //   flag: '',
      //   children: this.credentials
      // }
      // console.log(param)
      // this.$http("enterprise.Date_query/TemplateAdd", param, "post").then(res => {
      //   if (res.data.code == 1) {
      //     uni.showToast({
      //       title: "新增成功",
      //       icon: "none"
      //     })
      //     setTimeout(() => {
      //       uni.navigateBack({
      //         delta: 1
      //       })
      //     }, 500)
      //   }
      // })

    },
  }
}
</script>

<style lang="scss" scoped>
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

.view-list {
  padding: 10rpx 20rpx;
  display: flex;
  align-items: center;
  text {
    background-color: #f5f5f5;
    width: 230rpx;
    line-height: 66rpx;
    text-align: center;
  }
}
.view-lista {
  padding: 10rpx 20rpx;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #f5f5f5;
  text {
    width: 230rpx;
    text-align: center;
    line-height: 66rpx;
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

  // 	.financial-popup-main {
  // 		padding: 0 32rpx;

  // 		.financial-popup-item {
  // 			margin-top: 32rpx;
  // 			display: flex;
  // 			flex-direction: column;
  // 			border-bottom: 1px solid #EEF2FF;
  // 			padding-bottom: 32rpx;

  // 			.financial-popup-item-hd {
  // 				display: flex;
  // 				align-items: center;

  // 				.financial-popup-hd-status {
  // 					background: #FF253D;
  // 					border-radius: 48rpx;
  // 					padding: 4rpx 8rpx;
  // 					color: #fff;
  // 					font-size: 24rpx;
  // 					margin-left: 16rpx;
  // 				}

  // 				.financial-popup-hd-txt {
  // 					font-size: 32rpx;
  // 					color: #150E33;
  // 					font-weight: bold;
  // 				}
  // 			}

  // 			.financial-popup-item-bd {
  // 				color: #7A7C94;
  // 				font-size: 28rpx;
  // 				margin-top: 30rpx;
  // 			}
  // 		}
  // 	}
}
.picker-view {
  .item {
    font-size: 34rpx;
    text-align: center;
  }
}
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
.pr-5 {
  padding-right: 5rpx;
}
.flex-jc {
  justify-content: center;
}
.sure_btn {
  background: #4396f7;
  border-radius: 49px;
  width: 90%;
  color: #fff;
  margin: 32rpx auto;
}
.charu-btn{
  width: 90%;
  height: 80rpx;
  background: #4396f7;
  border-radius: 49px;
  color: #fff;
  text-align: center;
  line-height: 80rpx;
  margin-top: 32rpx;
  margin-bottom: 32rpx;
}
</style>

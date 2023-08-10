<template>
  <view>
    <!-- TODO: -->
    <view>
      <u-navbar :is-back="true" title="取数公式" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
        <!-- <view slot="right" style="margin-right: 25rpx;" @click="addadd()">添加</view> -->
      </u-navbar>
      <button type="default" class="charu-btn" @click="sureVoucherBtn1(1)" v-if="!goodlist.length">插入行</button>
      
      <view class="view-list">
        <view class="flex1">选择科目</view>
        <view class="flex1">选择方向</view>
        <view class="flex1">选择数据来源</view>
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
            <view class="flex2"><u-input disabled v-model="item.xxx3" @click="showDialog2(index)" type="input" placeholder="请选择" /></view>
            <view class="flex1">
              <image src="../../static/image/tab1/caiwu/menuicon.png" style="width: 25rpx;height: 20rpx;" mode="" @click="showDialog1(index)"></image>
            </view>
          </view>
        </view>
        <view class="flex flex-je" style="margin-top:20rpx;margin-bottom:10rpx;" v-if="index !== goodlist.length -1 ">
          <view class="flex1"></view>
          <view class="flex1"></view>
          <view class="flex flex1">
            <u-icon name="plus-circle-fill" :color="item.sign === 'plus' ? '#2979ff': '#909399'" size="50" style="margin-right: 10rpx;" @click="item.sign = 'plus'"></u-icon>
            <u-icon name="minus-circle-fill" :color="item.sign === 'minus' ? '#2979ff': '#909399'" size="50" @click="item.sign = 'minus'"></u-icon>
          </view>
        </view>
      </view>
    </view>

    <button type="default" class="sure_btn" @click="sureBtn">保存</button>

    <u-popup v-model="isShowDialog1" mode="bottom" border-radius="56">
      <view class="popup-main">
        <view class="popup-common-title">
          — 取数公式行 —
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

    <u-popup v-model="isShowDialog2" mode="bottom" border-radius="56">
      <view class="popup-main">
        <view class="popup-common-title">
          — 数据来源 —
        </view>

        <picker-view class="picker-view" style="height: 380rpx;" :value="digestValue2" @change="changePicker2">
          <picker-view-column>
            <view class="item" v-for="(item,index) in list2" :key="index">{{item}}</view>
          </picker-view-column>
        </picker-view>

        <view class="popup-common-btnGroup">
          <view class="popup-common-btn" @click="isShowDialog2 = false">
            取消
          </view>
          <view class="popup-common-btn active" @click="sureVoucherBtn2">
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
} from "vuex";
export default {
  data() {
    return {
      background: {
        backgroundColor: "#FFFFFF",
      },
      id: '',
      goodlist: [{
        kemu_id: '',
        sign: 'plus',
        // xxx1: "",
        // xxx2: "",
        // xxx3: '',
      }],
      list1: [{
        name: "插入行",
        id: 1
      }, {
        name: "删除行",
        id: 2
      }],
      list2: ["年初", "期初", "本期", "期末"],
      // list2: [{
      //   name: "年初",
      //   id: 1
      // }, {
      //   name: "期初",
      //   id: 2
      // }, {
      //   name: "本期",
      //   id: 3
      // }, {
      //   name: "期末",
      //   id: 4
      // }],
      isShowDialog1: false,
      isShowDialog2: false,
      currentIndex: '',
      digestValue1: [0],
      digestValue2: [0],
    };
  },
  onLoad(e) {
    console.log(e.id)
    this.id = e.id
    this.getload()
  },
  methods: {
    getload() {
      this.$http("enterprise.report/ListOfCalculationFormulas",
        { user_calc_cate_id: this.id }, "post").then(res => {
          // TODO:
          if (res.data.code == 1) {
            this.goodlist = res.data.data
            this.goodlist = this.goodlist.map(item => {
              return {
                ...item,
                sign: 'plus'
              }
            })
          }
        })
    },
    addadd() {
      this.$navigateTo({
        url: './zcfzset?id=' + this.id
      }).then(res => {
        this.getload()
      })
    },
    //删除
    deleta(id) {
      console.log(id)
      this.$http("enterprise.report/DeleteTheFormula",
        { user_cale_formula_id: id }, "post").then(res => {
          if (res.data.code == 1) {
            uni.showToast({
              title: "删除成功",
              icon: "none"
            })
            setTimeout(() => {
              this.getload()
            }, 500)
          }
        })
    },
    sureVoucherBtn1(id) {
      this.isShowDialog1 = false;
      let index = id ||  this.list1[this.digestValue1.join()].id;
      console.log('---', index)
      if (index == 2) {
        if(!this.currentIndex) this.currentIndex = 0;
        this.goodlist.splice(this.currentIndex, 1)
      } else if (index == 1) {
        // TODO:
        this.goodlist.push({
          kemu_id: '',
          sign: 'plus',
          // xxx1: "",
          // xxx2: "",
          // xxx3: '',
        })
      }
      this.$forceUpdate()
    },
    sureVoucherBtn2() {
      this.isShowDialog2 = false;
      console.log('---', this.digestValue2)
      let value = this.list2[this.digestValue2.join()];
      this.goodlist[this.currentIndex].xxx3 = value
      this.$forceUpdate()
    },
    showDialog1(index) {
      this.currentIndex = index;
      this.isShowDialog1 = true;
    },
    showDialog2(index) {
      this.currentIndex = index;
      this.isShowDialog2 = true;
    },
    changePicker1(e) {
      this.digestValue1 = e.detail.value;
    },
    changePicker2(e) {
      this.digestValue2 = e.detail.value;
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
    sureBtn() {
      this.$navigateBack({ item: this.goodlist })
      // for (let item of this.credentials) {
      // 	if (!item.abstract) {
      // 		uni.showToast({
      // 			title: "请输入摘要!",
      // 			icon: "none"
      // 		})
      // 		return
      // 	} else if (!item.kemu_id) {
      // 		uni.showToast({
      // 			title: "请选择科目!",
      // 			icon: "none"
      // 		})
      // 		return
      // 	} else if (!item.money) {
      // 		uni.showToast({
      // 			title: "请输入金额!",
      // 			icon: "none"
      // 		})
      // 		return
      // 	}
      // }
      // this.formData.documents_images = this.formData.documents_images ? this.formData.documents_images.join(
      // 	",") : "";


      // let adday = this.credentials
      // let jieNum = 0
      // let daiNum = 0

      // adday.forEach(item => {
      // 	item.balance_status = item.balance_status ? -1 : 1;
      // })
      // this.formData.credentials = JSON.stringify(adday);
      // uni.showLoading({
      // 	title: '正在提交中...',
      // })
      // this.$http("enterprise.Credentials/CreateCrdentials", this.formData, "post").then(res => {
      // 	if (res.data.code == 1) {
      // 		uni.hideLoading()
      // 		uni.showToast({
      // 			title: "新增成功",
      // 			icon: "none"
      // 		})
      // 		setTimeout(() => {
      // 			uni.navigateBack({
      // 				delta: 1
      // 			})
      // 		}, 500)
      // 	} else {
      // 		this.formData.documents_images = this.formData.documents_images ? this.formData
      // 			.documents_images.split(',') : [];
      // 		this.credentials.forEach(item => {
      // 			item.balance_status = item.balance_status == -1 ? true : false;
      // 		})
      // 	}
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

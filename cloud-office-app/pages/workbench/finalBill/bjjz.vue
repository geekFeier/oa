<template>
  <view>
    <u-navbar :is-back="true" title="编辑结转" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
    </u-navbar>
    <button type="default" class="charu-btn" @click="sureVoucherBtn1(1)" v-if="!children.length">插入行</button>
    <view>
      <u-form ref="uForm">
        <view style=" padding:0 20rpx 10rpx;">
          <u-form-item label="模板名称" label-width="150" :border-bottom="false">
            <u-input v-model="name" type="input" placeholder="请输入模板名称" />
          </u-form-item>
        </view>
      </u-form>
    </view>
    <view class="view-list">
      <view class="flex1">摘要</view>
      <view class="flex1">选择科目</view>
      <view class="flex1">选择方向</view>
      <view class="flex1">金额</view>
    </view>
    <view v-for="(item,index) in children" :key="index">
      <view class="view-lista">
        <view class="flex1"><u-input v-model="item.abstract" type="input" placeholder="请输入摘要" /></view>
        <view class="flex1"><u-input disabled v-model="item.kemu_name" @click="goKeMu(index)" type="input" placeholder="请选择科目" /></view>
        <view class="flex flex-ac flex1">
          <view class="pr-5">{{ item.direction ? '借' : '贷' }}</view>
          <u-switch space="2" v-model="item.direction" size="30" inactiveColor="rgb(245, 108, 108)" activeColor="rgb(90, 199, 37) ">
          </u-switch>
        </view>
        <view class="flex flex1">
          <view class="flex2"><u-input disabled v-model="item.valuetype" @click="goQushu(index,item)" type="input" placeholder="请选择" /></view>
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
      id: '',
      name: '', //名称 
      background: {
        backgroundColor: "#FFFFFF",
      },
      children: [{
        kemu_id: '',
        kemu_name: '',
        abstract: '',
        direction: true,
        valuetype: '',
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
    this.id = e.id
    this.getData()
  },
  methods: {
    getData() {
      let param = {
        prof_id: this.id
      }
      this.$http("enterprise.Date_query/TemplateInfo", param, "post").then(res => {
        if (res.data.code == 1) {
          this.name = res.data.data.name
          this.children = res.data.data.rule.map(item => {
            return {
              ...item,
              direction: item.direction == -1 ? true : false,
              valuetype: item.valuetype == -1 ? '按公式' : '借贷差',
              kemu_name: item.Course_content,
              formula:( item.formula && item.formula.map(item2 => {
                return {
                  ...item2,
                  kemu_name: item2.Course_content,
                }
              }) ) || []
            }
          })

        }
      })
    },
    showDialog1(index) {
      this.currentIndex = index;
      this.isShowDialog1 = true;
    },
    goQushu(i,item) {
      this.$navigateTo({
        url: `./zcfzset?from=edit&data=${JSON.stringify(item.formula || [])}`
      }).then(res => {
        this.children[i].formula = res;
        this.children[i].valuetype = res.length ? '按公式' : '借贷差';
        this.$forceUpdate()
      })
    },
    goKeMu(index) {
      this.$navigateTo({
        url: "/pages/my/subject/selectPage"
      }).then(res => {
        this.children[index].kemu_id = res.id;
        this.children[index].kemu_name = res.name;
        this.$forceUpdate()
      })
    },
    sureVoucherBtn1(id) {
      this.isShowDialog1 = false;
      let index = id || this.list1[this.digestValue1.join()].id;
      if (index == 2) {
        if (!this.currentIndex) this.currentIndex = 0;
        this.children.splice(this.currentIndex, 1)
      } else if (index == 1) {
        this.children.push({
          kemu_id: '',
          kemu_name: '',
          abstract: '',
          direction: true,
          valuetype: '',
        })
      }
      this.$forceUpdate()
    },
    changePicker1(e) {
      this.digestValue1 = e.detail.value;
    },
    sureBtn() {
      if (!this.name) {
        uni.showToast({
          title: "请输入模板名称",
          icon: "none"
        })
        return
      }
      let children = this.children.filter(item => {
        return item.kemu_id && item.abstract && item.valuetype
      })
      if (!children.length) {
        uni.showToast({
          title: "请完善数据",
          icon: "none"
        })
        return
      }
      let params = {
        pro_id: this.id,
        name: this.name,
        flag: 1,
        children: children.map(item => {
          return {
            kemu_id: item.kemu_id,
            abstract: item.abstract,
            formula: item.formula,
            direction: item.direction ? -1 : 1,
            valuetype: item.valuetype == '按公式' ? -1 : 1,
          }
        })
      }
//  TODO:
      this.$http("/enterprise.Date_query/TemplateSaveOne", params, "post").then(res => {
        if (res.data.code == 1) {
          uni.showToast({
            title: "编辑成功",
            icon: "none"
          })
          this.$navigateTo({
            url: '/pages/workbench/finalBill/jzlist'
          })
        }
      })
    }
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
.charu-btn {
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

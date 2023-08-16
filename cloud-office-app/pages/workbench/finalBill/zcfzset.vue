<template>
  <view>
    <u-navbar :is-back="true" title="公式添加" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
    </u-navbar>
    <view class="view-list" @click="selectname()">
      <view>科目名称:</view>
      <view class="kemu">{{account}}</view>
      <u-icon class="item-hd-r" name="arrow-right" color="#7A7C94" size="28"></u-icon>
    </view>
    <view class="view-list">
      <view>方向:</view>
      <view class="kemu"></view>
      <view class="flex flex-ac">
        <view style="padding-right:5rpx">{{ direction ? '借' : '贷' }}</view>
        <u-switch space="2" v-model="direction" size="30" inactiveColor="rgb(245, 108, 108)" activeColor="rgb(90, 199, 37) ">
        </u-switch>
      </view>
    </view> 
    <view class="view-list" @click="sourceF()">
      <view>数据来源:</view>
      <view class="kemu">{{source}}</view>
      <u-icon class="item-hd-r" name="arrow-right" color="#7A7C94" size="28"></u-icon>
    </view>
    <button type="default" class="sure_btn" @click="sureBtn">添加</button>
    <u-select v-model="show" mode="single-column" :list="list" @confirm="confirm"></u-select>
    <u-select v-model="show2" mode="single-column" :list="list2" @confirm="confirm2"></u-select>
  </view>
</template>

<script>
import {
  mapState
} from "vuex"; 
export default {
  data() {
    return {
      id: '',
      background: {
        backgroundColor: "#FFFFFF",
      },
      account: '请选择科目', 
      source: '请选择数据来源', 
      kemu_id: '',
      direction:true,
      show: false,
      show2: false, 
      list: [
        {
          value: '加',
          label: '+'
        },
        {
          value: '减',
          label: '-'
        }
      ],
      list2: [ 
        {
          value: 1,
          label: '期初'
        },
        {
          value: 2,
          label: '本期'
        },
        {
          value: 3,
          label: '期末'
        },
      ]
    };
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo
    })
  },
  onLoad(e) { 
    this.id = e.id
  },
  methods: {
    selectname() {
      this.$navigateTo({
        url: "./kemulist"
      }).then(res => { 
        this.kemu_id = res.id;
        this.account = res.name;
      })
    },
    add() {
      this.show = true
    },
    sourceF() {
      this.show2 = true
    }, 
    confirm2(e) {
      this.source = e[0].label
    },
    //添加
    sureBtn() {
      if (this.account == '请选择科目') {
        uni.showToast({
          title: "请选择科目",
          icon: "none"
        })
        return
      } 
      if (this.source == '请选择数据来源') {
        uni.showToast({
          title: "请选择数据来源",
          icon: "none"
        })
        return
      }
     
      this.$http("enterprise.report/ARormulaToCalculate", {
        kemu_id: this.kemu_id,
        user_calc_cate_id: this.id,
        source:this.list2.find(item=>item.label === this.source).value ,
        direction:this.direction ? 1 : 2
      }, "post").then(res => {
        if (res.data.code == 1) {
          uni.showToast({
            title: "添加成功",
            icon: "none"
          })
          setTimeout(() => {
            this.$navigateBack()
          }, 1000)
        }
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.flex{
  display: flex;
}
.flex-ac{
  display: flex;
  align-items: center;
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

.view-list {
  padding: 10rpx 20rpx;
  display: flex;
  align-items: center;
  .kemu {
    height: 66rpx;
    line-height: 66rpx;
    margin-right: 30rpx;
    text-align: right;
    flex: 1;
  }
}
</style>

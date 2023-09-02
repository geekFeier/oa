<template>
  <view>
    <u-navbar :is-back="true" title="公式添加" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
    </u-navbar>
    <view class="view-list" @click="selectname()">
      <view>科目名称:</view>
      <view class="kemu">{{formula.kemu_name}}</view>
      <u-icon class="item-hd-r" name="arrow-right" color="#7A7C94" size="28"></u-icon>
    </view>
    <view class="view-list">
      <view>方向:</view>
      <view class="kemu"></view>
      <view class="flex flex-ac">
        <view style="padding-right:5rpx">{{ formula.direction ? '借' : '贷' }}</view>
        <u-switch space="2" v-model="formula.direction" size="30" inactiveColor="rgb(245, 108, 108)" activeColor="rgb(90, 199, 37) " :disabled="formula.valuetype">
        </u-switch>
      </view>
    </view>
    <view class="view-list" @click="ruleF()">
      <view>数据来源:</view>
      <view class="kemu">{{formula.rule}}</view>
      <u-icon class="item-hd-r" name="arrow-right" color="#7A7C94" size="28"></u-icon>
    </view>

    <view class="border-top"></view>
    <view class="view-list">
      <view>借贷差:</view>
      <view class="kemu"></view>
      <view class="flex flex-ac">
        <view style="padding-right:5rpx">{{ formula.valuetype ? '是' : '否' }}</view>
        <u-switch space="2" v-model="formula.valuetype" size="30" inactiveColor="rgb(245, 108, 108)" activeColor="rgb(90, 199, 37) ">
        </u-switch>
      </view>
    </view>
    <button type="default" class="sure_btn" @click="sureBtn">添加</button>
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
      formula: {
        kemu_name: '请选择科目',
        kemu_id: '',
        rule: '请选择数据来源',
        direction: true,
        valuetype: false,
      },
      background: {
        backgroundColor: "#FFFFFF",
      },
      show: false,
      show2: false,
      list2: [{
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
  methods: {
    selectname() {
      if (this.formula.valuetype) return false
      this.$navigateTo({
        url: "/pages/my/kemulist"
      }).then(res => {
        this.formula.kemu_id = res.id;
        this.formula.kemu_name = res.name;
      })
    },
    add() {
      this.show = true
    },
    ruleF() {
      if (this.formula.valuetype) return false
      this.show2 = true
    },
    confirm2(e) {
      this.formula.rule = e[0].label
    },
    //添加
    sureBtn() {
      if (this.formula.valuetype) {
        this.$navigateBack([])
        return false
      }
      if (this.formula.kemu_name == '请选择科目') {
        uni.showToast({
          title: "请选择科目",
          icon: "none"
        })
        return
      }
      if (this.formula.rule == '请选择数据来源') {
        uni.showToast({
          title: "请选择数据来源",
          icon: "none"
        })
        return
      }
      let formula = [{
        kemu_id: this.formula.kemu_id,
        rule: this.formula.rule,
        // direction: this.formula.direction ? 1 : 2 // 暂时不传 TODO:
      }]

      this.$navigateBack(formula)
    },
  }
}
</script>

<style lang="scss" scoped>
.flex {
  display: flex;
}

.flex-ac {
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

.border-top {
  width: 92%;
  height: 1px;
  background: lightgray;
  margin-left: 4%;
  margin-right: 4%;
  margin-top: 40rpx;
  margin-bottom: 40rpx;
}
</style>
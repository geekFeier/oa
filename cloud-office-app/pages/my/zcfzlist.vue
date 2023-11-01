<template>
  <view>
    <u-navbar :is-back="true" title="取数公式" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
      <view slot="right" style="margin-right: 25rpx;" @click="addadd()">添加</view>
    </u-navbar>
    <view class="view-list">
      <view class="flex1">科目</view>
      <view class="flex1">方向</view>
      <view class="flex1">符号</view>
      <view class="flex1">数据来源</view>
      <view>操作</view>
    </view>
    <view v-for="(item,index) in goodlist" :key="index"  class="view-listaa">
      <view  class="view-lista">
        <view>{{item.kemu_info.name || '-'}}</view>
        <view>{{item.direction | filterDirection}}</view>
        <view>{{item.operator || '-'}}</view>
        <view>{{item.source | filterSource}}</view>
        <view style="color: red;" @click="deleta(item.id)">删除</view>
      </view>
     <view  class="view-lista">
       <view>{{item.kemu_info.name || '-'}}</view>
        <view>{{item.direction | filterDirection}}</view>
        <view>{{item.operator || '-'}}</view>
        <view>{{item.source | filterSource}}</view>
        <view style="color: red;" @click="deleta(item.id)">删除</view>
     </view>
    </view>
  </view>
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
      goodlist: []
    };
  },
  onLoad(e) {
    this.id = e.id
    this.getload()
  },
  filters: {
			filterDirection(val) {
				if (val == 1) {
					return "借"
				} else if (val == 2) {
					return "贷"
				} else  {
					return "-"
				}
			},
			filterSource(val) {
				if (val == 1) {
					return "期初"
				} else if (val == 2) {
					return "本期"
				} else if (val == 3) {
					return "期末"
				} else  {
					return "-"
				}
			}
		},  
  methods: {
    getload() {
      this.$http("enterprise.report/ListOfCalculationFormulas",
        { user_calc_cate_id: this.id }, "post").then(res => {
          console.log(res)
          if (res.data.code == 1) {
            this.goodlist = res.data.data
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
  view {
    background-color: #f5f5f5;
    flex:1;
    line-height: 66rpx;
    text-align: center;
  }
}
.view-lista {
  padding: 10rpx 20rpx;
  display: flex;
  align-items: center;
 
  view {
    flex:1;
    text-align: center;
    line-height: 66rpx;
  }
}
.view-listaa{
  margin:20rpx 20rpx;
   border: 1px solid #f5f5f5;
}
</style>

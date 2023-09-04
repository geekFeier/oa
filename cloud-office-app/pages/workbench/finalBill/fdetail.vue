<template>
  <view>
    <u-navbar :is-back="true" title="模板详情" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
    </u-navbar>
    <!-- valuetype  ： 1=自动平衡，-1=按公式取值 -->
    <view class="mainBox">
      <view class="one">
        <view>结转名称</view>
        <view>{{allData.name}}</view>
      </view>
      <view class="two" v-for="(item,index) in allData.rule.filter(item2=>item2.valuetype == -1)" :key="item.id">
        <view style="font-size: 32rpx;color: red;">按公式取值内容</view>
        <view>
          <view class="two-once">
            <view>摘要</view>
            <view>{{item.abstract}}</view>
          </view>
          <view class="two-once">
            <view>科目名称</view>
            <view>{{item.Course_content}}</view>
          </view>
          <view class="two-once">
            <view>借贷方向</view>
            <view>
              <view style="padding-right:5rpx">{{ item.direction == -1 ? '借' : '贷' }}</view>
            </view>
          </view>
          <view class="two-three">
            <view>公式列表</view>
            <view>
              <view class="view-list">
                <text>科目</text>
                <text>运算符号</text>
                <text>数据来源</text>
                <text>方向</text>
              </view>
              <view v-for="(item2,index) in item.formula" :key="index" class="view-lista">
                <text>{{item2.Course_content}}</text>
                <text>{{item2.operation}}</text>
                <text>{{item2.rule === 'period_end' ? '期末' : '-'}}</text>
                <text>
                  {{ item2.direction == -1 ? '借' : '贷' }}
                </text>
                <!-- <text style="color: red;" @click="deleta(item.id)">删除</text> -->
              </view>
            </view>
          </view>
        </view>
      </view>
      <view class="three" v-for="(item,index) in allData.rule.filter(item2=>item2.valuetype == 1)" :key="item.id">
        <view style="font-size: 32rpx;color: red;">自动平衡</view>
        <view>
          <view class="two-once">
            <view>摘要</view>
            <view>{{item.abstract}}</view>
          </view>
          <view class="two-once">
            <view>科目名称</view>
            <view>{{item.Course_content}}</view>
          </view>
          <view class="two-once">
            <view>借贷方向</view>
            <view>
              <view style="padding-right:5rpx">{{ item.direction == -1 ? '借' : '贷' }}</view>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      id: '',
      allData: { rule: [] },
      background: {
        backgroundColor: "#FFFFFF",
      },
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
          this.allData = res.data.data || {}
        }
      })
    }, 

  }
}
</script>

<style lang="scss" scoped>
.mainBox {
  padding: 0 30rpx;
  .one {
    height: 88rpx;
    font-size: 34rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .two {
    padding-top: 15rpx;
    line-height: 66rpx;
    font-size: 34rpx;
    .two-once {
      height: 88rpx;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }
  .three {
    padding-top: 15rpx;
    line-height: 66rpx;
    font-size: 34rpx;
    .two-once {
      height: 88rpx;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }
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
</style>

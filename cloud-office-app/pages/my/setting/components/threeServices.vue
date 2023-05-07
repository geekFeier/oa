<template>
	<view class="statementBox">
		<view class="statementMain">
			<view class="common-title">
				提示设置
			</view>
			<u-grid :col="4" :border="false" style="margin-top: 12rpx;">
				<u-grid-item @click="jumpTo('pushSetting')">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;" src="../../../../static/image/tab2/ts.png"
						mode="">
					</image>
					<view class="grid-text">推送设置</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('gonggao')">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;" src="../../../../static/image/my/setting/gg.png"
						mode="">
					</image>
					<view class="grid-text">单位公告</view>
				</u-grid-item>		
				<u-grid-item @click="goPieChat">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;" src="../../../../static/image/my/setting/bzt.png"
						mode="">
					</image>
					<view class="grid-text">饼状图设置</view>
				</u-grid-item>
			</u-grid>
	
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {}
		},
		methods:{
			
			jumpTo(val){
				uni.navigateTo({
					url:`/pages/my/${val}/index`
				})
			},
			goPieChat(){
				uni.navigateTo({
					url:"/pages/my/pieChatSet/index"
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.common-title {
		font-size: 34rpx;
		color: #150E33;
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
				color: #150E33;
				font-size: 26rpx;
				margin-top: 22rpx;
				width: 130rpx;
				text-align: center;
			}
		}
	}
	
</style>

<template>
	<view class="statementBox">
		<view class="statementMain">
			<view class="common-title">
				三方服务
			</view>
			<u-grid :col="4" :border="false" style="margin-top: 12rpx;">
				<u-grid-item>
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;" src="../../../../static/image/tab1/caiwu/yhzz.png"
						mode="">
					</image>
					<view class="grid-text">银行转账</view>
				</u-grid-item>
				<u-grid-item>
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;" src="../../../../static/image/tab1/caiwu/sbns.png"
						mode="">
					</image>
					<view class="grid-text">申报纳税</view>
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
		margin-top: 20rpx;
	
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

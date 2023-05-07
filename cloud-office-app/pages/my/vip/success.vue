<template>
	<view class="auditBox">
		<u-navbar :is-back="true" back-icon-color="#000" :background="background" title="支付成功" title-color="#000"
			:height="55">
		</u-navbar>

		<view class="mainBox">
			<image style="height: 180rpx;width: 180rpx;margin-top: 45px;" src="../../../static/image/tab1/success.png"
				mode=""></image>
			<text style="font-size: 18px; color:#4396F7 ;margin-top: 14px;">付款成功</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
			}
		},
		methods: {
			goback() {
				uni.navigateBack({
					delta: 1
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.mainBox {
		display: flex;
		flex-direction: column;
		align-items: center;

		.addBtn {
			text-align: center;
			position: fixed;
			bottom: 30px;
			width: 88%;
			left: 50%;
			transform: translateX(-50%);
			height: 50px;
			line-height: 50px;
			color: #fff;
			background: #037FC5;
			border-radius: 49px;
		}
	}
</style>

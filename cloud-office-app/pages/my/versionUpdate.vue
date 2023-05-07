<template>
	<view class="versionBox">
		<u-navbar :is-back="true" title="版本更新" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="hd">
			<image src="../../static/image/launch_icon.png" style="width: 180rpx;height: 180rpx;" mode=""></image>
			<text style="margin-top: 32rpx;color: #150E33;font-size: 24rpx;">当前版本1.0.0</text>
		</view>

		<view class="main">
			<view class="" style="margin-top: 56rpx;">
				检测到有新的版本可更新:<text>V2.0.0</text>
			</view>
			<view style="margin-top: 32rpx;">
				<view class="">
					1.优化了一些问题； 
				</view>
			</view>

		</view>

		<view class="bd" @click="updateBtn">
			更新云上办公APP
		</view>

	</view>
</template>

<script>
	export default {
		onLoad() {
			// plus.runtime.getProperty(plus.runtime.appid, function(wgtinfo) {
			// 	this.version = wgtinfo.version
			// })
		},
		data() {
			return {
				detailData: {},
				image: "",
				version: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		methods: {

			updateBtn() {
				// location.href = this.detailData.app_link
				uni.downloadFile({
					url: this.detailData.app_link
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.versionBox {

		// padding: 32rpx;
		.hd {
			display: flex;
			flex-direction: column;
			align-items: center;
			margin-top: 48rpx;
		}

		.main {
			padding: 32rpx;
		}

		.bd {
			width: 100%;
			height: 98rpx;
			line-height: 98rpx;
			text-align: center;
			position: fixed;
			bottom: 0;
			background: #4396F7;
			font-size: 32rpx;
			color: #fff;
		}
	}
</style>

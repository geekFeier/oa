<template>
	<view>
		<u-navbar :is-back="true" title="账套设置" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="mainBox">
			<view class="title">
				{{detailData.name}}
			</view>
			<image src="../../../static/image/tab1/success.png" mode=""
				style="width: 160rpx;height: 160rpx;margin: 32rpx 0;"></image>
			<view class="status">
				{{flag==1?"创建成功":"编辑成功"}}
			</view>
			<view class="btnBox" @click="goBack">
				进入账套
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				flag: 1,
				background: {
					backgroundColor: "#FFFFFF",
				},
				detailData: {},
			};
		},
		onLoad(e) {
			console.log(e.data,"Aaaaaaaaaaaaaaaaaaaaa");
			this.flag = e.flag;
			// this.detailData = JSON.parse(e.data);
		},
		methods: {
			goBack() {
				uni.navigateBack({
					delta: 2
				})
				uni.$emit("changeAccount", true)
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
	}

	.mainBox {
		text-align: center;
		font-size: 32rpx;
		margin-top: 80rpx;

		.status {
			color: #B5BFDA;
			font-size: 28rpx;
		}

		.btnBox {
			width: 240rpx;
			margin: 80rpx auto;
			height: 80rpx;
			line-height: 80rpx;
			text-align: center;
			color: #fff;
			background: #4396F7;
			border-radius: 49rpx;
		}
	}
</style>

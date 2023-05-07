<template>
	<view>
		<u-navbar :is-back="true" title="发布广告" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="common-title">
				上传广告图片
			</view>
			<view class="identityBox">
				<view class="identit-z" v-if="!license_images" @click="uploadBtn(1)">
					<view class="identit-z-c">
						<u-icon name="plus" color="#B5BFDA" size="48"></u-icon>
						<text style="color: #B5BFDA;margin-top: 8rpx;">上传图片</text>
					</view>
				</view>

				<image class="imageClass" v-if="license_images" :src="license_images" @click="uploadBtn(1)">
				</image>
			

			</view>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">确定</button>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
				license_images: ""
			};
		},
		methods: {
			uploadBtn() {

			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
	}

	.mainBox {
		padding: 0 32rpx;
	}
	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		margin-top: 120rpx;
		margin-bottom: 50rpx;
	}
	.common-title {
		font-size: 32rpx;
		font-weight: bold;
		margin-top: 32rpx;
	}

	.identityBox {
		text-align: center;
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 12rpx;
		.identit-z {
			display: flex;
			flex-direction: column;
			justify-content: center;
			margin-top: 35rpx;
			background: rgba(181, 191, 218, .08);
			border: 1px solid rgba(181, 191, 218, 1);
			border-radius: 16rpx;
			width: 600rpx;
			height: 340rpx;

			.identit-z-c {
				display: flex;
				flex-direction: column;
				align-items: center;
			}

		}

		.identit-desc {
			font-size: 22rpx;
			margin-top: 24rpx;
			color: #7A7C94;
		}
	}
</style>

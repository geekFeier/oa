<template>
	<view>
		<u-navbar :is-back="true" title="文件上传" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<u-form ref="uForm">
				<u-form-item label="文件名称" label-width="150">
					<u-input v-model="name" type="input" placeholder="请输入文件名称" />
				</u-form-item>
				<u-form-item label="选择接收人" label-width="170">
					<u-input v-model="name" type="input"  @click="goConcat" disabled placeholder=""/>
					<u-icon name="arrow-right" color="#7A7C94" slot="right"></u-icon>
				</u-form-item>

				<view class="file-item-upload">
					<view class="common-title">
						文件上传
					</view>
					<view class="file-box">
						<view class="file-item">
							<image class="file-item-icon" src="../../../static/image/my/cloud.png" mode=""></image>
						</view>
					</view>
					<view class="file-item-desc">
						文本、表格、图片、PPT、PDF等文件；
					</view>
				</view>
			</u-form>
			<button type="default" class="sure_btn" @click="sureBtn">上传</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				name: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		methods: {
			sureBtn() {

			},
			goConcat() {
				this.$navigateTo({
					url: "/components/contact"
				}).then(res => {
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.file-item-upload {
		padding: 14rpx 32rpx;
		font-size: 30rpx;
	}

	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		margin-top: 120rpx;
		margin-bottom: 50rpx;
	}

	.file-box {
		display: flex;
		margin-top: 32rpx;

		.file-item {
			width: 212rpx;
			height: 212rpx;
			background: #FBFCFF;
			border: 1px solid #B5BFDA;
			opacity: 1;
			border-radius: 16rpx;
			display: flex;
			justify-content: center;
			align-items: center;

			.file-item-icon {
				width: 76rpx;
				height: 66rpx;
			}

		}

	}

	.file-item-desc {
		color: #B5BFDA;
		font-size: 24rpx;
		margin-top: 32rpx;
	}

	/deep/ .u-input__input {
		display: flex !important;
		justify-content: flex-end !important;
	}

	/deep/ .uni-input-wrapper {
		width: 80%;
		// background-color: pink;
	}

	/deep/ .u-form-item__body {
		display: flex;
		justify-content: flex-end;
	}

	/deep/ .uni-input-placeholder {
		color: rgb(192, 196, 204);
		font-size: 30rpx;
		text-align: right;
	}

	/deep/ .uni-input-input {
		font-size: 30rpx;
		text-align: right;
	}

	/deep/ .u-form-item {
		padding: 14rpx 32rpx;
		font-size: 30rpx;
	}
</style>

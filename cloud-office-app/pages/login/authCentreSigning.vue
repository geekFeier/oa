<template>
	<view class="authCentre">
		<u-form>
			<u-form-item :border-bottom="false" :clearable="false" label="企业全称"
				:label-style="{fontSize:'28rpx','color':'#150E33'}" label-width="190">
				<u-input v-model="formData.enterprice_name" placeholder="请输入企业全称" />
			</u-form-item>

			<view class="form-title">
				企业LOGO
			</view>
			<htz-image-upload style="margin-top: 15px;" mediaType="image" :max="1" name="file" :chooseNum="1"
				v-model="imageData" @chooseSuccess="ceshiChooseSuccess">
			</htz-image-upload>


			<view class="form-title">
				营业执照
			</view>
			<view class="identityBox">
				<view class="identit-z" v-if="!formData.license_images" @click="uploadBtn(1)">
					<view class="identit-z-c">
						<image src="../../static/image/zxj.png" style="width: 62rpx;height: 53rpx;" mode=""></image>
						<text style="color: #B5BFDA;margin-top: 8rpx;">上传照片</text>
					</view>
				</view>

				<image class="imageClass" v-if="formData.license_images" :src="formData.license_images"
					@click="uploadBtn(1)">
				</image>
				<view class="identit-desc">
					拍摄/上传营业执照
				</view>

			</view>

			<u-form-item :border-bottom="false" :clearable="false" label="单位名称"
				:label-style="{fontSize:'28rpx','color':'#150E33'}" label-width="190" style="margin-top: 40rpx;">
				<u-input v-model="formData.work_units" placeholder="请输入单位名称" />
			</u-form-item>
			<u-form-item :border-bottom="false" :clearable="false" label="社会信用码"
				:label-style="{fontSize:'28rpx','color':'#150E33'}" label-width="190">
				<u-input v-model="formData.creditcode" placeholder="请输入社会信用码" />
			</u-form-item>





		</u-form>
		<button type="default" class="sure_btn" @click="loginBtn">确定</button>


	</view>
</template>

<script>
	import {
		url_config,
		img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {

				formData: {
					enterprice_name: "",
					enterprice_image: "",
					license_images: "",
					work_units: "",
					creditcode: ""
				},
				formRule: {
					enterprice_name: "请输入企业全程",
					enterprice_image: "请上传企业LOGO",
					license_images: "请上传营业执照",
					work_units: "请输入单位名称",
					creditcode: "请输入社会信用码"
				},
				imageData: [],
				value: ""
			};
		},
		onLoad(e) {},
		methods: {
			loginBtn() {
				for (let key in this.formData) {
					if (!this.formData[key]) {
						uni.showToast({
							title: this.formRule[key],
							icon: "none"
						})
						return
					}
				}
				this.$http("enterprise.Enterprise/submitEnterpriseInfo", this.formData, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "提交成功"
						})
						setTimeout(() => {
							uni.reLaunch({
								url: "/pages/login/index"
							})
						}, 400)
					}
				})
			},
			ceshiChooseSuccess(val) {
				this.uploadImgs(val, 0);
			},
			uploadImgs(files, val) {
				let temPath = files[0];
				uni.showLoading({
					title: "上传中"
				})
				uni.uploadFile({
					url: url_config + "/Common/upload",
					filePath: temPath,
					name: "file",
					header: {
						token: uni.getStorageSync("token") || ""
					},
					success: (res) => {
						let _res = JSON.parse(res.data);
						if (_res.code == 1) {
							uni.hideLoading()
							if (val == 0) {

								this.imageData = [`${img_url}${_res.data.url}`];
								this.formData.enterprice_image = `${img_url}${_res.data.url}`;
							} else if (val == 1) {
								this.formData.license_images = `${img_url}${_res.data.url}`;
							}
						} else {
							uni.showToast({
								title: "上传失败",
								icon: "none"
							})
						}
					},
					fail: (err) => {
						uni.showToast({
							title: "上传失败",
							icon: "none"
						})
					}
				})

			},
			uploadBtn(val) {
				uni.chooseImage({
					count: 1,
					success: (res) => {
						let _res = res.tempFilePaths;
						this.uploadImgs(_res, val)
					}
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.authCentre {
		.imageClass {
			border: 1px solid rgba(181, 191, 218, 1);
			border-radius: 16rpx;
			width: 600rpx;
			height: 340rpx;
			margin-top: 35rpx;
		}

		padding: 30rpx 32rpx;

		/deep/ .u-input__textarea {
			// background: pink;

			background: #FBFCFF;
			border-radius: 8px;
			padding: 8px;
		}

		.sure_btn {
			margin-top: 80rpx;
			background: #4396F7;
			border-radius: 49px;
			width: 90%;
			color: #fff;
		}

		/deep/ .uni-input-wrapper {
			text-align: right;
		}

		/deep/ .u-form-item {
			padding: 6rpx 0;
		}

		.form-title {
			margin-top: 20rpx;
			color: #150E33;
			font-size: 28rpx;
		}

		.form-title-sub {
			color: #7A7C94;
			margin-top: 12rpx;
			font-size: 24rpx;
		}

		.identityBox {
			text-align: center;
			display: flex;
			flex-direction: column;
			align-items: center;

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

		.business {
			display: flex;
			flex-direction: column;
			align-items: center;

			.identit-z {
				display: flex;
				flex-direction: column;
				justify-content: center;
				margin-top: 35rpx;
				background: rgba(181, 191, 218, .08);
				border: 1px solid rgba(181, 191, 218, 1);
				border-radius: 16rpx;
				width: 570rpx;
				height: 320rpx;

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

	}
</style>

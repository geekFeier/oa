<template>
	<view>
		<u-navbar :is-back="true" title="文件上传" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<u-form ref="uForm">
				<!-- 	<u-form-item label="文件名称" label-width="150">
					<u-input v-model="name" type="input" placeholder="请输入文件名称" />
				</u-form-item> -->
				<view class="file-item-upload">
					<view class="common-title">
						文件上传
					</view>
					<view class="file-box">
						<view class="file-item" v-if="!filePath" @click="uploadFile">
							<image class="file-item-icon" src="../../../static/image/my/cloud.png" mode=""></image>
						</view>
						<view class="file-item" v-if="filePath"
							:style="{'background': filePath? 'url(../../../static/image/othe.png) no-repeat':'','background-size':'100% 100%'}"
							@click="uploadFile">
							<!-- <image   class="file-item-icon"  src="../../../static/image/othe.png" mode=""></image> -->
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
	import {
		mapState
	} from "vuex";
	import {
		url_config,
		img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {
				fileUrl: "",
				filePath: "",
				name: "", //文件名称
				background: {
					backgroundColor: "#FFFFFF",
				},
				cateId: ""
			};
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo,
			}),
		},
		onLoad(e) {
			this.cateId = e.id;
		},
		methods: {
			uploadFile() {
				const plugin = uni.requireNativePlugin('GuoWei-SelectFileModule')
				console.log(123,"")
				console.log(plugin,"11111111111111111")
				plugin.chooseFile(
				    {
				        count: 1,
				        extension: ["docx", "xlsx", "pptx"],
				        rootDirName: '根目录',
				        themeColor: '#00ff00',
				        folderIconColor: '#ff0000',
				        fileIconColor: '#0000ff'
				    }, 
				    result => {
						let pathTemp = result.files[0].url;
						this.filePath = pathTemp;
						//this.uploadFileFun(pathTemp)
				    }
				)
				// uni.chooseFile({
				// 	count: 1,
				// 	type: "all",
				// 	success: res => {
				// 		let pathTemp = res.tempFilePaths[0];
				// 		this.filePath = pathTemp;
				// 		// this.uploadFileFun(pathTemp)
				// 	}
				// })
			},
			uploadFileFun() {
				uni.showLoading({
					title:"上传中!"
				})
				uni.uploadFile({
					url: url_config + "enterprise.cloud_pan/file_upload",
					name: "file",
					filePath: this.filePath,
					formData: {
						dir_id: this.cateId,
						pan_id: this.userInfo.config.pan_info.id,
					},
					header: {
						token: uni.getStorageSync("token") || ""
					},
					success: (res) => {
						let _res = JSON.parse(res.data);
						console.log(_res, "==================================");
						if (_res.code == 1) {
							// this.fileUrl = `${img_url}${_res.data.url}`;
							uni.hideLoading()
							uni.showToast({
								title: "上传成功",
								icon: "none"
							})
							setTimeout(() => {
							uni.$emit("changeFileList")
								uni.navigateBack({
									delta: 1
								})
							}, 400)
						} else {
							uni.hideLoading()
							console.log(_res, "上传失败");
							uni.showToast({
								title: _res.msg || "上传失败",
								icon: "none"
							})
						}
					},
					fail: (err) => {
						uni.hideLoading()
						console.log(err, "上传失败");
					}
				})
			},
			//上传
			sureBtn() {
				// if (this.name == '') {
				// 	uni.showToast({
				// 		title: "请输入文件名称",
				// 		icon: "none"
				// 	})
				// 	return
				// }
				this.uploadFile();
				// this.$http("enterprise.cloud_pan/file_upload", params, "post").then(res => {
				// 	if (res.data.code == 1) {

				// 	}
				// })
				console.log(666666)
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

<template>
	<view>
		<u-navbar :is-back="true" title="新建便签" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="header-time">
				{{date}}
			</view>
			<view class="header-con">
				<u-input type="textarea" v-model="formData.content" :height="50" :auto-height="true"
					placeholder="写点什么...." />
			</view>
			<htz-image-upload style="margin-top: 15px;" mediaType="image" :max="9" name="file" :chooseNum="1"
				v-model="imageData" @chooseSuccess="ceshiChooseSuccess">
			</htz-image-upload>

		</view>

		<button type="default" class="sure_btn" @click="sureBtn">完成新建</button>
	</view>
</template>

<script>
	import dayjs from "@/utils/dayjs.min.js";
	import {
		url_config,img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {
				value: "",
				date: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
				imageData: [],
				formData: {
					images: "",
					content: ""
				}
			};
		},
		onLoad() {
			this.date = dayjs().format("YYYY-MM-DD HH:mm");
		},
		methods: {
			sureBtn() {
				this.formData.images = this.imageData.join(",");
				if(!this.formData.images){
					uni.showToast({
						title:"请上传图片!",
						icon:"none"
					})
					return
				}else if(!this.formData.content){
					uni.showToast({
						title:"请输入内容!",
						icon:"none"
					})
					return
				}
				this.$http("enterprise.Stikcy_note/addStikcyNote", this.formData, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "新建成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			ceshiChooseSuccess(e) {
				this.uploadImgs(e)
			},
			uploadImgs(files) {
				uni.showLoading({
					title: "上传中"
				})
				let promiseArr = [];
				let promiseItem = files.forEach(item => {
					new Promise((resolve, reject) => {
						uni.uploadFile({
							url: url_config + "/Common/upload",
							name: "file",
							filePath: item,
							header: {
								token: uni.getStorageSync("token") || ""
							},
							success: (res) => {
								let _res = JSON.parse(res.data);
								if (_res.code == 1) {
									resolve(true)
									this.imageData.push(`${img_url}${_res.data.url}`)
								
								} else {
									reject(false)
								}
								// promiseArr.push();
							},
							fail: (err) => {
								reject(false)
							}
						})
					})


				})
				promiseArr.push(promiseItem);
				Promise.all(promiseArr).then(res => {
					uni.hideLoading()
					uni.showToast({
						title: "上传成功",
					})
				}).catch(err => {
					uni.hideLoading()
					uni.showToast({
						title: "上传失败",
						icon: "none"
					})
				})
			},
		}
	}
</script>

<style scoped lang="scss">
	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		position: fixed;
		bottom: 32rpx;
		left: 50%;
		transform: translateX(-50%);
	}

	.mainBox {
		padding: 32rpx;

		.header-time {
			color: #7A7C94;
			font-size: 28rpx;
		}

		.header-con {
			margin-top: 30rpx;

			/deep/ .u-input__textarea {
				// background: pink;
				// background: #FBFCFF;
				border-radius: 8px;
				// padding: 8px;
				font-size: 30rpx;
			}

			/deep/ .uni-textarea-textarea {
				// min-height: 100px;
			}

		}
	}
</style>

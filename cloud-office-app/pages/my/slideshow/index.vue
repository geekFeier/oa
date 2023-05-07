<template>
	<view>
		<u-navbar :is-back="true" title="轮播图设置" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="title">
				轮播图上传
			</view>
			<htz-image-upload style="margin-top: 15px;" mediaType="image" :max="5" name="file" :chooseNum="5"
				v-model="imageData" @chooseSuccess="ceshiChooseSuccess">
			</htz-image-upload>

			<view class="desc">
				<view class="">
					企业文化、企业宣传等图片；
				</view>
				<view class="">
					建议尺寸212*212（1:1）；
				</view>
				<view class="">
					最多上传五张；
				</view>
			</view>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">确定</button>
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
				imageData: [],
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad() {
			this.getListData();
		},
		methods: {
			getListData() {
				this.$http("enterprise/Slide/slide", {}, "get").then(res => {
					if (res.data.code == 1) {
						if (res.data.data.images) {
							this.imageData = res.data.data.images.split(",")
							this.imageData.forEach((item,index) => {
								this.imageData[index] = this.imageData[index].indexOf("http") >= 0 ? this.imageData[index] :
									`${img_url}${this.imageData[index]}`
							})
							console.log(this.imageData, "SDAsdasdasdasdasdasdasdasdasdasdasdasda");
						}
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
			sureBtn() {
				let params = {
					images: this.imageData.join(",")
				}
				this.$http("enterprise/Slide/slides_operation", params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "保存成功",
							icon: "none"
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
	}

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
		padding: 0 32rpx;

		.title {
			font-size: 32rpx;
			font-weight: bold;
			margin-top: 32rpx;
		}

		.desc {
			color: #B5BFDA;
			font-size: 26rpx;
			margin-top: 32rpx;
		}

		/deep/ .htz-image-upload-Item {
			margin-left: 0;
		}
	}
</style>

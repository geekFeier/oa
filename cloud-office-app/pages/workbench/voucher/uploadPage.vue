<template>
	<view>
		<u-navbar :is-back="true" title="新增凭证" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<htz-image-upload style="margin-top: 15px;" mediaType="image"  :max="12" name="file" :chooseNum="12"
				v-model="imageData" @chooseSuccess="ceshiChooseSuccess">
			</htz-image-upload>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">保存</button>

	</view>
</template>

<script>
	import {
		url_config,img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
				imageData: []
			};
		},
		onLoad(e) {
			this.imageData = JSON.parse(e.imgData)
		},
		methods: {
			ceshiChooseSuccess(e) {
				this.uploadImgs(e);
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
				this.$navigateBack(this.imageData)
			}
		}
	}
</script>

<style lang="scss">
	.mainBox {
		padding: 0 32rpx;

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
</style>

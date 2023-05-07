<template>
	<view class="detailBox">
		<u-navbar :is-back="true" title="新建留言" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="header-title">
				<input type="text" v-model="formData.content" placeholder="例如：下个月撰写项目会议纪要" />
			</view>
			<view class="itemBox">
				<view class="itemDiv active">
					<image class="item-img" src="../../../static/image/tab1/my1.png" mode=""></image>
					我执行
				</view>
				<view class="itemDiv" @click="goContact">
					<image class="item-img" src="../../../static/image/tab1/my.png" mode=""></image>
					{{join_names ? join_names : '添加联系人'}}
				</view>
			</view>

			<view class="itemBox" style="margin-top: 24rpx;">
				<view class="itemDiv" @click="selectTime">
					<image class="item-img" src="../../../static/image/tab1/time.png" mode=""></image>
					{{formData.end_time ? formData.end_time : '添加截止时间'}}
				</view>

				<view class="itemDiv" @click="addFile">
					<image class="item-img" src="../../../static/image/tab1/url.png" mode=""></image>
					添加附件
				</view>
			</view>

			<view class="item-con">
				<u-input type="textarea" v-model="formData.desc" placeholder="留言内容...." />
			</view>

		</view>

		<u-picker v-model="isShowTime" mode="time" @confirm="sureTime" :params="params"></u-picker>

		<button type="default" class="sure_btn" @click="loginBtn">确定</button>

	</view>
</template>

<script>
	import {
		url_config,img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {
				imgData: [],
				join_names: "",
				params: {
					year: true,
					month: true,
					day: true,
					hour: true,
					minute: true,
				},
				formData: {
					join_ids: "",
					end_time: "",
					content: "",
					desc: "",
					type: 1,
					images: "",
				},
				isShowTime: false,
				value: "",
				title: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		methods: {
			sureTime(e) {
				this.formData.end_time = `${e.year}-${e.month}-${e.day} ${e.hour}:${e.minute}:00`
			},
			loginBtn() {
				this.formData.images = this.imgData.join(",");
				if(!this.formData.join_ids){
					uni.showToast({
						title:"请添加联系人!",
						icon:"none"
					})
					return
				}else if(!this.formData.end_time){
					uni.showToast({
						title:"请添加截止时间!",
						icon:"none"
					})
					return
				}else if(!this.formData.desc){
					uni.showToast({
						title:"请输入留言内容!",
						icon:"none"
					})
					return
				}else if(!this.formData.content){
					uni.showToast({
						title:"请输入标题!",
						icon:"none"
					})
					return
				}
				
				this.$http("enterprise.User_todo/CreateTodo", this.formData, "post").then(res => {
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
			selectTime() {
				this.isShowTime = true;
			},
			addFile() {
				uni.chooseImage({
					count: 4,
					success: (res) => {
						this.uploadImgs(res.tempFilePaths)
					}
				})
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
									this.imgData.push(`${img_url}${_res.data.url}`)
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
			goContact() {
				this.$navigateTo({
					url: "/components/contact"
				}).then(res => {
					let arrIds = [];
					let names = [];
					this.formData.join_ids = "";
					res.forEach(item => {
						arrIds.push(item.id);
						names.push(item.username)
					})
					this.formData.join_ids = arrIds.join(",")
					this.join_names = names.join(",")
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	/deep/ .u-input__textarea {
		// background: pink;
		background: #FBFCFF;
		border-radius: 8px;
		padding: 8px;
		font-size: 30rpx;
	}

	/deep/ .uni-textarea-textarea {
		min-height: 110px;
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
		height: 82vh;
		overflow-y: auto;

		.detailBox-bd {
			text-align: center;
			color: #B5BFDA;
			margin-top: 60rpx;
		}

		.itemBox {
			display: flex;
			align-items: center;
			margin-top: 50rpx;

			.itemDiv {
				padding: 6rpx 16rpx;
				border-radius: 36rpx;
				color: #7A7C94;
				font-size: 26rpx;
				border: 1px solid #B5BFDA;
				margin-right: 30rpx;
			}

			.item-img {
				width: 24rpx;
				height: 26rpx;
				margin-right: 8rpx;
			}

			.active {
				border: 1px solid #4396F7;
				color: #4396F7;
			}


		}

		.img-item-box {
			display: flex;
			flex-wrap: wrap;
			margin-top: 50rpx;
			margin-right: -24rpx;

			.img-item {
				width: 212rpx;
				height: 212rpx;
				margin-right: 24rpx;
				margin-bottom: 24rpx;
			}
		}

		.item-con {
			color: #150E33;
			font-size: 28rpx;
			margin-top: 50rpx;
		}

		.header-title {
			font-size: 32rpx;
			font-weight: bold;
			color: #150E33;
			margin-top: 34rpx;
		}
	}
</style>

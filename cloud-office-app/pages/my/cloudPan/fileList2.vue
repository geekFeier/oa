<template>
	<view>
		<u-navbar :is-back="true" title="文件" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
			<text slot="right" style="margin-right: 32rpx;color: #4396F7;"
				@click="mgtBtn">{{isEdit ? "取消" : "管理"}}</text>
		</u-navbar>
		<view class="file-box">
			<view class="" v-for="(item,index) in listData" :key="index" @click="">
				<view class="file-item" style="border-bottom: 1px solid #F6F9FE;">
					<view class="checkbox" style="width: 46rpx;min-width: 44rpx;" v-if="isEdit"
						:class="{checked: item.checked}" @click.stop="checkBtn('item',index)">
					</view>
					<view class="flex justify-between align-center u-flex-1">
						<view class="file-item-l ">
							<image src="../../../static/image/my/fileicon.png" class="file-item-l-icon" mode="">
							</image>
							<view class="file-info">
								<view class="file-name">
									{{item.name}}
								</view>
								<view class="file-size">
									{{item.createtime}}
								</view>
							</view>
						</view>
						<view class="file-item-r" @click="handlerItem(index)">
							<u-icon name="arrow-right" color="#7A7C94"></u-icon>
						</view>
					</view>
				</view>
				<view class="file-item-handle" v-if="item.select">
					<!-- <view class="file-item-handle-item" @click="editBtn">
						<image src="../../../static/image/my/zhuanfa.png" class="file-item-handle-item-icon"
							style="width: 45rpx;height: 42rpx;" mode=""></image>
						<text>转发</text>
					</view> 
					<view class="file-item-handle-item" @click="editBtn">
						<u-icon class="file-item-handle-item-icon" name="edit-pen-fill" color="#7A7C94" size="45">
						</u-icon>
						<text>编辑</text>
					</view> -->
					<!-- 		<view class="file-item-handle-item" @click="editBtn(item)">
						<u-icon class="file-item-handle-item-icon" name="edit-pen-fill" color="#7A7C94" size="45">
						</u-icon>
						<text>重命名</text>
					</view> -->
					<view class="file-item-handle-item" @click="downloadF(item)">
						<u-icon class="file-item-handle-item-icon" name="download" color="#7A7C94" size="45">
						</u-icon>
						<text>下载</text>
					</view>
					<view class="file-item-handle-item" @click="delBtn(item.id)">
						<u-icon name="trash-fill" class="file-item-handle-item-icon" color="#7A7C94" size="45"></u-icon>
						<text>删除</text>
					</view>
				</view>
			</view>

		</view>



		<view class="bd-div" v-if="isEdit">
			<view class="edit-left">
				<view class="checkboxAll" style="margin-right: 8rpx;" v-if="isEdit" :class="{checked: isAll}"
					@click.stop="checkBtn('all')">
				</view>
				全选
			</view>
			<view class="edit-btn" @click="delBtns">
				删除
			</view>
		</view>

		<u-modal v-model="isShowAlert" :show-cancel-button="true" :show-confirm-button="true" :show-title="true"
			title="重命名" @confirm="confirmBtn">

			<view class="slot-content text-center" style="padding: 24rpx 32rpx;" slot="default">
				<view class="inputClass">
					<input type="text" placeholder="请输入新文件名" v-model="name" />
				</view>
			</view>
		</u-modal>

		<image v-if="!isEdit" src="../../../static/image/tab1/add.png" class="addBtn" mode="" @click="handlerAdd">
		</image>

	</view>
</template>

<script>
	import {
		mapState
	} from "vuex"
	import {
		img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {
				isAll: false,
				isEdit: false,
				name: "",
				itemId: '',
				isShowAlert: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
				listData: [],
				dir_id: this.dir_id,
				pan_id: this.pan_id,
			};
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo,

			})
		},
		onShow() {
			this.getlu();
		},
		onLoad(e) {
			this.dir_id = e.dir_id
			this.pan_id = e.pan_id
		},
		methods: {
			confirmBtn() {
				let params = {
					dir_id: this.dir_id,
					pan_id: this.pan_id,
					name: this.name,
					id: this.itemId
				}
				this.$http("enterprise.cloud_pan/rename", params, "post").then(res => {
					if (res.data.code == 1) {
						this.getlu();
						this.name = "";
						this.itemId = "";
					}
				})
			},
			delBtn(id) {
				let params = {
					file_id: id
				}
				this.$http("enterprise.cloud_pan/delfile", params, "post").then(res => {
					if (res.data.code == 1) {
						this.getlu();
						this.isEdit = false;
					}
				})
			},
			delBtns() {
				let ids = this.listData.filter(item => item.checked).map(items => items.id).join(",");
				let params = {
					file_id: ids
				}
				this.$http("enterprise.cloud_pan/delfile", params, "post").then(res => {
					if (res.data.code == 1) {
						this.getlu();
						this.isEdit = false;
					}
				})
			},
			checkBtn(type, index) {
				if (type == "item") {
					this.listData[index].checked = !this.listData[index].checked;
					this.isAll = this.listData.every(item => item.checked)
				} else {
					this.isAll = !this.isAll;
					this.listData.forEach(item => {
						item.checked = this.isAll;
					})
				}
				this.$forceUpdate()
			},
			//获取目录  1为目录 -1为文件
			getlu() {
				let formData = {
					dir_id: this.dir_id,
					pan_id: this.pan_id,
					page: 1,
					limit: 10,
					offset: 0,
					search: ''
				}
				console.log('~~~~~~~', formData)

				this.$http("enterprise.cloud_pan/dir", formData, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = res.data.data.rows
					}
				})
			},
			handlerAdd() {
				// this.isShowAlert = true;
				uni.navigateTo({
					url: `/pages/my/cloudPan/addFile?pan_id=${this.pan_id}&dir_id=${this.dir_id}`
				})
			},
			goAddPage() {

			},
			mgtBtn() {
				this.isEdit = !this.isEdit;
			},
			editBtn(item) {
				this.name = item.name
				this.itemId = item.id
				this.isShowAlert = true;
			},
			handlerItem(index) {
				this.listData.forEach(item => {
					item.select = false;
				})
				this.listData[index].select = !this.listData[index].select;
				this.$forceUpdate()
			},
			getFileExtension(filePath) {
				return filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
			},
			downloadF(item) {
				let url = encodeURI(`${img_url}${item.local_path}${item.name}`)
				console.log(item)
				uni.downloadFile({
					url: url,
					success: (res) => {
						console.log(res)

						// 获取文件类型
						const fileType = this.getFileExtension(url);
						if (res.statusCode === 200) {
							// 判断文件类型并进行相应处理
							if (fileType === 'png' || fileType === 'jpg' || fileType === 'jpeg') {

								// 图片类型，保存到相册
								uni.saveImageToPhotosAlbum({
									filePath: res.tempFilePath,
									success: () => {
										uni.showToast({
											title: "下载成功",
										})
									},
									fail: (saveErr) => {
										uni.showToast({
											title: "下载失败",
											icon: "none"
										})
									}
								});

							} else {
								// 其他文件类型，保存到本地
								uni.saveFile({
									tempFilePath: res.tempFilePath, //临时路径
									success: function(res) {
										uni.showToast({
											icon: 'none',
											mask: true,
											title: '文件已保存：' + res.savedFilePath, //保存路径
											duration: 3000,
										});
										setTimeout(() => {
											//打开文档查看
											uni.openDocument({
												filePath: res.savedFilePath,
												success: function(res) {
													// console.log('打开文档成功');
												}
											});
										}, 3000)
									}
								});
							}

						} else {
							uni.showToast({
								title: "下载失败",
								icon: "none"
							})
						}
					},
					fail: (err) => {
						uni.showToast({
							title: "下载失败",
							icon: "none"
						})
					}
				});
			}
		},

	}
</script>

<style lang="scss" scoped>
	.addBtn {
		position: fixed;
		bottom: 158rpx;
		right: 32rpx;
		width: 110rpx;
		height: 110rpx;
	}

	.bd-div {
		height: 100rpx;
		width: 100%;
		position: fixed;
		background-color: #F6F9FE;
		padding: 0 32rpx;
		display: flex;
		align-items: center;
		bottom: 0;
		justify-content: space-between;

		.edit-left {
			display: flex;
			align-items: center;
		}

		.edit-btn {
			width: 240rpx;
			height: 80rpx;
			background: #FF253D;
			color: #fff;
			font-size: 28rpx;
			border-radius: 55rpx;
			line-height: 80rpx;
			text-align: center;
		}
	}


	.checkbox {
		border-radius: 50%;
		border: 1px solid #e2e6f0;
		width: 54rpx;
		height: 42rpx;
		margin-right: 14rpx;
	}

	.checked {
		background: url(../../../static/image/login/select-a.png) no-repeat;
		background-size: 100% 100%;
	}

	.checkboxAll {
		border-radius: 50%;
		border: 1px solid #e2e6f0;
		width: 56rpx;
		height: 52rpx;
		margin-right: 14rpx;
	}

	.inputClass {
		border: 1px solid #B5BFDA;
		padding: 24rpx;
		border-radius: 12rpx;
	}

	.file-box {
		margin-top: 24rpx;
		background: #FFFFFF;
		border-radius: 24rpx;

		.file-item {
			display: flex;
			justify-content: space-between;
			height: 142rpx;
			align-items: center;
			padding: 0 25rpx;
			word-break: break-all;

			.file-item-l {
				display: flex;
				align-items: center;

				.file-item-l-icon {
					width: 54rpx;
					height: 54rpx;
					margin-right: 24rpx;
				}

				.file-info {
					display: flex;
					flex-direction: column;
					flex: 1;

					.file-name {
						font-size: 30rpx;
						color: #150E33;
					}

					.file-size {
						color: #B5BFDA;
						font-size: 26rpx;
						margin-top: 20rpx;
					}
				}

			}


		}

		.file-item-handle {
			height: 112rpx;
			background: #F6F9FE;
			display: flex;
			padding: 0 20rpx;
			justify-content: space-around;
			align-items: center;

			.file-item-handle-item {
				display: flex;
				flex-direction: column;
				align-items: center;
				font-size: 24rpx;

				.file-item-handle-item-icon {
					margin-bottom: 10rpx;
				}
			}
		}
	}
</style>
<template>
	<view>
		<u-navbar :is-back="true" title="文件" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
			<text slot="right" style="margin-right: 32rpx;color: #4396F7;"
				@click="mgtBtn">{{isEdit ? "取消" : "管理"}}</text>
		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索文件" bg-color="#fff"
					v-model="search" :height="65" @search="searchEvent" :show-action="false" @clear="clearBtn">
				</u-search>
			</view>
		</view>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="30" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" :current="swiperCurrent" @change="tabsChange" :list="tabList"
			:is-scroll="true" :bar-style="barStyle">
		</u-tabs-swiper>
		<view class="file-box">
			<view v-for="(item,index) in listData" :key="index" @click="gofile()" v-if="Number(item.is_file) !=1">
				<view class="file-item" style="border-bottom: 1px solid #F6F9FE;">
					<view class="checkbox" style="width: 46rpx;" v-if="isEdit" :class="{checked: item.checked}"
						@click.stop="checkBtn('item',index)">
					</view>
					<view class="flex justify-between align-center u-flex-1">

						<view class="file-item-l">
							<!-- <image  src="../../../static/image/my/fileicon.png"
								class="file-item-l-icon" mode="">
							</image> -->
							<image src="../../../static/image/my/file.png"
								class="file-item-l-icon" mode="">
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

						<view class="file-item-r" @click.stop="handlerItem(index)">
							<u-icon name="arrow-right" color="#7A7C94"></u-icon>
						</view>
					</view>
				</view>
				<!-- <view class="file-item-handle" v-if="item.select">
					<view class="file-item-handle-item" @click="editBtn">
						<image src="../../../static/image/my/zhuanfa.png" class="file-item-handle-item-icon"
							style="width: 45rpx;height: 42rpx;" mode=""></image>
						
						<text>转发</text>
					</view>
					<view class="file-item-handle-item" @click="editBtn">
						<u-icon class="file-item-handle-item-icon" name="edit-pen-fill" color="#7A7C94" size="45">
						</u-icon>
						<text>编辑</text>
					</view>

				</view> -->
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


		<!-- <image v-if="!isEdit" src="../../../static/image/tab1/add.png" class="addBtn" mode="" @click="handlerAdd">
		</image> -->

	</view>
</template>

<script>
	export default {
		data() {
			return {
				search: "",
				page: 1,
				limit: 10,
				keyword: "",
				listData: [],
				barStyle: {
					textAlign: "center",
				},
				tabList: [
					// {
					// 	name: "最新文件"
					// },
					{
						name: "最近打开"
					},
				],
				swiperCurrent: 0,
				isAll: false,
				isEdit: false,
				name: "",
				isShowAlert: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad() {
			this.getListData();
		},
		watch: {
			search(val) {
				if (val === "") {
					this.page = 1;
					this.limit = 10;
					this.listData = [];
					this.getListData();
				}
			}
		},
		methods: {
			gofile(){
				uni.navigateTo({
					url:'../../my/cloudPan/myFile'
				})
			},
			delBtns() {
				let ids = this.listData.filter(item => item.checked).map(items => items.id).join(",");
				let params = {
					file_id: ids
				}
				this.$http("enterprise.cloud_pan/deldirectory", params, "post").then(res => {
					if (res.data.code == 1) {
						this.page = 1;
						this.limit = 10;
						this.listData = [];
						this.getListData();
						this.isEdit = false;
					}
				})
			},
			clearBtn() {
				this.search = "";
			},
			searchEvent() {
				this.page = 1;
				this.limit = 10;
				this.listData = [];
				this.getListData();
			},
			getListData() {
				let params = {
					offset: (this.page - 1) * 10,
					page: this.page,
					limit: this.limit,
					search: this.search
				}
				this.$http("enterprise.cloud_pan/getRecently", params, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = this.listData.concat(res.data.data.rows);
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
			tabsChange(index) {
				this.swiperCurrent = index;
			},
			handlerAdd() {
				// this.isShowAlert = true;
				uni.navigateTo({
					url: "/pages/cooperation/file/addFile"
				})
			},
			goAddPage() {

			},
			mgtBtn() {
				this.isEdit = !this.isEdit;
			},
			editBtn() {
				this.isShowAlert = true;
			},
			handlerItem(index) {
				this.listData.forEach(item => {
					item.select = false;
				})
				this.currentId = this.listData[index].id;
				this.listData[index].select = !this.listData[index].select;
				this.$forceUpdate()
			}
		}
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

	.headerBox {
		height: 102rpx;
		background: #FBFCFF;
		line-height: 102rpx;


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

			.file-item-l {
				display: flex;
				align-items: center;

				.file-item-l-icon {
					width: 62rpx;
					height: 54rpx;
					margin-right: 24rpx;
				}

				.file-info {
					display: flex;
					flex-direction: column;

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

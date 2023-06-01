<template>
	<view>
		<u-navbar :is-back="true" title="辅助核算" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

			<text @click="addBtn" slot="right" style="margin-right: 32rpx;color: #4396F7;font-size: 30rpx;">添加</text>
		</u-navbar>

		<view class="flex flex-je">
			<view class="common-header-r" @click="isShowPopop = true">
				<text>{{accountText}} </text>
				<u-icon name="arrow-down-fill"></u-icon>
			</view>
		</view>

		<view class="mainBox">
			<view class="main-item" v-for="item in listData" :key="item.id" @click="goList(item.id)">
				<text class="main-item-title">{{item.name}}</text>
				<u-icon name="arrow-right" color="#A2A3B4"></u-icon>
			</view>
		</view>


		<u-modal v-model="isShowAlert" :show-cancel-button="true" :show-confirm-button="true" :show-title="true"
			title="新增辅助核算类别" @confirm="confirmBtn">

			<view class="slot-content text-center" style="padding: 24rpx 32rpx;" slot="default">
				<view class="inputClass">
					<input type="text" placeholder="请输入辅助核算类别的名称" v-model="name" />

				</view>
			</view>
		</u-modal>


		<u-popup v-model="isShowPopop" mode="bottom" border-radius="56">
			<view class="popup-main">
				<view class="popup-common-title">
					— 选择账套 —
				</view>
				<view class="financial-popup-main">
					<view class="financial-popup-item " v-for="(item,index) in zhangtaoListData"
						@click="selectAccount(item.id,index)" :key="index">
						<view class="popup-item-l">
							<view class="financial-popup-item-hd">
								<text class="financial-popup-hd-txt">{{item.name}}</text>
								<view class="financial-popup-hd-status" v-if="item.id == defaultId">
									进行中
								</view>
							</view>
							<view class="financial-popup-item-bd">
								启用时间：{{ item.start_time | filterTime}} {{item.cate.name}}
							</view>
						</view>
						<view class="popup-item-r">
							<image :src="cuttnetId == item.id ? selecta : select" style="width: 40rpx;height: 40rpx;"
								mode=""></image>
						</view>
					</view>
				</view>
				<view class="popup-common-btnGroup">
					<view class="popup-common-btn" @click="isShowPopop = false">
						取消
					</view>
					<view class="popup-common-btn active" @click="sureAccountBtn">
						确定
					</view>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import dayjs from '@/utils/dayjs';
	export default {
		data() {
			return {
				name: "",
				isShowAlert: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
				listData: [],
				selecta: "../../../static/image/tab2/selecta.png",
				select: "../../../static/image/tab2/select.png",
				cuttnetId: 0,
				defaultId: "",
				isShowPopop: false,
				zhangtaoListData: [],
				accountText: '请选择' // 账套获取
			};
		},
		onLoad() {
			this.getCate();
			this.getZhangtaoListData();
		},
		filters: {
			filterTime(val) {
				return dayjs(val).format("YYYY年MM月")
			}
		},
		methods: {
			confirmBtn() {
				if (!this.name) {
					uni.showToast({
						title: "请输入类别名称",
						icon: "none"
					})
					this.isShowAlert = true;
					return false
				}
				let params = {
					name: this.name
				}
				this.$http("enterprise.Accounting/createCate", params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "添加成功",
							icon: "none"
						})
						this.getCate();
					}
				})
			},
			addBtn() {
				this.isShowAlert = true;
			},
			getCate() {
				let params = {
					pid: 0,
					search: ""
				}
				this.$http("enterprise.Accounting/list?search&pid=0", params, "get").then(res => {
					this.listData = res.data.data.rows;
				})
			},
			goList(id) {
				uni.navigateTo({
					url: "/pages/my/assistAccounting/list?pid=" + id
				})
			},
			sureAccountBtn() {
				let params = {
					akid: this.cuttnetId
				}
				uni.showLoading({
					title: '切换中'
				})
			
				this.$http("enterprise.Account_books/booksChanges", params, "post").then(res => {
					uni.hideLoading()
			
					if (res.data.code == 1) {
						this.isShowPopop = false;
						// TODO:
						// 			uni.$emit("changeUserInfo", true);
			
						// 			this.getUserInfo()
						// 			this.$nextTick(() => {
						// 				this.getList();
						// 				this.getListData();
						// 			})
					}
				})
			},
			getZhangtaoListData() {
				console.log('开始了')
				let params = {
					offset: 0,
					page: 1,
					limit: 50
				}
				this.$http("enterprise.Account_books/index", params, "post").then(res => {
					if (res.data.code == 1) {
						this.zhangtaoListData = res.data.data.rows;
					} else {
						this.zhangtaoListData = [];

					}
				})
			},
			selectAccount(id) {
				this.cuttnetId = id;
			},
		}
	}
</script>

<style lang="scss" scoped>
	.flex {
		align-items: center;
	}

	.flex1 {
		flex: 1;
	}

	.flex2 {
		flex: 2;
	}

	.flex-je {
		justify-content: flex-end;
	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 32rpx 0;
			border-bottom: 1px solid #F6F9FE;

			.main-item-title {
				font-size: 32rpx;
			}


		}
	}

	/deep/ .inputClass {
		margin: 18rpx 0;
		width: 520rpx !important;
		height: 76rpx !important;
		background: #FFFFFF;

		border: 1px solid #B5BFDA;
		display: flex;
		align-items: center;
		padding-left: 12rpx;
		border-radius: 8rpx;

		.input-placeholder {
			color: #B5BFDA !important;

		}
	}

	.common-header-r {
		padding-top: 15rpx;
		padding-bottom: 15rpx;
		padding-right: 30rpx;

	}

	.popup-main {

		.popup-common-title {
			font-size: 36rpx;
			color: #150E33;
			text-align: center;
			margin-top: 56rpx;
		}

		.active {
			background: #4396F7 !important;
			color: #FFFFFF !important;
		}

		.popup-common-btnGroup {
			display: flex;
			padding: 0 50rpx;
			justify-content: space-between;
			font-size: 30rpx;
			margin-top: 50rpx;
			margin-bottom: 32rpx;


			.popup-common-btn {
				width: 260rpx;
				height: 80rpx;
				background: #F6F9FE;
				border-radius: 12rpx;
				color: #7A7C94;
				text-align: center;
				line-height: 80rpx;
			}
		}

		.financial-popup-main {
			padding: 0 32rpx;

			.financial-popup-item {
				margin-top: 32rpx;
				display: flex;
				align-items: center;
				justify-content: space-between;
				border-bottom: 1px solid #EEF2FF;
				padding-bottom: 32rpx;

				.financial-popup-item-hd {
					display: flex;
					align-items: center;

					.financial-popup-hd-status {
						background: #FF253D;
						border-radius: 48rpx;
						padding: 4rpx 8rpx;
						color: #fff;
						font-size: 24rpx;
						margin-left: 16rpx;
					}

					.financial-popup-hd-txt {
						font-size: 32rpx;
						color: #150E33;
						font-weight: bold;
					}
				}

				.financial-popup-item-bd {
					color: #7A7C94;
					font-size: 28rpx;
					margin-top: 30rpx;
				}
			}
		}
	}
</style>
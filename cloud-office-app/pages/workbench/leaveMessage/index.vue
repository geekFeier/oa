<template>
	<view class="generationOfficeBox">
		<u-navbar :is-back="true" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" title="我的留言" :height="55">
		</u-navbar>

		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" :key="index" @click="goDetail(item.id)">
				<view class="main-item-hd">
					<view class="circle" :class="item.type | filterColor">
					</view>
					<text class="main-item-title">{{item.content}}</text>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-con">
						{{item.desc}}
					</view>
					<view class="main-item-m-bd">
						<u-icon name="clock"></u-icon>
						<text style="margin-left: 8rpx;">{{item.end_time}}截止</text>
					</view>
				</view>
			</view>
		</view>

		<image src="../../../static/image/tab1/add.png" class="addBtn" mode="" @click="goAddPage"></image>



	</view>
</template>

<script>

	export default {
		data() {
			return {
				navTitle: "",
				listData: [],
				type: 0,
				page: 1,
				limit: 10,
				background: {
					backgroundColor: "#FFFFFF",
				},
				popupList: [{
						name: "我的待办",
						color: "pul"
					},
					// {
					// 	name: "待我处理",
					// 	color: "red"
					// },
					{
						name: "我发起的",
						color: "blue"
					}, {
						name: "我参与的",
						color: "orange"
					}, {
						name: "已处理的",
						color: "green"
					}
				]
			};
		},
		filters: {
			filterColor(val) {
				switch (Number(val)) {
					case 0:
						return "pul"
						break;
					case 1:
						return "blue"
						break;
					case 2:
						return "oragin"
						break;
					case 3:
						return "green"
						break;
				}

			}
		},
		onLoad() {
			this.getList();
		},
		onReachBottom(){
			this.page++;
			this.getList()
		},
		methods: {
			selectType(index) {
				this.type = index;
				this.page = 1;
				this.listData = [];
				this.getList();
			},
			getList() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
					status: "",
					type:1
				}
				this.$http("enterprise.User_todo/index", params, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = this.listData.concat(res.data.data.rows);
					}
				})
			},
			goDetail(id) {
				uni.navigateTo({
					url: "/pages/workbench/leaveMessage/detail?id=" + id
				})
			},
			goAddPage() {
				this.$navigateTo({
					url: "/pages/workbench/leaveMessage/addpage"
				}).then(res => {
					this.page=1;
					this.listData = [];
					this.getList();
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.addBtn {
		position: fixed;
		bottom: 298rpx;
		right: 32rpx;
		width: 110rpx;
		height: 110rpx;
	}

	page {
		background: #FBFCFF;
	}

	/deep/ .u-navbar-inner {
		position: relative;
		z-index: 99999999999;
	}

	.generationOfficeBox {
		.mainBox {
			padding: 0 32rpx;

			.main-item {
				padding: 24rpx 32rpx;
				box-sizing: border-box;
				background-color: #fff;
				border-radius: 14rpx;
				margin-top: 32rpx;

				.main-item-hd {
					display: flex;
					align-items: center;

					.main-item-title {
						color: #150E33;
						font-size: 32rpx;
						font-weight: bold;
						margin-left: 10rpx;
					}
				}

				.main-item-m {
					padding: 0 30rpx;
					margin-top: 24rpx;

					.main-item-m-con {
						color: #7A7C94;
						font-size: 28rpx;
					}

					.main-item-m-bd {
						color: #B5BFDA;
						font-size: 26rpx;
						margin-top: 30rpx;
					}
				}
			}
		}

		.header-div {
			transform: translateX(-2%);
			width: 100%;
			display: flex;
			justify-content: center;

			.header-icon {
				margin-left: 16rpx;
			}
		}

		.popup-main {
			padding: 32rpx;

			.popup-item {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-bottom: 50rpx;



				.popup-item-l {
					display: flex;
					align-items: center;
				}

				.popup-item-l-name {
					font-size: 32rpx;
					color: #150E33;
				}

				// width: 100vw;
			}
		}
	}

	.circle {
		width: 18rpx;
		height: 18rpx;
		border-radius: 50%;
		margin-right: 8rpx;
	}

	.pul {
		background-color: #915FF2;
	}

	.red {
		background-color: #FF253D;
	}

	.blue {
		background-color: #4396F7;
	}

	.orange {
		background-color: #FA7443;
	}

	.green {
		background-color: #12D592;
	}
</style>

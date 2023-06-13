<template>
	<view class="generationOfficeBox">
		<u-navbar :is-back="true" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
			<view class="header-div">
				<text @click="openPopup">{{navTitle}}</text>
				<u-icon @click="openPopup" name="arrow-down-fill" class="header-icon" size="22"></u-icon>
			</view>
		</u-navbar>


		<view class="mainBox" >
				<view class="tit">{{navTitle}}列表</view>
				<view class="main-item" v-for="(item,index) in listData"
					:key="index + 'daiban'" @click="goDetail(item)">
					<view class="main-item-hd">
						<view class="circle" >
						</view>
						<text class="main-item-title">{{item.content}}</text>
					</view>
					<view class="main-item-m">
						<view class="main-item-m-con">
							{{item.remarks}}
						</view>
						<view class="main-item-m-bd">
							<u-icon name="clock"></u-icon>
							<text style="margin-left: 8rpx;">发起时间：{{item.createtime}}</text>
						</view>
					</view>
				</view>

		</view>

		<popupLayer ref="popupRef" v-model="isShowPopup" :direction="'bottom'">
			<view class="popup-main">
				<view class="popup-item" v-for="(item,index) in popupList" :key="index" @click="selectType(index)">
					<view class="popup-item-l">
						<view class="circle" :class="item.color">

						</view>
						<text class="popup-item-l-name">{{item.name}}</text>
					</view>
					<u-icon color="#7A7C94" name="arrow-right"></u-icon>
				</view>
			</view>
		</popupLayer>
	</view>
</template>

<script>
	import popupLayer from "../../../components/popup-layer.vue";

	export default {
		data() {
			return {
				navTitle: "",
				listData: [],
				type: 4,
				page: 1,
				limit: 10,
				isShowPopup: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
				popupList: [{
					name: "全部",
					color: "black"
				}, {
					name: "我收到的",
					color: "pul"
				}, {
					name: "我发起的",
					color: "blue"
				}, {
					name: "我参与的",
					color: "orange"
				}, {
					name: "待我处理",
					color: "red"
				}, {
					name: "已处理的",
					color: "green"
				}]
			};
		},
		filters: {
			filterColor(val) {
				switch (Number(val)) {
					case 0:
						return "black"
						break;
					case 1:
						return "pul"
						break;
					case 2:
						return "blue"
						break;
					case 3:
						return "orange"
						break;
					case 4:
						return "red"
						break;
					case 5:
						return "green"
						break;
				}
			}
		},
		onLoad() {
			this.getListData();
			this.navTitle = this.popupList[this.type].name
		},
		watch: {
			type(val) {
				this.navTitle = this.popupList[val].name
			}
		},
		components: {
			popupLayer
		},
		onReachBottom: function() {
			this.page++;
			this.getListData()
		},
		onPullDownRefresh() {
			this.page = 1
			this.listData = []
			this.getListData();
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			selectType(index) {
				this.type = index;
				this.page = 1;
				this.isShowPopup = false;
				this.listData = [];
				this.getListData();
			},
			getListData() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
					type: this.type
				}

				this.$http("enterprise.applyfor.Oa/index", params, "get").then(res => {
					if (res.data.code == 1) {
						this.$set(this, 'listData', this.listData.concat(res.data.data.rows))
					}
				})
			},
			// goDetail(id) {
			// 	uni.navigateTo({
			// 		url: "/pages/workbench/generationOffice/detail?id=" + id
			// 	})
			// },
			goDetail(item) {
				console.log('报账：', item)
				switch (item.flag) {
					case 4:
						uni.navigateTo({
							url: "/pages/cooperation/applyAllPage/userCarApply/detail?data=" + encodeURIComponent(
								JSON
								.stringify(item))
						})
						break;
					case 2:
						uni.navigateTo({
							url: "/pages/cooperation/applyAllPage/sealApply/detail??data=" + encodeURIComponent(
								JSON
								.stringify(item))
						})
						break;
					case 3:
						uni.navigateTo({
							url: "/pages/cooperation/applyAllPage/maketTicketApply/detai?data=" +
								encodeURIComponent(JSON
									.stringify(item))
						})
						break;
					case 1:
						uni.navigateTo({
							url: "/pages/cooperation/applyAllPage/safeguard/detail?data=" + encodeURIComponent(JSON
								.stringify(item))
						})
						break;
					case 5:
						uni.navigateTo({
							url: "/pages/cooperation/applyAllPage/receiveApply/detail?data=" + encodeURIComponent(
								JSON
								.stringify(item))
						})
						break;
					case 6:
						uni.navigateTo({
							url: "/pages/cooperation/applyAllPage/payApply/detail?data=" + encodeURIComponent(JSON
								.stringify(item))
						})
						break;
					case 7:
						uni.navigateTo({
							url: "/pages/cooperation/applyAllPage/otherApply/detail?data=" + encodeURIComponent(
								JSON
								.stringify(item))
						})
						break;
				}
			},
			openPopup() {
				this.isShowPopup = true;
				// this.$refs.popup.open()
			}
		}
	}
</script>

<style lang="scss" scoped>
	.tit {
		padding-left: 20rpx;
		font-size: 30rpx;
		font-weight: bold;
	}

	.dash {
		height: 1px;
		width: 90%;
		margin: 30rpx auto 40rpx;
		background: #e2e4e7;
	}

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

	.black {
		background-color: black;
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
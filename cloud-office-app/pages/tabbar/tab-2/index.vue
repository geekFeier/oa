<template>
	<view class="tabBox">

		<!-- 		<view class="header-div">
			<view class="unit-info">
				<image class="unit-icon" src="../../../static/image/launch_icon.png" mode=""></image>
				<text class="unit-title">河南云赞有限公司</text>
			</view>

		</view> -->

		<u-navbar :is-back="false" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

			<view class="unit-info">
				<image class="unit-icon" v-if="enterprice.enterprice_image" :src="getUrl(enterprice.enterprice_image) "
					mode=""></image>
				<text class="unit-title u-line-2">{{enterprice.enterprice_name}}</text>

			</view>
		</u-navbar>


		<view class="mainBox">
			<apply></apply>
			<cooperation></cooperation>
			<!-- 公告 -->
			<view class="chargeBox">
				<view class="chargeMain">
					<view class="common-title">
						公告
					</view>
					<view class="carge-item" v-for="(item,index) in listData" :key="index"
						@click="goAnnouncementDetail(item)">
						<text class="carge-item-title">{{item.content}}</text>
						<u-icon name="arrow-right" color="#7A7C94" size="28"></u-icon>
					</view>
				</view>
			</view>


			<!-- 申请 -->
			<view class="useCar-box" v-if="recentlyData.length">
				<view class="useCar-main" v-for="(item,index) in recentlyData " :key="index">
					<view class="userCar-header">
						<view class="userCar-header-l">
							{{item.flag | filtersSq}}
						</view>
						<u-icon name="arrow-right" color="#7A7C94" size="28"></u-icon>
					</view>

					<view class="userCar-item" @click="goDetail(item)">
						<view class="userCar-item-header">
							<view class="userCar-item-header-l">
								{{item.user && item.user.username}}提交的申请
							</view>
							<view class="userCar-item-header-r">
								审核中
							</view>
						</view>
						<view class="userCar-item-desc">
							申请事由：{{item.content || '-'}}
						</view>
						<view class="userCar-item-desc">
							申请时间：{{item.createtime | filterTime}}
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		url_config,
		img_url
	} from "@/config/config.js"

	import apply from "./components/apply.vue"
	import cooperation from "./components/cooperation.vue"
	import dayjs from '@/utils/dayjs';
	import {
		mapState
	} from "vuex"
	export default {
		data() {
			return {
				recentlyData: [],
				useCarlistData: [],
				listData: [],
				page: 1,
				background: {
					backgroundColor: "#4396F7",
				},
			};
		},
		computed: {
			// ...mapState({
			// 	userInfo: state => state.user.userInfo,
			// 	enterprice: state => state.user.enterprise,
			// 	personType: state => state.user.personType,
			// })
		},
		components: {
			apply,
			cooperation
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('userInfo')
			this.personType = uni.getStorageSync('personType')
			this.enterprice = uni.getStorageSync('enterprise')

			this.getListData();
			this.getUserCarListData();
			this.getRecentlys();
		},
		filters: {
			filterTime(val) {
				return dayjs(val * 1000).format("YYYY-MM-DD hh:mm:ss");
			},
			filtersSq(val) {
				// 1.报账     2用印   3开票   4用车  5领用  6支付  7其他 
				switch (val) {
					case 1:
						return "报账申请"
						break;
					case 2:
						return "用印申请"
						break;
					case 3:
						return "开票申请"
						break;
					case 4:
						return "用车申请"
						break;
					case 5:
						return "领用申请"
						break;
					case 6:
						return "支付申请"
						break;
					case 7:
						return "其他申请"
						break;
					default:
						return "申请"
						break;
				}
			}
		},
		onPullDownRefresh() {
			this.listData = []
			this.getListData();
			this.getUserCarListData();
			this.getRecentlys();
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			getUrl(url) {
				return img_url + url
			},

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
			getRecentlys() {
				let params = {
					page: 1,
					type: 4,
					status: 0,
					limit: 100,
					offset: 0,
				}
				this.$http("enterprise.applyfor.Oa/index", params, "get").then(res => {
					console.log('wo----', res.data)
					if (res.data.code == 1) {
						this.recentlyData = res.data.data.rows;
					}
				})
			},
			addBtn() {
				uni.navigateTo({
					url: "/pages/cooperation/applyAllPage/userCarApply/index"
				})
			},
			getListData() {
				let params = {
					page: this.page,
					limit: 4,
					offset: (this.page - 1) * this.limit,
					sort: "id",
					order: "desc"
				}
				this.$http("enterprise.Notice/index", params, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = res.data.data.rows;
					}
				})
			},
			getUserCarListData() {
				let params = {
					offset: 1 * 10,
					page: 1,
					limit: 1
				}
				this.$http("enterprise.applyfor.Yongche/index", params, "get").then(res => {
					this.useCarlistData = res.data.data.rows;

					// this.listData.forEach(item => {
					// 	item.vihicel = JSON.parse(decodeURIComponent(item.vihicel))
					// })
				})
			},
			goAnnouncementDetail(item) {
				uni.navigateTo({
					url: "/pages/my/gonggao/detail?data=" + encodeURIComponent(JSON.stringify(item))
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {

		background: #FBFCFF;
		color: #150E33;
	}


	.unit-info {
		padding: 0 32rpx;
		display: flex;
		align-items: center;

		.unit-icon {
			width: 70rpx;
			height: 70rpx;
			border-radius: 50%;

		}

		.unit-title {
			width: 600rpx;
			margin-left: 20rpx;
			font-weight: 700;
			font-size: 40rpx;
			color: #FFFFFF;
		}
	}

	.common-title {
		font-size: 34rpx;
		color: #150E33;
		font-weight: bold;
	}

	.mainBox {

		// padding: 0 32rpx;
		/deep/ .u-grid-item-box {
			padding: 16rpx 0 !important;
		}

		.useCar-box {
			padding: 0 32rpx;
			margin-top: 20rpx;
			overflow: hidden;
			position: relative;

			.addBtn {
				position: absolute;
				width: 65rpx;
				height: 65rpx;
				bottom: 32rpx;
				right: 24rpx;
			}

			.useCar-main {
				// background-color: pink;
				padding: 32rpx 24rpx;
				background: #fff;
				box-sizing: border-box;
				border-radius: 24rpx;

				.userCar-header {
					display: flex;
					justify-content: space-between;
					font-size: 30rpx;
					color: #7A7C94;
					border-bottom: 1px solid #EEF2FF;
					padding: 0 0 32rpx 0;
				}

				.userCar-item {
					.userCar-item-header {
						display: flex;
						justify-content: space-between;
						font-size: 32rpx;
						margin-top: 32rpx;
					}

					.userCar-item-header-r {
						color: #4396F7;
					}

					.userCar-item-desc {
						font-size: 26rpx;
						color: #B5BFDA;
						margin-top: 24rpx;
					}
				}
			}
		}

		.chargeBox {
			padding: 0 32rpx;
			margin-top: 20rpx;
			overflow: hidden;

			.chargeMain {
				// background-color: pink;
				padding: 32rpx 24rpx;
				background: #fff;
				box-sizing: border-box;
				border-radius: 24rpx;

				.carge-item {
					display: flex;
					justify-content: space-between;
					margin-top: 24rpx;

					.carge-item-title {
						font-size: 30rpx;
						color: #150E33;
					}
				}
			}
		}

	}
</style>
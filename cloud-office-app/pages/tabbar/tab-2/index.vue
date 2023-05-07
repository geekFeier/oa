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
				<image class="unit-icon" :src="enterprice.enterprice_image" mode=""></image>
				<text class="unit-title">{{enterprice.enterprice_name}}</text>
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


			<!-- 用车申请 -->
			<view class="useCar-box" v-if="recentlyData">
				<view class="useCar-main">
					<view class="userCar-header">
						<view class="userCar-header-l">
							{{recentlyData.flag | filtersSq}}
						</view>
						<u-icon name="arrow-right" color="#7A7C94" size="28"></u-icon>
					</view>

					<view class="userCar-item" @click="goDetail()">
						<view class="userCar-item-header">
							<view class="userCar-item-header-l">
								{{recentlyUser.username}}提交的用车申请
							</view>
							<view class="userCar-item-header-r">
								{{recentlyItem.status == 0 ? "审核中" : (recentlyItem.status  == 1? "审核成功" : "审核失败") }}
							</view>
						</view>
						<view class="userCar-item-desc">
							申请事由：{{recentlyItem.content ? recentlyItem.content : recentlyItem.title}}
						</view>
						<view class="userCar-item-desc">
							申请时间：{{recentlyItem.createtime}}
						</view>
					</view>
				</view>

				<!-- <img src="../../../static/image/tab1/add.png" class="addBtn" @click="addBtn"> -->
			</view>
		</view>
	</view>
</template>

<script>
	import apply from "./components/apply.vue"
	import cooperation from "./components/cooperation.vue"
	import {
		mapState
	} from "vuex"
	export default {
		data() {
			return {
				recentlyUser: {},
				recentlyItem: {},
				recentlyData: {},
				useCarlistData: [],
				listData: [],
				page: 1,
				background: {
					backgroundColor: "#4396F7",
				},
			};
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo,
				enterprice: state => state.user.enterprise,
				personType: state => state.user.personType,
			})
		},
		components: {
			apply,
			cooperation
		},
		onLoad() {
			this.getListData();
			this.getUserCarListData();
			this.getRecentlys();
		},
		filters: {
			filtersSq(val) {
				switch (val) {
					case "yongche":
						return "用车申请"
						break;
					case "yongyin":
						return "用印申请"
						break;
					case "kaipiao":
						return "开票申请"
						break;
					case "baozhang":
						return "报账申请"
						break;
					case "lingyong":
						return "领用申请"
						break;
					case "zhifu":
						return "支付申请"
						break;
					case "qita":
						return "其他申请"
						break;
					default:
						return "申请"
						break;

				}
			}
		},
		methods: {
			goDetail(item){
				switch (this.recentlyData.flag) {
					case "yongche":
						uni.navigateTo({
							url:"/pages/cooperation/applyAllPage/userCarApply/detail?data=" + encodeURIComponent(JSON
						.stringify(this.recentlyData.itme))
						})
						break;
					case "yongyin":
						uni.navigateTo({
							url:"/pages/cooperation/applyAllPage/sealApply/detail??data=" + encodeURIComponent(JSON
						.stringify(this.recentlyData.itme))
						})
						break;
					case "kaipiao":
						uni.navigateTo({
							url:"/pages/cooperation/applyAllPage/maketTicketApply/detai?data=" + encodeURIComponent(JSON
						.stringify(this.recentlyData.itme))
						})
						break;
					case "baozhang":
						uni.navigateTo({
							url:"/pages/cooperation/applyAllPage/safeguard/detail?data=" + encodeURIComponent(JSON
						.stringify(this.recentlyData.itme))
						})
						break;
					case "lingyong":
						uni.navigateTo({
							url:"/pages/cooperation/applyAllPage/receiveApply/detail?data=" + encodeURIComponent(JSON
						.stringify(this.recentlyData.itme))
						})
						break;
					case "zhifu":
						uni.navigateTo({
							url:"/pages/cooperation/applyAllPage/payApply/detail?data=" + encodeURIComponent(JSON
						.stringify(this.recentlyData.itme))
						})
						break;
					case "qita":
						uni.navigateTo({
							url:"/pages/cooperation/applyAllPage/otherApply/detail?data=" + encodeURIComponent(JSON
						.stringify(this.recentlyData.itme))
						})
						break;
				
				}
			},
			getRecentlys() {
				this.$http("enterprise.applyfor.base/recentlys", {}, "get").then(res => {
					if (res.data.code == 1) {
						this.recentlyData = res.data.data;
						if (this.recentlyData) {
							this.recentlyItem = res.data.data.itme;
							this.recentlyUser = res.data.data.itme.user
						}


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

		}

		.unit-title {
			margin-left: 20rpx;
			font-weight: 700;
			font-size: 34rpx;
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

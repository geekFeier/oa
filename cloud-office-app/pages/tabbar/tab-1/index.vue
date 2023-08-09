<template>
	<view class="tabBox">
		<view class="headerBox">
			<view class="unit-info">
				<image class="unit-icon" v-if="enterprice.enterprice_image"
					:src="getComLogo(enterprice.enterprice_image)" mode=""></image>
				<text class="unit-title">{{enterprice.enterprice_name}}</text>
			</view>

			<view class="bannerBox">
				<u-swiper :list="list" border-radius="24" height="330"></u-swiper>
			</view>

		</view>
		<!-- menu -->
		<view class="menuBox">
			<u-grid :col="4" :border="false">
				<u-grid-item @click="jumpTo('generationOffice')">
					<view style="position:relative">
						<view class="badge">{{countData.daiban > 99 ? '99+' : countData.daiban}}</view>
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;position: relative;"
							src="../../../static/image/tab1/menu1.png" mode="">
						</image>
					</view>
					<view class="grid-text">待办</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('leaveMessage')">
					<view style="position:relative">
						<view class="badge"> {{countData.liuyan > 99 ? '99+' : (countData.liuyan || 0)}}</view>
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../static/image/tab1/menu2.png" mode="">
						</image>
					</view>
					<view class="grid-text">留言</view>
				</u-grid-item>
				<u-grid-item @click="goSchedule">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../static/image/tab1/menu3.png" mode="">
					</image>
					<view class="grid-text">新建日程安排</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('memo')">
					<image style="width: 50rpx;height: 54rpx;" src="../../../static/image/tab1/menu4.png" mode="">
					</image>
					<view class="grid-text">便签</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('signPage')" v-if="personType==2 || personType==3 || personType==4">
					<image style="width: 60rpx;height: 54rpx;" src="../../../static/image/tab1/qiandao.png" mode="">
					</image>
					<view class="grid-text">签到</view>
				</u-grid-item>
			</u-grid>
		</view>
		<!-- 便签 -->
		<view class="chargeBox" v-if="personType==2 || personType==4">
			<memo></memo>
		</view>
		<!-- 待办 -->
		<view class="chargeBox">
			<view class="chargeMain">
				<view class="common-title">
					待办
				</view>
				<view class="carge-item" v-for="(item,index) in daiBanListData.slice(0,3)" :key="index"
					@click="goDaiBanDetail(item)">
					<text class="carge-item-title">{{item.content}}</text>
					<u-icon name="arrow-right" color="#7A7C94" size="28"></u-icon>
				</view>
			</view>
		</view>
		<!-- v-if="personType==1 || personType==3" -->
		<!-- 财务核算 -->
		<financialAccounting v-if="!isLogin || personType==1 || personType==3"
			@changeFinancialPopup="changeFinancialPopup" :accountText="accountText"></financialAccounting>
		<!-- 三方服务 -->
		<threeServices v-if="personType==3"></threeServices>
		<!-- 财务报表 -->
		<view class="statementBox" v-if="personType==1">
			<view class="statementMain">
				<view class="common-title">
					财务报表
				</view>
				<u-grid :col="4" :border="false" style="margin-top: 12rpx;">
					<u-grid-item @click="jumpTo('incomeStatement')">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../static/image/tab1/c1.png" mode="">
						</image>
						<view class="grid-text">利润表</view>
					</u-grid-item>
					<u-grid-item @click="jumpTo('cashFlowStatement')">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../static/image/tab1/c2.png" mode="">
						</image>
						<view class="grid-text">现金流量表</view>
					</u-grid-item>
					<u-grid-item @click="jumpTo('assetAndLiability')">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../static/image/tab1/c3.png" mode="">
						</image>
						<view class="grid-text">资产负债表</view>
					</u-grid-item>
				</u-grid>
			</view>
		</view>
		<!-- 经营指标 -->
		<view class="indicatorBox" v-if="personType==1">
			<view class="common-title" style="font-size: 38rpx;margin-top: 40rpx;margin-bottom: 40rpx;">
				经营指标
			</view>
			<view class="indicatorMain">
				<view class="header-div" @click="goAccountBalanceSheet">
					<view class="header-div-l">
						<image class="header-div-l-img" src="../../../static/image/tab1/indicator.png" mode="">
						</image>
						<text style="margin-left: 12rpx;font-size: 30rpx;
						color: #150E33;">资金情况</text>
					</view>
					<u-icon name="arrow-right" color="#7A7C94" size="28"></u-icon>
				</view>
				<view class="hr-div">
				</view>
				<view class="indicator-m">
					<view class="indicator-m-l">
						<ringEchart></ringEchart>
					</view>
					<view class="indicator-m-r">
						<view class="indicator-m-r-item">
							借:{{ziJinData.borrow}}
						</view>
						<view class="indicator-m-r-item">
							贷:{{ziJinData.loan}}
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 应收账款 -->
		<view class="gatheringBox" v-if="personType==1 || personType==2">
			<view class="gatheringMain">
				<view class="header-div" @click="goAccountBalanceSheet">
					<view class="header-div-l">
						<image class="header-div-l-img" src="../../../static/image/tab1/yingshou.png" mode="">
						</image>
						<text style="margin-left: 12rpx;font-size: 30rpx;
						color: #150E33;">应收账款</text>
					</view>
					<u-icon name="arrow-right" color="#7A7C94" size="28"></u-icon>
				</view>
				<view class="hr-div">
				</view>
				<view class="gathering-m">
					<view class="gathering-m-item">
						<view class="gathering-m-item-hd">
							{{gathering.count}}
						</view>
						<view class="gathering-m-item-bd">
							客户数（家）
						</view>
					</view>
					<view class="gathering-m-item">
						<view class="gathering-m-item-hd">
							￥{{gathering.money}}
						</view>
						<view class="gathering-m-item-bd">
							金额（元）
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 应付账款 -->
		<view class="gatheringBox" v-if="personType==1 || personType==2">
			<view class="gatheringMain">
				<view class="header-div" @click="goAccountBalanceSheet">
					<view class="header-div-l">
						<image class="header-div-l-img" src="../../../static/image/tab1/fukuan.png" mode="">
						</image>
						<text style="margin-left: 12rpx;font-size: 30rpx;
						color: #150E33;">应付账款</text>
					</view>
					<u-icon name="arrow-right" color="#7A7C94" size="28"></u-icon>
				</view>
				<view class="hr-div">
				</view>
				<view class="gathering-m">
					<view class="gathering-m-item">
						<view class="gathering-m-item-hd">
							{{yingFuData.count}}
						</view>
						<view class="gathering-m-item-bd">
							客户数（家）
						</view>
					</view>
					<view class="gathering-m-item">
						<view class="gathering-m-item-hd">
							￥{{yingFuData.money}}
						</view>
						<view class="gathering-m-item-bd">
							金额（元）
						</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 经营情况 -->
		<view class="gatheringBox" v-if="personType==1">
			<view class="gatheringMain">
				<view class="header-div" @click="jyqk()">
					<view class="header-div-l">
						<image class="header-div-l-img" src="../../../static/image/tab1/jingying.png" mode="">
						</image>
						<text style="margin-left: 12rpx;font-size: 30rpx;
							color: #150E33;">经营情况</text>
					</view>
					<u-icon name="arrow-right" color="#7A7C94" size="28"></u-icon>
				</view>
				<view class="hr-div">
				</view>
				<view class="gathering-m">
					<view class="charts-box">
						<lineEchart></lineEchart>
					</view>
				</view>
			</view>
		</view>
		<u-popup v-model="isShowPopop" mode="bottom" border-radius="56">
			<view class="popup-main">
				<view class="popup-common-title">
					— 选择账套 —
				</view>
				<view class="financial-popup-main">
					<view class="financial-popup-item " v-for="(item,index) in listData"
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
							<!-- <image src="../../../static/image/tab2/select.png" mode=""></image> -->
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
	import financialAccounting from "./components/financialAccounting.vue"
	import threeServices from "./components/threeServices.vue"
	import memo from "./components/memo.vue";
	import lineEchart from "./components/line.vue"
	import ringEchart from "./components/ring.vue"
	import dayjs from '@/utils/dayjs';
	import {
		mapState,
		mapGetters
	} from "vuex"
	import {
		url_config,
		img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {
				ziJinData: {
					borrow: 0,
					loan: null
				},
				gathering: {
					count: 0,
					money: 0
				},
				yingFuData: {
					count: 0,
					money: 0
				},

				isLogin: "",
				daiBanListData: [],
				accountText: "",
				selecta: "../../../static/image/tab2/selecta.png",
				select: "../../../static/image/tab2/select.png",
				defaultId: "",
				listData: [],
				isShowPopop: false,
				page: 1,
				limit: 10,
				cuttnetId: 0,
				list: [],
				currentPage: {
					page: 1,
					limit: 10
				},
				enterprice: {},
				personType: {},
				userInfo: {},
				countData: {
					daiban: null,
					liuyan: null,
				}


			};
		},
		filters: {
			filterTime(val) {
				return dayjs(val).format("YYYY年MM月")
			}
		},
		onPullDownRefresh() {
			this.currentPage.page = 1
			this.getDaiBanList() 
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		components: {
			memo,
			lineEchart,
			ringEchart,
			financialAccounting,
			threeServices
		},
		onShow() {
			this.getDaiBanList(); 
			this.getListData();
			this.getUserInfo();
			this.getYingFuData();

		},
		onLoad() {
			this.isLogin = uni.getStorageSync("userInfo") || ""
			this.getUserInfo();
			this.personType = uni.getStorageSync('personType')
			this.enterprice = uni.getStorageSync('enterprise')

			if (!this.isLogin || this.personType == 1 || this.personType == 3) {
				this.defaultId = this.userInfo ? this.userInfo.account_books_id : "";
				this.cuttnetId = this.userInfo ? this.userInfo.account_books_id : "";
				this.getList();
			} 
			this.getZiJinData();
			this.getGatheringData();
			

		}, 
		computed: {
			// ...mapState({
			// 	userInfo: state => state.user.userInfo,
			// 	enterprice: state => state.user.enterprise,
			// 	personType: state => state.user.personType,

			// })
		},
		methods: {
			getComLogo(url) {
				return img_url + url
				console.log(url, img_url)
			},

			getYingFuData() {
				this.$http("enterprise.dashboard.dashboard/accounts_payable?years=" + dayjs().year() + "&month=" +
					dayjs().month() + 1, {}, "get").then(res => {
					if (res.data.data) {
						this.yingFuData = res.data.data;
					}

				})
				
				// TODO:
				this.$http("enterprise.User_todo/getCount", {}, "get").then(res => {
					console.log(res)
					if (res.data.code == 1) {
						this.countData.liuyan = res.data.data.liuyan
					}
				})
			},
			getGatheringData() {
				let params = {
					years: dayjs().year(),
					month: dayjs().month() + 1
				}
				this.$http("enterprise.dashboard.dashboard/accounts_receivable", params, "get").then(res => {
					if (res.data.data) {
						this.gathering = res.data.data;
						console.log('2222', this.gathering)
					}

				})
			},
			getZiJinData() {
				let params = {
					years: dayjs().year(),
					month: dayjs().month() + 1
				}
				this.$http("enterprise.dashboard.dashboard/Money", params, "get").then(res => {
					if (res.data.data) {
						this.ziJinData = res.data.data;
					}

				})
			},
			goAccountBalanceSheet() {
				uni.navigateTo({
					url: "/pages/workbench/accountBalanceSheet/index"
				})
			},
			jyqk() {
				uni.navigateTo({
					url: "/pages/workbench/incomeStatement/index"
				})
			},
			getListData() {
				let vm = this
				this.$http("enterprise/Slide/slide", {}, "get").then(res => {
					if (res.data.code == 1) {
						if (res.data.data.images) {
							let listArr = res.data.data.images.split(",");
							vm.list = listArr.map(item => {
								return {
									image: item.indexOf("http") >= 0 ? item : `${vm.img_url}${item}`
								}
							})
						}
						console.log(this.list, "ASdddddddddddddddddddddd");
						// this.imageData = res.data.data.images ?  res.data.data.images.split(",") :[] 
					}
				})
			},
			goDaiBanDetail(item) {
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
			goSchedule() {
				uni.navigateTo({
					url: "/pages/workbench/schedule/list"
				})
			},
			getDaiBanList() { 
				let params = {
					page: 1,
					limit: 100,
					offset: (this.page - 1) * this.limit,
					type: 4 //待办列表
				}
				this.$http("enterprise.applyfor.Oa/index", params, "get").then(res => {
					if (res.data.code == 1) {
						 this.countData.daiban = res.data.data.rows.length
						 this.daiBanListData = res.data.data.rows || [];
					}
				})
			}, 
			getUserInfo() {
				this.$http("/User/getUser", {}, "post").then(res => {
					if (res.data.code == 1) {
						this.userInfo = res.data.data

						this.$store.dispatch("user/GET_USER_INFO", res.data.data);
						this.defaultId = this.userInfo ? this.userInfo.account_books_id : "";
						this.cuttnetId = this.userInfo ? this.userInfo.account_books_id : "";
            this.getList()
					}
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
						uni.$emit("changeUserInfo", true);

						this.getUserInfo()
						this.$nextTick(() => {
							this.getList();
							this.getListData();
						})
					}
				})
			},
			selectAccount(id) {
				this.cuttnetId = id;
			},
			getList() {
				console.log('开始了')
				let params = {
					offset: (this.page - 1) * 10,
					page: this.page,
					limit: this.limit
				}
				this.$http("enterprise.Account_books/index", params, "post").then(res => {
					if (res.data.code == 1) {
						this.listData = res.data.data.rows;
						if (this.userInfo.account_books_id) {
							this.accountText = this.listData.find(item => Number(item.id) === this.userInfo
								.account_books_id);
							this.accountText = this.accountText ? this.accountText.name : "请选择"
						} else {
							if (this.userInfo.is_admin == 'staff') {
								this.accountText = this.listData.find(item => Number(item.id) === this.userInfo
									.jobs.account_books_id);
								this.accountText = this.accountText ? this.accountText.name : "请选择"
							} else {
								this.accountText = "请选择";
							}
						}
					} else {
						this.listData = [];
						this.accountText = "请选择";
					}
				})
			},
			changeFinancialPopup(val) {
				this.isShowPopop = true;
			},

			jumpTo(val) {
				uni.navigateTo({
					url: `/pages/workbench/${val}/index`
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.badge {
		position: absolute;
		right: -18rpx;
		top: -18rpx;
		text-align: center;
		border-radius: 50%;
		height: 34rpx;
		min-width: 34rpx;
		line-height: 34rpx;
		font-size: 24rpx;
		color: #fff;
		background-color: #f56c6c;
		z-index: 999;
	}

	page {
		background: #FBFCFF !important;
	}

	.hr-div {
		height: 0px;
		border: 1px solid #EEF2FF;
		opacity: 1;
		margin-top: 34rpx;
	}

	.common-title {
		font-size: 34rpx;
		color: #150E33;
		font-weight: bold;
	}

	.header-div {
		display: flex;
		justify-content: space-between;
		margin-top: 32rpx;

		.header-div-l {
			display: flex;
			align-items: center;

			.header-div-l-img {
				width: 46rpx;
				height: 46rpx;
			}
		}
	}

	.headerBox {
		background: url(../../../static/image/tab1/headerBg.png) no-repeat;
		min-height: 478rpx;
		width: 750rpx;
		padding: 0 32rpx;
		box-sizing: border-box;
		background-size: 100% 100%;
		overflow: hidden;

		// padding-top: var(--status-bar-height);
		.unit-info {
			/* #ifdef APP-PLUS */
			margin-top: var(--status-bar-height);
			/* #endif */
			/* #ifndef APP-PLUS */
			margin-top: 31rpx;
			/* #endif */
			display: flex;
			align-items: center;

			.unit-icon {
				width: 70rpx;
				height: 70rpx;
				border-radius: 50%;

			}

			.unit-title {
				margin-left: 20rpx;
				font-weight: 700;
				font-size: 40rpx;
				color: #FFFFFF;
			}
		}

		.bannerBox {
			margin-top: 42rpx;
		}
	}

	.menuBox {
		margin-top: 30rpx;
		background-color: #fff;

		.jiaob {
			background-color: red;
			padding: 2rpx 10rpx;
			color: #fff;
			border-radius: 50%;
			position: absolute;
			top: 10rpx;
			right: 42rpx;
		}

		.grid-text {
			color: #150E33;
			font-size: 26rpx;
			margin-top: 22rpx;
		}
	}

	.chargeBox {
		padding: 0 32rpx;
		margin-top: 20rpx;
		overflow: hidden;

		.chargeMain {
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

	.statementBox {
		padding: 0 32rpx;
		margin-top: 20rpx;

		.statementMain {
			padding: 32rpx 24rpx;
			background: #fff;
			box-sizing: border-box;
			border-radius: 24rpx;

			.grid-text {
				color: #150E33;
				font-size: 26rpx;
				margin-top: 22rpx;
			}
		}
	}

	.indicatorBox {
		padding: 0 32rpx;

		.indicatorMain {
			background: #fff;
			box-sizing: border-box;
			border-radius: 24rpx;
			padding: 0 24rpx;
			overflow: hidden;


			.indicator-m {
				display: flex;
				margin-top: 50rpx;

				.indicator-m-l {
					width: 300rpx;
					height: 300rpx;
				}

				.indicator-m-r {
					padding: 50rpx 0;
					margin-left: 140rpx;
					display: flex;
					flex-direction: column;
					justify-content: space-around;
					font-size: 36rpx;
					color: #150E33;
				}
			}


		}
	}

	.gatheringBox {
		padding: 0 32rpx;
		margin-top: 24rpx;

		.gatheringMain {
			background: #fff;
			box-sizing: border-box;
			border-radius: 24rpx;
			padding: 0 24rpx;
			overflow: hidden;

			.gathering-m {
				display: flex;
				justify-content: space-around;
				margin-top: 60rpx;
				margin-bottom: 40rpx;

				.gathering-m-item {
					display: flex;
					flex-direction: column;
					align-items: center;

					.gathering-m-item-hd {
						color: #150E33;
						font-size: 42rpx;
					}

					.gathering-m-item-bd {
						color: #7A7C94;
						margin-top: 20rpx;
					}
				}
			}

		}
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
<template>
	<view>
		<u-navbar :is-back="true" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" title="科目余额表" :height="55">
		</u-navbar>
		<view class="header-div" @click="openPopup">
			<text
				class="date-text">{{formData.years}}年{{formData.month}}期-{{formData.to_year}}年{{formData.to_month}}期</text>
			<u-icon name="arrow-down-fill" class="header-icon" size="22"></u-icon>
		</view>

		<u-tabs-swiper style="margin: 30rpx 0;" bar-height="4" bar-width="95" ref="tabs" :font-size="32"
			active-color="#4396F7" :active-item-style="{'color':'#4396F7'}" :current="swiperCurrent"
			@change="tabsChange" :list="tabList" :is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>


		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" :key="index">
				<view class="main-item-header" @click="geiminhxi(item.kemu_id)">
					<text>{{item.years}}</text>
					<text>{{item.subject_headings}}</text>
					<u-icon name="arrow-right" color="#B5BFDA"></u-icon>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-n">

					</view>
					<view class="main-item-m-n green">
						借
					</view>
					<view class="main-item-m-n pink">
						贷
					</view>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-n">
						期初
					</view>
					<view class="main-item-m-n">
						{{item.borrow_early_period_balance > 0 ? item.borrow_early_period_balance : 0}}
					</view>
					<view class="main-item-m-n">
						{{item.borrow_early_period_balance < 0 ? Math.abs(item.borrow_early_period_balance) : 0}}
					</view>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-n">
						本期
					</view>
					<view class="main-item-m-n ">
						{{item.sum_borrower}}
					</view>
					<view class="main-item-m-n ">
						{{Math.abs(item.sum_lender)}}
					</view>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-n">
						期末
					</view>
					<view class="main-item-m-n">
						{{item.borrow_end_period_balance > 0 ? item.borrow_end_period_balance:0}}
					</view>
					<view class="main-item-m-n ">
						{{item.borrow_end_period_balance < 0 ? Math.abs(item.borrow_end_period_balance):0}}
					</view>
				</view>
			</view>

			<!-- 统计 -->
			<view class="main-item">
				<view class="main-item-header text-bold">
					<text>合计</text>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-n">

					</view>
					<view class="main-item-m-n green">
						借
					</view>
					<view class="main-item-m-n pink">
						贷
					</view>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-n">
						期初
					</view>
					<view class="main-item-m-n ">
						{{totalData.borrow_early_period_balance}}
					</view>
					<view class="main-item-m-n ">
						{{totalData.loan_early_period_balancece}}
					</view>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-n">
						本期
					</view>
					<view class="main-item-m-n ">
						{{totalData.borrow_self_period_balance}}
					</view>
					<view class="main-item-m-n ">
						{{totalData.loan_self_period_balancece}}
					</view>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-n">
						期末
					</view>
					<view class="main-item-m-n ">
						{{totalData.borrow_end_period_balance}}
					</view>
					<view class="main-item-m-n ">
						{{totalData.loan_end_period_balancece}}
					</view>
				</view>
			</view>
		</view>

		<u-popup v-model="isShowPopup" mode="bottom" border-radius="56">
			<view class="popup-main">
				<view class="popup-common-title">
					— 选择期间 —
				</view>
				<view class="popup-box">
					<view class="popup-title">
						2021
					</view>
					<view class="popup-bottom">
						<view class="popup-bottom-item" @click="selectDate(index)"
							:class="{active:month==index+1 || to_month==index+1,disable:monthArr.indexOf(item)<0}" v-for="(item,index) in seasonList"
							:key="index">
							{{item}}期
						</view>
					</view>
				</view>

				<view class="popup-common-btnGroup">
					<view class="popup-common-btn" @click="isShowPopup = false">
						取消
					</view>
					<view class="popup-common-btn active" @click="sureBtn">
						确定
					</view>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import dayjs from "@/utils/dayjs.min.js";
	export default {
		data() {
			return {
				monthArr:[],
				month: "",
				to_month: "",
				isShowPopup: false,
				listData: [],
				background: {
					backgroundColor: "#FFFFFF",
				},
				seasonList: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
				swiperCurrent: 0,
				barStyle: {
					textAlign: "center",
				},
				formData: {
					years: "",
					month: "",
					level: 0,
					to_year: "",
					to_month: ""
				},
				totalData: {},
				tabList: [{
						name: "全部"
					},
					{
						name: "一级"
					},
				]
			};
		},
		onLoad() {
			this.formData.years = dayjs().year();
			this.formData.to_year = dayjs().year();
			this.getday();
		},
		methods: {
			//跳转详情
			geiminhxi(id){
				uni.navigateTo({
					url:"/pages/workbench/detailAccount/list?id="+id
				})
				// uni.navigateTo({
				// 	url:'../detailAccount/index'
				// })
			},
			getday() {
				this.$http("enterprise.date_query/getQueryTime", "get").then(res => {
					if (res.data.code == 1) {
						let list = res.data.data;
						let currentData = list.find(item => {
							return item.years === this.formData.years
						})
						currentData = currentData ? currentData.month : [];
						if (currentData.length > 0 && currentData.length) {
							this.formData.month = currentData[0].month
							this.formData.to_month = currentData[0].month
						} else {
							this.formData.to_month = dayjs().month()+1;
							this.formData.month = dayjs().month()+1;
						}
						this.monthArr = currentData.map(item => {
							return this.formatNum(item.month)
						}).join(",")
						this.getListData();
					}
				})
			},
			formatNum(num) {
				let value = "";
				if (Number(num) < 10) {
					value = `0${num}`
				} else {
					value = num;
				}
				return value
			},
			sureBtn() {
				if (this.to_month > this.month) {
					this.formData.to_month = this.to_month;
					this.formData.month = this.month
				} else {
					this.formData.to_month = this.month;
					this.formData.month = this.to_month
				}
				this.isShowPopup = false;
				this.getListData();
			},
			selectDate(index) {
				if (this.monthArr.indexOf(this.seasonList[index]) >= 0) {
					let month = index + 1;
					if (this.month && !this.to_month) {
						this.to_month = month;
					} else if (this.to_month && !this.month) {
						this.month = month;
					} else if (this.to_month && this.month) {
						this.month = month;
						this.to_month = "";
					} else {
						this.month = month;
					}
				}
			},
			openPopup() {
				this.isShowPopup = true;
			},
			getListData() {
				if (this.formData.month == '') {
					uni.showToast({
						title: "请选择您要查询的期间",
						icon: "none"
					})
					return
				}
				this.$http("enterprise.subject_balance/SubjectsIndex", this.formData, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = res.data.data.rows;
						console.log(this.listData,">>>>>>>>>>>>")
						this.totalData = res.data.data.totalData;
					}
				})
			},
			tabsChange(index) {
				this.swiperCurrent = index;
				this.formData.level = index;
				this.getListData();
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: #FBFCFF;
		color: #150E33;
	}
	.disable {
		color: #dfdfdf !important;
	}

	.header-div {
		display: flex;
		align-items: center;
		height: 94rpx;
		justify-content: center;
		background-color: #fff;

		.date-text {
			font-size: 30rpx;
		}

		.header-icon {
			margin-left: 8rpx;
		}
	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			background-color: #fff;
			border-radius: 20rpx;
			padding: 32rpx 24rpx;
			margin-top: 24rpx;

			.main-item-header {
				display: flex;
				font-size: 32rpx;
				justify-content: space-between;
				align-items: center;

			}

			.main-item-m {
				display: flex;
				margin-top: 32rpx;

				.main-item-m-n {
					width: 212rpx;
					height: 60rpx;
					font-size: 28rpx;
					line-height: 60rpx;
					text-align: center;
				}
			}
		}
	}

	.green {
		background: rgba(18, 213, 146, .3);
	}

	.pink {
		background: rgba(255, 37, 61, .3);

	}

	.popup-main {
		.popup-box {
			.popup-title {
				font-weight: bold;
				font-size: 36rpx;
			}

			padding: 32rpx 40rpx;
		}

		.popup-bottom {
			display: flex;
			flex-wrap: wrap;
			margin-right: -34rpx;
			width: 100%;
			margin-top: 24rpx;

			.popup-bottom-item {
				margin-bottom: 12rpx;
				color: #7A7C94;
				font-size: 34rpx;
				width: 88rpx;
				height: 88rpx;
				margin-right: 22rpx;
				text-align: center;
				line-height: 88rpx;
			}

		}

		.active {
			background-color: #4396F7;
			color: #fff;
			border-radius: 50%;
		}

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

		// 	.financial-popup-main {
		// 		padding: 0 32rpx;

		// 		.financial-popup-item {
		// 			margin-top: 32rpx;
		// 			display: flex;
		// 			flex-direction: column;
		// 			border-bottom: 1px solid #EEF2FF;
		// 			padding-bottom: 32rpx;

		// 			.financial-popup-item-hd {
		// 				display: flex;
		// 				align-items: center;

		// 				.financial-popup-hd-status {
		// 					background: #FF253D;
		// 					border-radius: 48rpx;
		// 					padding: 4rpx 8rpx;
		// 					color: #fff;
		// 					font-size: 24rpx;
		// 					margin-left: 16rpx;
		// 				}

		// 				.financial-popup-hd-txt {
		// 					font-size: 32rpx;
		// 					color: #150E33;
		// 					font-weight: bold;
		// 				}
		// 			}

		// 			.financial-popup-item-bd {
		// 				color: #7A7C94;
		// 				font-size: 28rpx;
		// 				margin-top: 30rpx;
		// 			}
		// 		}
		// 	}
	}
</style>

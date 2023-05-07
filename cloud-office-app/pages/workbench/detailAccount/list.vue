<template>
	<view>
		<u-navbar :is-back="true" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" title="明细账详情" :height="55">
		</u-navbar>
		<view class="header-div" @click="openPopup">
			<text
				class="date-text">{{formData.years}}年{{formData.month}}期-{{formData.to_years}}年{{formData.to_month}}期</text>
			<u-icon name="arrow-down-fill" class="header-icon" size="22"></u-icon>
		</view>


		<view class="mainBox">
			<view class="main-itema" v-for="(item,index) in listData" :key="index">
				<view class="view-top" v-if="item.credentials_id" @click="getmaytime(item.credentials_id)">
					<view class="view-time">
						{{item.createtime}}
					</view>
					<u-icon name="arrow-right" color="#B5BFDA" ></u-icon>
				</view>
				<view class="main-item">
					<view class="main-item-l">
						{{item.abstract}}
					</view>
					<view class="main-item-r">
						<view class="main-item-r-line">
							<view class="main-item-r-line-l">
								借方
							</view>
							<view class="main-item-r-line-r">
								{{item.borrower}}
							</view>
						</view>
						<view class="main-item-r-line">
							<view class="main-item-r-line-l">
								贷方
					
							</view>
							<view class="main-item-r-line-r">
								{{Math.abs(item.lender)}}
							</view>
						</view>
						<view class="main-item-r-line">
							<!-- <view class="main-item-r-line-l">
								余额 <view class="main-item-one-m" :class="{blue:item.lender===item.borrower,red:item.lender!==item.borrower}">
								{{item.lender===item.borrower ? "平" : "贷"}}
								</view>
							</view> -->
							<view class="main-item-r-line-l" v-if="Math.abs(item.balance) == 0">
								余额 <view class="main-item-one-m" :class="{blue : Math.abs(item.balance) == 0}">
								平
								</view>
							</view>
							<view class="main-item-r-line-l" v-else>
								余额 <view class="main-item-one-m" :class="{blue:item.balance > 0,red:item.balance < 0}">
								{{item.balance > 0 ? "借" : "贷"}}
								</view>
							</view>
							<view class="main-item-r-line-r">
								{{Math.abs(item.balance)}}
							</view>
						</view>
					</view>
				</view>
			</view>

<!-- 			<view class="common-title">
				2021年08期
			</view>
			<view class="main-header-div">
				<view class="main-header-div-l">
					2021-08-12
				</view>
				<u-icon name="arrow-right" color="#7A7C94"></u-icon>
			</view>
			<view class="main-item" style="margin: 0;">
				<view class="main-item-l">
					收入
				</view>
				<view class="main-item-r">
					<view class="main-item-r-line">
						<view class="main-item-r-line-l">
							借方
						</view>
						<view class="main-item-r-line-r">
							0.00
						</view>
					</view>
					<view class="main-item-r-line">
						<view class="main-item-r-line-l">
							余额 <view class="main-item-one-m red"> 贷

							</view>
						</view>
						<view class="main-item-r-line-r">
							0.00
						</view>
					</view>
				</view>
			</view>

			<view class="main-item">
				<view class="main-item-l">
					本期合计
				</view>
				<view class="main-item-r">
					<view class="main-item-r-line">
						<view class="main-item-r-line-l">
							借方
						</view>
						<view class="main-item-r-line-r">
							0.00
						</view>
					</view>
					<view class="main-item-r-line">
						<view class="main-item-r-line-l">
							贷方
						</view>
						<view class="main-item-r-line-r">
							0.00
						</view>
					</view>
					<view class="main-item-r-line">
						<view class="main-item-r-line-l">
							余额 <view class="main-item-one-m red"> 贷

							</view>
						</view>
						<view class="main-item-r-line-r">
							0.00
						</view>
					</view>
				</view>
			</view>

			<view class="main-item">
				<view class="main-item-l">
					本年累计
				</view>
				<view class="main-item-r">
					<view class="main-item-r-line">
						<view class="main-item-r-line-l">
							借方
						</view>
						<view class="main-item-r-line-r">
							0.00
						</view>
					</view>
					<view class="main-item-r-line">
						<view class="main-item-r-line-l">
							贷方
						</view>
						<view class="main-item-r-line-r">
							0.00
						</view>
					</view>
					<view class="main-item-r-line">
						<view class="main-item-r-line-l">
							余额 <view class="main-item-one-m red"> 贷

							</view>
						</view>
						<view class="main-item-r-line-r">
							0.00
						</view>
					</view>
				</view>
			</view>
		 -->
		
		
		
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
							:class="{active:month==index+1 || to_month==index+1,disable:(monthArr + '').indexOf(item)<0}"
							v-for="(item,index) in seasonList" :key="index">
							{{item}}
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
				month: "",
				to_month: "",
				isShowPopup: false,
				listData: [],
				monthArr:'',//
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
					km_id: 0,
					to_years: "",
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
		onLoad(params) {
			this.formData.km_id = params.id;
			this.formData.years = dayjs().year();
			this.formData.to_years = dayjs().year();
			this.getday() //获取日期
		},
		methods: {
			getmaytime(id){
				uni.navigateTo({
					url:'../voucher/detail?id=' + id
				})
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
				this.$http("enterprise.subject_balance/dateils", this.formData, "get").then(res => {
					if (res.data.code == 1) {
						this.listData  =res.data.data.credentils;
						// this.listData = res.data.data.rows;
						// this.totalData = res.data.data.totalData;
					}
				})
			},
			tabsChange(index) {
				this.swiperCurrent = index;
				// this.formData.level = index;
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

	.main-header-div {
		display: flex;
		justify-content: space-between;
		color: #7A7C94;
		font-size: 30rpx;
		padding: 32rpx 0;
		background: #fff;
		border-bottom: 1px solid #EEF2FF;
		border-radius: 16rpx 16rpx 0 0;
	}

	.common-title {
		font-size: 36rpx;
		font-weight: bold;
		margin: 32rpx 0;
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
		font-size: 32rpx;
		padding: 0 32rpx;
		.main-itema{
			margin-top: 32rpx;
			padding: 32rpx 24rpx;
			background-color: #fff;
			border-radius: 16rpx;
			.view-top{
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding: 0 0 30rpx 0;
				
			}
		}
		.main-item {
			display: flex;
			// margin-top: 32rpx;
			padding: 10rpx 24rpx;
			// background-color: #fff;
			// border-radius: 16rpx;

			.main-item-l {
				width: 240rpx;
				font-weight: bold;
			}

			.main-item-r {
				flex: 1;

				.main-item-r-line {
					margin-bottom: 32rpx;
					display: flex;
					align-items: center;
					justify-content: space-between;

					.main-item-r-line-l {
						display: flex;
						width: 250rpx;
						align-items: center;
					}
				}
			}
		}

	}

	.main-item-one-m {
		font-size: 28rpx;
		width: 46rpx;
		height: 46rpx;
		color: #fff;
		background: #FF253D;
		border-radius: 50%;
		text-align: center;
		line-height: 45rpx;
		margin-left: 20rpx;
	}

	.blue {
		background: #4396F7;
	}

	.red {
		background: #FF253D;

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

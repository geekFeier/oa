<template>
	<view>
		<u-navbar :is-back="true" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" title="利润表" :height="55">
		</u-navbar>
		<view class="header-div" @click="openPopup" v-if="swiperCurrent==0">
			<text class="date-text">{{formData.years}}年{{formData.num}}期-{{formData.to_year}}年{{formData.num}}期</text>
			<u-icon name="arrow-down-fill" class="header-icon" size="22"></u-icon>
		</view>
		<view class="header-div" @click="openPopup2" v-if="swiperCurrent==1">
			<text class="date-text">第{{formData.num}}季</text>
			<u-icon name="arrow-down-fill" class="header-icon" size="22"></u-icon>
		</view>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="32" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" :current="swiperCurrent" @change="tabsChange" :list="tabList"
			:is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>


		<!-- 月度 -->
		<view class="mainBox" >
			<table border="1" style="border-collapse:collapse;margin-top: 40rpx;" class="tableBox">
				<tr style="background: #F6F7FB;color: #333333;font-size: 28rpx;">
					<th style="width: 265rpx;">项目</th>
					<th>本年累计金额</th>
					<th>本期金额</th>
				</tr>
				<block v-for="(item,index) in listData" :key="index">
					<tr class="trItem" style="font-size: 32rpx;">
						<td style="width: 265rpx;font-size: 28rpx;" @click="watchname(item.id)">{{item.name}}
						<text style="font-size: 14rpx;color: #0081FF;">编辑公式</text>
						</td>
					<!-- <td style="width: 265rpx;">{{item.name}}</td> -->
					<td>{{Math.abs(item.current_years_total)}}</td>
					<td>{{Math.abs(item.current_months_total)}}</td>
				</tr>
				<tr class="trItem" v-for="(items,inde) in item.children" :key="inde">
					<td style="width: 265rpx;font-size: 28rpx;" @click="watchname(items.id)">{{items.name}}
					<text style="font-size: 14rpx;color: #0081FF;">编辑公式</text>
					</td>
					
					<td>{{items.current_years_total ? Math.abs(items.current_years_total) : 0}}</td>
					<td>{{items.current_months_total ? Math.abs(items.current_months_total) : 0}}</td>
				</tr>
				</block>
			</table>
		</view>
		<u-popup v-model="isShowPopup" mode="bottom" border-radius="56">
			<view class="popup-main">
				<view class="popup-common-title">
					— 选择期间 —
				</view>
				<view class="popup-box">
					<view class="popup-title">
						{{formData.years}}
					</view>
					<view class="popup-bottom">
						<view class="popup-bottom-item" @click="selectDate(index)"
							:class="{active:num==index+1,disable:monthArr.indexOf(item)<0}"
							v-for="(item,index) in seasonList" :key="index">
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


		<!-- 选择季度 -->
		<u-popup v-model="isShowPopup2" mode="bottom" border-radius="56">
			<view class="popup-main">
				<view class="popup-common-title">
					— 选择季度 —
				</view>
				<view class="popup-box">
					<view class="popup-title">
						2021
					</view>
					<view class="popup-bottom justify-around">
						<view class="popup-bottom-item" style="width: 110rpx;height: 110rpx;"
							@click="selectDate2(index)" :class="{active:num==index+1}"
							v-for="(item,index) in saisonList" :key="index">
							{{item}}
						</view>
					</view>
				</view>

				<view class="popup-common-btnGroup">
					<view class="popup-common-btn" @click="isShowPopup2 = false">
						取消
					</view>
					<view class="popup-common-btn active" @click="sureBtn2">
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
				monthArr: "",
				num: "",
				currentActive: "",
				listData: [], //数据
				table1: [{
						name: "减：营业成本",
						pice1: "30,000.00",
						pice2: "30,000.00",
					},
					{
						name: "税金及附加",
						pice1: "",
						pice2: "",
					},
					{
						name: "销售费用",
						pice1: "",
						pice2: "",
					},
					{
						name: "管理费用",
						pice1: "10,000.00",
						pice2: "10,000.00",
					},
					{
						name: "财务费用",
						pice1: "",
						pice2: "",
					}, {
						name: "支付的税费",
						pice1: "",
						pice2: "",
					},
					{
						name: "加：投资收益",
						pice1: "",
						pice2: "",
					}
				],
				table2: [{
					name: "加：营业外收入",
					pice1: "",
					pice2: "",
				}, {
					name: "减：营业外支出",
					pice1: "",
					pice2: "",
				}],
				table3: [{
					name: "减：所得税费用",
					pice1: "",
					pice2: "",
				}],
				table4: [{
					name: "加：期初现金余额",
					pice1: "",
					pice2: "",
				}],
				swiperCurrent: 0,
				tabList: [{
						name: "月度"
					},
					{
						name: "季度"
					},
				],
				barStyle: {
					textAlign: "center",
				},
				to_month: "",
				isShowPopup: false,
				isShowPopup2: false,
				month: "",
				background: {
					backgroundColor: "#FFFFFF",
				},

				/* 	期间 */
				seasonList: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
				/* 季节 */
				saisonList: ["第一季", "第二季", "第三季", "第四季"],
				formData: {
					years: "",
					num: "",
					type: 2
					// month: "",
					// level: 0,
					// to_year: "",
					// to_month: ""
				},
			};
		},
		onLoad() {
			this.formData.years = dayjs().year();
			this.formData.to_year = dayjs().year();
			this.getday() //获取日期
		},
		methods: {
			watchname(i){
				uni.navigateTo({
					url:'../../my/lrblist?id=' + i
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
					if(currentData.length>0 &&  currentData.length){
						this.formData.num = currentData[0].month
					}else{
						this.formData.num = 1;
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
			sureBtn2() {
				this.formData.num = this.num;
				this.isShowPopup2 = false;
				this.getListData()
			},
			selectDate2(index) {
				this.num = index + 1;
			},
			openPopup2() {
				this.isShowPopup2 = true;
			},
			tabsChange(index) {
				this.swiperCurrent = index;
				if (index == 0) {
					this.formData.type = 2;
					this.getday();
				} else {
					this.formData.num = 1;
					this.formData.type = 1;
				}
				this.getListData();
			},
			sureBtn() {
				// if (this.to_month > this.month) {
				// 	this.formData.to_month = this.to_month;
				// 	this.formData.month = this.month
				// } else {
				// 	this.formData.to_month = this.month;
				// 	this.formData.month = this.to_month
				// }
				this.formData.num = this.num;
				this.isShowPopup = false;
				this.getListData();
			},
			getListData() {
				if (this.formData.month == '') {
					uni.showToast({
						title: "请选择您要查询的期间",
						icon: "none"
					})
					return
				}
				this.$http("enterprise.report/income_statement", this.formData, "get").then(res => {
					if (res.data.code == 1) {
						res.data.data.SystemCalcCate[0].children[5]['current_months_total'] = Math.abs(res.data.data.SystemCalcCate[0].children[1].current_months_total) + Math.abs(res.data.data.SystemCalcCate[0].children[2].current_months_total) + Math.abs(res.data.data.SystemCalcCate[0].children[3].current_months_total) + Math.abs(res.data.data.SystemCalcCate[0].children[4].current_months_total)
						res.data.data.SystemCalcCate[0].children[5]['current_years_total'] = Math.abs(res.data.data.SystemCalcCate[0].children[1].current_years_total) + Math.abs(res.data.data.SystemCalcCate[0].children[2].current_years_total) + Math.abs(res.data.data.SystemCalcCate[0].children[3].current_years_total) + Math.abs(res.data.data.SystemCalcCate[0].children[4].current_years_total)
						res.data.data.SystemCalcCate[1].current_months_total = Math.abs(res.data.data.SystemCalcCate[0].current_months_total) - Math.abs(res.data.data.SystemCalcCate[0].children[0].current_months_total) - Math.abs(res.data.data.SystemCalcCate[0].children[1].current_months_total) - Math.abs(res.data.data.SystemCalcCate[0].children[2].current_months_total) - Math.abs(res.data.data.SystemCalcCate[0].children[3].current_months_total) - Math.abs(res.data.data.SystemCalcCate[0].children[4].current_months_total) + Math.abs(res.data.data.SystemCalcCate[0].children[5]['current_months_total'])
						res.data.data.SystemCalcCate[1].current_years_total = Math.abs(res.data.data.SystemCalcCate[0].current_years_total) - Math.abs(res.data.data.SystemCalcCate[0].children[0].current_years_total) - Math.abs(res.data.data.SystemCalcCate[0].children[1].current_years_total) - Math.abs(res.data.data.SystemCalcCate[0].children[2].current_years_total) - Math.abs(res.data.data.SystemCalcCate[0].children[3].current_years_total) - Math.abs(res.data.data.SystemCalcCate[0].children[4].current_years_total) + Math.abs(res.data.data.SystemCalcCate[0].children[5]['current_years_total'])
						res.data.data.SystemCalcCate[2].current_months_total = res.data.data.SystemCalcCate[1].current_months_total + Math.abs(res.data.data.SystemCalcCate[1].children[0].current_months_total) - Math.abs(res.data.data.SystemCalcCate[1].children[1].current_months_total)
						res.data.data.SystemCalcCate[2].current_years_total = res.data.data.SystemCalcCate[1].current_years_total + Math.abs(res.data.data.SystemCalcCate[1].children[0].current_years_total) - Math.abs(res.data.data.SystemCalcCate[1].children[1].current_years_total)
						res.data.data.SystemCalcCate[3].current_months_total = res.data.data.SystemCalcCate[2].current_months_total - Math.abs(res.data.data.SystemCalcCate[2].children[0].current_months_total)
						res.data.data.SystemCalcCate[3].current_years_total = res.data.data.SystemCalcCate[2].current_years_total - Math.abs(res.data.data.SystemCalcCate[2].children[0].current_years_total)
						this.listData = res.data.data.SystemCalcCate;
						
					}
				})
			},
			openPopup() {
				this.isShowPopup = true;
			},
			selectDate(index) {
				if (this.monthArr.indexOf(this.seasonList[index]) >= 0) {
					let month = index + 1;
					this.num = month;
					// if (this.month && !this.to_month) {
					// 	this.to_month = month;
					// } else if (this.to_month && !this.month) {
					// 	this.month = month;
					// } else if (this.to_month && this.month) {
					// 	this.month = month;
					// 	this.to_month = "";
					// } else {
					// 	this.month = month;
					// }
				}

			},
		}
	}
</script>

<style lang="scss" scoped>
	.common-title {
		color: #150E33;
		font-size: 32rpx;
		font-weight: bold;
		margin-top: 32rpx;
		margin-bottom: 24rpx;
	}

	.disable {
		color: #dfdfdf !important;
	}

	.mainBox {
		padding: 0 32rpx;
	}

	.tableBox {
		width: 100%;
		border: 1px solid #EFEFEF;

		tr {
			height: 70rpx;
			text-align: center;
		}

		.trItem {
			color: #150E33;
			font-size: 28rpx;

			td {
				text-align: left;
				padding: 24rpx 32rpx;
			}
		}
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
				font-size: 32rpx;
				width: 88rpx;
				height: 88rpx;
				margin-right: 22rpx;
				display: flex;
				align-items: center;
				justify-content: center;
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

	}
</style>

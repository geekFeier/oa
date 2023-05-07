<template>
	<view>
		<u-navbar :is-back="true" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" title="现金流量表" :height="55">
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

		<!-- <view class="mainBox">
			<table border="1" style="border-collapse:collapse;" class="tableBox">
				<tr style="background: #F6F7FB;color: #333333;font-size: 28rpx;">
					<th style="width: 300rpx;">项目</th>
					<th style="">期末金额</th>
					<th style="">年初金额</th>
				</tr>
				<block v-for="(item,index) in listData" :key="index">
					<tr class="trItem" >
						<td style="font-size: 28rpx;" colspan="3">{{item.name}}</td>
					
					</tr>
					<tr class="trItem" v-for="(items,inde) in item.children" :key="inde">
					<td style="font-size: 28rpx;">{{items.name}}</td>
					<td style="">{{items.qm_balance ? items.qm_balance : 0}}</td>
					<td style="">{{items.nc_balance ? items.nc_balance : 0}}</td>
					
				</tr>
				</block>
				</table>
			</view> -->
		<!-- 月度 -->
		<view class="mainBox">
			<table border="1" style="border-collapse:collapse;margin-top: 40rpx;" class="tableBox">
				<tr style="background: #F6F7FB;color: #333333;font-size: 28rpx;">
					<th style="width: 265rpx;">项目</th>
					<th>本年累计金额</th>
					<th>本期金额</th>
				</tr>
				<block v-for="(item,index) in listData" :key="item.id">
					<tr class="trItem">
					<td style="width: 265rpx;font-size: 32rpx;" colspan="3">{{item.name}}</td>
					<!-- <td></td>
					<td></td> -->
				</tr>
				<tr class="trItem" v-for="(items,indexa) in item.children" :key="indexa">
					<td style="width: 265rpx;font-size: 28rpx;">{{items.name}}</td>
					<td>{{items.current_years_total ? items.current_years_total : 0}}</td>
					<td>{{items.current_months_total ? items.current_months_total : 0}}</td>
				</tr>
				</block>
				
			</table>

			<!-- <view class="common-title">
				二、投资活动产生的现金流量
			</view>
			<table border="1" style="border-collapse:collapse;margin-top: 40rpx;" class="tableBox">

				<tr class="trItem" v-for="(item,index) in table2" :key="index">
					<td style="width: 265rpx;font-size: 28rpx;">{{item.name}}</td>
					<td>{{item.pice1 ? item.pice1 : ""}}</td>
					<td>{{item.pice2 ? item.pice2 : ""}}</td>
				</tr>
			</table> -->

			<!-- <view class="common-title">
				三、筹集活动产生的现金流量
			</view>
			<table border="1" style="border-collapse:collapse;margin-top: 40rpx;" class="tableBox">

				<tr class="trItem" v-for="(item,index) in table3" :key="index">
					<td style="width: 265rpx;font-size: 28rpx;">{{item.name}}</td>
					<td>{{item.pice1 ? item.pice1 : ""}}</td>
					<td>{{item.pice2 ? item.pice2 : ""}}</td>
				</tr>
			</table> -->

			<!-- <view class="common-title">
				四、现金净增加额
			</view>
			<table border="1" style="border-collapse:collapse;margin-top: 40rpx;" class="tableBox">
			
				<tr class="trItem" v-for="(item,index) in table4" :key="index">
					<td style="width: 265rpx;font-size: 28rpx;">{{item.name}}</td>
					<td>{{item.pice1 ? item.pice1 : ""}}</td>
					<td>{{item.pice2 ? item.pice2 : ""}}</td>
				</tr>
			</table>
			<view class="common-title">
				五、期末现金余额
			</view> -->
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
							{{item}}期
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
				isShowPopup2:false,
				listData: [], //shuju
				table1: [{
						name: "销售产品成品、商 品、提供劳务收到 的现金",
						pice1: "30,000.00",
						pice2: "30,000.00",
					},
					{
						name: "收到其他与经营活动有关的现金 ",
						pice1: "30,000.00",
						pice2: "30,000.00",
					},
					{
						name: "购买原材料、商品 接受劳务支付的现 金",
						pice1: "30,000.00",
						pice2: "30,000.00",
					},
					{
						name: "销售产品成品、商 品、提供劳务收到 的现金",
						pice1: "",
						pice2: "",
					},
					{
						name: "支付的职工薪酬",
						pice1: "",
						pice2: "",
					}, {
						name: "支付的税费",
						pice1: "",
						pice2: "",
					},
					{
						name: "支付其他与经营活 动有关的现金",
						pice1: "",
						pice2: "",
					}, {
						name: "经营活动产生的现 金流量净额",
						pice1: "",
						pice2: "",
					},
				],
				table2: [{
						name: "收回短期投资、长 期债券投资和长期 股权投资收到的现金",
						pice1: "",
						pice2: "",
					}, {
						name: "取得投资收益收到 的现金",
						pice1: "",
						pice2: "",
					}, {
						name: "处置固定资产、无 形资产和其他非流 动资产收回的现金 净额",
						pice1: "",
						pice2: "",
					}, {
						name: "短期投资、长期债 券投资和长期股份 投资支付的现金",
						pice1: "",
						pice2: "",
					},
					{
						name: "构建固定资产、无 形资产和其他非流 动资产支付的现金",
						pice1: "",
						pice2: "",
					}, {
						name: "投资活动产生的现 金流量净额",
						pice1: "",
						pice2: "",
					},
				],
				table3: [{
						name: "取得借款收到的现金",
						pice1: "",
						pice2: "",
					}, {
						name: "吸收投资者投资收到的现金",
						pice1: "",
						pice2: "",
					},
					{
						name: "偿还借款本金支付的现金到的现金 ",
						pice1: "-100.00",
						pice2: "-100.00",
					},
					{
						name: "偿还借款利息支付的现金到的现金 ",
						pice1: "",
						pice2: "",
					}, {
						name: "分配利润支付的现金到的现金",
						pice1: "",
						pice2: "",
					}, {
						name: "筹集活动产生的现金流量净额到的现金",
						pice1: "-100.00",
						pice2: "-100.00",
					},
				],
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
				month: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
				seasonList: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
				/* 季节 */
				saisonList: ["第一季", "第二季", "第三季", "第四季"],
				formData: {
					years: "",
					num: 8,
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
			// this.formData.month = dayjs().month() + 1;
			// this.formData.to_year = dayjs().year();
			// this.formData.to_month = dayjs().month() + 1;
			this.getday() //获取日期
		},
		methods: {
			watchname(i){
				uni.navigateTo({
					url:'../../my/xllist?id=' + i
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
							this.formData.num = dayjs().month()+1;
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
				this.$http("enterprise.report/cash_flow_statement", this.formData, "get").then(res => {
					if (res.data.code == 1) {
						console.log(res.data.data, "?????????????")
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

	}
</style>

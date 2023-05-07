<template>
	<view>
		<u-navbar :is-back="true" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" title="资产负债表" :height="55">
		</u-navbar>
		<view class="header-div" @click="openPopup">
			<text class="date-text">{{formData.years}}年{{formData.num}}期-{{formData.to_year}}年{{formData.num}}期</text>
			<u-icon name="arrow-down-fill" class="header-icon" size="22"></u-icon>
		</view>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="32" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" :current="swiperCurrent" @change="tabsChange" :list="tabList"
			:is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>
		<view class="mainBox">
			<table border="1" style="border-collapse:collapse;" class="tableBox">
				<tr style="background: #F6F7FB;color: #333333;font-size: 28rpx;">
					<th style="width: 320rpx;">项目</th>
					<th style="">期末金额</th>
					<th style="">年初金额</th>
				</tr>
				<block v-for="(item,index) in listData" :key="item.id">
					<tr class="trItem">
						<block v-if="item.name == '资产合计' || item.name == '负债合计' || item.name == '负债和所有者权益（或股东权益）'"><td style="font-size: 28rpx;" colspan="">{{item.name}}</td></block>
						<block v-else><td style="font-size: 28rpx;" colspan="3">{{item.name}}</td></block>
						<block v-if="item.name == '资产合计' || item.name == '负债合计' || item.name == '负债和所有者权益（或股东权益）'">
							<td style="">{{item.qm_balance}}</td>
							<td style="">{{item.nc_balance}}</td>
						</block>
					</tr>
					<block>
							<tr class="trItem" v-for="(items,inde) in item.children" :key="items.id" v-if=" is_Array(items.children)">
							<td style="font-size: 28rpx;" @click="watchname(items.id)">{{items.name}}
								<text style="font-size: 14rpx;color: #0081FF;">编辑公式</text>
							</td>
							<td style="">{{items.qm_balance ? Math.abs(items.qm_balance) : 0}}</td>
							<td style="">{{items.nc_balance ? Math.abs(items.nc_balance) : 0}}</td>
						</tr>
					</block>
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

	</view>
</template>

<script>
	import dayjs from "@/utils/dayjs.min.js";
	export default {
		data() {
			return {
				num: "",
				monthArr: [],
				listData: [], //
				swiperCurrent: 0,
				tabList: [{
						name: "资产"
					},
					{
						name: "负债和所有者权益"
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
				formData: {
					years: "",
					num: "",
					type: 2

				},
			};
		},
		onLoad() {
			this.formData.years = dayjs().year();
			this.formData.to_year = dayjs().year();
			this.getday() //获取日期

		},
		methods: {
			is_Array(arr){
				return Array.isArray(arr)
			},
			watchname(i) {
				uni.navigateTo({
					url: '../../my/zcfzlist?id=' + i
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
							this.formData.num = currentData[0].month
						} else {
							this.formData.num = dayjs().month() + 1;
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
			tabsChange(index) {
				this.swiperCurrent = index;
				this.listData = []
				// this.formData.level = index;
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
				let taska
				if (this.formData.month == '') {
					uni.showToast({
						title: "请选择您要查询的日期区间",
						icon: "none"
					})
					return
				}
				if (this.formData.num == '') {
					uni.showToast({
						title: "请选择您要查询的日期区间",
						icon: "none"
					})
					return
				}
				this.$http("enterprise.report/assets_liabilities", this.formData, "get").then(res => {
					if (res.data.code == 1) {
						if (this.swiperCurrent == 0) {
							var a = 0;
							var b = 0;
							res.data.data.left.forEach(item => {
								console.log("UUU")
								// if (item.children instanceof Array) {
									if (Array.isArray(item.children)) {
									item.all_price=0
									item.qc_price=0
									item.children.forEach((items,i) => {
										if(i<item.children.length-1){
											item.all_price += Math.abs(items.qm_balance)
											item.qc_price += Math.abs(items.nc_balance)
										}
										item.children[item.children.length-1].qm_balance = item.all_price
										item.children[item.children.length-1].nc_balance = item.qc_price
										a = Math.abs(item.all_price)
										b = Math.abs(item.qc_price)
									})
								}else{
									var teag = res.data.data.left[0].children[res.data.data.left[0].children.length-1].qm_balance
									var teaga = res.data.data.left[0].children[res.data.data.left[0].children.length-1].nc_balance
									item.qm_balance=a + Math.abs(teag)
									item.nc_balance=b + Math.abs(teaga)
								}

							})
							this.listData = res.data.data.left;
						} else {
							this.listData = res.data.data.right;
							res.data.data.right.forEach(item => {
								if (Array.isArray(item.children)) {
									item.all_price=0
									item.qc_price=0
									item.children.forEach((items,i) => {
										if(i<item.children.length-1){
											item.all_price += Math.abs(items.qm_balance)
											item.qc_price += Math.abs(items.nc_balance)
										}
										item.children[item.children.length-1].qm_balance = item.all_price
										item.children[item.children.length-1].nc_balance = item.qc_price
									})
								}else{
									
								}
								if(item.name == '负债合计'){
									let taal = Math.abs(res.data.data.right[0].children[res.data.data.right[0].children.length-1].qm_balance) + Math.abs(res.data.data.right[1].children[res.data.data.right[1].children.length-1].qm_balance)
									let yeartpal = Math.abs(res.data.data.right[0].children[res.data.data.right[0].children.length-1].nc_balance) + Math.abs(res.data.data.right[1].children[res.data.data.right[1].children.length-1].nc_balance)
									
									item.qm_balance= taal
									item.nc_balance= yeartpal
								}else if(item.name == '负债和所有者权益（或股东权益）'){
									let twoal =  Math.abs(res.data.data.right[2].qm_balance) + Math.abs(res.data.data.right[3].children[res.data.data.right[3].children.length-1].qm_balance)
									let yeartwo = Math.abs(res.data.data.right[2].nc_balance) + Math.abs(res.data.data.right[3].children[res.data.data.right[3].children.length-1].nc_balance)
									// console.log(item.name,"负债和所有者权益")
									item.qm_balance = twoal
									item.nc_balance = yeartwo
								}else{
									
								}
							})
						}

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
	.disable {
		color: #dfdfdf !important;
	}

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

	// th,td{
	// 	padding: 0 !important;
	// 	margin:0 !important;
	// 	box-sizing: border-box;
	// }
</style>

<template>
	<view class="finalBox">
		<u-navbar :is-back="true" title="期末结账" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>

		<view class="mainBox">
			<view class="headerBox">
				<view class="item-div">
					<view class="item-cricle red">
					</view>
					<text>未结转损益未结账</text>
				</view>
				<view class="item-div">
					<view class="item-cricle pul">
					</view>
					<text>已结转损益未结账</text>
				</view>
				<view class="item-div">
					<view class="item-cricle green">
					</view>
					<text>已结转损益已结账</text>
				</view>
			</view>

			<view class="main-con" v-for="(item,index) in daylist" :key="index">
				<view class="main-con-title">
					{{item.years}}
				</view>
				<view class="main-con-m">
					<view class="main-con-m-item" v-for="(items,i) in item.month" :key="i">
						<view class="red" style="border-radius: 50%;"
							@click="btnClick(item.years,items.month,items.IsActive)" v-if="items.IsActive == 0">
							{{items.month}}</view>
						<view class="pul" style="border-radius: 50%;"
							@click="btnClick(item.years,items.month,items.IsActive)" v-if="items.IsActive == 1">
							{{items.month}}</view>
						<view class="green" style="border-radius: 50%;"
							@click="btnClick(item.years,items.month,items.IsActive)" v-if="items.IsActive == 2">
							{{items.month}}</view>
					</view>
					<!-- <view class="main-con-m-item" @click="btnClick(2)">
						02
					</view> -->
				</view>
			</view>
		</view>

		<u-modal v-model="isShowAlert" :show-cancel-button="false" :show-confirm-button="false" :show-title="false">

			<view class="slot-content text-center" style="padding: 24rpx 32rpx;" slot="default">
				<view class="flex justify-end" @click="closeBtn">
					<u-icon name="close-circle" color="#B5BFDA" size="46"></u-icon>
				</view>
				<view class="header text-bold text-center" style="font-size: 32rpx;">
					{{dina}}年第{{din}}期
				</view>

				<image src="../../../static/image/tab1/img.png" class="alert-img" mode=""></image>


				<view style="color: #7A7C94;font-size: 26rpx;" v-if="indexactive == 0">
					未结转损益未结账
				</view>
				<view style="color: #7A7C94;font-size: 26rpx;" v-if="indexactive == 1">
					已结转损益未结账
				</view>
				<view style="color: #7A7C94;font-size: 26rpx;" v-if="indexactive == 2">
					已结转损益已结账
				</view>

				<view class="addBtn" @click="checkOut">
					结转
				</view>
			</view>
		</u-modal>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isShowAlert: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
				din: '',
				dina: '',
				indexactive: '',
				credentials: [],
				daylist: []
			};
		},
		onShow() {
			this.getday()

		},
		methods: {
			getday() {
				this.$http("enterprise.date_query/getQueryTime", "get").then(res => {
					if (res.data.code == 1) {
						this.daylist = res.data.data
					}
				})
			},
			getpolist() {
				let param = {
					page: 1,
					limit: 10,
					offset: 0,
					sort: 'id',
					order: 'createtime',
					years: this.dina,
					month: this.din
				}
				this.$http("enterprise.Date_query/index", param, "get").then(res => {
					if (res.data.code == 1) {
						console.log(res.data.data)
						res.data.data.rows[0].rule.forEach(items => {
							// console.log(items, "??????????")
							this.credentials.push({
								abstract: this.din + '月结转收益',
								subject_headings: items.subject.name,
								balance_status: items.direction,
								kemu_id: items.kemu_id,
								money: items.sum,
								hesuan_children: []
							})
						})
						console.log(JSON.stringify(this.credentials), ">>>>>>>>>>>>>>")
					}
				})
			},
			checkOut() {
				// uni.navigateTo({
				// 	url: "/pages/workbench/finalBill/status"
				// })
				let param = {
					credentials: JSON.stringify(this.credentials),
					recording_time: 11,
					documents_images: 11,
					remarks: '你好',
					years: this.dina,
					month: this.din,
					day: 30,
					operation: 'add',
					key: 46,
					is_profit: 1,
					// asid: 2
				}
				this.$http("enterprise.Date_query/CreateCredetials", param, "post").then(res => {
					if (res.data.code == 1) {
						this.isShowAlert = false;
						uni.navigateTo({
							url: "/pages/workbench/finalBill/status?year=" + this.dina + '&month=' + this
								.din + '&indexa=' + this.indexactive
						})
					}
				})
			},
			closeBtn() {
				this.isShowAlert = false;
			},
			btnClick(year, i, active) {
				console.log(year)
				if (active == 2) {
					uni.navigateTo({
						url: "/pages/workbench/finalBill/status?year=" + year + '&month=' + i + '&indexa=' + this.indexactive
					})
				} else {
					uni.navigateTo({
						url: './jzlist?years=' + year + '&month=' + i
					})
				}

				// this.credentials = []
				// this.din = i
				// this.dina = year
				// this.indexactive = active
				// this.getpolist()
				// if(active == 2){
				// 	uni.navigateTo({
				// 		url: "/pages/workbench/finalBill/status?year=" + this.dina + '&month=' + this.din + '&indexa=' + this.indexactive
				// 	})
				// }else if(active == 0){
				// 	this.isShowAlert = true;
				// }else if(active == 1){
				// 	uni.navigateTo({
				// 		url: "/pages/workbench/finalBill/status?year=" + this.dina + '&month=' + this.din + '&indexa=' + this.indexactive
				// 	})
				// }

			}
		}
	}
</script>

<style lang="scss" scoped>
	page {

		background: #FBFCFF;
		color: #150E33;
	}

	.alert-img {
		width: 161rpx;
		height: 140rpx;
		margin: 57rpx auto 32rpx auto;
	}

	.addBtn {
		width: 520rpx;
		height: 80rpx;
		background: #4396F7;
		border-radius: 16rpx;
		color: #fff;
		text-align: center;
		line-height: 80rpx;
		font-size: 32rpx;
		margin-top: 60rpx;
	}

	.mainBox {
		padding: 0 32rpx;

		.main-con {
			padding: 32rpx 24rpx;
			background: #fff;
			border-radius: 16rpx;

			.main-con-title {
				font-size: 36rpx;
				font-weight: bold;
			}

			.main-con-m {
				display: flex;
				margin-top: 32rpx;

				.main-con-m-item {
					width: 88rpx;
					height: 88rpx;
					background: #EEF2FF;
					color: #fff;
					font-size: 32rpx;
					text-align: center;
					line-height: 88rpx;
					border-radius: 50%;
				}
			}
		}

		.headerBox {
			display: flex;
			padding: 32rpx 24rpx;
			margin-top: 32rpx;
			border-radius: 16rpx;
			flex-wrap: wrap;
			background-color: #fff;

			.item-div {
				margin-bottom: 20rpx;
				display: flex;
				align-items: center;
				font-size: 30rpx;
				width: 50%;
			}
		}
	}

	.item-cricle {
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

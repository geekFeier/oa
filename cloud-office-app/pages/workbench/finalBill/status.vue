<template>
	<view>
		<u-navbar :is-back="true" title="期末结账" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="main-title">
				{{years}}年第{{montha}}期
			</view>
			<image src="../../../static/image/tab1/caiwu/red.png" v-if="status==1" class="status-img" mode=""></image>
			<image src="../../../static/image/tab1/success.png" v-if="status==2" class="status-img" mode=""></image>
			<image src="../../../static/image/tab1/caiwu/green.png" v-if="status==3" class="status-img" mode=""></image>
			<view class="status-text red" v-if="status==1">
				凭证未完成审核
			</view>
			<view class="status-text blue" v-if="status==2">
				检查完毕，请结账
			</view>
			<view class="status-text green" v-if="status==3">
				已结转损益，已结账
			</view>
			<view class="main-btn" @click="changeStatus1" v-if="status==1">
				一键审核
			</view>
			<view class="main-btn" v-if="status==2" @click="changeStatus2">
				结账
			</view>
			<view class="main-btn" v-if="status==3" @click="changeStatus3">
				反结账
			</view>
		</view>
		<u-modal v-model="isShowAlert" @confirm="confirm" :show-cancel-button="true" :show-confirm-button="true"
			:show-title="true" title="提示">

			<view class="slot-content modal-con" slot="default">
				反结账后，本期及后面的期间都会反结 账，确定反结账？
			</view>
		</u-modal>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
				status: 3,
				isShowAlert: false,
				montha: '',
				years: ''
			};
		},
		onLoad(e) {
			console.log(e.year)
			this.years = e.year
			this.montha = e.month
			// if (e.indexa == 0) {
			// 	this.status = 1
			// } else if (e.indexa == 1) {
			// 	this.status = 2
			// } else {
			// 	this.status = 3
			// }
			console.log(e.month)
		},
		methods: {
			//点击确定
			confirm() {
				let param = {
					years: this.years,
					month: this.montha,
				}
				this.$http("enterprise.Date_query/reversProfitLoss", param, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "反结账成功",
							icon: "none"
						})
						setTimeout(()=>{
							uni.navigateBack({
								delta:1
							})
						},1000)
						// uni.switchTab({
						// 	url: '../../tabbar/tab-1/index'
						// })
					}
				})

			},
			changeStatus1() {
				let param = {
					years: this.years,
					month: this.montha,
				}
				this.$http("enterprise.Date_query/audit", param, "post").then(res => {
					if (res.data.code == 1) {
						this.status = 2;
					}
				})

			},
			//结账
			changeStatus2() {
				let param = {
					years: this.years,
					month: this.montha,
				}
				this.$http("enterprise.Date_query/NextCheck", param, "get").then(res => {
					if (res.data.code == 1) {
						this.status = 3;
					}
				})
				// uni.switchTab({
				// 	url:'../../tabbar/tab-1/index'
				// })
				
			},
			changeStatus3() {
				this.isShowAlert = true;
			}
		}

	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
	}

	.red {
		color: #FF253D;
	}

	.blue {
		color: #4396F7;
	}

	.green {
		color: #12D592;
	}

	.modal-con {
		padding: 40rpx;
		color: #150E33;
		font-size: 28rpx;
	}

	.mainBox {
		padding: 0 32rpx;
		text-align: center;

		.main-title {
			font-size: 56rpx;
			text-align: center;
			margin-top: 80rpx;
		}

		.status-img {
			width: 200rpx;
			height: 200rpx;
			margin-top: 80rpx;
		}

		.status-text {
			font-size: 32rpx;
			margin-top: 32rpx;
		}

		.main-btn {
			margin-top: 100rpx;
			width: 686rpx;
			height: 98rpx;
			background: #4396F7;
			text-align: center;
			line-height: 98rpx;
			color: #fff;
			font-size: 30rpx;
			border-radius: 49rpx;
		}
	}
</style>

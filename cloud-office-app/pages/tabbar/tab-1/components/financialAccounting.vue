<template>
	<view class="statementBox">
		<view class="statementMain">
			<view class="common-header">
				<view class="common-title">
					财务核算
				</view>
				<view class="common-header-r" @click="openPopup">
					<text>{{accountText}} </text>
					<u-icon name="arrow-down-fill" style="margin-left: 8rpx;"></u-icon>
				</view>
			</view>
			<u-grid :col="4" :border="false" style="margin-top: 12rpx;">
				<u-grid-item @click="goAddVoucher">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/xzpz.png" mode="">
					</image>
					<view class="grid-text">新增凭证</view>
				</u-grid-item>
				<u-grid-item @click="goVoucher">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/ckpz.png" mode="">
					</image>
					<view class="grid-text">查看凭证</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('finalBill')">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/qmjz.png" mode="">
					</image>
					<view class="grid-text">期末结账</view>
				</u-grid-item>
				<u-grid-item @click="goAccountBalanceSheet">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/kmye.png" mode="">
					</image>
					<view class="grid-text">科目余额表</view>
				</u-grid-item>
			</u-grid>
			<u-grid :col="4" :border="false" style="margin-top: 12rpx;">
				<u-grid-item  @click="jumpTo('assetAndLiability')">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/zcbz.png" mode="">
					</image>
					<view class="grid-text">资产负债表</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('incomeStatement')">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/lrb.png" mode="">
					</image>
					<view class="grid-text">利润表</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('cashFlowStatement')">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/xkll.png" mode="">
					</image>
					<view class="grid-text">现金流量表</view>
				</u-grid-item>
				<u-grid-item @click="goDetailAccount">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/mxz.png" mode="">
					</image>
					<view class="grid-text">明细账</view>
				</u-grid-item>
			</u-grid>


			<u-grid :col="4" :border="false" style="margin-top: 12rpx;">
				<u-grid-item @click="jumpTo('fuzhuBalance')">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/yeb.png" mode="">
					</image>
					<view class="grid-text">辅助核算余额表</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('fuzhuDetail')">
					<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
						src="../../../../static/image/tab1/caiwu/fzhs.png" mode="">
					</image>
					<view class="grid-text">辅助核算明细账</view>
				</u-grid-item>
			</u-grid>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

				isShowPopop: false,

				defaultId: "",
				nameText: ""
			}
		},
		onLoad() {
			this.getList();
		},
		props: ["accountText"],
		
		methods: {
			goDetailAccount(){
				uni.navigateTo({
					url:"/pages/workbench/detailAccount/index"
				})
			},
			goAccountBalanceSheet(){
				uni.navigateTo({
					url:"/pages/workbench/accountBalanceSheet/index"
				})
			},
			getList() {
				console.log(123)
				let params = {
					offset: (this.page - 1) * 10,
					page: this.page,
					limit: this.limit
				}
				this.$http("enterprise.Account_books/index", params, "post").then(res => {
					if (res.data.code == 1) {
						this.listData = res.data.data.rows;
					} else {
						this.listData = [];
					}
					console.log(res, "++++++++++++++++++++++++++++++");
				})
			},
			jumpTo(val) {
				uni.navigateTo({
					url: `/pages/workbench/${val}/index`
				})
			},
			openPopup() {
				this.isShowPopop = true;
				this.$emit("changeFinancialPopup", true)
			},
			goVoucher() {
				uni.navigateTo({
					url: "/pages/workbench/voucher/list"
				})
			},
			goAddVoucher() {
				uni.navigateTo({
					url: "/pages/workbench/voucher/addPage?flag=1"
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.common-header {
		display: flex;
		justify-content: space-between;
	}

	.common-title {
		font-size: 34rpx;
		color: #150E33;
		font-weight: bold;
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
				width: 130rpx;
				text-align: center;
			}
		}
	}
</style>

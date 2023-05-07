<template>
	<view class="statementBox">
		<view class="statementMain">
			<view class="common-header">
				<view class="common-title">
					申请
				</view>
			</view>
			<view class="con">
				<u-grid :col="4" :border="false">
					<u-grid-item @click="jumpTo('safeguard')">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../../static/image/tab2/bzsq.png" mode="">
						</image>
						<view class="grid-text">报账申请</view>
					</u-grid-item>
					<u-grid-item @click="jumpTo('sealApply')">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../../static/image/tab2/yysq.png" mode="">
						</image>
						<view class="grid-text">用印申请</view>
					</u-grid-item>
					<u-grid-item @click="jumpTo('maketTicketApply')">
						<image class="grid-icon" style="width: 64rpx;height: 54rpx;"
							src="../../../../static/image/tab2/kpsq.png" mode="">
						</image>
						<view class="grid-text">开票申请</view>
					</u-grid-item>
					<u-grid-item @click="jumpTo('userCarApply')">
						<image class="grid-icon" style="width: 64rpx;height: 54rpx;"
							src="../../../../static/image/tab2/ycsq.png" mode="">
						</image>
						<view class="grid-text">用车申请</view>
					</u-grid-item>
				</u-grid>
				<u-grid :col="4" :border="false">
					<u-grid-item  @click="jumpTo('receiveApply')">
						<image class="grid-icon" style="width: 58rpx;height: 54rpx;"
							src="../../../../static/image/tab2/lysq.png" mode="">
						</image>
						<view class="grid-text">领用申请</view>
					</u-grid-item>
					<u-grid-item  @click="jumpTo('payApply')">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../../static/image/tab2/dksq.png" mode="">
						</image>
						<view class="grid-text">支付申请</view>
					</u-grid-item>
					<u-grid-item @click="jumpTo('otherApply')">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../../static/image/tab2/qtsq.png" mode="">
						</image>
						<view class="grid-text">其他申请</view>
					</u-grid-item>
					<!-- <u-grid-item  @click="jumpTo('dispatchApply')">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../../static/image/tab2/fwsq.png" mode="">
						</image>
						<view class="grid-text">发文申请</view>
					</u-grid-item> -->
				</u-grid>

			</view>


		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isShowPopop: false
			}
		},
		methods: {
			jumpTo(val){
				uni.navigateTo({
					url:`/pages/cooperation/applyAllPage/${val}/index`
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

	.con {
		margin-top: 30rpx;
	}

	.statementBox {
		padding: 0 32rpx;
		margin-top: 20rpx;

		.statementMain {
			padding: 32rpx 24rpx;
			background: #fff;
				// background-color: pink;
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

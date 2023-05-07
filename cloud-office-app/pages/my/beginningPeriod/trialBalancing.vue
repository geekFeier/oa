<template>
	<view>
		<u-navbar :is-back="true" title="试算平衡" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="main-item">
				<view class="main-item-l">
					<view class="main-item-l-t">
						期初余额
					</view>
					<view class="main-item-b">
						<u-icon color="#fff" size="48" name="checkbox-mark" style="margin-right: 32rpx;"></u-icon>
						<text v-if="qc.is_ping">相等</text>
						<text v-else>不相等</text>
					</view>
				</view>

				<view class="main-item-r">
					<view class="main-item-r-l">
						<view class="">
							借
						</view>
						<view class="main-item-r-price">
							{{qc.qc_debit_balance}}
						</view>
					</view>
					<view class="main-item-r-m" v-if="qc.is_ping">
						=
					</view>
					<view class="main-item-r-m" v-else>
						!=
					</view>
					<view class="main-item-r-r">
						<view class="">
							贷
						</view>
						<view class="main-item-r-price">
							{{qc.qc_creditor_balance}}
						</view>
					</view>

				</view>
			</view>

			<view class="main-item">
				<view class="main-item-l">
					<view class="main-item-l-t">
						累计发生
					</view>
					<view class="main-item-b">
						<u-icon color="#fff" size="48" name="checkbox-mark" style="margin-right: 32rpx;"></u-icon>
						<text v-if="lj.is_ping">相等</text>
						<text v-else>不相等</text>
					</view>
				</view>

				<view class="main-item-r">
					<view class="main-item-r-l">
						<view class="">
							借
						</view>
						<view class="main-item-r-price">
							{{lj.lj_debit_balance}}
						</view>
					</view>
					<view class="main-item-r-m" v-if="lj.is_ping">
						=
					</view>
					<view class="main-item-r-m" v-else>
						!=
					</view>
					<view class="main-item-r-r">
						<view class="">
							贷
						</view>
						<view class="main-item-r-price">
							{{lj.lj_creditor_balance}}
						</view>
					</view>

				</view>
			</view>


			<view class="main-item">
				<view class="main-item-l">
					<view class="main-item-l-t">
						资产负债表期初
					</view>
					<view class="main-item-b">
						<u-icon color="#fff" size="48" name="checkbox-mark" style="margin-right: 32rpx;"></u-icon>
						<text v-if="zc.is_ping">相等</text>
						<text v-else>不相等</text>
					</view>
				</view>

				<view class="main-item-r">
					<view class="main-item-r-l">
						<view class="">
							资产
						</view>
						<view class="main-item-r-price">
							{{zc.zc_debit_balance}}
						</view>
					</view>
					<view class="main-item-r-m" v-if="zc.is_ping">
						=
					</view>
					<view class="main-item-r-m" v-else>
						!=
					</view>
					<view class="main-item-r-r">
						<view class="" style="width: 130rpx;margin-top: -38rpx;">
							负债和所 有者权益
						</view>
						<view class="main-item-r-price">
							{{zc.zc_creditor_balance}}
						</view>
					</view>

				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
				k_id: "",
				lj:'',
				qc:'',
				zc:''
			};
		},
		onLoad(e) {
			this.getuser()
		},
		methods: {
			// enterprise.Subject/scale
			getuser(){
				this.$http("enterprise.Subject/scale", "post").then(res => {
					if (res.data.code == 1) {
						this.lj = res.data.data.lj
						this.qc = res.data.data.qc
						this.zc = res.data.data.zc
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: #FBFCFF;
		color: #150E33;
	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			padding: 32rpx;
			display: flex;
			// background-color: pink;
			margin-top: 32rpx;
			background: #FFFFFF;
			border-radius: 20rpx;

			.main-item-l {
				flex-direction: column;
				display: flex;
				align-items: center;
				justify-content: center;
				width: 280rpx;
				height: 180rpx;
				background: url(../../../static/image/my/setting/bgg.png) no-repeat;
				color: #fff;
				background-size: 100%;

				.main-item-l-t {
					font-size: 30rpx;
					margin-bottom: 20rpx;
				}

				.main-item-b {
					font-size: 28rpx;
				}
			}

			.main-item-r {
				font-size: 32rpx;
				display: flex;
				margin-left: 55rpx;
				padding: 40rpx 0;

				.main-item-r-m {
					margin: 0 32rpx;
				}

				.main-item-r-l,
				.main-item-r-r {
					// text-align: center;
				}

				.main-item-r-price {
					margin-top: 32rpx;
					font-size: 30rpx;
				}
			}
		}
	}
</style>

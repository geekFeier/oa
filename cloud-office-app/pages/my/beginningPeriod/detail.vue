<template>
	<view>
		<u-navbar :is-back="true" title="辅助项目明细" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" :key="index">
				<view class="main-item-header">
					<text>{{index+1}}.{{item.name}}</text>
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
					<view class="main-item-m-n ">
						<input type="text" v-model="item.borrow" @blur="blueBorrow(item)" placeholder="请输入" />
					</view>
					<view class="main-item-m-n ">
						<input type="text" v-model="item.loan" @blur="blueBorrow(item)" placeholder="请输入" />
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
				k_id: "",
				value: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
				listData: []
			};
		},
		onLoad(e) {
			this.k_id = e.id;
			this.getData();
		},
		methods: {
			blueBorrow(item) {
				this.$http("enterprise.Subject/hesuanSet", item, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "设置成功",
							icon: "none"
						})
					}
				})
			},
			getData() {
				let params = {
					k_id: this.k_id
				}
				this.$http("enterprise.Subject/hesuanSet", params, "get").then(res => {
					if (res.data.code == 1) {
						let dataArr = res.data.data;
						this.listData = dataArr.map(item => {
							return {
								borrow: item.borrow ? item.borrow : "",
								loan: item.loan ? item.loan : "",
								h_id: item.id,
								name:item.hesuanc.name
							}
						})
					}
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
		background: #FBFCFF;
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
</style>

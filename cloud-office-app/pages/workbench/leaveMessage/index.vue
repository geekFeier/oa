<template>
	<view class="generationOfficeBox">
		<u-navbar :is-back="true" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" title="我的留言" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="main-item flex" v-for="(item,index) in listData" :key="index" @click="goDetail(item.id)">
						{{item.userInfo &&  item.userInfo.job && item.userInfo.job.flag | filterJob}} ——
						{{item.userInfo && item.userInfo.job && item.userInfo.job.name}}
						<view class="txt flex flex-ac">（未读：<view class="circle">{{10>99 ? '99+' : 10}}</view>条）</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				navTitle: "",
				listData: [],
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		filters: {
			filterJob(val) {
				// 1=总经理，2=部门经理，3=会计，4=职员
				switch (Number(val)) {
					case 1:
						return "总经理"
						break;
					case 2:
						return "部门经理"
						break;
					case 3:
						return "会计"
						break;
					case 4:
						return "职员"
						break;
				}

			}
		},
		onLoad() {
			this.getList();
		},
		onPullDownRefresh() {
			this.listData = []
			this.getList();
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			getList() {
				let params = {}
				this.$http("enterprise.message/index", params, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = res.data.data
						console.log('~~~~~~this.listData')
						console.log(this.listData)
					}
				})
			},
			goDetail(id) {
				uni.navigateTo({
					url: "/pages/workbench/leaveMessage/detail?id=" + id
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.addBtn {
		position: fixed;
		bottom: 298rpx;
		right: 32rpx;
		width: 110rpx;
		height: 110rpx;
	}

	page {
		background: #FBFCFF;
	}

	/deep/ .u-navbar-inner {
		position: relative;
		z-index: 99999999999;
	}

	.generationOfficeBox {
		.mainBox {
			padding: 0 32rpx;

			.main-item {
				color: #150E33;
				font-size: 32rpx;
				border-bottom: 1px dashed lightgray;
				padding:16rpx 0;
			}
		}

		.header-div {
			transform: translateX(-2%);
			width: 100%;
			display: flex;
			justify-content: center;

			.header-icon {
				margin-left: 16rpx;
			}
		}

		.popup-main {
			padding: 32rpx;

			.popup-item {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-bottom: 50rpx;



				.popup-item-l {
					display: flex;
					align-items: center;
				}

				.popup-item-l-name {
					font-size: 32rpx;
					color: #150E33;
				}

				// width: 100vw;
			}
		}
	}
.txt{
	color:#999;
	font-size: 24rpx;
}
	.circle {
		width: 30rpx;
		height: 30rpx;
		line-height: 34rpx;
		border-radius: 50%;
		background-color: #FF253D;
		color:#fff;
		font-size: 22rpx;
		text-align: center;
		margin-right: 4rpx;
		
	}

	.pul {
		background-color: #915FF2;
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
<template>
	<view>
		<u-navbar :is-back="true" title="我的文档" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="mainBox">
			<!-- <view class="enterpriseCloud-box">
				<view class="enterpriseCloud-title">
					{{userInfo.config.pan_info.last_name}}
				</view>
				<progress class="progress-div" :percent="GetPercent(userInfo.config.pan_info.use,userInfo.config.pan_info.size)" stroke-width="10" backgroundColor="#EEF2FF" />
				<view class="status-div">
					<text class="status-l">{{userInfo.config.pan_info.use}}M/{{userInfo.config.pan_info.size}}M</text>
					<text class="status-r">已使用{{GetPercent(userInfo.config.pan_info.use,userInfo.config.pan_info.size)}}</text>
				</view>
				<view class="flex justify-end">
					<view class="dilatation-btn" @click="kuoRong">
						扩容
					</view>
				</view>
			</view> -->
			<view class="file-box">
				<view class="file-item" @click="goFileList" style="border-bottom: 1px solid #F6F9FE;">
					<view class="file-item-l">
						<image src="../../../static/image/my/cloud-file.png" class="file-item-l-icon" mode=""></image>
						<view class="file-info">
							<view class="file-name">
								云盘文档
							</view>
							<view class="file-size">
								{{userInfo.config.dir[0].size}}M
							</view>
						</view>

					</view>

					<view class="file-item-r">
						<u-icon name="arrow-right" color="#7A7C94"></u-icon>
					</view>
				</view>
				<view class="file-item"  @click="goFileList">
					<view class="file-item-l">
						<image src="../../../static/image/my/file.png" class="file-item-l-icon" mode=""></image>
						<view class="file-info">
							<view class="file-name">
								本地文档
							</view>
							<view class="file-size">
								{{userInfo.config.dir[0].size}}M
							</view>
						</view>

					</view>

					<view class="file-item-r">
						<u-icon name="arrow-right" color="#7A7C94"></u-icon>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState
	} from "vuex"
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo,
		
			})
		},
		methods: {
			GetPercent(num, total) {
			    /// <summary>
			    /// 求百分比
			    /// </summary>
			    /// <param name="num">当前数</param>
			    /// <param name="total">总数</param>
			    num = parseFloat(num);
			    total = parseFloat(total);
			    if (isNaN(num) || isNaN(total)) {
			        return "-";
			    }
			    return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00)+"%";
			},
			kuoRong(){
				uni.navigateTo({
					url:"/pages/my/vip/dilatation/index"
				})
			},
			goFileList() {
				uni.navigateTo({
					url: "/pages/my/cloudPan/fileList"
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: #FBFCFF;
	}

	.file-box {
		margin-top: 24rpx;
		background: #FFFFFF;
		border-radius: 24rpx;

		.file-item {
			display: flex;
			justify-content: space-between;
			height: 142rpx;
			align-items: center;
			padding: 0 25rpx;

			.file-item-l {
				display: flex;
				align-items: center;

				.file-item-l-icon {
					width: 62rpx;
					height: 54rpx;
					margin-right: 24rpx;
				}

				.file-info {
					display: flex;
					flex-direction: column;

					.file-name {
						font-size: 30rpx;
						color: #150E33;
					}

					.file-size {
						color: #B5BFDA;
						font-size: 26rpx;
						margin-top: 20rpx;
					}
				}
			}


		}
	}

	.mainBox {
		padding: 0 32rpx;

		.enterpriseCloud-box {
			background: #fff;
			// background-color: pink;
			padding: 32rpx 24rpx;
			border-radius: 20rpx;
			margin-top: 32rpx;

			.enterpriseCloud-title {
				font-size: 32rpx;
			}

			.progress-div {
				margin: 32rpx 0;
			}

			.status-div {
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-size: 26rpx;

				.status-r {
					color: #4396F7;
				}
			}

			.dilatation-btn {
				width: 130rpx;
				height: 56rpx;
				font-size: 30rpx;
				color: #fff;
				line-height: 56rpx;
				text-align: center;
				background: #4396F7;
				border-radius: 30rpx;
				margin-top: 32rpx;
			}

		}
	}
</style>

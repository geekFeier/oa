<template>
	<view>
		<u-navbar :is-back="true" title="发文申请" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="header-div">
			<view class="header-t">张明明提交的开票申请</view>
			<view class="header-m">企业名称</view>
			<view class="header-b">等待李四处理审批</view>
		</view>

		<view class="main-box">
			<view class="mainBox">
				<view class="form-item">
					<view class="form-item-title">
						审批编号
					</view>
					<view class="form-item-value">2014545124527452123325641</view>
				</view>

				<view class="form-item">
					<view class="form-item-title">
						申请事由
					</view>
					<view class="form-item-value">报销</view>
				</view>

			

				<view class="form-item">
					<view class="form-item-title">
						备注
					</view>
					<view class="form-item-value">无</view>
				</view>
				<view class="form-item">
					<view class="form-item-title">
						凭证
					</view>
					<view class="form-item-img-list">
						<image v-for="(item,index) in 7" src="../../../../static/image/launch_icon.png"
							class="form-item-img-list-i" mode=""></image>
					</view>
				</view>
			</view>

			<view class="flowBox">
				<view class="flowBox-title">
					流程
				</view>
				<view class="flowBox-con">
					<view class="flowBox-item">
						<view class="flowBox-item-l">
							<view class="flowBox-item-l-div">
								<image src="../../../../static/image/launch_icon.png" class="flowBox-item-l-img"
									mode=""></image>
								<image src="../../../../static/image/tab1/success.png" class="flowBox-item-l-status"
									mode=""></image>
							</view>

							<view class="flowBox-item-l-info">
								<view class="flowBox-item-l-info-hd">
									<text class="u-info1">发起申请</text>
									<text class="u-info2">07.06 14:54</text>
								</view>
								<view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">我</view>
							</view>
						</view>
					</view>
					
					<view class="hrDiv">
						
					</view>
					<view class="flowBox-item">
						<view class="flowBox-item-l">
							<view class="flowBox-item-l-div">
								<image src="../../../../static/image/launch_icon.png" class="flowBox-item-l-img"
									mode=""></image>
								<image src="../../../../static/image/tab2/dd.png" class="flowBox-item-l-status"
									mode=""></image>
							</view>

							<view class="flowBox-item-l-info">
								<view class="flowBox-item-l-info-hd">
									<text class="u-info1">审批人</text>
									<text class="u-info2">07.06 14:54</text>
								</view>
								<view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">李四(等待处理)</view>
							</view>
						</view>
					</view>
					
					<view class="hrDiv">
						
					</view>
					
					<view class="flowBox-item">
						<view class="flowBox-item-l">
							<view class="flowBox-item-l-div">
								<image src="../../../../static/image/launch_icon.png" class="flowBox-item-l-img"
									mode=""></image>
						<!-- 		<image src="../../../../static/image/tab1/success.png" class="flowBox-item-l-status"
									mode=""></image> -->
							</view>

							<view class="flowBox-item-l-info">
								<view class="flowBox-item-l-info-hd">
									<text class="u-info1">抄送人</text>
									<text class="u-info2">07.06 14:54</text>
								</view>
								<view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">已抄送1人</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	
		<view class="btnGroup">
			<view class="btn1">
				拒绝
			</view>
			<view class="btn2">
				同意
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
			};
		}
	}
</script>

<style lang="scss" scoped>
	.btnGroup{
		display: flex;
		justify-content: space-between;
		margin: 30rpx  0;
		.btn1{
			width: 200rpx;
			height: 80rpx;
			line-height: 80rpx;
			background: #F6F9FE;
			border-radius: 12rpx;
			font-size: 30rpx;
				text-align: center;
			color: #7A7C94;
		}
		.btn2{
			width: 462rpx;
			height: 80rpx;
			background: #4396F7;
			border-radius: 12rpx;
			color: #FFFFFF;
			font-size: 30rpx;
			text-align: center;
			margin-left: 24rpx;
			line-height: 80rpx;
		}
	}
	
	.hrDiv{
		width: 0;
		height: 60rpx;
		border: 1px solid #B5BFDA;
		margin-left: 36rpx;
		margin-top: 8rpx;
		margin-bottom: 8rpx;
	}
	page {
		color: #150E33;
		background: #FBFCFF;
	}

	.flowBox {
		padding: 32rpx 24rpx;
		margin: 32rpx 0;
		border-radius: 18rpx;
		background-color: #fff;
		.flowBox-title {
			font-size: 32rpx;
			font-weight: bold;
			margin-bottom: 16rpx;
		}

		.flowBox-con {
			padding: 24rpx;

			.flowBox-item {
				.flowBox-item-l {
					display: flex;

					.flowBox-item-l-div {
						position: relative;
						width: 85rpx;
						height: 85rpx;

						.flowBox-item-l-img {
							width: 85rpx;
							height: 85rpx;
						}

						.flowBox-item-l-status {
							width: 20rpx;
							height: 20rpx;
							position: absolute;
							right: 0;
							bottom: 0;
						}
					}

					.flowBox-item-l-info {
						margin-left: 16rpx;
						flex: 1;

						.flowBox-item-l-info-hd {
							display: flex;
							justify-content: space-between;
							align-items: center;
						}

						.u-info1 {
							font-size: 28rpx;

						}

						.u-info2 {
							color: #7A7C94;
							font-size: 26rpx;
						}

					}
				}
			}

		}
	}

	.form-item {
	padding-bottom: 32rpx;
	border-bottom: 1px solid #EEF2FF;
		.form-item-title {
			font-size: 30rpx;
			color: #7A7C94;
		}
	}

	.header-div {
		padding: 32rpx;
		background-color: #fff;

		.header-t {
			font-weight: bold;
			font-size: 32rpx;
		}

		.header-m {
			color: #7A7C94;
			font-size: 28rpx;
			margin: 24rpx 0;
		}

		.header-b {
			font-size: 28rpx;
			color: #FA7443;
		}
	}

	.main-box {
		padding: 0 32rpx;
		margin-top: 20rpx;

		.mainBox {
			border-radius: 18rpx;
			padding: 32rpx 24rpx;
			padding-bottom: 0;
			background-color: #fff;

			.form-item {
				margin-bottom: 30rpx;

				.form-item-value {
					margin-top: 20rpx;
					font-size: 32rpx;
				}

				.form-item-img-list {
					margin-top: 20rpx;
					display: flex;
					flex-wrap: wrap;
					margin-right: -25rpx;

					.form-item-img-list-i {
						margin-bottom: 20rpx;
						width: 200rpx;
						height: 200rpx;
						margin-right: 20rpx;

					}
				}
			}

		}
	}
</style>

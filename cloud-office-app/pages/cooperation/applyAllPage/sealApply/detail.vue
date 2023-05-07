<template>
	<view>
		<u-navbar :is-back="true" title="申请印章" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="header-div">
			<view class="header-t">{{detailData.user.username}}提交的报账申请</view>
				<view class="header-m">{{detailData.enterprise.enterprice_name}}</view>
			<view class="header-b" :class="detailData.status | filterStatus">
				{{detailData.status == 0 ? "审核中" : (detailData.status == 1? "审核成功" : "审核失败") }}
			</view>
		</view>

		<view class="main-box">
			<view class="mainBox">
				<view class="form-item">
					<view class="form-item-title">
						审批编号
					</view>
					<view class="form-item-value">{{detailData.serial}}</view>
				</view>

				<view class="form-item">
					<view class="form-item-title">
						经手人
					</view>
					<view class="form-item-value">{{detailData.receivi_person}}</view>
				</view>

				<view class="form-item">
					<view class="form-item-title">
						日期
					</view>
					<view class="form-item-value">{{detailData.use_time}}</view>
				</view>
				<view class="form-item">
					<view class="form-item-title">
						用印文件名称
					</view>
					<view class="form-item-value">{{detailData.use_file_name}}</view>
				</view>
				<!-- 		<view class="form-item">
					<view class="form-item-title">
						文件类别
					</view>
					<view class="form-item-value">合作协议</view>
				</view> -->
				<view class="form-item">
					<view class="form-item-title">
						加盖何种印章
					</view>
					<view class="form-item-value">{{detailData.stamp}}</view>
				</view>


				<view class="form-item">
					<view class="form-item-title">
						备注
					</view>
					<view class="form-item-value">{{detailData.remarks}}</view>
				</view>
				<view class="form-item">
					<view class="form-item-title">
						图片
					</view>
					<view class="form-item-img-list">
						<image v-for="(item,index) in imgData" :src="item"
							class="form-item-img-list-i" @click="previewBtn" mode=""></image>
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
								<image :src="detailData.user.avatar" class="flowBox-item-l-img" mode=""></image>
								<image src="../../../../static/image/tab1/success.png" class="flowBox-item-l-status"
									mode=""></image>
							</view>

							<view class="flowBox-item-l-info">
								<view class="flowBox-item-l-info-hd">
									<text class="u-info1">发起申请</text>
									<!-- <text class="u-info2">07.06 14:54</text> -->
								</view>
								<view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">
									{{detailData.user.id == userInfo.id ? "我" : detailData.user.username}}
								</view>
							</view>
						</view>
					</view>

					<view class="hrDiv">
					</view>
					<view class="" v-for="(item,index) in detailData.audit.receivi_persion" :key="index">
						<view class="flowBox-item">
							<view class="flowBox-item-l">
								<view class="flowBox-item-l-div">
									<image :src="item.avatar" class="flowBox-item-l-img" mode=""></image>
									<image src="../../../../static/image/tab2/dd.png" class="flowBox-item-l-status"
										v-if="item.status==0" mode="">
									</image>
									<image src="../../../../static/image/tab1/success.png" class="flowBox-item-l-status"
										v-if="item.status == 1" mode=""></image>
								</view>

								<view class="flowBox-item-l-info">
									<view class="flowBox-item-l-info-hd">
										<text class="u-info1">审批人</text>
										<!-- <text class="u-info2">07.06 14:54</text> -->
									</view>
									<view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">
										{{item.username}}({{item.status == 0 ? "等待审核" : (item.status == 1? "同意" : "拒绝") }})
									</view>
								</view>
							</view>
						</view>

						<view class="hrDiv">

						</view>
					</view>

					<view class="flowBox-item">
						<view class="flowBox-item-l">
							<view class="flowBox-item-l-div text-center"
								style="background-color: #289CFF;border-radius: 8rpx;">
								<image src="../../../../static/image/tongzhi.png" mode=""
									style="width: 60rpx;height: 60rpx;margin-top: 12rpx;"></image>
								<!-- 	<image src="../../../../static/image/launch_icon.png" class="flowBox-item-l-img"
							mode=""></image> -->
								<!-- 		<image src="../../../../static/image/tab1/success.png" class="flowBox-item-l-status"
							mode=""></image> -->
							</view>

							<view class="flowBox-item-l-info">
								<view class="flowBox-item-l-info-hd">
									<text class="u-info1">抄送人</text>
									<!-- <text class="u-info2">07.06 14:54</text> -->
								</view>
								<view class="flowBox-item-l-info-bd" style="margin-top: 10rpx; color: #7A7C94;">
									已抄送{{detailData.audit.cc_persion.length}}人
								</view>
								<view class="flowBox-item-l-info-bd-list flex" style="margin-top: 8rpx;">
									<view
										class="flowBox-item-l-info-bd-item flex flex-direction flex-wrap justify-center align-center"
										v-for="(item,index) in detailData.audit.cc_persion"
										style="margin-right: 12rpx;margin-bottom: 8rpx;">
										<view class="" style="position: relative;">
											<image style="height: 60rpx;width: 60rpx;" :src="item.avatar" mode="">
											</image>
											<image v-if="item.status==1"
												style="width: 20rpx;height: 20rpx;position: absolute;top: 0;right: 0;"
												src="../../../../static/image/tab1/success.png" mode=""></image>
										</view>
										<text style="margin-top: 8rpx;color: #7A7C94;">{{item.username}}</text>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>

			<view class="btnGroup" v-if="detailData.status == 0 && detailData.user_id !== userInfo.id ">
				<view class="btn1" @click="refuseBtn">
					拒绝
				</view>
				<view class="btn2" @click="sureBtn">
					同意
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
				imgData:[],
				detailData: {},
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad(e) {
			this.detailData = JSON.parse(decodeURIComponent(e.data));
			this.imgData = this.detailData.images ? this.detailData.images.split(",") : []
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo
			})
		},
		filters: {
			filterStatus(val) {
				if (val == 0) {
					return "blue"
				} else if (val == 1) {
					return "green"
				} else if (val == 1) {
					return "red"
				}
			}
		},
		methods: {
			refuseBtn() {
				let params = {
					key: this.detailData.id,
					status: 2
				}
				this.$http("enterprise.applyfor.Yongyin/operaction", params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			sureBtn() {
				let params = {
					key: this.detailData.id,
					status: 1
				}
				this.$http("enterprise.applyfor.Yongyin/operaction", params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			previewBtn() {
				uni.previewImage({
					urls: this.imgData
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.hrDiv {
		width: 0;
		height: 60rpx;
		border: 1px solid #B5BFDA;
		margin-left: 36rpx;
		margin-top: 8rpx;
		margin-bottom: 8rpx;
	}

	.btnGroup {
		display: flex;
		justify-content: space-between;
		margin: 30rpx 0;

		.btn1 {
			width: 200rpx;
			height: 80rpx;
			line-height: 80rpx;
			background: #F6F9FE;
			border-radius: 12rpx;
			font-size: 30rpx;
			text-align: center;
			color: #7A7C94;
		}

		.btn2 {
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

	.pul {
		color: #915FF2;
	}

	.red {
		color: #FF253D;
	}

	.blue {
		color: #4396F7;
	}

	.orange {
		color: #FA7443;
	}

	.green {
		color: #12D592;
	}
</style>

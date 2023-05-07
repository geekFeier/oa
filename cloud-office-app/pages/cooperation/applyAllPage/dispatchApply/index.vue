<template>
	<view class="">
		<u-navbar :is-back="true" title="其他申请" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="32" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" :current="swiperCurrent" @change="tabsChange" :list="tabList"
			:is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>

		<view class="mainBox" v-if="swiperCurrent==0">
			<view class="form-item">
				<view class="form-item-title">
					申请事由
				</view>
				<u-input v-model="value" type="textarea" :auto-height="true" :clearable="false" placeholder="请输入申请事由" />
			</view>


			<view class="form-item">
				<view class="form-item-title">
					备注
				</view>
				<u-input v-model="value" type="text" :clearable="false" placeholder="请输入" />
			</view>

			<view class="form-item2">
				<view class="form-item2-title">
					照片
				</view>
				<image src="../../../../static/image/tab2/addImg.png" style="width: 50rpx;height: 40rpx;" mode="">
				</image>
			</view>

			<view class="form-item3">
				<view class="form-item3-main">
					<view class="form-item3-title">
						流程
					</view>
					<view class="form-item3-main-con">
						<image src="../../../../static/image/tab2/leftimg.png" class="form-item3-main-con-l" mode="">
						</image>
						<view class="form-item3-main-con-r">
							<view class="con-r-item">
								<view class="con-r-item-l">
									<text style="font-size: 32rpx;">接收人</text>
									<text style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">1人接收</text>
								</view>
								<view class="con-r-item-r">
									<view class="upload-div">
										+
									</view>
								</view>
							</view>
							<view class="con-r-item">
								<view class="con-r-item-l">
									<text style="font-size: 32rpx;">接收人</text>
									<text style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">1人接收</text>
								</view>

								<view class="con-r-item-r">
									<view class="con-r-item-r-person">
										<image src="../../../../static/image/launch_icon.png"
											class="con-r-item-r-person-img" mode=""></image>
										<image src="../../../../static/image/tab2/close.png"
											class="con-r-item-r-person-close" mode=""></image>
									</view>
									<view class="upload-div">
										+
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>

			</view>

			<button type="default" class="addBtn" @click="submitBtn">提交</button>
		</view>

		<view class="mainBox2" v-if="swiperCurrent==1">
			<view class="main-item" @click="goDetail">
				<view class="main-item-t">
					<view class="main-item-t-l">
						张明明提交的申请
					</view>
					<view class="main-item-t-r">
						2021.07.14
					</view>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-text">
						申请事由：报销
					</view>
					<view class="main-item-m-text">
						备注：无
					</view>

				</view>
				<view class="main-item-b">
					<view class="main-item-b-l">
						<image class="main-item-b-l-img" src="../../../../static/image/launch_icon.png" mode=""></image>
						<text>由张明明提交</text>
					</view>
					<view class="main-item-b-r red">
						王三处理中
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
				currentSelectType: "",
				formData: {
					time: "",
					type: ""
				},
				showSelectTime: false,
				currentSelect: 0,
				selectSrc: "../../../../static/image/tab2/select.png",
				selectSrcA: "../../../../static/image/tab2/selecta.png",
				showVoucherPopup: false,
				value: "",
				swiperCurrent: 0,
				background: {
					backgroundColor: "#FFFFFF",
				},
				voucherSelectlist: [{
						name: "公章"
					},
					{
						name: "合同专用章"
					},
					{
						name: "法人代表名章"
					},
					{
						name: "财务专用章"
					},
					{
						name: "人事专用章"
					},
					{
						name: "财务总监章"
					},
					{
						name: "其他印章"
					},
				],
				barStyle: {
					textAlign: "center",
				},
				tabList: [{
						name: "发起提交"
					},
					{
						name: "提交记录"
					},
				]
			}
		},
		methods: {
			sureSelectType() {
				this.showVoucherPopup = false;
				this.formData.type = this.currentSelectType
			},
			sureTime(e) {
				console.log(e, "-----------------------------------");
				this.formData.time = `${e.year}-${e.month}-${e.day}`
			},
			showSelectPopup() {
				this.showSelectTime = true
			},
			selectbtn(index) {
				this.currentSelect = index;
				this.currentSelectType = this.voucherSelectlist[index].name
			},
			voucherPopupBtn() {
				this.showVoucherPopup = true;
			},
			goDetail() {
				uni.navigateTo({
					url: "/pages/cooperation/applyAllPage/dispatchApply/detail"
				})
			},
			submitBtn() {

			},
			tabsChange(index) {
				this.swiperCurrent = index;
			}
		}
	}
</script>

<style scoped lang="scss">
	/deep/ .u-input__input {
		font-size: 32rpx;
	}

	.addBtn {
		text-align: center;
		width: 88%;
		height: 90rpx;
		line-height: 90rpx;
		color: #fff;
		background: #4396F7;
		margin-top: 60rpx;
		margin-bottom: 40rpx;
		border-radius: 49px;
	}

	page {
		background: #FBFCFF;
		color: #150E33;
	}

	.mainBox2 {
		padding: 0 32rpx;

		.main-item {
			background-color: #fff;
			margin-top: 32rpx;
			border-radius: 24rpx;
			padding: 32rpx 24rpx;

			.main-item-t {
				display: flex;
				align-items: center;
				justify-content: space-between;

				.main-item-t-l {
					font-size: 32rpx;
				}

				.main-item-t-r {
					font-size: 26rpx;
					color: #B5BFDA;
				}
			}

			.main-item-m {
				margin-top: 32rpx;
				font-size: 28rpx;
				color: #7A7C94;

				.main-item-m-text {
					margin-bottom: 20rpx;
				}
			}

			.main-item-b {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-top: 32rpx;
				padding-top: 32rpx;
				border-top: 1px solid #EEF2FF;

				.main-item-b-l {
					display: flex;
					align-items: center;
					font-size: 30rpx;

					.main-item-b-l-img {
						margin-right: 16rpx;
						width: 60rpx;
						height: 60rpx;
						border-radius: 50%;
					}

				}

			}
		}
	}


	.mainBox {


		.form-item3 {
			padding: 0 32rpx;

			.form-item3-main {
				background-color: #fff;
				padding: 32rpx 24rpx;

				.form-item3-title {
					font-size: 32rpx;

				}

				.form-item3-main-con {
					display: flex;
					align-items: center;
					margin-top: 40rpx;

					.form-item3-main-con-l {
						width: 20rpx;
						height: 160rpx;
						margin-right: 25rpx;
					}

					.form-item3-main-con-r {
						flex: 1;

					}

					.con-r-item {
						display: flex;
						align-items: center;
						justify-content: space-between;
						padding: 32rpx 0;
						border-bottom: 1px solid #EEF2FF;

						&:nth-of-type(2) {
							border: none;
						}

						.con-r-item-l {
							display: flex;
							flex-direction: column;
						}

						.con-r-item-r {
							display: flex;

							.upload-div {
								width: 72rpx;
								height: 72rpx;
								background: #FBFCFF;
								text-align: center;
								line-height: 72rpx;
								font-size: 50rpx;
								color: #B5BFDA;
								border: 1px solid #B5BFDA;
								border-radius: 8rpx;
							}

							.con-r-item-r-person {
								display: flex;
								position: relative;
								margin-right: 20rpx;

								.con-r-item-r-person-img {
									width: 72rpx;
									height: 72rpx;
									border-radius: 8rpx;
								}

								.con-r-item-r-person-close {
									position: absolute;
									width: 30rpx;
									height: 30rpx;
									right: -10rpx;
									top: -10rpx;
								}
							}
						}
					}
				}
			}
		}

		.form-item2 {
			display: flex;
			justify-content: space-between;
			padding: 32rpx;
			background: #fff;
			margin-top: 20rpx;
			font-size: 32rpx;
		}

		.form-item {
			background: #fff;
			padding: 32rpx;
			margin-top: 20rpx;

			.form-item-title {
				font-size: 32rpx;
				margin-bottom: 10rpx;
			}
		}

	}

	.popup-main {
		.popup-common-m {
			padding: 0 40rpx;

			.popup-common-item {
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-size: 32rpx;
				margin-top: 36rpx;
			}
		}

		.popup-common-title {
			font-size: 36rpx;
			color: #150E33;
			text-align: center;
			margin: 56rpx 0 56rpx 0;
		}

		.active {
			background: #4396F7 !important;
			color: #FFFFFF !important;
		}

		.popup-common-btnGroup {
			display: flex;
			padding: 0 50rpx;
			justify-content: space-between;
			font-size: 30rpx;
			margin-top: 50rpx;
			margin-bottom: 32rpx;


			.popup-common-btn {
				width: 260rpx;
				height: 80rpx;
				background: #F6F9FE;
				border-radius: 12rpx;
				color: #7A7C94;
				text-align: center;
				line-height: 80rpx;
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

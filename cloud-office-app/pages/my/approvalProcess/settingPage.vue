<template>
	<view>
		<u-navbar :is-back="true" title="审批流程" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>

		<view class="" style="padding: 0 32rpx;margin-top: 32rpx;">
			<view class="form-item3">
				<view class="form-item3-main">
					<view class="form-item3-title">
						流程
					</view>
					<view class="form-item3-main-con">
						<image src="../../../static/image/tab2/leftimg.png" class="form-item3-main-con-l" mode="">
						</image>
						<view class="form-item3-main-con-r">
							<view class="con-r-item">
								<view class="con-r-item-l">
									<text style="font-size: 32rpx;">接收人</text>
									<text v-if="!recipientList.length"
										style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">请选择接收人</text>
									<text v-if="recipientList.length"
										style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">{{recipientList.length}}人接收</text>
								</view>
								<view class="con-r-item-r">
									<!-- 					<scroll-view :scroll-x="true" class="con-r-item-r-person-item"> -->
									<view class="flex" style="overflow-x: scroll;width: 320rpx;">
										<view class="con-r-item-r-person-item">

											<view v-for="(item,index) in recipientList" :key="item.id"
												class="con-r-item-r-person">

												<image :src="item.avatar" class="con-r-item-r-person-img" mode="">
												</image>

												<image src="../../../static/image/tab2/close.png"
													@click="closeBtn(1,item.id)" class="con-r-item-r-person-close"
													mode=""></image>
											</view>
										</view>
									</view>

									<!-- </scroll-view> -->
									<view class="upload-div" @click="addRecipient">
										+
									</view>
								</view>
							</view>
							<view class="con-r-item">
								<view class="con-r-item-l">
									<text style="font-size: 32rpx;">抄送人</text>
									<text v-if="!copiedPeople.length"
										style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">请选择抄送人</text>
									<text v-if="copiedPeople.length"
										style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">{{copiedPeople.length}}人抄送</text>
								</view>

								<view class="con-r-item-r">
									<view class="flex" style="overflow-x: scroll;width: 320rpx;">
										<view class="con-r-item-r-person-item">

											<view v-for="(item,index) in copiedPeople" :key="item.id"
												class="con-r-item-r-person">

												<image :src="item.avatar" class="con-r-item-r-person-img" mode="">
												</image>

												<image @click="closeBtn(2,item.id)"
													src="../../../static/image/tab2/close.png"
													class="con-r-item-r-person-close" mode=""></image>
											</view>
										</view>
									</view>

									<!-- </scroll-view> -->
									<view class="upload-div" @click="addCopied">
										+
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>

			</view>

		</view>

		<button type="default" class="sure_btn" @click="sureBtn">保存并提交</button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				params: {},
				recipientList: [],
				background: {
					backgroundColor: "#FFFFFF",
				},
				copiedPeople: [],
			};
		},
		onLoad(e) {
			this.params = JSON.parse(e.params);
			this.getUserList();
		},
		methods: {
			closeBtn(type, id) {
				if (type == 1) {
					let index = this.recipientList.findIndex(item => {
						item.id == id
					});
					this.recipientList.splice(index, 1)
				} else if (type == 2) {
					let index = this.copiedPeople.findIndex(item => {
						item.id == id
					});
					this.copiedPeople.splice(index, 1)
				}
			},
			getUserList() {
				let params = {
					job_id: this.params.job_id
				}
				this.$http("enterprise.jobs/getGroupDefaultUser", params, "get").then(
					res => {
						this.recipientList = res.data.data.users;
						this.copiedPeople = res.data.data.cc_person
					})
			},
			sureBtn() {
				let param = {
					cc_person: "",
					send_to_uid: ""
				};
				param.cc_person = this.recipientList.map(item => {
					return item.id
				}).join(",")
				param.send_to_uid = this.copiedPeople.map(item => {
					return item.id
				}).join(",")
				if (!param.cc_person) {
					uni.showToast({
						title: "请选择接收人",
						icon: "none"
					})
					return
				} 
				let params = Object.assign(param, this.params);
				console.log(params, "++++++++++++++++++++++++++++++++++++");
				this.$http("enterprise.Jobs/getoaauthlist?job_id=" + params.job_id, params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "保存成功",
							icon: "none"
						})
						uni.$emit("changeApprvalProcess", true)
						setTimeout(() => {
							this.$navigateBack(true, {
								delta: 2
							})
						}, 500)
					}
				})
			},
			addCopied() {
				this.$navigateTo({
					url: "/components/contact"
				}).then(res => {
					this.copiedPeople = res;
				})
			},
			addRecipient() {
				this.$navigateTo({
					url: "/components/contact"
				}).then(res => {
					this.recipientList = res;
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.con-r-item-r-person-item {
		display: flex;
		// line-height: 140rpx;
		// display: block;
		align-items: center;
		flex: 1 0 auto;
		// height: 100%;
		white-space: nowrap;
		// overflow: scroll;
		// margin-right: 16rpx;
		// width: 320rpx; // 加入width
		justify-content: flex-end;
	}

	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		position: fixed;
		bottom: 32rpx;
		left: 50%;
		transform: translateX(-50%);
	}

	page {
		background: #FBFCFF;
	}

	.form-item3 {
		padding: 32rpx 24rpx;
		background-color: #fff;

		border-radius: 18rpx;

		.form-item3-main {

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
					// height: 155rpx;
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
						height: 100%;
						align-items: center;

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
							position: relative;
							margin-right: 20rpx;
							display: inline-block;

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
								top: 0rpx;
								z-index: 9999999;
							}
						}

						// .con-r-item-r-person::before{
						// 	content: "";
						// 	background: url(../../../static/image/login/home.png);
						// 	width: 50rpx;
						// 	height: 50rpx;
						// 	position: absolute;
						// 	bottom: -10rpx;
						// 	left: 0;
						// 	z-index: 9999999;
						// }
					}
				}
			}
		}
	}
</style>

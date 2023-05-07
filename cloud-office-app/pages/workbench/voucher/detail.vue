<template>
	<view>
		<u-navbar :is-back="true" title="凭证详情" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
			<text slot="right" style="margin-right: 32rpx;" @click="delBtn">删除</text>
		</u-navbar>
		<view class="comBox">
			<view class="headerBox">
				<view style="font-size: 30rpx;">
					{{detailData.years}}-{{Math.abs(detailData.month)}}-{{detailData.day}}
				</view>
				<view style="color: #4396F7;margin-top: 20rpx;">
					摘要：{{detailData.abstract}}
				</view>
			</view>

			<view class="mainBox">
				<view class="main-item">
					<view class="main-item-one" v-for="(item,index) in detailData.childs" :key="index">
						<view class="main-item-one-l">
							{{item.subject_headings}}
						</view>
						<view class="main-item-one-m" :class="{green:item.balance_status == -1}">
							{{item.balance_status == -1 ? "借" : "贷"}}
						</view>
						<view class="main-item-one-r">
							{{item.balance_status == -1 ? item.borrower:item.lender}}
						</view>
					</view>
					<!-- 		<view class="main-item-one main-item-tow">
						<view class="main-item-one-l">
							4001 生产成本
						</view>
						<view class="main-item-one-m green">
							借
						</view>
						<view class="main-item-one-r">
							100
						</view>
					</view> -->
				</view>



				<view class="main-item flex align-center" style="background: #fff;">
					<view class="main-item-l"
						style="font-size: 36rpx;font-weight: bold;margin-right: 234rpx;margin-left: 24rpx;">
						合计
					</view>
					<view class="main-item-r" style="flex: 1;">
						<view class="main-item-one">

							<view class="main-item-one-m">
								贷
							</view>
							<view class="main-item-one-r">
								{{detailData.credit_total_fee}}
							</view>
						</view>
						<view class="main-item-one main-item-tow">

							<view class="main-item-one-m green">
								借
							</view>
							<view class="main-item-one-r">
								{{detailData.debit_total_fee}}
							</view>
						</view>
					</view>

				</view>


				<view class="userBox flex justify-between">
					<view class="user-l">
						制单人：<text style="color: #150E33;">{{detailData.recorder_name}}</text>
					</view>
					<view class="user-r flex">
						附单据 {{imgNum}}张 <view class="user-r-btn" @click="viewBtn">
							查看
						</view>
					</view>
				</view>

					<view class="imgBox" v-if="docuimgs != 0">
						<u-swiper :list="detailData.documents_images" height="360" :autoplay="false" img-mode="aspectFill" @click="bigimg()"></u-swiper>
				</view>
			</view>
		</view>

		<button type="default" class="sure_btn" @click="editBtn()">编辑</button>
		<u-modal v-model="isShowDel" content="你确定删除?" @confirm="sureAgree" :show-cancel-button="true"
			:show-confirm-button="true" :show-title="false">
		</u-modal>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isShowDel: false,
				currentId: "",
				detailData: '',
				childimg:'../../../static/image/tab1/caiwu/22x.png',
				background: {
					backgroundColor: "#FFFFFF",
				},
				docuimgs:'',
				years:'',
				month:''
			};
		},
		onLoad(e) {
			this.currentId = e.id;
			this.getDetail();
		},
		computed: {
			imgNum() {
				return this.detailData.documents_images ? this.detailData.documents_images.length : 0
			}
		},
		methods: {
			bigimg(){
				uni.previewImage({
					urls: this.detailData.documents_images
				})
			},
			sureAgree() {
				let params = {
					key: this.detailData.id
				}
				this.$http("enterprise.Credentials/delCredentials", params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "删除成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			delBtn() {
				this.isShowDel = true;

			},
			viewBtn() {
				uni.navigateTo({
					url: "/pages/workbench/voucher/viewImg?imgData=" + JSON.stringify(this.detailData
						.documents_images)
				})
			},
			editBtn() {
				console.log(this.years)
				console.log(this.month)
				let params = {
					years: this.years,
					month:this.month
				}
				this.$http("enterprise.Date_query/getAudit", params, "get").then(res => {
					if (res.data.code == 1) {
						console.log(res.data.data)
						if(res.data.data == 0){
							this.$navigateTo({
								url: "/pages/workbench/voucher/editPage?data=" + JSON.stringify(this.detailData)
							}).then(res=>{
								this.getDetail();
							})
						}else{
							uni.showToast({
								title: "已结账，不能编辑凭证",
								icon: "none"
							})
						}
					}
				})
				
			},
			getDetail() {
				let params = {
					id: this.currentId
				}
				this.$http("enterprise.Credentials/ViewCredentials", params, "get").then(res => {
					if (res.data.code == 1) {
						this.detailData = res.data.data;
						this.years = res.data.data.years
						this.month = Math.abs(res.data.data.month)
						this.detailData.documents_images = this.detailData.documents_images ? this.detailData
							.documents_images.split(",") : [],
							this.docuimgs = this.detailData.documents_images.length
							
					}
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		margin-top: 32rpx;
		margin-bottom: 32rpx;
	}

	page {
		background: #FBFCFF;
		color: #150E33;
	}

	.imgBox {
		margin-top: 32rpx;
	}

	.userBox {
		padding: 38rpx 24rpx;
		font-size: 32rpx;
		margin-top: 24rpx;
		background-color: #fff;
		border-radius: 16rpx;

		.user-l {
			color: #7A7C94;
		}

		.user-r-btn {
			width: 88rpx;
			height: 42rpx;
			background: #4396F7;
			color: #fff;
			margin-left: 18rpx;
			font-size: 26rpx;
			text-align: center;
			line-height: 42rpx;
			border-radius: 21rpx;
		}
	}

	.headerBox {
		padding: 0 32rpx;
		margin-top: 32rpx;
	}

	.mainBox {
		padding: 0 32rpx;

		.green {
			background-color: #12D592 !important;
		}

		.main-item {
			background: #fff;
			margin-top: 32rpx;
			border-radius: 16rpx;

			.main-item-one {
				font-size: 32rpx;
				padding: 0 24rpx;
				display: flex;
				align-items: center;
				justify-content: space-between;
				height: 104rpx;
				border-radius: 20rpx 20rpx 0 0;
				line-height: 104rpx;
				background-color: #fff;
				border-bottom: 1px solid #EEF2FF;

				.main-item-one-l {
					width: 48%;
				}

				.main-item-one-r {
					width: 40%;
					text-align: right;
				}

				.main-item-one-m {
					font-size: 28rpx;
					width: 46rpx;
					height: 46rpx;
					color: #fff;
					background: #FF253D;
					border-radius: 50%;
					text-align: center;
					line-height: 45rpx;
				}
			}

			.main-item-tow {
				border-radius: 0 0 20rpx 20rpx;
			}
		}
	}
</style>

<template>
	<view class="detailBox">
		<u-navbar :is-back="true" title="聊天记录" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		
	
	</view>
</template>

<script>
	export default {
		data() {
			return {
				nameArr: [],
				join_ids: [],
				imgData: [],
				background: {
					backgroundColor: "#FFFFFF",
				},
				currentId: "",
				detailData: {},
				loading: false

			};
		},
		onLoad(e) {
			this.currentId = e.id;
			this.getDetail();
		},
		methods: {
			previewImg() {
				uni.previewImage({
					urls: this.imgData
				})
			},
			sureBtn() {
				let params = {
					id: this.currentId
				}
				if (this.loading) {
					return
				}
				this.loading = true

				this.$http("enterprise.User_todo/complete?id=" + this.currentId, {}, "post", 2).then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "完成",
							icon: "none"
						})
						setTimeout(() => {
							this.loading = false

							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			getDetail() {
				let params = {
					id: this.currentId
				}
				this.$http("enterprise.User_todo/Dateils", params, "get").then(res => {
					this.detailData = res.data.data;
					this.imgData = this.detailData.images.split(",");
					this.nameArr = [];
					this.detailData.join_ids.forEach(item => {
						this.nameArr.push(item.username)
					})
					this.join_ids = this.nameArr.slice(0, 2).join(",")
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
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

	.mainBox {
		padding: 0 32rpx;
		height: 82vh;
		overflow-y: auto;

		.detailBox-bd {
			text-align: center;
			color: #B5BFDA;
			margin-top: 60rpx;
		}

		.itemBox {
			display: flex;
			align-items: center;
			margin-top: 50rpx;

			.itemDiv {
				padding: 6rpx 16rpx;
				border-radius: 36rpx;
				color: #7A7C94;
				font-size: 26rpx;
				border: 1px solid #B5BFDA;
				margin-right: 30rpx;
			}

			.item-img {
				width: 24rpx;
				height: 26rpx;
				margin-right: 8rpx;
			}

			.active {
				border: 1px solid #4396F7;
				color: #4396F7;
			}


		}

		.img-item-box {
			display: flex;
			flex-wrap: wrap;
			margin-top: 50rpx;
			margin-right: -24rpx;

			.img-item {
				width: 212rpx;
				height: 212rpx;
				margin-right: 24rpx;
				margin-bottom: 24rpx;
			}
		}

		.item-con {
			color: #150E33;
			font-size: 28rpx;
			margin-top: 50rpx;
		}

		.header-title {
			font-size: 32rpx;
			font-weight: bold;
			color: #150E33;
			margin-top: 34rpx;
		}
	}
</style>
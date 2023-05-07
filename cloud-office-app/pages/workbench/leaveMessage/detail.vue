<template>
	<view class="detailBox">
		<u-navbar :is-back="true" title="留言详情" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="header-title">
				{{detailData.content}}
			</view>
			<view class="itemBox">
				<view class="itemDiv active">
					<image class="item-img" src="../../../static/image/tab1/my1.png" mode=""></image>
					我执行
				</view>
				<view class="itemDiv">
					<image class="item-img" src="../../../static/image/tab1/my.png" mode=""></image>
					{{join_ids}}等{{nameArr.length}}人参与
				</view>
			</view>

			<view class="itemBox" style="margin-top: 24rpx;">
				<view class="itemDiv">
					<image class="item-img" src="../../../static/image/tab1/time.png" mode=""></image>
					{{detailData.end_time}} 截止
				</view>
			</view>

			<view class="item-con">
				{{detailData.desc}}
			</view>

			<view class="img-item-box">
				<image v-for="(item,index) in imgData" @click="previewImg" class="img-item" :src="item" mode="">
				</image>
			</view>

			<view class="detailBox-bd">
				<view class="" v-for="(item,index) in detailData.record" :key="index">
					{{item.title.substring(0,item.title.length-5)}} <text style="color: #12D592;margin-left: 6rpx;">{{item.title.substring(item.title.length-5)}}</text>
				</view>
			</view>
		</view>

		<button v-if="detailData.status == 0" type="default" class="sure_btn" @click="sureBtn">完成留言</button>

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
				detailData: {}
			};
		},
		onLoad(e) {
			this.currentId = e.id;
			this.getDetail();
		},
		methods: {
			previewImg(){
				uni.previewImage({
					urls:this.imgData
				})
			},
			sureBtn() {
				let params = {
					id: this.currentId
				}
				this.$http("enterprise.User_todo/complete?id="+this.currentId, {}, "post",2).then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "完成",
							icon: "none"
						})
						setTimeout(() => {
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

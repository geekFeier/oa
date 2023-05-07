<template>
	<view class="vipBox">
		<u-navbar :is-back="true" back-icon-color="#000" :background="background" title="会员开通" :height="55">
		</u-navbar>
		<view class="userMain">
			<view class="userInfo">
				<view class="userInfo_left">
					<view class="userInfo_imgDiv">
						<image :src="userInfo.avatar" mode="" class="userInfo_pic"></image>
					</view>
					<view class="userInfo_dsc">
						<view class="userInfo_name">
							{{userInfo.nickname}}
						</view>
						<view class="userInfo_status" v-if="userInfo.is_vip == 0">
							你暂时未开通会员
						</view>
						<view class="userInfo_status" v-else>
							年费会员
						</view>
					</view>
				</view>
				<view class="userInfo_right" @click="goPay(viplist.price)">
					<text style="margin-left: 5px;font-size: 22rpx;">开通会员</text>
				</view>
			</view>

		</view>



		<view class="mainBox">
			<view class="typeBox">
				<!-- 	<view class="title">
					开通类型
				</view> -->
				<view class="type-item-box">
					<view class="type-item active" @click="changeIndex(index)">
						<text style="color: #150E33;font-size: 32rpx;font-weight: bold;">{{viplist.title}}</text>
						<text style="color: #FF0000;
					font-size: 56rpx;font-weight: bold;">￥{{viplist.price}}</text>
						<text style="color: #7A7C94;font-size: 26rpx;">{{viplist.mprice}}元/月</text>

					</view>
				</view>
			</view>

			<view class="memberServices">
				<view class="memberServices-title">
					- 会员服务 -
				</view>
				<view class="memberServices_con">
					<u-grid :col="4" :border="false" style="margin-top: 12rpx;">
						<u-grid-item @click="goDilatation">
							<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
								src="../../../static/image/my/krsq.png" mode="">
							</image>
							<view class="grid-text">扩容申请</view>
						</u-grid-item>
						<u-grid-item>
							<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
								src="../../../static/image/my/ggfb.png" mode="">
							</image>
							<view class="grid-text">广告发布</view>
						</u-grid-item>
					</u-grid>
				</view>
			</view>


			<view class="vipDesc">
				<view class="title">
					会员权益
				</view>
				
				<view class="desc" style="color: #7A7C94;font-size: 26rpx;margin-top: 32rpx;">
					<u-parse :html="viplist.content"></u-parse>
				</view>
				<!-- <image style="width: 100%;margin-top: 32rpx;" src="../../../static/image/tab1/caiwu/22x.png"></image> -->
			</view>



		</view>
		<view class="btnBox">
			<view class="btn" @click="goPay(viplist.price)">
				开通会员
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState
	} from "vuex";
	import dayjs from '@/utils/dayjs'
	export default {
		data() {
			return {
				currentIndex: 0,
				background: {
					backgroundColor: "#FFFFFF",
				},
				// userInfo: {},
				typeList: [],
				vipInfo: {},
				viplist:''
			};
		},
		onLoad() {
			this.getnum()
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo,
			})
		},
		methods: {
			getnum(){
				console.log(this.userInfo,">>>>>>>>>")
				this.$http("User/getMemberDateils", "get").then(res => {
					if (res.data.code == 1) {
						this.viplist = res.data.data[0]
					}
				})
			},
			goAdvertising(){
				uni.navigateTo({
					url:"/pages/my/vip/advertising/index"
				})
			},
			goDilatation(){
				uni.navigateTo({
					url:"/pages/my/vip/dilatation/index"
				})
			},
			goPay(num){
				uni.navigateTo({
					url:"/pages/my/vip/payPage?price=" + num
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.btnBox {
		position: fixed;
		bottom: 0;
		height: 120rpx;
		width: 100%;
		background: #fff;
		display: flex;
		justify-content: center;
		align-items: center;

		.btn {
			// margin-top: 24rpx;
			height: 98rpx;
			background: url(../../../static/image/my/btn22.png) no-repeat;

			width: 90%;
			text-align: center;
			line-height: 98rpx;
			color: #150E33;
			font-size: 30rpx;
			background-size: 100% 100%;
		}
	}

	.memberServices {
		margin-top: 32rpx;

		border-radius: 20rpx;
		background-color: #fff;
		padding: 32rpx 24rpx;
		padding-bottom: 0;
		.memberServices-title {
			text-align: center;
			color: #7A7C94;
			font-size: 30rpx;
			
		}
		/deep/ .grid-icon{
			margin-bottom: 20rpx !important;
		}
	}

	.userMain {
		// background: #03498E;
		background-image: url(../../../static/image/my/hyvip.png);
		height: 252rpx;
		background-size: 100% 100%;
		width: 100%;
		// position: absolute;
		position: relative;
		display: flex;
		align-items: center;
	}

	.mainBox {
		padding: 0 32rpx;
		padding-bottom: 140rpx;

		.title {
			font-size: 32rpx;
			color: #150E33;
			margin-top: 40rpx;
		}

		.typeBox {
			.type-item-box {
				margin-top: 32rpx;
				display: flex;
				justify-content: space-around;
				background-color: rgba(251, 252, 255, 1);

				.type-item {
					flex: 1;
					// width: 400rpx;
					height: 300rpx;
					border: 1px solid #B5BFDA;
					border-radius: 24rpx;
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-content: space-around;
					padding: 8rpx 0;
				}

				.active {
					background: rgba(236, 184, 89, 0.15);
					border: 1px solid rgba(236, 184, 89, 1);
				}
			}
		}
	}

	.userInfo_status {
		color: #fff;
		font-size: 26rpx;
		margin-top: 12rpx;
	}

	.userInfo {
		// margin-top: 20px;
		// padding-top: 20px;
		padding: 20px 15px;
		justify-content: space-between;
		display: flex;
		box-sizing: border-box;
		align-items: center;
		flex: 1;

		.userInfo_left {
			display: flex;
			align-items: center;

			.userInfo_imgDiv {
				width: 145rpx;
				background-image: url(../../../static/image/my/22.png);
				background-repeat: no-repeat;
				background-size: 145rpx;
				display: flex;
				justify-content: center;
				align-items: center;
				height: 145rpx;

				.userInfo_pic {
					width: 125rpx;
					height: 125rpx;
					border-radius: 50%;
				}
			}

			.userInfo_dsc {
				margin-left: 10px;
				display: flex;
				flex-direction: column;
				justify-content: flex-start;

				.userInfo_name {
					font-size: 36rpx;
					font-weight: bold;
					color: #fff;
					font-weight: 500;
				}

				.userInfo_account {
					margin-left: 4rpx;
					color: rgba(255, 255, 255, .7);
				}

				.userInfo_grade {
					margin-top: 4px;
				}
			}
		}

		.userInfo_right {
			color: #150E33;
			display: flex;
			align-items: center;
			justify-content: center;
			height: 58rpx;
			width: 180rpx;
			background: url(../../../static/image/my/btnBg.png) no-repeat;
			background-size: 100% 100%;
		}
	}
</style>

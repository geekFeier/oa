<template>
	<view>
		<view class="userMain">
			<view class="headerBox">
				<view class="unit-info">
					<image class="unit-icon" v-if="enterprice.enterprice_image"
						:src="getUrl(enterprice.enterprice_image) " mode=""></image>
					<text class="unit-title u-line-2">{{enterprice.enterprice_name}}</text>

				</view>
			</view>
			<view class="userInfo">
				<view class="userInfo_left">
					<view class="userInfo_imgDiv">
						<image v-if="userInfo.avatar" :src="userInfo.avatar" mode="" class="userInfo_pic"></image>

					</view>
					<view class="userInfo_dsc">
						<view class="userInfo_name">
							{{userInfo.username}}
							<image @click="editBtn" src="../../../static/image/my/edit.png"
								style="width: 30rpx;height: 30rpx;margin-left: 16rpx;" mode=""></image>
						</view>

					</view>
				</view>

			</view>
			<view class="userPrice">
				<view class="hy-l">
					<image style="width: 43rpx;height: 34rpx;" src="../../../static/image/my/hz.png" mode="">
					</image>
					<text style="margin-left: 17rpx;">开通会员享受会员权益</text>
				</view>
				<view class="kt_vip_btn" @click="jumpTo('vip')">
					{{userInfo.is_vip==0? '开通会员':'续费会员'}}

				</view>
			</view>
		</view>

		<view class="mainBox">
			<view class="enterpriseCloud-box" v-if="userInfo.config">
				<view class="enterpriseCloud-title" v-if="enterprice.enterprice_name">
					{{enterprice.enterprice_name?enterprice.enterprice_name:""}}
				</view>

				<progress class="progress-div"
					:percent="GetPercent(userInfo.config.pan_info.use,userInfo.config.pan_info.size)" stroke-width="10"
					backgroundColor="#EEF2FF" />
				<view class="status-div">
					<text class="status-l">{{userInfo.config.pan_info.use}}M/{{userInfo.config.pan_info.size}}M</text>
					<text
						class="status-r">已使用{{GetPercent(userInfo.config.pan_info.use,userInfo.config.pan_info.size)}}</text>
				</view>
				<view class="flex justify-end">
					<view class="dilatation-btn" @click="kuoRong">
						扩容
					</view>
				</view>
			</view>

			<view class="file-box">
				<view class="file-item" v-for="(item,index) in listData" :key="index"
					@click="goMyFile(item.pan_id,item.id)">
					<image src="../../../static/image/my/file.png" class="file-img" mode=""></image>
					<view class="file-r">
						<view class="file-r-name">
							{{item.name}}
						</view>
						<!-- 	<view class="file-r-size">
							{{userInfo.config.dir[0].size}}M
						</view> -->
					</view>
				</view>

			</view>

			<!-- 		<view class="recent-doc-box">
				<view class="recent-doc-header">
					<view class="recent-doc-header-l">
						最近文档
					</view>
					<view class="recent-doc-header-r">
						更多
						<u-icon name="arrow-right" color="#B5BFDA" size="28"></u-icon>
					</view>
				</view>
				<view class="recent-doc-file">
					<image class="recent-doc-file-img" src="../../../static/image/my/excel.png" mode=""></image>
					<view class="recent-doc-file-r">
						<view class="recent-doc-file-name">
							文档名称-工作计划表.exel
						</view>
						<view class="recent-doc-file-date">
							2021-01-12 12:21:41
						</view>
					</view>
				</view>
			</view> -->

			<view class="main-bd">
				<u-cell-group :border="false">
					<u-cell-item title="会员服务" @click="jumpTo('vip')"></u-cell-item>
					<u-cell-item title="意见反馈" @click="jumpTo('feedback')"></u-cell-item>
					<u-cell-item v-if="userInfo.config" title="联系我们"
						:value="userInfo.config.phone ? userInfo.config.phone : ''" @click="phoneCall"></u-cell-item>


					<u-cell-item title="开发票" @click="jumpTo('invoice')"></u-cell-item>
					<u-cell-item title="设置" @click="jumpTo('setting')"></u-cell-item>
					<!-- <u-cell-item title="资产负载表" @click="zcfz()"></u-cell-item> -->
				</u-cell-group>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		url_config,
		img_url
	} from "@/config/config.js"

	import {
		mapState
	} from "vuex"
	export default {
		data() {
			return {
				listData: [],
				percentage: "55"
			};
		},
		computed: {
			// ...mapState({
			// 	userInfo: state => state.user.userInfo,
			// 	// phone:state => state.user.userInfo.config.phone,
			// 	enterprice: state => state.user.enterprise,
			// 	personType: state => state.user.personType,

			// })
		},
		// onShow() {
		// 	if(!this.userInfo.config){
		// 		uni.navigateTo({
		// 			url:"/pages/login/index"
		// 		})
		// 	}
		// },
		onLoad() {
			this.personType = uni.getStorageSync('personType')
			this.enterprice = uni.getStorageSync('enterprise')

			this.getFileList()
		},
		onShow() {
			this.getUserInfo();
		},

		methods: {
			getUserInfo() {
				this.$http("/User/getUser", {}, "post").then(res => {
					if (res.data.code == 1) {
						this.userInfo = res.data.data
						this.$store.dispatch("user/GET_USER_INFO", res.data.data);

					}
				})
			},
			getUrl(url) {
				return img_url + url
			},

			getFileList(id) {
				this.$http("enterprise.cloud_pan/getSelfDir", {}, "get").then(res => {
					if (res.data.code == 1) {
						console.log('目录列表', res.data)
						this.listData = res.data.data
					}
				})
			},
			/// 求百分比
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
				return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00) + "%";
			},
			kuoRong() {
				uni.navigateTo({
					url: "/pages/my/vip/dilatation/index"
				})
			},
			goMyFile(pan_id, dir_id) {
				uni.navigateTo({
					url: `/pages/my/cloudPan/fileList?pan_id=${pan_id}&dir_id=${dir_id}`
				})
			},
			editBtn() {
				this.$navigateTo({
					url: "/pages/my/editPage"
				})
			},
			phoneCall() {
				uni.makePhoneCall({
					phoneNumber: this.userInfo.config.phone,
				})
			},
			jumpTo(val) {
				uni.navigateTo({
					url: `/pages/my/${val}/index`
				})
			},
			// zcfz(){
			// 	uni.navigateTo({
			// 		url:'../../my/zcfzlist'
			// 	})
			// },
			goSetting() {

			}
		}
	}
</script>

<style lang="scss" scoped>
	/deep/ .uni-progress-inner-bar {
		border-radius: 14rpx !important;
	}

	/deep/ .uni-progress-bar {
		border-radius: 14rpx !important;

	}

	page {
		background: #FBFCFF;
	}

	.main-bd {
		margin-top: 24rpx;
		border-radius: 20rpx;
		background-color: #fff;
	}

	.recent-doc-box {
		padding: 24rpx 32rpx;
		margin-top: 24rpx;
		background: #fff;
		border-radius: 20rpx;

		.recent-doc-header {
			display: flex;
			justify-content: space-between;
			padding-bottom: 32rpx;
			border-bottom: 1px solid #EEF2FF;

			.recent-doc-header-l {
				color: #7A7C94;
				font-size: 30rpx;
			}

			.recent-doc-header-r {
				color: #B5BFDA;
				font-size: 26rpx;
				margin-right: 16rpx;
			}
		}

		.recent-doc-file {
			margin-top: 32rpx;
			display: flex;

			.recent-doc-file-img {
				width: 100rpx;
				height: 100rpx;
			}

			.recent-doc-file-r {
				margin-left: 24rpx;

				.recent-doc-file-name {
					font-size: 32rpx;
				}

				.recent-doc-file-date {
					color: #B5BFDA;
					font-size: 26rpx;
					margin-top: 24rpx;
				}
			}
		}
	}

	.file-box {
		display: flex;
		justify-content: space-between;
		margin-top: 24rpx;

		.file-item {
			display: flex;
			align-items: center;
			width: 337rpx;
			height: 142rpx;
			background: #fff;
			border-radius: 16rpx;
			padding: 32rpx 60rpx;
			box-sizing: border-box;

			.file-img {
				width: 62rpx;
				height: 54rpx;
			}

			.file-r {
				margin-left: 32rpx;

				.file-r-name {
					font-size: 30rpx;

				}

				.file-r-size {
					color: #B5BFDA;
					font-size: 28rpx;
					margin-top: 24rpx;
				}
			}

		}
	}

	.userMain {
		// background: #03498E;
		background-image: url(../../../static/image/my/bg.png);
		background-repeat: no-repeat;
		background-size: 100% 100%;
		height: 411rpx;
		width: 100%;
		// position: absolute;
		position: relative;

		.headerBox {
			width: 750rpx;
			padding: 0 32rpx;
			box-sizing: border-box;
			background-size: 100% 100%;
			overflow: hidden;

			// padding-top: var(--status-bar-height);
			.unit-info {
				/* #ifdef APP-PLUS */
				margin-top: var(--status-bar-height);
				/* #endif */
				/* #ifndef APP-PLUS */
				margin-top: 31rpx;
				/* #endif */
				display: flex;
				align-items: center;

				.unit-icon {
					width: 70rpx;
					height: 70rpx;
					border-radius: 50%;

				}

				.unit-title {
					margin-left: 20rpx;
					font-weight: 700;
					font-size: 40rpx;
					color: #FFFFFF;
				}
			}

			.bannerBox {
				margin-top: 42rpx;
			}
		}


		.userInfo {
			margin-top: 20rpx;
			// padding-top: 20px;
			padding: 0 32rpx;
			justify-content: space-between;
			display: flex;
			box-sizing: border-box;
			align-items: center;

			.userInfo_left {
				display: flex;
				align-items: center;

				.userInfo_imgDiv {
					width: 155rpx;
					height: 155rpx;
					background-image: url(../../../static/image/my/22.png);
					background-repeat: no-repeat;
					background-size: 155rpx;
					display: flex;
					justify-content: center;
					align-items: center;

					.userInfo_pic {
						width: 130rpx;
						height: 130rpx;
						border-radius: 50%;
					}
				}

				.userInfo_dsc {
					margin-left: 10px;
					display: flex;
					flex-direction: column;
					justify-content: flex-start;

					.userInfo_name {
						font-size: 22px;
						color: #fff;
						font-weight: 500;
						width: 360rpx;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap
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
				color: #fff;
				display: flex;
				align-items: center;
				justify-content: center;
				height: 42rpx;
				width: 108rpx;

				border-radius: 18px;
				background: #FF7704;
			}
		}

		.userPrice {
			border-radius: 10px;
			height: 144rpx;
			bottom: -75rpx;
			left: 50%;
			transform: translateX(-50%);
			width: 90%;
			position: absolute;
			// box-shadow: 1px 1px 1px rgba(0,0,0,.4);
			padding: 0 33rpx;
			box-sizing: border-box;
			background: url(../../../static/image/my/1.png) no-repeat;
			background-size: 100% 100%;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.hy-l {
				font-size: 32rpx;
				display: flex;
				align-items: center;
			}

			.kt_vip_btn {
				width: 150rpx;
				height: 56rpx;
				border-radius: 33rpx;
				background-color: #fff;
				text-align: center;
				line-height: 56rpx;
				font-size: 24rpx;
				color: #150E33;
			}
		}

	}

	.mainBox {
		padding: 0 32rpx;
		margin-top: 100rpx;

		.enterpriseCloud-box {
			background: #fff;
			// background-color: pink;
			padding: 32rpx 24rpx;
			border-radius: 20rpx;

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
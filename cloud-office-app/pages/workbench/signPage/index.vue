<template>
	<view class="signBox">
		<u-navbar :is-back="true" title="签到" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox" v-if="isShow==1">
			<view class="header-div">
				<image :src="userInfo.avatar" class="header-l" mode=""></image>
				<view class="header-r">
					<view class="header-name">
						{{userInfo.username}}
					</view>
					<text class="header-unit">{{enterinfo.enterprice_name}}</text>
				</view>
			</view>

			<view class="mainBox-con">
				<view class="mainBox-item">
					<view class="mainBox-item-hd">
						上班{{enterinfo.morning_attendance_time}}
					</view>
					<view class="mainBox-item-bd">
						<image v-if="isDutyed" style="width: 24rpx;height: 24rpx;margin-right: 8rpx;"
							src="../../../static/image/login/select-a.png" mode=""></image>
					
								{{ isDutyed ? dutyedText : '未打卡'}}
					</view>
				</view>
				<view class="mainBox-item">
					<view class="mainBox-item-hd">
						下班{{enterinfo.eventing_attendance_time}}
					</view>
					<view class="mainBox-item-bd">
						<image v-if="isOffDutyed" style="width: 24rpx;height: 24rpx;margin-right: 8rpx;"
							src="../../../static/image/login/select-a.png" mode=""></image>
					<!-- 	{{enterinfo.today.evening_sign_status!=-1 ? enterinfo.today.evening_sign_status : '未打卡'}} -->
					{{ isOffDutyed ? dutyedOffText : '未打卡'}}
					</view>
				</view>
			</view>

			<view class="mainBox-btn" @click="signBtn">
				<view class="mainBox-btn-status">
					{{isDuty ? "上班打卡" : (isDutyed ? "下班打卡" : (isOffDutyed?"更新打卡":"下班打卡") )}}
				</view>
				<view class="mainBox-btn-time">{{date}}</view>
			</view>


			<view class="mainBox-status" v-if="isCanPunch">
				<image style="width: 24rpx;height: 24rpx;margin-right: 8rpx;"
					src="../../../static/image/login/select-a.png" mode=""></image>
				已进入考勤范围：{{address}}
			</view>

		</view>

		<view class="mainBox" v-if="isShow==2">
			<view class="success-header">
				<view class="success-header-t">
					<text style="font-size: 40rpx;color: #150E33;">打卡成功</text>
					<u-icon name="close" color="#B5BFDA" size="40" @click="closeBtn"></u-icon>
				</view>
				<view class="success-header-b">
					打卡时间 {{isDuty ? is_sign.morning_sign_time : is_sign.evening_sign_time}}
				</view>

			</view>

			<image class="success-img" src="../../../static/image/tab1/success.png" mode=""></image>

		</view>

	</view>
</template>

<script>
	import {
		mapState
	} from "vuex";
	import dayjs from "@/utils/dayjs.min.js";
	var timer;
	var timer2;
	var EARTH_RADIUS = 6378137.0; //单位M
	var PI = Math.PI;

	function getRad(d) {
		return d * PI / 180.0;
	}
	export default {
		data() {
			return {
				currentHour: "",
				isCanPunch: false,
				currentLatitude: "",
				currentLongitude: "",
				date: "",
				isShow: 1,
				enterinfo: "",
				is_sign: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
				distance: 0,
				address: "",
				dutyedText:"",
				dutyedOffText:""
			};
		},
		onLoad() {
			this.date = dayjs().format("HH:mm:ss");
			this.currentHour = dayjs().hour();
			timer2 = setInterval(() => {
				this.date = dayjs().format("HH:mm:ss")
				this.currentHour = dayjs().hour();

			}, 1000)
			this.getInfo();
			this.getLocaltion();
			timer = setInterval(() => {
				this.getLocaltion();
			}, 3000)
		},
		onUnload() {
			clearInterval(timer)
			clearInterval(timer2)
		},
		onShow() {},
		watch:{
			enterinfo:{
				deep:true,
				handler(val){
					this.enterinfo = val;
				}
			}
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo
			}),
			isDutyed() {
				if (this.is_sign && Number(this.is_sign.morning_sign_status) != -1 ) {
					this.dutyedText = this.is_sign.morning_sign_time;
					return true
				}else{
					return false
				}
			},
			isOffDutyed() {
				if (this.is_sign && Number(this.is_sign.evening_sign_status) != -1 ) {
					this.dutyedOffText = this.is_sign.evening_sign_time;
					return true
				}else{
					return false
				}
			},
			isDuty() {
				let hours = 12;
				if(this.enterinfo.e_morning_off_work_time){
					hours = Number(this.enterinfo.e_morning_off_work_time.substring(0,2))
				}
				if (this.currentHour >= hours) {
					return false
				} else if (this.currentHour < hours) {
					return true
				}
			},
		},
		methods: {
			getLocaltion() {
				uni.getLocation({
					geocode: true,
					success: (res) => {
						console.log(res, "asDDDDDDDDDDDDDDDDDDDDD");
						if (res.address) {
							this.address =
								`${res.address.district}${res.address.street}${res.address.streetNum}`
						}
						this.currentLatitude = res.latitude;
						this.currentLongitude = res.longitude;
						this.distance = Math.floor(this.getGreatCircleDistance(res.latitude, res.longitude,
							this.enterinfo.latitude, this.enterinfo.longitude));
						if (Number(this.enterinfo.sign_distance) == -1) {
							this.isCanPunch = false
						} else {
							if (this.distance > Number(this.enterinfo.sign_distance)) {
								this.isCanPunch = false
							} else if (this.distance <= Number(this.enterinfo.sign_distance)) {
								this.isCanPunch = true;
							}
						}
						this.$forceUpdate()

					},
					fail: (err) => {
						uni.showToast({
							title: "获取定位权限失败",
							icon: "none"
						})
						clearInterval(timer)
					}
				})
			},
			/* 计算距离（米） */
			getGreatCircleDistance(lat1, lng1, lat2, lng2) {
				var radLat1 = getRad(lat1);
				var radLat2 = getRad(lat2);

				var a = radLat1 - radLat2;
				var b = getRad(lng1) - getRad(lng2);

				var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math
					.pow(Math.sin(b / 2), 2)));
				s = s * EARTH_RADIUS;
				s = Math.round(s * 10000) / 10000.0;

				return s
			},
			getInfo() {
				this.$http("enterprise/Staff/getIsSign", {}, "get").then(res => {
					if (res.data.code == 1) {
						this.is_sign = res.data.data.today;
						this.enterinfo = res.data.data.enterinfo;
					}
				})
			},
			signBtn() {
				// if (this.isCanPunch) {
				let params = {
					latitude: this.currentLatitude,
					longitude: this.currentLongitude,
				}
				this.$http("enterprise/Staff/sign", params, "post").then(res => {
					if (res.data.code == 1) {
						this.getInfo();
						this.isShow = 2;
					}
				})
				// } else {
				// 	uni.showToast({
				// 		title: "请到规定打卡区域!",
				// 		icon: "none"
				// 	})
				// }
			},
			closeBtn() {
				this.isShow = 1;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.mainBox {
		padding: 0 32rpx;

		.noCanDiv {
			background-color: #ccc !important;
		}

		.success-header {
			padding: 0 32rpx;
			margin-top: 72rpx;

			.success-header-t {
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-weight: bod;
			}

			.success-header-b {
				font-size: 26rpx;
				color: #7A7C94;
				margin-top: 24rpx;
			}
		}

		.success-img {
			width: 220rpx;
			height: 220rpx;
			margin: 142rpx auto;
			text-align: center;
			display: flex;
		}

		.mainBox-btn {
			margin: 0 auto;
			margin-top: 90rpx;
			width: 340rpx;
			height: 340rpx;
			background: url(../../../static/image/tab1/qiandaoBg.png) no-repeat;
			overflow: hidden;
			background-size: 100% 100%;
			text-align: center;

			.mainBox-btn-status {
				color: #FFFFFF;
				font-size: 36rpx;
				margin-top: 120rpx;
			}

			.mainBox-btn-time {
				color: rgba(255, 255, 255, .6);
				font-size: 32rpx;
				margin-top: 14rpx;
			}

		}

		.mainBox-status {
			display: flex;
			align-items: center;
			justify-content: center;
			color: #7A7C94;
			margin-top: 6rpx;
		}

		.header-div {
			margin-top: 64rpx;
			padding: 0 32rpx;
			box-sizing: border-box;
			display: flex;

			.header-l {
				width: 100rpx;
				height: 100rpx;
				border-radius: 12rpx;
			}

			.header-r {
				padding: 12rpx 0;
				margin-left: 24rpx;
				display: flex;
				flex-direction: column;
				justify-content: space-around;

				.header-name {
					font-size: 32rpx;
					color: #150E33;
				}

				.header-unit {
					color: #7A7C94;
					font-size: 26rpx;
				}
			}
		}

		.mainBox-con {
			display: flex;
			margin-top: 60rpx;
			padding: 0 32rpx;
			justify-content: space-between;

			.mainBox-item {
				padding: 24rpx;
				width: 303rpx;
				background: #F6F9FE;
				opacity: 1;
				border-radius: 16px;

				.mainBox-item-hd {
					color: #150E33;
					font-size: 32rpx;
				}

				.mainBox-item-bd {
					color: #7A7C94;
					display: flex;
					align-items: center;
					margin-top: 16rpx;
				}
			}
		}
	}
</style>

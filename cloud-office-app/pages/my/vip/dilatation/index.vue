<template>
	<view>
		<u-navbar :is-back="true" :background="background" title="扩容" :height="55">
		</u-navbar>
		<view class="headerBox">
			<view class="enterpriseCloud-box">
				<view class="enterpriseCloud-title">
					{{userInfo.config.pan_info.last_name}}
				</view>
				<progress class="progress-div" :percent="GetPercent(userInfo.config.pan_info.use,userInfo.config.pan_info.size)" stroke-width="10" backgroundColor="#EEF2FF" />
				<view class="status-div">
					<text class="status-l">{{userInfo.config.pan_info.use}}M/{{userInfo.config.pan_info.size}}M</text>
					<text class="status-r">已使用{{GetPercent(userInfo.config.pan_info.use,userInfo.config.pan_info.size)}}</text>
				</view>
			</view>
		</view>

		<view class="mainBox">
			<view class="common-title">
			    购买空间
			</view>
			<view class="g-box">
				<view class="g-item" @click="selectBtn(1,index,item.id,item)" :class="{active:currentIndex==index}"
					v-for="(item,index) in oneData" :key="index">
					{{item.name}}
				</view>
			</view>

			<view class="common-title margin-top-lg">
				购买年限
			</view>
			<view class="g-box">
				<view class="g-item" @click="selectBtn(2,index,item.id,item)" :class="{active:currentIndex2==index}"
					v-for="(item,index) in twoData" :key="index">
					{{item.name}}
				</view>
			</view>

			<view class="common-title margin-top-lg">
				有效期
			</view>
			<view class="time-desc">
				{{nowDate}}至{{endDate}}
			</view>
			<view class="desc-hint">
				提示：空间扩容为虚拟产品，购买即生效，不支持退款， 敬请理解！
			</view>
		</view>

		<u-cell-group :border="false">
			<u-cell-item title="需要发票" @click="needFapiao">
			</u-cell-item>
		</u-cell-group>



		<view class="bd">
			<text style="font-size: 16px;font-weight: bold;">选择支付方式</text>
			<!-- <u-cell-item title="支付宝" :arrow="false" class="type-item" style="margin-top: 20rpx;" :border-bottom="false">
				<image src="../../../../static/image/my/zfb.png" slot="icon"
					style="width: 50rpx;height: 50rpx;margin-right: 24rpx" mode="">
				</image>
				<radio value="2" v-model="type" :checked="type==2" @click="changeType('2')" slot="right-icon" />
			</u-cell-item> -->
			<u-cell-item title="微信" :arrow="false" class="type-item" :border-bottom="false">
				<image src="../../../../static/image/my/wx.png" slot="icon"
					style="width: 50rpx;height: 50rpx;margin-right: 24rpx;" mode="">
				</image>
				<!-- <u-badge count="99" :absolute="false" slot="right-icon"></u-badge> -->
				<radio value="1" v-model="type" :checked="type==1" @click="changeType('1')" />
			</u-cell-item>

		</view>
				<button type="default" class="addBtn" @click="submitBtn(currentYear.price)">¥{{currentYear.price}}立即支付</button>
	</view>
</template>

<script>
	import dayjs from "@/utils/dayjs.min.js";
	import {
		mapState
	} from "vuex"
	export default {
		data() {
			return {
				value:'',
				type: 1,
				currentIndex: 0,
				currentIndex2: 0,
				background: {
					backgroundColor: "#FFFFFF",
				},
				vid: 20,
				nowDate:"",
				endDate:"",
				oneData: [],
				twoData: [],
				currentYear:{},
				copiedPeople:''
			};
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo,
		
			})
		},
		onLoad() {
			this.getvid();
			this.nowDate = dayjs().format("YYYY-MM-DD");
		},
		methods: {
			//立即支付
			submitBtn(price){
				let paytype = ''
				if(this.type == 1){
					paytype = "wechat"
				}else{
					paytype = "alipay"
				}
				let params = {
					pay_type:paytype,
					vid:this.vid,
					name:'',
					phone:''
				}
				this.$http("user/capacity", params, "post").then(res => {
					if (res.data.code == 1) {
						if(this.type == 1){
							let makpay = JSON.parse(res.data.data)
						console.log(makpay.appid,"?????????")
						uni.requestPayment({
							"provider": "wxpay",
							"orderInfo": {
								"appid": makpay.appid, // 微信开放平台 - 应用 - AppId，注意和微信小程序、公众号 AppId 可能不一致
								"noncestr": makpay.noncestr, // 随机字符串
								"package": makpay.package, // 固定值
								"partnerid": makpay.partnerid, // 微信支付商户号
								"prepayid": makpay.prepayid, // 统一下单订单号 
								"timestamp": makpay.timestamp, // 时间戳（单位：秒）
								"sign": makpay.sign // 签名，这里用的 MD5 签名
							},
							success(res) {
								uni.showToast({
									title: '支付成功',
									icon: 'none'
								})
								setTimeout(function() {
									uni.navigateTo({
										url: "/pages/my/vip/success"
									})
									uni.$emit("changeUserInfo", true)
								}, 1200)
							},
							fail(e) {
								console.log(e);
							}
						})
						}else{
							let alizfb = res.data.data
							uni.requestPayment({
								provider: 'alipay',
								orderInfo: alizfb, //支付宝
								success: function(res) {
									uni.showToast({
										title: '支付成功',
										icon: 'none'
									})
									setTimeout(function() {
										uni.switchTab({
											url: '../../pages/tabbar/tab-4/tab-4'
										})
									}, 2000)
								},
								fail: function(err) {
									uni.showToast({
										title: '支付失败',
										icon: 'none'
									})
									console.log('fail:' + JSON.stringify(err));
								}
							});
						}
						
						
						
					}
				})
			},
			GetPercent(num, total) {
			    num = parseFloat(num);
			    total = parseFloat(total);
			    if (isNaN(num) || isNaN(total)) {
			        return "-";
			    }
			    return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00)+"%";
			},
			changeType(type) {
				this.type = type;
			},
			needFapiao(){
					this.$navigateTo({
						url: "/pages/my/vip/invoicePage?contny=" + JSON.stringify(this.copiedPeople)
					}).then(res => {
						this.copiedPeople =JSON.parse(res.name);
						console.log(this.copiedPeople,">>>>>>>>>>")
					})
			},
			selectBtn(flag, index, id,item) {
				if (flag == 1) {
					console.log(555)
					this.currentIndex = index;
					this.vid = id;
					this.getInfoList();
				} else if (flag == 2) {
					console.log(6666)
					this.vid = id;
					this.currentIndex2 = index;
					this.currentYear = item
						this.endDate = dayjs().add(this.currentYear.num,'year').format("YYYY-MM-DD")
				}
			},
			getvid() {
				this.$http("user/VirtualSpaceOne", {}, "get").then(res => {
					if (res.data.code == 1) {
						this.vid = res.data.data[0].id
						this.getInfoList();
					}
				})
			},
			getInfoList() {
				this.$http("user/capacity?vid=" + this.vid, {}, "get").then(res => {
					if (res.data.code == 1) {
						this.oneData = res.data.data.one;
						this.twoData = res.data.data.two;
						this.currentYear = res.data.data.two[0];
						this.endDate = dayjs().add(this.currentYear.num,'year').format("YYYY-MM-DD");
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
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
	}

	.headerBox {
		padding: 0 32rpx;

		.enterpriseCloud-box {
			margin-top: 32rpx;
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

		}
	}

	.common-title {
		font-size: 34rpx;
		// font-weight: bold;
		color: #150E33;
	}

	.mainBox {
		margin-top: 32rpx;
		padding: 32rpx;

		.g-box {
			display: flex;
			margin-right: -12rpx;
			margin-top: 32rpx;

			.g-item {
				width: 210rpx;
				text-align: center;
				height: 82rpx;
				line-height: 82rpx;
				background: #F6F9FE;
				opacity: 1;
				color: #7A7C94;
				border-radius: 12rpx;
				margin-right: 18rpx;
				font-size: 30rpx;
			}
		}

		.active {
			background: #4396F7 !important;
			color: #FFFFFF !important;
		}

		.time-desc {
			margin-top: 32rpx;
		}

		.desc-hint {
			color: #7A7C94;
			font-size: 26rpx;
			background: #F6F9FE;
			margin-top: 32rpx;
			padding: 32rpx 24rpx;
		}
	}

	.bd {
		padding: 32rpx;
		background-color: #fff;
		margin-top: 20rpx;
	}
</style>

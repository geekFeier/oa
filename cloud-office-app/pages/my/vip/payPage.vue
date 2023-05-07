<template>
	<view class="payBox">
		<u-navbar :is-back="true" :background="background" title="支付" :height="55">
		</u-navbar>

		<view class="mainBox">
			<view class="prce flex flex-direction align-center">
				<text style="color: #150E33;font-size: 60rpx;">￥{{payprice}}</text>
				<text style="color: #B5BFDA;font-size: 26rpx;margin-top: 18rpx;">年费会员</text>
			</view>


			<view class="hr-div" />

			<view class="info-box">
				<view class="info-title">
					会员信息
				</view>
				<view class="info-item">
					<view class="info-item-l">
						购买年限
					</view>
					<view class="info-item-r">
						1年
					</view>
				</view>
				<view class="info-item">
					<view class="info-item-l">
						应付金额
					</view>
					<view class="info-item-r">
						￥{{payprice}}
					</view>
				</view>
				<view class="info-item">
					<view class="info-item-l">
						会员姓名
					</view>
					<view class="info-item-r">
						<input type="text" v-model="username" placeholder="请输入您的姓名"/>
					</view>
				</view>
				<view class="info-item">
					<view class="info-item-l">
						联系电话
					</view>
					<view class="info-item-r">
						<input type="text" v-model="phone" placeholder="请输入您的手机号码"/>
					</view>
				</view>
				<view class="info-item" @click="goInvoice">
					<view class="info-item-l">
						需要发票
					</view>
					<view class="info-item-r">
						<u-icon name="arrow-right" color="#7A7C94"></u-icon>
					</view>
				</view>

			</view>

			<view class="bd">
				<text style="font-size: 16px;font-weight: bold;">选择支付方式</text>
				<!-- <u-cell-item title="支付宝" :arrow="false" class="type-item" :border-bottom="false">
					<image src="../../../static/image/my/zfb.png" slot="icon" style="width: 26px;height: 26px;" mode="">
					</image>
					<radio value="2" v-model="type" :checked="type==2" @click="changeType('2')" slot="right-icon" />
				</u-cell-item> -->
				<u-cell-item title="微信" :arrow="false" class="type-item" :border-bottom="false">
					<image src="../../../static/image/my/wx.png" slot="icon" style="width: 26px;height: 26px;" mode="">
					</image>
					<!-- <u-badge count="99" :absolute="false" slot="right-icon"></u-badge> -->
					<radio value="1" v-model="type" :checked="type==1" @click="changeType('1')" />
				</u-cell-item>

			</view>

		</view>

		<button type="default" class="addBtn" @click="TiXianBtn">立即支付</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				value:"",
				type: "1",
				background: {
					backgroundColor: "#fff",
				},
				currentData: {},
				payprice:'',//支付的钱
				username:'',//姓名
				phone:'',//
				copiedPeople:''
			};
		},
		onLoad(e) {
			console.log(e.price)
			this.payprice = e.price
		},
		methods: {
			goInvoice(){
				this.$navigateTo({
					url: "/pages/my/vip/invoicePage?contny=" + JSON.stringify(this.copiedPeople)
				}).then(res => {
					this.copiedPeople =JSON.parse(res.name);
					console.log(this.copiedPeople,">>>>>>>>>>")
				})
			},
			changeType(type) {
				this.type = type;
			},
			TiXianBtn() {
				if(this.username == ''){
					uni.showToast({
						title: "请输入您的姓名",
						icon: "none"
					})
					return
				}
				if(this.phone == ''){
					uni.showToast({
						title: "请输入您的手机号码",
						icon: "none"
					})
					return
				}
				console.log(this.type,">>>>>>>")
				let params = {
					vid: 1,
					name:this.username,
					phone:this.phone
				};
				if (this.type == 1) {
					params.pay_type = "wechat"
				} else if (this.type == 2) {
					params.pay_type = "alipay"
				} else if (this.type == 3) {
					params.pay_type = "balance"
				}

				this.$http("User/getMemberDateils", params, "post").then(res => {
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
		}
	}
</script>

<style scoped lang="scss">
	.hr-div {
		height: 20rpx;
		background: #FBFCFF;
		margin-top: 70rpx;
	}

	.mainBox {
		padding: 0 32rpx;

		.prce {
			margin-top: 80rpx;
		}

		.info-box {
			.info-title {
				color: #150E33;
				font-size: 32rpx;
				font-weight: bold;
			}

			.info-item {
				display: flex;
				justify-content: space-between;
				font-size: 32rpx;
				padding: 26rpx 0;
				border-bottom: 1px solid #F6F9FE;

				.info-item-l {
					color: #7A7C94;
				}

				.info-item-r {
					color: #150E33;
					 /deep/ .uni-input-wrapper{
						text-align: right !important; 
						.uni-input-placeholder{
							color: #B5BFDA !important;
						}
					}
				}
			}
		}

	}

	/deep/ .u-cell {
		padding-left: 0;
	}

	/deep/ .u-cell_title {
		margin-left: 12rpx;
	}

	.addBtn {
		text-align: center;
		width: 88%;
		height: 98rpx;
		line-height: 50px;
		color: #fff;
		background: #4396F7;
		margin-top: 32rpx;
		border-radius: 49px;
		margin-bottom: 32rpx;
	}


	.bd {
		margin-top: 60rpx;
	}

	.ktx {
		display: flex;
		justify-content: space-between;
		margin-top: 16px;
	}

	.sh {
		margin-top: 14px;
		color: #B5BFDA;
	}

	.hd {
		margin-top: 20px;

		.priceInput {
			margin-top: 14px;
			display: flex;
		}
	}
</style>

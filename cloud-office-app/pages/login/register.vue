<template>
	<view class="loginBox">
		<u-navbar title="注册" title-color="#150E33"></u-navbar>
		<view class="loginMain">
			<image src="../../static/image/launch_icon.png" mode="" class="login_logo"></image>

			<view class="form-item">
				<image src="../../static/image/login/tel.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="text" v-model="formData.mobile" placeholder="请输入手机号" maxlength="11" class="telInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
			</view>
			<view class="form-item form-code">
				<image src="../../static/image/login/code.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="text" v-model="formData.code" placeholder="请输入验证码" maxlength="6" class="codeInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
				<view class="get-code" v-if="codeTime==60" @click="getCode">获取验证码</view>
				<view class="get-code" v-else>{{codeTime}}s后重新发送</view>
			</view>
			<view class="form-item form-pass">
				<image src="../../static/image/login/pass.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="password" v-model="formData.password" placeholder="请输入密码" class="telInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
			</view>
			<view class="form-item form-pass">
				<image src="../../static/image/login/pass.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="password" v-model="newPassword" placeholder="请再次输入密码" class="telInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
			</view>





			<button type="default" class="login_btn" @click="loginBtn">注册</button>
			<!-- 			<view style="color: #7A7C94;text-align: center;margin-top: 28rpx;">
				已有账号？ <text style="color: #037FC5;" @click="goLoginPage"> 去登录</text>
			</view> -->

			<view class="tcp">
				登录注册即代表同意<text style="color: #0491CA;" @click="goUserTcp">《平台用户协议》</text>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				newPassword: "",
				isShow: false,
				// selector:["家庭成员","个体工商户","企业会员","签约税务师"],

				formData: {
					mobile: "",
					password: "",
					code: "",
				},
				currentSelect: "",
				codeTime: 60,
				isClick: true,
				currentSelectIndex: "",
				isClick: true
			}
		},
		onLoad(e) {

		},
		methods: {
			confrimSelect(e) {
				this.currentSelect = this.selector[e.join()].cateName;
				this.currentSelectIndex = e.join();
			},

			jumpAuthPage(val, uid) {
				this.$navigateTo({
					url: `/pages/login/${val}?uid=${uid}`
				}).then(res => {
					console.log(res, "ASDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
				})
			},
			goLoginPage() {
				uni.navigateTo({
					url: "/pages/login/index"
				})
			},
			selectDdentity() {
				this.isShow = true;
			},
			register() {},
			forgetPass() {},
			goUserTcp() {
				uni.navigateTo({
					url: "/pages/userAgreement"
				})
			},
			loginBtn() {
				let verify = Object.values(this.formData).every(item => {
					return item != ""
				})
				if (verify) {
					if (this.newPassword === this.formData.password) {
						this.$http("user/register", this.formData, "post", 2, true).then(res => {
							console.log(res, "+++++++++++++++++++++++++++++++");
							if (res.data.code == 1) {
								uni.showToast({
									title: "注册成功",
									icon: "none"
								})
								this.$store.dispatch("user/GET_TOKEN", res.data.data.userinfo)
								setTimeout(() => {
									uni.navigateTo({
										url: "/pages/login/authCentreSigning"
									})
								}, 500)
							}
						})
					} else {
						uni.showToast({
							title: "二次输入密码不一致",
							icon: "none"
						})
					}
				} else {
					uni.showToast({
						title: "请填写完整",
						icon: "none"
					})
				}

				// uni.navigateTo({
				// 	url:"/pages/login/authCentreSigning"
				// })
				// this.$http("Login/UserRegister", this.formData, "post").then(res => {
				// 	this.isClick = true;
				// 	if (res.data.code == 200) {
				// 		let uid = res.data.data.uid;
				// 		uni.showToast({
				// 			title: "注册成功"
				// 		})
				// 		setTimeout(() => {

				// 		}, 500)
				// 	} else {
				// 		this.isClick = true;
				// 		uni.showToast({
				// 			title: res.data.msg || "注册失败",
				// 			icon: "none"
				// 		})
				// 	}
				// })



			},
			getCode() {
				uni.showToast({
					title: "请配置验证码信息",
					icon: "none"
				})
				// if (this.isClick) {
				// 	this.isClick = false;
				// 	const telphone = /^1[3456789]\d{9}$/; // 手机号
				// 	if (!telphone.test(this.formData.mobile)) {
				// 		uni.showToast({
				// 			title: '请检查您的手机号码',
				// 			icon: 'none',
				// 		})
				// 		this.isClick = true;
				// 		return false;
				// 	} else {
				// 		let params = {
				// 			phone: this.formData.mobile,
				// 		}
				// 		this.$http("login/phone_check", params, "post", 2).then(res => {
				// 			if (res.data.code == 200) {
				// 				uni.showToast({
				// 					title: "发送成功",
				// 					icon: "none"
				// 				})
				// 				this.downTime();

				// 			} else {
				// 				uni.showToast({
				// 					title: res.data.msg,
				// 					icon: "none"
				// 				})
				// 			}


				// 		})
				// 	}
				// }
			},
			// 发送验证码倒计时
			downTime() {
				if (this.codeTime == 0) {
					this.codeTime = 60;
					this.isClick = true;
					return;
				} else {
					this.codeTime--;
					this.isClick = false;
					setTimeout(() => {
						this.downTime();
					}, 1000);
				}
			},

		}
	}
</script>

<style scoped lang="scss">
	.loginBox {
		width: 100%;
		height: 100%;
		background: #fff;

		.loginMain {
			display: flex;
			flex-direction: column;
			// align-items: center;
			padding: 0 30px;
			box-sizing: border-box;

			.login_logo {
				width: 160rpx;
				height: 160rpx;
				margin-top: 50px;
				align-self: center;
				margin-bottom: 55px;
			}

			.form-item {
				display: flex;
				align-items: center;
				margin-left: 26rpx;

				.telInput {
					margin-left: 15px;
					height: 32px;
					width: 280px;

				}

			}

			.form-pass {
				margin-top: 42rpx;
			}

			.form-code {
				margin-top: 42rpx;

				.codeInput {
					margin-left: 15px;
					height: 32px;
					width: 260rpx;
				}

				.get-code {
					color: #0491CA;
					margin-left: 35px;
					font-size: 16px;
				}
			}

			.login_btn {
				margin-top: 100rpx;
				background: #4396F7;
				border-radius: 49rpx;
				width: 92%;
				color: #fff;
			}

			.tcp {
				width: 100%;
				color: #B5BFDA;
				position: fixed;
				text-align: center;
				bottom: 20px;
				left: 50%;
				transform: translateX(-50%);
			}

		}
	}
</style>

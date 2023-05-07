<template>
	<view class="loginBox">
		<u-navbar title="登录" :is-back="true" title-color="#150E33"></u-navbar>
		<view class="loginMain">
			<image src="../../static/image/launch_icon.png" mode="" class="login_logo"></image>

			<view class="form-item margin-top-lg">
				<image src="../../static/image/login/tel.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="text" v-model="formData.mobile" placeholder="请输入手机号" maxlength="11" class="telInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
			</view>
			<view class="form-item form-pass" v-if="modeFlag==1">
				<image src="../../static/image/login/pass.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="password" v-model="formData.password" placeholder="请输入密码" class="telInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
			</view>
			<view class="form-item form-code" v-if="modeFlag==2">
				<image src="../../static/image/login/code.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="text" v-model="formData.captcha" placeholder="请输入验证码" maxlength="6" class="codeInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
				<view class="get-code" v-if="codeTime==60" @click="getCode">获取验证码</view>
				<view class="get-code" v-else>{{codeTime}}s后重新发送</view>
			</view>

			<view class="form-item form-pass" v-if="modeFlag==1">
				<image src="../../static/image/login/home.png" mode="" style="width: 40rpx;height: 40rpx;"></image>
				<input type="text" disabled :value="unitName" placeholder="请选择单位" class="telInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" @click="selectUnit" />
				<u-icon name="arrow-right" color="#B5BFDA"></u-icon>
			</view>



			<view class="change-mode">
				<text style="color: #150E33;" @click="changeMode()">{{modeFlag === 1 ? "企业登录" : "员工登录"}}</text>

				<text style="color: #150E33;" @click="registerBtn()">注册账号</text>
			</view>
			<button type="default" class="login_btn" @click="loginBtn">登录</button>


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
				currentSelect: "",
				unitName: "",
				formData: {
					mobile: "",
					password: "",
					captcha: "",
					unitwork: ""
				},
				codeTime: 60,
				modeFlag: 2, // 2企业  1用户
				isClick: true,
				isHidden: 1
			}
		},
		mounted() {},
		methods: {
			selectUnit() {
				this.$navigateTo({
					url: "/pages/login/selectUnit?id=" + this.formData.unitwork + "&name=" + this.unitName
				}).then(res => {
					console.log(res, "aAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
					this.formData.unitwork = res.currentIndex;
					this.unitName = res.currentName;
				})
			},
			registerBtn() {
				uni.navigateTo({
					url: "/pages/login/register"
				})
			},
			forgetPass() {
				uni.navigateTo({
					url: "/pages/login/forgetPass"
				})
			},
			goUserTcp() {
				uni.navigateTo({
					url: "/pages/userAgreement"
				})
			},
			loginBtn() {
				// uni.reLaunch({
				// 	url:"/pages/tabbar/tab-1/index"
				// })
				if (this.modeFlag == 1) {
					let params = {
						account: this.formData.mobile,
						password: this.formData.password,
						unitwork: this.formData.unitwork
					}
					this.$http("user/login", params, "post", 2, true).then(res => {
						if (res.data.code == 1) {

							this.$store.dispatch("user/GET_TOKEN", res.data.data.userinfo);
							this.$http("User/getUser", {}, "post").then(res => {
								if (res.data.code == 1) {
									this.$store.dispatch("user/GET_USER_INFO", res.data.data);
									uni.showToast({
										title: "登录成功",
										icon: "none"
									})
									setTimeout(() => {
										uni.reLaunch({
											url: "/pages/tabbar/tab-1/index"
										})
									}, 500)
								}
							})
						}
					})
				} else {
					let params = {
						mobile: this.formData.mobile,
						captcha: this.formData.captcha
					}
					this.$http("user/mobilelogin", params, "post", 2, true).then(res => {
						if (res.data.code == 1) {
							this.$store.dispatch("user/GET_TOKEN", res.data.data.userinfo)
							this.$http("/User/getUser", {}, "post").then(res => {
								if (res.data.code == 1) {

									this.$store.dispatch("user/GET_USER_INFO", res.data.data);
									this.$http("enterprise.Subject/init", {}, "post").then(_res => {
										if (_res.data.code == 1) {
											uni.showToast({
												title: "登录成功",
												icon: "none"
											})
											setTimeout(() => {
												uni.reLaunch({
													url: "/pages/tabbar/tab-1/index"
												})
											}, 500)
										}
									})

								} else if (res.data.code == 20001) {
									setTimeout(() => {
										this.$navigateTo({
											url: "/pages/my/accountSet/index"
										})
									}, 500)
								} else if (res.data.code == 20006) {
									setTimeout(() => {
										uni.reLaunch({
											url: "/pages/tabbar/tab-1/index"
										})
									}, 500)
								}
							})

						}
					})
				}
			},

			getCode() {
					uni.showToast({
									title:"请配置验证码信息",
									icon:"none"
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
				// 			mobile: this.formData.mobile,
				// 			event: "mobilelogin"
				// 		}
				// 		this.$http("sms/send", params, "post", 1, true).then(res => {
				// 			this.downTime();
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
			changeMode() {
				this.modeFlag === 1 ? this.modeFlag = 2 : this.modeFlag = 1;
			}
		}
	}
</script>

<style scoped lang="scss">
	.loginBox {
		height: 100%;

		.loginMain {
			display: flex;
			flex-direction: column;
			// align-items: center;
			padding: 0 30px;
			box-sizing: border-box;

			.login_logo {
				width: 160rpx;
				height: 160rpx;
				margin-top: 40px;
				align-self: center;
				margin-bottom: 40px;
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
				margin-top: 20px;
			}

			.form-code {
				margin-top: 20px;

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
				margin-top: 80rpx;
				background: #4396F7;
				border-radius: 49px;
				width: 92%;
				color: #fff;
			}

			.change-mode {
				display: flex;
				justify-content: space-between;
				padding: 0 12rpx;
				padding-left: 18rpx;
				box-sizing: border-box;
				margin-top: 54rpx;
			}

			.user_help {
				height: 30px;
				display: flex;
				text-align: center;
				line-height: 30px;
				margin-top: 22px;

				.register {
					font-weight: 400;
					line-height: 33px;
					color: #7A7C94;
					width: 49%;
					border-right: 2px solid #B5BFDA;

				}

				.forgetPass {
					width: 49%;
					color: #B5BFDA;
				}
			}

			.weixin-login {
				align-items: center;
				// margin-top: 12px;
				position: fixed;
				bottom: 60px;
				left: 50%;
				transform: translateX(-50%);
				display: flex;
				flex-direction: column;
			}

			.tcp {
				display: flex;
				justify-content: center;
				// margin-top: 15px;
				color: #B5BFDA;
				position: fixed;
				bottom: 10px;
				left: 50%;
				transform: translateX(-50%);
				width: 320px;
			}
		}
	}
</style>

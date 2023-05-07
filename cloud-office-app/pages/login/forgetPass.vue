<template>
	<view class="loginBox">
		<u-navbar title="忘记密码" title-color="#150E33"></u-navbar>
		<view class="loginMain">
			<image src="../../static/image/launch_icon.png" mode="" class="login_logo"></image>

			<view class="form-item">
				<image src="../../static/image/login/tel.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="text" v-model="formData.phone" placeholder="请输入已注册手机号" maxlength="11" class="telInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
			</view>
			<view class="form-item form-code">
				<image src="../../static/image/login/code.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="text" v-model="formData.code" placeholder="请输入验证码" maxlength="4" class="codeInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
				<view class="get-code" v-if="codeTime==60" @click="getCode">获取验证码</view>
				<view class="get-code" v-else>{{codeTime}}s后重新发送</view>
			</view>
			<view class="form-item form-pass">
				<image src="../../static/image/login/pass.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
				<input type="password" v-model="formData.pwd" placeholder="请输入新密码" class="telInput"
					placeholder-style="color: #B5BFDA;font-size:32rpx" />
			</view>

			<button type="default" class="login_btn" @click="loginBtn">完成</button>
			<!-- <view class="tcp">
				登录注册即代表同意<text style="color: #0491CA;" @click="goUserTcp">《平台用户协议》</text>
			</view> -->
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				formData: {
					phone: "",
					pwd: "",
					code: "",
				},
				codeTime: 60,
				isClick: true,
			}
		},
		methods: {
			register() {},
			forgetPass() {},
			goUserTcp() {
				uni.navigateTo({
					url: "/pages/userAgreement"
				})
			},
			loginBtn() {
				this.$http("login/changePwd", this.formData, "post").then(res => {
					if (res.data.code == 200) {
						uni.showToast({
							title: "修改成功"
						})
						setTimeout(() => {
							uni.navigateTo({
								url: "/pages/login/index"
							})
						}, 500)
					}
				})

			},
			getCode() {
				if (this.isClick) {
					this.isClick = false;
					const telphone = /^1[3456789]\d{9}$/; // 手机号
					if (!telphone.test(this.formData.mobile)) {
						uni.showToast({
							title: '请检查您的手机号码',
							icon: 'none',
						})
						this.isClick = true;
						return false;
					} else {
						let params = {
							mobile: this.formData.mobile,
							event: "resetpwd"
						}
						this.$http("Sms/send", params, "post").then(res => {
							if (res.data.code == 1) {
								this.downTime();
							} else {
								this.isClick = true;
							}
						})
					}
				}
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
				height:  160rpx;
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
				margin-top: 30px;
			}

			.form-code {
				margin-top: 30px;

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
				margin-top: 40px;
				background: #037FC5;
				border-radius: 49px;
				width: 90%;
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

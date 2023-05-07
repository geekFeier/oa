<template>
	<view>
		<u-navbar :is-back="true" title="移交权限" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="form">
				<view class="form-item margin-top-lg">
					<image src="../../../static/image/login/tel.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
					<input type="text" v-model="formData.mobile" placeholder="请输入新手机号" maxlength="11" class="telInput"
						placeholder-style="color: #B5BFDA;font-size:32rpx" />
				</view>
				<view class="form-item form-code">
					<image src="../../../static/image/login/code.png" mode="" style="width: 32rpx;height: 40rpx;"></image>
					<input type="password" v-model="formData.code" placeholder="请输入验证码" maxlength="5" class="codeInput"
						placeholder-style="color: #B5BFDA;font-size:32rpx" />
					<view class="get-code" v-if="codeTime==60" @click="getCode">获取验证码</view>
					<view class="get-code" v-else>{{codeTime}}s后重新发送</view>
				</view>
				
			</view>
				
		</view>
			<button type="default" class="sure_btn" @click="sureBtn">确定</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				codeTime:60,
				background: {
					backgroundColor: "#FFFFFF",
				},
				formData:{
					mobile:"",
					code:""
				}
			};
		},
		methods:{
			getCode(){}
		}
	}
</script>

<style lang="scss" scoped>
	
	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		margin-top: 120rpx;
		margin-bottom: 50rpx;
	}
	
	page {
		color: #150E33;
	}

	.mainBox {
		padding: 0 32rpx;
		// font-weight: bold;
		font-size: 32rpx;

		.title {
			margin-top: 32rpx;
		}
	}
	.form{
		margin-top: 80rpx;
		padding: 0 28rpx;
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
</style>

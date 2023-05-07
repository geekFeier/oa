<template>
	<view>
		<u-navbar :is-back="true" title="意见反馈" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="common-title">
				意见反馈类型
			</view>
			<view class="group-box">
				<view class="group-item" @click="addc(index)" v-for="(item,index) in groupList" :key="index" :class="tab1Index===index?'active':''">
					{{item.name}}
				</view>
			</view>

			<view class="textarea-box">
				<textarea v-model="value" auto-height placeholder="选择问题类型，并填写3字以上的反馈" />

				<!-- <htz-image-upload style="margin-top: 15px;" mediaType="image" :max="1" name="file" :chooseNum="1"
					v-model="imageData" @chooseSuccess="ceshiChooseSuccess">
				</htz-image-upload> -->

			</view>
			<u-form ref="uForm">
				<u-form-item label="手机号" label-width="150">
					<u-input v-model="phone" type="input" placeholder="请输入手机号" />
				</u-form-item>
			</u-form>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">确定</button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				value: "",
				phone:'',
				imageData: [],
				background: {
					backgroundColor: "#FFFFFF",
				},
				groupList: [{
						name: "功能反馈"
					},
					{
						name: "优化建议"
					},
					{
						name: "其他反馈"
					},
				],
				tab1Index:0
			};
		},
		methods: {
			addc(index){
				this.tab1Index = index
			},
			ceshiChooseSuccess(val) {

			},
			sureBtn(){
				if (this.value == '') {
					uni.showToast({
						title: "请输入反馈内容",
						icon: "none"
					})
					return
				}
				const telphone = /^1[3456789]\d{9}$/; // 手机号
				if (!telphone.test(this.phone)) {
					uni.showToast({
						title: '请检查您的手机号码',
						icon: 'none',
					})
					return
				}
				let formData = {
					content:this.value,
					advice_name:'feedback',
					images:'http://ysbg.qingyukeji.online//uploads/20220107/57d7b0a6c55b8c564349ef541505ccf8.jpg',
					mobile:this.phone,
					flag:'other',
				}
				
				this.$http("User/feedback", formData, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title:"提交成功",
							icon:"none"
									})
						setTimeout(() => {
							uni.reLaunch({
								url: "/pages/tabbar/tab-3/index"
							})
						}, 1000)
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	/deep/ .uni-textarea-placeholder {
		color: #B5BFDA;
		font-size: 28rpx;
	}

	/deep/ .uni-textarea-wrapper {
		min-height: 200rpx;
	}

	/deep/ .u-form-item--left__content__label {
		font-size: 32rpx;
	}

	/deep/ .uni-input-placeholder {
		color: rgb(192, 196, 204);
		font-size: 30rpx;
		text-align: right;
	}

	/deep/ .uni-input-input {
		font-size: 30rpx;
		text-align: right;
	}

	.sure_btn {
		text-align: center;
		width: 88%;
		height: 90rpx;
		line-height: 90rpx;
		color: #fff;
		background: #4396F7;
		margin-top: 140rpx;
		border-radius: 49px;
	}

	.mainBox {
		padding: 0 32rpx;
		margin-top: 32rpx;

		.common-title {
			font-size: 32rpx;
			color: #150E33;
		}

		.group-box {
			display: flex;
			margin-top: 32rpx;

			.group-item {
				width: 180rpx;
				height: 70rpx;
				background: #FFFFFF;
				font-size: 28rpx;
				color: #7A7C94;
				border: 1px solid #B5BFDA;
				line-height: 70rpx;
				text-align: center;
				border-radius: 12rpx;
				margin-right: 24rpx;
			}

			.active {
				border: 1px solid #4396F7;
				color: #4396F7;
			}
		}

		.textarea-box {
			margin-top: 32rpx;
			// height: 520rpx;
			width: 100%;
			// background: pink;
			background: #FBFCFF;
			border-radius: 24rpx;
			margin-bottom: 32rpx;
			padding: 32rpx;
		}
	}
</style>

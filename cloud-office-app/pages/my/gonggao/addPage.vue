<template>
	<view>
		<u-navbar :is-back="true" title="新增推送" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>

		<view class="mainBox">
			<view style="font-size: 30rpx;">
				公告内容
			</view>
			<!-- <input type="textarea" v-model="value" style="min-height: 100px" :auto-height="true"/> -->
			<u-input v-model="content" type="textarea" :height="340" :auto-height="true" :clearable="false"
				style="margin-top: 18px;" />


			<u-form-item style="margin-top: 50rpx;" label="添加链接" label-width="150" :border-bottom="false">
				<u-input v-model="url" type="input" placeholder="请输入链接" />
			</u-form-item>



			<button type="default" class="addBtn" @click="addBtn">保存并发布</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				content: "",
				url: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
			}
		},
		methods: {
			addBtn() {
				if(!this.content){
					uni.showToast({
						title:"请输入公告内容!",
						icon:"none"
					})
					return
				}
				let params = {
					content: this.content,
					url: this.url
				}
				this.$http("enterprise.Notice/pushNotice", params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "发送成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
		}
	}
</script>

<style scoped lang="scss">
	.mainBox {
		padding: 12px 14px;

		/deep/ .u-form-item__body {
			font-size: 30rpx;
		}

		/deep/ .uni-input-wrapper {
			text-align: right;
		}

		/deep/ .u-input__textarea {
			// background: pink;

			background: #F6F9FE;
			border-radius: 8px;
			padding: 8px;
		}

		/deep/ .uni-textarea-textarea {
			min-height: 340rpx;
		}

		.addBtn {
			text-align: center;
			width: 88%;
			height: 90rpx;
			line-height: 90rpx;
			color: #fff;
			background: #4396F7;
			margin-top: 100rpx;
			border-radius: 49px;
		}
	}
</style>

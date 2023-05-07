<template>
	<view>
		<u-navbar :is-back="true" title="自动平衡" :border-bottom="false" back-icon-color="#000"
			:background="background" title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="hr-div"></view>
			<u-form ref="uForm">
				<view>
					<view class="hr-div"></view>
					<u-form-item label="科目" label-width="150" :border-bottom="false" right-icon="arrow-right"
						:right-icon-style="{color:'#7d7f97'}">
						<u-input disabled v-model="credentials.Course_content" @click="goKeMu(index)" type="input"
							placeholder="请选择科目" />
					</u-form-item>
					<view class="hr-div"></view>
					<u-form-item label="方向" label-width="150" :border-bottom="false">
						<u-input class="passDiv" disabled type="input" placeholder="" />
						<view class="passDiv-r">
							<text class="passDiv-r-text">{{credentials.direction ? "借" :"贷"}}</text>
							<u-switch size="40" v-model="credentials.direction" active-color="#13C75B"
								inactive-color="#FF253D">
							</u-switch>
						</view>
					</u-form-item>
					<view class="hr-div"></view>
				</view>
			</u-form>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">保存</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				credentials: {
					abstract: '',
					kemu_id: '',
					valuetype: 1,
					direction: false,
					ratevalue:100,
					formula: []
				},
				ida:'',
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad(e) {
			this.credentials = JSON.parse(e.detail)
			if(JSON.parse(e.detail).direction == 1){
				this.credentials.direction = true
			}else{
				this.credentials.direction = false
			}
			this.ida = JSON.parse(e.detail).id
			console.log(JSON.parse(e.detail))
		},
		methods: {
			goKeMu(index) {
				this.$navigateTo({
					url: "/pages/my/subject/selectPage"
				}).then(res => {
					this.credentials.kemu_id = res.id;
					this.credentials.Course_content = res.name;
				})
			},
			sureBtn() {
				if(this.credentials.kemu_id == ''){
					uni.showToast({
						title: "请选择科目",
						icon: "none"
					})
					return
				}
				let task
				if(this.credentials.direction){
					task = 1
				}else{
					task = -1
				}
				let param = {
					direction:task,
					rule_id:this.ida,
					kemu_id:this.credentials.kemu_id
				}
				this.$http("enterprise.Date_query/TemplateSaveTow", param, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "修改成功",
							icon: "none"
						})
						setTimeout(()=>{
							this.$navigateBack(param)
						},1000)
					}
				})
			},
			
		}
	}
</script>

<style lang="scss" scoped>
	.picker-view {
		.item {
			font-size: 34rpx;
			text-align: center;
		}
	}

	.form-item-right {
		display: flex;
		align-items: center;
	}

	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		margin: 32rpx auto;
	}

	.hr-div {
		width: 750rpx;
		height: 20rpx;
		background: #FBFCFF;
	}

	.mainBox {

		/deep/ .u-form-item,
		.form-item-group {
			padding: 14rpx 32rpx;
			font-size: 30rpx;
		}

		.form-item-btn {
			display: flex;
			flex: 1;
			justify-content: space-between;
			align-items: center;

			.upload-btn {
				width: 88rpx;
				line-height: 42rpx;
				height: 42rpx;
				background: #4396F7;
				border-radius: 21rpx;
				text-align: center;
				font-size: 24rpx;
				color: #FFFFFF;
			}
		}

		.form-item-group {
			display: flex;

			.form-item {
				display: flex;

				.form-item-label {
					width: 280rpx;
					margin-right: 10rpx;
				}

				/deep/ .uni-input-placeholder {
					color: rgb(192, 196, 204);
					font-size: 30rpx;
				}
			}
		}

		.passDiv-r {
			display: flex;
			align-items: center;

			.passDiv-r-text {
				color: #150E33;
				font-size: 30rpx;
				margin-right: 20rpx;
			}
		}
	}

	.popup-main {

		.popup-common-title {
			font-size: 36rpx;
			color: #150E33;
			text-align: center;
			margin-top: 56rpx;
		}

		.active {
			background: #4396F7 !important;
			color: #FFFFFF !important;
		}

		.popup-common-btnGroup {
			display: flex;
			padding: 0 50rpx;
			justify-content: space-between;
			font-size: 30rpx;
			margin-top: 50rpx;
			margin-bottom: 32rpx;
			.popup-common-btn {
				width: 260rpx;
				height: 80rpx;
				background: #F6F9FE;
				border-radius: 12rpx;
				color: #7A7C94;
				text-align: center;
				line-height: 80rpx;
			}
		}
	}
</style>

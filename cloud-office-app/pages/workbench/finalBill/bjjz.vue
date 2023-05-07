<template>
	<view>
		<u-navbar :is-back="true" title="模板修改" :border-bottom="false" back-icon-color="#000"
			:background="background" title-color="#000" :height="55">
		</u-navbar>

		<view class="mainBox">
			<view class="hr-div"></view>
			<u-form ref="uForm">
				<view>
					<u-form-item label="结转名称" label-width="150" :border-bottom="false">
						<u-input v-model="remarks" type="input" placeholder="请输入名称" />
					</u-form-item>
					<view class="hr-div"></view>
					<view class="hr-div"></view>
					<u-form-item label="按公式取值" label-width="180" :border-bottom="false" right-icon="arrow-right"
						:right-icon-style="{color:'#7d7f97'}">
						<u-input disabled v-model="anchildreb" @click="formula()" type="input"
							placeholder="请选择" />
					</u-form-item>
					<view class="hr-div"></view>
					<u-form-item label="自动平衡" label-width="150" :border-bottom="false" right-icon="arrow-right"
						:right-icon-style="{color:'#7d7f97'}">
						<u-input disabled v-model="anchildren" @click="autbalance()" type="input"
							placeholder="请选择" />
					</u-form-item>
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
				remarks:'',//名称
				anchildreb:'已选择',//公式取值
				anchildren:'已选择',//自动平衡
				credentials: [],
				adetails:'',
				bdetails:'',
				flag: 1,
				ida:'',
				background: {
					backgroundColor: "#FFFFFF",
				},
				id:''
				
			};
		},
		onLoad(e) {
			this.id = e.id
			this.deleta()
		},
		methods: {
			deleta() {
				let param = {
					prof_id:this.id
				}
				this.$http("enterprise.Date_query/TemplateInfo", param, "post").then(res => {
					if (res.data.code == 1) {
						this.adetails = res.data.data.rule[0]
						this.bdetails = res.data.data.rule[1]
						this.remarks = res.data.data.name
						this.ida = res.data.data.id
					}
				})
			},
			//按公式取值
			formula(){
				this.$navigateTo({
					url: "./bjset?detail=" + JSON.stringify(this.bdetails) + '&id=' + this.id
				}).then(res => {
					this.deleta()
				})
			},
			//自动平衡
			autbalance(){
				this.$navigateTo({
					url: "./bjautobalance?detail=" + JSON.stringify(this.adetails)
				}).then(res => {
					this.deleta()
				})
			},
			sureBtn() {
				if(this.remarks == ''){
					uni.showToast({
						title: "请输入结转名称",
						icon: "none"
					})
					return
				}
				let param = {
					name:this.remarks,
					pro_id:this.ida,
				}
				this.$http("enterprise.Date_query/TemplateSaveOne", param, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "修改成功",
							icon: "none"
						})
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

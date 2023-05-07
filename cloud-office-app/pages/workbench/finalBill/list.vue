<template>
	<view>
		<u-navbar :is-back="true" :title="flag==1 ? '新增结转'  : '编辑结转'" :border-bottom="false" back-icon-color="#000"
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
					<!-- <u-form-item label="科目" label-width="150" :border-bottom="false" right-icon="arrow-right"
						:right-icon-style="{color:'#7d7f97'}">
						<u-input disabled v-model="subject_headings" @click="goKeMu(index)" type="input"
							placeholder="请选择科目" />
					</u-form-item> -->
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
				// subject_headings:'',//
				anchildreb:'请选择',//公式取值
				anchildren:'请选择',//自动平衡
				credentials: [],
				flag: 1,
				background: {
					backgroundColor: "#FFFFFF",
				},
				formData: {
					recording_time: "",
					operation: "add",
					documents_images: "",
					years: "",
					month: "",
					day: "",
					key: "",
					remarks: ""
				},
				
			};
		},
		onLoad(e) {
			this.flag = e.flag ? e.flag : 1;
		},
		methods: {
			//按公式取值
			formula(){
				this.$navigateTo({
					url: "./set"
				}).then(res => {
					console.log(res,"RRRRRRRRRRRRRRRRR")
					this.anchildreb = '已选择'
					if(res.direction){
						res.direction = 1
					}else{
						res.direction = -1
					}
					this.credentials[1] = res
					console.log(this.credentials,"AAAAAAAAAAAA")
				})
			},
			//自动平衡
			autbalance(){
				this.$navigateTo({
					url: "./autobalance"
				}).then(res => {
					this.anchildren = '已选择'
					if(res.direction){
						res.direction = 1
					}else{
						res.direction = -1
					}
					this.credentials[0] = res
					console.log(this.credentials,"AAAAAAAAAAAA")
				})
			},
			goKeMu(index) {
				this.$navigateTo({
					url: "/pages/my/subject/selectPage"
				}).then(res => {
					console.log(res,">>>>>>>>")
					this.credentials[index].kemu_id = res.id;
					this.credentials[index].subject_headings = res.name;
					this.credentials[index].balance_status = res.balance_status == -1 ? true : false;
					let dataArr = res.hesuan_list.map(item => {
						return {
							name: item.name,
							hs_id: item.id,
							memberName: ""
						}
					})
					this.credentials[index].hesuan_children = dataArr;
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
				if(this.anchildreb == '请选择'){
					uni.showToast({
						title: "请添加按公式取值",
						icon: "none"
					})
					return
				}
				if(this.anchildren == '请选择'){
					uni.showToast({
						title: "请添加自动平衡",
						icon: "none"
					})
					return
				}
				// console.log(this.credentials,"GGGGGGGGGGGGGGGGGGGGG")
				let param = {
					name:this.remarks,
					flag:'',
					children:this.credentials
				}
				console.log(param)
				this.$http("enterprise.Date_query/TemplateAdd", param, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "新增成功",
							icon: "none"
						})
						setTimeout(() => {
							uni.navigateBack({
								delta: 1
							})
						}, 500)
					}
				})
				// for (let item of this.credentials) {
				// 	if (!item.abstract) {
				// 		uni.showToast({
				// 			title: "请输入摘要!",
				// 			icon: "none"
				// 		})
				// 		return
				// 	} else if (!item.kemu_id) {
				// 		uni.showToast({
				// 			title: "请选择科目!",
				// 			icon: "none"
				// 		})
				// 		return
				// 	} else if (!item.money) {
				// 		uni.showToast({
				// 			title: "请输入金额!",
				// 			icon: "none"
				// 		})
				// 		return
				// 	}
				// }
				// this.formData.credentials = JSON.stringify(adday);
				// uni.showLoading({
				// 	title:'正在提交中...',
				// })
				// this.$http("enterprise.Credentials/CreateCrdentials", this.formData, "post").then(res => {
				// 	if (res.data.code == 1) {
				// 		uni.hideLoading()
				// 		uni.showToast({
				// 			title: "新增成功",
				// 			icon: "none"
				// 		})
				// 		setTimeout(() => {
				// 			uni.navigateBack({
				// 				delta: 1
				// 			})
				// 		}, 500)
				// 	}
				// })
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

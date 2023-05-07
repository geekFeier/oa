<template>
	<view>
		<u-navbar :is-back="true" :title="flag==1 ? '新增凭证'  : '编辑凭证'" :border-bottom="false" back-icon-color="#000"
			:background="background" title-color="#000" :height="55">
		</u-navbar>

		<view class="mainBox">
			<view class="time-div" @click="selectTimeBtn">
				{{formData.recording_time}}
				<u-icon name="arrow-down-fill" style="margin-left: 10rpx;" color="#150E33" size="24"></u-icon>
			</view>
			<view class="hr-div" />


			<u-form ref="uForm">
				<view class="" v-for="(item,index) in credentials" :key="index">
					<u-form-item label="摘要" label-width="150" :border-bottom="false">
						<u-input v-model="item.abstract" type="text" placeholder="请选择摘要" />
						<view class="form-item-right" slot="right">

							<u-icon name="arrow-down-fill" color="#150E33" size="20" @click="selectPopup2(index)">
							</u-icon>
							<text style="margin: 0 8rpx;">|</text>

							<image src="../../../static/image/tab1/caiwu/menuicon.png"
								style="width: 25rpx;height: 20rpx;" mode="" @click="selectDigest(index)"></image>

						</view>
					</u-form-item>
					<u-form-item label="科目" label-width="150" :border-bottom="false" right-icon="arrow-right"
						:right-icon-style="{color:'#7d7f97'}">
						<u-input disabled v-model="item.subject_headings" @click="goKeMu(index)" type="input"
							placeholder="请选择科目" />
					</u-form-item>
					<u-form-item v-for="(_item,indexs) in item.hesuan_children" :key="indexs" :label="_item.name"
						label-width="150" :border-bottom="false" right-icon="arrow-right"
						:right-icon-style="{color:'#7d7f97'}">
						<u-input disabled @click="goHsPage(_item.hs_id,index)" v-model="_item.memberName" placeholder=""
							type="input" />
					</u-form-item>
					<u-form-item label="金额" label-width="150" :border-bottom="false">
						<u-input class="passDiv" v-model="item.money" type="input" placeholder="请输入金额" />
						<view class="passDiv-r">
							<text class="passDiv-r-text">{{item.balance_status ? "借" :"贷"}}</text>
							<u-switch size="40" v-model="item.balance_status" active-color="#13C75B"
								inactive-color="#FF253D">
							</u-switch>
						</view>
					</u-form-item>
					<view class="hr-div" />
				</view>
				<view class="form-item-group">
					<view class="form-item">
						<view class="form-item-label">借方合计</view>
						￥<input type="text" disabled v-model="debtorStatic" />
					</view>
					<view class="form-item">
						<view class="form-item-label">贷方合计</view>
						￥<input type="text" disabled v-model="creditStatic" />
					</view>
				</view>

				<u-form-item label="附单据" label-width="150" :border-bottom="false">
					<view class="form-item-btn">
						<text>{{formData.documents_images.length}}</text>
						<view class="upload-btn" @click="uploadBtn">
							上传
						</view>
					</view>
				</u-form-item>

				<u-form-item label="备注" label-width="150" :border-bottom="false">
					<u-input v-model="formData.remarks" type="input" placeholder="请输入备注信息" />
				</u-form-item>
			</u-form>

		</view>

		<button type="default" class="sure_btn" @click="sureBtn">保存</button>
		<u-picker v-model="isShowTime" mode="time" @confirm="confirmTime"></u-picker>

		<u-popup v-model="isShowDigest" mode="bottom" border-radius="56">
			<view class="popup-main">
				<view class="popup-common-title">
					— 凭证行 —
				</view>

				<picker-view class="picker-view" style="height: 380rpx;" :value="digestValue2" @change="changePicker2">
					<picker-view-column>
						<view class="item" v-for="(item,index) in digestList" :key="index">{{item.name}}</view>
					</picker-view-column>
				</picker-view>

				<view class="popup-common-btnGroup">
					<view class="popup-common-btn" @click="isShowDigest = false">
						取消
					</view>
					<view class="popup-common-btn active" @click="sureVoucherBtn">
						确定
					</view>
				</view>
			</view>
		</u-popup>


		<u-popup v-model="isShowPopup2" mode="bottom" border-radius="56">
			<view class="popup-main">
				<view class="popup-common-title">
					— 选择摘要 —
				</view>

				<picker-view class="picker-view" style="height: 380rpx;" :value="digestValue" @change="changePicker">
					<picker-view-column>
						<view class="item" v-for="(item,index) in popupList" :key="index">{{item.name}}</view>
					</picker-view-column>
				</picker-view>

				<view class="popup-common-btnGroup">
					<view class="popup-common-btn" @click="isShowPopup2 = false">
						取消
					</view>
					<view class="popup-common-btn active" @click="sureDigest">
						确定
					</view>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import dayjs from "@/utils/dayjs.min.js"
	export default {
		data() {
			return {
				creditStatic: 0,
				debtorStatic: 0,
				currentZhaiYaoIndex: "",
				currentPingZhengIndex: "",
				credentials: [{
					abstract: "",
					subject_headings: "",
					balance_status: true,
					kemu_id: "",
					money: 0,
					hesuan_children: []
				}, {
					abstract: "",
					subject_headings: "",
					balance_status: false,
					kemu_id: "",
					money: 0,
					hesuan_children: []
				}],
				flag: 1,
				isShowPopup2: false,
				popupList: [{
						name: "提现",
					},
					{
						name: "利息收入",
					},
					{
						name: "支付银行手续费",
					},
					{
						name: "购入固定资产",
					},
				],
				digestValue: [0],
				digestValue2: [0],
				digestList: [{
					name: "插入凭证行",
					id: 1
				}, {
					name: "删除凭证行",
					id: 2
				}],
				isShowDigest: false,
				isShowTime: false,
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
				newmonth:0
			};
		},
		watch: {
			credentials: {
				deep: true,
				handler(val) {
					this.creditStatic = 0;
					this.debtorStatic = 0;
					this.credentials.forEach(item => {
						if (!item.balance_status) {
							this.creditStatic = this.creditStatic + Number(item.money)
						} else {
							this.debtorStatic = this.debtorStatic + Number(item.money)
						}
					})
				}
			},
		},
		onLoad(e) {
			this.getday()
			this.formData.recording_time = dayjs().format("YYYY-MM-DD");
			this.formData.years = dayjs().year();
			this.formData.month = dayjs().month() + 1
			this.formData.day = dayjs().date()
			// console.log(this.formData.month,">>>>>>>")
			this.flag = e.flag ? e.flag : 1;
		},
		methods: {
			getday(){
				this.$http("enterprise.Date_query/EndAudit", "post").then(res => {
					if (res.data.code == 1) {
						if(res.data.data.month){
							this.newmonth = res.data.data.month
						}
					}
				})
			},
			goHsPage(id, index) {
				this.$navigateTo({
					url: "/pages/my/assistAccounting/list?pid=" + id + "&flag=2"
				}).then(res => {
					let currentIndex = this.credentials[index].hesuan_children.findIndex(item => item.hs_id === res
						.pid);
					this.credentials[index].hesuan_children[currentIndex].memberName = res.name;
					this.credentials[index].hesuan_children[currentIndex].members = res.id;
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
			confirmTime(e) {
				this.formData.recording_time = `${e.year}-${e.month}-${e.day}`;
				this.formData.years = e.year
				this.formData.month = e.month
				this.formData.day = e.day
			},
			sureVoucherBtn() {
				this.isShowDigest = false;
				let currentIndex = this.digestList[this.digestValue2.join()].id;
				if (currentIndex == 2) {
					this.credentials.splice(this.currentPingZhengIndex, 1)
				} else if (currentIndex == 1) {
					this.credentials.push({
						abstract: "",
						subject_headings: "",
						balance_status: false,
						kemu_id: "",
						money: 0,
						hesuan_children: []
					})
				}
				this.$forceUpdate()
			},
			changePicker(e) {
				this.digestValue = e.detail.value;
			},
			changePicker2(e) {
				this.digestValue2 = e.detail.value;
			},
			sureDigest() {
				this.isShowPopup2 = false;
				this.credentials[this.currentZhaiYaoIndex].abstract = this.popupList[this.digestValue.join()].name;
				// this.$forceUpdate()
			},
			sureBtn() {
				console.log(this.formData.month,"KKKKKKKKKKK")
				if(Number(this.formData.month) <= this.newmonth){
					uni.showToast({
						title: "该月份不能新增凭证",
						icon: "none"
					})
					return
				}
				for (let item of this.credentials) {
					if (!item.abstract) {
						uni.showToast({
							title: "请输入摘要!",
							icon: "none"
						})
						return
					} else if (!item.kemu_id) {
						uni.showToast({
							title: "请选择科目!",
							icon: "none"
						})
						return
					} else if (!item.money) {
						uni.showToast({
							title: "请输入金额!",
							icon: "none"
						})
						return
					}
				}
				this.formData.documents_images = this.formData.documents_images ? this.formData.documents_images.join(
					",") : "";
					
					
					let adday = this.credentials
				adday.forEach(item => {
					item.balance_status = item.balance_status ? -1 : 1;
				})
				this.formData.credentials = JSON.stringify(adday);
				uni.showLoading({
					title:'正在提交中...',
				})
				this.$http("enterprise.Credentials/CreateCrdentials", this.formData, "post").then(res => {
					if (res.data.code == 1) {
						uni.hideLoading()
						uni.showToast({
							title: "新增成功",
							icon: "none"
						})
						setTimeout(() => {
							uni.navigateBack({
								delta: 1
							})
						}, 500)
					} else {
						this.formData.documents_images = this.formData.documents_images ? this.formData
							.documents_images.split(',') : [];
						this.credentials.forEach(item => {
							item.balance_status = item.balance_status == -1 ? true : false;
						})
					}
				})
			},
			uploadBtn() {
				let imgData = this.formData.documents_images ? this.formData.documents_images : [];
				this.$navigateTo({
					url: "/pages/workbench/voucher/uploadPage?imgData=" + JSON.stringify(imgData)
				}).then(res => {
					this.formData.documents_images = res;
				})
			},
			selectPopup2(index) {
				this.currentZhaiYaoIndex = index;
				this.isShowPopup2 = true;
			},
			selectDigest(index) {
				this.currentPingZhengIndex = index;
				this.isShowDigest = true;
			},
			selectTimeBtn() {
				this.isShowTime = true;
			}
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

		.time-div {
			background-color: #fff;
			display: flex;
			align-items: center;
			color: #150E33;
			font-size: 30rpx;
			padding: 32rpx 32rpx;
		}

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

		// 	.financial-popup-main {
		// 		padding: 0 32rpx;

		// 		.financial-popup-item {
		// 			margin-top: 32rpx;
		// 			display: flex;
		// 			flex-direction: column;
		// 			border-bottom: 1px solid #EEF2FF;
		// 			padding-bottom: 32rpx;

		// 			.financial-popup-item-hd {
		// 				display: flex;
		// 				align-items: center;

		// 				.financial-popup-hd-status {
		// 					background: #FF253D;
		// 					border-radius: 48rpx;
		// 					padding: 4rpx 8rpx;
		// 					color: #fff;
		// 					font-size: 24rpx;
		// 					margin-left: 16rpx;
		// 				}

		// 				.financial-popup-hd-txt {
		// 					font-size: 32rpx;
		// 					color: #150E33;
		// 					font-weight: bold;
		// 				}
		// 			}

		// 			.financial-popup-item-bd {
		// 				color: #7A7C94;
		// 				font-size: 28rpx;
		// 				margin-top: 30rpx;
		// 			}
		// 		}
		// 	}
	}
</style>

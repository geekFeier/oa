<template>
	<view>
		<u-navbar :is-back="true" title="新建账套" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
			<text v-if="flag==2" @click="delBtn" slot="right"
				style="margin-right: 32rpx;color: #4396F7;font-size: 30rpx;">删除</text>
		</u-navbar>
		<view class="hd-form">
			<u-form ref="uForm">
				<u-form-item label="账套名称" label-width="150">
					<u-input v-model="formData.name" type="input" placeholder="请输入账套名称" />
				</u-form-item>
				<u-form-item label="单位名称" label-width="150">
					<u-input v-model="formData.enterprise_unit" type="input" placeholder="请输入单位名称" />
				</u-form-item>
				<u-form-item label="社会信用码" label-width="190">
					<u-input v-model="formData.code" type="input" placeholder="请输入社会信用码" />
				</u-form-item>

				<u-form-item label="启用月份" label-width="150" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input disabled v-model="date" @click="selectTime" type="input" placeholder="请选择" />
				</u-form-item>

				<u-form-item label="会计准则" label-width="150" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input disabled v-model="accounting_cate_name" @click="selectStatus" type="input"
						placeholder="请选择" />
				</u-form-item>
				<u-form-item label="增值税种类" label-width="160" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input disabled v-model="tax_cate_name" @click="directionPopup" type="input" placeholder="请选择" />
				</u-form-item>
	<!-- 			<u-form-item :border-bottom="false" label="显示/隐藏" label-width="150">
					<u-switch slot="right" size="40" v-model="formData.status"></u-switch>
				</u-form-item> -->
				<!-- 	<u-form-item :border-bottom="false" label="编辑权限" label-width="150">
					<u-switch slot="right" size="40" v-model="formData.edit_auth"></u-switch>
				</u-form-item> -->
			</u-form>

		</view>
		<button type="default" class="sure_btn" @click="sureBtn">确定</button>


		<!-- 会计准则 -->
		<dellPopup :popupList="popupList2" title="会计准则" :itemId.sync="formData.accounting_cate_id"
			v-model="accounting_cate_name" :isShowPopup.sync="isShowPopup2">
		</dellPopup>


		<!-- 增值税种类 -->
		<dellPopup :popupList="popupList3" title="请选择增值税种类" v-model="tax_cate_name" :itemId.sync="formData.tax_cate_id"
			:isShowPopup.sync="isShowPopup3">
		</dellPopup>


		<u-picker v-model="showSelectTime" mode="time" :params="params" @confirm="sureTime"></u-picker>

		<u-modal v-model="isShowDel" content="你确定删除?" @confirm="sureAgree" :show-cancel-button="true"
			:show-confirm-button="true" :show-title="false">
		</u-modal>
	</view>
</template>

<script>
	import dellPopup from "@/components/dell-popup.vue"
	export default {
		data() {
			return {
				isShowDel: false,
				date: "",
				tax_cate_name: "",
				accounting_cate_name: "",
				flag: 1,
				params: {
					year: true,
					month: true,
					day: false,
					hour: false,
					minute: false,
					second: false
				},
				showSelectTime: false,
				isShowPopup: false,
				isShowPopup2: false,
				isShowPopup3: false,
				select: "../../../static/image/login/select.png",
				selecta: "../../../static/image/login/select-a.png",
				background: {
					backgroundColor: "#FFFFFF",
				},
				formData: {
					name: "",
					enterprise_unit: "",
					code: "",
					start_time: "",
					accounting_cate_id: "",
					tax_cate_id: "",
					status: true,
					// edit_auth: false,
				},
				ruleForm: {
					name: "请输入账套名称",
					enterprise_unit: "请输入单位名称",
					code: "请输入社会信用码",
					start_time: "请选择启用月份",
					accounting_cate_id: "请选择会计准则",
					tax_cate_id: "请选择增值税种类",
					// status: false,
					// edit_auth: false,
				},
				digestValue: [],
				popupList2: [],
				popupList3: [],

			};
		},
		components: {
			dellPopup
		},
		onLoad(e) {
			this.flag = e.flag;
			if (this.flag == 2) {
				this.formData = JSON.parse(e.data);
				this.formData.status = this.formData.status == 1 ? true : false;
			}

			this.getCate();

		},
		methods: {
			sureAgree() {
				let params = {
					b_id: this.formData.id
				}
				this.$http("enterprise.Account_books/delb", params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "删除成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						})
					}
				})
			},
			getCate() {
				this.$http("enterprise.Account_books/add", {}, "get").then(res => {
					if (res.data.code == 1) {
						this.popupList2 = res.data.data.meeting_norm;
						this.popupList3 = res.data.data.value_added_tax;

						if (this.flag == 2) {

							this.formData.status = this.formData.status == 1 ? true : false;
							this.date = this.formData.start_time.substring(0, 7);
							this.popupList2.forEach(item => {
								if (item.id == this.formData.accounting_cate_id) {
									this.accounting_cate_name = item.name
								}
							})
							this.popupList3.forEach(item => {
								if (item.id == this.formData.tax_cate_id) {
									this.tax_cate_name = item.name
								}
							})
							// tax_cate_name: "",
							// accounting_cate_name: "",
						}
					}
				})
			},
			delBtn() {
				this.isShowDel = true;
			},
			sureTime(e) {
				this.formData.start_time = `${e.year}-${e.month}-1`
				this.date = `${e.year}-${e.month}`
			},
			selectTime() {
				this.showSelectTime = true;
			},
			sureBtn() {
				if (this.flag == 1) {
					for (let key in this.formData) {
						if (!this.formData[key]) {
							uni.showToast({
								title: this.ruleForm[key],
								icon: "none"
							})
							return
						}
					}
					this.formData.status = this.formData.status ? 1 : 0;
					this.$http("enterprise.Account_books/add", this.formData, "post").then(res => {
						if (res.data.code == 1) {
							this.$http("enterprise.Subject/init", {}, "get").then(res => {
								if (res.data.code == 1) {
									uni.showToast({
										title: "添加成功"
									})
									setTimeout(() => {
										// this.$navigateBack(true)
										uni.navigateTo({
											url: "/pages/my/accountSet/successPage?flag=" +
												this.flag +
												"&data=" + this.formData
										})
									}, 500)
								}
							})

						}
					})
				} else {
					this.formData.status = this.formData.status ? 1 : 0;
					this.formData.b_id = this.formData.id;
					this.$http("enterprise.Account_books/upadte", this.formData, "post").then(res => {
						if (res.data.code == 1) {
							uni.showToast({
								title: "编辑成功"
							})
							setTimeout(() => {
								// this.$navigateBack(true)
								uni.navigateTo({
									url: "/pages/my/accountSet/successPage?flag=" + this.flag +
										"&data=" + JSON.stringify(this.formData)
								})
							}, 500)
						}
					})
				}

			},
			directionPopup() {
				this.isShowPopup3 = true;
			},
			selectStatus() {
				this.isShowPopup2 = true;
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

	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		margin-top: 60rpx;
		margin-bottom: 50rpx;
	}

	page {
		color: #150E33;
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

	/deep/ .u-form-item {
		padding: 14rpx 32rpx;
		font-size: 30rpx;
	}

	.radio-form {
		.radio-form-item {
			display: flex;
			align-items: center;

			padding: 32rpx;
		}
	}

	.hr-div {
		padding: 24rpx 32rpx;
		color: #7A7C94;
		background: #F6F9FE;
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

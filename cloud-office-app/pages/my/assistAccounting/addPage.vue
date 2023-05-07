<template>
	<view>
		<u-navbar :is-back="true" :title="flag ==1? '新增核算' : '编辑核算'" :border-bottom="false" back-icon-color="#000"
			:background="background" title-color="#000" :height="55">
			<text v-if="flag==2" @click="delBtn" slot="right"
				style="margin-right: 32rpx;color: #4396F7;font-size: 30rpx;">删除</text>
		</u-navbar>
		<u-form ref="uForm">

			<u-form-item label="核算编码" label-width="150">
				<u-input v-model="formData.code" type="input" placeholder="请输入核算编码" />
			</u-form-item>
			<u-form-item label="核算名称" label-width="150">
				<u-input v-model="formData.name" type="input" placeholder="请输入核算名称" />
			</u-form-item>
			<u-form-item :border-bottom="false" label="备注" label-width="150">
				<u-input v-model="formData.remarks" type="input" placeholder="请输入备注" />
			</u-form-item>
		</u-form>
		<button type="default" class="sure_btn" @click="sureBtn">确定</button>

		<u-modal v-model="isShowDel" content="你确定删除?" @confirm="sureAgree" :show-cancel-button="true"
			:show-confirm-button="true" :show-title="false">
		</u-modal>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				isShowDel: false,
				currentId: "",
				flag: "",
				currentIndex: 0,
				select: "../../../static/image/login/select.png",
				selecta: "../../../static/image/tab2/selecta.png",
				isShowPopup3: false,
				popupList3: [{
						name: "借"
					},
					{
						name: "贷"
					},
				],
				background: {
					backgroundColor: "#FFFFFF",
				},
				formData: {
					name: "",
					remarks: "",
					code: ""
				}
			};
		},
		onLoad(e) {
			this.flag = e.flag;
			this.currentId = e.pid;
			if (this.flag == 2) {
				this.formData = JSON.parse(e.data);
			} else {
				this.formData = {
					name: "",
					remarks: "",
					code: ""
				}
			}
		},
		components: {},
		methods: {
			sureAgree() {
				this.$http("enterprise.Accounting/del?id=" + this.formData.id, {}, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			delBtn() {
				this.isShowDel = true;

			},
			goDetailPage() {

			},
			sureBtn() {
				if(!this.formData.code){
					uni.showToast({
						title:"请输入核算编码",
						icon:"none",
					})
					return
				}else if(!this.formData.name){
					uni.showToast({
						title:"请输入核算名称",
						icon:"none"
					})
					return
				}else if(!this.formData.remarks){
					uni.showToast({
						title:"请输入备注",
						icon:"none"
					})
					return
				}
				if (this.flag == 1) {
					this.formData.pid = this.currentId;
					this.$http("enterprise.Accounting/createHesuan", this.formData, "post").then(res => {
						if (res.data.code == 1) {
							uni.showToast({
								title: "成功",
								icon: "none"
							})
							setTimeout(() => {
								this.$navigateBack(true)
							}, 500)
						}
					})
				} else {
					this.$http("enterprise.Accounting/editHesuan?id=" + this.formData.id, this.formData, "post").then(
						res => {
							if (res.data.code == 1) {
								uni.showToast({
									title: "成功",
									icon: "none"
								})
								setTimeout(() => {
									this.$navigateBack(true)
								}, 500)
							}
						})
				}

			},
			isHs(index) {
				this.currentIndex = index;
			},
			directionPopup() {
				this.isShowPopup3 = true;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		position: fixed;
		bottom: 32rpx;
		left: 50%;
		transform: translateX(-50%);
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
</style>

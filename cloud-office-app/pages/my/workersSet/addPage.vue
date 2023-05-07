<template>
	<view>
		<u-navbar :is-back="true" :title="flag==2 ? '编辑人员' : '新建人员'" :border-bottom="false" back-icon-color="#000"
			:background="background" title-color="#000" :height="55">
			<text v-if="flag==2" @click="saveBtn" slot="right"
				style="margin-right: 32rpx;color: #4396F7;font-size: 30rpx;">保存</text>
		</u-navbar>
		<view class="hd-form">
			<u-form ref="uForm">

				<u-form-item label="姓名" label-width="150">
					<u-input v-model="formData.username" type="input" placeholder="请输入姓名" />
				</u-form-item>
				<u-form-item label="身份证号" label-width="150">
					<u-input v-model="formData.card" type="input" placeholder="请输入身份证号" />
				</u-form-item>
				<u-form-item label="电话" label-width="150">
					<u-input maxlength="11" v-model="formData.mobile" type="number" placeholder="请输入电话" />
				</u-form-item>
				<u-form-item label="岗位" label-width="150" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input disabled v-model="job_name" type="input" placeholder="请选择岗位" @click="goJobList" />
				</u-form-item>
				<u-form-item label="用户编号" label-width="150">
					<u-input v-model="formData.serial_no" type="input" placeholder="请输入用户编号" />
				</u-form-item>
				<u-form-item :border-bottom="false" label="初始密码" label-width="150">
					<u-input v-model="formData.password" type="password" placeholder="初始密码" />
				</u-form-item>
			</u-form>

		</view>
		<button v-if="flag!=2" type="default" class="sure_btn" @click="sureBtn">确定</button>
		<button v-if="flag==2" type="default" class="sure_btn" @click="delBtn">删除</button>



	</view>
</template>

<script>
	export default {
		data() {
			return {
				flag: 1,
				select: "../../../static/image/login/select.png",
				selecta: "../../../static/image/login/select-a.png",
				background: {
					backgroundColor: "#FFFFFF",
				},
				currentId: "",
				job_name: "",
				formData: {
					username: "",
					card: "",
					mobile: "",
					serial_no: "",
					password: "",
					job_id: "",
				},
				formRule: {
					username: "请输入姓名",
					card: "请输入身份证号",
					mobile: "请输入电话",
					serial_no: "请输入用户编号",
					job_id: "请选择岗位",
					password: "请输入初始密码"
				},
				digestValue: [],

			};
		},
		onLoad(e) {
			this.flag = e.flag;
			if (this.flag == 2) {
				let _res = JSON.parse(e.data);
				this.formData.username = _res.username;
				this.formData.card = _res.card;
				this.formData.mobile = _res.mobile;
				this.formData.serial_no = _res.serial_no;
				this.formData.job_id = _res.jobs.job.id;
				this.currentId = _res.id;
				this.job_name = _res.jobs.job.name;
			}
		},
		components: {},
		methods: {
			saveBtn() {
				this.$http("enterprise.Staff/editStaff?id=" + this.currentId, this.formData, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "编辑成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			delBtn() {
				this.$http("enterprise.Staff/delete?id=" + this.currentId, {}, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "删除成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			sureBtn() {
				for (let key in this.formData) {
					if (!this.formData[key]) {
						uni.showToast({
							title: this.formRule[key],
							icon: "none"
						})
						return
					}
				}
				let sfReg = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
				if (!sfReg.test(this.formData.card)) {
					uni.showToast({
						title: "请输入有效身份证号码!",
						icon: "none"
					})
					return
				}
				const telphone = /^1[3456789]\d{9}$/; // 手机号
				if (!telphone.test(this.formData.mobile)) {
					uni.showToast({
						title: "请输入有效手机号!",
						icon: "none"
					})
					return
				}
				this.$http("enterprise.Staff/addStaff", this.formData, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "新建成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			goJobList() {
				this.$navigateTo({
					url: "/pages/my/workersSet/jobList?job_name=" + this.job_name + "&job_id=" + this.formData
						.job_id
				}).then(res => {
					this.job_name = res.currentName
					this.formData.job_id = res.currentIndex
				})
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

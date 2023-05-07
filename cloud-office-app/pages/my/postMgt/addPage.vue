<template>
	<view>
		<u-navbar :is-back="true" :title="flag==1 ? '新增岗位' : '编辑岗位'" :border-bottom="false" back-icon-color="#000"
			:background="background" title-color="#000" :height="55">
			<text v-if="flag==2" slot="right" style="margin-right: 32rpx;" @click="delBtn">删除</text>
		</u-navbar>
		<view class="hd-form">
			<u-form ref="uForm">
				<u-form-item label="岗位名称" label-width="150">
					<u-input v-model="formData.name" type="input" placeholder="请输入岗位名称" />
				</u-form-item>
				<u-form-item label="岗位权限" label-width="150" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input @click="showPopupBtn" disabled v-model="currentTypeName" type="input" placeholder="请选择" />
				</u-form-item>
				<u-form-item label="权限描述" label-width="150">
					<u-input v-model="formData.remark" type="input" placeholder="请输入权限描述" />
				</u-form-item>
				<!-- 		<u-form-item label="科目编码" label-width="150">
					<u-input disabled v-model="formData.value" type="input" placeholder="请输入科目编码" />
				</u-form-item> -->

			</u-form>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">确定</button>


		<!-- 选择岗位权限 -->
		<dellPopup :popupList="tabList" :itemId.sync="formData.flag" title="选择岗位权限" v-model="currentTypeName"
			:isShowPopup.sync="isShowPopup">
		</dellPopup>




	</view>
</template>

<script>
	import dellPopup from "@/components/dell-popup.vue";
	export default {
		data() {
			return {
				flag: 1,
				flagName: "",
				treeData: [],
				HsListData: [],
				hesuan: false,
				currentParentName: "",
				currentTypeName: "",
				currentBalanceName: "",
				currentStatusName: "",
				listData: [],
				currentFy: {
					currentPage: 1,
					currentSize: 999
				},
				swiperCurrent: 0,
				superiorPopup: false,
				tabList: [{
					name: "管理员",
					id: 1
				}, {
					name: "部门经理",
					id: 2
				}, {
					name: "会计",
					id: 3
				}, {
					name: "职员",
					id: 4
				}],
				isShowPopup: false,
				select: "../../../static/image/login/select.png",
				selecta: "../../../static/image/login/select-a.png",
				background: {
					backgroundColor: "#FFFFFF",
				},
				formData: {
					flag: "",
					name: "",
					remark: "",
					weigh: "0"
				},
				digestValue: [],
				category_id: "",
				popupList2: [{
						name: "正常",
						id: "normal"
					},
					{
						name: "不可用",
						id: "hidden"
					},
				],
				popupList3: [{
						name: "借",
						id: -1
					},
					{
						name: "贷",
						id: 1
					},
				],
				barStyle: {
					textAlign: "center",
				},
				page: 1,
				limit: 10
			};
		},
		components: {
			dellPopup,
		},
		onLoad(e) {
			this.flag = e.flag ? e.flag : 1;
			if (this.flag == 2) {
				this.formData = JSON.parse(e.data);
				var flagObj = this.tabList.find(item => {
					return item.id === Number(this.formData.flag)
				});
				this.currentTypeName = flagObj.name;

			}

		},
		methods: {
			delBtn() {
				let params = {
					job_id: this.formData.id
				}
				this.$http("enterprise.Jobs/delJob", params, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "删除成功"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			showPopupBtn() {
				this.isShowPopup = true;
			},
			sureBtn() {
				if (!this.formData.name) {
					uni.showToast({
						title:"请输入岗位名称!",
						icon:"none"
					})
					return
				}
				if (!this.formData.flag) {
					uni.showToast({
						title:"请选择岗位权限!",
						icon:"none"
					})
					return
				}
				if (!this.formData.remark) {
					uni.showToast({
						title:"请输入权限描述!",
						icon:"none"
					})
					return
				}
				if (this.flag == 1) {
					this.$http("enterprise.Jobs/CreateJob", this.formData, "post").then(res => {
						if (res.data.code == 1) {
							uni.showToast({
								title: "新增成功",
								icon: "none"
							})
							setTimeout(() => {
								this.$navigateBack(true)
							}, 500)
						}
					})
				} else {
					this.formData.job_id = this.formData.id;
					this.formData.weigh = "0";
					this.$http("enterprise.Jobs/UpdateJob", this.formData, "post").then(res => {
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
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	.popupConBox {
		padding: 0 32rpx;
		margin-top: 8rpx;

		.main-item {
			display: flex;
			justify-content: space-between;
			border-bottom: 1px solid #EEF2FF;
			padding: 32rpx 0;
			font-size: 32rpx;
		}
	}

	.picker-view {
		.item {
			font-size: 34rpx;
			text-align: center;
		}
	}

	.popupMainBox {
		padding: 32rpx;
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

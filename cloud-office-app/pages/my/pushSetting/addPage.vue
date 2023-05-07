<template>
	<view>
		<u-navbar :is-back="true" title="新增推送" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>

		<view class="mainBox">
			<u-form-item label="选择岗位" label-width="150" right-icon="arrow-right"
				:right-icon-style="{color:'#7d7f97'}">
				<u-input disabled v-model="job_name" @click="selectJobName" type="input"
					placeholder="请选择" />
			</u-form-item>
			<u-form-item label="推送标题" label-width="150">
				<u-input v-model="formData.title" type="input" placeholder="请输入推送标题" />
			</u-form-item>
			<view style="font-size: 30rpx;margin-top: 32rpx;">
				推送内容
			</view>
			<!-- <input type="textarea" v-model="value" style="min-height: 100px" :auto-height="true"/> -->
			<u-input v-model="formData.content" type="textarea" :height="340" :auto-height="true" :clearable="false"
				style="margin-top: 18px;" />


			<u-form-item style="margin-top: 50rpx;" label="添加链接" label-width="150" :border-bottom="false">
				<u-input v-model="formData.url" type="input" placeholder="请输入链接" />
			</u-form-item>



			<button type="default" class="addBtn" @click="addBtn">保存并发布</button>
		</view>
		<!-- 增值税种类 -->
		<dellPopup :popupList="popupList" title="请选择岗位" v-model="job_name" :itemId.sync="formData.job_id"
			:isShowPopup.sync="isShowPopup">
		</dellPopup>
	</view>
</template>

<script>
	import dellPopup from "@/components/dell-popup.vue"
	export default {
		data() {
			return {
				page:1,
				limit:10,
				isShowPopup:false,
				job_name:"",
				popupList:[],
			
				formData:{
					content: "",
					url: "",
					job_id:"",
					title:""
				},
				background: {
					backgroundColor: "#FFFFFF",
				},
			}
		},
		components:{
			dellPopup
		},
		onLoad() {
			this.getlistData();
		},
		methods: {
			getlistData() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
					sort: "weigh",
					order: "asc"
				}
				this.$http("enterprise.Jobs/index", params, "get").then(res => {
					this.popupList = res.data.data.rows;
				})
			},
			selectJobName(){
				this.isShowPopup = true;
			},
			addBtn() {
				if (this.formData.title == '') {
					uni.showToast({
						title: "请填写标题",
						icon: "none"
					})
					return
				}
				if (this.formData.content == '') {
					uni.showToast({
						title: "请填写内容",
						icon: "none"
					})
					return
				}
				if (this.formData.url == '') {
					uni.showToast({
						title: "请填写链接",
						icon: "none"
					})
					return
				}
				this.$http("enterprise.Smspush/push", this.formData, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "推送成功",
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

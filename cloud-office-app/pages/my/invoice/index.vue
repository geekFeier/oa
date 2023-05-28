<template>
	<view>
		<u-navbar :is-back="true" title="开发票" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		
		<view class="main-bd">
			<u-form :model="form" ref="uForm">
				<u-form-item label-width="150" label="发票类型">
					<!-- <u-radio-group v-model="form.ftype">
						<u-radio v-for="(item, index) in ftypeList" :key="item.name" :name="item.value">
							{{item.name}}
						</u-radio>
					</u-radio-group> -->
					企业
				</u-form-item>
				<u-form-item label-width="150" label="开票类型">
				<!-- 	<u-radio-group v-model="form.ktype">
						<u-radio v-for="(item, index) in ktypeList" :key="item.name" :name="item.value"
							:disabled="item.disabled">
							{{item.name}}
						</u-radio>
					</u-radio-group> -->
					电子
				</u-form-item>
				<u-form-item label-width="150" label="发票抬头">
					<u-input type="input" v-model="form.title" placeholder="请输入发票抬头(必填)" />
				</u-form-item>
				<u-form-item label-width="150" label="税号">
					<u-input type="input" v-model="form.serial_no" placeholder="请输入纳税人识别号(必填)" />
				</u-form-item>
				<u-form-item label-width="150" label="公司地址">
					<u-input type="input" v-model="form.address" placeholder="请输入公司地址" />
				</u-form-item>
				<u-form-item label-width="150" label="电话号码">
					<u-input type="input" v-model="form.telephone" placeholder="请输入电话号码" />
				</u-form-item>
				<u-form-item label-width="150" label="开户银行">
					<u-input type="input" v-model="form.bank_name" placeholder="请输入开户银行" />
				</u-form-item>
				<u-form-item label-width="150" label="银行账号">
					<u-input type="input" v-model="form.bank_acount" placeholder="请输入银行账号" />
				</u-form-item>
				<u-form-item label-width="150" label="邮箱">
					<u-input type="input" v-model="form.email" placeholder="请输入邮箱(必填)" />
				</u-form-item>
			</u-form>
		</view>
		<button type="default" class="sure_btn" @click="loginOutBtn">申请开票</button>

		<u-modal v-model="isShowOut" content="你确定申请开票吗?" @confirm="sureAgree" :show-cancel-button="true"
			:show-confirm-button="true" :show-title="false">
		</u-modal>

	</view>
</template>

<script>
	import {
		mapState
	} from "vuex";
	export default {
		data() {
			return {
				isShowOut: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
				ftypeList: [{
					name: '企业',
					value: '1'
				}
				// , {
				// 	name: '个人及政府事业单位',
				// 	value: '2',
				// 	disabled: true
				// },
				],
				ktypeList: [{
					name: '电子',
					value: '1'
				}
				// , {
				// 	name: '纸质',
				// 	value: '2',
				// 	disabled: true
				// },
				],
				form: {
					ftype: '1',
					ktype: '1',
					title: '',
					serial_no: '',
					email: '',
					address:'',
					telephone:'',
					bank_name:'',
					bank_acount:''
				}
			};
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo,
			})
		},
		onLoad(e) {

		},
		methods: {
			sureAgree() {
				
				this.$http("enterprise.invoice/addInvoice", this.form, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "开票成功",
							icon: "none"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}else{
						uni.showToast({
							title: "开票失败",
							icon: "none"
						})
					}
				}).catch(()=>{
					uni.showToast({
						title: "开票失败",
						icon: "none"
					})
				})
			},
			loginOutBtn() {
				if(!this.form.title){
					uni.showToast({
						title: "请输入发票抬头",
						icon: "none"
					})
					return false
				}
				if(!this.form.serial_no){
					uni.showToast({
						title: "请输入纳税人识别号",
						icon: "none"
					})
					return false
				}
				if(!this.form.email){
					uni.showToast({
						title: "请输入邮箱",
						icon: "none"
					})
					return false
				}
				this.isShowOut = true;
			},
		}
	}
</script>

<style lang="scss" scoped>
	page {

		background: #FBFCFF;
	}

	/deep/ .u-grid-item-box {
		padding: 16rpx 0 !important;
	}

	/deep/ .u-cell__value {
		color: #150E33;
	}

	.main-bd {
		padding: 0 32rpx;
		margin-top: 20rpx;
	}

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
	 
</style>
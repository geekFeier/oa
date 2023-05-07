<template>
	<view>
		<u-navbar :is-back="true" title="编辑期初" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<u-form ref="uForm">

			<u-form-item label="科目编码" label-width="150">
				<u-input disabled v-model="formData.serial" type="input" placeholder="请输入科目编码" />
			</u-form-item>
			<u-form-item label="科目名称" label-width="150">
				<u-input disabled  v-model="formData.name" type="input" placeholder="请输入科目名称" />
			</u-form-item>
			<u-form-item label="余额方向" label-width="150" >
				<u-input disabled v-model="currentTypeName" @click="directionPopup" type="input"
				 />
			</u-form-item>
			<u-form-item :border-bottom="false" label="是否有辅助核算" label-width="220">
			<!-- 	<input type="text" placeholder="请输入" v-if="currentIndex==1" style="margin-right: 6rpx;" /> -->
				<view class="flex" slot="right" style="color: #150E33;">
					<view class="flex align-center margin-right-lg" >
						<image :src="currentIndex==0 ? selecta : select" mode=""
							style="width: 40rpx;height: 40rpx;margin-right: 24rpx;"></image>
						<text>是</text>
					</view>
					<view class="flex align-center">
						<image :src="currentIndex==1 ? selecta : select" mode=""
							style="width: 40rpx;height: 40rpx;margin-right: 24rpx;"></image>
						<text>否</text>
					</view>
				</view>
			</u-form-item>

			<u-form-item   v-if="currentIndex == 0" :border-bottom="false" label="科目核算明细"
				label-width="190" right-icon="arrow-right" :right-icon-style="{color:'#7d7f97'}">
				<input style="width: 100%;"  @click="goDetailPage(formData.id)"  type="text" value="" placeholder="" disabled/>
			</u-form-item>
			<u-form-item label="年初余额" label-width="150">
				<u-input disabled  v-model="formData.value" type="input" placeholder="自动计算无需填写" />
			</u-form-item>
			<u-form-item label="期初余额" label-width="150">
				<u-input  v-model="params.initial_balance" type="input" placeholder="请输入期初余额" />
			</u-form-item>
			<u-form-item label="借方累计" label-width="150">
				<u-input  v-model="params.debit_balance" type="input" placeholder="请输入借方累计" />
			</u-form-item>
			<u-form-item label="贷方累计" label-width="150">
				<u-input  v-model="params.creditor_balance" type="input" placeholder="请输入贷方累计" />
			</u-form-item>
	
		</u-form>
		<button type="default" class="sure_btn" @click="sureBtn">保存</button>



		<!-- 余额方向 -->
		<dellPopup :popupList="popupList3" title="请选择余额方向" v-model="formData.balance" :isShowPopup.sync="isShowPopup3">
		</dellPopup>
	</view>
</template>

<script>
	import dellPopup from "@/components/dell-popup.vue"
	export default {
		data() {
			return {
				params:{
					initial_balance:"0",
					debit_balance:"0",
					creditor_balance:"0"
				},
				currentTypeName:"",
				currentId: "",
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
					type: "",
					balance: "",
					value: ""
				}
			};
		},
		components: {
			dellPopup
		},
		watch: {
			params: {
				deep: true,
				handler(val) {
					this.formData.value = 0;
					if(this.currentTypeName == '贷'){
						this.formData.value = Number(this.params.initial_balance) + Number(this.params.debit_balance) - Number(this.params.creditor_balance)
					}else{
						this.formData.value = Number(this.params.initial_balance) - Number(this.params.debit_balance) + Number(this.params.creditor_balance)
					}
					
				},
				}
		},
		onLoad(e) {
			this.currentId = e.id;
			this.params.k_id = e.id
			this.getDetail();
			uni.$on('choosePath', (res) => {
				console.log(res.name,"KKKKKKKK")
				this.currentTypeName = res.name
			})
		},
		methods: {
			goDetailPage(id) {
				console.log(id,"+++++++++++++++++666666666666666666666666");
				uni.navigateTo({
					url:"/pages/my/beginningPeriod/detail?id="+id
				})
			},
			getDetail() {
				let params = {
					k_id: this.currentId
				}
				this.$http("enterprise.Subject/initializeParam",params,"get").then(res=>{
					console.log(res,"++++++++++++++++++++++++++++++++++++++");
					this.formData = res.data.data;
					this.currentTypeName = this.formData.balance_status == -1 ? "借" : "贷"
					this.currentIndex = this.formData.hesuan == "normal"  ? 0 : 1
				})
			},
			sureBtn() {
				this.$http("enterprise.Subject/initializeParam",this.params,"post").then(res=>{
					if(res.data.code==1){
						uni.showToast({
							title:"设置成功",
							icon:"none"
						})
						setTimeout(()=>{
							this.$navigateBack(true)
						},500)
					}
				})
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
		margin-top: 120rpx;
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
</style>

<template>
	<view>
		<u-navbar :is-back="true" back-icon-color="#000" :background="background" title="发票信息" :height="55">
		</u-navbar>
		<view class="mainBox">
			<u-form ref="uForm">
				<u-form-item label="发票类型" label-width="150">
					<view class="form-item-radio" slot="right">
						<view class="form-item-radio-div" @click="changeCurrent1(1)">
							<image style="width: 40rpx;height: 40rpx;margin-right: 12rpx;" :src="currentIndex1==1 ? selecta : select"
								mode=""></image>
							<text>企业</text>
						</view>
						<view class="form-item-radio-div" @click="changeCurrent1(2)">  
							<image style="width: 40rpx;height: 40rpx;margin-right: 12rpx;" :src="currentIndex1==2 ? selecta : select"
								mode=""></image>
							<text>个人</text>
						</view>
					</view>
				</u-form-item>
				<u-form-item label="发票抬头" label-width="150">
					<u-input v-model="name" type="input" placeholder="请输入发票抬头" />
				</u-form-item>
				<u-form-item label="开票类型" label-width="150">
					<view class="form-item-radio" slot="right">
						<view class="form-item-radio-div" @click="changeCurrent2(1)">
							<image style="width: 40rpx;height: 40rpx;margin-right: 12rpx;" :src="currentIndex2==1 ? selecta : select"
								mode=""></image>
							<text>电子</text>
						</view>
						<view class="form-item-radio-div" @click="changeCurrent2(2)">  
							<image style="width: 40rpx;height: 40rpx;margin-right: 12rpx;" :src="currentIndex2==2 ? selecta : select"
								mode=""></image>
							<text>纸质</text>
						</view>
					</view>
				</u-form-item>
				<u-form-item label="邮寄地址" label-width="150">

					<u-input v-model="address" type="input" placeholder="请输入邮寄地址" />
				</u-form-item>
				<u-form-item label="收件人" label-width="150">
					<u-input v-model="peoname" type="input" placeholder="请输入收件人" />
				</u-form-item>
				<u-form-item label="联系电话" label-width="150">
					<u-input v-model="phone" type="input" placeholder="请输入联系电话" />
				</u-form-item>
			</u-form>
		</view>
			<button type="default" class="addBtn" @click="submitBtn">保存</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				currentIndex1:1,
				currentIndex2:1,
				select: "../../../static/image/tab2/select.png",
				selecta: "../../../static/image/tab2/selecta.png",
				name: "",//抬头
				address:'',
				peoname:'',
				phone:'',
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad(e) {
			console.log(JSON.parse(e.contny))
			let addlist = JSON.parse(e.contny)
			this.name = addlist.buyer
			this.address = addlist.address
			this.peoname = addlist.consignee
			this.phone = addlist.phone
			this.currentIndex1 = addlist.lnvoicing_type =='electron'? 1: 2
			this.currentIndex1 = addlist.receipt_type == 'enterprise' ? 1 :2
			
		},
		methods:{
			submitBtn(){
				if(this.name == ''){
					uni.showToast({
						title: "请输入发票抬头",
						icon: "none"
					})
					return
				}
				if(this.address == ''){
					uni.showToast({
						title: "请输入邮寄地址",
						icon: "none"
					})
					return
				}
				if(this.peoname == ''){
					uni.showToast({
						title: "请输入收件人",
						icon: "none"
					})
					return
				}
				if(this.phone == ''){
					uni.showToast({
						title: "请输入联系电话",
						icon: "none"
					})
					return
				}
				console.log(this.currentIndex1)
				let istype = ''
				if(this.currentIndex1 == 1){
					istype = 'enterprise'
				}else{
					istype = 'individual'
				}
				
				let isbook = ''
				if(this.currentIndex2 == 1){
					isbook = 'electron'
				}else{
					isbook = 'paper'
				}
				// 1是企业  2是个人
				console.log(this.currentIndex2,"???????")
				let that = this
				// 1是电子  2是纸质
				let param = {
					receipt_type:istype,
					buyer:that.name,
					lnvoicing_type:isbook,
					address:that.address,
					phone:that.phone,
					consignee:that.peoname
				}
				this.$http("User/receipt", param, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: '提交成功',
							icon: 'none'
						})
						setTimeout(function() {
							let params = {
								name:JSON.stringify(param)
							}
							that.$navigateBack(params,1)
							// uni.navigateBack({
							// 	delta:1
							// })
						}, 1000)
					}
				})
			},
			changeCurrent1(index){
				this.currentIndex1 = index;
			},
			changeCurrent2(index){
				this.currentIndex2 = index;
				
			}
		}
	}
</script>

<style lang="scss" scoped>
	.addBtn {
		text-align: center;
		width: 88%;
		height: 90rpx;
		line-height: 90rpx;
		color: #fff;
		background: #4396F7;
		margin-top: 60rpx;
		margin-bottom: 40rpx;
		border-radius: 49px;
	}
	
	.mainBox {
		.form-item-radio{
			display: flex;
			// justify-content: flex-end;
			// margin-left: 260rpx;
			.form-item-radio-div{
				display: flex;
				align-items: center;
				margin-right: 24rpx;
			}
		}
		/deep/ .u-input__input {
			display: flex !important;
			justify-content: flex-end !important;
		}

		/deep/ .uni-input-wrapper {
			width: 80%;
			// background-color: pink;
		}

		/deep/ .u-form-item__body {
			display: flex;
			justify-content: flex-end;
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

	}
</style>

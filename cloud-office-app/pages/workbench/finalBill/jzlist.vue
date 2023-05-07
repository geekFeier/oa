<template>
	<view>
		<u-navbar :is-back="true" title='结转模板列表' :border-bottom="false" back-icon-color="#000"
			:background="background" title-color="#000" :height="55">
			<view class="content" slot="right" @click="rightbtn()">
					添加模板
			</view>
		</u-navbar>
		<view class="mainBox">
				<view class="may" v-for="(item,index) in credentials" :key="index" @click="gdetail(item.id)">
					<view class="view-flx">
						<view>{{item.name}}</view>
					</view>
					<view class="view-flxa">
						<view></view>
						<view class="view-one">
							<!-- <view @click.stop="jz(item.id)" class="view-jz">结转</view> -->
							<view @click.stop="bj(item.id)" class="view-bj">编辑</view>
							<view @click.stop="deletae(item.id)">删除</view>
						</view>
					</view>
				</view>
		</view>
		<view class="yjjz" v-if="credentials.length > 0" @click="yjza()">
			一键结转
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				remarks:'',//名称
				anchildreb:'请选择',//公式取值
				anchildren:'请选择',//自动平衡
				credentials: [],
				years:'',
				month:'',
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad(e) {
			this.years = e.years
			this.month = e.month
		},
		onShow() {
			this.getlist()
		},
		methods: {
			//
			yjza(){
				let param = {
					years:this.years,
					month:this.month
				}
				this.$http("enterprise.Date_query/NextCheck", param, "get").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "一键结转成功",
							icon: "none"
						})
					}
				})
			},
			//结转按钮
			jz(){
				
			},
			//编辑按钮
			bj(id){
				uni.navigateTo({
					url:'./bjjz?id=' + id
				})
			},
			//删除按钮
			deletae(id){
				let param = {
					pro_id:id
				}
				this.$http("enterprise.Date_query/TemplateDel", param, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "删除成功",
							icon: "none"
						})
						setTimeout(()=>{
							this.getlist()
						},1000)
					}
				})
			},
			//新赠模板
			rightbtn(){
				uni.navigateTo({
					url:'./list'
				})
			},
			//列表
			getlist(){
				this.$http("enterprise.Date_query/TemplateList", "post").then(res => {
				if (res.data.code == 1) {
					this.credentials = res.data.data
					console.log(this.credentials)
				}
			})
			},
			//详情
			gdetail(id){
				uni.navigateTo({
					url:'fdetail?id=' + id
				})
			},
			
		}
	}
</script>

<style lang="scss" scoped>
	page{
		background-color: #fff;
	}
	.yjjz{
		width: 200rpx;
		height: 66rpx;
		background-color: red;
		color: #fff;
		border-radius: 30rpx;
		line-height: 66rpx;
		text-align: center;
		margin: 66rpx auto;
	}
	.content{
		margin-right: 22rpx;
	}
	.mainBox {
		padding: 0 30rpx;
		.may{
			
			border-bottom: 1px solid #E0E0E0;
		}
		.view-flx{
			height: 88rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			font-size: 30rpx;
			font-weight: bolder;
		}
		.view-flxa{
			height: 66rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			.view-one{
				display: flex;
				justify-content: space-between;
				align-items: center;
				.view-jz{
					background-color: #67C23A;
				}
				.view-bj{
					background-color: #E6A23C;
				}
				view{
					padding: 10rpx 30rpx;
					background-color: red;
					color: #fff;
					border-radius: 16rpx;
					margin-right: 12rpx;
				}
			}
		}
	}
</style>

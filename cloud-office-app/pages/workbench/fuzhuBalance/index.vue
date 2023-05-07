<template>
	<view>
		<u-navbar :is-back="true" title="辅助核算余额表" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

	
		</u-navbar>

		<view class="mainBox">
			<view class="main-item" v-for="item in listData" :key="item.id" @click="goList(item.id)">
				<text class="main-item-title">{{item.name}}</text>
				<u-icon name="arrow-right" color="#A2A3B4"></u-icon>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				name: "",
				isShowAlert: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
				listData: []
			};
		},
		onLoad() {
			this.getCate();
		},
		methods: {
			confirmBtn() {
				let params = {
					name: this.name
				}
				this.$http("enterprise.Accounting/createCate", params, "post").then(res => {
					if(res.data.code==1){
						uni.showToast({
							title:"添加成功",
							icon:"none"
						})
						this.getCate();
					}
				})
			},
			addBtn() {
				this.isShowAlert = true;
			},
			getCate() {
				let params = {
					pid: 0,
					search: ""
				}
				this.$http("enterprise.Accounting/list?search&pid=0", params, "get").then(res => {
					this.listData = res.data.data.rows;
				})
			},
			goList(id) {
				uni.navigateTo({
					url:"/pages/workbench/fuzhuBalance/list?id="+id
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.mainBox {
		padding: 0 32rpx;

		.main-item {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 32rpx 0;
			border-bottom: 1px solid #F6F9FE;

			.main-item-title {
				font-size: 32rpx;
			}


		}
	}

	/deep/ .inputClass {
		margin: 18rpx 0;
		width: 520rpx !important;
		height: 76rpx !important;
		background: #FFFFFF;

		border: 1px solid #B5BFDA;
		display: flex;
		align-items: center;
		padding-left: 12rpx;
		border-radius: 8rpx;

		.input-placeholder {
			color: #B5BFDA !important;

		}
	}
</style>

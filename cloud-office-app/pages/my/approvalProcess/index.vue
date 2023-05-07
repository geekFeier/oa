<template>
	<view>
		<u-navbar :is-back="true" title="审批流程" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" :key="index">
				<view class="main-item-l">
					{{item.name}}
				</view>
				<view class="main-item-r" @click="editBtn(item.id)">
					<text>编辑权限</text>
					<u-icon name="arrow-right" color="#9FA0B2" size="30" style="margin-left: 16rpx;"></u-icon>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				listData: [],
				page: 1,
				limit: 10,
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad() {
			this.getListData();
			this.$on("changeApprvalProcess",()=>{
					this.listData =  [];
					this.page = 1;
					this.getListData()
			})
		},
		onReachBottom() {
			this.page++;
			this.getListData()
		},
		methods: {
			getListData() {
				let params = {
					page: this.page,
					limit: this.limit,
					order: "asc",
					sort: "weigh",
					offset: (this.page - 1) * this.limit,
				}
				this.$http("enterprise.Jobs/index", params, "get").then(res => {
					if(res.data.code==1){
						this.listData = this.listData.concat(res.data.data.rows);
					}
				})
			},
			editBtn(id) {
				this.$navigateTo({
					url: "/pages/my/approvalProcess/editAuth?id="+id
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
		background: #FBFCFF;
	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			padding: 32rpx 24rpx;
			background-color: #fff;
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 32rpx;
			border-radius: 16rpx;

			.main-item-l {
				font-size: 32rpx;
			}

			.main-item-r {
				font-size: 28rpx;
				color: #7A7C94;
			}
		}
	}
</style>

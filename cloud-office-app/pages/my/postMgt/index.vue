<template>
	<view>
		<u-navbar :is-back="true" title="岗位管理" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" :key="index">
				<view class="main-item-l">
					{{item.name}}
				</view>
				<view class="main-item-r" @click="editBtn(item)">
					<text>编辑</text>
					<u-icon name="arrow-right" color="#9FA0B2" size="30" style="margin-left: 16rpx;"></u-icon>
				</view>
			</view>
		</view>
		<image src="../../../static/image/tab1/add.png" class="addBtn" mode="" @click="goAddPage"></image>
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
		},
		onReachBottom() {
			this.page++;
			this.getListData()
		},
		methods: {
			goAddPage() {
				this.$navigateTo({
					url: "/pages/my/postMgt/addPage?flag=1"
				}).then(res => {
					this.page = 1;
					this.listData = [];
					this.getListData();
				})
			},
			getListData() {
				let params = {
					page: this.page,
					limit: this.limit,
					order: "asc",
					sort: "weigh",
					offset: (this.page - 1) * this.limit,
				}
				this.$http("enterprise.Jobs/index", params, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = this.listData.concat(res.data.data.rows);
					}
				})
			},
			editBtn(item) {
				this.$navigateTo({
					url: `/pages/my/postMgt/addPage?flag=2&data=` + JSON.stringify(item)
				}).then(res => {
					this.page = 1;
					this.listData = [];
					this.getListData();
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

	.addBtn {
		position: fixed;
		bottom: 158rpx;
		right: 32rpx;
		width: 110rpx;
		height: 110rpx;
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

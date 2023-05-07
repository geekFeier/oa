<template>
	<view class="memoBox">
		<u-navbar :is-back="true" title="我的便签" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" :key="index" @click="goDetai(item.id,item)">
				<view class="main-item-l">
					<view class="main-item-l-title">
						{{item.content}}
					</view>
					<text class="main-item-l-time">{{item.updatetime}}</text>
				</view>
				<image class="main-item-r" :src="item.images[0]" mode=""></image>
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
		onShow() {
			this.getListData();
		},
		methods: {
			getListData() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit
				}
				this.$http("enterprise.Stikcy_note/index", params, "get").then(res => {
					this.listData = res.data.data.rows;
					console.log(res, "//////////////////////////////");
				})
			},
			goDetai(id,item) {
				this.$navigateTo({
					url: "/pages/workbench/memo/detail?id="+id+"&data="+JSON.stringify(item)
				})
			},
			goAddPage() {
				this.$navigateTo({
					url: "/pages/workbench/memo/addPage"
				}).then(res => {
					this.page = 1;
					this.getListData();
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: #FBFCFF;
	}

	.addBtn {
		position: fixed;
		bottom: 206rpx;
		right: 32rpx;
		width: 110rpx;
		height: 110rpx;
	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			background: #fff;
			width: 686rpx;
			padding: 32rpx;
			border-radius: 18rpx;
			display: flex;
			justify-content: space-between;
			margin-top: 24rpx;

			.main-item-l {
				display: flex;
				flex-direction: column;
				justify-content: space-between;

				.main-item-l-title {
					font-size: 34rpx;
					color: #150E33;
					font-weight: bold;
				}

				.main-item-l-time {
					color: #B5BFDA;
					font-size: 26rpx;

				}
			}

			.main-item-r {
				width: 180rpx;
				height: 180rpx;
			}
		}

	}
</style>

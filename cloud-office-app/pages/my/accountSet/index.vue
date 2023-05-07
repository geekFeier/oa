<template>
	<view>
		<u-navbar :is-back="true" title="账套设置" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="mainBox">
			<view class="header-item flex align-center justify-between">
				<view class="header-item-l">
					新增账套
				</view>
				<view class="header-item-r">
					{{listData.length}}/5
				</view>
			</view>

			<view class="addBtn" @click="addBtn()">
				<u-icon name="plus" color="#B5BFDA" size="46"></u-icon>
			</view>

			<view class="bd-item flex align-center justify-between" v-for="(item,index) in listData" :key="index">
				<view class="bd-item-l">
					{{item.name}}
				</view>
				<view class="bd-item-r" @click="editBtn(item)">
					<text>编辑</text>
					<u-icon style="margin-left: 16rpx;" name="arrow-right" color="#7A7C94" size="30"></u-icon>
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
			this.getList();
			uni.$on("changeAccount", () => {
				this.getList();
			})
		},
		methods: {
			getList() {
				let params = {
					offset: (this.page - 1) * 10,
					page: this.page,
					limit: this.limit
				}
				this.$http("enterprise.Account_books/index", params, "post").then(res => {
					if (res.data.code == 1) {
						this.listData = res.data.data.rows;
					} else {
						this.listData =  [];
					}
				})
			},
			addBtn() {
				this.$navigateTo({
					url: "/pages/my/accountSet/addPage?flag=1"
				})
			},
			editBtn(data) {
				this.$navigateTo({
					url: "/pages/my/accountSet/addPage?flag=2&data=" + JSON.stringify(data)
				}).then(res=>{
					this.getList();
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
	}

	.bd-item {
		padding: 32rpx 0;
		border-bottom: 1px solid #F6F9FE;

		.bd-item-l {
			font-size: 30rpx;
		}

		.bd-item-r {
			color: #7A7C94;
			font-size: 28rpx;
		}
	}

	.mainBox {
		padding: 0 32rpx;

		.header-item {
			padding: 32rpx 0;
			font-size: 30rpx;
		}

		.addBtn {
			width: 140rpx;
			height: 140rpx;
			background: #FBFCFF;
			border: 1rpx solid #B5BFDA;
			text-align: center;
			border-radius: 16rpx;
			line-height: 140rpx;
			margin-bottom: 20rpx;
		}
	}
</style>

<template>
	<view>
		<u-navbar :is-back="true" title="选择岗位" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="main-item" @click="selectBtn(item.id,item.name)" v-for="(item,index) in listData" :key="index">
				<view class="main-item-l">
					<image :src="currentIndex == item.id ? selecta : select" style="width: 40rpx;height: 40rpx;"
						mode="">
					</image>
				</view>
				<view class="main-item-r">
					<view class="main-item-r-hd">
						{{item.name}}
					</view>
					<view class="main-item-r-bd">
						{{item.remark}}
					</view>
				</view>
			</view>
		</view>


		<button type="default" class="sure_btn" @click="sureBtn">确定</button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				currentName: "",
				page: 1,
				limit: 10,
				listData: [],
				currentIndex: 0,
				select: "../../../static/image/login/select.png",
				selecta: "../../../static/image/login/select-a.png",
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad(e) {
			this.currentName = e.job_name;
			this.currentIndex = e.job_id;
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
					this.listData = res.data.data.rows;
				})
			},
			sureBtn() {
				let params = {
					currentName: this.currentName,
					currentIndex: this.currentIndex
				}
				this.$navigateBack(params)
			},
			selectBtn(index, name) {
				this.currentName = name;
				this.currentIndex = index;
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: #FBFCFF;
		color: #150E33;
	}

	.sure_btn {
		text-align: center;
		width: 88%;
		height: 90rpx;
		line-height: 90rpx;
		color: #fff;
		background: #4396F7;
		margin-top: 100rpx;
		border-radius: 49px;
	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			padding: 32rpx 24rpx;
			background-color: #fff;
			border-radius: 16rpx;
			margin-top: 32rpx;
			display: flex;

			.main-item-r {
				margin-left: 24rpx;

				.main-item-r-hd {
					font-size: 32rpx;
				}

				.main-item-r-bd {
					color: #7A7C94;
					margin-top: 24rpx;
				}
			}
		}
	}
</style>

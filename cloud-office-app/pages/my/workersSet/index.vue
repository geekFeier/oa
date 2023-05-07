<template>
	<view class="">
		<u-navbar :is-back="true" title="人员设置" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

			<u-icon name="plus" color="#4396F7" slot="right" size="38" style="margin-right: 36rpx;color: #4396F7;"
				@click="addBtn">
			</u-icon>
		</u-navbar>
		<view style="padding: 0 32rpx;">
			<view class="headerBox">
				<view class="headerBox-l">
					<image :src="selfData.avatar"
						style="width: 120rpx;height: 120rpx;border-radius: 50%;" mode=""></image>
					<view class="headerBox-l-info">
						<view class="headerBox-l-info-name">
							{{selfData.username}}
						</view>
						<view class="headerBox-l-info-status">
							当前管理员
						</view>
					</view>
				</view>
				<view class="headerBox-r" @click="transferAuthBtn">
					管理员移交
				</view>
			</view>
		</view>
		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" :key="index" @click="goEdit(item)">
				<view class="main-item-l">
					<view class="main-item-l-name">
						{{item.username}}
					</view>
					<view class="main-item-l-num">
						{{item.mobile}}
					</view>
				</view>
				<view v-if="item.jobs" class="main-item-r" >
					<text>{{item.jobs.job.name}}</text>
					<u-icon name="arrow-right" style="margin-left: 16rpx;" color="#999AAD"></u-icon>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				selfData:{},
				listData:[],
				page: 1,
				limit: 10,
				background: {
					backgroundColor: "#FFFFFF",
				}
			};
		},
		onLoad() {
			this.getListData();
		},
		methods: {
			transferAuthBtn(){
				uni.navigateTo({
					url:"/pages/my/workersSet/transferAuth"
				})
			},
			getListData(){
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
				}
				this.$http("enterprise.Staff/index",params,"get").then(res=>{
					console.log(res,"aAAAAAAAAAA");
					if(res.data.code==1){
						this.listData = res.data.data.rows;
						this.selfData = res.data.data.self
					}
				})
			},
			goEdit(item){
				this.$navigateTo({
					url:"/pages/my/workersSet/addPage?flag=2&data="+JSON.stringify(item)
				}).then(res=>{
					this.getListData();
				})
			},
			addBtn() {
				this.$navigateTo({
					url: "/pages/my/workersSet/addPage?flag=1"
				}).then(res=>{
					this.getListData();
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: #FBFCFF;
		color: #150E33;
	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			margin-top: 24rpx;
			background: #fff;
			border-radius: 16rpx;
			display: flex;
			padding: 32rpx 24rpx;
			align-items: center;
			justify-content: space-between;

			.main-item-l {
				.main-item-l-name {
					font-size: 30rpx;
				}

				.main-item-l-num {
					color: #7A7C94;
					font-size: 26rpx;
					margin-top: 24rpx;
				}
			}

			.main-item-r {
				font-size: 32rpx;
			}
		}
	}

	.headerBox {
		margin-top: 32rpx;
		padding: 32rpx 24rpx;
		background: #fff;
		border-radius: 16rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;

		.headerBox-l {
			display: flex;
			align-items: center;

			.headerBox-l-info {

				margin-left: 24rpx;
			}

			.headerBox-l-info-name {
				font-size: 40rpx;
			}

			.headerBox-l-info-status {
				color: #4396F7;
				font-size: 28rpx;
				margin-top: 16rpx;
			}
		}

		.headerBox-r {
			width: 150rpx;
			height: 48rpx;
			background: #4396F7;
			border-radius: 20rpx;
			color: #fff;
			font-size: 24rpx;
			line-height: 48rpx;
			text-align: center;
		}
	}
</style>

<template>
	<view>
		<u-navbar :is-back="true" title="科目选择" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" @click="selectBtn(item)" :key="index">
				<view class="main-item-l">
					{{item.serial}}
				</view>
				<view class="main-item-r">
					{{item.name}}
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
				barStyle: {
					textAlign: "center",
				},
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		watch: {
		},
		onLoad() {
			this.getCate();
		},
		methods: {
			selectBtn(item) {
				this.$navigateBack(item)
			},
			getCate(){
				this.$http("enterprise.report/CourseList", {}, "post").then(res => {
					console.log(res)
					if (res.data.code == 1) {
						this.listData = res.data.data
						console.log(this.listData,"LLLKKK")
					}
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
	}

	.addBtn {
		position: fixed;
		bottom: 158rpx;
		right: 32rpx;
		width: 110rpx;
		height: 110rpx;
	}

	.headerBox {
		height: 102rpx;
		background: #FBFCFF;
		line-height: 102rpx;


	}

	.mainBox {
		padding: 0 32rpx;
		margin-top: 8rpx;

		.main-item {
			display: flex;
			justify-content: space-between;
			border-bottom: 1px solid #EEF2FF;
			padding: 32rpx 0;
			font-size: 32rpx;
		}
	}
</style>

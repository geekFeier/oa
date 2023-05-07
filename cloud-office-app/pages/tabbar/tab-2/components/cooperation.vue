<template>
	<view class="statementBox">
		<view class="statementMain">
			<view class="common-header">
				<view class="common-title">
					协作
				</view>
			</view>
			<view class="con">
				<u-grid :col="4" :border="false">
					<u-grid-item @click="goBacklog">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../../static/image/tab2/dbsx.png" mode="">
						</image>
						<view class="grid-text">待办事项</view>
					</u-grid-item>
					<u-grid-item @click="goFile">
						<image class="grid-icon" style="width: 50rpx;height: 54rpx;"
							src="../../../../static/image/tab2/wj.png" mode="">
						</image>
						<view class="grid-text">文档/文件</view>
					</u-grid-item>
					<u-grid-item @click="goAddTs" >
						<image class="grid-icon" style="width: 64rpx;height: 54rpx;"
							src="../../../../static/image/tab2/ts.png" mode="">
						</image>
						<view class="grid-text">推送</view>
					</u-grid-item>
					<u-grid-item>
						<image class="grid-icon" style="width: 64rpx;height: 54rpx;"
							src="../../../../static/image/tab2/fqhy.png" mode="">
						</image>
						<view class="grid-text">发起会议</view>
					</u-grid-item>
					<u-grid-item @click="goGonggao">
						<image class="grid-icon" style="width: 58rpx;height: 54rpx;"
							src="../../../../static/image/tab2/gg.png" mode="">
						</image>
						<view class="grid-text">单位公告</view>
					</u-grid-item>
				</u-grid>

			</view>


		</view>
	</view>
</template>

<script>
	import {mapState} from "vuex"
	export default {
		data() {
			return {
				isShowPopop: false
			}
		},
		computed: {
			...mapState({
				personType: state => state.user.personType,
			})
		},
		methods: {
			goFile(){
				uni.navigateTo({
					url:"/pages/cooperation/file/index"
				})
			},
			goGonggao(){
				uni.navigateTo({
					url:"/pages/my/gonggao/index"
				})
			},
			goBacklog(){
				uni.navigateTo({
					url:"/pages/workbench/generationOffice/index"
				})
			},
			goAddTs(){
				uni.navigateTo({
					url:"/pages/my/pushSetting/index"
				})
			},
			openPopup() {
				this.isShowPopop = true;
				this.$emit("changeFinancialPopup", true)
			},
			goVoucher() {
				uni.navigateTo({
					url: "/pages/workbench/voucher/list"
				})
			},
			goAddVoucher() {
				uni.navigateTo({
					url: "/pages/workbench/voucher/addPage?flag=1"
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.common-header {
		display: flex;
		justify-content: space-between;
	}

	.common-title {
		font-size: 34rpx;
		color: #150E33;
		font-weight: bold;
	}

	.con {
		margin-top: 30rpx;
	}

	.statementBox {
		padding: 0 32rpx;
		margin-top: 20rpx;

		.statementMain {
			padding: 32rpx 24rpx;
			background: #fff;
				// background-color: pink;
			box-sizing: border-box;
			border-radius: 24rpx;

			.grid-text {
				color: #150E33;
				font-size: 26rpx;
				margin-top: 22rpx;
				width: 130rpx;
				text-align: center;
			}
		}
	}
</style>

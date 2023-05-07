<template>
	<view class="statementBox">
		<view class="statementMain">
			<view class="common-header">
				<view class="common-title">
					初始化设置
				</view>
			</view>
			<u-grid :col="4" :border="false" style="margin-top: 12rpx;">
				<u-grid-item  v-if="personType==1" @click="goPostMgt">
					<image class="grid-icon" style="width: 60rpx;height: 54rpx;"
						src="../../../../static/image/tab2/qtsq.png" mode="">
					</image>
					<view class="grid-text">岗位管理</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('accountSet')">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
						src="../../../../static/image/my/setting/ztsz.png" mode="">
					</image>
					<view class="grid-text">账套设置</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('workersSet')" v-if="personType==1">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
						src="../../../../static/image/my/setting/rysz.png" mode="">
					</image>
					<view class="grid-text">人员设置</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('approvalProcess')" v-if="personType==1 || personType==2">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
						src="../../../../static/image/my/setting/splc.png" mode="">
					</image>
					<view class="grid-text">审批流程</view>
				</u-grid-item>
	
				<u-grid-item @click="jumpTo('subject')">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
						src="../../../../static/image/my/setting/kmsz.png" mode="">
					</image>
					<view class="grid-text">科目设置</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('beginningPeriod')">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
						src="../../../../static/image/my/setting/qcsz.png" mode="">
					</image>
					<view class="grid-text">期初设置</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('assistAccounting')">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
						src="../../../../static/image/my/setting/fzhs.png" mode="">
					</image>
					<view class="grid-text">辅助核算</view>
				</u-grid-item>
				<u-grid-item @click="jumpTo('slideshow')">
					<image class="grid-icon" style="width: 54rpx;height: 54rpx;"
						src="../../../../static/image/my/setting/lbt.png" mode="">
					</image>
					<view class="grid-text">轮播图设置</view>
				</u-grid-item>
			</u-grid>

		</view>
	</view>
</template>

<script>
	import {mapState} from "vuex";
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
			goPostMgt(){
				uni.navigateTo({
					url:"/pages/my/postMgt/index"
				})
			},
			jumpTo(val) {
				uni.navigateTo({
					url: `/pages/my/${val}/index`
				})
			},
			openPopup() {
				this.isShowPopop = true;
				this.$emit("changeFinancialPopup",true)
			},
			goVoucher(){
				uni.navigateTo({
					url:"/pages/workbench/voucher/list"
				})
			},
			goAddVoucher(){
				uni.navigateTo({
					url:"/pages/workbench/voucher/addPage?flag=1"
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

	.statementBox {
		padding: 0 32rpx;
		margin-top: 20rpx;

		.statementMain {
			padding: 32rpx 24rpx;
			background: #fff;
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

<template>
	<view class="memoBox">
		<view class="memoTitle">
			便签
		</view>
		<view class="memoMain">
			<view class="memoCon">
				今天想记录什么
			</view>
			<view class="btnDiv" @click="goAddPage">
				创建便签
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data() {
			return {
			}
		},
		methods:{
			goAddPage(){
				uni.navigateTo({
					url:"/pages/workbench/memo/addPage"
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.memoBox{
		padding: 32rpx;
		background: #fff;
		box-sizing: border-box;
		overflow: hidden;
		border-radius: 24rpx;
		.memoTitle{
			color: #7A7C94;
			font-size: 32rpx;
		}
		.memoMain{
			margin-top: 40rpx;
			display: flex;
			flex-direction: column;
			align-items: center;
			.memoCon{
				font-size: 42rpx;
				color: #B5BFDA;
			}
			.btnDiv{
				color: #4396F7;
				font-size: 30rpx;
				margin-top: 20rpx;
			}
		}
	}
	
</style>

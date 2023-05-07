<template>
	<view>
		<u-navbar :is-back="true" title="公告详情" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<view class="user-box">
				<view class="user-box-l">
					<image class="user-box-l-img" src="../../../static/image/launch_icon.png" mode=""></image>
					<view class="user-box-l-text">
						发起人：<text style="color: #150E33;">总经理</text>
					</view>
				</view>
				<view class="user-box-r">
					2021-08-12 14:21
				</view>
			</view>
			
			<view class="content-main">
				<view class="content-title">
					公告内容全员可见公告内容全员可见
				</view>
				<view class="content">
					公告内容公告内容公告内容公告内容公告内容公告内容 公告内容公告内容公告内容
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		}
	}
</script>

<style lang="scss" scoped>
.mainBox{
	padding: 0 32rpx;
	.user-box{
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 32rpx;
		.user-box-l{
			display: flex;
			color: #7A7C94;
			font-size: 30rpx;
			align-items: center;
			.user-box-l-text{
				margin-left: 24rpx;
			}
			.user-box-l-img{
				width: 100rpx;
				height: 100rpx;
			}
		}
		.user-box-r{
			color: #B5BFDA;
			font-size: 26rpx;
		}
	}

	.content-main{
		.content-title{
			color: #150E33;
			font-size: 32rpx;
			margin: 32rpx 0;
		}
		.content{
			font-size: 30rpx;
			color: #7A7C94;
		}
	}
}
</style>

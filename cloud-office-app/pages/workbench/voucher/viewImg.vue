<template>
	<view>
		<u-navbar :is-back="true" title="附件内容" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="mainBox">
			<image v-for="(item,index) in imageData" :key="index" @click="previewImg" :src="item"
				style="width: 180rpx;height: 180rpx;margin-bottom: 12rpx;margin-right: 24rpx;" mode=""></image>
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
				imageData: []
			};
		},
		onLoad(e) {
			this.imageData = JSON.parse(e.imgData)
		},
		methods: {
			previewImg() {
				uni.previewImage({
					urls: this.imageData
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.mainBox {
		padding: 32rpx;
		display: flex;
		flex-wrap: wrap;
		margin-right: -24rpx;
	}
</style>

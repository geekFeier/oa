<template>
	<view class="launch">
		<view class="lunch_hd">
			<image src="../static/image/startStart2.png" style="width:100%;height:100%;position: absolute;" mode=""></image>
			<view class="down_div" @click="goApp">
				{{codeTime}}s跳过
			</view>
		</view>
		<view class="lunch_bd">
			<image src="../static/image/launch_icon.png" mode="" class="lunch_bd_icon"></image>
			<view class="lunch_bd_title">
				云上办公APP
			</view>
		</view>
	</view>
</template>

<script>
	var timer;
	export default {
		data() {
			return {
				codeTime: 3
			}
		},
		onLoad() {
			this.downTime();
			uni.getLocation({
				geocode: true,
				success: (res) => {
				},
				fail: (err) => {
					
				}
			})
		},
		methods: {
			goApp() {
				clearTimeout(timer)
				this.codeTime = 0
				uni.reLaunch({
					url: "/pages/tabbar/tab-1/index"
				})
			},
			downTime() {
				if (this.codeTime == 0) {
				
					uni.reLaunch({
							url: "/pages/tabbar/tab-1/index"
					})
				} else {
					this.codeTime--;
					timer = setTimeout(() => {
						this.downTime();
					}, 1000);
				}
			},
		}
	}
</script>

<style scoped lang="scss">
	.launch {
		height: 100vh;
		.lunch_hd {
			position: relative;
			height: 75vh;
			.down_div {
				position: absolute;
				top: 40px;
				right: 16px;
				padding: 8rpx 30rpx;
				background: rgba(255, 255, 255, .3);
				// opacity: 0.3;
				border-radius: 27px;
				color: #fff;
				// font-weight: bold;
			}
		}

		.lunch_bd {
			display: flex;
			// justify-content: center;
			align-items: center;
			flex-direction: column;

			// position: relative;
			.lunch_bd_icon {
				width: 145rpx;
				height: 145rpx;
				margin-top: 50rpx;
				margin-bottom: 10px;
				// position: absolute;
			}

			.lunch_bd_title {
				color: #150E33;
				font-size: 37rpx;
				font-weight: 500;
			}
		}
	}
</style>

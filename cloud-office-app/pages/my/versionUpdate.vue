<template>
	<view class="versionBox">
		<u-navbar :is-back="true" title="版本更新" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="hd">
			<image src="../../static/image/launch_icon.png" style="width: 180rpx;height: 180rpx;" mode=""></image>
			<text style="margin-top: 32rpx;color: #150E33;font-size: 24rpx;">当前版本{{version}}</text>
		</view>

		<view class="main" v-if="version<detailData.version">
			<view class="" style="margin-top: 56rpx;">
				检测到有新的版本可更新:<text>V{{detailData.version}}</text>
			</view>
			<view style="margin-top: 32rpx;">
				<view class="">
					{{detailData.descript}}
				</view>
			</view>

		</view>
		<view class="main" v-else>
			<view class="" style="margin-top: 56rpx;">
				当前是最新版本的APP
			</view>


		</view>
		<view class="bd" @click="updateBtn">
			更新云上办公APP
		</view>

	</view>
</template>

<script>
	export default {
		onLoad() {
			this.getVesionInfo()
			let vm = this
			// #ifdef APP-PLUS
			plus.runtime.getProperty(plus.runtime.appid, function(wgtinfo) {
				vm.version = wgtinfo.version
				console.log(this.version)
			})
			// #endif

		},
		data() {
			return {
				detailData: {},
				image: "",
				version: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},

		methods: {
			getVesionInfo() {

				this.$http('/common/getVersionInfo', 'get').then(res => {
					console.log(res)
					this.detailData = res.data.data
					console.log(this.version, this.detailData.version)
				})
			},
			updateBtn() {
				// location.href = this.detailData.app_link
				let vm = this
				if (this.version < this.detailData.version) {
					uni.setClipboardData({
						data:this.detailData.version_url,
						success:function(){
							uni.showToast({
								title:'安装链接已复制,请在浏览器打开下载',
								icon:"none"
							})
						}
					})
					// uni.downloadFile({
					// 		url: vm.detailData.version_url,//下载地址接口返回
					// 		success: (data) => {
					// 			uni.showToast({
					// 				title:'安装包下载完成，即将安装',
									
					// 			})
					// 			plus.runtime.install(data.tempFilePath)
					// 		},
					// 		fail: (err) => {
					// 			console.log(err);
					// 			uni.showToast({
					// 				icon: 'none',
					// 				mask: true,
					// 				title: '失败请重新下载',
					// 			});
					// 		},
					// 	});
					

				} else {
					uni.showToast({
						title: '当前是最新版本APP,无需更新',
						icon: "none"
					})
				}

			}
		}
	}
</script>

<style scoped lang="scss">
	.versionBox {

		// padding: 32rpx;
		.hd {
			display: flex;
			flex-direction: column;
			align-items: center;
			margin-top: 48rpx;
		}

		.main {
			padding: 32rpx;
		}

		.bd {
			width: 100%;
			height: 98rpx;
			line-height: 98rpx;
			text-align: center;
			position: fixed;
			bottom: 0;
			background: #4396F7;
			font-size: 32rpx;
			color: #fff;
		}
	}
</style>

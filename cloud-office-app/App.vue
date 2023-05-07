<script>
	export default {
		onLaunch: function() {
			console.log('App Launch');
			// uni.connectSocket({
			//   url: 'ws://cloud.yunzanet.com:8083'
			// });
			// uni.onSocketOpen(function (res) {
			//   console.log('WebSocket连接已打开！');
			// });
			// uni.onSocketError(function (res) {
			//   console.log('WebSocket连接打开失败，请检查！');
			// });
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	/*每个页面公共css */
	@import "colorui/main.css";
	@import "colorui/icon.css";
	/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
	@import "uview-ui/index.scss";
	html {
		overflow-y: initial;
	}
	
	page,
	.container {
		// height: initial;
		// overflow-y: initial;
		// min-height: 100vh;
		background-color: #fff;
	}
</style>

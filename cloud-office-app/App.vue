<script>
	export default {
		onLaunch() {
		 
		},
		onShow() {
			 
		},
		onHide() {
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

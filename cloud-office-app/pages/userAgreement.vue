<template>
	<view class="mainBox">
		<rich-text :nodes="reg_policy"></rich-text>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				reg_policy: ""
			};
		},
		onLoad() {
			this.getContent();
		},
		methods: {

			getContent() {
				this.$http("user/agreement", {}, "get").then(res => {
					if (res.data.code == 1) {
						this.reg_policy = res.data.data.user_agreement;
						console.log(res, "sadDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.mainBox {
		padding: 32rpx;
	}
</style>

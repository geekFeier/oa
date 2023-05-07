<template>
	<view>
		<u-navbar :is-back="true" title="辅助核算明细账" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>

		<view class="mainBox">
			<view class="main-item" v-for="item in listData" :key="item.id" @click="goList(item)">
				<text class="main-item-title">{{item.name}}</text>
				<u-icon name="arrow-right" color="#A2A3B4"></u-icon>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				name: "",
				search:"",
				isShowAlert: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
				listData: [],
				h_id:""
			};
		},
		onLoad(params) {
			this.h_id = params.pid;
			this.getCate();
		},
		methods: {
			getCate() {
				let params = {
					h_id: this.h_id,
					search: this.search
				}
				this.$http("enterprise.Hesuan_balance/screening", params, "get").then(res => {
					this.listData = res.data.data;
				})
			},
			goList(item) {
				uni.navigateTo({
					url: "/pages/workbench/fuzhuDetail/list?data="+JSON.stringify(item)
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.mainBox {
		padding: 0 32rpx;

		.main-item {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 32rpx 0;
			border-bottom: 1px solid #F6F9FE;

			.main-item-title {
				font-size: 32rpx;
			}


		}
	}

	/deep/ .inputClass {
		margin: 18rpx 0;
		width: 520rpx !important;
		height: 76rpx !important;
		background: #FFFFFF;

		border: 1px solid #B5BFDA;
		display: flex;
		align-items: center;
		padding-left: 12rpx;
		border-radius: 8rpx;

		.input-placeholder {
			color: #B5BFDA !important;

		}
	}
</style>

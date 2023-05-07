<template>
	<view>
		<u-navbar :is-back="true" title="便签详情" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
			<view class="navbar-right" slot="right" style="margin-right: 20rpx;">
				<u-icon @click="del_exit()" name="trash" color="black" size="50"></u-icon>
			</view>
		</u-navbar>
		<view class="mainBox">
			<view class="header-time">
				{{detailData.createtime}}
			</view>
			<view class="header-con">
				<view class="header-con-main">
					{{detailData.content}}
				</view>
				<image v-for="(item,index) in detailData.images" style="margin-top: 16rpx;width: 100%;" :src="item" mode="aspectFill"></image>
			</view>

		</view>

	</view>
</template>

<script>
	import dayjs from "@/utils/dayjs.min.js"
	export default {
		data() {
			return {
				detailData:{},
				currentId:"",
				value: "",
				date: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
				imageData: []
			};
		},
		onLoad(e) {
			this.date = dayjs().format("YYYY-MM-DD HH:mm:ss");
			this.currentId = e.id;
			this.detailData = JSON.parse(e.data)
		},
		methods: {
			//删除便签
			del_exit(){
				let params = {
					id:this.currentId
				}
				this.$http("enterprise.Stikcy_note/delete", params, "get").then(res => {
					if(res.data.code == 1){
						uni.showToast({
							title: "删除成功",
							icon: "none"
						})
						setTimeout(() => {
							uni.navigateBack({
								delta:1
							})
						}, 1000)
						
					}
				})
			},
		}
	}
</script>

<style scoped lang="scss">
	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		position: fixed;
		bottom: 32rpx;
		left: 50%;
		transform: translateX(-50%);
	}

	.mainBox {
		padding: 32rpx;

		.header-time {
			color: #7A7C94;
			font-size: 28rpx;
		}

		.header-con {
			margin-top: 30rpx;

			.header-con-main {
				font-size: 30rpx;
				color: #150E33;
			}

			/deep/ .u-input__textarea {
				// background: pink;
				// background: #FBFCFF;
				border-radius: 8px;
				// padding: 8px;
				font-size: 30rpx;
			}

			/deep/ .uni-textarea-textarea {
				// min-height: 100px;
			}

		}
	}
</style>

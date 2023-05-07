<template>
	<view>
		<u-popup :mask-close-able="false" v-model="isShowPopup" mode="bottom" border-radius="56">
			<view class="popup-main">
				<view class="popup-common-title">
					— {{title}} —
				</view>

				<picker-view class="picker-view" style="height: 380rpx;" :value="digestValue" @change="changePicker">
					<picker-view-column  >
						<view class="item" v-for="(item,index) in popupList" :key="index">{{item.name}}</view>
					</picker-view-column>
				</picker-view>

				<view class="popup-common-btnGroup">
					<view class="popup-common-btn" @click="$emit('update:isShowPopup', false)">
						取消
					</view>
					<view class="popup-common-btn active" @click="sureBtn">
						确定
					</view>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		name: "dell-popup",
		props: {
			"popupList": {
				default: () => [],
				type: Array
			},
			"isShowPopup": {
				default: false,
				type: Boolean
			},
			"label": {
				default: "name",
				type: String,
			},
			"title":{
				default:"选择",
				type:String
			}
		},
		onLoad() {

		},
		data() {
			return {
				digestValue: [0],
			};
		},
		methods: {
			changePicker(e){
				this.digestValue = e.detail.value;
			},
			sureBtn() {
				uni.$emit('choosePath',this.popupList[this.digestValue.join()])
				this.$emit("input", this.popupList[this.digestValue.join()].name);
				this.$emit("update:itemId", this.popupList[this.digestValue.join()].id);
				this.$emit('update:isShowPopup', false)
				console.log(this.popupList[this.digestValue.join()], "= popup 抛出 =");
			}
		}
	}
</script>

<style scoped lang="scss">
	.picker-view {
		.item {
			font-size: 34rpx;
			text-align: center;
		}
	}


	.popup-main {

		.popup-common-title {
			font-size: 36rpx;
			color: #150E33;
			text-align: center;
			margin-top: 56rpx;
		}

		.active {
			background: #4396F7 !important;
			color: #FFFFFF !important;
		}

		.popup-common-btnGroup {
			display: flex;
			padding: 0 50rpx;
			justify-content: space-between;
			font-size: 30rpx;
			margin-top: 50rpx;
			margin-bottom: 32rpx;


			.popup-common-btn {
				width: 260rpx;
				height: 80rpx;
				background: #F6F9FE;
				border-radius: 12rpx;
				color: #7A7C94;
				text-align: center;
				line-height: 80rpx;
			}
		}


	}
</style>

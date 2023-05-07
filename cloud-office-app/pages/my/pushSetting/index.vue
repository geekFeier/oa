<template>
	<view>
		<u-navbar :is-back="true" title="推送设置" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

			<u-icon v-if="personType==1 || personType==2" name="plus" color="#4396F7" slot="right" size="38" style="margin-right: 36rpx;color: #4396F7;"
				@click="addBtn">
			</u-icon>
		</u-navbar>

		<view class="mainBox">
			<view class="main-item" @click="goDetail(item)" v-for="(item,index) in listData" :key="index">
				<view class="main-item-hd">
					<view class="main-item-hd-l">
						发起人：<text style="color: #150E33;">{{item.user.username}}</text>
					</view>
					<view class="main-item-hd-r">
						<text>查看详情</text>
						<u-icon name="arrow-right" color="#B5BFDA" size="34" style="margin-left: 16rpx;" />
					</view>
				</view>

				<view class="main-item-m">
					{{item.content}}
				</view>
				<view class="main-item-date">
					{{item.createtime}}
					<!-- {{filtersTime(item.createtime)}} -->
				</view>
			</view>
		</view>
	</view>
</template>

<script>
		import dayjs from "@/utils/dayjs.min.js";
		import {mapState} from "vuex";
	export default {
		data() {
			return {
				listData:[],
				page: 1,
				limit: 10,
				background: {
					backgroundColor: "#FFFFFF",
				}
			};
		},
		
		onLoad() {
			this.getListData();
		},
		onReachBottom() {
			this.page++;
			this.getListData()
		},
		computed: {
			...mapState({
				personType: state => state.user.personType,
			})
		},
		methods: {
			filtersTime(val){
			  return dayjs(val).format("YYYY-MM-DD HH:mm")
			},
			getListData() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
					// sort:"id",
					// order:"desc"
				}
				this.$http("enterprise.Smspush/index", params, "get").then(res => {
					if(res.data.code==1){
					this.listData = this.listData.concat(res.data.data.rows);
					}
				})
			},
			goDetail(data) {
				uni.navigateTo({
					url: "/pages/my/pushSetting/detail?data="+encodeURIComponent(JSON.stringify(data))
				})
			},
			addBtn() {
				this.$navigateTo({
					url: "/pages/my/pushSetting/addPage"
				}).then(res=>{
					this.page=1;
					this.listData = [];
					this.getListData()
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: #FBFCFF;
		color: #150E33;
	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			border-radius: 20rpx;
			background-color: #fff;
			padding: 0 32rpx;
			padding-bottom: 32rpx;
			margin-top: 32rpx;

			.main-item-hd {
				font-size: 28rpx;
				display: flex;
				padding: 32rpx 0;
				border-bottom: 1px solid #EEF2FF;
				align-items: center;
				justify-content: space-between;

				.main-item-hd-l {
					color: #7A7C94;
				}

				.main-item-hd-r {
					display: flex;
					align-items: center;
					color: #B5BFDA;

				}
			}

			.main-item-m {
				font-size: 32rpx;
				margin-top: 32rpx;
			}

			.main-item-date {
				font-size: 28rpx;
				color: #B5BFDA;
				margin-top: 50rpx;
			}
		}
	}
</style>

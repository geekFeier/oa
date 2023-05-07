<template>
	<view>
		<u-navbar :is-back="true" title="查看凭证" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索凭证" bg-color="#fff"
					@change="changeSearch" v-model="keyword" :height="65" @search="searchEvent" :show-action="false">
				</u-search>
			</view>
		</view>

		<view class="mainBox">
			<view class="main-item" v-for="item in listData" :key="item.id" @click="goDetail(item.id)">
				<view class="item-hd">
					<view class="item-hd-l">
						{{item.abstract}}
					</view>
					<u-icon class="item-hd-r" name="arrow-right" color="#7A7C94" size="28"></u-icon>
				</view>
				<view class="item-bd">
					<view class="item-bd-l">
						{{item.createtime}}
					</view>
					<view class="item-bd-r">
						<!-- 100 -->
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				listData: [],
				page: 1,
				limit: 10,
				keyword: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad() {
			this.getListData();
			uni.$on("changeVocherList", () => {
				this.page = 1;
				this.listData = [];
				this.getListData();
			})
		},
		onReachBottom() {
			this.page++;
			this.getListData();
		},
		watch: {
			// keyword(val){
			// 	if(val==""){
			// 		console.log("这批这阿松大");
			// 		this.page = 1;
			// 		this.listData = [];
			// 		this.getListData();
			// 	}
			// }
		},
		methods: {
			searchEvent() {
				this.page = 1;
				this.listData = [];
				this.getListData();
			},
			changeSearch(e) {
				if (e == "") {
					this.page = 1;
					this.listData = [];
					this.getListData();
				}
			},
			clearBtn() {
				this.keyword = ""
			},
			getListData() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
					search: this.keyword
				}
				this.$http("enterprise.Credentials/index", params, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = this.listData.concat(res.data.data.rows);
					}
				})
			},
			goDetail(id) {
				this.$navigateTo({
					url: "/pages/workbench/voucher/detail?id=" + id
				}).then(res => {
					this.page = 1;
					this.listData = [];
					this.getListData();
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.headerBox {
		height: 102rpx;
		background: #FBFCFF;
		line-height: 102rpx;


	}

	.mainBox {
		padding: 0 32rpx;

		.main-item {
			// height: 143rpx;
			padding: 32rpx 0;
			border-bottom: 1px solid #EEF2FF;

			.item-bd {
				font-size: 28rpx;
				margin-top: 24rpx;

				.item-bd-l {
					color: #B5BFDA;
				}
			}

			.item-hd-l {
				color: #150E33;
				font-size: 32rpx;
			}

			.item-hd,
			.item-bd {
				display: flex;
				justify-content: space-between;
			}
		}
	}
</style>

<template>
	<view>
		<u-navbar :is-back="true" title="科目选择" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<!-- 	<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索编码或关键字查找" bg-color="#fff"
					v-model="keyword" :height="65" @custom="searchEvent"  :show-action="false" @clear="clearBtn">
				</u-search> -->
				<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索编码或关键字查找" bg-color="#fff"
					v-model="keyword" :height="65" @custom="searchEvent" @clear="clearBtn"></u-search>
			</view>
		</view>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="30" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" inactive-color="#7A7C94" :current="swiperCurrent"
			@change="tabsChange" :list="tabList" :is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>
		<view class="mainBox">
		<!-- 	<view class="main-item" v-for="(item,index) in listData" @click="selectBtn(item)" :key="index">
				<view class="main-item-l">
					{{item.serial}}
				</view>
				<view class="main-item-r">
					{{item.name}}
				</view>
			</view> -->
			<view v-for="(item,index) in listData" :key="index">
				<selectItem :item="item" :flag="2"></selectItem>
			</view>
		</view>

	</view>
</template>

<script>
		import selectItem from "./select-item.vue"
	export default {
		data() {
			return {
				listData: [],
				category_id: "",
				page: 1,
				tabList: [

				],
				barStyle: {
					textAlign: "center",
				},
				swiperCurrent: 0,
				keyword: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
				limit: 50,
				page: {
					currentPage: 1,
					currentSize: 99999
				}
			};
		},
		watch: {
			keyword(val) {
				if (val == "") {
					this.getListData()
				}
			}
		},
		onLoad() {
			this.getCate();
		},
		components:{
			selectItem
		},
		methods: {
			selectBtn(item) {
				this.$navigateBack(item)
			},
			getCate() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
				}
				this.$http("enterprise.Subject/getKmeuCate", params, "post").then(res => {
					if (res.data.code == 1) {
						this.tabList = res.data.data;
						this.category_id = this.tabList[0].id || ""
						this.getListData();
					}
				})
			},
			getListData() {
				let params = {
					page: this.page.currentPage,
					limit: this.page.currentSize,
					offset: (this.page.currentPage - 1) * this.page.currentSize,
					category_id: this.category_id,
					search: this.keyword,
					sort: "serial",
					order: "asc",
					loading_type: "kemu"
				}
				this.$http("enterprise.Subject/getIndex", params, "get").then(res => {
					console.log(res, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
					if (res.data.code == 1) {

						this.listData = res.data.data.rows;
					}
				})
			},

			searchEvent() {
				this.page.currentPage = 1;
				this.getListData();
			},
			clearBtn() {
				this.getListData();
			},
			tabsChange(index) {
				this.swiperCurrent = index;
				this.category_id = this.tabList[index].id;
				this.page.currentPage = 1;
				this.getListData()
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		color: #150E33;
	}

	.addBtn {
		position: fixed;
		bottom: 158rpx;
		right: 32rpx;
		width: 110rpx;
		height: 110rpx;
	}

	.headerBox {
		height: 102rpx;
		background: #FBFCFF;
		line-height: 102rpx;


	}

	.mainBox {
		padding: 0 32rpx;
		margin-top: 8rpx;

		.main-item {
			display: flex;
			justify-content: space-between;
			border-bottom: 1px solid #EEF2FF;
			padding: 32rpx 0;
			font-size: 32rpx;
		}
	}
</style>

<template>
	<view>
		<u-navbar :is-back="true" title="明细账" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索编码或关键字查找" bg-color="#fff"
					v-model="keyword" :show-action="false" :height="65" @search="searchEvent()" @clear="clearBtn"></u-search>
			</view>
		</view>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="30" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" inactive-color="#7A7C94" :current="swiperCurrent"
			@change="tabsChange" :list="tabList" :is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>
		<view class="mainBox">
			<view v-for="(item,index) in listData" :key="index">
			<view class="main-item" @click="selectBtn(item.id)">
				<view class="main-item-l">
					{{item.serial}}
				</view>
				<view class="main-item-r">
					{{item.name}}
				</view>
			</view>
			</view>
		</view>
	</view>
</template>

<script>
	// import selectItem from "./select-item.vue"
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
		components:{
			// selectItem
		},
		watch:{
			keyword(val){
				if(val==""){
					this.getListData()
				}
			}
		},
		onLoad() {
			this.getCate();
		},
		methods: {
			selectBtn(id){
				uni.navigateTo({
					url:"/pages/workbench/detailAccount/list?id="+id
				})
			},
			getCate() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
				}
				this.$http("enterprise.Subject/getKmeuCate", params, "post").then(res => {
			if(res.data.code==1){
				this.tabList = res.data.data;
				this.category_id = this.tabList[0].id || ""
				this.getListData();
			}
				})
			},
			getListData() {
				let params = {
					// page: this.page.currentPage,
					// limit: this.page.currentSize,
					// offset: (this.page.currentPage - 1) * this.page.currentSize,
					cid: this.category_id,
					search: this.keyword,
					// sort: "serial",
					// order: "asc",
					// loading_type: "kemu"
				}
				this.$http("enterprise.subject_balance/existing_subject", params, "get").then(res => {
					if(res.data.code==1){
						
					this.listData = res.data.data;
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

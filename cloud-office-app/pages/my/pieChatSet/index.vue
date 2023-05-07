<template>
	<view>
		<u-navbar :is-back="true" title="饼状图设置" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索编码或关键字查找" bg-color="#fff"
					v-model="keyword" :show-action="false" :height="65" @search="searchEvent()" @clear="clearBtn">
				</u-search>
			</view>
		</view>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="30" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" inactive-color="#7A7C94" :current="swiperCurrent"
			@change="tabsChange" :list="tabList" :is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>
		<view class="mainBox">
			<view v-for="(item,index) in listData" :key="index">
				<view class="main-item">
					<view class="main-item-l">
						<!-- <checkbox v-model="item.checked"  :checked="item.checked" @change="checkBoxBtn(index)" /> -->
						<!-- <checkbox value="cb" checked="true" /> -->
						<u-checkbox v-model="item.checked" @change="checkBoxBtn(index)">

						</u-checkbox>
					</view>
					<view class="main-item-r">
						{{item.serial}} {{item.name}}
					</view>
				</view>
			</view>
		</view>
		
		<button type="default" class="sure_btn" @click="sureBtn">确定</button>
		
	</view>
</template>

<script>
	// import selectItem from "./select-item.vue"
	export default {
		data() {
			return {
				listData: [{
					serial: 101,
					name: "测试"
				}],
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
		components: {
			// selectItem
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
		methods: {
			sureBtn(){
				let checkedList = this.listData.filter(item=>{
					return item.checked
				}).map(item=>item.id).join(",");
				console.log(checkedList,"aaaaaaaaaaaaaaaaaaaaaaaaa");
			},
			checkBoxBtn(index) {
				this.listData[index].checked = !this.listData[index].checked;
				this.$forceUpdate()
			},
			selectBtn(id) {
				uni.navigateTo({
					url: "/pages/workbench/detailAccount/list?id=" + id
				})
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
					if (res.data.code == 1) {

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


	.headerBox {
		height: 102rpx;
		background: #FBFCFF;
		line-height: 102rpx;


	}

	.mainBox {
		padding: 0 32rpx;
		margin-top: 8rpx;
		height: 70vh;
		.main-item {
			display: flex;
			justify-content: space-between;
			border-bottom: 1px solid #EEF2FF;
			padding: 32rpx 0;
			font-size: 32rpx;
		}
	}
</style>

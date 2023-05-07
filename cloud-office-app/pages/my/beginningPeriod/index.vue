<template>
	<view>
		<u-navbar :is-back="true" title="期初设置" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索编码或关键字查找" bg-color="#fff"
					v-model="keyword" :height="65" @search="searchEvent" :show-action="false" @clear="clearBtn">
				</u-search>
			</view>
		</view>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="30" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" inactive-color="#7A7C94" :current="swiperCurrent"
			@change="tabsChange" :list="tabList" :is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>

		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in listData" :key="index">
				<view class="main-item-l" @click="editBtn(item.id,item)">
					{{item.serial}} {{item.name}}
				</view>
				<view class="main-item-r">
					<view class="main-item-r-status"
						:style="{background:item.balance_status == -1 ? '#12D592' : '#FF253D'}">
						{{item.balance_status == -1 ? "借" : "贷"}}
					</view>
					<view class="main-item-r-info">
						<view class="main-item-r-info-num">
							年初：{{item.years_balance}}
						</view>
						<view class="main-item-r-info-num">
							借方：{{item.debit_balance}}
						</view>
						<view class="main-item-r-info-num">
							贷方：{{item.creditor_balance}}
						</view>
						<view class="main-item-r-info-num">
							期初：{{item.initial_balance}}
						</view>

					</view>
				</view>
			</view>

		</view>
		<button type="default" class="sure_btn" @click="loginBtn">试算平衡</button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				category_id: "",
				listData: [],
				page: 1,
				limit: 50,
				currentFy: {
					currentPage: 1,
					currentSize: 99999
				},
				tabList: [],
				barStyle: {
					textAlign: "center",
				},
				swiperCurrent: 0,
				keyword: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad() {
			this.getCate();
		},
		watch: {
			keyword(val) {
				if (val === "") {
					this.currentFy.page = 1;
					this.getListData();
				}
			}
		},
		methods: {
			clearBtn() {
				this.currentFy.page = 1;
				this.getListData();
			},
			searchEvent() {
				this.currentFy.page = 1;
				this.getListData();
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
					page: this.currentFy.currentPage,
					limit: this.currentFy.currentSize,
					offset: (this.currentFy.currentPage - 1) * this.currentFy.currentSize,
					category_id: this.category_id,
					search: this.keyword,
					sort: "serial",
					order: "asc",
					loading_type: "init_set"
				}
				this.$http("enterprise.Subject/getIndex", params, "get").then(res => {
					if (res.data.code == 1) {
						this.listData = this.treeToArray(res.data.data.rows);
					}
				})
			},
			treeToArray(tree) {
				let arr = [];
				const expanded = datas => {
					if (datas && datas.length > 0) {
						datas.forEach(e => {
							arr.push(e);
							expanded(e.children);
						})
					}
				};
				expanded(tree);
				return arr;
			},
			tabsChange(index) {
				this.swiperCurrent = index;
				this.category_id = this.tabList[index].id;
				this.currentFy.currentPage = 1;
				this.getListData()
			},
			loginBtn() {
				uni.navigateTo({
					url: "/pages/my/beginningPeriod/trialBalancing"
				})
			},
			editBtn(id, item) {
				if (item.children.length || item.children.length > 0) {
					uni.showToast({
						title: "无法编辑期初的父级科目",
						icon: "none"
					})
				} else {
					this.$navigateTo({
						url: "/pages/my/beginningPeriod/editPage?id=" + id
					}).then(res => {
						this.currentFy.currentPage = 1;
						this.getListData();
					})
				}

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
		height: 69vh;
		overflow-y: auto;

		.main-item {
			display: flex;
			align-items: center;
			padding: 16rpx 32rpx;
			border-bottom: 1px solid #EEF2FF;
			padding-right: 100rpx;
			justify-content: space-between;

			.main-item-l {
				font-size: 32rpx;
				font-weight: bold;
				width: 42%;
			}

			.main-item-r {
				width: 50%;
				// margin-left: 120rpx;
				display: flex;
				align-items: center;
			}

			.main-item-r-status {
				width: 40rpx;
				height: 40rpx;
				border-radius: 50%;
				line-height: 40rpx;
				text-align: center;
				color: #fff;
			}

			.main-item-r-info {
				margin-left: 30rpx;
				font-size: 30rpx;

				.main-item-r-info-num {
					margin-bottom: 12rpx;
				}

				&::nth-last-child(1) {
					margin-bottom: 0;
				}
			}
		}
	}

	.pul {
		background-color: #915FF2;
	}

	.red {
		background-color: #FF253D;
	}

	.blue {
		background-color: #4396F7;
	}

	.orange {
		background-color: #FA7443;
	}

	.green {
		background-color: #12D592;
	}
</style>

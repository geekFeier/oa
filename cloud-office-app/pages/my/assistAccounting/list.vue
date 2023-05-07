<template>
	<view>
		<u-navbar :is-back="true" title="辅助核算" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="padding: 0px 20px" placeholder="请输入关键字" bg-color="#fff"
					@change="changeKeyword" v-model="keyword" :height="65" @search="searchBtn" :show-action="false"
					@clear="clearBtn">
				</u-search>
			</view>
		</view>

		<view class="mainBox">
			<view class="main-item" v-for="item in listData" :key="item.id" @click="editBtn(item)">
				<text class="main-item-title">{{item.name}}</text>
				<u-icon name="arrow-right" color="#A2A3B4"></u-icon>

			</view>
		</view>

		<image src="../../../static/image/tab1/add.png" class="addBtn" mode="" @click="goAddPage"></image>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				listData: [],
				currentId: "",
				keyword: "",
				flag: 1,
				isShowAlert: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad(e) {
			this.currentId = e.pid;
			this.flag = e.flag ;
				console.log(this.flag,"SDAsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasda");
			this.getListData();
		},
		methods: {
			changeKeyword(e) {
				if (e == "") {
					this.getListData();
				}
			},
			searchBtn() {
				this.getListData();
			},
			getListData() {
				let params = {
					pid: this.currentId,
					search: this.keyword
				}
				this.$http("enterprise.Accounting/list", params, "get").then(res => {
					this.listData = res.data.data.rows;
				})
			},
			editBtn(data) {
				if (this.flag == 2) {
					this.$navigateBack(data);
				} else {
					this.$navigateTo({
						url: "/pages/my/assistAccounting/addPage?flag=2&pid=" + this.currentId + "&data=" + JSON
							.stringify(data)
					}).then(res => {
						this.getListData()
					})
				}

			},
			goAddPage() {
				this.$navigateTo({
					url: "/pages/my/assistAccounting/addPage?flag=1&pid=" + this.currentId
				}).then(res => {
					this.getListData()
				})
			},
			delBtn() {},
			clearBtn() {},
			addBtn() {
				this.isShowAlert = true;
			}
		}
	}
</script>

<style lang="scss" scoped>
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
</style>

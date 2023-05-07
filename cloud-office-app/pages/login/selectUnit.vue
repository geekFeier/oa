<template>
	<view class="unitBox">
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索" bg-color="#F6F5FA"
					v-model="formData.search" :height="65" @custom="searchEvent" @clear="clearBtn"></u-search>
			</view>
		</view>

		<view class="mainBox">
			<view class="unit-item" v-for="(item,index) in listData" :key="index"
				@click="selectBtn(item.enterprise_id,item.enterprice_name)">
				<image :src="currentIndex==item.enterprise_id ? selectASrc : selectSrc " mode=""
					style="width: 40rpx;height: 40rpx;"></image>
				<text class="unit-item-name">{{item.enterprice_name}}</text>
			</view>
		</view>

		<button type="default" class="addBtn" @click="saveBtn">确认</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				listData: [],
				keyword: "",
				selectSrc: "../../static/image/login/select.png",
				selectASrc: "../../static/image/login/select-a.png",
				currentIndex: 0,
				formData: {
					page: 1,
					limit: 10,
					offset: 0,
					search: ""
				}
			};
		},
		onLoad(e) {
			this.currentIndex = e.id;
			this.currentName = e.name;
			this.getData();
		},
		watch: {
			keyword(val) {
				if (val === "") {
					this.getData();
				}
			}
		},
		methods: {
			getData() {
				this.$http("/enterprise.Enterprise/index", this.formData, "get", 1, true).then(res => {
					this.listData = res.data.data.rows;
					console.log(res, "+++++++++++++++++++++++++++++++++++++++++++");
				})
			},
			selectBtn(index, name) {
				this.currentIndex = index;
				this.currentName = name;
			},
			searchEvent() {
				this.formData.page = 1;
				this.getData();
			},
			saveBtn() {
				let params = {
					currentName: this.currentName,
					currentIndex: this.currentIndex,
				}
				this.$navigateBack(params)
			},
			clearBtn() {
				this.keyword= "";
				this.getData();
			}
		}
	}
</script>

<style lang="scss" scoped>
	.addBtn {
		text-align: center;
		position: fixed;
		bottom: 32rpx;
		width: 88%;
		left: 50%;
		transform: translateX(-50%);
		height: 90rpx;
		line-height: 90rpx;
		color: #fff;
		background: #4396F7;
		border-radius: 49px;
	}

	.headerBox {
		margin-top: 16rpx;
	}

	.mainBox {
		padding: 0 32rpx;
		box-sizing: border-box;
		margin-top: 48rpx;

		.unit-item {
			margin-bottom: 32rpx;
			display: flex;
			align-items: center;

			.unit-item-name {
				color: #150E33;
				font-size: 32rpx;
				margin-left: 16rpx;
			}
		}
	}
</style>

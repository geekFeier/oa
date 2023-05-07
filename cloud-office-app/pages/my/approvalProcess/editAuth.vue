<template>
	<view>
		<u-navbar :is-back="true" title="编辑权限" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">

		</u-navbar>
		<view class="mainBox">
			<view class="main-item" v-for="(item,index) in list" :key="index" @click="selectBtn(index)">
				<image :src="item.checked ? selecta : select" style="width: 40rpx;height: 40rpx;margin-right: 24rpx;"
					mode="">
				</image>
				<text style="font-size: 32rpx;">{{item.title}}</text>
			</view>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">保存并提交</button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [],
				select: "../../../static/image/login/select.png",
				selecta: "../../../static/image/login/select-a.png",
				background: {
					backgroundColor: "#FFFFFF",
				},
			}
		},
		onLoad(e) {
			this.currentId = e.id;
			this.getListData();
		},
		methods: {
			getListData() {
				let params = {
					job_id: this.currentId
				}
				this.$http("enterprise.Jobs/getoaauthlist", params, "get").then(res => {
					this.list = res.data.data.rows;
				})
			},
			sureBtn() {
				let arr = [];
				console.log(this.list, "SDAsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasda");
			
				this.list.forEach(item => {
					if (item.checked) {
						arr.push(item.id)
					}
				})
				if (!arr.join()) {
					uni.showToast({
						title: "请选择权限!",
						icon: "none"
					})
					return
				}
				let params = {
					job_id: this.currentId,
					oa_page_id: arr.join()
				}
				uni.navigateTo({
					url: "/pages/my/approvalProcess/settingPage?params=" + JSON.stringify(params)
				})
			},
			selectBtn(index) {
				this.list[index].checked = !this.list[index].checked;
				this.$forceUpdate()
			}
		}
	}
</script>

<style lang="scss" scoped>
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
		padding: 0 32rpx;

		.main-item {
			padding: 32rpx 0;
			border-bottom: 1px solid #F6F9FE;
			display: flex;
			align-items: center;
		}
	}
</style>

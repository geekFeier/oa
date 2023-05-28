<template>
	<view>
		<u-navbar :is-back="true" title="个人设置" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<financialAccounting v-if="personType && (personType==1 || personType==2 || personType==3)">
		</financialAccounting>
		<threeServices v-if="personType && (personType==1 || personType==2 || personType==3)"></threeServices>
		<view class="main-bd">
			<u-cell-group :border="false">
				<u-cell-item title="清除缓存" @click="clearBtn" :value="all"></u-cell-item>
				<u-cell-item title="检查更新" @click="goVersion" value="">

				</u-cell-item>
			</u-cell-group>
		</view>
		<button type="default" class="sure_btn" @click="loginOutBtn">退出登录</button>

		<u-modal v-model="isShowOut" content="你确定退出登录?" @confirm="sureAgree" :show-cancel-button="true"
			:show-confirm-button="true" :show-title="false">
		</u-modal>

	</view>
</template>

<script>
	import financialAccounting from "./components/financialAccounting.vue"
	import threeServices from "./components/threeServices.vue";
	import {
		mapState
	} from "vuex";
	export default {
		data() {
			return {
				makename: "",
				name: "",
				isShowOut: false,
				background: {
					backgroundColor: "#FFFFFF",
				},
				all: "0",
				userInfo: {}

			};
		},
		components: {
			financialAccounting,
			threeServices
		},
		computed: {
			...mapState({
				// userInfo: state => state.user.userInfo,
				personType: state => state.user.personType,
			})
		},
		onLoad(e) {
			// #ifdef APP-PLUS
			this.getCacheInfo()
			// #endif


			if (e.flag == 2) {
				this.makename = "";
			} else {
				// this.makename = this.userInfo.config.makename ? this.userInfo.config.makename : "";
			}

		},
		onShow() {
			this.getUserInfo();
		},
		methods: {
			goVersion() {
				uni.navigateTo({
					url: "/pages/my/versionUpdate"
				})
			},
			getUserInfo() {
				this.$http("/User/getUser", {}, "post").then(res => {
					if (res.data.code == 1) {
						this.$store.dispatch("user/GET_USER_INFO", res.data.data);
						this.makename = res.data.data.config.makename ? res.data.data.config.makename : "";
					}
				})
			},

			sureAgree() {
				this.$store.commit('user/GET_TOKEN', {})
				this.$store.commit('user/GET_USER_INFO', {})
				// this.$store.commit('user',{})
				uni.clearStorage();
				setTimeout(() => {
					uni.reLaunch({
						url: "/pages/login/index"
					})
				}, 500)
			},
			loginOutBtn() {
				this.isShowOut = true;
			},
			clearBtn() {
				let vm = this
				//可以询问用户是否删除
				if (this.all == 0) return uni.showToast({
					title: '暂无缓存可清除',
					icon: "none"
				})
				uni.showModal({
					title: '提示',
					content: '确定清除缓存吗?',
					success(res) {
						// 用户确定要删除
						if (res.confirm) {
							//使用plus.cache.clear 清除应用中的缓存数据 这里清除后还要二十几KB没有清除，达不到全部清除
							plus.cache.clear(function() {
								uni.showToast({
									title: '清除成功',
									icon: 'none',
									success: () => {
										vm.getCacheInfo();
									}
								})
							});
						}
					}
				})

			},
			getCacheInfo() {
				// 使用plus.cache.calculate 获取应用的缓存大小，
				plus.cache.calculate((size) => { //size是多少个字节单位是b
					//你可以做下面相应的处理
					if (size < 1024) {
						this.all = size + 'B';
					} else if (size / 1024 >= 1 && size / 1024 / 1024 < 1) {
						this.all = Math.floor(size / 1024 * 100) / 100 + 'KB';
					} else if (size / 1024 / 1024 >= 1) {
						this.all = Math.floor(size / 1024 / 1024 * 100) / 100 + 'M';
					}
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
	page {

		background: #FBFCFF;
	}

	/deep/ .u-grid-item-box {
		padding: 16rpx 0 !important;
	}

	/deep/ .u-cell__value {
		color: #150E33;
	}

	.main-bd {
		padding: 0 32rpx;
		margin-top: 20rpx;
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
</style>
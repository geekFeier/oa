<template>
	<view>
		<u-navbar :is-back="true" title="查看凭证" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="" :disabled='true' @click="openPop" placeholder="搜索凭证"
					bg-color="#fff" @change="changeSearch" v-model="keyword" :height="65" @search="searchEvent"
					:show-action="false">
				</u-search>
				<text @click="openPop">筛选</text>

			</view>
		</view>

		<view class="mainBox" v-if='listData.length>0'>
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
		<view class="u-empty" v-if='listData.length==0'>暂无数据</view>


		<uni-popup ref="popup" :type="'right'" background-color="#fff" @change="change">
			<view class="popup-content">
				<u-form ref="uForm">
					<u-form-item label="科目" label-width="150" :border-bottom="false" right-icon="arrow-right"
						:right-icon-style="{color:'#7d7f97'}">
						<u-input disabled v-model="kemu_title" @click="goKeMu()" type="input" placeholder="请选择科目" />
					</u-form-item>
					<u-form-item label="摘要" label-width="150" :border-bottom="false">
						<u-input disabled v-model="zhaiYaoName" @click="selectPopup2()" type="input"
							placeholder="请选择摘要" />
					</u-form-item>
					<u-form-item label="状态" label-width="150" :border-bottom="false" right-icon="arrow-right"
						:right-icon-style="{color:'#7d7f97'}">
						<picker mode="selector" :range="statusList" @change="changeStatus">
							<view>{{statusList[statusIndex]}}</view>
						</picker>
					</u-form-item>

					<u-form-item class="form-item-group flex">
						<view class="form-item flex">
							<view class="form-item-label">金额</view>
							<input type="number" style="width: 200rpx;" placeholder="输入金额" v-model="amount_start" />
						</view>
						<view class="form-item flex">
							<view class="form-item-label">至</view>
							<input type="number" style="width: 200rpx;" placeholder="输入金额" v-model="amount_end" />
						</view>
					</u-form-item>
					<u-form-item class="form-item-group flex">
						<view class="form-item flex ">
							<view class="form-item-label">时间范围</view>
							<uni-datetime-picker v-model="range" type="daterange" @maskClick="maskClick" />
						</view>

					</u-form-item>
					<u-form-item class="form-item-group flex">
						<view class="form-item flex">
							<view class="form-item-label">凭证号</view>
							<input type="number" style="width: 200rpx;" placeholder="" v-model="code_start" />
						</view>
						<view class="form-item flex">
							<view class="form-item-label">至</view>
							<input type="number" style="width: 200rpx;" placeholder="" v-model="code_end" />
						</view>
					</u-form-item>
					<view class="form-item-group flex">
						<button class="sure" @click="sureBtn()">确定</button>
					</view>
					<view class="form-item-group flex">
						<button @click="clearSearch()" class="cancel">重置</button>
					</view>
				</u-form>
				<u-popup v-model="isShowPopup2" mode="bottom" border-radius="56">
					<view class="popup-main">
						<view class="popup-common-title">
							— 选择摘要 —
						</view>

						<picker-view class="picker-view" style="height: 380rpx;" :value="digestValue"
							@change="changePicker">
							<picker-view-column>
								<view class="item" v-for="(item,index) in popupList" :key="index">{{item.name}}</view>
							</picker-view-column>
						</picker-view>

						<view class="popup-common-btnGroup">
							<view class="popup-common-btn" @click="isShowPopup2 = false">
								取消
							</view>
							<view class="popup-common-btn active" @click="sureDigest">
								确定
							</view>
						</view>
					</view>
				</u-popup>
			</view>
		</uni-popup>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				listData: [],
				page: 1,
				limit: 10,
				formData: {
					recording_time: "",
					operation: "add",
					documents_images: "",
					years: "",
					month: "",
					day: "",
					key: "",
					code: "",
					remarks: ""
				},
				pingZheng: "",
				zhaiYaoName: "",
				isShowPopup2: false,

				keyword: "",
				digestValue: [0],

				background: {
					backgroundColor: "#FFFFFF",
				},
				range: [],
				kemu_title: '',
				c_status: "",
				amount_start: "",
				amount_end: "",
				code_start: "",
				code_end: "",
				time_start: "",
				time_end: '',
				statusIndex: 0,
				statusList: ['全部', '未审核', '审核'],
				changed: false,
				popupList: [{
						name: "提现",
					},
					{
						name: "利息收入",
					},
					{
						name: "支付银行手续费",
					},
					{
						name: "购入固定资产",
					},
				],

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
		onReachBottom: function() {
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
			maskClick(e) {
				console.log('maskClick事件:', e);
			},
			openPop() {
				this.$refs.popup.open()
			},
			change(e) {},
			sureBtn() {
				this.$refs.popup.close()
				this.$set(this, 'listData', [])
				this.getListData();

			},
			goKeMu() {
				this.$navigateTo({
					url: "/pages/my/subject/selectPage"
				}).then(res => {
					console.log(res, ">>>>>>>>")
					this.kemu_title = res.name

				})
			},
			clearSearch() {
				this.digestValue = null
				this.range = []
				this.kemu_title = ''
				this.amount_start = ''
				this.amount_end = ''
				this.code_start = ''
				this.code_end = ''
				this.statusIndex = 0
			},
			changePicker(e) {
				this.changed = true
				this.digestValue = e.detail.value;
			},
			changeStatus(e) {
				console.log(e)
				this.statusIndex = e.detail.value
			},
			selectPopup2(index) {
				this.currentZhaiYaoIndex = index;
				this.isShowPopup2 = true;
			},
			sureDigest() {
				this.isShowPopup2 = false;
				this.zhaiYaoName = this.popupList[this.digestValue.join()].name;
				// this.$forceUpdate()
			},

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
					abstract: this.changed ? this.popupList[this.digestValue].name : '',
					kemu_title: this.kemu_title,
					c_status: this.statusIndex == 0 ? 2 : this.statusIndex == 1 ? 0 : 1,
					amount_start: this.amount_start,
					amount_end: this.amount_end,
					code_start: this.code_start,
					code_end: this.code_end,
					time_start: this.range[0] ? this.range[0] : '',
					time_end: this.range[1] ? this.range[1] : ''

				}
				console.log('---', params)
				this.$http("enterprise.Credentials/index", params, "get").then(res => {
					console.log('aaa---------', res)
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

	opup-content {
		padding: 130rpx 40rpx 20rpx;
		background-color: #fff;

		.form-item-label {
			width: 160rpx;
		}
	}

	.popup-common-title {
		font-size: 36rpx;
		color: #150E33;
		text-align: center;
		margin-top: 56rpx;
	}

	.active {
		background: #4396F7 !important;
		color: #FFFFFF !important;
	}

	.popup-common-btnGroup {
		display: flex;
		padding: 0 50rpx;
		justify-content: space-between;
		font-size: 30rpx;
		margin-top: 50rpx;
		margin-bottom: 32rpx;


		.popup-common-btn {
			width: 260rpx;
			height: 80rpx;
			background: #F6F9FE;
			border-radius: 12rpx;
			color: #7A7C94;
			text-align: center;
			line-height: 80rpx;
		}
	}

	.sure {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;

	}

	.cancel {
		margin-top: 20rpx;
		border-radius: 49px;
		width: 90%;
		color: #333;
	}

	.searchBox {
		display: flex;
		justify-content: space-between;
		padding: 0 40rpx;
	}

	.u-empty {
		text-align: center;
		color: #666;
	}
</style>
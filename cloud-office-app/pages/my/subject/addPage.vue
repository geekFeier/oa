<template>
	<view>
		<u-navbar :is-back="true" title="新增科目" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="hd-form">
			<u-form ref="uForm">
				<!-- superiorPopup -->
				<u-form-item label="上级科目" label-width="150" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input @click="showSuperiorPopup" disabled v-model="currentParentName" type="input"
						placeholder="请选择" />
				</u-form-item>

				<u-form-item label="科目类别" label-width="150" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input @click="showPopupBtn" disabled v-model="currentTypeName" type="input" placeholder="请选择" />
				</u-form-item>
				<!-- 		<u-form-item label="科目编码" label-width="150">
					<u-input disabled v-model="formData.value" type="input" placeholder="请输入科目编码" />
				</u-form-item> -->
				<u-form-item label="科目名称" label-width="150">
					<u-input v-model="formData.name" type="input" placeholder="请输入科目名称" />
				</u-form-item>
				<u-form-item label="状态" label-width="150" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input disabled v-model="currentStatusName" @click="selectStatus" type="input"
						placeholder="请选择" />
				</u-form-item>
				<u-form-item label="余额方向" label-width="150" right-icon="arrow-right"
					:right-icon-style="{color:'#7d7f97'}">
					<u-input disabled v-model="currentBalanceName" @click="directionPopup" type="input"
						placeholder="请选择" />
				</u-form-item>
				<u-form-item :border-bottom="false" label="辅助核算" label-width="150">
					<u-switch slot="right" size="40" v-model="hesuan"></u-switch>
				</u-form-item>
			</u-form>
			<view class="hr-div" v-if="hesuan">
				选择辅助核算类型
			</view>
			<view class="radio-form" v-if="hesuan">
				<view class="radio-form-item" v-for="(item,index) in HsListData" :key="index"
					@click="selectHsItem(index)">
					<image style="width: 40rpx;height: 40rpx;" :src="item.checked ? selecta : select" mode=""></image>
					<text style="margin-left: 24rpx;font-size: 30rpx;">{{item.name}}</text>
				</view>
			</view>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">确定</button>


		<!-- 选择科目类别 -->
		<dellPopup :popupList="tabList" :itemId.sync="formData.category_id" title="选择科目类别" v-model="currentTypeName"
			:isShowPopup.sync="isShowPopup">
		</dellPopup>



		<!-- 选择状态 -->
		<dellPopup :popupList="popupList2" title="选择状态" :itemId.sync="formData.status" v-model="currentStatusName"
			:isShowPopup.sync="isShowPopup2">
		</dellPopup>


		<!-- 余额方向 -->
		<dellPopup :popupList="popupList3" :itemId.sync="formData.balance_status" title="请选择余额方向"
			v-model="currentBalanceName" :isShowPopup.sync="isShowPopup3">
		</dellPopup>

		<!-- 上级 -->
		<u-popup :mask-close-able="true" height="900" v-model="superiorPopup" mode="bottom" border-radius="56">
			<view class="popupMainBox">
				<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="30" active-color="#4396F7"
					:active-item-style="{'color':'#4396F7'}" inactive-color="#7A7C94" :current="swiperCurrent"
					@change="tabsChange" :list="tabList" :is-scroll="false" :bar-style="barStyle">
				</u-tabs-swiper>

				<view class="popupConBox">
					<view class="main-item" v-for="(item,index) in listData" :key="index"
						@click="selectParent(item.name,item.id,item.category.id,item.category.name)">
						<view class="main-item-l">
							{{item.serial}}
						</view>
						<view class="main-item-r">
							{{item.name}}
						</view>
					</view>
				</view>
			</view>
		</u-popup>





	</view>
</template>

<script>
	import dellPopup from "@/components/dell-popup.vue";
	import selectItem from "./select-item.vue"
	export default {
		data() {
			return {
				treeData: [],
				HsListData: [],
				hesuan: false,
				currentParentName: "",
				currentTypeName: "",
				currentBalanceName: "",
				currentStatusName: "",
				listData: [],
				currentFy: {
					currentPage: 1,
					currentSize: 999
				},
				swiperCurrent: 0,
				superiorPopup: false,
				tabList: [],
				isShowPopup: false,
				isShowPopup2: false,
				isShowPopup3: false,
				select: "../../../static/image/login/select.png",
				selecta: "../../../static/image/login/select-a.png",
				background: {
					backgroundColor: "#FFFFFF",
				},
				formData: {
					pid: "",
					category_id: "",
					balance_status: "",
					status: "",
					hesuan: false,
					hesuan_ids: "",
					name: ""
				},
				digestValue: [],
				category_id: "",
				popupList2: [{
						name: "正常",
						id: "normal"
					},
					{
						name: "不可用",
						id: "hidden"
					},
				],
				popupList3: [{
						name: "借",
						id: -1
					},
					{
						name: "贷",
						id: 1
					},
				],
				barStyle: {
					textAlign: "center",
				},
				page: 1,
				limit: 10
			};
		},
		components: {
			dellPopup,
			selectItem
		},
		onLoad() {
			this.getCate();
			this.getHsList();
		},
		methods: {
			selectHsItem(index) {
				this.HsListData[index].checked = !this.HsListData[index].checked;
				this.$forceUpdate()
			},
			getHsList() {
				this.$http("enterprise.Accounting/list?search&pid=0", {}, "post").then(res => {
					this.HsListData = res.data.data.rows;
				})
			},
			selectParent(name, id,cateId,cateName) {
				this.superiorPopup = false;
				this.currentParentName = name;
				this.formData.pid = id;
				this.formData.category_id = cateId;
				this.currentTypeName = cateName;
			},
			getListData() {
				let params = {
					page: this.currentFy.currentPage,
					limit: this.currentFy.currentSize,
					offset: (this.currentFy.currentPage - 1) * this.currentFy.currentSize,
					category_id: this.category_id,
					search: "",
					sort: "serial",
					order: "asc",
					loading_type: "kemu"
				}
				this.$http("enterprise.Subject/getIndex", params, "get").then(res => {
					this.listData = this.treeToArray(res.data.data.rows);
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
				this.currentFy.currentPage = 1;
				this.category_id = this.tabList[index].id;
				this.getListData()
			},

			showSuperiorPopup() {
				this.superiorPopup = true;
			},
			getCate() {
				let params = {
					page: this.page,
					limit: this.limit,
					offset: (this.page - 1) * this.limit,
				}
				this.$http("enterprise.Subject/getKmeuCate", params, "post").then(res => {
					this.tabList = res.data.data;
					this.category_id = this.tabList[0].id || "";
					this.getListData();
				})
			},
			sureBtn() {
				console.log(this.formData.category_id, "asDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
				if(!this.formData.pid){
					uni.showToast({
						title:"请选择上级科目!",
						icon:"none"
					})
					return
				}else if(!this.formData.category_id){
					uni.showToast({
						title:"请选择科目类别!",
						icon:"none"
					})
					return
				}else if(!this.formData.name){
					uni.showToast({
						title:"请输入科目名称!",
						icon:"none"
					})
					return
				}else if(!this.formData.balance_status){
					uni.showToast({
						title:"请选择余额状态!",
						icon:"none"
					})
					return
				}
				if (this.hesuan) {
					this.formData.hesuan = "normal";
					this.formData.hesuan_ids = this.HsListData.filter(item => {
						return item.checked
					}).map(_item => {
						return _item.id
					}).join(",");
				} else {
					this.formData.hesuan = "hidden";
				}
				
				this.$http("enterprise.Subject/create_subject", this.formData, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "新增成功"
						})
						setTimeout(() => {
							this.$navigateBack(true)
						}, 500)
					}
				})
			},
			directionPopup() {
				this.isShowPopup3 = true;
			},
			selectStatus() {
				this.isShowPopup2 = true;
			},
			showPopupBtn() {
				if(!this.formData.pid){
				this.isShowPopup = true;
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.popupConBox {
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

	.picker-view {
		.item {
			font-size: 34rpx;
			text-align: center;
		}
	}

	.popupMainBox {
		padding: 32rpx;
	}

	.sure_btn {
		background: #4396F7;
		border-radius: 49px;
		width: 90%;
		color: #fff;
		margin-top: 60rpx;
		margin-bottom: 50rpx;
	}

	page {
		color: #150E33;
	}

	/deep/ .uni-input-placeholder {
		color: rgb(192, 196, 204);
		font-size: 30rpx;
		text-align: right;
	}

	/deep/ .uni-input-input {
		font-size: 30rpx;
		text-align: right;
	}

	/deep/ .u-form-item {
		padding: 14rpx 32rpx;
		font-size: 30rpx;
	}

	.radio-form {
		.radio-form-item {
			display: flex;
			align-items: center;

			padding: 32rpx;
		}
	}

	.hr-div {
		padding: 24rpx 32rpx;
		color: #7A7C94;
		background: #F6F9FE;
	}

	.popup-main {

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


	}
</style>

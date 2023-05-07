<template>
	<view class="">
		<u-navbar :is-back="true" title="领用申请" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<u-tabs-swiper bar-height="4" bar-width="95" ref="tabs" :font-size="32" active-color="#4396F7"
			:active-item-style="{'color':'#4396F7'}" :current="swiperCurrent" @change="tabsChange" :list="tabList"
			:is-scroll="false" :bar-style="barStyle">
		</u-tabs-swiper>

		<view class="mainBox" v-if="swiperCurrent==0">
			<view class="form-item">
				<view class="form-item-title">
					领用事由
				</view>
				<u-input v-model="formData.content" type="textarea" :auto-height="true" :clearable="false"
					placeholder="请输入领用事由" />
			</view>

			<view class="car-div-title">
				领用明细({{carList.length}})
			</view>
			<view class="form-item4">
				<view class="form-item4-div" v-for="(item,index) in carList" :key="index">
					<view class="form-item">
						<view class="form-item-title">
							名称
						</view>
						<u-input v-model="item.name" type="text" :auto-height="true" :clearable="false"
							placeholder="请输入" />
					</view>
					<view class="form-item">
						<view class="form-item-title">
							规格
						</view>
						<u-input v-model="item.specification" type="text" :auto-height="true" :clearable="false"
							placeholder="请输入" />
					</view>
					<view class="form-item">
						<view class="form-item-title">
							数量
						</view>
						<u-input v-model="item.num" type="number" :auto-height="true" :clearable="false"
							placeholder="请输入" />
					</view>
					<view class="form-item">
						<view class="form-item-title">
							单位
						</view>
						<u-input v-model="item.unit" type="text" :auto-height="true" :clearable="false"
							placeholder="请输入" />
					</view>
					<view class="form-item">
						<view class="form-item-title">
							价格
						</view>
						<u-input v-model="item.price" type="text" :auto-height="true" :clearable="false"
							placeholder="请输入" />
					</view>

				</view>

			</view>

			<view class="form-item4-text">
				如需多种领用，请点击“增加明细”
			</view>

			<view class="form-item">
				<view class="form-item-addBtn" @click="addCarBtn">
					<image style="width: 40rpx;height: 40rpx;margin-right:16rpx ;"
						src="../../../../static/image/tab2/addBtn2.png" mode="">
					</image>增加明细
				</view>

			</view>

			<view class="form-item">
				<view class="form-item-title">
					备注
				</view>
				<u-input v-model="value" type="text" :clearable="false" placeholder="请输入" />
			</view>

			<view class="form-item2">
				<view class="form-item2-header">
					<view class="form-item2-title">
						照片
					</view>
					<image @click="uploadBtn" src="../../../../static/image/tab2/addImg.png"
						style="width: 50rpx;height: 40rpx;" mode="">
					</image>
				</view>
				<view class="form-item2-main">
					<view class="form-item2-main-div" v-for="(item,index) in imgData" :key="index">
						<image :src="item" style="width: 110rpx;height: 110rpx;" mode=""></image>
						<view class="form-item2-main-close" @click="closeImg(index)">
							x
						</view>
					</view>
				</view>
			</view>

			<view class="form-item3">
				<view class="form-item3-main">
					<view class="form-item3-title">
						流程
					</view>
					<view class="form-item3-main-con">
						<image src="../../../../static/image/tab2/leftimg.png" class="form-item3-main-con-l" mode="">
						</image>
						<view class="form-item3-main-con-r">
							<view class="con-r-item">
								<view class="con-r-item-l">
									<text style="font-size: 32rpx;">接收人</text>
									<text v-if="!recipientList.length"
										style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">请选择接收人</text>
									<text v-if="recipientList.length"
										style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">{{recipientList.length}}人接收</text>
								</view>
								<view class="con-r-item-r">
									<!-- 					<scroll-view :scroll-x="true" class="con-r-item-r-person-item"> -->
									<view class="flex" style="overflow-x: scroll;width: 320rpx;">
										<view class="con-r-item-r-person-item">

											<view v-for="(item,index) in recipientList" :key="item.id"
												class="con-r-item-r-person">

												<image :src="item.avatar" class="con-r-item-r-person-img" mode="">
												</image>

												<image src="../../../../static/image/tab2/close.png"
													@click="closeBtn(1,item.id)" class="con-r-item-r-person-close"
													mode=""></image>
											</view>
										</view>
									</view>

									<!-- </scroll-view> -->
									<view class="upload-div" @click="addRecipient">
										+
									</view>
								</view>
							</view>
							<view class="con-r-item">
								<view class="con-r-item-l">
									<text style="font-size: 32rpx;">抄送人</text>
									<text v-if="!copiedPeople.length"
										style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">请选择抄送人</text>
									<text v-if="copiedPeople.length"
										style="font-size: 24rpx;color: #7A7C94;margin-top: 8rpx;">{{copiedPeople.length}}人抄送</text>
								</view>

								<view class="con-r-item-r">
									<view class="flex" style="overflow-x: scroll;width: 320rpx;">
										<view class="con-r-item-r-person-item">

											<view v-for="(item,index) in copiedPeople" :key="item.id"
												class="con-r-item-r-person">

												<image :src="item.avatar" class="con-r-item-r-person-img" mode="">
												</image>

												<image @click="closeBtn(2,item.id)"
													src="../../../../static/image/tab2/close.png"
													class="con-r-item-r-person-close" mode=""></image>
											</view>
										</view>
									</view>

									<!-- </scroll-view> -->
									<view class="upload-div" @click="addCopied">
										+
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>

			</view>

			<button type="default" class="addBtn" @click="submitBtn">提交</button>
		</view>

		<view class="mainBox2" v-if="swiperCurrent==1">
			<view class="main-item" @click="goDetail(item)" v-for="(item,index) in listData" :key="index">
				<view class="main-item-t">
					<view class="main-item-t-l">
						{{item.user.username}}提交的领用申请
					</view>
					<view class="main-item-t-r">
						{{item.createtime.substring(0,10)}}
					</view>
				</view>
				<view class="main-item-m">
					<view class="main-item-m-text">
						领用事由：{{item.content}}
					</view>

					<view class="main-item-m-text2">
						<text>领用明细(1)：</text>
						<view class="main-item-m-text2-r">
							<view class="main-item-text2-r-item " v-for="(_item,index) in item.dateils">
								<view class="main-item-text2-r-item-t">
									<text>名称：{{_item.name}}</text>
									<text>数量：{{_item.num}}</text>
									<text>价格：{{_item.price}}</text>
								</view>
								<view class="main-item-text2-r-item-t" style="margin-left: 50rpx;">
									<text>规格：{{_item.specification}}</text>
									<text>单位：{{_item.unit}}</text>
								</view>
							</view>
						</view>
					</view>

				</view>
			<view class="main-item-b">
				<view class="main-item-b-l">
					<image class="main-item-b-l-img" :src="item.user.avatar" mode=""></image>
					<text>由{{item.user.username}}提交</text>
				</view>
				<view class="main-item-b-r" :class="item.status | filterStatus">
					{{item.status == 0 ? "审核中" : (item.status == 1? "审核成功" : "审核失败") }}
				</view>
			</view>
			</view>
		</view>


	</view>
</template>

<script>
	import {
		url_config,img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {
				listData: [],
				page: 1,
				limit: 10,
				copiedPeople: [],
				recipientList: [],
				imgData: [],
				carList: [{
					name: "",
					specification: "",
					num: "",
					unit: "",
					price: ""
				}],
				currentTimeType: "",
				currentSelectType: "",
				formData: {
					content: "",
					images: "",
					dateils: "",
					remarks: ""
				},
				showSelectTime: false,
				currentSelect: 0,
				selectSrc: "../../../../static/image/tab2/select.png",
				selectSrcA: "../../../../static/image/tab2/selecta.png",
				showVoucherPopup: false,
				value: "",
				swiperCurrent: 0,
				background: {
					backgroundColor: "#FFFFFF",
				},

				barStyle: {
					textAlign: "center",
				},
				tabList: [{
						name: "发起提交"
					},
					{
						name: "提交记录"
					},
				]
			}
		},
		filters: {
			filterStatus(val) {
				if (val == 0) {
					return "blue"
				} else if (val == 1) {
					return "green"
				} else if (val == 1) {
					return "red"
				}
			},
		
		},
		onReachBottom() {
			if(this.swiperCurrent==0){
				this.page++;
				this.getListData();
			}
		},
		onLoad() {
			this.getListData();
			this.getReceiviPersion();
		},
		methods: {
			getReceiviPersion(){
				let params = {
					pageId : 1
				}
				this.$http("enterprise.applyfor.base/getReceiviPersion?pageId=5",params,"post").then(res=>{
				
					if(res.data.code==1){
						this.copiedPeople = res.data.data.ccPerson;
						this.recipientList = res.data.data.receiviPerson;
					}
				})
			},
			tabsChange(index) {
				this.swiperCurrent = index;
			},
			addCarBtn() {
				this.carList.push({
					name: "",
					specification: "",
					num: "",
					unit: "",
					price: ""
				})
			},

			goDetail(item) {
				this.$navigateTo({
					url: "/pages/cooperation/applyAllPage/receiveApply/detail?data=" + encodeURIComponent(JSON
						.stringify(item))
				})
			},
			submitBtn() {
				if(!this.formData.content){
					uni.showToast({
						title:"请输入领用事由!",
						icon:"none"
					})
					return
				}
				for (let item of this.carList) {
					if(!item.name){
						uni.showToast({
							title:"请输入领用名称!",
							icon:"none"
						})
						return
					}
					if(!item.specification){
						uni.showToast({
							title:"请输入领用规格!",
							icon:"none"
						})
						return
					}
					if(!item.unit){
						uni.showToast({
							title:"请输入领用单位!",
							icon:"none"
						})
						return
					}
					if(!item.num){
						uni.showToast({
							title:"请输入领用数量!",
							icon:"none"
						})
						return
					}if(!item.price){
						uni.showToast({
							title:"请输入价格!",
							icon:"none"
						})
						return
					}
				}
				
				this.formData.images = this.imgData.join(",");
				this.formData.receivi_user_ids = this.recipientList.map(item => {
					return item.id
				}).join(",")
				this.formData.cc_persion = this.copiedPeople.map(item => {
					return item.id
				}).join(",")
				if(!this.formData.receivi_user_ids){
					uni.showToast({
						title:"请选择接收人!",
						icon:"none"
					})
					return
				}
				// if(!this.formData.cc_persion){
				// 	uni.showToast({
				// 		title:"请选择抄送人!",
				// 		icon:"none"
				// 	})
				// 	return
				// }
				this.formData.dateils = JSON.stringify(this.carList)
			
					this.$http("enterprise.applyfor.Lingyong/CreateForm", this.formData, "post").then(res => {
						if (res.data.code == 1) {
							this.formData = {};
							this.recipientList = [];
							this.copiedPeople = [];
							this.imgData = [];
							this.carList = [];
							uni.showToast({
								title: "申请成功",
								icon: "none"
							})
							this.listData = [];
							this.page = 1;
							this.getListData();
						}
					})
				
			},
			getListData() {
				let params = {
					offset: (this.page - 1) * 10,
					page: this.page,
					limit: this.limit
				}
				this.$http("enterprise.applyfor.Lingyong/index", params, "get").then(res => {
					this.listData = this.listData.concat(res.data.data.rows);
					this.listData.forEach(item => {
						item.dateils = JSON.parse(decodeURIComponent(item.dateils))
					})
				})
			},
			closeBtn(type, id) {
				if (type == 1) {
					let index = this.recipientList.findIndex(item => {
						item.id == id
					});
					this.recipientList.splice(index, 1)
				} else if (type == 2) {
					let index = this.copiedPeople.findIndex(item => {
						item.id == id
					});
					this.copiedPeople.splice(index, 1)
				}
			},
			addCopied() {
				this.$navigateTo({
					url: "/components/contact"
				}).then(res => {
					this.copiedPeople = res;
				})
			},
			addRecipient() {
				this.$navigateTo({
					url: "/components/contact"
				}).then(res => {
					this.recipientList = res;
				})
			},
			uploadBtn() {
				uni.chooseImage({
					count: 9,
					success: (res) => {
						this.uploadImgs(res.tempFilePaths)
					}
				})
			},
			uploadImgs(files) {
				uni.showLoading({
					title: "上传中"
				})
				let promiseArr = [];
				let promiseItem = files.forEach(item => {
					new Promise((resolve, reject) => {
						uni.uploadFile({
							url: url_config + "/Common/upload",
							name: "file",
							filePath: item,
							header: {
								token: uni.getStorageSync("token") || ""
							},
							success: (res) => {
								let _res = JSON.parse(res.data);
								if (_res.code == 1) {
									resolve(true)
										this.imgData.push(`${img_url}${_res.data.url}`)
								} else {
									reject(false)
								}
								// promiseArr.push();
							},
							fail: (err) => {
								reject(false)
							}
						})
					})


				})
				promiseArr.push(promiseItem);
				Promise.all(promiseArr).then(res => {
					uni.hideLoading()
					uni.showToast({
						title: "上传成功",
					})
				}).catch(err => {
					uni.hideLoading()
					uni.showToast({
						title: "上传失败",
						icon: "none"
					})
				})
			},
			closeImg(index) {
				this.imgData.splice(index, 1)
			},
		}
	}
</script>

<style scoped lang="scss">
	.con-r-item-r-person-item {
		display: flex;
		// line-height: 140rpx;
		// display: block;
		align-items: center;
		flex: 1 0 auto;
		// height: 100%;
		white-space: nowrap;
		// overflow: scroll;
		// margin-right: 16rpx;
		// width: 320rpx; // 加入width
		justify-content: flex-end;
	}

	/deep/ .u-input__input {
		font-size: 32rpx;
	}

	.form-item4-text {
		padding: 24rpx 32rpx;
		color: #7A7C94;
	}

	.car-div-title {
		color: #4396F7;
		// background-color: pink;
		padding: 24rpx 32rpx;
	}

	.form-item4 {
		padding: 32rpx 0;

		.form-item {

			margin: 0 !important;
			padding-top: 32rpx !important;
			padding-bottom: 32rpx !important;
			border-bottom: 1px solid #EEF2FF;

			.form-item-title {
				margin-bottom: 10rpx;
			}
		}
	}

	.addBtn {
		text-align: center;
		width: 88%;
		height: 90rpx;
		line-height: 90rpx;
		color: #fff;
		background: #4396F7;
		margin-top: 60rpx;
		margin-bottom: 40rpx;
		border-radius: 49px;
	}

	page {
		background: #FBFCFF;
		color: #150E33;
	}

	.mainBox2 {
		padding: 0 32rpx;

		.main-item {
			background-color: #fff;
			margin-top: 32rpx;
			border-radius: 24rpx;
			padding: 32rpx 24rpx;

			.main-item-t {
				display: flex;
				align-items: center;
				justify-content: space-between;

				.main-item-t-l {
					font-size: 32rpx;
				}

				.main-item-t-r {
					font-size: 26rpx;
					color: #B5BFDA;
				}
			}

			.main-item-m {
				margin-top: 32rpx;
				font-size: 28rpx;
				color: #7A7C94;

				.main-item-m-text {
					margin-bottom: 20rpx;
				}

				.main-item-m-text2 {
					display: flex;

					.main-item-m-text2-r {
						flex: 1;
					}

					.main-item-text2-r-item {
						display: flex;

						// justify-content: space-between;
						// flex-wrap: wrap;
						// justify-content: space-between;
						.main-item-text2-r-item-t {
							display: flex;
							flex-direction: column;
						}
					}
				}
			}

			.main-item-b {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-top: 32rpx;
				padding-top: 32rpx;
				border-top: 1px solid #EEF2FF;

				.main-item-b-l {
					display: flex;
					align-items: center;
					font-size: 30rpx;

					.main-item-b-l-img {
						margin-right: 16rpx;
						width: 60rpx;
						height: 60rpx;
						border-radius: 50%;
					}

				}

			}
		}
	}


	.mainBox {


		.form-item3 {
			padding: 0 32rpx;

			.form-item3-main {
				background-color: #fff;
				padding: 32rpx 24rpx;

				.form-item3-title {
					font-size: 32rpx;

				}

				.form-item3-main-con {
					display: flex;
					align-items: center;
					margin-top: 40rpx;

					.form-item3-main-con-l {
						width: 20rpx;
						height: 160rpx;
						margin-right: 25rpx;
					}

					.form-item3-main-con-r {
						flex: 1;

					}


					.con-r-item {
						display: flex;
						align-items: center;
						justify-content: space-between;
						padding: 32rpx 0;
						border-bottom: 1px solid #EEF2FF;

						&:nth-of-type(2) {
							border: none;
						}

						.con-r-item-l {
							display: flex;
							flex-direction: column;
						}

						.con-r-item-r {
							display: flex;

							.upload-div {
								width: 72rpx;
								height: 72rpx;
								background: #FBFCFF;
								text-align: center;
								line-height: 72rpx;
								font-size: 50rpx;
								color: #B5BFDA;
								border: 1px solid #B5BFDA;
								border-radius: 8rpx;
							}


							.con-r-item-r-person {
								position: relative;
								margin-right: 20rpx;
								display: inline-block;

								.con-r-item-r-person-img {
									width: 72rpx;
									height: 72rpx;
									border-radius: 8rpx;
								}

								.con-r-item-r-person-close {
									position: absolute;
									width: 30rpx;
									height: 30rpx;
									right: -10rpx;
									top: 0rpx;
								}
							}

						}
					}
				}
			}
		}

		.form-item2 {
			padding: 32rpx;
			background: #fff;
			margin-top: 20rpx;
			font-size: 32rpx;

			.form-item2-header {
				display: flex;
				justify-content: space-between;
			}

			.form-item2-main {
				margin-top: 24rpx;
				display: flex;
				flex-wrap: wrap;
				margin-right: -24rpx;

				.form-item2-main-div {
					position: relative;
					margin-bottom: 12rpx;
					margin-right: 24rpx;
				}

				.form-item2-main-close {
					position: absolute;
					top: 0;
					right: 0;
					width: 30rpx;
					height: 30rpx;
					background-color: #f5222d;
					line-height: 30rpx;
					text-align: center;
					color: #fff;
					font-size: 24rpx;
				}
			}
		}

		.form-item {

			background: #fff;
			padding: 32rpx;
			margin-top: 20rpx;

			.form-item-addBtn {
				display: flex;
				justify-content: center;
				align-items: center;
				color: #4396F7;
				font-size: 36rpx;
			}

			.form-item-title {
				font-size: 32rpx;
				margin-bottom: 10rpx;
			}
		}

	}

	.popup-main {
		.popup-common-m {
			padding: 0 40rpx;

			.popup-common-item {
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-size: 32rpx;
				margin-top: 36rpx;
			}
		}

		.popup-common-title {
			font-size: 36rpx;
			color: #150E33;
			text-align: center;
			margin: 56rpx 0 56rpx 0;
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

	.pul {
		color: #915FF2;
	}

	.red {
		color: #FF253D;
	}

	.blue {
		color: #4396F7;
	}

	.orange {
		color: #FA7443;
	}

	.green {
		color: #12D592;
	}
</style>

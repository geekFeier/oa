<template>
	<view class="contactBox">
		<u-navbar :is-back="true" title="选择接收人" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="headerBox">
			<view class="searchBox">
				<u-search input-align="center" style="padding: 0px 20px" placeholder="搜索" bg-color="#F6F5FA"
					v-model="keyword" :height="65" @custom="searchEvent" @clear="clearBtn"></u-search>
			</view>
		</view>

		<view class="allSelect">
			<view class="checkbox" :class="{'uni-radio-input-checked' : allChecked}" @click="allCheckedBtn"></view>全选
		</view>

		<tree ref="treeRef" :treeData="treeData"></tree>

		<button type="default" class="sure_btn" @click="loginBtn">确定</button>
	</view>
</template>

<script>
	import tree from './tree';
	const someFun = (arr, s) => {
		let m = arr.some(e => {
			if (e.type == 1) {
				var reg = new RegExp(s, 'i');
				// console.log("reg: " , e.orgName,e);
				let m = reg.test(e.username);
				if (m) {
					return true
				} else if (e.staff.length > 0) {
					return someFun(e.staff, s)
				} else {
					return false
				}
			} else {
				return false
			}
		})
		return m
	}
	export default {
		name: "contact",
		data() {
			return {
				treeData: [],
				allChecked: false,
				keyword: "",
				background: {
					backgroundColor: "#FFFFFF",
				},
				mult: true,
				tabIndex: "",
				lastChecked: [], //上次默认选中的项
				container: [] //保存已选中的项
			};
		},
		watch: {
			keyword(val) {
				if (val == "") {
					this.getTreeData();
				}
			}
		},
		components: {
			tree
		},
		onLoad() {
			this.getTreeData();
			uni.$on('onChangeChecked', (item) => {
				this.onItemChangeChecked(item)
			})

		},
		onLaunch() {
			uni.$off("onChangeChecked")
		},

		methods: {
			// 当某一项改变选中状态
			onItemChangeChecked(item) {
				for (let i = 0; i < this.treeData.length; i++) {
					const active_item = this.treeData[i]['staff']
					for (let o = 0; o < active_item.length; o++) {
						if (active_item[o]['id'] === item.id) {
							active_item[o]['checked'] = !active_item[o]['checked']
							break;
						}
					}
				}
				if (item.checked) {
					this.isCheckAll() //判断是不是全选状态
				} else {
					this.allChecked = false
				}
			},
			// 判断是不是全选状态
			isCheckAll() {
				let isSelectAll = true
				for (let i = 0; i < this.treeData.length; i++) {
					const active_item = this.treeData[i]['staff']
					for (let o = 0; o < active_item.length; o++) {
						if (!active_item[o]['checked']) {
							isSelectAll = false
							break;
						}
					}
				}
				this.allChecked = isSelectAll
			},

			changeRadioArr(e) {
				console.log(e, "单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选");
				let indexs = null
				this.container.forEach((item, index) => {
					if (item.id == e.id) {
						item.checked = e.checked
					}
				})
				this.$forceUpdate()
				// this.container[indexs]

			},
			changeAllChecked() {
				console.log("触发");
				// this.container = [];
				if (this.allChecked) {
					this.treeRecursion(this.treeData, this.allChecked);
				} else {
					// this.container = [];
				}
			},
			getTreeData() {
				let params = {
					search: this.keyword
				}
				this.$http("enterprise.Jobs/list", params, "get").then(res => {

					// this.treeData = res.data.data.group;
					console.log(this.treeData);
					// 获取
					const list = res.data.data.group
					list.forEach(e => {
						e.staff.forEach(o => {
							o.checked = false
						})
					})
					this.treeData = list;

				})
			},

			loginBtn() {
				const active_list = [];
				this.treeData.forEach(e => {
					e.staff.forEach(item => {
						if (item.checked) active_list.push(item)
					})

				})

				console.log("选中的集合", active_list);

				// let arr= []
				// console.log(this.container)
				// this.container.forEach(item=>{
				// 	if(item.checked){
				// 		arr.push(item)
				// 	}
				// })
				this.$navigateBack(active_list)
				// console.log(arr, "======================================");

			},
			allCheckedBtn() {
				this.allChecked = !this.allChecked;
				// this.$forceUpdate()
				if (this.allChecked) {
					this.treeData.forEach((e) => {
						e.staff.forEach(o => {
							o.checked = true
						})
					})
				} else {
					this.treeData.forEach((e) => {
						e.staff.forEach(o => {
							o.checked = false
						})
					})
				}
				// this.$forceUpdate()

				// this.recursionTree(this.treeData);
			},
			treeRecursion(arr, val) {
				// this.container.forEach(item => {
				// 	// console.log(item.checked, "==================", val, "Aaaaaaaaaaaaaaaaa");
				// 	if (item.checked == val) {
				// 		item.checked=val
				// 		// this.container.push(item)
				// 	}
				// 	if (item.staff) {
				// 		this.treeRecursion(item.staff, val)
				// 	}
				// })
				// console.log(this.container)

				// console.log(arr, "======", val);
			},
			// recursionTree(arr) {
			// 	arr.forEach(item => {
			// 		if (item.staff && item.staff.length > 0) {
			// 			this.recursionTree(item.staff)
			// 		}
			// 		item.checked === this.allChecked;
			// 	})
			// },
			searchEvent() {
				this.getTreeData();
				// this.$refs.treeRef.onSearch();
			},
			clearBtn() {
				this.keyword = "";
				this.getTreeData();
			},
			tabChange() {
				let tabIndex = e.detail.current
				this.tabIndex = tabIndex
			}
		}
	}
</script>

<style scoped lang="scss">
	page {
		background: #FBFCFF;
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

	.allSelect {
		display: flex;
		align-items: center;
		padding: 0 32rpx;
		height: 100rpx;
		margin: 20rpx 0;
		background-color: #fff;
	}

	.checkbox {
		margin-right: 12px;
		border: 1px #d1d1d1 solid;
		border-radius: 12px;
		width: 24px;
		height: 24px;
	}

	.uni-radio-input-checked {
		background-color: rgb(0, 122, 255);
		border-color: rgb(0, 122, 255);
		position: relative;
	}

	.uni-radio-input-checked::before {

		font-family: "cuIcon";
		content: "\e645";
		position: absolute;
		color: #ffffff !important;
		top: 50%;
		margin-top: -8px;
		right: 3px;
		font-size: 16px;
		line-height: 16px;
		pointer-events: none;
		-webkit-transform: scale(1, 1);
		transform: scale(1, 1);
		-webkit-transition: all 0.3s ease-in-out 0s;
		transition: all 0.3s ease-in-out 0s;
		z-index: 9;
	}
</style>
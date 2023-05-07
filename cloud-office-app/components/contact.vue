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
			<view class="checkbox" :class="[allChecked?'uni-radio-input-checked':'']" @click="allCheckedBtn"></view>全选
		</view>

		<tree @changeAllChecked="changeAllChecked" ref="treeRef" :allChecked.sync="allChecked" :mult="mult" :treeData.sync="treeData"
			:lastChecked="lastChecked" @changeRadioArr="changeRadioArr" :container.sync="container"></tree>

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
		},
		methods: {
			changeRadioArr(e){
				console.log(e,"单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选单选");
			},
			changeAllChecked(){
				console.log("触发");
				this.container = [];
				if(this.allChecked){
				this.treeRecursion(this.treeData, this.allChecked);
				}else{
					// this.container = [];
				}
			},
			getTreeData() {
				let params = {
					search: this.keyword
				}
				this.$http("enterprise.Jobs/list", params, "get").then(res => {
					this.treeData = res.data.data.group;
				})
			},
	
			loginBtn() {
				this.$navigateBack(this.container)
				console.log(this.container, "======================================");
			},
			allCheckedBtn() {
				this.allChecked = !this.allChecked;
				this.$forceUpdate()
				// this.recursionTree(this.treeData);
			},
			treeRecursion(arr, val) {
				arr.forEach(item => {
					// console.log(item.checked, "==================", val, "Aaaaaaaaaaaaaaaaa");
					if (item.checked == val) {
						this.container.push(item)
					}
					if (item.staff) {
						this.treeRecursion(item.staff, val)
					}
				})
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

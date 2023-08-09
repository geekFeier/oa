<template>
	<view class="tree-view">
		<!-- 				<view class="cu-bar bg-white search"  style="top:0px;border-radius: 0 0 25px 25px;box-shadow: 0 0.5px 3px rgba(0, 0, 0, 0.1);">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input type="text" placeholder="请输入" confirm-type="search" v-model="searchStr" @change="onSearch"></input>
			</view>
		</view> -->
		<view class=" flex-sub" style="position: relative;padding-bottom: 55px;padding-top: 4px;">
			<scroll-view scroll-y class="container" scroll-with-animation="true" enable-back-to-top="true">
				<!-- 	<nest-item  v-bind="$attrs" v-on="$listeners" :search="searchStr" :mult="mult" :container.sync="container" :value.sync="item"
					v-for="(item,index) in treeData" :key="index"></nest-item> -->
				<nest-item :value="item" v-for="(item,index) in treeData" :key="index"></nest-item>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	// import tkiTree from '@/components/tki-tree/tki-tree.vue'
	import nestItem from '@/components/nest-item/nest-item.vue'
	import {
		mapState
	} from 'vuex'
	import {
		remove,
		every,
		flatMapDeep
	} from 'lodash'
	const someFun = (arr, s) => {
		let m = arr.some(e => {
			if (e.type == 1) {
				var reg = new RegExp(s, 'i');
				let m = reg.test(e.orgName);
				if (m) {
					return true
				} else if (e.children.length > 0) {
					return someFun(e.children, s)
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
		components: {
			// tkiTree,
			// nestItem
		},
		props: {
			searchStr: {
				type: String,
				default: ""
			},
			treeData: {
				type: Array,
				default: () => []
			},
			mult: {
				type: Boolean,
				default: false
			},
			lastChecked: {
				type: Array,
				default: () => {
					return []
				}
			},
			container: {
				type: Array,
				default: () => {
					return []
				}
			},
		},
		provide() {
			return {
				container: this.container,
			};
		},
		data() {
			return {
				multiple: true,
				selectParent: true,
				flod: false,
				indexCopy: [],
				// searchStr: '',
				lastStr: '',
				treeMap: [],
				treeAndPerson: []
			}
		},
		filters: {
			formatName(item) {
				if (item.name) {
					return item.name
				}
				return '没有名字'
			},

		},
		computed: {},
		mounted() {
			this.initIndexList()

		},
		onLoad() {
			uni.$on('changeSelect', () => {
			})
		},
		methods: {
			onChangeRadioArr(e) {
			},

			changeRadioArr(val) {
				console.log(val)
			},

			initIndexList() {
				// this.indexCopy = JSON.parse(JSON.stringify(this.treeAndPerson))
				this.indexCopy = [{
					orgType: "project",
					orgName: "测试部门",
					type: 1,
					children: [{
						orgType: "person",
						name: "人员",
						type: 2,
					}]
				}]
				// this.recursiveSet(this.indexCopy)
			},

			/**
			 * 格式化头像
			 * @param {Object} person
			 */
			formatAvatar(person) {
				if (person.profileFile) {
					return person.profileFile + '?x-oss-process=image/resize,l_100,m_mfit'
				} else {
					return '../../../static/img/def_avatar.png'
				}
			},
			/**
			 * 搜索框获取焦点
			 * @param {Object} e
			 */
			onSearch(e) {
				let s = this.searchStr;
				this.lastStr = this.searchStr
				this.treeData.forEach(e => {
					e.staff = someFun(e.staff, s)
				})
			},
			/**
			 * 递归
			 * @param {Object} arr
			 */
			recursiveSet(arr) {
				arr.forEach(e => {
					if (e.type == 1) {
						this.recursiveSet(e.children)
					} else {
						if (this.lastChecked.indexOf(e.id + '') != -1) {
							e.checked = true
							let i = this.container.findIndex(c => c.id == e.id)
							if (i == -1) {
								// this.container.push(e)
							}

						}
					}
				})
			},

			// 确定回调事件
			treeConfirm(e) {
				console.log(e)
			},
			// 取消回调事件
			treeCancel(e) {
				console.log(e)
			},
			// 显示树形选择器
			showTree() {
				this.$refs.tkitree._show();
			},
			getTreelist(val) {
				const unChecked = val.filter(item => {
					return item.checked
				})
			}
		},
		watch: {
			treeAndPerson(nval, oval) {
				this.initIndexList()
			}

		},
	}
</script>

<style lang="scss" scoped>
	.tree-view {
		width: 100%;
		height: 100%;
		overflow: hidden;
		display: -webkit-box;
		display: -webkit-flex;
		display: flex;
		-webkit-box-orient: vertical;
		-webkit-box-direction: normal;
		-webkit-flex-direction: column;
		flex-direction: column;
		position: relative;
	}

	.container {
		height: 100%;
		width: 100%;
		overflow: auto;
	}

	.tki-tree-check {
		width: 40px;
		height: 40px;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.tki-tree-check-yes,
	.tki-tree-check-no {
		width: 20px;
		height: 20px;
		border-top-left-radius: 50%;
		border-top-right-radius: 50%;
		border-bottom-right-radius: 50%;
		border-bottom-left-radius: 50%;
		border-top-width: 1rpx;
		border-left-width: 1rpx;
		border-bottom-width: 1rpx;
		border-right-width: 1rpx;
		border-style: solid;
		border-color: #007aff;
		display: flex;
		justify-content: center;
		align-items: center;
		box-sizing: border-box;
	}

	.tki-tree-check-yes-b {
		width: 12px;
		height: 12px;
		border-top-left-radius: 50%;
		border-top-right-radius: 50%;
		border-bottom-right-radius: 50%;
		border-bottom-left-radius: 50%;
		background-color: #007aff;
	}

	.tki-tree-check .radio {
		border-top-left-radius: 50%;
		border-top-right-radius: 50%;
		border-bottom-right-radius: 50%;
		border-bottom-left-radius: 50%;
	}

	.tki-tree-check .radio .tki-tree-check-yes-b {
		border-top-left-radius: 50%;
		border-top-right-radius: 50%;
		border-bottom-right-radius: 50%;
		border-bottom-left-radius: 50%;
	}

	.flex-sub {
		height: 100%;
	}
</style>
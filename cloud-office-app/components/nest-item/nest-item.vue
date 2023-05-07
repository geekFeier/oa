<template>
	<view v-if="!this.search!=''||match||childMatch">
		<template v-if="value.flag">
			<view class="nest-item" @click="openChild">
				<image class="tki-tree-icon" :src="value.open  ? currentIcon : defaultIcon"></image>
				<text class="title-text">{{value.name + '(' + value.staff.length + '人)'||" "}}</text>
			</view>
		</template>
		<template v-else-if="search==''||parentMatch">
			<view class="persion-item flex " @click="onClick">
				<view class="checkbox" :class="[value.checked?'uni-radio-input-checked':'']"></view>
				<view class="persion-item-l flex ">
					<image class="avatar" :src="value.avatar" mode="aspectFill" lazy-load></image>
					<view class="persion-item-user flex flex-direction">
						<text class="name text-gray">{{value.username}}</text>
					</view>
				</view>

			</view>
		</template>
		<template v-if="value.staff&&value.staff.length>0&&value.open">
			<view class="child-padding">
				<nest-item :mult="mult" :parentMatch="match" :search="search" v-for="(item,index) in value.staff"
					:value.sync="item" :key="value.id+'child'+index"></nest-item>
			</view>
		</template>
	</view>
</template>

<script>
	/**
	 * children:Array[16]
id:86
orgName:"项目部"
parentId:0
profileFile:null
type:1
	 */
	const someFun = (arr, s) => {
		// let m = arr.some(e => {
		// 	var reg = new RegExp(s, 'i');
		// 	let m = reg.test(e.username);
		// })
		let m = arr.some(e => {
			if (e.staff) {
				var reg = new RegExp(s, 'i');
				// console.log("reg: " , e.orgName,e);
				let m = reg.test(e.username);
				console.log(m, "ASDsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsd");
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
	import {
		remove
	} from 'lodash'
	export default {
		name: 'nest-item',
		props: {
			mult: {
				type: Boolean,
				default: false
			},
			// allChecked: {
			// 	type: Boolean,
			// 	default: false
			// },
			value: {

			},
			currentIcon: { // 展开时候的ic
				type: String,
				default: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFEAAABRCAYAAACqj0o2AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MEQ0QTM0MzQ1Q0RBMTFFOUE0MjY4NzI1Njc1RjI1ODIiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MEQ0QTM0MzU1Q0RBMTFFOUE0MjY4NzI1Njc1RjI1ODIiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDowRDRBMzQzMjVDREExMUU5QTQyNjg3MjU2NzVGMjU4MiIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDowRDRBMzQzMzVDREExMUU5QTQyNjg3MjU2NzVGMjU4MiIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PidwepsAAAK0SURBVHja7JxbTsJAFIYHww7ciStgCeoGvGxAiOsgURegoL5720AXYLiIr0aJviq3Zx3PhIEnKG3ndtr+f3KixrSUj/ZjzjClIqUUiFm2gAAQAREQEUAEREAERAQQAREQAREBREAEREBEEqa67h9RFDWllDv0awWYlqlQHmu1WjMRRMoV1QFttA12y3xRtdNczq8EsE4/f8FumX2q77ROvNXk8UGMEKdUz6tYJHljaZAbuyUH+UR1to5BEohTuqwPCeS4pAA/qY6o/kyHOAMCeRK3owJnj+rH1jjxhqpVsstaebCz6TmnHWyXyY+xHjSBWBY/bvSgadtXBj9u9KCN3rnIfkzkQVsTEEX0Y2IP2oKo/HhMICcFAThUcwVZNGU6FdbX/XURzkbVF4+ybGhjPrFdgP66QdXNurGtSdk6Xdb9nAJ8oDo3OQlsQZzkdPw41ONBo6vI5scDefRjZg+6gpg3Pxp50CXEvPjR2IOuIXL3oxUPuobI3Y9WPOgDIlc/WvOgL4iL/vqFCcD7LH0xB4hj7cfQ/fWH9qCT+FhG0tN+DBk1PzjOM0SVllixcsBT1AvYc/kAPhc0hRg/3uvxoCgKRN9+dOrBUBB9+9GpB0NC9OVH5x4MDdG1H714kANEV3705kEOEBf9dcPi/lQnsuvLg1wgSu3Ha0v7Uh4MMgUXeuG71H407a+VBy9CPQkOdw+MtB+nGbd/D+FBbhBNxo9SjwcngJjNj0E9yBFiFj8G9SBXiGn8GNyDnCEm8SMLD3KHGOdHNh7kDjHOj2w8mAeIi/5arX+c6b/fxHz9oADEdGdjR/fXCw/OOB5oVfCOgnepz8IB14PMw03jCmTE+QBx5z0gAmKSqK9OUF+hcAeIhu/QYr4Qie8rjW83hhMBERARQAREQAREBBABERCLnH8BBgA+TQI7U4t53AAAAABJRU5ErkJggg=='
			},
			defaultIcon: { // 折叠时候的ic
				type: String,
				default: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFEAAABRCAYAAACqj0o2AAACE0lEQVR4Xu3c200DMRCF4XEltJAOkEugA+ggpUAHoQMqiFMCdEAJUMEiS4mEELlIO7bPOeN9i6K1rG/952myyea1WiCtXmEuYBPR4RBMxInoIOCwhOtJLKVszWyXc/5y2BvNEq6I+/3+kFK6M7OHnPM7jcLKjbZAvD/uaZtzflm5P4rbWyJWgDcze1LPuzVihfxUz7sH4ilJ2bx7Isrm3RtRMu8RiHJ5j0SUyXs0okTeCIj0eSMh0uaNhkiZNyIiXd7IiDR5oyNS5M2ACJ83EyJs3myIkHkzIsLlzYwIkzc7IkTeCojD81ZCHJa3GuKQvBURu+etjNgtb3XELnlHQGyedyTEZnlHQ2ySd0RE97wjI7rlHR3RJe+JeIrbLOecD6ePpZQ6W1kn2epo4MUrPOKyLN8ppYq1+y1VStncOjIdGnFZlo+U0uOtWOeOY2TE12Ouq//pEA7xXL7XfvcufR8K0Svfv6CREN3yDYfYIt9QiK3yjYTYLF95xB75SiP2ylcZsVu+cogj8pVCHJWvEuKwfOkREfKlRkTJlxkRJl86RMR8qRBR82VChM0XHpEhX2hElnyREWnyhUNkzBcKkTVfJETafIcjKuQ7FFEl35GIMvl2R1TMtyuiar49EWXzbY5oZpv/hibXTF2h3+s60FRKeT6+3TjMS3nrA3ZFRD8xrfY3ER1kJ+JEdBBwWGKeRAfEH1wS5WFZSDB/AAAAAElFTkSuQmCC'
			},
			search: {
				type: String,
				default: ''
			},
			parentMatch: {
				type: Boolean,
				default: false
			}
		},
		inject: {
			container: {
				type: Array,
				default: () => {
					return []
				}
			},
		},
		data() {
			return {

			};
		},
		computed: {
			match() {
				if (this.search == '') {
					return false
				}
				if (this.search && this.value && this.value.staff) {
					var reg = new RegExp(this.search, 'i');
					return reg.test(this.value.username);
				} else {
					return false
				}
			},
			/**
			 * 是否有子项命中搜索条件
			 */
			childMatch() {
				if (this.search == '') {
					return false
				}
				if (this.value.staff) {
					return someFun(this.value.staff, this.search)
				}
				return false
			}
		},
		methods: {
			/* 递归全选 */
			recursionTree(arr, val) {
				let that = this;
				console.log(arr, "=======================", val);
				arr.forEach(item => {
					item.checked = val;
				})
				this.$forceUpdate()
			},
			onClick() {
				
				if (this.value.checked) {
					this.value.checked = false
					remove(this.container, e => e.id == this.value.id)
				} else {
					
					this.value.checked = true
					if (this.mult) {
						this.container.push(this.value)
					} else {
						this.container.forEach(e => e.checked = false)
						remove(this.container, e => true)
						this.container.push(this.value)
					}
				}
				console.log(this.container, "+++++++++++++++++++++++++11111111");
				this.$emit("update:value", this.value);
				this.$emit("changeRadioArr",this.container)
				this.$forceUpdate()
			},
			openChild() {
				this.value.open = this.value.open ? false : true
				this.$forceUpdate()
			},
			/**
			 * 格式化头像
			 * @param {Object} person
			 */
			formatAvatar(person) {
				if (person.profileFile) {
					return person.profileFile
				} else {
					return '../../../static/img/def_avatar.png'
				}
			},
		},
		watch: {
			"$attrs.allChecked": {
				handler(val) {
					console.log(val,"+++++++++++++++++++++++++++++++*********************");
					// console.log(this.container,"--------------------------");
					this.container.splice(0, this.container.length);
					if (this.value.staff && this.value.staff.length) {
						this.value.staff.forEach((item, index) => {
							this.$set(this.value.staff[index], 'checked', val);
							this.$emit("changeAllChecked",val);
							// if (val) {
							// 	this.container.push(this.value.staff[index])
							// } else {
							// 	this.container.splice(0, this.container.length)
							// }
						})
					}
					this.$forceUpdate()
				}
			},
			match(nval, oval) {
				if (nval && this.search != '' && this.value.staff) {
					console.log("matchwatch: ", nval, oval, this.value);
					this.value.open = true
				} else {
					this.value.open = false
				}

			},
			childMatch(nval, oval) {
				if (nval && this.search != '' && this.value.staff) {
					this.value.open = true
				} else {
					this.value.open = false
				}
			},
			// container(nval, oval) {
			// 	if (!this.mult) {
			// 		let cid = nval.map(e => e.id)
			// 		this.value.checked = cid.indexOf(this.value.id) != -1
			// 		this.$forceUpdate()
			// 		// console.log(this.value.orgName,cid,"选中项");
			// 	}
			// }

		}

	}
</script>

<style lang="scss" scoped>
	.persion-item-l{
		margin-left: 12rpx;
	}
	.nest-item {
		width: 100%;
		height: auto;
		display: flex;
		align-items: center;
		border-bottom: 1px #f1f1f1 solid;

		.tki-tree-icon {
			width: 26rpx;
			height: 26rpx;
			margin: 16px 4px 16px 16px;
		}
	}

	.title-text {
		padding: 8px 16px;
	}

	.child-padding {
		padding-left: 22px;
	}

	.persion-item {
		height: 50px;
		border-bottom: 1px #F1F1F1 solid;
		display: flex;
		align-items: center;
		padding: 0 16px;

		.avatar {

			height: 80rpx;
			width: 80rpx;
			border-radius: 12rpx;
			background: #F1F1F1;
		}

		.name {
			flex: 1;
			padding: 4px 22px;
			align-self: center;
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
	}
</style>

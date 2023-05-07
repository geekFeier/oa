<template>
	<view>
		<template v-if="mult">
			<checkbox-group ref="cg" class="chooser" @change="checkboxChange">
				<slot v-bind:ids="ids"></slot>
			</checkbox-group>
		</template>
		<template v-else>
			<radio-group ref="rg" class="chooser" @change="checkboxChange">
				<slot v-bind:ids="ids"></slot>
			</radio-group>
		</template>
	</view>
</template>

<script>
	export default {
		name: 'uni-choose-group',
		props: {
			id: {
				type: String,
				default: 'id'
			},
			label: {
				type: String,
				default: 'name'
			},
			children: {
				type: String,
				default: 'children'
			},
			mult: {
				type: Boolean,
				default: false
			},
			
			checked: {
				type: Array,
				default: () => {
					return []
				}
			}
		},
		data() {
			return {
				index: 0,
				tlist: [],
				tempList: {},
				values: []
			};
		},
		provide() {
			return {
				multiple: this.mult,
			};
		},
		computed:{
			ids(){
				let all= this.tlist.map(e=>e.id)
				this.$emit('change',all)
				return all
			}
		},
		mounted() {
			this.$nextTick(() => {
				// console.log('v-for渲染已经完成')
			})
		},
		methods: {
			/**
			 * 行点击事件触发 item选中或取消
			 * @param {Object} item
			 */
			chooseItem(item){
				if(!this.mult){
					this.tlist=[]
				}
			   let i=	this.tlist.findIndex(e=>e.id==item.id)
			   console.log("不不不"+i)
			   if(i>-1){
				   this.tlist.splice(i,1)
			   }else{
				   this.tlist.push(item)
			   }
			},
			/**
			 * 清空选中项
			 */
			clear(){
				this.tlist=[]
			},
			/**
			 * 设置默认选中项
			 */
			setDefChoose(item){
				console.log('复选',item,this.tlist)
				if (this.mult) {
					 let i=	this.tlist.findIndex(e=>e.id==item.id)
					 if(i==-1){
						 this.tlist.push(item)
					 }
					}else{
						this.tlist=[item]
					}
			},
			
			checkboxChange(e) {
				if (this.mult) {
					if (Array.isArray(e.detail.value)) {
						this.tlist=[]
						this.tlist = e.detail.value.map(j => JSON.parse(j))
					} else {
						
						this.tlist.push(e.detail.value)  
					}
				} else {
					let vlu = JSON.parse(e.detail.value);
					if (Array.isArray(vlu)) {
						this.tlist = vlu
					} else {
						this.tlist = [vlu]
					}
				}
				// console.log('chooseGGGG', this.tlist)
			},

			/**
			 * 获取选中的值
			 */
			getChoosed() {
				console.log("选中", this.tlist)
				return this.tlist
			},

		},
		watch: {
		
		}
	}
</script>

<style lang="scss" scoped>
	.chooser {
		height: 100%;
		width: 100%;
	}
</style>

<template>
	<view class="itemBox">
		<view class="main-item" @click="selectBtn(item)">
			<view class="main-item-l">
				{{item.serial}}
			</view>
			<view class="main-item-r">
				{{item.name}}
			</view>
		</view>
		<select-item v-if="item.children" :flag="flag" v-for="(item,index) in item.children" :item="item" :key="item.id"></select-item>
	</view>
</template>

<script>
	import selectItem from "./select-item.vue"
	export default {
		name: 'select-item',
		props: {
			item: {
				default: () => {},
				type: Object
			},
			flag:{
				default:1,
				type:Number
			}
		},
		
		data() {
			return {}
		},
		methods:{
			selectBtn(item){
				if(this.flag == 2){
					this.$navigateBack(item)
				}
			}
		},
		components: {
			selectItem
		}
	}
</script>

<style scoped lang="scss">
	.main-item {
		display: flex;
		justify-content: space-between;
		border-bottom: 1px solid #EEF2FF;
		padding: 32rpx 0;
		font-size: 32rpx;
	}
</style>

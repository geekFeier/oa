<template>
	<view>
		<u-navbar :is-back="true" title="公式列表" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
			<view slot="right" style="margin-right: 25rpx;" @click="addadd()">添加</view>
		</u-navbar>
		<view class="view-list">
			<text>科目名称</text>
			<text>运算符号</text>
			<text>操作</text>
			</view>
			<view v-for="(item,index) in goodlist" :key="index" class="view-lista">
				<text>{{item.name}}</text>
				<text>{{item.operation}}</text>
				<text style="color: red;" @click="deleta(index)">删除</text>
			</view>
			<view class="bbtn" @click="listback()">
				保存
			</view>
	</view>
</template>

<script>
	import {
		mapState
	} from "vuex";
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
				id:'',
				goodlist:[]
			};
		},
		onLoad() {
		},
		methods: {
			listback(){
				if(this.goodlist.length <= 0){
					uni.showToast({
						title: "请添加公式列表",
						icon: "none"
					})
					return
				}
				let arr = []
				console.log(this.goodlist)
				this.goodlist.forEach(item=>{
					let obj = {}
					obj.kemu_id = item.kemu_id
					obj.operation = item.operation
					obj.ratevalue = item.ratevalue
					obj.rule = item.rule
					arr.push(obj)
				})
				this.$navigateBack(JSON.stringify(arr))
			},
			addadd(){
				this.$navigateTo({
					url:'./zcfzset?id=' + this.id
				}).then(res => {
					this.goodlist.push(res)
				})
			},
			//删除
			deleta(index){
				this.goodlist.splice(index, 1);
			},
		}
	}
</script>

<style lang="scss" scoped>
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

	.view-list {
		padding: 10rpx 20rpx;
		display: flex;
		align-items: center;
		text{
			background-color: #F5F5F5;
			width: 230rpx;
			line-height: 66rpx;
			text-align: center;
		}
	}
	.view-lista {
		padding: 10rpx 20rpx;
		display: flex;
		align-items: center;
		border-bottom: 1px solid #F5F5F5;
		text{
			width: 230rpx;
			text-align: center;
			line-height: 66rpx;
		}
	}
	.bbtn{
		text-align: center;
		width: 500rpx;
		height: 80rpx;
		background-color: red;
		color: #fff;
		line-height: 80rpx;
		border-radius: 40rpx;
		position: fixed;
		bottom: 88rpx;
		left: 50%;
		transform: translateX(-50%);
	}
</style>

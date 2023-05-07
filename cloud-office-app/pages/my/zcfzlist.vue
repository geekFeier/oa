<template>
	<view>
		<u-navbar :is-back="true" title="公式列表" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
			<view slot="right" style="margin-right: 25rpx;" @click="addadd()">添加</view>
		</u-navbar>
		<view class="view-list">
			<text>科目名称</text>
			<text>运算符号</text>
			<text>操作</text></view>
			<view v-for="(item,index) in goodlist" :key="index" class="view-lista">
				<text>{{item.kemu_info.name}}</text>
				<text>{{item.operator}}</text>
				<text style="color: red;" @click="deleta(item.id)">删除</text>
			</view>
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
		onLoad(e) {
			console.log(e.id)
			this.id = e.id
			this.getload()
		},
		methods: {
			getload(){
				this.$http("enterprise.report/ListOfCalculationFormulas", 
				{user_calc_cate_id:this.id}, "post").then(res => {
					console.log(res)
					if (res.data.code == 1) {
						this.goodlist = res.data.data
					}
				})
			},
			addadd(){
				this.$navigateTo({
					url:'./zcfzset?id=' + this.id
				}).then(res => {
					this.getload()
				})
			},
			//删除
			deleta(id){
				console.log(id)
				this.$http("enterprise.report/DeleteTheFormula",
				{user_cale_formula_id:id}, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "删除成功",
							icon: "none"
						})
						setTimeout(()=>{
							this.getload()
						},500)
					}
				})
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
</style>

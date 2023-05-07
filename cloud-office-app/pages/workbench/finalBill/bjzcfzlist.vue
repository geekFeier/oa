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
				<text>{{item.Course_content}}</text>
				<text style="color: red;">{{item.operation}}</text>
				<text style="color: red;" @click="deleta(item.id)">删除</text>
			</view>
			<!-- <view class="bbtn" @click="listback()">
				保存
			</view> -->
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
				id:'12',
				ida:'',
				goodlist:[]
			};
		},
		onLoad(e) {
			this.id = e.id
			console.log(this.id,"KKKKKKKK")
			this.deletaa()
		},
		methods: {
			deletaa() {
				let param = {
					prof_id:this.id
				}
				this.$http("enterprise.Date_query/TemplateInfo", param, "post").then(res => {
					if (res.data.code == 1) {
						// this.goodlist = res.data.data.rule[1].formula
						res.data.data.rule.forEach((item)=>{
							console.log(item.valuetype)
							if(item.valuetype == -1){
								this.goodlist = item.formula
								this.ida = item.id
							}
						})
					}
				})
			},
			addadd(){
				this.$navigateTo({
					url:'./bjzcfzset?id=' + this.ida
				}).then(res => {
					this.deletaa()
				})
			},
			//删除
			deleta(id){
				let param = {
					sub_id:id
				}
				this.$http("enterprise.Date_query/TemplateSubDel", param, "post").then(res => {
					if (res.data.code == 1) {
						uni.showToast({
							title: "删除成功",
							icon: "none"
						})
						this.deletaa()
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

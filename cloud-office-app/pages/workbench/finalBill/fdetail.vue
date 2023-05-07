<template>
	<view>
		<u-navbar :is-back="true" title="模板详情" :border-bottom="false" back-icon-color="#000"
			:background="background" title-color="#000" :height="55">
		</u-navbar>

		<view class="mainBox">
			<view class="one">
				<view>结转名称</view>
				<view>{{remarks}}</view>
			</view>
			<view class="two">
				<view style="font-size: 32rpx;color: red;">按公式取值内容</view>
				<view>
					<view class="two-once">
						<view>科目名称</view>
						<view>{{ansign.abstract}}</view>
					</view>
					<view class="two-once">
						<view>借贷方向</view>
						<view>
							<u-switch size="40" v-model="ansign.direction == 1 ? true :false" active-color="#13C75B"
								inactive-color="#FF253D">
							</u-switch>
						</view>
					</view>
					<view class="two-three">
						<view>公式列表</view>
						<view>
							<view class="view-list">
								<text>科目ID</text>
								<text>运算符号</text>
								<text>操作</text>
								</view>
								<view v-for="(item,index) in ansign.formula" :key="index" class="view-lista">
									<text>{{item.kemu_id}}</text>
									<text>{{item.operation}}</text>
									<text style="color: red;" @click="deleta(item.id)">删除</text>
								</view>
						</view>
					</view>
				</view>
			</view>
			<view class="three">
				<view style="font-size: 32rpx;color: red;">自动平衡</view>
				<view>
					<view class="two-once">
						<view>科目名称</view>
						<view>{{tasksign.abstract}}</view>
					</view>
					<view class="two-once">
						<view>借贷方向</view>
						<view>
							<u-switch size="40" v-model="tasksign.direction == 1 ? true :false" active-color="#13C75B"
								inactive-color="#FF253D">
							</u-switch>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				remarks:'',//名称
				credentials: [],
				tasksign:'',//自动平衡
				ansign:'',//按公式取值内容
				goodlist:[
					{
						kemu_id:'123',
						operation:'+'
					}
				],
				background: {
					backgroundColor: "#FFFFFF",
				},
			};
		},
		onLoad(e) {
			this.id = e.id
			console.log(this.id)
			this.sureBtn()
		},
		methods: {
			sureBtn() {
				let param = {
					prof_id:this.id
				}
				this.$http("enterprise.Date_query/TemplateInfo", param, "post").then(res => {
					if (res.data.code == 1) {
						this.remarks = res.data.data.name
						this.ansign = res.data.data.rule[1]
						this.tasksign = res.data.data.rule[0]
					}
				})
			},
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
						setTimeout(()=>{
							this.sureBtn()
						},1000)
					}
				})
			}
			
		}
	}
</script>

<style lang="scss" scoped>
	.mainBox {
		padding: 0 30rpx;
		.one{
			height: 88rpx;
			font-size: 34rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		.two{
			line-height: 66rpx;
			font-size: 34rpx;
			.two-once{
				height: 88rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
		}
		.three{
			line-height: 66rpx;
			font-size: 34rpx;
			.two-once{
				height: 88rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
		}
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

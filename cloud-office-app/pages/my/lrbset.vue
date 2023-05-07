<template>
	<view>
		<u-navbar :is-back="true" title="公式添加" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="view-list" @click="selectname()">
			<view>科目名称:</view>
			<view class="kemu">{{account}}</view>
			<u-icon class="item-hd-r" name="arrow-right" color="#7A7C94" size="28"></u-icon>
		</view>
		<view class="view-list" @click="add()">
			<view>运算符号:</view>
			<view class="kemu">{{addand}}</view>
			<u-icon class="item-hd-r" name="arrow-right" color="#7A7C94" size="28"></u-icon>
		</view>
		<view class="view-list" @click="arules()">
			<view>取数规则:</view>
			<view class="kemu">{{fseh}}</view>
			<u-icon class="item-hd-r" name="arrow-right" color="#7A7C94" size="28"></u-icon>
		</view>
		<button type="default" class="sure_btn" @click="sureBtn">添加</button>
		<u-select v-model="show" mode="single-column" :list="list" @confirm="confirm"></u-select>
		<u-select v-model="isshow" mode="single-column" :list="lista" @confirm="confirma"></u-select>
	</view>
</template>

<script>
	import {
		mapState
	} from "vuex";
	import {
		url_config,
		img_url
	} from "@/config/config.js"
	export default {
		data() {
			return {
				id:'',
				background: {
					backgroundColor: "#FFFFFF",
				},
				account:'请选择科目',
				addand:'请选择运算符号',
				kemu_id:'',
				show:false,
				isshow:false,
				operator:'',
				addan:'',
				fseh:'请选择取数规则',//发生额
				list:[
					{
						value: '加',
						label: '+'
					},
					{
						value: '减',
						label: '-'
					}
				],
				lista:[
					{
						value: 'fse',
						label: '发生额'
					},
					{
						value: 'dffse',
						label: '贷方发生额'
					}
				],
				isfseh:''
			};
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo
			})
		},
		onLoad(e) {
			console.log(e.id)
			this.id = e.id
		},
		methods: {
			selectname(){
					this.$navigateTo({
						url: "./kemulist"
					}).then(res => {
						console.log(res,">>>>>>>>")
						this.kemu_id = res.id;
						this.account = res.name;
						
					})
			},
			add(){
				this.show = true
			},
			arules(){
				this.isshow = true
			},
			confirm(e){
				console.log(e[0].label)
				this.addand = e[0].label
			},
			confirma(e){
				console.log(e[0].label)
				this.fseh = e[0].label
				this.isfseh = e[0].value
			},
			//添加
			sureBtn() {
				if(this.account == '请选择科目'){
					uni.showToast({
						title: "请选择科目",
						icon: "none"
					})
					return
				}
				if(this.addand == '请选择运算符号'){
					uni.showToast({
						title: "请选择运算符号",
						icon: "none"
					})
					return
				}
				if(this.fseh == '请选择取数规则'){
					uni.showToast({
						title: "请选择取数规则",
						icon: "none"
					})
					return
				}
				this.$http("enterprise.report/IncomeStatementToCalculate", {kemu_id:this.kemu_id,
					operator:this.addand,
					calcfieldrule:this.isfseh,
					user_calc_cate_id:this.id}, "post").then(res => {
					console.log(res)
					if (res.data.code == 1) {
						uni.showToast({
							title: "添加成功",
							icon: "none"
						})
						setTimeout(()=>{
							this.$navigateBack()
						},1000)
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
		.kemu {
			height: 66rpx;
			line-height: 66rpx;
			margin-right: 30rpx;
			text-align: right;
			flex: 1;
		}
	}
</style>

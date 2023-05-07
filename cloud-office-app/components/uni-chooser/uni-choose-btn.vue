<template>
	<view>
		<template v-if="multiple">
			<checkbox :value="JSON.stringify(value)"  :checked="checked||choosed"></checkbox>
		</template>
		<template v-else>
			<radio :value="JSON.stringify(value)"  :checked="checked||choosed">
			</radio>
		</template>
	</view>
</template>

<script>
	export default {
		name: 'uni-choose-btn',
		inject: {
			multiple: {
				type: Boolean,
				default: false
			},
		},
		props: {
			size: {
				type: String,
				default: () => {
					return '40px'
				}
			},
			id: '',
			value: {
				type: Object,
				default:()=> {
					return {}
				}
			},
			confirmColor: {
				type: String,
				default: "#007aff"
			},
			checked:{
				type:Boolean,
				default:false
			},
			ids:{
				type: Array,
				default:()=> {
					return []
				}
			}
		},
		data() {
			return {
			};
		},
		mounted() {

		},
		methods: {
			
		},
		computed: {
			choosed(){
				if(this.ids){
					return this.ids.indexOf(this.value.id)!=-1
				}
				return false
			},
			vid(){
				return this.value.id 
			},
		},
		watch: {
		}
	}
</script>

<style lang="scss" scoped>
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

	.checkbox {
		position: relative;

		:before {
			font-family: "cuIcon";
			content: "\e645";
			position: absolute;
			color: #ffffff !important;
			top: 0%;
			left: 0;
			right: 0;
			bottom: 0;
			text-align: center;
			line-height: 100%;
			padding-top: 2px;
		}

		.tki-tree-check-yes-b {
			width: 100%;
			height: 100%;
		}
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
</style>

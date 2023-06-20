<template>
	<view class="detailBox">
		<u-navbar :is-back="true" :title="`${currentJob}${currentName}`" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="chat">
				<view class="list" :style="{ height: chatListHeight + 'px' }">
					<cell :ref="'item'+index" v-for="(item, index) in chatList" :key="index">
						<view :class="['item', item.type]">
							<image class="avatar" :src="item.avatar"></image>
							<text class="content" style="max-width: 500rpx;">{{ item.content }}</text>
						</view>
					</cell>
				</view>
			<view class="bottomAction">
				<view class="main flex" :style="{ height: bottomActionHeight + 'px' }">
					<input placeholder="请输入..." class="textInput" type="text" :value="inputText" cursor-spacing="10"
						:confirm-hold="true" :adjust-position="false" confirm-type="send" @input="getInputText"
						@keyboardheightchange="changeKeyboardHeight" @confirm="confirmInput" />
						<button type="default" class="sure_btn" @click="confirmInput">发送</button>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	import {
		mapState,
		mapGetters
	} from "vuex"
	const dom = uni.requireNativePlugin('dom');
	export default {
		data() {
			return {
				limit: 10,
				page: 1,
				background: {
					backgroundColor: "#FFFFFF",
				},
				currentId: '',
				currentJob:'',
				currentName:'',
				systemInfo: {}, // 系统参数 
				bottomActionHeight: 54, // 底部输入框原始高度
				chatListHeight: 0, // 聊天列表高度
				inputText: '', // 输入框内容
				chatList: [] // 聊天列表
			};
		},
		onPullDownRefresh() {
			this.page++;
			this.getData();
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		created() {
			let _self = this;
			setTimeout(() => {
				_self.systemInfo = uni.getSystemInfoSync();
				_self.chatListHeight = _self.systemInfo.windowHeight - _self.bottomActionHeight - 55 - 25;
			}, 1);
		},
		onLoad(e) {
			this.currentId = e.id;
			this.currentJob = e.job
			this.currentName = e.name
			this.getData()
			this.init();
		},
		computed: {
			...mapState({
				userInfo: state => state.user.userInfo
			})
		},
		methods: {
			init() {
				this.scrollBottom();
			},
			getInputText(e) {
				this.inputText = e.detail.value;
			},
			confirmInput(e) {
				if (this.inputText || !e.detail.value ) return uni.showToast({
					title: '内容不能为空',
					duration: 1500,
					position: 'bottom'
				});
				let params = {
					to_user_id: this.currentId,
					content:this.inputText ||  e.detail.value
				}

				this.$http("enterprise.message/addMessage", params, "post").then(res => {
					this.chatList.push({
						avatar: '../../../static/avatar.png',
						content: this.inputText || e.detail.value,
						type: 'right'
					});
					this.inputText = '';
					this.scrollBottom();
				})
			},
			changeKeyboardHeight(e) {
				this.chatListHeight = this.systemInfo.windowHeight - this.bottomActionHeight - 55 - 25 - e.detail.height;
				this.scrollBottom();
			},
			scrollBottom() {
				setTimeout(() => {
					const listLen = this.chatList.length;
					
					if(listLen > 0) {
						const el = this.$refs[`item${ listLen -1 }`][0];
						
						dom.scrollToElement(el, { offset: 0, animated: true });
						console.log('dom')
						console.log(dom)
					}
				}, 100);
			},
			getData() {
				let params = {
					get_user_id: this.currentId,
					page: this.page,
					limit: this.limit
				}
				this.$http("enterprise.message/record", params, "get").then(res => {
					let data = res.data.data.rows.reverse()
					data.forEach(item => {

						if (item.own == 1) {
							item.type = 'right'
							item.avatar= '../../../static/avatar.png'
						} else {
							item.type = 'left'
							item.avatar= '../../../static/avatar.png'
						}
					})
					this.chatList.unshift(...data)
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.sure_btn {
		background: #4396F7;
		border-radius: 10rpx;
		width: 110rpx;
		color: #fff;
		font-size: 26rpx;
		white-space:nowrap;
		margin-left:10rpx;
	}
	.list .item .avatar {
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		overflow: hidden;
		font-size: 36rpx;
		line-height: 80rpx;
		color: #fff;
		text-align: center;
		background-color: #4396F7;
		border: 1px solid #4396F7;
		z-index: 0;
	}

	.list {
		width: 100vw;
		background-color: #F3F5F8;
		overflow-y: auto;
	}

	.list .item {
		padding: 15rpx 30rpx;
		display: flex;
	}

	.list .item .content {
		line-height: 40rpx;
		font-size: 28rpx;
		padding: 20rpx;
	}

	.list .item.left {
		flex-direction: row;
	}

	.list .item.right {
		flex-direction: row-reverse;
	}

	.list .item.left .content {
		color: #333333;
		background-color: #fff;
		border-radius: 2rpx 20rpx 20rpx 20rpx;
		margin-left: 20rpx;
	}

	.list .item.right .content {
		color: #FFFFFF;
		background-color: #4396F7;
		border-radius:  20rpx 2rpx 20rpx 20rpx;
		text-align: right;
		margin-right: 20rpx;
	}

	.bottomAction {
		position: relative;
		z-index: 1;
		width: 750rpx;
		background-color: #FFFFFF;
	}

	.bottomAction .main {
		padding:0 15rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-around;
	}

	.bottomAction .main .textInput {
		 
		flex:1;
		height: 34px;
		line-height: 34px;
		font-size: 28rpx;
		background-color: #F3F5F8;
		border-radius: 10rpx;
		padding: 0 24rpx;
	}
</style>
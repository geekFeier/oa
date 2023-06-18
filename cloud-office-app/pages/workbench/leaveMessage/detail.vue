<template>
	<view class="detailBox">
		<u-navbar :is-back="true" title="留言详情" :border-bottom="false" back-icon-color="#000" :background="background"
			title-color="#000" :height="55">
		</u-navbar>
		<view class="chat">
			<list class="list" :style="{ height: chatListHeight + 'px' }">
				<cell :ref="'item'+index" v-for="(item, index) in chatList" :key="index">
					<view :class="['item', item.type]">
						<image class="avatar" :src="item.avatar"></image>
						<text class="content" style="max-width: 500rpx;">{{ item.content }}</text>
					</view>
				</cell>
			</list>
			<view class="bottomAction">
				<view class="main" :style="{ height: bottomActionHeight + 'px' }">
					<input 
					class="textInput" 
					type="text" 
					:value="inputText" 
					cursor-spacing="10" 
					:confirm-hold="true" 
					:adjust-position="true"
					confirm-type="send" 
					@input="getInputText"
					@keyboardheightchange="changeKeyboardHeight"
					@confirm="confirmInput" />
				</view> 
			</view>
		</view>

	</view>
</template>

<script>
	const dom = uni.requireNativePlugin('dom');
	export default {
		data() {
			return {
				background: {
					backgroundColor: "#FFFFFF",
				},
				currentId: '', 
				systemInfo: {},	// 系统参数
				keyboardConfig: {	// 键盘参数
					height: 0	// 键盘高度
				},
				bottomActionHeight: 54,	// 底部输入框原始高度
				chatListHeight: 0,	// 聊天列表高度
				inputText: '',	// 输入框内容
				chatList: []	// 聊天列表

			};
		},
		created() {
			let _self = this;
			
			setTimeout(() => {
				_self.systemInfo = uni.getSystemInfoSync();
				_self.keyboardConfig = uni.getStorageSync('keyboardConfig');
				_self.chatListHeight = _self.systemInfo.windowHeight - _self.bottomActionHeight;
			}, 1);
		},
		onLoad(e) {
			this.currentId = e.id;
			this.init();
		},
		methods: {
			init() {
				this.scrollBottom();
			},
			/**
			 * @description 输入框聚焦
			 * @param {Object} e 键盘参数
			 */ 
			/**
			 * @description 键盘输入
			 * @param {Object} e 输入框参数
			 */
			getInputText(e) {
				this.inputText = e.detail.value;
			},
			/**
			 * @description 键盘高度发生变化
			 * @param {Object} e 键盘参数
			 */
			changeKeyboardHeight(e) {
				 
			 
			},
			/**
			 * @description 完成输入
			 * @param {Object} e 输入框参数
			 */
			confirmInput(e) {
				if(!e.detail.value) return uni.showToast({ title: '内容不能为空', duration: 1500, position: 'bottom' });
				
				this.chatList.push({
					avatar: '/static/avatar.png',
					content: e.detail.value,
					type: 'left'
				});
				
				this.chatList.push({
					avatar: '/static/avatar.png',
					content: e.detail.value,
					type: 'right'
				});
				
				this.inputText = '';
				this.scrollBottom();
			},
			/**
			 * @description 打开表情符号
			 */
		 
			/**
			 * @description 选择表情符号
			 * @param {String} item 选择的内容
			 */ 
			/**
			 * @description 滚动至底部
			 */
			scrollBottom() {
				setTimeout(() => {
					const listLen = this.chatList.length;
					
					if(listLen > 0) {
						const el = this.$refs[`item${ listLen -1 }`][0];
						
						dom.scrollToElement(el, { offset: 0, animated: false });
					}
				}, 30);
			},
			getData() {
				let params = {
					id: this.currentId
				}
				this.$http("enterprise.User_todo/Dateils", params, "get").then(res => {
					this.chatList = [{
						content: '啦啦啦',
						type: 'left'
					}]
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	 
	.list .item .avatar {
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		overflow: hidden;
		font-size:36rpx;
		line-height: 80rpx;
		color:#fff;
		text-align: center;
		background-color: #2472FF;
	}

	.list {
		width: 100vw;
		background-color: #F3F5F8;
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
		background-color: #F3F5F8;
		border-radius: 2rpx 20rpx 20rpx 20rpx;
		margin-left: 20rpx;
	}
	.list .item.right .content {
		color: #FFFFFF;
		background-color: #2472FF;
		border-radius: 2rpx 20rpx 20rpx 20rpx;
		text-align: right;
		margin-right: 20rpx;
	}
	.bottomAction {
		width: 750rpx;
		background-color: #FFFFFF;
	}
	.bottomAction .main {
		flex-direction: row;
		align-items: center;
		justify-content: center;
	}
	.bottomAction .main .textInput {
		width: 90vw;
		height: 68rpx;
		line-height: 68rpx;
		font-size: 28rpx;
		background-color: #F3F5F8;
		border-radius: 10rpx;
		padding: 0 24rpx;
	} 
</style>
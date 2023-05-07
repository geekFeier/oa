import App from './App'

// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif

import uView from "uview-ui";
Vue.use(uView);

import sotre from "./store/index.js"
Vue.prototype.$store = sotre;

import {
	request
} from "./utils/http.js";
Vue.prototype.$http = request;

import htzImageUpload from "@/components/htz-image-upload/htz-image-upload.vue";
Vue.component("htzImageUpload", htzImageUpload)

import uniPopup from './components/uni-popup/uni-popups.vue';
Vue.component('uni-popup', uniPopup);

var callbacks = {}
// 4. 添加实例方法
Vue.prototype.$navigateTo = function(opt) {
	// 逻辑...
	var pages = getCurrentPages()
	var curr = pages[pages.length - 1]
	return new Promise((resolve, reject) => {
		if (!opt) {
			reject(new Error("参数错误@" + opt))
		} else {
			let url = curr.route
			// uni.$once(url,(res)=>{
			// 	console.log("@once url=",url,res,resolve)
			// 	if(resolve){
			// 		resolve(res)
			// 	}
			// })
			
			callbacks[url] = resolve
			uni.navigateTo(opt)
		}
	})
}
Vue.prototype.$navigateBack = function(data, opt) {
	let option = {
		delta: 1,
		animationType: "pop-out",
		animationDuration: 300
	}
	if (opt) {
		option = Object.assign(option, opt)
	} 
	uni.navigateBack(option) //注意这行代码位置会影响 prepage的值
	setTimeout(() => {
		var pages = getCurrentPages()
		var prepage = pages[pages.length - 1]
		if (prepage) {
			var url = prepage.route
			// uni.$emit("update",data)
			// uni.$emit('/'+url,data)
			if (callbacks[url]) {
				callbacks[url](data)
				// setTimeout(() => {
				// 	callbacks['/' + url](data)
				// }, 100)
			}
		}
	}, 500)
}

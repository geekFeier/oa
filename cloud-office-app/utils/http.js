import {
	url_config
} from '../config/config.js'
var qs = require('qs');
//几个参数分别是，url，参数，方法，默认是get，剩下的两个时请求头的信息，一个时类型，一个是存储token的
function request(url, data, method = 'get', contentType = 1, Authorization) {
	var baseUrl = url_config;
	if (url && url.startsWith('http')) {
		baseUrl = ''
	}
	console.log("url", baseUrl, url);
	let tokenData = ""
	if (!Authorization) { //不是登陆，不用token
		tokenData = uni.getStorageSync('token') || "";
	}
	let header = {
		'content-type': contentType === 1 ? 'application/json' : 'application/x-www-form-urlencoded',
		'token': tokenData ? tokenData : "",
	}
	if (Authorization) { //登陆的时候，写死的是dGVzdDp0ZXN0（这个请求头默认是用access_token，如果有的话，用有的）
		header["token"] = "" //登陆的时候，写死的是dGVzdDp0ZXN0（这个请求头默认是用）
	}
	for (let property in data) {
		if (data[property] == null) {
			delete data[property]
		}
	}
	var params = data
	if (method == 'get') {
		//params=qs.stringify(data)
	}
	// console.log("请求参数",params)
	return new Promise((resolve, reject) => {
		uni.request({
			url: baseUrl + url,
			data: params,
			method,
			header,
			sslVerify: false,
			success: (res) => {
				console.log("url=", method, url, params, res);
				console.log(res.statusCode, res.data.code, "请求库的接口状态！！！！！！！！！！！！！！！！！！！！！");
				if (res.statusCode == 200) {
					resolve(res)
				} else if (res.statusCode == 405) {
					uni.showToast({
						icon: 'none',
						title: '请求方法错误',
						duration: 1500
					});
				} else if (res.statusCode == 401) {
					uni.showToast({
						icon: 'none',
						title: '未登录或登录状态已超时',
						duration: 1500
					});
					uni.clearStorage()
					uni.redirectTo({
						url: "/pages/login/index"
					})
				} else {}

				if (res.data.code == 20006) {
					let pages = getCurrentPages();
					let prevPage = pages[pages.length - 1];
					if (prevPage.route !== 'pages/tabbar/tab-1/index') {
						uni.reLaunch({
							url: "/pages/tabbar/tab-1/index"
						})
					}

				}

				if (res.data.code == 20005) {
					let pages = getCurrentPages();
					let prevPage = pages[pages.length - 1];
					if (prevPage.route !== 'pages/login/authCentreSigning') {
						setTimeout(() => {
							uni.reLaunch({
								url: "/pages/login/authCentreSigning"
							})
						}, 500)

					}

				}
				
				
				
				if (res.data.code == 10001) {
					let pages = getCurrentPages();
					let prevPage = pages[pages.length - 1];
					if (prevPage.route !== 'pages/my/setting/index') {
						setTimeout(() => {
							uni.navigateTo({
								url: "/pages/my/setting/index?flag=2"
							})
						}, 500)
				
					}
				
				}

				if (res.data.code == 401) {
					uni.showToast({
						icon: 'none',
						title: '未登录或登录状态已超时',
						duration: 1500
					});
					uni.redirectTo({
						url: "/pages/login/index"
					})
					uni.clearStorage();
				}

				if (res.data.code != 1) {
					uni.showToast({
						icon: 'none',
						title: res.data.msg,
						duration: 1500
					});
					// reject(res)//reject  改成resolve  因为此时已经请求成功了 失败的状态由具体页面根据code判断
					if (res.data.code == 1) {
						resolve(res)
					} else {
						reject(res)
					}

				}
			},
			fail: (err) => {
				console.log('eee', err);
				// uni.showToast({
				// 	icon: 'none',
				// 	title: err.errMsg,
				// 	duration: 1500
				// });
				// resolve(err)
				reject(err)
			}
		})
	})
}
export {
	request
}

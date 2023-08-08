let url_config = "";
let img_url = "";
console.log(process.env.NODE_ENV)

if (process.env.NODE_ENV === 'development') {
	// url_config = 'http://ixi0r09f.dongtaiyuming.net/api/';
	// img_url = "http://ixi0r09f.dongtaiyuming.net";
	// url_config = 'http://cloud.yunzanet.com/api/';
	// img_url = "http://cloud.yunzanet.com/"
	// url_config = 'http://ysbg.qingyukeji.online/api/';
	// img_url = "http://ysbg.qingyukeji.online/"
	// url_config = 'http://39.96.177.195/yun/public/api/'
	// img_url = "http://39.96.177.195/yun/public/"
	url_config = 'http://www.moyizu.cn/api/'
	img_url = "http://www.moyizu.cn/"
} else {
	// url_config = 'http://ysbg.qingyukeji.online/api/';
	// img_url = "http://ysbg.qingyukeji.online/"
	// url_config = 'http://cloud.yunzanet.com/api/';
	// img_url = "http://cloud.yunzanet.com/"
	// url_config = 'http://yunshangoa.yunzanet.com/api/';
	// img_url = "http://yunshangoa.yunzanet.com"
	// url_config = 'http://39.96.177.195/yun/public/api/'
	// img_url = "http://39.96.177.195/yun/public/"
	url_config = 'http://www.moyizu.cn/api/'
	img_url = "http://www.moyizu.cn/"

}
export {
	url_config,
	img_url
}
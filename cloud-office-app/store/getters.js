// +----------------------------------------------------------------------
// | jufeiniu [ 万物之中 - 希望最美 ]
// +----------------------------------------------------------------------
// | Copyright (c) 2022 https://jufeiniu.com/ All rights reserved.
// +----------------------------------------------------------------------
// | Author: 巨臻晞-巨肥牛 <jufeiniu@163.com>
// +----------------------------------------------------------------------
// | Personal site: https://jufeiniu.com/
// +----------------------------------------------------------------------
export default {
	token: state => state.user.token,
	userInfo: state => state.user.userInfo,
	enterprise: state => state.user.enterprise,
	personType:state => state.user.personType,
};
// export default {
//   token: state => 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrYWlmYS5jcm1lYi5uZXQiLCJhdWQiOiJrYWlmYS5jcm1lYi5uZXQiLCJpYXQiOjE1NzcwODM1MzQsIm5iZiI6MTU3NzA4MzUzNCwiZXhwIjoxNTc3MDk0MzM0LCJqdGkiOnsiaWQiOjExMCwidHlwZSI6InVzZXIifX0.U-i1pbdRjyXI1gr79Uq2XBPZ89T8f5Ai9jwrR8woTwE',
//   isLogin: state => true,
//   backgroundColor: state => state.app.backgroundColor,
//   userInfo: state => state.app.userInfo || {}
// };

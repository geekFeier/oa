<template>
  <view>
    <u-navbar :is-back="true" title="修改信息" :border-bottom="false" back-icon-color="#000" :background="background" title-color="#000" :height="55">
    </u-navbar>
    <view class="mainBox">
      <u-form ref="uForm">
        <u-form-item label="手机号" label-width="150">
          <u-input v-model="formData.mobile" type="input" placeholder="请输入手机号" />
        </u-form-item>
      </u-form>
      <u-form ref="uForm">
        <u-form-item label="昵称" label-width="150">
          <u-input v-model="formData.username" type="input" placeholder="请输入昵称" />
        </u-form-item>
      </u-form>
      <view class="form-item">
        <view class="form-item-label">头像</view>
        <view v-if="!imageData" class="img-div" @click="uploadImg()">
          <u-icon name="plus" color="#B5BFDA" size="80">
          </u-icon>
        </view>
        <image style="width: 280rpx;height: 280rpx;border-radius: 24rpx;margin-left: 50%; transform: translateX(-50%);margin-top: 32rpx;" v-if="imageData" @click="uploadImg" :src="imageData" mode=""></image>
      </view>
    </view>
    <button type="default" class="sure_btn" @click="sureBtn">保存</button>

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
      background: {
        backgroundColor: "#FFFFFF",
      },
      imageData: "",
      formData: {
        mobile: '',
        username: "",
      }
    };
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo
    })
  },
  onLoad() {
    console.log(this.userInfo.avatar)
    this.imageData = this.userInfo.avatar;//this.userInfo.avatar.indexOf("data:image/") >= 0 ? "" :
    this.formData.mobile = this.userInfo.mobile;
    this.formData.username = this.userInfo.username;
  },
  methods: {
    getUserInfo() {
      this.$http("/User/getUser", {}, "post").then(res => {
        if (res.data.code == 1) {
          uni.setStorageSync('userInfo', res.data.data)
          this.$store.dispatch("user/GET_USER_INFO", res.data.data);

        }
      })
    },
    sureBtn() {
      if ((this.imageData && this.imageData.length) && this.formData.username && this.formData.mobile) {
        this.formData.avatar = this.imageData;
        this.$http("User/saveUser", this.formData, "post").then(res => {
          if (res.data.code == 1) {

            if (this.userInfo.mobile !== this.formData.mobile) {
              uni.showToast({
                title: "修改成功,请重新登录",
                icon: "none"
              })
              this.$store.commit('user/GET_TOKEN', {})
              this.$store.commit('user/GET_USER_INFO', {})
              uni.clearStorage();
              setTimeout(() => {
                uni.reLaunch({
                  url: "/pages/login/index"
                })
              }, 500)
            } else {
              uni.showToast({
                title: "修改成功",
                icon: "none"
              })
              this.getUserInfo();
            }
          }
        })
      } else {
        uni.showToast({
          title: "请填写信息!",
          icon: "none"
        })
      }

    },
    uploadImg() {
      uni.chooseImage({
        count: 1,
        success: (res) => {
          console.log('上传图片 success')
          let tempFilePath = res.tempFilePaths[0];
          this.uploadImgs(tempFilePath)
        },
        fail: (err) => {
          console.log('上传图片 fail')
          console.log(err)
        }
      })
    },
    uploadImgs(file) {
      let vm = this
      uni.showLoading({
        title: "上传中"
      })
      uni.uploadFile({
        url: url_config + "/Common/upload",
        name: "file",
        filePath: file,
        header: {
          token: uni.getStorageSync("token") || ""
        },
        success: (res) => {
          let _res = JSON.parse(res.data);
          if (_res.code == 1) {
            vm.imageData = _res.data.fullurl;
            uni.hideLoading()
            uni.showToast({
              title: "上传成功",
            })
          } else {
            uni.hideLoading()
            uni.showToast({
              title: "上传失败",
              icon: "none"
            })
          }
          // promiseArr.push();
        },
        fail: (err) => {
          uni.hideLoading()
        }
      })

    },

  }
}
</script>

<style lang="scss" scoped>
.mainBox {
}

.sure_btn {
  background: #4396f7;
  border-radius: 49px;
  width: 90%;
  color: #fff;
  position: fixed;
  bottom: 32rpx;
  left: 50%;
  transform: translateX(-50%);
}

/deep/ .uni-input-placeholder {
  color: rgb(192, 196, 204);
  font-size: 30rpx;
  text-align: right;
}

/deep/ .uni-input-input {
  font-size: 30rpx;
  text-align: right;
}

/deep/ .u-form-item--left__content__label,
.form-item-label {
  color: #7a7c94 !important;
}

.img-div {
  width: 280rpx;
  height: 280rpx;
  border-radius: 24rpx;
  background: rgba(251, 252, 255, 0.4);
  border: 1px solid #b5bfda;
  line-height: 280rpx;
  text-align: center;
  margin: 0 auto;
  margin-top: 32rpx;
}

/deep/ .u-form-item,
.form-item {
  padding: 14rpx 32rpx;
  font-size: 30rpx;
}
</style>
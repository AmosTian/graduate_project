// index.js
// 获取应用实例
const app = getApp()

Page({

  checkLogin(){
    const _this = this
    wx.checkSession({
      success: (res) => {
        // session_key 未过期，并且在本生命周期一直有效
        wx.showToast({
          title: '处于登录状态',
          icon: 'success',
          duration: 2000
          });
      },
      fail() {
        wx.showToast({
          title: '登录已失效!',
          icon: 'none',
          duration: 2000
        });
        _this.login() // 重新登录
        }
    })
  },

  login(){
    wx.login({
      success(res){
        console.log(res)
        if(res.code){
          //发送临时凭证code，向服务端请求用户标识
          wx.request({
            url: 'http://127.0.0.1:9091/wx/login',
            method:"POST",
            header:{
              "content-type":"application/json"
            },
            data: {
              code: res.code
            },
            success(res){
              console.log(res)
              if(res.data.status == 200){
                wx.setStorage({
                  key: 'TICKET',
                  data: res.data.data
                });
                wx.showToast({
                  title: '登录成功',
                  icon: 'success',
                  duration: 2000
                });
              }else{
                wx.removeStorage({
                  key: 'TICKET'
                });
                wx.showToast({
                  title: '登录失败!',
                  icon: 'none',
                  duration: 2000
                })
              }
            }
          })
        }
      }
    })
  }
})

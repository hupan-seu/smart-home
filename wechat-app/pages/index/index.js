//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    sessionId: '123',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    
    // 登录，获取code，用code换取sessionId
    wx.login({
      success (res) {
        if (res.code) {
          wx.request({
            url: 'https://wechat.ioman.top/api/v1/login',
            method: 'POST',
            data: {
              code: res.code
            },
            success (res) {
              app.globalData.sessionId = res.data
              console.log(res.data)
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })

    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },

  switch1Change(e){
    console.log('switch1: ' + e.detail.value)
    wx.request({
      url: 'https://wechat.ioman.top/api/v1/device',
      method: 'POST',
      data: {
        sessionId: app.globalData.sessionId,
        deviceId: "switch1",
        status: e.detail.value
      }
    })
  },

  switch2Change(e){
    console.log('switch1: ' + e.detail.value)
    wx.request({
      url: 'https://wechat.ioman.top/api/v1/device',
      method: 'POST',
      data: {
        sessionId: app.globalData.sessionId,
        deviceId: "switch2",
        status: e.detail.value
      }
    })
  }
})

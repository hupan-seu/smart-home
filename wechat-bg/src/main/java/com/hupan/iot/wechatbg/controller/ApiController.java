package com.hupan.iot.wechatbg.controller;

import com.hupan.iot.wechatbg.domain.DeviceCmd;
import com.hupan.iot.wechatbg.domain.LoginReq;
import com.hupan.iot.wechatbg.domain.LoginResp;
import com.hupan.iot.wechatbg.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private ApiService apiService;
    /**
     * 微信小程序登录
     *
     * @param req
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginReq req) {

        log.info("login code: {}", req.getCode());

        return apiService.login(req.getCode());
    }

    @PostMapping(value = "/device")
    public void deviceCmd(@RequestBody DeviceCmd deviceCmd) {

        log.info("deviceId: {}", deviceCmd.getDeviceId());
        log.info("sessionId: {}", deviceCmd.getSessionId());
        log.info("status: {}", deviceCmd.getStatus());

        apiService.deviceCmd(deviceCmd.getSessionId(), deviceCmd.getDeviceId(), deviceCmd.getStatus());
    }
}

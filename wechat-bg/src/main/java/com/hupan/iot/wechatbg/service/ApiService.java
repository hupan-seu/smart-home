package com.hupan.iot.wechatbg.service;

public interface ApiService {

    String login(String code);

    void deviceCmd(String sessionId, String deviceId, boolean status);
}

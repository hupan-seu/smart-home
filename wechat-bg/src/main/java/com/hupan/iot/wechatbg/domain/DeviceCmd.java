package com.hupan.iot.wechatbg.domain;

import lombok.Data;

@Data
public class DeviceCmd {

    private String sessionId;
    private String deviceId;
    private Boolean status;
}

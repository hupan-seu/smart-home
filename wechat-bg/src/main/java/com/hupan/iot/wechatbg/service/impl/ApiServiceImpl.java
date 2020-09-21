package com.hupan.iot.wechatbg.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hupan.iot.wechatbg.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ApiServiceImpl implements ApiService {

    private static final JsonParser PARSER = new JsonParser();

    //
    private static final String WECHAT_BASE = "https://api.weixin.qq.com";
    private static final String WECHAT_JS2SESSION = WECHAT_BASE + "/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    // emq 地址
    private static final String EMQ_PUBLISH = "http://localhost:18080/api/v2/mqtt/publish";
    private static final String EMQ_HTTP_BODY = "{\"topic\":\"home/hupan/%s\",\"payload\":\"{\\\"state\\\":\\\"%s\\\"}\",\"qos\":0,\"retain\":false,\"client_id\":\"mqttjs_722b4d8123\"}";

    // emq认证
    @Value("${wechatbg.emq.host-key}")
    private String hostKey;

    // wechat 认证
    @Value("${wechatbg.app-id}")
    private String appId;

    @Value("${wechatbg.app-secret}")
    private String appSecret;

    // openId和family的对应关系
    // 当前 openIdSet，将来 openId和family的对应关系 Map<String, String> userFamilyMap = new HashMap<>();
    private static Set<String> userSet = new HashSet<>(Arrays.asList(
            "oAjcQ5dbGr8XsR0Sc-DPZPcSVbWM",
            "oAjcQ5XQ6SaKMDjgFHpOl_ONSFmA",
            "oAjcQ5ed5yXrBgz26r72RB1I-ii4"));

    // openId和sessionId对应关系
    private Map<String, String> userSessionMap = new HashMap<>();

    @Override
    public String login(String code){

        // 到微信后台校验身份，获取用户信息
        String openId = getOpenId(code);
        log.info("openId: {}", openId);

        // 将用户信息与session绑定
        String sessionId = UUID.randomUUID().toString();
        if(userSet.contains(openId)){
            userSessionMap.put(openId, sessionId);
        }
        return sessionId;
    }

    @Override
    public void deviceCmd(String sessionId, String deviceId, boolean status){

        userSessionMap.forEach((k, v)->{
            if(v.equals(sessionId)){
                log.info("sendCmd: {}", k);
                sendCmd(deviceId, status);
            }
        });
    }

    private void sendCmd(String deviceId, boolean status){
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(EMQ_PUBLISH);

            httpPost.setHeader("Authorization","Basic " + hostKey);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");

            StringEntity stringEntity = new StringEntity(String.format(EMQ_HTTP_BODY, deviceId, status?"on":"off"));
            httpPost.setEntity(stringEntity);

            httpClient.execute(httpPost);
        } catch (Exception e){
            log.error("exception when get openid: {}, {}", e.getMessage(), e.getStackTrace());
        }
    }

    private String getOpenId(String code){
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet(String.format(WECHAT_JS2SESSION, appId, appSecret, code));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String res = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = PARSER.parse(res).getAsJsonObject();
            if(jsonObject.has("openid")){
                return jsonObject.get("openid").getAsString();
            }
        } catch (Exception e){
            log.error("exception when get openid: {}, {}", e.getMessage(), e.getStackTrace());
        }

        return "";
    }
}

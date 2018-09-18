package com.tanjin.framework.base.wechat.vo;

/**
 * 类说明
 *
 * @author fish
 * @create 2017/8/10
 **/
public class WechatShareJSVO {

    private String appid;

    private String timestamp;

    private String signature;

    private String nonceStr;

    private String url;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

}

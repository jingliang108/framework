package com.tanjin.framework.base.wechat.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tanjin.framework.base.common.utils.EmptyUtil;
import com.tanjin.framework.base.common.utils.HttpRequester;
import com.tanjin.framework.base.common.utils.HttpRespons;
import com.tanjin.framework.base.common.utils.LogFormatUtil;
import com.tanjin.framework.base.common.utils.Sha1Util;
import com.tanjin.framework.base.database.redis.RedisManager;
import com.tanjin.framework.base.wechat.constant.WechatConstants;
import com.tanjin.framework.base.wechat.vo.WechatOpenIdVo;
import com.tanjin.framework.base.wechat.vo.WechatShareJSVO;

/**
 * 类说明
 *
 * @author fish
 * @create 2017/7/18
 **/
public class WechatUtil {

    private static Logger baseLog = LoggerFactory.getLogger("baseLog");


    public static String getWeixinToken(RedisManager redisManager, String appid, String secret) {
        try {
            String accessToken = redisManager.getStringValueByKey(WechatConstants.WECHAT_ACCESS_TOKEN);
            // baseLog.info("redisManager - accessToken:" + accessToken);
            if (EmptyUtil.isNotEmpty(accessToken)) {
                return accessToken;
            }
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
            baseLog.info(LogFormatUtil.getActionFormat("请求accessToken的请求URL:" + url));
            HttpRequester httpRequest = new HttpRequester();
            HttpRespons hr;
            hr = httpRequest.sendPost(url);
            String results = hr.getContent();
            baseLog.info(LogFormatUtil.getActionFormat("获取accessToken的results结果:" + results));
            accessToken = (String) JSONObject.parseObject(results).get("access_token");
            if (EmptyUtil.isEmpty(accessToken)) {
                return null;
            }
            Integer expiresIn = (Integer) JSONObject.parseObject(results).get("expires_in");
            redisManager.saveStringBySeconds(WechatConstants.WECHAT_ACCESS_TOKEN, accessToken, expiresIn);
            baseLog.info(LogFormatUtil.getActionFormat("new - accessToken:" + accessToken));
            return accessToken;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public static String getReloadWeixinToken(RedisManager redisManager, String appid, String secret) {
        try {
            String accessToken = redisManager.getStringValueByKey(WechatConstants.WECHAT_ACCESS_TOKEN);
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
            baseLog.info(LogFormatUtil.getActionFormat("重新请求accessToken的请求URL:" + url));
            HttpRequester httpRequest = new HttpRequester();
            HttpRespons hr;
            hr = httpRequest.sendPost(url);
            String results = hr.getContent();
            baseLog.info(LogFormatUtil.getActionFormat("重新获取accessToken的results结果:" + results));
            accessToken = (String) JSONObject.parseObject(results).get("access_token");
            if (EmptyUtil.isEmpty(accessToken)) {
                return null;
            }
            Integer expiresIn = (Integer) JSONObject.parseObject(results).get("expires_in");
            redisManager.saveStringBySeconds(WechatConstants.WECHAT_ACCESS_TOKEN, accessToken, expiresIn);
            baseLog.info(LogFormatUtil.getActionFormat("new - accessToken:" + accessToken));
            return accessToken;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String getLineLinkToUserInfo(String appid, String state, String redirectUri) {
        String lineLink = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
        lineLink += appid;
        lineLink += "&redirect_uri=" + redirectUri;
        lineLink += "&response_type=code&scope=" + "snsapi_userinfo" + "&state=" + state + "#wechat_redirect";
        baseLog.info(LogFormatUtil.getActionFormat("微信请求的地址:" + lineLink));
        return lineLink;
    }
    
    public static String getLineLinkToOpenId(String appid, String state, String redirectUri) {
        String lineLink = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
        lineLink += appid;
        lineLink += "&redirect_uri=" + redirectUri;
        lineLink += "&response_type=code&scope=" + "snsapi_base" + "&state=" + state + "#wechat_redirect";
        baseLog.info(LogFormatUtil.getActionFormat("微信请求的地址:" + lineLink));
        return lineLink;
    }

    /**
     * @param appid
     * @param secret
     * @param code
     * @return
     * @Title: 获取用户的微信openId
     * @Description: TODO
     * @author LILEI
     * @date 2016年5月10日 上午11:19:20
     * @version V1.0
     */
    public static WechatOpenIdVo getWechatOpenId(String appid, String secret, String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        baseLog.info(LogFormatUtil.getActionFormat("获取openid的url:" + url));
        HttpRequester httpRequest = new HttpRequester();
        HttpRespons hr = null;
        try {
            hr = httpRequest.sendPost(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String results = hr.getContent();
        baseLog.info(LogFormatUtil.getActionFormat("获取openid的results:" + results));
        WechatOpenIdVo openIdBean = JSON.parseObject(results, WechatOpenIdVo.class);
        return openIdBean;
    }


    public static String getWechatUserInfo(String accessToken, String openid) {
        baseLog.info(LogFormatUtil.getActionFormat("获取微信的用户基本信息", openid, 1));
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
        HttpRequester httpRequest = new HttpRequester();
        HttpRespons hr = null;
        try {
            hr = httpRequest.sendGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (EmptyUtil.isEmpty(hr)) {
            return null;
        }
        String results = hr.getContent();
        baseLog.info(LogFormatUtil.getActionFormat("accessToken:" + accessToken));
        baseLog.info(LogFormatUtil.getActionFormat("微信返回信息:" + results));
        return results;
    }


    /**
     * 用户是否关注公众号
     *
     * @param accessToken
     * @param openid
     * @return
     */
    public static String getWechatUserAttention(String accessToken, String openid) {
        baseLog.info(LogFormatUtil.getActionFormat("获取微信的用户是否关注", openid, 1));
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
        HttpRequester httpRequest = new HttpRequester();
        HttpRespons hr = null;
        try {
            hr = httpRequest.sendGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (EmptyUtil.isEmpty(hr)) {
            return null;
        }
        String results = hr.getContent();
        baseLog.info(LogFormatUtil.getActionFormat("accessToken:" + accessToken));
        baseLog.info(LogFormatUtil.getActionFormat("微信返回信息:" + results));
        return results;
    }


    /**
     * 微信sdk写入实体中
     *
     * @param redisManager
     * @param url
     * @param appid
     * @param secret
     * @return
     */
    public static WechatShareJSVO InjectWechatConfig(RedisManager redisManager, String url, String appid, String secret) {
        // baseLog.info("WeixinConstants.WEIXIN_APPID" +
        // WeixinConstants.WEIXIN_APPID);
        // baseLog.info("WeixinConstants.WEIXIN_SECRET" +
        // WeixinConstants.WEIXIN_SECRET);
        String token = WechatUtil.getWeixinToken(redisManager, appid, secret);
        String ticket = WechatUtil.getWeixinTicket(redisManager, token);
        if (EmptyUtil.isEmpty(ticket)) {
            token = WechatUtil.getReloadWeixinToken(redisManager, appid, secret);
            ticket = WechatUtil.getReloadWeixinTicket(redisManager, token);
        }
//		try{
//			ticket = WechatUtil.getWeixinTicket(redisManager, token);
//		}catch(Exception e){
//			token = WechatUtil.getReloadWeixinToken(redisManager, appid, secret);
//			ticket = WechatUtil.getReloadWeixinTicket(redisManager, token);
//		}
        WechatShareJSVO wsj = WechatUtil.getShareJS(ticket, url, appid);
        return wsj;
    }


    /**
     * 第二步 根据token获取ticket
     *
     * @param accessToken
     * @return
     */
    public static String getWeixinTicket(RedisManager redisManager, String accessToken) {
        baseLog.info(LogFormatUtil.getActionFormat("获取微信ticket开始,参数accessToken:" + accessToken));
        try {
            String ticket = redisManager.getStringValueByKey(WechatConstants.WECHAT_TICKET);
            // baseLog.info("redisManager - ticket:" + ticket);
            if (EmptyUtil.isEmpty(ticket)) {
                String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
                HttpRequester httpRequest = new HttpRequester();
                HttpRespons hr;
                hr = httpRequest.sendPost(url);
                String results = hr.getContent();
                baseLog.info(LogFormatUtil.getActionFormat("获取ticket的results结果:" + results));
                if (EmptyUtil.isNotEmpty(results)) {
                    JSONObject json = JSON.parseObject(results, JSONObject.class);
                    if (EmptyUtil.isNotEmpty(json.get("errcode"))) {
                        if ("42001".equals(json.get("errcode").toString())) {
                            baseLog.info(LogFormatUtil.getActionFormat("accessToken is timeout"));
                        }
                    }
                }
                ticket = (String) JSONObject.parseObject(results).get("ticket");
                Integer expiresIn = (Integer) JSONObject.parseObject(results).get("expires_in");
                redisManager.saveStringBySeconds(WechatConstants.WECHAT_TICKET, ticket, expiresIn);
                baseLog.info(LogFormatUtil.getActionFormat("new - ticket:" + ticket));
                return ticket;
            } else {
                return ticket;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 第二步 根据token获取ticket
     *
     * @param accessToken
     * @return
     */
    public static String getReloadWeixinTicket(RedisManager redisManager, String accessToken) {
        baseLog.info(LogFormatUtil.getActionFormat("重新获取微信ticket开始,参数accessToken:" + accessToken));
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
            HttpRequester httpRequest = new HttpRequester();
            HttpRespons hr;
            hr = httpRequest.sendPost(url);
            String results = hr.getContent();
            baseLog.info(LogFormatUtil.getActionFormat("获取ticket的results结果:" + results));
            if (EmptyUtil.isNotEmpty(results)) {
                JSONObject json = JSON.parseObject(results, JSONObject.class);
                if (EmptyUtil.isNotEmpty(json.get("errcode"))) {
                    if ("42001".equals(json.get("errcode").toString())) {
                        baseLog.info(LogFormatUtil.getActionFormat("accessToken is timeout"));
                    }
                }
            }
            String ticket = (String) JSONObject.parseObject(results).get("ticket");
            Integer expiresIn = (Integer) JSONObject.parseObject(results).get("expires_in");
            redisManager.saveStringBySeconds(WechatConstants.WECHAT_TICKET, ticket, expiresIn);
            baseLog.info(LogFormatUtil.getActionFormat("new - ticket:" + ticket));
            return ticket;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 第三步 根据ticket生成分享链接
     *
     * @param ticket
     * @param url
     * @return
     */
    private static WechatShareJSVO getShareJS(String ticket, String url, String appid) {
        WechatShareJSVO wsj = new WechatShareJSVO();
        Long timestamp = System.currentTimeMillis() / 1000;
        String wxStr = "jsapi_ticket=" + ticket + "&noncestr=" + WechatConstants.WECHAT_JSNONCESTR + "&timestamp=" + timestamp + "&url=" + url;
        String signature = Sha1Util.getSha1(wxStr);
        wsj.setNonceStr(WechatConstants.WECHAT_JSNONCESTR);
        wsj.setUrl(url);
        wsj.setTimestamp(timestamp.toString());
        wsj.setSignature(signature);
        wsj.setAppid(appid);
        return wsj;
    }

}

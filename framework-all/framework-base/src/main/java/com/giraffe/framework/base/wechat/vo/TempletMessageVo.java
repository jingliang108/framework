package com.tanjin.framework.base.wechat.vo;

import java.util.Map;

/**
 * 类说明
 *
 * @author fish
 * @create 2017/7/19
 **/
public class TempletMessageVo {

    private String touser;

    private String template_id;

    private String url;

    private String topcolor;

    private Map<String, TemplateMessageDataVo> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Map<String, TemplateMessageDataVo> getData() {
        return data;
    }

    public void setData(Map<String, TemplateMessageDataVo> data) {
        this.data = data;
    }
}

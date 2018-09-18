package com.tanjin.framework.base.wechat.vo;

/**
 * 类说明
 *
 * @author fish
 * @create 2017/7/19
 **/
public class TemplateMessageDataVo {

    private String value;

    private String color;

    public String getValue() {
        return value;
    }

    public TemplateMessageDataVo() {
        super();
    }

    public TemplateMessageDataVo(String value) {
        super();
        this.value = value;
        this.color = "#000000";
    }

    public TemplateMessageDataVo(String value, String color) {
        super();
        this.value = value;
        this.color = color;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}

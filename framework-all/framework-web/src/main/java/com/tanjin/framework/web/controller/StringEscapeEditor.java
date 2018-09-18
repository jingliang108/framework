package com.tanjin.framework.web.controller;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

/**
 * 防止xss，sql，javascript等攻击
 * @author liuyijun
 * @date 2016/1/12
 * @description
 */
public class StringEscapeEditor extends PropertyEditorSupport {

    private boolean escapeHTML;
    private boolean escapeJavaScript;
    private boolean escapeSQL;

    public StringEscapeEditor() {
        super();
    }

    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {
        super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
        this.escapeSQL = escapeSQL;
    }

    @Override
    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            if (escapeHTML) {
                value = HtmlUtils.htmlEscape(value);
            }
            if (escapeJavaScript) {
                value= JavaScriptUtils.javaScriptEscape(value);
            }
            if (escapeSQL) {
                value = StringEscapeUtils.escapeSql(value);
            }
            setValue(value);
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }


}

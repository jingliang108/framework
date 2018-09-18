package com.giraffe.framework.base.common.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by liuyijun on 2016/8/15.
 */
public class LoadPropertiesUtil {


    public static Properties loadFile(String path) {
        InputStreamReader in = null;
        Properties properties=null;
        try {
           properties = new Properties();;
            in = new FileReader(new File(path));
            properties.load(in);
        } catch (IOException e) {
        } finally {
            IOUtils.closeQuietly(in);
        }
        return properties;
    }
}

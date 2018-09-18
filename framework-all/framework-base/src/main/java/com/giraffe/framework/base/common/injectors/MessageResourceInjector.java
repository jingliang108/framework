package com.giraffe.framework.base.common.injectors;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

public class MessageResourceInjector {
	static Logger logger = LoggerFactory.getLogger(MessageResourceInjector.class);
	
	private static Properties messageResourceMap = new Properties();
	
	public static synchronized void inject(Resource[] locations) throws Exception{
		if (locations != null) {
			for (Resource location : locations) {
				logger.info("Loading properties file from " + location);
				InputStream is = null;
				try {
					is = location.getInputStream();
					PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
					propertiesPersister.load(messageResourceMap, new InputStreamReader(is, "UTF-8"));
				}
				catch (IOException ex) {
					throw ex;
				}
				finally {
					if (is != null) {
						is.close();
					}
				}
			}
		}
	}

	public static String getMessage(String code) {
		return messageResourceMap.getProperty(code);
	}
}

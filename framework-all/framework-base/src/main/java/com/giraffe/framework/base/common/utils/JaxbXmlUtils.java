package com.giraffe.framework.base.common.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;

public class JaxbXmlUtils {
	/**
	 * 使用JAXB将对象转换成XML格式 
	 * 默认为UTF-8编码
	 * @param obj
	 * @return
	 */
	public static String convert2Xml(Object obj) {  
        return convert2Xml(obj, "UTF-8");  
    }  
	
	/**
	 * 使用JAXB将对象转换成XML格式
	 * @param obj       对象
	 * @param encoding  编码
	 * @return
	 */
	public static String convert2Xml(Object obj, String encoding) {  
        String result = null;  
        StringWriter writer=null;
        try {  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller shaller = context.createMarshaller();  
            shaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            shaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
            writer= new StringWriter();  
            shaller.marshal(obj, writer);  
            result = writer.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally{ 
        	if(writer!=null)
        	{
        		try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
  
        return result;  
    }  
	
	/**
	 * 将XML字符串转换成对象
	 * @param xml   需要解析的xml
	 * @param clazz 需要生成的对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert2Bean(String xml, Class<T> clazz) {  
        T t = null;
        if(StringUtils.isNotEmpty(xml)&&clazz!=null)
        {
        	StringReader reader=null;
        	try {  
                JAXBContext context = JAXBContext.newInstance(clazz);  
                Unmarshaller unmarshaller = context.createUnmarshaller();  
                reader=new StringReader(xml);
                t = (T) unmarshaller.unmarshal(reader);  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {
            	if(reader!=null)
            	{
            		reader.close();
            	}
            }
        }
  
        return t;  
    }  
	
	
}

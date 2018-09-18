package com.tanjin.framework.base.common.utils;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartEntityBuilderUtil {

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @author liuyijun
	 */
	public static MultipartEntityBuilder createMultipartEntityBuilderByMultipartFile(
			MultipartFile file) throws IOException {
		MultipartEntityBuilder multipartEntity = null;
		try {
			Assert.notNull(file);
			multipartEntity = MultipartEntityBuilder.create();
			String filename = file.getOriginalFilename();
			multipartEntity.addPart("files", new ByteArrayBody(file.getBytes(),
					filename));
		} catch (IOException e) {
			throw e;
		}
		return multipartEntity;

	}

}

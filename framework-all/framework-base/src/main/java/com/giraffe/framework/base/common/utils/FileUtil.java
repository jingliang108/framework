package com.tanjin.framework.base.common.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * @author liuyijun
 * @date 2016/1/11
 * @description
 */
public class FileUtil {

	/**
	 * 将一个目录下所有文件（包括字目录的文件）全部copy到另一个目录下
	 * 
	 * @author LiuYiJun
	 * @param srcDir
	 *            被copy的目录地址
	 * @param destDir
	 *            copy到目标目录的地址
	 * @param filter
	 *            要copy哪些文件（可以为null，为null是copy所有文件)
	 * */
	public static void copyDirectory(File srcDir, File destDir,
			FileFilter filter) throws IOException {
		FileUtils.copyDirectory(srcDir, destDir, filter);
	}

	/**
	 * 将互联网上的文件下载到本地。
	 * 
	 * @author LiuYiJun
	 * @param urlAddr
	 *            互联网地址
	 * @param filePath文件存放地址和文件名
	 * */
	public static void copyURLToFile(String urlAddr, String filePath)
			throws IOException {
		URL url = new URL(urlAddr);
		File file = new File(filePath);
		FileUtils.copyURLToFile(url, file);
	}

	/**
	 * 删除文件
	 * 
	 *
	 * @param filepath
	 *            要删除的文件或是文件夹路径
	 * @throws IOException
	 * @author LiuYiJun
	 * */
	public static void forceDeleteOnExit(String filePath) throws IOException {
		File file = new File(filePath);
		FileUtils.forceDeleteOnExit(file);
	}

	/**
	 * 读文件，将所有的数据读成一行，并以string的形式进行返回
	 * 
	 * @author LiuYiJun
	 * 
	 * @param filePath
	 *            读取文件的路径
	 * @param encoding
	 *            读文件时的编码，可以为空，默认为UTF-8
	 * */
	public static String readFileToString(String filePath, String encoding)
			throws IOException {
		File file = new File(filePath);
		if (EmptyUtil.isEmptyChars(encoding)) {
			encoding = "UTF-8";
		}
		return FileUtils.readFileToString(file, encoding);
	}

	/**
	 * 读文件，一行读完后，以String形式存入list中
	 * 
	 * @author LiuYiJun
	 * 
	 * @param filePath
	 *            读取文件的路径
	 * @param encoding
	 *            读文件时的编码，可以为空，默认为UTF-8
	 * */
	public static List<String> readLines(String filePath, String encoding)
			throws IOException {
		File file = new File(filePath);
		if (EmptyUtil.isNotEmptyChars(encoding)) {
			encoding = "UTF-8";
		}
		return FileUtils.readLines(file, encoding);
	}

	/**
	 * 写文件，将所有的文件
	 * 
	 * @author LiuYiJun
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param encoding
	 *            文件的编码，可以为空，默认为UTF-8
	 * */
	public static void writeStringToFile(String filePath, String encoding,
			String data) throws IOException {
		File file = new File(filePath);
		if (EmptyUtil.isNotEmptyChars(encoding)) {
			encoding = "UTF-8";
		}
		FileUtils.writeStringToFile(file, data, encoding);
	}

	/**
	 * 写文件，将所有的文件
	 * 
	 * @author LiuYiJun
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param encoding
	 *            文件的编码，可以为空，默认为UTF-8
	 * */
	public static void writeLines(String filePath, String encoding,
			Collection<String> datas) throws IOException {
		File file = new File(filePath);
		if (EmptyUtil.isNotEmptyChars(encoding)) {
			encoding = "UTF-8";
		}
		FileUtils.writeLines(file, datas, encoding);
	}

	/**
	 * 将srcDirPath文件夹中的文件移动到destDirPath文件夹中，如果destDirPath文件夹地址不存在，则进行创建
	 * 
	 * @author LiuYiJun
	 * */
	public static void moveDirectory(String srcDirPath, String destDirPath)
			throws IOException {
		File srcDir = new File(srcDirPath);
		File destDir = new File(destDirPath);

		FileUtils.moveDirectoryToDirectory(srcDir, destDir, true);
	}

	/**
	 * 将srcPath文件移动到destDirPath文件夹中，如果destDirPath文件夹地址不存在，则进行创建
	 * 
	 * @author LiuYiJun
	 * */
	public static void moveFileToDirectory(String srcPath, String destDirPath)
			throws IOException {
		File srcDir = new File(srcPath);
		File destDir = new File(destDirPath);

		FileUtils.moveFileToDirectory(srcDir, destDir, true);
	}

	/**
	 * 关闭文件流，不抛出异常
	 * 
	 * @author LiuYiJun
	 * @param input
	 *            要关闭的输入流，可以为null
	 * @param output
	 *            要关闭的输出流，可以为null
	 * */
	public static void close(Reader input, Writer output) {
		IOUtils.closeQuietly(output);
		IOUtils.closeQuietly(input);
	}

	/**
	 * 把一个输入流按照给定的编码转换成字符串
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in, String encoding)
			throws Exception {
		return IOUtils.toString(in, encoding);
	}

	/**
	 * 把一个输入流转换成字节数组
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] InputStreamToBytes(InputStream in) throws Exception {
		return IOUtils.toByteArray(in);
	}

	/**
	 * 把一个输入流转成文件
	 * 
	 * @param in
	 * @param filePath
	 *            文件路径
	 * @throws Exception
	 */
	public static void InputStreamTOFile(InputStream in, String filePath)
			throws Exception {
		File file = new File(filePath);
		byte[] gif = IOUtils.toByteArray(in);
		FileUtils.writeByteArrayToFile(file, gif);
		IOUtils.closeQuietly(in);
	}
	
	public static void ByteArrayTOFile(byte[] data, String filePath)
			throws Exception {
		File file = new File(filePath);
		FileUtils.writeByteArrayToFile(file, data);
	}

}

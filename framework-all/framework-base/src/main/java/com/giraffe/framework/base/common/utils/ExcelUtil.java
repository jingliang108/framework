package com.giraffe.framework.base.common.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 导入导出Excel工具类
 *
 * @author jingliang
 * @version 1.0.0 , 2015年1月5日 下午8:33:09
 */
public class ExcelUtil {
	private static Logger baseLog = LoggerFactory.getLogger("baseLog");

	/**
	 * 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，可自定义工作表大小）
	 *
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系,如果需要的是引用对象的属性，则英文属性使用类似于EL表达式的格式,如：
	 *            list中存放的都是student
	 *            ，student中又有college属性，而我们需要学院名称，则可以这样写,fieldMap
	 *            .put("college.collegeName","学院名称")
	 * @param sheetName
	 *            工作表的名称
	 * @param sheetSize
	 *            每个工作表中记录的最大个数
	 * @param out
	 *            导出流
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void listToExcel(List<T> list,
			LinkedHashMap<String, String> fieldMap, String sheetName,
			int sheetSize, OutputStream out) throws Exception {

		if (list == null || list.size() == 0) {
			throw new Exception("数据源中没有任何数据");
		}

		if (sheetSize > 65535 || sheetSize < 1) {
			sheetSize = 65535;
		}

		// 创建工作簿并发送到OutputStream指定的地方
		WritableWorkbook wwb;
		try {
			wwb = Workbook.createWorkbook(out);

			// 因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
			// 所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
			// 1.计算一共有多少个工作表
			double sheetNum = Math.ceil(list.size()
					/ new Integer(sheetSize).doubleValue());

			// 2.创建相应的工作表，并向其中填充数据
			for (int i = 0; i < sheetNum; i++) {
				// 如果只有一个工作表的情况
				if (1 == sheetNum) {
					WritableSheet sheet = wwb.createSheet(sheetName, i);
					fillSheet(sheet, list, fieldMap, 0, list.size() - 1);

					// 有多个工作表的情况
				} else {
					WritableSheet sheet = wwb.createSheet(sheetName + (i + 1),
							i);

					// 获取开始索引和结束索引
					int firstIndex = i * sheetSize;
					int lastIndex = (i + 1) * sheetSize - 1 > list.size() - 1 ? list
							.size() - 1 : (i + 1) * sheetSize - 1;
							// 填充工作表
					fillSheet(sheet, list, fieldMap, firstIndex, lastIndex);
				}
			}

			wwb.write();
			wwb.close();

		} catch (Exception e) {
			e.printStackTrace();
			// 如果是ExcelException，则直接抛出
			if (e instanceof Exception) {
				throw (Exception) e;

				// 否则将其它异常包装成ExcelException再抛出
			} else {
				throw new Exception("导出Excel失败");
			}
		}

	}
	public static <T> void listToExcel1(List<T> list,
			LinkedHashMap<String, String> fieldMap, String sheetName,
			int sheetSize, OutputStream out,String beginDate,String endDate) throws Exception {

		if (list == null || list.size() == 0) {
//			throw new Exception("数据源中没有任何数据");
			baseLog.info(LogFormatUtil.getActionErrorFormat("异常申请记录导出：数据源没有数据"));
		}

		if (sheetSize > 65535 || sheetSize < 1) {
			sheetSize = 65535;
		}

		// 创建工作簿并发送到OutputStream指定的地方
		WritableWorkbook wwb;
		try {
			wwb = Workbook.createWorkbook(out);

			// 因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
			// 所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
			// 1.计算一共有多少个工作表
			double sheetNum =0.0;
			if(list.size()>0){
			 sheetNum = Math.ceil(list.size()
					/ new Integer(sheetSize).doubleValue());
			}
			// 2.创建相应的工作表，并向其中填充数据
			for (int i = 0; i < sheetNum; i++) {
				// 如果只有一个工作表的情况
				if (1 == sheetNum) {
					WritableSheet sheet = wwb.createSheet(sheetName, i);
					fillSheet1(sheet, list, fieldMap, 0, list.size() - 1,beginDate,endDate);

					// 有多个工作表的情况
				} else {
					WritableSheet sheet = wwb.createSheet(sheetName + (i + 1),
							i);

					// 获取开始索引和结束索引
					int firstIndex = i * sheetSize;
					int lastIndex = (i + 1) * sheetSize - 1 > list.size() - 1 ? list
							.size() - 1 : (i + 1) * sheetSize - 1;
							// 填充工作表
					fillSheet1(sheet, list, fieldMap, firstIndex, lastIndex,beginDate,endDate);
				}
			}

			wwb.write();
			wwb.close();

		} catch (Exception e) {
			baseLog.info(LogFormatUtil.getActionErrorFormat("考勤记录导出：数据源没有数据"));
		}

	}
	/**
	 * 导出excel
	 *
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param collectionName
	 *            子集合的名称
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param sheetName
	 *            工作表的名称
	 * @param sheetSize
	 *            每个工作表中记录的最大个数
	 * @param out
	 *            导出流
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void listToExcel(List<T> list,
			LinkedHashMap<String, String> fieldMap, String collectionName,
			String title, String content, String sheetName, int sheetSize,
			OutputStream out) throws Exception {

		if (list == null || list.size() == 0) {
			throw new Exception("数据源中没有任何数据");
		}

		if (sheetSize > 65535 || sheetSize < 1) {
			sheetSize = 65535;
		}

		// 创建工作簿并发送到OutputStream指定的地方
		WritableWorkbook wwb;
		try {
			wwb = Workbook.createWorkbook(out);

			// 因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
			// 所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
			// 1.计算一共有多少个工作表
			double sheetNum = Math.ceil(list.size()
					/ new Integer(sheetSize).doubleValue());

			// 2.创建相应的工作表，并向其中填充数据
			for (int i = 0; i < sheetNum; i++) {
				// 如果只有一个工作表的情况
				if (1 == sheetNum) {
					WritableSheet sheet = wwb.createSheet(sheetName, i);
					fillSheet(sheet, list, fieldMap, collectionName, title,
							content, 0, list.size() - 1);

					// 有多个工作表的情况
				} else {
					WritableSheet sheet = wwb.createSheet(sheetName + (i + 1),
							i);

					// 获取开始索引和结束索引
					int firstIndex = i * sheetSize;
					int lastIndex = (i + 1) * sheetSize - 1 > list.size() - 1 ? list
							.size() - 1 : (i + 1) * sheetSize - 1;
					// 填充工作表
					fillSheet(sheet, list, fieldMap, collectionName, title,
							content, firstIndex, lastIndex);
				}
			}

			wwb.write();
			wwb.close();

		} catch (Exception e) {
			e.printStackTrace();
			// 如果是ExcelException，则直接抛出
			if (e instanceof Exception) {
				throw (Exception) e;

				// 否则将其它异常包装成ExcelException再抛出
			} else {
				throw new Exception("导出Excel失败");
			}
		}

	}

	/**
	 * 导出excel
	 *
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param sheetName
	 *            工作表的名称
	 * @param out
	 *            导出流
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void listToExcel(List<T> list,
			LinkedHashMap<String, String> fieldMap, String sheetName,
			OutputStream out) throws Exception {

		listToExcel(list, fieldMap, sheetName, 65535, out);

	}

	/**
	 * 导出Excel（导出到浏览器，可以自定义工作表的大小）
	 *
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param sheetName
	 *            工作表的名称
	 * @param sheetSize
	 *            每个工作表中记录的最大个数
	 * @param response
	 *            使用response可以导出到浏览器
	 * @param fileName
	 *            Excel的文件名
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void listToExcel(List<T> list,
			LinkedHashMap<String, String> fieldMap, String sheetName,
			int sheetSize, HttpServletResponse response, String fileName)
			throws Exception {

		// 如果文件名没提供，则使用时间戳
		if (fileName == null || fileName.trim().equals("")) {
			// 设置默认文件名为当前时间：年月日时分秒
			fileName = new SimpleDateFormat("yyyyMMddhhmmss")
					.format(new Date()).toString();
		}

		// 设置response头信息
		response.reset();
		response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName + ".xls");

		// 创建工作簿并发送到浏览器
		try {
			OutputStream out = response.getOutputStream();
			listToExcel(list, fieldMap, sheetName, sheetSize, out);

		} catch (Exception e) {
			e.printStackTrace();

			// 如果是ExcelException，则直接抛出
			if (e instanceof Exception) {
				throw (Exception) e;

				// 否则将其它异常包装成ExcelException再抛出
			} else {
				throw new Exception("导出Excel失败");
			}
		}
	}
	
	public static <T> void listToExcel1(List<T> list,
			LinkedHashMap<String, String> fieldMap, String sheetName,
			int sheetSize, HttpServletResponse response, String fileName,String beginDate,String endDate)
			throws Exception {

		// 如果文件名没提供，则使用时间戳
		if (fileName == null || fileName.trim().equals("")) {
			// 设置默认文件名为当前时间：年月日时分秒
			fileName = new SimpleDateFormat("yyyyMMddhhmmss")
					.format(new Date()).toString();
		}

		// 设置response头信息
		response.reset();
		response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName + ".xls");

		// 创建工作簿并发送到浏览器
		try {
			OutputStream out = response.getOutputStream();
			listToExcel1(list, fieldMap, sheetName, sheetSize, out,beginDate,endDate);

		} catch (Exception e) {
//			e.printStackTrace();
//
//			// 如果是ExcelException，则直接抛出
//			if (e instanceof Exception) {
//				throw (Exception) e;
//
//				// 否则将其它异常包装成ExcelException再抛出
//			} else {
//				throw new Exception("导出Excel失败");
//			}
			baseLog.info(LogFormatUtil.getActionErrorFormat("异常申请记录导出：数据源没有数据"));
		}
	}

	/**
	 * 导出Excel（导出到浏览器，可以自定义工作表的大小）
	 *
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param collectionName
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param sheetName
	 *            工作表的名称
	 * @param sheetSize
	 *            每个工作表中记录的最大个数
	 * @param response
	 *            使用response可以导出到浏览器
	 * @param fileName
	 *            Excel的文件名
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void listToExcel(List<T> list,
			LinkedHashMap<String, String> fieldMap, String collectionName,
			String title, String content, String sheetName, int sheetSize,
			HttpServletResponse response, String fileName)
			throws Exception {

		// 如果文件名没提供，则使用时间戳
		if (fileName == null || fileName.trim().equals("")) {
			// 设置默认文件名为当前时间：年月日时分秒
			fileName = new SimpleDateFormat("yyyyMMddhhmmss")
					.format(new Date()).toString();
		}

		// 设置response头信息
		response.reset();
		response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName + ".xls");

		// 创建工作簿并发送到浏览器
		try {

			OutputStream out = response.getOutputStream();
			listToExcel(list, fieldMap, collectionName, title, content,
					sheetName, sheetSize, out);

		} catch (Exception e) {
			e.printStackTrace();

			// 如果是ExcelException，则直接抛出
			if (e instanceof Exception) {
				throw (Exception) e;

				// 否则将其它异常包装成ExcelException再抛出
			} else {
				throw new Exception("导出Excel失败");
			}
		}
	}

	/**
	 * 导出Excel（导出到浏览器，可以自定义工作表的大小）
	 *
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param sheetName
	 *            工作表的名称
	 * @param sheetSize
	 *            每个工作表中记录的最大个数
	 * @param response
	 *            使用response可以导出到浏览器
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void listToExcel(List<T> list,
			LinkedHashMap<String, String> fieldMap, String sheetName,
			int sheetSize, HttpServletResponse response) throws Exception {

		// 设置默认文件名为当前时间：年月日时分秒
		String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(
				new Date()).toString();

		listToExcel(list, fieldMap, sheetName, sheetSize, response, fileName);
	}

	/**
	 * 导出Excel（导出到浏览器，可以自定义工作表的大小）
	 *
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param collectionName
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param sheetName
	 *            工作表的名称
	 * @param response
	 *            使用response可以导出到浏览器
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void listToExcel(List<T> list,
			LinkedHashMap<String, String> fieldMap, String collectionName,
			String title, String content, String sheetName,
			HttpServletResponse response) throws Exception {

		listToExcel(list, fieldMap, collectionName, title, content, sheetName,
				65535, response, "");
	}

	/**
	 * 导出Excel（导出到浏览器，可以自定义工作表的大小）
	 *
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param sheetName
	 *            工作表的名称
	 * @param response
	 *            使用response可以导出到浏览器
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void listToExcel(List<T> list,
			LinkedHashMap<String, String> fieldMap, String sheetName,
			HttpServletResponse response,String fileName) throws Exception {

		listToExcel(list, fieldMap, sheetName, 65535, response,fileName);
	}
	public static <T> void listToExcel1(List<T> list,
			LinkedHashMap<String, String> fieldMap, String sheetName,
			HttpServletResponse response,String fileName,String beginDate,String endDate) throws Exception {

		listToExcel1(list, fieldMap, sheetName, 65535, response,fileName,beginDate,endDate);
	}
	/**
	 * 导出Excel模板 考试专用（导出到浏览器，可以自定义工作表的大小）
	 *
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param sheetName
	 *            工作表的名称
	 * @param response
	 *            使用response可以导出到浏览器
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> void leadToExcelQuestionBankTemplet(LinkedHashMap<String, String> fieldMap, String sheetName,
			HttpServletResponse response) throws Exception {

				// 设置默认文件名为当前时间：年月日时分秒
				String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(
						new Date()).toString();


				// 如果文件名没提供，则使用时间戳
				if (fileName == null || fileName.trim().equals("")) {
					// 设置默认文件名为当前时间：年月日时分秒
					fileName = new SimpleDateFormat("yyyyMMddhhmmss")
							.format(new Date()).toString();
				}

				// 设置response头信息
				response.reset();
				response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
				response.setHeader("Content-disposition", "attachment; filename="
						+ fileName + ".xls");

				// 创建工作簿并发送到浏览器
				try {

					OutputStream out = response.getOutputStream();

					 int sheetSize = 65535;


					// 创建工作簿并发送到OutputStream指定的地方
					WritableWorkbook wwb;
					try {
						wwb = Workbook.createWorkbook(out);

						// 因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
						// 所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
						// 1.计算一共有多少个工作表
//						double sheetNum = Math.ceil(list.size()
//								/ new Integer(sheetSize).doubleValue());

						double sheetNum=1;
						
						// 2.创建相应的工作表，并向其中填充数据
							// 如果只有一个工作表的情况
							if (1 == sheetNum) {
								WritableSheet sheet = wwb.createSheet(sheetName, 1);

								// 定义存放英文字段名和中文字段名的数组
								String[] enFields = new String[fieldMap.size()];
								String[] cnFields = new String[fieldMap.size()];

								// 填充数组
								int count = 0;
								for (Entry<String, String> entry : fieldMap.entrySet()) {
									enFields[count] = entry.getKey();
									cnFields[count] = entry.getValue();
									count++;
								}
								// 填充表头
								for (int i = 0; i < cnFields.length; i++) {
									Label label = new Label(i, 0, cnFields[i]);
									sheet.addCell(label);
								}


								// 设置自动列宽
								setColumnAutoSize(sheet, 5);
								

							} 

						wwb.write();
						wwb.close();

					} catch (Exception e) {
						e.printStackTrace();
						// 如果是ExcelException，则直接抛出
						if (e instanceof Exception) {
							throw (Exception) e;

							// 否则将其它异常包装成ExcelException再抛出
						} else {
							throw new Exception("导出Excel失败");
						}
					}

				} catch (Exception e) {
					e.printStackTrace();

					// 如果是ExcelException，则直接抛出
					if (e instanceof Exception) {
						throw (Exception) e;

						// 否则将其它异常包装成ExcelException再抛出
					} else {
						throw new Exception("导出Excel失败");
					}
				}
	}	
	
	/**
	 * 将Excel转化为List
	 *
	 * @param in
	 *            ：承载着Excel的输入流
	 * @param sheetIndex
	 *            ：要导入的工作表序号
	 * @param entityClass
	 *            ：List中对象的类型（Excel中的每一行都要转化为该类型的对象）
	 * @param fieldMap
	 *            ：Excel中的中文列头和类的英文属性的对应关系Map
	 * @param uniqueFields
	 *            ：指定业务主键组合（即复合主键），这些列的组合不能重复
	 * @return list集合
	 * @throws ExcelException
	 *             异常
	 */
	public static <T> List<T> excelToList(Workbook wb, String sheetName,
			Class<T> entityClass, LinkedHashMap<String, String> fieldMap,
			String[] uniqueFields) throws Exception {

		// 定义要返回的list
		List<T> resultList = new ArrayList<T>();

		try {

			// 根据Excel数据源创建WorkBook
			// = Workbook.getWorkbook(in);
			// 获取工作表
			Sheet sheet = wb.getSheet(sheetName);

			// 获取工作表的有效行数
			int realRows = 0;
			for (int i = 0; i < sheet.getRows(); i++) {

				int nullCols = 0;
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell currentCell = sheet.getCell(j, i);
					if (currentCell == null
							|| "".equals(currentCell.getContents().toString())) {
						nullCols++;
					}
				}

				if (nullCols == sheet.getColumns()) {
					break;
				} else {
					realRows++;
				}
			}

			// 如果Excel中没有数据则提示错误
			if (realRows <= 1) {
				throw new Exception("Excel文件中没有任何数据");
			}

			Cell[] firstRow = sheet.getRow(0);

			String[] excelFieldNames = new String[firstRow.length];

			// 获取Excel中的列名
			for (int i = 0; i < firstRow.length; i++) {
				excelFieldNames[i] = firstRow[i].getContents().toString()
						.trim();
			}

			// 判断需要的字段在Excel中是否都存在
			boolean isExist = true;
			List<String> excelFieldList = Arrays.asList(excelFieldNames);
			for (String cnName : fieldMap.keySet()) {
				if (!excelFieldList.contains(cnName)) {
					isExist = false;
					break;
				}
			}

			// 如果有列名不存在，则抛出异常，提示错误
			if (!isExist) {
				throw new Exception("Excel中缺少必要的字段，或字段名称有误");
			}

			// 将列名和列号放入Map中,这样通过列名就可以拿到列号
			LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
			for (int i = 0; i < excelFieldNames.length; i++) {
				colMap.put(excelFieldNames[i], firstRow[i].getColumn());
			}

			// 判断是否有重复行
			// 1.获取uniqueFields指定的列
			Cell[][] uniqueCells = new Cell[uniqueFields.length][];
			for (int i = 0; i < uniqueFields.length; i++) {
				int col = colMap.get(uniqueFields[i]);
				uniqueCells[i] = sheet.getColumn(col);
			}

			// 2.从指定列中寻找重复行
			for (int i = 1; i < realRows; i++) {
				int nullCols = 0;
				for (int j = 0; j < uniqueFields.length; j++) {
					String currentContent = uniqueCells[j][i].getContents();
					Cell sameCell = sheet.findCell(currentContent,
							uniqueCells[j][i].getColumn(),
							uniqueCells[j][i].getRow() + 1,
							uniqueCells[j][i].getColumn(),
							uniqueCells[j][realRows - 1].getRow(), true);
					if (sameCell != null) {
						nullCols++;
					}
				}

				if (nullCols == uniqueFields.length) {
					throw new Exception("Excel中有重复行，请检查");
				}
			}

			// 将sheet转换为list
			for (int i = 1; i < realRows; i++) {
				// 新建要转换的对象
				T entity = entityClass.newInstance();

				// 给对象中的字段赋值
				for (Entry<String, String> entry : fieldMap.entrySet()) {
					// 获取中文字段名
					String cnNormalName = entry.getKey();
					// 获取英文字段名
					String enNormalName = entry.getValue();
					// 根据中文字段名获取列号
					int col = colMap.get(cnNormalName);

					// 获取当前单元格中的内容
					String content = sheet.getCell(col, i).getContents()
							.toString().trim();

					// 给对象赋值
					setFieldValueByName(enNormalName, content, entity);
				}

				resultList.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 如果是ExcelException，则直接抛出
			if (e instanceof Exception) {
				throw (Exception) e;

				// 否则将其它异常包装成ExcelException再抛出
			} else {
				e.printStackTrace();
				throw new Exception("导入Excel失败");
			}
		}
		return resultList;
	}

	/**
	 * 根据字段名获取字段值
	 *
	 * @param fieldName
	 *            字段名
	 * @param o
	 *            对象
	 * @return 字段值
	 * @throws Exception
	 *             异常
	 */
	public static Object getFieldValueByName(String fieldName, Object o)
			throws Exception {

		Object value = null;
		Field field = getFieldByName(fieldName, o.getClass());

		if (field != null) {
			field.setAccessible(true);
			value = field.get(o);
		} else {
			throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 "
					+ fieldName);
		}

		return value;
	}

	/**
	 * 根据字段名获取字段
	 *
	 * @param fieldName
	 *            字段名
	 * @param clazz
	 *            包含该字段的类
	 * @return 字段
	 */
	public static Field getFieldByName(String fieldName, Class<?> clazz) {
		// 拿到本类的所有字段
		Field[] selfFields = clazz.getDeclaredFields();

		// 如果本类中存在该字段，则返回
		for (Field field : selfFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		// 否则，查看父类中是否存在此字段，如果有则返回
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null && superClazz != Object.class) {
			return getFieldByName(fieldName, superClazz);
		}

		// 如果本类和父类都没有，则返回空
		return null;
	}
	
	
	/**
	 * 根据实体拿到该实体的所有属性
	 *
	 * @param clazz 实体
	 * @return 返回属性的list集合
	 */
	public static List getFieldByClass( Class<?> clazz) {
		
		List list = new ArrayList();
		
		// 拿到本类的所有字段
		Field[] selfFields = clazz.getDeclaredFields();

		for (Field field : selfFields) {
			list.add(field.getName());
		}
		// 否则，查看父类中是否存在此字段，如果有则返回
		Class<?> superClazz = clazz.getSuperclass();
		
		Field[] superFields=superClazz.getDeclaredFields();
		for (Field field : superFields) {
			list.add(field.getName());
		}
		

		// 如果本类和父类都没有，则返回空
		return list;
	}
		
	
	/**
	 * 根据实体拿到该实体的所有属性
	 *
	 * @param clazz 实体
	 * @return 返回属性的list集合
	 */
	public static List getSuperClassFieldByClass( Class<?> clazz) {
		
		List list = new ArrayList();
		
		// 否则，查看父类中是否存在此字段，如果有则返回
		Class<?> superClazz = clazz.getSuperclass();
		
		Field[] superFields=superClazz.getDeclaredFields();
		for (Field field : superFields) {
			list.add(field.getName());
		}
		

		// 如果父类没有，则返回空
		return list;
	}	

	/**
	 * 根据带路径或不带路径的属性名获取属性值,即接受简单属性名，如userName等，又接受带路径的属性名，如student.department.
	 * name等
	 *
	 * @param fieldNameSequence
	 *            带路径的属性名或简单属性名
	 * @param o
	 *            对象
	 * @return 属性值
	 * @throws Exception
	 *             异常
	 */
	public static Object getFieldValueByNameSequence(String fieldNameSequence,
			Object o) throws Exception {

		Object value = null;

		// 将fieldNameSequence进行拆分
		String[] attributes = fieldNameSequence.split("\\.");
		if (attributes.length == 1) {
			value = getFieldValueByName(fieldNameSequence, o);
		} else {
			// 根据属性名获取属性对象
			Object fieldObj = getFieldValueByName(attributes[0], o);
			String subFieldNameSequence = fieldNameSequence
					.substring(fieldNameSequence.indexOf(".") + 1);
			value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
		}
		return value;

	}

	/**
	 * 根据字段名给对象的字段赋值
	 *
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @param o
	 *            对象
	 * @throws Exception
	 *             异常
	 */
	public static void setFieldValueByName(String fieldName,
			Object fieldValue, Object o) throws Exception {

		Field field = getFieldByName(fieldName, o.getClass());
		if (field != null) {
			field.setAccessible(true);
			// 获取字段类型
			Class<?> fieldType = field.getType();

			// 根据字段类型给字段赋值
			if (String.class == fieldType) {
				field.set(o, String.valueOf(fieldValue));
			} else if ((Integer.TYPE == fieldType)
					|| (Integer.class == fieldType)) {
				field.set(o, Integer.parseInt(fieldValue.toString()));
			} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
				field.set(o, Long.valueOf(fieldValue.toString()));
			} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
				field.set(o, Float.valueOf(fieldValue.toString()));
			} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
				field.set(o, Short.valueOf(fieldValue.toString()));
			} else if ((Double.TYPE == fieldType)
					|| (Double.class == fieldType)) {
				field.set(o, Double.valueOf(fieldValue.toString()));
			} else if (Character.TYPE == fieldType) {
				if ((fieldValue != null)
						&& (fieldValue.toString().length() > 0)) {
					field.set(o,
							Character.valueOf(fieldValue.toString().charAt(0)));
				}
			} else if (Date.class == fieldType) {
				field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(fieldValue.toString()));
			} else {
				field.set(o, fieldValue);
			}
		} else {
			throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 "
					+ fieldName);
		}
	}

	/**
	 * 设置工作表自动列宽和首行加粗
	 *
	 * @param ws
	 *            要设置格式的工作表
	 * @param extraWith
	 *            额外的宽度
	 */
	public static void setColumnAutoSize(WritableSheet ws, int extraWith) {
		// 获取本列的最宽单元格的宽度
		for (int i = 0; i < ws.getColumns(); i++) {
			int colWith = 0;
			for (int j = 0; j < ws.getRows(); j++) {
				String content = ws.getCell(i, j).getContents().toString();
				int cellWith = content.length();
				if (colWith < cellWith) {
					colWith = cellWith;
				}
			}
			// 设置单元格的宽度为最宽宽度+额外宽度
			ws.setColumnView(i, colWith + extraWith);
		}

	}

	/**
	 * 向工作表中填充数据
	 *
	 * @param sheet
	 *            工作表名称
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            中英文字段对应关系的Map
	 * @param firstIndex
	 *            开始索引
	 * @param lastIndex
	 *            结束索引
	 * @throws Exception
	 *             异常
	 */
	public static <T> void fillSheet(WritableSheet sheet, List<T> list,
			LinkedHashMap<String, String> fieldMap, int firstIndex,
			int lastIndex) throws Exception {

		// 定义存放英文字段名和中文字段名的数组
		String[] enFields = new String[fieldMap.size()];
		String[] cnFields = new String[fieldMap.size()];

		// 填充数组
		int count = 0;
		for (Entry<String, String> entry : fieldMap.entrySet()) {
			enFields[count] = entry.getKey();
			cnFields[count] = entry.getValue();
			count++;
		}
		// 填充表头
		for (int i = 0; i < cnFields.length; i++) {
			Label label = new Label(i, 0, cnFields[i]);
			sheet.addCell(label);
		}

		// 填充内容
		int rowNo = 1;
		for (int index = firstIndex; index <= lastIndex; index++) {
			// 获取单个对象
			T item = list.get(index);
			for (int i = 0; i < enFields.length; i++) {
				Object objValue = getFieldValueByNameSequence(enFields[i], item);
				
				String fieldValue = objValue == null ? "" : objValue.toString();
				Label label =null;
				if(objValue!=null){
					if(objValue.getClass().getName().equals("java.lang.Integer")||objValue.getClass().getName().equals("java.lang.Double")){
						Number	label1 = new Number(i, rowNo, Double.valueOf(fieldValue));
						sheet.addCell(label1);
					}else{
						 label = new Label(i, rowNo, fieldValue);
						 sheet.addCell(label);
					}
				}else{
					 label = new Label(i, rowNo, fieldValue);
					 sheet.addCell(label);
				}
			}
			rowNo++;
		}

		// 设置自动列宽
		setColumnAutoSize(sheet, 5);
	}
	public static <T> void fillSheet1(WritableSheet sheet, List<T> list,
			LinkedHashMap<String, String> fieldMap, int firstIndex,
			int lastIndex,String beginDate,String endDate) throws Exception {

		// 定义存放英文字段名和中文字段名的数组
		String[] enFields = new String[fieldMap.size()];
		String[] cnFields = new String[fieldMap.size()];

		// 填充数组
		int count = 0;
		for (Entry<String, String> entry : fieldMap.entrySet()) {
			enFields[count] = entry.getKey();
			cnFields[count] = entry.getValue();
			count++;
		}
		sheet.addCell(new Label(0,0,"统计报表"));
		sheet.mergeCells(0, 0, cnFields.length-1, 1); 
		sheet.addCell(new Label(0,2,"时间"));
		sheet.addCell(new Label(1,2,beginDate));
		sheet.addCell(new Label(2,2,"至"));
		sheet.addCell(new Label(3,2,endDate));
		// 填充表头
		for (int i = 0; i < cnFields.length; i++) {
			Label label = new Label(i, 3, cnFields[i]);
			sheet.addCell(label);
		}

		// 填充内容
		int rowNo = 4;
		for (int index = firstIndex; index <= lastIndex; index++) {
			// 获取单个对象
			T item = list.get(index);
			for (int i = 0; i < enFields.length; i++) {
				Object objValue = getFieldValueByNameSequence(enFields[i], item);
				
				String fieldValue = objValue == null ? "" : objValue.toString();
				Label label =null;
				if(objValue!=null){
					if(objValue.getClass().getName().equals("java.lang.Integer")||objValue.getClass().getName().equals("java.lang.Double")){
						Number	label1 = new Number(i, rowNo, Double.valueOf(fieldValue));
						sheet.addCell(label1);
					}else{
						 label = new Label(i, rowNo, fieldValue);
						 sheet.addCell(label);
					}
				}else{
					 label = new Label(i, rowNo, fieldValue);
					 sheet.addCell(label);
				}
			}
			rowNo++;
		}

		// 设置自动列宽
		setColumnAutoSize(sheet, 5);
	}
	/**
	 * 向工作表中填充数据
	 *
	 * @param sheet
	 * @param list
	 *            数据源
	 * @param normalFieldMap
	 *            普通中英文字段对应关系的Map
	 * @param collectionFieldMap
	 *            集合類中英文字段对应关系的Map
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param firstIndex
	 *            开始索引
	 * @param lastIndex
	 *            结束索引
	 * @throws Exception
	 */
	public static <T> void fillSheet(WritableSheet sheet, List<T> list,
			LinkedHashMap<String, String> normalFieldMap,
			String collectionFieldName, String title, String content,
			int firstIndex, int lastIndex) throws Exception {

		// 定义存放普通英文字段名和中文字段名的数组
		String[] enFields = new String[normalFieldMap.size()];
		String[] cnFields = new String[normalFieldMap.size()];

		// 填充普通字段数组
		int count = 0;
		for (Entry<String, String> entry : normalFieldMap.entrySet()) {
			enFields[count] = entry.getKey();
			cnFields[count] = entry.getValue();
			count++;
		}

		// 填充表头（普通字段）
		for (int i = 0; i < cnFields.length; i++) {
			Label label = new Label(i, 0, cnFields[i]);
			sheet.addCell(label);
		}

		// 填充表头（行转列字段）
		T firstItem = list.get(0);
		List childList = (List) getFieldValueByName(collectionFieldName,
				firstItem);

		int colCount = cnFields.length;
		for (Object obj : childList) {
			Object objValue = getFieldValueByNameSequence(title, obj);
			String fieldValue = objValue == null ? "" : objValue.toString();
			Label label = new Label(colCount, 0, fieldValue);
			sheet.addCell(label);
			colCount++;
		}

		// 填充内容
		int rowNo = 1;
		for (int index = firstIndex; index <= lastIndex; index++) {
			// 获取单个对象
			T item = list.get(index);
			// 填充普通字段內容
			for (int i = 0; i < enFields.length; i++) {
				Object objValue = getFieldValueByNameSequence(enFields[i], item);
				String fieldValue = objValue == null ? "" : objValue.toString();
				Label label = new Label(i, rowNo, fieldValue);
				sheet.addCell(label);
			}

			// 填充集合字段內容
			if (collectionFieldName != null && !collectionFieldName.equals("")) {
				// 拿到集合对象
				List currentList = (List) getFieldValueByName(
						collectionFieldName, item);
				// 将集合对象行转列
				for (int i = 0; i < currentList.size(); i++) {
					Object objValue = getFieldValueByNameSequence(content,
							currentList.get(i));
					String fieldValue = objValue == null ? "" : objValue
							.toString();
					Label label = new Label(i + cnFields.length, rowNo,
							fieldValue);
					sheet.addCell(label);
				}
			}

			rowNo++;
		}

		// 设置自动列宽
		setColumnAutoSize(sheet, 5);
	}

}

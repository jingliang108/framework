package com.tanjin.framework.base.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

public class VmUtil {

	private static final Logger LOGGER = Logger.getLogger(VmUtil.class);

	private static VmUtil instance;

	private OperatingSystemMXBean systemMXBean = null;
	private Method tmsMethod = null;
	private Method fmsMethod = null;
	private Method sclMethod = null;
	private Method pclMethod = null;
	private ClassLoadingMXBean classMXBean = null;
	private ThreadMXBean threadMXBean = null;

	private VmUtil() {
		try {
			systemMXBean = ManagementFactory.getOperatingSystemMXBean();
			classMXBean = ManagementFactory.getClassLoadingMXBean();
			threadMXBean = ManagementFactory.getThreadMXBean();
			tmsMethod = systemMXBean.getClass().getDeclaredMethod("getTotalPhysicalMemorySize");
			tmsMethod.setAccessible(true);
			fmsMethod = systemMXBean.getClass().getDeclaredMethod("getFreePhysicalMemorySize");
			fmsMethod.setAccessible(true);
			sclMethod = systemMXBean.getClass().getDeclaredMethod("getSystemCpuLoad");
			sclMethod.setAccessible(true);
			pclMethod = systemMXBean.getClass().getDeclaredMethod("getProcessCpuLoad");
			pclMethod.setAccessible(true);
			LOGGER.info("Build helper");
		} catch (Exception exp) {
			systemMXBean = null;
			classMXBean = null;
			threadMXBean = null;
			tmsMethod = null;
			fmsMethod = null;
			sclMethod = null;
			pclMethod = null;
			exp.printStackTrace();
			LOGGER.error("Build help error", exp);
		}
	}

	public static synchronized VmUtil getInstance() {
		if (instance == null) {
			instance = new VmUtil();
		}
		return instance;
	}

	public Integer getTotalMemorySize() {
		Integer result = 0;
		synchronized (systemMXBean) {
			try {
				Long value = ((Long) tmsMethod.invoke(systemMXBean)) / 1024l / 1024l;
				result = Integer.parseInt(Long.toString(value));
			} catch (Exception exp) {
				result = 0;
				exp.printStackTrace();
				LOGGER.error(exp);
			}
		}
		return result;
	}

	public Integer getFreeMemorySize() {
		Integer result = 0;
		synchronized (systemMXBean) {
			try {
				Long value = ((Long) fmsMethod.invoke(systemMXBean)) / 1024l / 1024l;
				result = Integer.parseInt(Long.toString(value));
			} catch (Exception exp) {
				result = 0;
				exp.printStackTrace();
				LOGGER.error(exp);
			}
		}
		return result;
	}

	public Double getSystemCpuLoad() {
		Double result = 0.0000d;
		synchronized (systemMXBean) {
			try {
				result = (Double) sclMethod.invoke(systemMXBean);
				if (result <= 0.0000d) {
					result = 0.0000d;
				}
			} catch (Exception exp) {
				result = 0.0000d;
				exp.printStackTrace();
				LOGGER.error(exp);
			}
		}
		return roundDouble(result);
	}

	public Double getProcessCpuLoad() {
		Double result = 0.0000d;
		synchronized (systemMXBean) {
			try {
				result = (Double) pclMethod.invoke(systemMXBean);
				if (result <= 0.0000d) {
					result = 0.0000d;
				}
			} catch (Exception exp) {
				result = 0.0000d;
				exp.printStackTrace();
				LOGGER.error(exp);
			}
		}
		return roundDouble(result);
	}

	public Integer getClassCount() {
		return classMXBean.getLoadedClassCount();
	}

	public Integer getThreadCount() {
		return threadMXBean.getThreadCount();
	}

	public Double getLoadIndex() {
		try {
			double mem = ((this.getTotalMemorySize().doubleValue() - this.getFreeMemorySize().doubleValue())
					/ this.getTotalMemorySize().doubleValue());
			double cpu = this.getSystemCpuLoad();
			return roundDouble((mem + cpu) / 2.0f);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOGGER.error(exp);
			return 2.0000d;
		}
	}

	private Double roundDouble(Double input) {
		BigDecimal bigDecimal = new BigDecimal(input);
		bigDecimal = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}

	public byte[] objectToBytes(Object object) {
		ObjectOutputStream stream = null;
		ByteArrayOutputStream bit = null;
		try {
			bit = new ByteArrayOutputStream();
			stream = new ObjectOutputStream(bit);
			stream.writeObject(object);
			return bit.toByteArray();
		} catch (Exception exp) {
			exp.printStackTrace();
			return null;
		} finally {
			try {
				if(bit!=null){
					bit.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(stream!=null){
					stream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			stream = null;
			bit = null;
		}
	}

	public Object bytesToObject(byte[] bs) {
		ObjectInputStream stream = null;
		ByteArrayInputStream bit = null;
		try {
			bit = new ByteArrayInputStream(bs);
			stream = new ObjectInputStream(bit);
			return stream.readObject();
		} catch (Exception exp) {
			exp.printStackTrace();
			return null;
		} finally {
			try {
				if(bit!=null){
					bit.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(stream!=null){
					stream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			stream = null;
			bit = null;
		}
	}

}

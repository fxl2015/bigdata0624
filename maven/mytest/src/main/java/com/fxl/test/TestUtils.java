package com.fxl.test;

import org.apache.log4j.Logger;

public class TestUtils {
	private static Logger logger=Logger.getLogger(TestUtils.class);
	
	public static void printInfo(String info) {
		logger.info(info);
	}
	
	public static void printDebug(String debugInfo) {
		logger.debug(debugInfo);
	}
	
	public static void printWarn(String warnInfo) {
		logger.warn(warnInfo);
	}
	
	public static void printError(String errorInfo) {
		logger.error(errorInfo);
	}
}

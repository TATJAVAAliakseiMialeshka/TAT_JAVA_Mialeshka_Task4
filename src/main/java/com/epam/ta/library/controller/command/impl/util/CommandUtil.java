package com.epam.ta.library.controller.command.impl.util;


public class CommandUtil {

	public static String[] splitParams(String paramStr) {

		String[] params = null;
		if (null != paramStr && !paramStr.isEmpty()) {
			String delimiter = "'+";
			params = paramStr.replaceAll("^['\\s]", "").replaceAll("'\\s+'", "''").split(delimiter);
		}

		return params;
	}
}

package com.conflux.common;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class ApplicationUtils {

	public static final String PATH_NAME = System.getProperty("user.home")
			+ File.separator + ".risk_management" + File.separator;

	public static final String REPORTS_PATH_NAME = PATH_NAME + "reports"
			+ File.separator;

	public static Random random = new Random();

	public ApplicationUtils() throws IOException {
		if (!new File(PATH_NAME).isDirectory()) {
			new File(PATH_NAME).mkdir();
		}
	}

	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

}

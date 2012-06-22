package com.github.enr.xite;

import java.io.File;

import com.google.common.base.Preconditions;

/*
 * utility pertaining file names
 */
public class Filenames {

	private Filenames() {
		// only static methods here
	}
	
	public static String base(File file) {
		Preconditions.checkNotNull(file);
		String name = file.getName();
		if (! name.contains(".")) {
			return name;
		}
		String[] tokens = name.split("\\.(?=[^\\.]+$)");
		return tokens[0];
	}
}

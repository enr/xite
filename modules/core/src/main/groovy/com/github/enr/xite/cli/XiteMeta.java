package com.github.enr.xite.cli;

import com.github.enr.clap.api.AppMeta;

/**
 * Xite application metadata, used from Clap.
 *
 */
public class XiteMeta implements AppMeta {

	@Override
	public String name() {
		return "xite";
	}

	@Override
	public String version() {
		return "0.2-SNAPSHOT";
	}
	
	@Override
	public String displayName() {
		return "Xite";
	}
}

package com.github.enr.xite;


public class StandardOutReporter implements Reporter {

	Level level = Level.WARN;

	@Override
	public void debug(String message) {
		if (isDebugEnabled()) {
			System.out.println(String.format(message));
		}
	}

	@Override
	public void debug(String template, Object... args) {
		if (isDebugEnabled()) {
			System.out.println(format(template, args));
		}
	}

	@Override
	public void info(String message) {
		if (isInfoEnabled()) {
			System.out.println(String.format(message));
		}
	}

	@Override
	public void info(String template, Object... args) {
		if (isInfoEnabled()) {
			System.out.println(format(template, args));
		}
	}

	@Override
	public void warn(String message) {
		System.out.println(String.format(message));
	}

	@Override
	public void warn(String template, Object... args) {
		System.out.println(format(template, args));
	}

	@Override
	public void out(String message) {
		System.out.println(String.format(message));
	}

	@Override
	public void out(String template, Object... args) {
		System.out.println(format(template, args));
	}

	@Override
	public void err(String message) {
		System.err.println(String.format(message));
	}

	@Override
	public void err(String template, Object... args) {
		System.err.println(format(template, args));
	}

	private static String format(String template, Object... args) {
		return String.format(template, args);
	}
	
	private boolean isDebugEnabled() {
		return (level.equals(Level.DEBUG));
	}

	private boolean isInfoEnabled() {
		return ((level.equals(Level.DEBUG)) || (level.equals(Level.INFO)));
	}
	
	@Override
	public void setLevel(Level level) {
		this.level = level;
	}

}

package com.github.enr.xite;

/*
 * interface for classes used to log messages and send output to the user.
 * messages intended to be logged are written based on a level such as logging libraries.
 * messages to the user are always sent to system output (standard or error).
 */
public interface Reporter {
	public enum Level {
		DEBUG,INFO,WARN
	}
	
	/*
	 * sets the minimum level for messages
	 */
	void setLevel(Reporter.Level level);
	
	void debug(String message);
	void debug(String template, Object... args);
	
    void info(String message);
    void info(String template, Object... args);
    
    void warn(String message);
    void warn(String template, Object... args);

    /*
     * report without level, intended to be used to write to the standard out
     */
    void out(String message);
    void out(String template, Object... args);

    /*
     * report without level, intended to be used to write to the standard err
     */
    void err(String message);
    void err(String template, Object... args);
}

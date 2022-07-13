package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;


/**
 * @author Young-jun Bae
 *
 */

public class Logger{

	private static org.apache.log4j.Logger fileLogger = null ;
	private static org.apache.log4j.Logger consoleLogger = null ;
	private int level = 0;	
	private static HashMap cache = new HashMap();

	private Logger(String name){

		synchronized(org.apache.log4j.Logger.class){

			fileLogger = 
				org.apache.log4j.Logger.getLogger("[file]."+name);
			consoleLogger = 
				org.apache.log4j.Logger.getLogger("[console]."+name);
		}

		setLevel(getFileLogLevel(), fileLogger);
		setLevel(getConsoleLogLevel(), consoleLogger);
		setPatternAndConsoleAppender();
		setPatternAndFileAppender();
	}


	public static Logger getLogger(Class cls){
		String clsName = cls.getName();
		return getLogger(clsName);
	}

	public static Logger getLogger(String name) {

		Logger logger = (Logger) cache.get(name);
		if (logger != null){
			return logger;
		}
		logger = new Logger(name);
		if (logger != null){
			cache.put(name, logger);
		}
		return logger;
	}


	public static void debug(String mesg){
		fileLogger.debug(mesg);
		consoleLogger.debug(mesg);
	}

	public static void info(String mesg){
		fileLogger.info(mesg);
		consoleLogger.info(mesg);
	}

	public static void warn(String mesg){
		fileLogger.warn(mesg);
		consoleLogger.warn(mesg);
	}

	public static void error(String mesg){
		fileLogger.error(mesg);
		consoleLogger.error(mesg);
	}
	
	private static void setPatternAndFileAppender(){

		PatternLayout layout = null;
//		if(isLogVerbose()){
//			layout = new PatternLayout("%d %-5p %C %l %F [%t]: %m%n");	
//		} else{
			layout = new PatternLayout("%d{HH:mm:ss} %-5p [%t]: %m%n");	
//		}
		FileAppender appender;
		try{
       		DailyRollingFileAppender rfa = 
				new DailyRollingFileAppender
					(layout, getLogFileName(), "'.'yyyy-MM-dd");
			appender = rfa;
			fileLogger.addAppender(appender);	

		} catch(IOException e){
			System.err.println("[MSH Warning] : Log file problem!!");
		}
	}
	
	private static void setPatternAndConsoleAppender(){

		PatternLayout layout = null;
//		if(isLogVerbose()){
//			layout = new PatternLayout("%d %-5p %c %l %F [%t]: %m%n");	
//		} else{
			layout = new PatternLayout("%d{HH:mm:ss} %-5p [%t]: %m%n");	
//		}
		ConsoleAppender appender;
		ConsoleAppender rfa = new ConsoleAppender(layout);
		appender = rfa;
		consoleLogger.addAppender(appender);	
	}

	private static void setLevel(String logLevel, 
		org.apache.log4j.Logger l){

		if(logLevel.equals("DEBUG")){
			l.setLevel(org.apache.log4j.Level.DEBUG);
		} else if(logLevel.equals("INFO")){
			l.setLevel(org.apache.log4j.Level.INFO);
		} else if(logLevel.equals("WARN")){
			l.setLevel(org.apache.log4j.Level.WARN);
		} else if(logLevel.equals("ERROR")){
			l.setLevel(org.apache.log4j.Level.ERROR);
		} else if(logLevel.equals("FATAL")){
			l.setLevel(org.apache.log4j.Level.FATAL);
        } else {
			l.setLevel(org.apache.log4j.Level.DEBUG);
		}
	}



	
	private static String getLogFileName(){
		ServerProperties props = ServerProperties.getInstance();
		String logDir = 
			props.getProperty("MSH.LOG.FILE.DIR", System.getProperty("user.home"));
		File logDirFile = new File(logDir);
		logDirFile.mkdirs();
		return new String(logDir+"/msh.log");
	}



	private static String getLevelString(String s){

		// UNKOWN LEVEL is setted to DEBUG
	
		if(s== null) return "DEBUG";
		if(s.trim().equals("0")) return "DEBUG";
		if(s.trim().equals("1")) return "INFO";
		if(s.trim().equals("2")) return "WARN";
		if(s.trim().equals("3")) return "ERROR";
		if(s.trim().equals("4")) return "FATAL";
		return "DEBUG";
	}


	private static String getFileLogLevel(){
		ServerProperties props = ServerProperties.getInstance();
		String logLevel = 
			props.getProperty("MSH.LOG.FILE.LEVEL",
				 System.getProperty("user.home"));
		String level = getLevelString(logLevel);

		return level;				
	}


	private static String getConsoleLogLevel(){
		ServerProperties props = ServerProperties.getInstance();
		String logLevel = 
			props.getProperty("MSH.LOG.CONSOLE.LEVEL",
				 System.getProperty("user.home"));
		String level = getLevelString(logLevel);

		return level;				
	}


	private static boolean isLogVerbose(){
		boolean result = false;
		ServerProperties props = ServerProperties.getInstance();
		//default value OFF
		String isLogVerbose = "OFF";
		isLogVerbose = 
			props.getProperty("MSH.LOG.VERBOSE", 
				System.getProperty("user.home"));
		if(isLogVerbose.equals("ON")){
			result = true;
		}
		return result;
	}
}

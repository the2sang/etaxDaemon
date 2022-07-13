package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Log4jConfigure{

	private static Properties props = null;

	public Log4jConfigure(){
	}

    public static void initLogger(Properties pro){
		props = pro;

		///////////////////////////////////////////////////////////////////// 	
        //String delim = "kr.co.bizframe.ebxml";
		//String level = "DEBUG";
		//String consoleLevel = "INFO";
		//String fileLevel = "INFO";
		//String consoleAppender = "ConsoleAppender";
		//String fileAppender = "FileAppender";
		//String fileName = "/home/ebms/msh.log";
		//String fileMaxSize = "100KB";

        String delim = "kr.co.bizframe.ebxml";
		String consoleAppender = "ConsoleAppender";
		String fileAppender = "FileAppender";
		String level = "DEBUG";
		String fileName = getLogFileName();
		String fileLevel = getFileLogLevel(); 
		String fileMaxSize = getFileLogMaxSize(); 
		String consoleLevel = getConsoleLogLevel(); 
		boolean isVerbose = isLogVerbose();			


        Properties property = new Properties();
        property.setProperty("log4j.logger."+delim , level+","+consoleAppender+","+
			fileAppender);
        property.setProperty("log4j.appender."+consoleAppender,
			 "org.apache.log4j.ConsoleAppender");
        property.setProperty("log4j.appender."+consoleAppender+".threshold",
			 consoleLevel);
		property.setProperty("log4j.appender."+consoleAppender+".layout",
			 "org.apache.log4j.PatternLayout ");
		if(isVerbose){
        property.setProperty("log4j.appender."+consoleAppender+".layout.ConversionPattern",
             "%d %-5p %c [%t]: %m%n");
		} else{
        property.setProperty("log4j.appender."+consoleAppender+".layout.ConversionPattern",
             "%d{HH:mm:ss} %-5p [%t]: %m%n");
		}
        property.setProperty("log4j.appender."+fileAppender,
			 "org.apache.log4j.DailyRollingFileAppender");
		property.setProperty("log4j.appender."+fileAppender+".layout",
			 "org.apache.log4j.PatternLayout ");
		property.setProperty("log4j.appender."+fileAppender+".threshold",
			 fileLevel);
		if(isVerbose){
        property.setProperty("log4j.appender."+fileAppender+".layout.ConversionPattern",
             "%d %-5p %c [%t]: %m%n");
		} else{
        property.setProperty("log4j.appender."+fileAppender+".layout.ConversionPattern",
             "%d{HH:mm:ss} %-5p [%t]: %m%n");
		}
        property.setProperty("log4j.appender."+fileAppender+".File", fileName);
        //property.setProperty("log4j.appender."+fileAppender+".MaxFileSize", fileMaxSize);

        property.setProperty("log4j.additivity."+delim, "false");
        PropertyConfigurator.configure(property);

    }



    private static String getLevelString(String s){

        // UNKOWN LEVEL is setted to DEBUG

       /* if(s== null) return "DEBUG";
        if(s.trim().equals("0")) return "DEBUG";
        if(s.trim().equals("1")) return "INFO";
        if(s.trim().equals("2")) return "WARN";
        if(s.trim().equals("3")) return "ERROR";
        if(s.trim().equals("4")) return "FATAL";*/
        return "DEBUG";
    }


    public static String getLogFileName(){
        String logPath =
            props.getProperty("MSH.LOG.FILE.DIR", System.getProperty("user.home"));
        String logFile =
            props.getProperty("MSH.LOG.FILE.FILENAME", "TaxInvoice.log");

        File logDirFile = new File(logPath);

		if(!logDirFile.exists()){
			System.err.println("Log Warning : Log Dir["+logPath+"] is not exist");
			System.err.println("Log Warning : msh is making Log Dir["+logPath+"]");
		}
		logDirFile.mkdirs();
//		System.out.println("Log Dir = "+logDir+"/"+fileName);
        return new String(logPath+"/"+ logFile);
    }



    private static String getFileLogLevel(){
        String logLevel =
            props.getProperty("MSH.LOG.FILE.LEVEL",
                 System.getProperty("user.home"));
        String level = getLevelString(logLevel);

        return level;
    }


    private static String getConsoleLogLevel(){
        String logLevel =
            props.getProperty("MSH.LOG.CONSOLE.LEVEL",
                 System.getProperty("user.home"));
        String level = getLevelString(logLevel);

        return level;
    }


    private static boolean isLogVerbose(){
        boolean result = false;
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

    private static String getFileLogMaxSize(){
        String fileLogMaxSize 
            = props.getProperty("MSH.LOG.FILE.SIZE","100KB");
        return fileLogMaxSize;
    }



}

package loggerPackage;



import org.apache.log4j.*;


public class Log{
	
	/*private Logger logger ;

	
	public Log(){
		logger = Logger.getLogger(Log.class);
		
	}

	public void writeLog(String message){
		logger.info(message);
	}*/
	
	
	private Logger logger ;

	
	public Log(){
		//PropertyConfigurator.configure("../log4j.properties");
		
		//logger = Logger.getLogger(Log.class);
		logger = Logger.getLogger("MyLog");
	}

	public void writeLog(String message){
		logger.info(message);
		
	}
	

}

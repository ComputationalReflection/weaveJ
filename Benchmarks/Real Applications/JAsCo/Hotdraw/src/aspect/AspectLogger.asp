package aspect;

import loggerPackage.Log;


class AspectLogger {
	Log l = new Log();

	
	
	hook OpenLogger{
		
		OpenLogger(method(..args)){
			execution(method);
		}
		
		after(){
			global.l.writeLog("ACTIVATING LOG....");
		}
	}//hook OpenLogger
	
	
	
	hook CloseLogger{
		CloseLogger(method(..args)){
			execution(method);
		}
	
		before(){
			global.l.writeLog("DISACTIVATING LOG....");
		}
	}//hook CloseLogger
	
	
	
	hook writeLogger{
		writeLogger(method(..args)){
			execution(method);
		}
		
		before(){
			String message = (String)args[1];
			global.l.writeLog(message);
		}
	
	}//hook writeLogger
		
}

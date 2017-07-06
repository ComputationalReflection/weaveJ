package aspect;

import java.text.DateFormat;
import java.util.Date;
import profilerPackage.*;
import loggerPackage.Log;
import utilsClass.*;
import java.util.Map;



class AspectProfiler {
	
	Profiler p = new Profiler();
	String message = "";
	
	
   hook OpenProfiler{
		
		OpenProfiler(method(..args)){
			execution(method);
		}
		
		after(){
			global.p.writeProfiler("ACTIVATING PROFILER....");
		}
	}//hook OpenProfiler
	
	
	
	hook CloseProfiler{
		CloseProfiler(method(..args)){
			execution(method);
		}
	
		before(){
			global.p.writeProfiler("DISACTIVATING PROFILER....");
		}
	}//hook CloseProfiler
	
	
	hook writeProfiler{
		writeProfiler(method(..args)){
			execution(method);
		}
		
		before() {
			global.p.initTime();
		}
		
		after(){
			global.message = (String)args[1];
			global.message = global.message + ", Time necesary: " + global.p.endTime() + "ms";
			global.p.writeProfiler(global.message);
		}
	
	}//hook writeLogger
	
	
	
	hook profilerDemo{
		profilerDemo(method(..args)){
			execution(method);
		}
		
		before() {
			global.p.initTimeDemo();
			global.p.writeProfiler("stating demo....");
		}
		
		after(){
			global.message = "Time necesary to execute demo: " + global.p.endTimeDemo() + "ms";
			global.p.writeProfiler(global.message);
		}
	
	}//hook writeLogger
		
	
}


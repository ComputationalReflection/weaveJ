package aspects;

import jasco.runtime.aspect.*;
import application.Program;

public class LoggingAspect {
	//private boolean isEnabled=true;
	static public long sumTotal = 0;
	public static int i = 0;
	

//	
//	public boolean isEnabled() {
//		return isEnabled;
//	} 
//	
//	
//	
//	
//	public void isEnabled(boolean enabled) {
//		isEnabled=enabled;
//	}
	
	hook LoggingHook {
		
		LoggingHook(method(..args)) {
			execution(method);
		}
//		isApplicable() {
//			return global.isEnabled();
//		}
	//	before() {
		//	System.out.println("BEFORE ASPECT");
	//	}
		
		around() {

			//Full
			//Problemas con String lenght hashCode etc
			
			//De momento quitamos el arguments array .lenght , es muy heavy sustituirlo por el número pero es lo que hay, yo lo sé en compilacion y ellos no
			global.sumTotal+= Program.length(thisJoinPoint.getSignature())+Program.length(thisJoinPoint.getCalledObject().toString())+thisJoinPoint.getArgumentsArray().length;
			
			
			for (Object o : thisJoinPoint.getArgumentsArray())
			{
				
					global.sumTotal += Program.length(o.toString()) + Program.length(o.getClass());
			}
			return 1;
			//return proceed();
		}
		
//		after() {
//
//		//System.out.println("AFTER ASPECT");
//		}
	}
}

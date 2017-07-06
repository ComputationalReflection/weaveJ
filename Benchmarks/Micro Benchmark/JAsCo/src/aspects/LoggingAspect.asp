package aspects;

import jasco.runtime.aspect.*;
import application.*;

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
//		before() {
//			global.sumTotal+= Util.length(thisJoinPoint.getSignature())+Util.length(thisJoinPoint.getCalledObject().toString())+thisJoinPoint.getArgumentsArray().length;
//			
//			
//			for (Object o : thisJoinPoint.getArgumentsArray())
//			{
//				
//					global.sumTotal += Util.length(o.toString()) ;//+ Util.length(o.getClass());
//			}
//		} 
//		
		around() { 
			//Full
			//Problemas con String lenght hashCode etc
			
			//De momento quitamos el arguments array .lenght , es muy heavy sustituirlo por el número pero es lo que hay, yo lo sé en compilacion y ellos no
			global.sumTotal+= Util.length(thisJoinPoint.getSignature())+Util.length(thisJoinPoint.getCalledObject().toString())+thisJoinPoint.getArgumentsArray().length;
			
			for (Object o : thisJoinPoint.getArgumentsArray())
			{
				
					global.sumTotal += Util.length(o.toString());// + Util.length(o.getClass());
			}
			return 1;
			//return proceed();
	}
		 
/*		after() {
//for(int i=0;i<3;i++)
		//	System.out.println("AFTER");
	global.sumTotal+= Util.length(thisJoinPoint.getSignature())+Util.length(thisJoinPoint.getCalledObject().toString());
	Object[] arguments = new Object[]{5,6,23};
	for (Object o : arguments) {
		global.sumTotal += Util.length(o.toString());//+ Util.length(o.getClass());
	}
		}*/
	}
}

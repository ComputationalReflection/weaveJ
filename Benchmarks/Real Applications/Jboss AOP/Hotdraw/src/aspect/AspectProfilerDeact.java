package aspect;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.joinpoint.Invocation;

import profilerPackage.Profiler;

public class AspectProfilerDeact implements Interceptor{

	public Object invoke(Invocation invocation) throws Throwable {
		AspectProfilerAct.p.writeProfiler("DISACTIVATING PROFILER....");
		return invocation.invokeNext();
	}
	public String getName() {
		return "deacProf";
	}


}

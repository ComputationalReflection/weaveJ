package aspect;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.joinpoint.Invocation;

import profilerPackage.Profiler;

public class AspectProfilerAct implements Interceptor {

	static Profiler p = new Profiler();

	public Object invoke(Invocation invocation) throws Throwable {
		Object ret = invocation.invokeNext();
		p.writeProfiler("ACTIVATING PROFILER....");
		return ret;
	}

	public String getName() {
		return "actProf";
	}

}

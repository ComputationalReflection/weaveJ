package aspect;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.joinpoint.Invocation;

import profilerPackage.Profiler;

public class AspectProfilerDemo implements Interceptor {


	public Object invoke(Invocation invocation) throws Throwable {
		Profiler p = AspectProfilerAct.p;
		p.initTimeDemo();
		p.writeProfiler("starting demo....");
		Object ret = invocation.invokeNext();
		long endTime = p.endTimeDemo();
		p.writeProfiler("Time necesary to execute demo: " + endTime + "ms");
		return ret;
	}

	public String getName() {
		return "demoProf";
	}

}

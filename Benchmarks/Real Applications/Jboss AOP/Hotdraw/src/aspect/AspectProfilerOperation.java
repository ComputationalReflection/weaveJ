package aspect;


import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;

import profilerPackage.Profiler;

public class AspectProfilerOperation implements Interceptor {

	public Object invoke(Invocation invocation) throws Throwable {
		String message = (String) ((MethodCalledByMethodInvocation) invocation).getArguments()[1];
		Profiler p = AspectProfilerAct.p;
		p.initTime();
		Object ret = invocation.invokeNext();
		p.writeProfiler(message + ", Time necesary: " + p.endTime() + "ms");
		return ret;
	}

	public String getName() {
		return "ProfOp";
	}
}

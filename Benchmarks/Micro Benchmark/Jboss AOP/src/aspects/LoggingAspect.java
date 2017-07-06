package aspects;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;

import application.Program;
import application.Util;

public class LoggingAspect implements Interceptor {
	private boolean isEnabled = true;
	static public long sumTotal = 0;
	public static int i = 0;

	public boolean isEnabled() {
		return isEnabled;
	}

	public void isEnabled(boolean enabled) {
		isEnabled = enabled;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return "INTERCEPTOR";
	}

	public Object invoke(Invocation invocation) throws Throwable {
		sumTotal += Util.length(invocation.toString()) + Util.length(invocation.getTargetObject().toString())
				+ ((MethodCalledByMethodInvocation) invocation).getArguments().length;

		for (Object o : ((MethodCalledByMethodInvocation) invocation).getArguments()) {
			sumTotal += Util.length(o.toString());// +
													// Util.length(o.getClass());
		}
		return 1;
	}
}

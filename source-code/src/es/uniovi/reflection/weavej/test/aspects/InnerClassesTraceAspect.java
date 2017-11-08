package es.uniovi.reflection.weavej.test.aspects;

import es.uniovi.reflection.weavej.test.component.TestFinalFields.InnerClass;

public class InnerClassesTraceAspect {

	public static void fieldTrace(InnerClass instance, int value) {
		System.out.println("Before modifying or after accessing the final field 'value'");
	}
}

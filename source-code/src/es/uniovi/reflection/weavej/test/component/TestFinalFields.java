package es.uniovi.reflection.weavej.test.component;

import org.junit.Test;

import es.uniovi.reflection.weavej.weaver.Weaver;

public class TestFinalFields {

	public class InnerClass {

		public final int value = 2;
	}

	static class StaticInnerClass {

		public final int valueStatic = 5;
	}

	@Test
	public void test() throws Throwable {

		Weaver.weaveAspectForFieldAccessGetAfter("es.uniovi.reflection.weavej.test.component.TestFinalFields$InnerClass",
				"value", "es.uniovi.reflection.weavej.test.aspects.InnerClassesTraceAspect", "fieldTrace", int.class);
	}

}

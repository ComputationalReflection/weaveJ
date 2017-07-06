package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import aspects.LoggingAspect;
import es.uniovi.reflection.weaver.Weaver;

public class Program {
	int testigo = 0;

	public void /* int */ addInt(Integer a, Integer b, Integer c) {
		// return a + b + c;
		testigo = a + b + c;
	}

	public void /* int */ testAddInt(int b, int c, int d) {

		// return b + c + d;

		testigo = b + c + d;
	}

	int value;

	public Program() {
		this.value = 0;
	}

	public Program(int value) {
		this.value = value;
	}

	public static void main(String[] args) throws IOException {
		// try {
		// System.in.read();
		// } catch(Exception e) {
		// ;
		// }
		String componentClass = "application.Program";
		String componentMethod = "addInt";
		String aspectClass = "aspects.LoggingAspect";
		String aspectMethod = "methodAround";
		// String aspectMethod = "methodBefore";
		// String aspectMethod = "methodAfter";
		try {
			// AROUND
			Weaver.weaveAspectForMethodAround(componentClass, componentMethod, aspectClass, aspectMethod, void.class,
					Integer.class, Integer.class, Integer.class);
			// Weaver.weaveAspectForMethodBefore(componentClass,
			// componentMethod, aspectClass, aspectMethod, void.class,
			// Integer.class, Integer.class, Integer.class);
			// Weaver.weaveAspectForMethodAfter(componentClass, componentMethod,
			// aspectClass, aspectMethod, void.class,
			// Integer.class, Integer.class, Integer.class);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		Program program = new Program();
		long beginLong = 0, endLong = 0;
		int iterations = 1;
		long totalTime = 0;
		// int auxiliar = a.addInt(5, 6, 27);
		program.addInt(5, 6, 27);
		for (int j = 0; j < iterations; j++) {
			System.out.println("Iter " + j);
			beginLong = System.currentTimeMillis();
			for (long i = 0; i <= 10000000; i++) {
				// auxiliar = a.addInt(5, 6, 27);
				program.addInt(5, 6, 27);
				// System.out.println("Returned value " + auxiliar);
			}
			endLong = System.currentTimeMillis();
			totalTime = totalTime + (endLong - beginLong);
		}
		System.out.println("The media duration is " + totalTime / iterations + " (ms.)");
		System.out.println(LoggingAspect.sumTotal);

		if (args.length == 0) {
			BufferedWriter bw = new BufferedWriter(new FileWriter("temp/output.txt"));
			bw.write("" + totalTime);
			bw.close();
		} else
			System.out.println("memory test");
		// auxiliar = a.testAddInt(5, 7, 21);
		// OSCAR program.testAddInt(5, 7, 21);

		// OSCAR System.out.println(Memory.getPeakMemoryUsage("java.exe"));

		// try {
		// System.in.read();
		// } catch(Exception e) {
		// ;
		// }

		/*
		 * try { System.in.read(); } catch(Exception e) { ; }
		 * 
		 * beginLong = System.currentTimeMillis(); for (int i = 0; i <= 10; i++)
		 * { auxiliar = a.addInt(5, 6, 27); System.out.println("Returned value "
		 * + auxiliar); } endLong = System.currentTimeMillis();
		 * System.out.println("The time of this process was " +
		 * (endLong-beginLong)+" (ms.)" );
		 * 
		 * try { System.in.read(); } catch(Exception e) { ; }
		 * 
		 * try { System.in.read(); } catch(Exception e) { ; }
		 * 
		 * try { System.in.read(); } catch(Exception e) { ; }
		 * 
		 * beginLong = System.currentTimeMillis(); for (int i = 0; i <= 10; i++)
		 * { auxiliar = a.addInt(5, 6, 27); System.out.println("Returned value "
		 * + auxiliar); } endLong = System.currentTimeMillis();
		 * System.out.println("The time of this process was " +
		 * (endLong-beginLong)+" (ms.)" );
		 * 
		 * try { System.in.read(); } catch(Exception e) { ; }
		 */

		return;
	}

}

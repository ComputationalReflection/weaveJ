package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import aspects.LoggingAspect;

public class Program {

	public static int length(String s) {
		return s.length();
	}

	public static int length(Class c) {
		return c.toString().length();
	}

	int testigo = 0;

	public void /* int */ addInt(Integer a, Integer b, Integer c) {
		// return a + b + c;
		testigo = a + b + c;
	}

	public void /* int */ addIntAround(Integer a, Integer b, Integer c) {
		// return a + b + c;

		LoggingAspect.methodAround("addInt", this, 5, 6, 27);
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
				// AROUND
				program.addIntAround(5, 6, 27);
				// BEFORE
				// LoggingAspect.methodBefore("addInt", program, 5, 6, 27);
				// program.addInt(5, 6, 27);
				// AFTER
				// LoggingAspect.methodAfter("addInt", program);
				// System.out.println("Returned value " + auxiliar);
			}
			endLong = System.currentTimeMillis();
			totalTime = totalTime + (endLong - beginLong);
			System.out.println(endLong - beginLong);
		}
		System.out.println("The media duration is " + totalTime / iterations + " (ms.)");
		System.out.println(LoggingAspect.sumTotal);
		if (args.length == 0) {
			BufferedWriter bw = new BufferedWriter(new FileWriter("temp/output.txt"));
			bw.write("" + totalTime);
			bw.close();
		} else
			System.out.println("memory test");

		return;
	}

}

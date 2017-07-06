package example.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUItoFileController {

	private static List<String> traceAspectLines = new ArrayList<String>();
	private static List<String> profileAspectLines = new ArrayList<String>();

	private static final File WEAVING_FILE = new File("src/example/weaving.txt");
	private static final String PROFILE_CLASS_NAME = "example.aspect.ProfilerAspect";
	private static final String TRACE_CLASS_NAME = "example.aspect.TraceAspect";
	private static final String COMP_CLASS_NAME = "example.component.CreditCard";

	public static String addTraceAspect(String compMethodName, boolean isBefore) {
		String joinPoint = isBefore ? "Before" : "After";
		String aspectMethod = "trace" + joinPoint;
		String res = addAspect(TRACE_CLASS_NAME, aspectMethod, compMethodName, joinPoint, traceAspectLines);
		return String.format("[%s] %s", joinPoint, res);
	}

	private static String addAspect(String aspectClass, String aspectMethod, String compMethodName, String joinPoint,
			List<String> lineContainer) {
//		String commandLine = String.format("%s - %s %s %s %s double double\n", joinPoint.toUpperCase(), aspectClass,
//				aspectMethod, COMP_CLASS_NAME, compMethodName);
//		lineContainer.add(commandLine);
		String fileLine = String.format("%s - %s %s %s %s double double\n", joinPoint.toUpperCase(), aspectClass,
				removeSignature(aspectMethod), COMP_CLASS_NAME, removeSignature(compMethodName));
		addStringToFile(fileLine, true);
		lineContainer.add(fileLine);
		return String.format("CreditCard.%s", compMethodName);
	}

	private static String addAspectWithoutFile(String aspectClass, String aspectMethod, String compMethodName,
			String joinPoint, List<String> lineContainer) {

		String commandLine = String.format("%s - %s %s %s %s double double\n", joinPoint.toUpperCase(), aspectClass,
				aspectMethod, COMP_CLASS_NAME, compMethodName);
		lineContainer.add(commandLine);
		String traceExtraInfo = aspectClass.contains("Trace") ? "[" + joinPoint + "] " : "";
		return String.format("%sCreditCard.%s", traceExtraInfo, compMethodName);
	}

	public static String addProfileAspect(String compMethodName) {
		return addAspect(PROFILE_CLASS_NAME, "profileOperation", compMethodName, "AROUND", profileAspectLines);
	}

	public static void removeTraceIndexes(int[] indexes) {

		removeIndexes(indexes, traceAspectLines);
	}

	public static void removeProfileIndexes(int[] indexes) {

		removeIndexes(indexes, profileAspectLines);
	}

	public static List<String> getFileLines() {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(WEAVING_FILE));
			while (br.ready()) {
				String[] line = br.readLine().split(" ");
				if (line.length >= 5)
					lines.add(lineToSummary(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	private static String removeSignature(String methodName) {
		if (methodName.contains("("))
			return methodName.split("\\(")[0];
		return methodName;
	}

	private static String lineToSummary(String[] line) {
		return addAspectWithoutFile(line[2], removeSignature(line[3]), removeSignature(line[5]),
				line[0].substring(0, 1) + line[0].substring(1, line[0].length()).toLowerCase(),
				line[2].contains("Profiler") ? profileAspectLines : traceAspectLines);
	}

	private static void removeIndexes(int[] indexes, List<String> container) {
		int counter = 0;
		for (int index : indexes)
			container.remove(index - counter++);

		String allLines = "";
		for (String line : traceAspectLines)
			allLines += line;
		for (String line : profileAspectLines)
			allLines += line;

		addStringToFile(allLines, false);
	}

	private static void addStringToFile(String lines, boolean append) {
		try {
			FileWriter bw = new FileWriter(WEAVING_FILE, append);
			bw.append(lines);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

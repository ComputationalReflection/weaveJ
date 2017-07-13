package es.uniovi.reflection.weaver.dynamicGeneration;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Opcodes;

/**
 * Static class used by the dynamic generators to get specific information
 * relating java.lang.Class objects with different kinds of VM opcodes.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */
public class OpcodesUtil {
	private static Map<Class<?>, Integer> loadInstructionsMap = initializeMap();

	public static int getDupOpcode(Class<?> c) {
		if (c.equals(double.class) || c.equals(long.class))
			return Opcodes.DUP2;
		return Opcodes.DUP;
	}

	public static int getLoadOpcode(Class<?> c) {
		Integer opcode = loadInstructionsMap.get(c);
		return opcode != null ? opcode : Opcodes.ALOAD;
	}

	public static int getPopOpcode(Class<?> c) {
		if (c.equals(double.class) || c.equals(long.class))
			return Opcodes.POP2;
		return Opcodes.POP;
	}

	public static int getReturnOpcode(Class<?> c) {
		return getLoadOpcode(c) + 151;
	}

	public static int getStoreOpcode(Class<?> c) {
		return getLoadOpcode(c) + 33;
	}

	private static Map<Class<?>, Integer> initializeMap() {
		loadInstructionsMap = new HashMap<Class<?>, Integer>();
		loadInstructionsMap.put(int.class, Opcodes.ILOAD);
		loadInstructionsMap.put(double.class, Opcodes.DLOAD);
		loadInstructionsMap.put(boolean.class, Opcodes.ILOAD);
		loadInstructionsMap.put(byte.class, Opcodes.ILOAD);
		loadInstructionsMap.put(char.class, Opcodes.ILOAD);
		loadInstructionsMap.put(short.class, Opcodes.ILOAD);
		loadInstructionsMap.put(float.class, Opcodes.FLOAD);
		loadInstructionsMap.put(long.class, Opcodes.LLOAD);
		loadInstructionsMap.put(void.class, Opcodes.RETURN - 151);

		return loadInstructionsMap;
	}
}

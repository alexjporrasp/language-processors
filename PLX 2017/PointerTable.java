import java.util.*;

public class PointerTable {
	// variables have level 0 while pinters have level from 1 onwards
	private static Map<String, Integer> pointers = new HashMap<String, Integer>();

	public static void addPointer(String name, int level) {
		pointers.put(name, level);
	}

	public static Boolean isPointer(String name) {
		return pointers.containsKey(name);
	}

	public static int getLevel(String name) {
		if (pointers.containsKey(name)) {
			return pointers.get(name);
		} else {
			return 0;
		}
	}
}

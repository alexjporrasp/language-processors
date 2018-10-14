import java.util.*;

public class VarTable {
	// Map< var_name, Tuple< type, size>>
	// Array can have multiple sizes
	// Normal variables have size 1
	private static Map<String, Tuple<String, List<Integer>>> variables =
							new HashMap<String, Tuple<String, List<Integer>>>();

	public static void addVar(String name, String type, List<Integer> size) {
		variables.put(name, new Tuple(type, size));
	}

	public static void addVar(String name, String type) {
		variables.put(name, new Tuple(type, (new ArrayList<Integer>()).add(1)));
	}

	public static int getSize(String var) {
		int res = 0;

		if (isDeclared(var)) {
			res = variables.get(var)._2().get(0); // get 1st size for now
		} else {
			Gen.error();
			PLXC.out.println("\t# variable was not declared.");
			Gen.halt();
		}

		return res;
	}

	public static String getType(String var) {
		String res = null;

		if (isDeclared(var)) {
			res = variables.get(var)._1();
		} else {
			Gen.error();
			PLXC.out.println("\t# variable was not declared.");
			Gen.halt();
		}

		return res;
	}

	public static Boolean isDeclared(String var) {
		return variables.containsKey(var);
	}
}

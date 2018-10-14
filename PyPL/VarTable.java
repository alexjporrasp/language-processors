import java.util.*;

public class VarTable {
	private static List<String> variables = new ArrayList<String>();

	public static void addVar(String v) {
		if (!variables.contains(v)) {
			variables.add(v);
		}
	}

	public static Boolean isDeclared(String v) {
		return variables.contains(v);
	}
}

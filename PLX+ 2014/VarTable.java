import java.util.*;

public class VarTable {
  private static int scope = 0;

  private static List<Tuple<String, Integer>> variables = new ArrayList<Tuple<String, Integer>>();

  public static void declare_var(String var) {
    for (Tuple<String, Integer> t: variables) {
      if(t._1().equals(var) && t._2() == currentScope()) {
        Gen.error();
        PLXC.out.println("\t# variable already declared in this scope.");
        Gen.halt();
      }
    }

    variables.add(new Tuple(var, currentScope()));
  }

  public static void clear_scope() {

    List<Tuple<String, Integer>> dump = new ArrayList<Tuple<String, Integer>>();

    for (Tuple<String, Integer> t: variables) {
      if (t._2() == currentScope()) {
        dump.add(t);
      }
    }

    variables.removeAll(dump);
  }

  public static String getVar(String name) {
    int max = -1;

    for (Tuple<String, Integer> t: variables) {
      if(t._1().equals(name)) {
        max = t._2() > max ? t._2() : max;
      }
    }

    if (max == -1) {
      Gen.error();
      PLXC.out.println("\t# not declared variable");
      Gen.halt();
    }

    return name + "_" + max;
  }

  public static String showTable() {
    return variables.toString();
  }

  public static void climb() {
    scope++;
  }

  public static void fall() {
    scope--;
  }

  public static int currentScope() {
    return scope;
  }
}

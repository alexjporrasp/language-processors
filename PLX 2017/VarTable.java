import java.util.*;

public class VarTable {
  private static int scope = 0;

  // identifier, scope, type, size (arrays)
  private static List<Tuple<String, Tuple<Integer, Tuple<String, Integer>>>> variables = new ArrayList<Tuple<String, Tuple<Integer, Tuple<String, Integer>>>>();


  // returns the variables with the highest scope available
  public static String getVar(String id) {
    int max = -1;
    for (Tuple<String, Tuple<Integer, Tuple<String, Integer>>> t : variables) {
      if (getName(t).equals(id)) {
        max = getScope(t) > max ? getScope(t) : max;
      }
    }

    if (max == -1) {
      Gen.error();
      PLXC.out.println("\t# var does not exist.");
      Gen.halt();
    }

    return id + "_" + max;
  }

  public static void declare_var(String name, int scope, String type, int size) {

    for (Tuple<String, Tuple<Integer, Tuple<String, Integer>>> t : variables) {
      if (getName(t).equals(name) && getScope(t) == scope) {
        Gen.error();
        PLXC.out.println("\t# var already declared in this scope");
        Gen.halt();
      }
    }
    
    variables.add(new Tuple(name, new Tuple(scope, new Tuple(type, size))));
  }

  public static void clearScope() {
    for (Tuple<String, Tuple<Integer, Tuple<String, Integer>>> t : variables) {
      if (getScope(t) == scope) {
        variables.remove(t);
      }
    }
  }

  public static String getName(Tuple<String, Tuple<Integer, Tuple<String, Integer>>> t) {
    return t._1();
  }

  public static int getScope(Tuple<String, Tuple<Integer, Tuple<String, Integer>>> t) {
    return t._2()._1();
  }

  public static String getType(Tuple<String, Tuple<Integer, Tuple<String, Integer>>> t) {
    return t._2()._2()._1();
  }

  public static int getSize(Tuple<String, Tuple<Integer, Tuple<String, Integer>>> t) {
    return t._2()._2()._2();
  }

  public static void climb() {
    scope++;
  }

  public static void fall() {
    scope = scope == 0 ? scope : (scope - 1);
  }

  public static int currentScope() {
    return scope;
  }
}

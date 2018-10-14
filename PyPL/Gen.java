// @author Alex J. Porras
// University of Malaga, Spain
// Language Processors

public class Gen {

  public static final int EQ = 1, NEQ = 2, LESS = 3, MORE = 4, LEQ = 5, MOEQ = 6;

  private static int tag, label;

  public Gen () {
    tag = 0;
    label = 0;
  }

  public static void label(String l) {
    PYPLC.out.println(l + ":");
  }

  public static void _goto(String l) {
    PYPLC.out.println("\tgoto " + l + ";");
  }

  public static void _print(String e) {
    PYPLC.out.println("\tprint " + e + ";");
  }

  public static void asig(String ident, String exp) {
    PYPLC.out.println("\t" + ident + " = " + exp + ";");
  }

  public static void operate(String id, String e1, String op, String e2) {
    PYPLC.out.println("\t" + id + " = " + e1 + " " + op + " " + e2 + ";");
  }

  public static DoubleTag cond (int type, String e1, String e2) {
    DoubleTag temp = new DoubleTag();

    switch (type) {
      case EQ:
        PYPLC.out.println("\tif (" + e1 + " == " + e2 + ") goto " + temp.getT() + ";");
        PYPLC.out.println("\tgoto " + temp.getF() + ";");
        break;
      case NEQ:
        PYPLC.out.println("\tif (" + e1 + " == " + e2 + ") goto " + temp.getF() + ";");
        PYPLC.out.println("\tgoto " + temp.getT() + ";");
        break;
      case LESS:
        PYPLC.out.println("\tif (" + e1 + " < " + e2 + ") goto " + temp.getT() + ";");
        PYPLC.out.println("\tgoto " + temp.getF() + ";");
        break;
      case MORE:
        PYPLC.out.println("\tif (" + e2 + " < " + e1 + ") goto " + temp.getT() + ";");
        PYPLC.out.println("\tgoto " + temp.getF() + ";");
        break;
      case LEQ:
        PYPLC.out.println("\tif (" + e2 + " < " + e1 + ") goto " + temp.getF() + ";" );
        PYPLC.out.println("\tgoto " + temp.getT() + ";");
        break;
      case MOEQ:
        PYPLC.out.println("\tif (" + e1 + " < " + e2 + ") goto " + temp.getF() + ";");
        PYPLC.out.println("\tgoto " + temp.getT() + ";");
        break;
      default: // Error
    }

    return temp;
  }

  public static void error() {
    PYPLC.out.println("\terror;");
  }

  public static void halt() {
    PYPLC.out.println("\thalt;");
  }

  public static String genTemp () {
    return "t" + tag++;
  }

  public static String genTag () {
    return "L" + label++;
  }

}

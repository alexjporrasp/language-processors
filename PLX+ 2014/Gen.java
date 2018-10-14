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
    PLXC.out.println(l + ":");
  }

  public static void _goto(String l) {
    PLXC.out.println("\tgoto " + l + ";");
  }

  public static void _print(String e) {
    PLXC.out.println("\tprint " + e + ";");
  }

  public static void asig(String ident, String exp) {
    PLXC.out.println("\t" + ident + " = " + exp + ";");
  }

  public static void operate(String id, String e1, String op, String e2) {
    PLXC.out.println("\t" + id + " = " + e1 + " " + op + " " + e2 + ";");
  }

  public static DoubleTag cond (int type, String e1, String e2) {
    DoubleTag temp = new DoubleTag();

    switch (type) {
      case EQ:
        PLXC.out.println("\tif (" + e1 + " == " + e2 + ") goto " + temp.getT() + ";");
        PLXC.out.println("\tgoto " + temp.getF() + ";");
        break;
      case NEQ:
        PLXC.out.println("\tif (" + e1 + " == " + e2 + ") goto " + temp.getF() + ";");
        PLXC.out.println("\tgoto " + temp.getT() + ";");
        break;
      case LESS:
        PLXC.out.println("\tif (" + e1 + " < " + e2 + ") goto " + temp.getT() + ";");
        PLXC.out.println("\tgoto " + temp.getF() + ";");
        break;
      case MORE:
        PLXC.out.println("\tif (" + e2 + " < " + e1 + ") goto " + temp.getT() + ";");
        PLXC.out.println("\tgoto " + temp.getF() + ";");
        break;
      case LEQ:
        PLXC.out.println("\tif (" + e2 + " < " + e1 + ") goto " + temp.getF() + ";" );
        PLXC.out.println("\tgoto " + temp.getT() + ";");
        break;
      case MOEQ:
        PLXC.out.println("\tif (" + e1 + " < " + e2 + ") goto " + temp.getF() + ";");
        PLXC.out.println("\tgoto " + temp.getT() + ";");
        break;
      default: // Error
    }

    return temp;
  }

  public static void error() {
    PLXC.out.println("\terror;");
  }

  public static void halt() {
    PLXC.out.println("\thalt;");
  }

  public static String genTemp () {
    return "t" + tag++;
  }

  public static String genTag () {
    return "L" + label++;
  }

}

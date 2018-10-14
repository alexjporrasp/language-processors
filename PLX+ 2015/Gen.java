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


  public static void asig(Tuple ident, Tuple exp) {
		if (ident._2().equals("Integer") && exp._2().equals("Real")) {
			Gen.error();
			PLXC.out.println("\t# Can't assign float to int.");
			Gen.halt();
		} else if (ident._2().equals("Real") && exp._2().equals("Integer")) {
			PLXC.out.println("\t" + ident._1() + " = (float) " + exp._1() + ";");
		} else { // Types are the same
			PLXC.out.println("\t" + ident._1() + " = " + exp._1() + ";");
		}
  }

	public static void check_range(String i, Tuple e1) {
		DoubleTag db = new DoubleTag();
		PLXC.out.println("\t# Check range");
		PLXC.out.println("\tif (" + e1._1() + " < 0) goto " + db.getT() + ";");
		PLXC.out.println("\tif (" + VarTable.getSize(i) + " < " + e1._1() + ") goto " + db.getT() + ";");
		PLXC.out.println("\tif (" + VarTable.getSize(i) + " == " + e1._1() + ") goto " + db.getT() + ";");
		Gen._goto(db.getF());
		Gen.label(db.getT());
		Gen.error();
		Gen.halt();
		Gen.label(db.getF());
	}

  public static void operate(Tuple id, Tuple e1, String op, Tuple e2) {
		if (e1._2().equals("Real") || e2._2().equals("Real")) {
			if (e1._2().equals("Integer")) {
				String t = Gen.genTemp();
				PLXC.out.println("\t" + t + " = (float) " + e1._1() + ";");
				PLXC.out.println("\t" + id._1() + " = " + t + " " + op + "r " + e2._1() + ";");
			} else if (e2._2().equals("Integer")) {
				String t = Gen.genTemp();
				PLXC.out.println("\t" + t + " = (float) " + e2._1() + ";");
				PLXC.out.println("\t" + id._1() + " = " + e1._1() + " " + op + "r " + t + ";");
			} else { // Both are float
				PLXC.out.println("\t" + id._1() + " = " + e1._1() + " " + op + "r " + e2._1() + ";");
			}
			id.setB("Real");
		} else { // Both are integers
			PLXC.out.println("\t" + id._1() + " = " + e1._1() + " " + op + " " + e2._1() + ";");
			id.setB("Integer");
		}
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

	public static Tuple<String, String> cast(String type, Tuple exp) {
		String t = Gen.genTemp();
		if (type.equals("Integer") && !exp._2().equals("Integer")) {
			PLXC.out.println("\t" + t + " = (int) " + exp._1() + ";");
		} else if (type.equals("Real") && !exp._2().equals("Real")) {
			PLXC.out.println("\t" + t + " = (float) " + exp._1() + ";");
		}
		return new Tuple(t, type);
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

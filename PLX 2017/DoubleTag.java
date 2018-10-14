// Simple Java interpreter (PL Language)
// @author Alex J. Porras
// University of Malaga, Spain
// Language Processors

public class DoubleTag {
  String t, f;

  public DoubleTag() {
    t = Gen.genTag();
    f = Gen.genTag();
  }

  public String getT() {
    return t;
  }

  public String getF() {
    return f;
  }

  public void setT(String t) {
    this.t = t;
  }

  public void setF(String f) {
    this.f = f;
  }
}

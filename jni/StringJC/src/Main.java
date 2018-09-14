public class Main {
  public native String strMethod(String s);
  public native char[] chrMethod(char[] c);

  static {
    String dir = System.getProperty("user.dir");
    System.load(dir+"/stringlib.so");
  }

  public static void main(String[] args) {
    System.out.println();
    String s = "heh mda kek";
    String r = new Main().strMethod(s);
    System.out.println(r+" from java");

    char[] c = {'a','b','c'};
    char[] rc = new Main().chrMethod(c);
    for (int i = 0; i<3; i++ ) {
      System.out.print(rc[i]);
    }

    System.out.println("\n"+s); // modifying string from c
    // does not affects this string in java
  }
}

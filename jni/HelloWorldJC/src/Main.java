public class Main {
  public native void helloC(); // method declaration
  // informs the compiler that this method is native
  // and present in a C source file we'll call
  public native int sum(int a, int b);



  static {
    String dir = System.getProperty("user.dir");
    //System.out.println(dir);
    System.load(dir+"/hello.o"); // loading the library
    System.load(dir+"/numbers.o");
  }

  public static void main(String[] args) {
    new Main().helloC();
    int onePlusTwo = new Main().sum(1,2);
    System.out.println(onePlusTwo);
  }
}

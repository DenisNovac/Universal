package testorg;
import testpackage.*;
import dbpackage.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Inner inner = new Inner();
        DBConnection db = new DBConnection();
        db.tryConnect();
    }
}

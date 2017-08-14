package org.novac;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        DatabaseH2 mytestdb = new DatabaseH2("test");
    }
}

/*
 * Denis Novac
 * Some app for H2 Client-Server tests
 */
package org;

import org.client.*;
import org.server.*;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {	
    	Scanner sc = new Scanner(System.in);
        System.out.print( "Input 1 for server, 2 for client: " );
        int answer = sc.nextInt();
        switch (answer) {
        	case 1: 
        		new H2Server();
        		break;
        	case 2: 
        		new H2Client();
        		break;
        	default: break; // ends the program
        }
    }
}

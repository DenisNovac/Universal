package org.server;

import org.h2.tools.Server;
import org.h2.Driver;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.util.Scanner;
/**
 * Class for starting server side.
 *
 *
 */
public class H2Server {
	private String path = "jdbc:h2:tcp://localhost/./h2databases/my_serverdb";
	private String settings = ";CIPHER=AES;AUTO_SERVER=TRUE;MULTI_THREADED=1";
	private String user = "serveradmin";
	private String password = "adminpassword";

	public H2Server() {
		System.out.println(":: H2 server side ::");
		
		Server server=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		String sql=null;
		
		try {
			// starting the TCP Server
			server = Server.createTcpServer("-tcpAllowOthers", "-webAllowOthers", 
				"-pgAllowOthers").start();
			// because of "tcp" in bd path here i "bind" connection to server
			connection = DriverManager.getConnection(path, user, password);
			statement=connection.createStatement();
			
			System.out.println("Port: "+server.getPort());
			System.out.println("URL: "+server.getURL());
			System.out.println("Status: "+server.getStatus());

			sql =   "CREATE TABLE IF NOT EXISTS MYUSERS("+
					"userid int, username varchar(255),"+
					"PRIMARY KEY(userid) )";
			statement.execute(sql);
			sql = "INSERT INTO MYUSERS (userid,username) VALUES (1,'denis')";
			statement.execute(sql);
			sql = "INSERT INTO MYUSERS (userid,username) VALUES (2,'fil')";
			statement.execute(sql);
			System.out.println(connection.getCatalog());

			resultSet = statement.executeQuery("SELECT userid, username FROM MYUSERS");
			while (resultSet.next()) {
				String userid = resultSet.getString("userid");
				String username = resultSet.getString("username");
				System.out.println(userid+" "+username);
			}

			// makes new user
			sql = "CREATE USER MARY PASSWORD 'Marypassword'";
			statement.execute(sql);
			// gives mary rights only to read
			sql = "GRANT SELECT ON MYUSERS TO MARY";
			statement.execute(sql);

			// user for insert rule
			sql = "CREATE USER HELEN PASSWORD 'Helenpassword'";
			statement.execute(sql);
			sql = "GRANT SELECT ON MYUSERS TO HELEN";
			statement.execute(sql);
			sql = "GRANT INSERT ON MYUSERS TO HELEN";
			statement.execute(sql);

			System.out.println("Input 0 for exit");
			int i=1;
			Scanner sc = new Scanner(System.in);
			while (i!=0){
				i=sc.nextInt();
			}
			sc.close();
			connection.close();
			server.stop();
			System.exit(0);
			
		} catch (SQLException e) {
			System.out.println(e);
		}	

	} // end of constructor


}
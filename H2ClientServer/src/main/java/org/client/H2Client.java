package org.client;

import org.h2.Driver;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class H2Client {
	private String path = "jdbc:h2:tcp://localhost/./h2databases/my_serverdb";
	private String user = null;
	private String password = null;

	public H2Client() {
		System.out.println(":: H2 client side ::");

		// entering username and password
		Scanner sc = new Scanner(System.in);
		System.out.print("Input username: ");
		user = sc.nextLine();
		System.out.print("Input password: ");;
		password = sc.nextLine();
		sc.close();
		System.out.println();

		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		String sql=null;

		try {
			connection = DriverManager.getConnection(path, user, password);
			statement=connection.createStatement();
			System.out.println(connection.getCatalog());
			resultSet = statement.executeQuery("SELECT userid, username FROM MYUSERS");
			while (resultSet.next()) {
				String userid = resultSet.getString("userid");
				String username = resultSet.getString("username");
				System.out.println(userid+" "+username);
			}

			statement.execute("INSERT INTO MYUSERS (userid,username) VALUES (3,'helen')");
			resultSet = statement.executeQuery("SELECT userid, username FROM MYUSERS");
			while (resultSet.next()) {
				String userid = resultSet.getString("userid");
				String username = resultSet.getString("username");
				System.out.println(userid+" "+username);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		} 
	} // end of constructor

}
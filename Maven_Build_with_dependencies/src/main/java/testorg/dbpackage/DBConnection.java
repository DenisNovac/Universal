package dbpackage;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnection {
	private String driverName = "com.mysql.jdbc.Driver";
    private String connectionString = "jdbc:mysql://localhost:3306/world";
    private String login = "root";
	private String password = "admin";

	public void tryConnect() {
		
	    Connection connection = null;
	    Statement statement = null;

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("Can't get class. No driver found");
            e.printStackTrace();
            return;
        }
        
        try {
            connection = DriverManager.getConnection(connectionString, login, password);
            statement = connection.createStatement();
            String sql = "SELECT Name, Continent FROM Country";
            ResultSet resultTest = statement.executeQuery(sql);
            while (resultTest.next()) {
            	String name = resultTest.getString("Name");
            	String continent = resultTest.getString("Continent");
            	System.out.println(name+" : "+continent);
            }
            resultTest.close();

        } catch (SQLException e) {
            System.out.println("Can't get connection. Incorrect URL");
            e.printStackTrace();
            return;
        } catch (Exception e) {
        	System.out.println(e);
        }


        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
            return;
        }
	} // end of tryConnect();
}
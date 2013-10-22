/**
 * @(#)MySQLAccess.java
 *
 *
 * @David Rivera
 * @version 1.00 2013/9/16
 */
package pasProject;
import java.sql.*;
import javax.sql.*;

public class MySQLAccess
{
	private Connection con=null;
	private String database;
	private String server;
	private String port;
	private String userid;
	private String password;

	public MySQLAccess(String db) //Default Constructor db ={ med || pas }
	{
			//default connection settings for Med/Appli
			server = "168.61.37.221";
			port = "3306";
			database = db;
			userid = "medasset";
			password = "password";
	}
	public MySQLAccess(String db, String host, String portNum, String user, String pass) //Constructor for diffrent server
	{
		database = db;
		server = host;
		port= portNum;
		userid = user;
		password=pass;
	}
	public void openConnection() //Opens Connection
    {
    	try
		{

			String url = "jdbc:mysql://"+server+":"+port+"/"+database;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, userid, password);
			System.out.println("Connection Opened");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    public void closeConnection() //Closes the Database Connection
    {
    	try
    	{
    		con.close();
    		System.out.println("Connection Closed");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    public Connection getConnection() //Returns the Connection
    {
    	return con;
    }
}
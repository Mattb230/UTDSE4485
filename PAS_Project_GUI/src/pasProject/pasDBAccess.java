/**
 * @(#)pasDBAccess.java
 *
 *
 * @Davd Rivera
 * @version 1.00 2013/10/21
 */
package pasProject;
import java.sql.*;
import javax.sql.*;
import java.util.LinkedList;

public class pasDBAccess
{
	Connection con;
	MySQLAccess db;

    public pasDBAccess()
    {
    	db=new MySQLAccess("pas");
		db.openConnection();
		setConnection(db.getConnection());
    }
    public void closeConnection()
    {
    	db.closeConnection();
    }
    public void setConnection(Connection c)
    {
    	con=c;
    }
    public ResultSet executeQuery(String query)
    {

    	String preQueryStatement = query;
    	ResultSet rs=null;
    	try
   		{
   			PreparedStatement pStmnt = con.prepareStatement(preQueryStatement);
   			rs = pStmnt.executeQuery();
   		}
   		catch(Exception e)
   		{
   			e.printStackTrace();
   		}
   		return rs;
    }
    public void dropDirectory()
    {
    	executeQuery("drop table directory");
    }
    public void addDirectory(String name, String columns, int num, int included)
    {
    	try
    	{
    		String query="INSERT INTO directory ( VALUES (?,?,?,?))";
    		String preQueryStatement = query;
    		PreparedStatement pStmnt = con.prepareStatement(preQueryStatement);
    		pStmnt.setString(1, name);
    		pStmnt.setString(2, columns);
    		pStmnt.setString(3, num+"");
    		pStmnt.setString(4, included+"");
    		pStmnt.executeUpdate();
    	}
    	catch(Exception e)
    	{
    		System.out.println("Directory Update Failed Table: "+name);
    		e.printStackTrace();
    	}

    }

}
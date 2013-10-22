/**
 * @(#)medDBAccess.java
 *
 *
 * @David Rivera
 * @version 1.00 2013/10/14
 */
package pasProject;
import java.sql.*;
import javax.sql.*;
import java.util.LinkedList;


public class medDBAccess
{
	Connection con;
	MySQLAccess db;

    public medDBAccess()
    {
		db=new MySQLAccess("med");
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


	public String prepareQuery(String query)
    {

    	//String query="INSERT INTO "+table+" (";
    	//String val =" VALUES (";
    	return "true";

    }
    public LinkedList<String> getTables()
    {

		String preQueryStatement = "show tables";
		LinkedList tables = new LinkedList();

   		ResultSet rs = executeQuery("show tables");
		try
		{
   			while(rs.next())
   			{
   				tables.add((String)rs.getObject(1));
   			}
   			try { rs.close(); } catch (Throwable ignore){}
		}
		catch(Exception e)
   		{
   			e.printStackTrace();
   		}
   		return tables;
    }
 	public LinkedList<String> getColumns(String table)
   	{
		String preQueryStatement = "";
		LinkedList tables = new LinkedList();

   		ResultSet rs = executeQuery("show columns from "+table);
		try
		{
   			while(rs.next())
   			{
   				tables.add((String)rs.getObject(1));
   			}
   			try { rs.close(); } catch (Throwable ignore){}
		}
		catch(Exception e)
   		{
   			e.printStackTrace();
   		}
   		return tables;
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
	
    /*public static void main(String[] args)
	{
		MySQLAccess db=new MySQLAccess("med");
		db.openConnection();
		medDBAccess bob = new medDBAccess();
		bob.setConnection(db.getConnection());
		LinkedList<String> tables=bob.getTables();

		for(String a:tables)
			System.out.println("Table "+a+" Vales:"+bob.getColumns(a));

		db.closeConnection();

	}
	*/
    

}
package pasProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Date date;

	public void readDataBase(String query) throws Exception {
		try {
	     
		 // This will load the MySQL driver, each DB has its own driver
	     //Class.forName("com.mysql.jdbc.Driver");
	     // Setup the connection with the DB -- David to provide DB connection details
	     // example: connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
	     //         + "user=sqluser&password=sqluserpw");
	     //connect = DriverManager.getConnection("David to fill in details");

	     //TODO set up statements to issue SQL queries to the database
	     
	     //
	      
	   } catch (Exception e) {
	     throw e;
	   } finally {
	     close();
	   }

	 }
	
	public ResultSet getResultSet(){
		return resultSet;
	}
	

	private void writeMetaData(ResultSet resultSet) throws SQLException
	{
	  //writes the resultSet metadata to the console
	  
	  //Get the metadata
	  
	  ResultSetMetaData resSetMetaData = resultSet.getMetaData();
	  int intNumColumns = resSetMetaData.getColumnCount();
	  
	  for  (int i = 1; i< intNumColumns + 1; i++)
	  {
	  	String strName = resSetMetaData.getColumnName(i);
	  	System.out.println(strName);
	  }
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
	  // ResultSet is initially before the first data set
	  while (resultSet.next()) {
	    // It is possible to get the columns via name
	    // also possible to get the columns via the column number
	    // which starts at 1
	    // e.g. resultSet.getSTring(2);
	    //TODO if this method is needed, fill in the desired statements below
	  }
	}

	// Close the resultSet
	private void close() {
	  try {
	    if (resultSet != null) {
	      resultSet.close();
	    }

	    if (statement != null) {
	      statement.close();
	    }

	    if (connect != null) {
	      connect.close();
	    }
	  } catch (Exception e) {

	  }
	}

}

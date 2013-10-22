/**
 * @(#)DBSetup.java
 *
 *
 * @David Rivera
 * @version 1.00 2013/9/16
 */
package pasProject;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.Iterator;

public class DBSetup
{

	Connection con;
	Scanner in;

    public DBSetup()
    {
    }
	public void openCon()
    {
    	try
		{
			String server = "168.61.37.221";//"localhost";
			String port = "3306";
			String database = "med";
			String url = "jdbc:mysql://"+server+":"+port+"/"+database;
			Class.forName("com.mysql.jdbc.Driver");
			String userid = "medasset";
			String password = "password";
			con = DriverManager.getConnection(url, userid, password);
			System.out.println("Connection Made");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    public void closeCon()
    {
    	try
    	{
    		con.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    public void openFile()
    {
    	try
    	{
    		File file = new File("data.txt");
    		System.out.println("File Opened");
    		in = new Scanner(file);

    	}
    	catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
    public void extracData()
    {
    	Stack<String> tags = new Stack<>();
    	HashMap<String,String> table= new HashMap<>();	//will hold values to be up into the corresponding DB table
		String billID	= new String();
		int clientID =0;

    	while(in.hasNextLine())
    	{
    		String line=in.nextLine();
    		if(!line.equals(""))
    		{
				int[] chevron = new int[4]; //angle bracket locations
				int x=0;
				int y=0; //number of chevrons

				while(x<line.length())
				{
					//System.out.print(line);
					if(line.charAt(x)=='<')
					{
						chevron[y]=x;
						y++;
					}
					else if(line.charAt(x)=='>')
					{
						chevron[y]=x;
						y++;
					}
					x++;
				}
				if(!tags.empty() && tags.peek().equals("PatientBillPackage"))
				{
					tags.pop(); //get rid of BillPackage Wrapper
					clientID = Integer.parseInt(line.substring(27,32));
					billID=line.substring(42,78);

					table.put("ClientID",line.substring(27,32));
					table.put("BillID",billID);
					tags.push("PatientBill");
					y=0;
				}

				if(y==4)
				{
					String tag= line.substring(chevron[0]+1,chevron[1]);
					if( tag.equals(line.substring(chevron[2]+2,chevron[3])))
					{
						//System.out.println("\t"+line.substring(chevron[1]+1,chevron[2]));
						table.put(tag,line.substring(chevron[1]+1,chevron[2]));
					}
				}
				else if(y==2)
				{

					if(line.charAt(chevron[0]+1)=='/') //End of tag
					{
						//System.out.println(tags.peek());
						//	System.out.println(table+"\n\n");
						//	tags.pop();
					}
					else if(line.charAt(chevron[1]-1)=='/') //No information in tag
					{}
					else //Start of Tag
					{
						boolean keeptable=false;
						if(!tags.empty())
						{
							if(line.substring(chevron[0]+1,chevron[1]).equals("Modifiers")||line.substring(chevron[0]+1,chevron[1]).equals("Modifier"))
							{
								keeptable=true;
							}
							else
							{
								if(tags.peek().equals("Modifier"))
								{

									tags.pop();
									//System.out.println(tags.peek());
									String temp=table.get("Code");
									table.remove("Code");
									table.put("Modifier",temp);
									//System.out.println(table);
								}

								if(table.size()>1) //put calues in Database
								{
									String query =prepareQuery(table,billID,tags.peek());
									insertDB(table,query);
								}
							}
						}

						tags.push(line.substring(chevron[0]+1,chevron[1]));
						if(!keeptable)
							table.clear();


						if(tags.peek().equals("Modifiers"))
						{
							tags.pop();
						}
						else if(tags.peek().length()>25 && tags.peek().substring(0,20).equals("PatientBillException"))
						{
							tags.pop();
							tags.push("PatientBillException");
							table.put("ExceptionNumber",line.substring(108,109));
							table.put("ClientID",clientID+"");
							y=0;
						}
						else if(tags.peek().length()>25 && tags.peek().substring(0,19).equals("ExceptionResolution"))
						{
							tags.pop();
							tags.push("ExceptionResolution");
							table.put("ClientID",clientID+"");
							table.put("ExceptionNumber",line.substring(107,108));
							y=0;
						}
						else if(tags.peek().length()>25 && tags.peek().substring(0,8).equals("RuleInfo"))
						{
							tags.pop();
							tags.push("RuleInfo");
							table.put("ClientID",clientID+"");

							String temp="";
							int i = 57;
							while(line.charAt(i)!='"')
							{
								temp+=line.charAt(i);
								i++;
							}
							table.put("RuleID",temp);
							table.put("BatchID",line.substring(42,47));
							y=0;
						}
						else if(tags.peek().length()>25 && tags.peek().substring(0,15).equals("ExceptionDetail"))
						{
							tags.pop();
							tags.push("ExceptionDetail");
							table.put("TypeName",line.substring(38,chevron[1]-1));
							y=0;
						}
						if(!keeptable)
							table.put("BillID",billID);
						//System.out.println(tags.peek());
					}
				}
    		}
    	}
		in.close();
		System.out.println("File Closed");
    }
    public String prepareQuery(HashMap<String,String> values, String billID,String table)
    {
    	Iterator it = values.keySet().iterator();

    	String query="INSERT INTO "+table+" (";
    	String val =" VALUES (";

    	while((it.hasNext()))
    	{
    		query+=it.next()+", ";
    		val+="?,";
    		//it.remove();
    	}
    	query=query.substring(0,query.length()-2)+")";
    	val=val.substring(0,val.length()-1)+")";

    	return query+val;
    }
    public void insertDB(HashMap<String,String> values, String query)
    {
    		Iterator it = values.keySet().iterator();
    		try
			{	//	example:	INSERT INTO Customers (CustomerName, City, Country)
				//				VALUES ('Cardinal', 'Stavanger', 'Norway');

    			String preQueryStatement = query;	//"INSERT  INTO  url  VALUES  (?,?)";
   				PreparedStatement pStmnt = con.prepareStatement(preQueryStatement);

   		 		int x=1;
				String temp="";
				while((it.hasNext()))
    			{
    				temp=values.get(it.next()+"");
    				pStmnt.setString(x, temp);
    				x++;
    			}


    			//pStmnt.setString(2, desc);
    			pStmnt.executeUpdate();
			}
			catch(Exception e)
    		{
    			System.out.println(query);
    			e.printStackTrace();
    		}

    }


	public static void main(String[] args)
   	{
        DBSetup bob = new DBSetup();
		bob.openCon();
		bob.openFile();
		bob.extracData();
		bob.closeCon();
		System.out.println("Done");
    }
}

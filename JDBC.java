import java.io.*;
import java.sql.*;

public class JDBC {

	public static void main(String[] args) {

	    if (args.length!=4) {
	    	System.err.println ("Invalid value. First argument appended to jdbc:db2: must specify a valid URL.");
	    	System.err.println ("Second argument must be a valid user ID.");
	    	System.err.println ("Third argument must be the password for the user ID.");
	    	System.err.println ("Fourth argument must be a valid SQL query");
	    	System.exit(1);
	    }
	    
	    String url= "jdbc:db2:"+args[0];
	    String user= args[1];
	    String password= args[2];
	    String SQL= args[3];
	    File f = new File("output.csv");
	    Connection con;
	    Statement stmt;
	    ResultSet rs;

	    try {                                                                        
		    Class.forName("com.ibm.db2.jcc.DB2Driver");     
		    System.out.println("**** Loaded the JDBC driver");
	
		    con = DriverManager.getConnection (url, user, password);    
		    con.setAutoCommit(false);
		    System.out.println("**** Created a JDBC connection to the data source");
	
		    stmt = con.createStatement();        
		    System.out.println("**** Created JDBC Statement object");
	
		    rs = stmt.executeQuery(SQL);      
		    System.out.println("**** Created JDBC ResultSet object");
		      
		    rsToCSV(rs, f, true);
		    System.out.println("**** ResultSet stored in output.csv");
		      
		    rs.close();
		    System.out.println("**** Closed JDBC ResultSet");
		     
		    stmt.close();
		    System.out.println("**** Closed JDBC Statement");
	
		    con.commit();
		    System.out.println ( "**** Transaction committed" );

	      	con.close();
	      	System.out.println("**** Disconnected from data source");

	      	System.out.println("**** JDBC Exit from class - no errors");

	    }
	    
	    catch (ClassNotFoundException ex) {
	    	System.err.println("Could not load JDBC driver");
	      	System.out.println("Exception: " + ex);
	    }

	    catch(SQLException ex) {
	    	System.err.println("SQLException information");
	    	while(ex!=null) {
	      		System.err.println ("Error msg: " + ex.getMessage());
	      		System.err.println ("SQLSTATE: " + ex.getSQLState());
	      		System.err.println ("Error code: " + ex.getErrorCode());
	      		ex = ex.getNextException();
	      	}
	    }
	    
	    catch(IOException ex) {
	    	System.err.println("IO Exception");
		    System.out.println("Exception: " + ex);
	    }
	}
	
	public static void rsToCSV(ResultSet rs, File f, boolean columnNames) throws SQLException, FileNotFoundException, IOException{
	    ResultSetMetaData metaData = rs.getMetaData();
	    int columnCount = metaData.getColumnCount();
	    try (OutputStream os = new FileOutputStream(f)) {
	        os.write(239);
	        os.write(187);
	        os.write(191);
	        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"UTF-8"))) {
	            if(columnNames){
	                for(int i = 1; i<=columnCount; i++){
	                    pw.print(metaData.getColumnName(i));
	                    if(i<columnCount){
	                        pw.print(",");
	                        pw.flush();
	                    }
	                    if(i==columnCount){
	                        pw.println();
	                        pw.flush();
	                    }
	                }
	            }
	            while (rs.next()){
	                for (int i = 1; i <=columnCount; i++){
	                    pw.print(rs.getObject(i));
	                    if(i<columnCount){
	                        pw.print(",");
	                        pw.flush();
	                    }
	                    if(i==columnCount){
	                        pw.println();
	                        pw.flush();
	                    }
	                }
	            }
	        }
	    }
	}
}

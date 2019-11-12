# JDBC
Connect and query an AS400 remotely using IBM's DB2 drivers

# DB2 drivers

In order for the code to work, the three licencsed .jar files must be placed in a folder and their path must be added to the CLASSPATH environment variable. Do this from Contol Panel > System Properites > Advanced > Environment Variables

* db2jcc_license_cisuz.jar
* db2jcc_license_cu.jar
* db2jcc.jar

# Connecting to an AS400

If JDBC.class does not exist, it must be created by compiling the Java code:

  `javac JDBC.java`

Once the Java code has been sucessfully compiled, a query can be made using the following syntax:

  `java JDBC <url> <user id> <password> <sql query>`

An example use is given below:

  `java JDBC "//as400.example.com" "admin" "********" "SELECT * FROM INFORMATION_SCHEMA.TABLES LIMIT 100"`

The table returned from the SQL query will be saved as `output.csv` in the current directory

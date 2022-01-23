package lib.assets;

import lib.utility.MySQLUtil;

import java.sql.*;

/**
 * Abstract Database class
 * will require a host, databaseName, port, username, and password
 * to be instantiated.
 *
 * Contains 1 abstract method #defaultDatabase()
 */
public abstract class MDatabase {

    private String host; 		//host address (eg: "localhost")
    private String database; 	//name of database (eg: "lemon")
    private int port; 			//port (eg: 3306)
    private String username; 	//username for database priveleges (eg: "root")
    private String password; 	//password for database priveleges (eg: "password" or null)

    private Connection con;

    public MDatabase(String host, String databaseName, int port, String username, String password) {
        this.host = host;
        this.database = databaseName;
        this.port = port;
        this.username = username;
        this.password = password;
        if(!connectionExists()) {
            openConnection();
        }
    }

    /**
     * Prepares a MySQL Statement and executes the query
     * This method is used more for queries that returns things such as SELECT * FROM x;
     * @param query MySQL query (see query examples in README.md)
     * @return ResultSet (use ResultSets in MySQLUtil)
     * @throws SQLException catches exceptions such as query syntax errors
     */
    public ResultSet prepareStatement(String query) throws SQLException {
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        return rs;
    }

    /**
     * Creates a statement and executes the query.
     * This method is used more for queries that execute and don't return anything such as UPDATE or INSERT
     * @param query MySQL query (see query examples in README.md)
     * @throws SQLException catches exceptions such as query syntax errors
     */
    public void createStatement(String query) throws SQLException {
        Statement st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        System.out.println("Running query: " + query);
        st.execute(query);
    }

    /**
     * Initializes database & tables if not already created.
     */
    public abstract void defaultDatabase();

    /**
     * Returns the host which is set in the constructor
     * @return host String
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the database name which is set in the constructor
     * @return database name String
     */
    public String getDatabaseName() {
        return database;
    }

    /**
     * Returns the port of the database which is set in the constructor
     * @return port int
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the username of the database which is set in the constructor
     * @return username String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the database which is set in the constructor
     * @return password String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the Connection object in case of advanced queries
     * but otherwise you should use #prepareStatement and #createStatement
     * for simple uses
     * @return MySQL JDBC Connection object (can be null)
     */
    public Connection getConnection() {
        return con;
    }

    /**
     * Checks if the connection is not null and if it is not closed.
     * @return true if not null and not closed
     */
    public boolean connectionExists() {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Closes the MySQL database connection
     * Called in onDisable() of MPlugin
     * @throws SQLException maybe the connection wasn't open in the first place?
     */
    public void closeConnection() throws SQLException {
        con.close();
    }

    /**
     * Opens the connection
     * which instantiates the Connection.
     * Connection should be checked if it exists #connectionExists()
     */
    public void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(MySQLUtil.getMySQLURL(host, port, database), username, password);
        } catch (SQLException e) {
            System.out.println("Incorrect database details");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL library not found.");
            e.printStackTrace();
        }
    }

}

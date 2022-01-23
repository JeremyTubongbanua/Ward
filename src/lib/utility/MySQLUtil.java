package lib.utility;

import lib.assets.MDatabase;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handful of static methods for MySQL uses.
 */
public class MySQLUtil {

    /**
     * Returns a JDBC url
     * @param host - host of the database (eg: localhost)
     * @param port - port of the database( eg: 3306)
     * @param databaseName - database name (eg: marklib)
     * @return string JDBC MySQL url
     */
    public static String getMySQLURL(String host, int port, String databaseName) {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
        return url;
    }

    /**
     * Returns MySQL URL given a MDatabase
     * @param db MDatabase object (tip: use keyword this)
     * @return MySQL Url String (see #getMySQLURL(String, int, String)
     */
    public static String getMySQLURL(MDatabase db) {
        return getMySQLURL(db.getHost(), db.getPort(), db.getDatabaseName());
    }

    /**
     * Displays a result set using println's
     * @param rs ResultSet object (used through prepareStatement which returns a ResultSet)
     * @throws SQLException - don't know why this would be thrown ?
     */
    public static void displayResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int columnsNumber = meta.getColumnCount();
        for(int i = 1; i <= columnsNumber; i++) {
            if (i > 1) System.out.print(", ");
            System.out.print(meta.getColumnName(i));
        }
        System.out.println("===========");
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }

    /**
     * Returns lines of a ResultSet
     * does not contain the column labels.
     * Example:
     * - ["CONSOLE", 1]
     * - ["MarkIsCool", 2]
     * @param rs ResultSet object (used through prepareStatement which returns a ResultSet through a Connection)
     * @return List<String[]> object
     * @throws SQLException don't know why this would be thrown ?
     */
    public static List<String[]> getLines(ResultSet rs) throws SQLException {
        List<String[]> lines = new ArrayList<String[]>();
        ResultSetMetaData meta = rs.getMetaData();
        int columnsNumber = meta.getColumnCount();
        while(rs.next()) {
            String[] data = new String[columnsNumber];
            for(int i = 1; i <= columnsNumber; i++) {
                String value = rs.getString(i);
                data[i-1] = value;
            }
            lines.add(data);
        }
        return lines;
    }

}

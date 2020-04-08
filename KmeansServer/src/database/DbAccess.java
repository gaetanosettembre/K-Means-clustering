package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * CLasse che realizza l'accesso alla base di dati.
 * @author Gaetano
 */
public class DbAccess {
	
	static String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private static final String DBMS = "jdbc:mysql";
	private static final String SERVER = "localhost";
	private static final String DATABASE = "MapDB";
	private static final String PORT = "3306";
	private static final String USER_ID = "MapUser";
	private static final String PASSWORD = "map";
	private static Connection conn;
	
	/**
	 * Impartisce al class loader 
	 * @throws DatabaseConnectionException
	 * @throws ClassNotFoundException 
	 * @throws SQLException  
	 */
	public static void initConnection() throws DatabaseConnectionException, ClassNotFoundException, SQLException {
		try {
			Class.forName(DRIVER_CLASS_NAME);
		}catch (ClassNotFoundException ex) {
			System.err.println("Problema con il Driver del DB");;
        }
			conn = DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE, USER_ID, PASSWORD);
	}
	
	/**
	 * @return conn Restituisce la connessione del DB.
	 */
	public static Connection getConnection() {
		return conn;
	}
	
	/**
	 * Chiude la connessione con il DB.
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		conn.close();
	}
	
	
	
	

}

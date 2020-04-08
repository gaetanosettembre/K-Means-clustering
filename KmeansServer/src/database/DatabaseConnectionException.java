package database;

/**
 * Classe che modella un'eccezione di tipo DataBaseConnectionException.
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception {
	
	public DatabaseConnectionException(){
		super("Eccezione sollevata!!!!");
	}
	
	public String toString() {
		return "Impossible connettersi al DB!!!!";
	}

}

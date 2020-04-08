package database;

/**
 * Classe che modella un'eccezione di tipo EmptySetException.
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class EmptySetException extends Exception {
	
	EmptySetException(){
		super("Eccezione sollevata!");
	}
	
	public String toString() {
		return "ResultSet VUOTO!";
	}

}

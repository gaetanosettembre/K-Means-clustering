package database;

/**
 * Classe che modella un'eccezione di tipo NoValueException.
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class NoValueException extends Exception {
	
	NoValueException(){
		super("Eccezione sollevata!!!!");
	}
	
	public String toString() {
		return "Valore non presente del ResultSet!!!!";
	}

}

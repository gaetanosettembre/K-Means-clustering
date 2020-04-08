package database;

/**
 * Classe che modella un'eccezione di tipo EmptyTypeException.
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class EmptyTypeException extends Exception{
	
	EmptyTypeException(){
		super("Eccezione sollevata!");
	}
	
	public String toString() {
		return "Tipo vuoto!";
	}
	
	
	
}

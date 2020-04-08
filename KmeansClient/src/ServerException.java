@SuppressWarnings("serial")
/**
 * Classe che gestisce un'eccezione di tipo ServerException
 * @author Gaetano
 */
public class ServerException extends Exception {
	
	ServerException(){
		super("Eccezione sollevata!");
	}
	
	/**
	 * Metodo che genera una stringa contenente il messaggio di errore.
	 * @return Messaggio di errore.
	 */
	public String toString() {
		return "Errore nella comunicazione con il server!";
	}
	
}

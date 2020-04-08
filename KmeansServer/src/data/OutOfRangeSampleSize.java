package data;

/**
 * Classe OutOfRangeSampleSize che modella una eccezione controllata da considerare qualora il numero k di cluster inserito da tastiera 
 * è maggiore rispetto al numero di centroidi generabili dall'insieme di transazioni.
 * @author Gaetano
 */
public class OutOfRangeSampleSize extends Exception {
	
	private static final long serialVersionUID = 1L;

	OutOfRangeSampleSize(){
		super("Eccezione sollevata!!!!");
	}
	
	public String toString() {
		return "Il valore inserito corrisponde ad un numero di centroidi non ammissibile!!!!";
	}
	
}
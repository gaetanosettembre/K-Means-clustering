package data;

import java.io.Serializable;

/**
 * Classe concreta Tuple che rappresenta una tupla come sequenza di coppie attributo-valore.
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class Tuple implements Serializable {
	
	Item[] tuple;
	
	/**
	 * Costruttore della classe che inizializza la dimensione della tupla.
	 * @param size Dimensione della tupla.
	 */
	Tuple(int size){
		tuple = new Item[size];
	}
	
	/**
	 * Metodo che restituisce il numero di item che costituisce la tupla.
	 * @return restituisce il numero di item che costituisce la tupla.
	 */
	public int getLength(){
		return tuple.length;
	}
	
	/**
	 * Restituisce l'item in posizione i.
	 * @param i Indice dell'array
	 * @return Item in posizione i.
	 */
	public Item get(int i){
		return tuple[i];
	}
	
	/**
	 * Memorizza il parametro c in Tuple.
	 * @param c Item
	 * @param i Indice 
	 */
	void add(Item c, int i){
		tuple[i] = c;
	}
	
	/**
	 * Determina la distanza tra le tupla riferita da obj e la tupla corrente.
	 * @param obj Tupla
	 * @return Distanza tra le due tuple.
	 */
	public double getDistance(Tuple obj) {
		int currentLenght;
		double distance = 0;
		if(obj.getLength() > this.getLength())
			currentLenght = this.getLength();
		else
			currentLenght = obj.getLength();
		for(int i = 0; i < currentLenght; i++) {
				distance += this.get(i).distance(obj.get(i).getValue());
		}
		return distance;
	}
	
	/**
	 * Restituisce la media delle distanze tra la tupla corrente e quelle
	 * ottenibili dalle righe della matrice in data aventi indice in clusteredData.
	 * @param data Riferiemnto ad oggetto Data
	 * @param clusteredData Vettore di interi
	 * @return Media delle distanze.
	 */
	public double avgDistance(Data data, int clusteredData[]) {
		double p=0, sumD=0.0;
		for(int i=0; i<clusteredData.length; i++) {
			double d = getDistance(data.getItemSet(clusteredData[i]));
			sumD += d;
		}
		p = sumD/clusteredData.length;
		return p;
	}
	
	/**
	 * Metodo che restituisce una stringa che descrive lo stato della tupla.
	 */
	public String toString() {
		String str = "";
		for(int i = 0; i < this.getLength(); i++) {
			str += this.get(i).getValue() + ", ";
		}
		return str + "\n";
	}
	
}

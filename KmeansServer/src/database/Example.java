package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per gestire una transazione (riga della matrice).
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class Example implements Comparable<Example>, Serializable {
	
	private List<Object> example=new ArrayList<Object>();

	
	/**
	 * Aggiunge o incoda un Object all'ArrayList.
	 * @param o Riferimento all'oggetto da aggiungere.
	 */
	public void add(Object o){
		example.add(o);
	}

	/**
	 * Metodo che restituisce l'i-esimo riferimento collezionato in example
	 * @param i Indice di riga.
	 * @return l'i-esimo riferimento collezionato in example.
	 */
	public Object get(int i){
		return example.get(i);
	}
	
	/**
	 * Implementazione dell'interfaccia Comparable.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int compareTo(Example ex) {
		int i = 0;
		for(Object o : ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}
	
	/**
	 * Metodo che restituisce una stringa che rappresenta lo stato di example.
	 * @return Stringa che rappresenta lo stato di example.
	 */
	public String toString(){
		String str = "";
		for(Object o:example)
			str += o.toString() + " ";
		return str;
	}

}
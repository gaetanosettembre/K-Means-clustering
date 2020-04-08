
package data;

import java.io.Serializable;

/**
 * Classe astratta che modella l'entità attributo.
 * @author Gaetano
 *
 */
@SuppressWarnings("serial")
public abstract class Attribute implements Serializable {
	
	protected String name; //nome simbolico dell'attributo
	protected int index; //identificativo numero dell'attributo
	
	/**
	 * Costruttore di classe che crea l'ententà attributo.
	 * @param name Nome dell'attributo.
	 * @param index Indice dell'attributo.
	 */
	public Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * Metodo che restituisce il nome dell'attributo.
	 * @return Nome dell'attributo.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Metodo che restituisce l'identificativo numerico dell'attributo.
	 * @return Identificativo numerico dell'attributo.
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Metodo che restituisce una stringa rappresentante lo stato dell'oggetto.
	 * @return Stringa rappresentante lo stato dell'oggetto.
	 */
	public String toString() {
		return new String(this.name);
	}
	
}

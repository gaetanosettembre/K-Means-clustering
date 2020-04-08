package data;
import java.io.Serializable;
import java.util.Set;

/**
 * Classe astratta Item che modella un generico item (coppia attributo-valore). 
 * @author Gaetano
 */
@SuppressWarnings("serial")
public abstract class Item implements Serializable {
	
	protected Attribute attribute; //attributo coinvolto nell'item
	protected Object value; //valore assegnato all'attributo
	
	public Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}
	
	/**
	 * Metodo che restituisce l'attributo.
	 * @return Restituisce attribute.
	 */
	Attribute getAttribute() {
		return attribute;
	}
	
	/**
	 * Metodo che restituisce il valore dell'attributo.
	 * @return Restituisce il valore dell'attributo.
	 */
	Object getValue() {
		return value;
	}
	
	/**
	 * Metodo che restituisce una stringa che descrive il valore assegnato all'item.
	 */
	public String toString() {
		return value.toString();
	}
	
	/**
	 * Metodo astratto la cui implementazione sarà diversa per item discreto e item continuo.
	 * @param a Riferimento all'oggetto
	 * @return distanza tra Item.
	 */
	abstract double distance(Object a);
	
	/**
	 * Modifica il membro value, assegnandogli il valore
	 * restituito da data.computePrototype(clusteredData,attribute).
	 * @param data riferimento a Data
	 * @param clusteredData riferimento a clusteredData.
	 */
	public void update(Data data, Set<Integer> clusteredData) {
		value = data.computePrototype(clusteredData, attribute);
	}
	
}

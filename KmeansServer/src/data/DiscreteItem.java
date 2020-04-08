package data;

import java.io.Serializable;

/**
 * Classe concreta che estende la classe Item che modella un attributo discreto.
 * @author Gaetano
 *
 */
@SuppressWarnings("serial")
public class DiscreteItem extends Item implements Serializable {

	/**
	 * Costruttore di classe che inizializza la coppia attributo-valore.
	 * @param attribute Attributo.
	 * @param value Valore dell'attributo.
	 */
	public DiscreteItem(Attribute attribute, Object value) {
		super(attribute, value);
	}
	
	/**
	 * Restituisce 0 se (getValue().equals(a)), 1 altrimenti.
	 * @param a riferimento all'oggetto.
	 */
	@Override
	double distance(Object a) {
		if(getValue().equals(a))
			return 0;
		else
			return 1;
		
	}

}

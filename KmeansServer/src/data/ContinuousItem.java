package data;

import java.io.Serializable;

/**
 * Classe concreta che estende la classe Item e modella una coppia Attributo continuo-valore numerico.
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class ContinuousItem extends Item implements Serializable{
	
	/**
	 * Costruttore di classe che inizializza gli attributi della classe.
	 * @param attribute Elemento di tipo attributo.
	 * @param value Valore dell'attributo.
	 */
	public ContinuousItem(Attribute attribute, Double value) {
		super(attribute, value);
	}
	
	/**
	 * Determina la distanza (in valore assoluto) tra il valore scalato memorizzato nello item corrente
	 * e quello scalato associato al parametro a.
	 * @param a Riferimento all'oggetto a.
	 */
	@Override
	double distance(Object a) {
		double distance = 0.0;
		distance = Math.abs(((ContinuousAttribute)this.getAttribute()).getScaledValue(Double.parseDouble(this.getValue().toString())) - 
				((ContinuousAttribute)this.getAttribute()).getScaledValue(Double.parseDouble(a.toString())));			
		return distance;
	}
	
	

}

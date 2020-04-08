package data;

import java.io.Serializable;

/**
 * Classe concreta ContinuousAttribute che estende la classe Attribute e modella un attributo continuo (numerico).
 * Tale classe include i metodi per la “normalizzazione” del dominio dell'attributo nell'intervallo [0,1] 
 * al fine da rendere confrontabili attributi aventi domini diversi.
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class ContinuousAttribute extends Attribute implements Serializable {
	
	//Estremi dell'intervallo di valori (dominio) che l'attributo può realmente assumere
	private double max;
	private double min;
	
	/**
	 * Costruttore di classe che inizializza gli attributi della classe.
	 * @param name Nome dell'attributo.
	 * @param index Indice dell'attributo.
	 * @param min Valore minimo del dominio dell'attributo.
	 * @param max Valore massimo del dominio dell'attributo.
	 */
	public ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	}
	
	/**
	 * Calcola e restituisce il valore normalizzato del parametro passato in input
	 * @param v Valore dell'attributo da normalizzare.
	 * @return Valore normalizzato.
	 */
	public double getScaledValue(double v) {
		return (v-min)/(max-min);
	}


}

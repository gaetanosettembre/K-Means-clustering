package data;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * Classe concreta che estende la classe Attribute e rappresenta un attributo discreto (categorico).
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable {
	
	//TreeSet di oggetti String, uno per ciascun valore del dominio discreto
	private TreeSet<String> values;

	public DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		this.values = new TreeSet<String>();
		for(int i = 0; i < values.length; i++) {
			this.values.add(values[i]);
		}
	}

	/**
	 * Metodo che restituisce il numero di valori discreti nel dominio dell'attributo.
	 * @return Numero di valori discreti nel dominio dell'attributo.
	 */
	int getNumberOfDistinctValues() {
		return values.size();
	}


	/**
	 * Determina il numero di volte che il valore v compare in corrispondenza dell'attributo corrente (indice di colonna) negli
	 * esempi memorizzati in data e indicizzate (per riga) da idList.
	 * @param data oggetto Data.
	 * @param idList riferimento all'oggetto ArrayList.
	 * @param v valore.
	 * @return numero di occorrenze del valore discreto.
	 */
	int frequency(Data data, Set<Integer> idList, String v) {
		int freq = 0;
		Iterator<Integer> iter = idList.iterator();
		while(iter.hasNext()) {
			int i = Integer.parseInt(iter.next().toString());
			for(int j = 0; j < data.getNumberOfExplanatoryAttributes(); j++) {
					if(data.getAttributeValue(i, j).equals(v))
						freq++;
			}
		}
		return freq;
	}

	@Override
	/** 
	 * Restituisce il riferimento all' iteratore sul set di valore dell' attributo.
	 * @return riferimento all' oggetto iteratore dell' elemento set.
	 */
	public Iterator<String> iterator() {
		return values.iterator();
	}

}

package mining;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import data.Data;
import data.Tuple;

/**
 * Classe concreta che modella un cluster (importata)
 * @author Gaetano
 *
 */
@SuppressWarnings("serial")
public class Cluster implements Serializable {

	private Tuple centroid;
	private Set<Integer> clusteredData; 

	/**
	 * Costruttore di classe che inizializza il centroide del cluster.
	 * @param centroid Centroide del cluster.
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();

	}

	/**
	 * Metodo che restituisce il centroide del cluster.
	 * @return Centroide del cluster.
	 */
	Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Metodo per il calcolo del centroide.
	 * @param data Riferimento alla matrice.
	 */
	void computeCentroid(Data data) {
		for(int i = 0; i < centroid.getLength(); i++){
			centroid.get(i).update(data, clusteredData);

		}

	}

	//return true if the tuple is changing cluster
	boolean addData(int id) {
		return clusteredData.add(id);

	}

	//verifica se una transazione è clusterizzata nell'array corrente
	boolean contain(int id) {
		return clusteredData.contains(id);
	}


	//remove the tuple that has changed the cluster
	void removeTuple(int id) {
		clusteredData.remove(id);
	}

	/**
	 * Metodo che restituisce una stringa contenente il centroide.
	 * @return Stringa contenente il centroide.
	 */
	public String toString() {
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str += centroid.get(i);
		str+=")";
		return str;

	}

	/**
	 * Metodo che restituisce una stringa contenente il risultato della clusterizzazione.
	 * @param data Riferimento all'oggetto matrice.
	 * @return Stringa contenente il risultato della clusterizzazione
	 */
	public String toString(Data data) {
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		int array[] = new int[clusteredData.size()];
		int k = 0;
		for(Object o : clusteredData.toArray()) {
			array[k++] = Integer.parseInt(o.toString());
		}		
		for(int i=0;i<array.length;i++){
			str+="[";
			for(int j=0;j<data.getNumberOfExplanatoryAttributes();j++)
				str+=data.getAttributeValue(array[i], j)+" ";
			str+="] dist = "+getCentroid().getDistance(data.getItemSet(array[i]))+"\n";

		}
		str+="AvgDistance="+getCentroid().avgDistance(data, array) + "\n";
		return str;

	}

}

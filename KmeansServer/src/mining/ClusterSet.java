package mining;
import java.io.Serializable;
import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

/**
 * Classe concreta ClusterSet che modella un insieme di cluster determinati da k-means.
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class ClusterSet implements Serializable{

	Cluster C[];
	int i = 0;
	
	/**
	 * Costruttore di classe che inizializza la dimensione del clusterSet.
	 * @param k Dimensione del ClusterSet.
	 */
	ClusterSet(int k) {
		C = new Cluster[k];
	}
	
	/**
	 * Assegna c ad C[i] ed incrementa i.
	 * @param c Cluster da aggiungere
	 */
	void add(Cluster c) {
		C[i] = c;
		i++;
	}
	
	/**
	 * Restituisce il cluster.
	 * @param i Indice in ClusterSet.
	 * @return Cluster in posizione i.
	 */
	Cluster get(int i) {
		return C[i];
	}
	
	/**
	 * Sceglie i centroidi, crea un cluster per ogni centroide e lo aggiunge nel ClusterSet.
	 * @param data Riferimento all'oggetto Data.
	 * @throws OutOfRangeSampleSize 
	 */
	void initializeCentroids(Data data) throws OutOfRangeSampleSize {
		int centroidIndexes[] = data.sampling(C.length);
		for(int i = 0; i < centroidIndexes.length; i++){
			Tuple centroidI = data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidI));
		}
	}
	
	
	/**
	 * Calcola la distanza tra la tupla riferita da tuple ed il centroide di ciascun cluster in C e
	 * restituisce il cluster più vicino.
	 * @param tuple Riferimento ad un oggetto Tuple.
	 * @return Cluster più "vicino" alla tupla passata come parametro.
	 */
	Cluster nearestCluster(Tuple tuple) {
		double currentDistance = tuple.getDistance(C[0].getCentroid());
		int indexCluster = 0;
		for(int j = 1; j < i; j++) {
			if(tuple.getDistance(C[j].getCentroid()) < currentDistance) {
				currentDistance = tuple.getDistance(C[j].getCentroid());
				indexCluster = j;
			}
		}
		return get(indexCluster);
	}
	
	
	/**
	 * Identifica e restituisce il cluster a cui la tupla rappresentate l'esempio identificato da id.
	 * Se la tupla non è inclusa in nessun cluster restituisce null.
	 * @param id Riga della matrice data
	 * @return CLuster di appartenenza.
	 */
	Cluster currentCluster(int id) {
		int index = 0;
		boolean found = false;
		for(int j = 0; j < i; j++) {
			if(get(j).contain(id)) {
				found = true;
				index = j;
				break;
			}
		}
		if(found == true) {
			return get(index);
		}else {
			return null;
		}
	}

	/**
	 * Calcola il nuovo centroide per ciascun cluster in C.
	 * @param data Riferimento alla matrice data.
	 */
	void updateCentroids(Data data) {
		for(int i = 0; i < C.length; i++) {
			get(i).computeCentroid(data);
		}
	}
	
	/**
	 * Metodo che restituisce una stringa fatta da ciascun centroide dell’insieme dei cluster.
	 * @return str Stringa fatta da ciascun centroide dell’insieme dei cluster.
	 */
	public String toString() {
		String str = "";
		for(int i = 0; i < C.length; i++) {
			str += get(i).getCentroid().toString() + " ";
		}

		return str;
	}
	
	/**
	 * Restituisce una stringa che descrive lo stato di ciascun cluster in C.
	 * @param data Riferimento alla matrice data
	 * @return str stringa che descrive lo stato di ciascun cluster in C.
	 */
	public String toString(Data data) {
		String str="";
		for(int i = 0; i < C.length; i++){
			if (C[i] != null){
				str += i+1 + ":" + C[i].toString(data) + "\n";		
			}
		}
		return str;		
	}


}

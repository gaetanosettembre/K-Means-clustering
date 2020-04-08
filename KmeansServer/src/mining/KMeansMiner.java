package mining;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import data.Data;
import data.OutOfRangeSampleSize;

/**
 * Classe concreta KMeansMiner che include l'implementazione dell'algoritmo k-means
 * @author Gaetano
 */
@SuppressWarnings("serial")
public class KMeansMiner implements Serializable{
	
	ClusterSet C;
	
	/**
	 * Costruttore di classe che inizializza un cluster di dimensione k.
	 * @param k Dimensione del cluster.
	 */
	public KMeansMiner(int k) {
		C = new ClusterSet(k); 
	}
	
	/**
	 * Metodo che restituisce il ClusterSet.
	 * @return Restituice il ClusterSet.
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * Esegue l'algoritmo k-means eseguendo i passi dello pseudo-codice:
	 * 1) Scelta casuale di centroidi per k cluster;
	 * 2) Assegnazione di ciascuna riga della matrice al cluster avente centroide più vicino all'esempio;
	 * 3) Calcolo dei nuovi centroidi per ciascun cluster;
	 * 4) Ripete i passi 2 e 3 finchè due iterazioni consecutive non restituiscono centroidi uguali.
	 * @param data Riferimento alla matrice.
	 * @return Numero di iterazioni eseguite.
	 * @throws OutOfRangeSampleSize 
	 */
	public int kmeans(Data data) throws OutOfRangeSampleSize {
		int numberOfIterations = 1;
		//STEP 1
		C.initializeCentroids(data);
		boolean changedCluster = false;
		do {
			numberOfIterations++;
			//STEP 2
			changedCluster = false;
			for(int i = 0; i < data.getNumberOfExamples(); i++) {
				Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
				Cluster oldCluster = C.currentCluster(i);
				boolean currentChange = nearestCluster.addData(i);
				if(currentChange)
					changedCluster=true;
				//rimuovo la tupla dal vecchio cluster
				if(currentChange && oldCluster != null)
					//il nodo va rimosso dal suo vecchio cluster
					oldCluster.removeTuple(i);
			}			
			//STEP 3
			C.updateCentroids(data);
		} while(changedCluster);
		return numberOfIterations;
	}
	
	/**
	 * Costruttore che apre il file identificato da fileName, legge l'oggetto ivi memorizzato e lo assegna a C.
	 * @param fileName Percorso + nomeFile.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		this.C = (ClusterSet)in.readObject();
		in.close();
	}
	
	/**
	 * Apre il file identificato da fileName e salva l'oggetto riferito da C in tale file.
	 * @param fileName Percorso + nomeFile.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save(String fileName) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(C);
		out.close();
	}
	
	
}

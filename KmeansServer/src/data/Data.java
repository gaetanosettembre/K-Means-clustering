package data;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;


/**
 * Classe concreta Data per modellare l'insieme di transazioni o tuple.
 * @author Gaetano Settembre
 */
@SuppressWarnings("serial")
public class Data implements Serializable {

	private List<Example> data; // una matrice nXm di tipo Object dove ogni riga modella una transazioni
	private int numberOfExamples; // cardinalità dell’insieme di transazioni (numero di righe in data)
	private List<Attribute> explanatorySet; // linked list degli attributi in ciascuna tupla (schema della tabella di dati)
	
	/**
	 * Costruttore di classe che crea una tabella popolata da elementi presenti in un database.
	 * @param tableName Nome della tabella.
	 * @throws ClassNotFoundException
	 * @throws DatabaseConnectionException
	 * @throws SQLException
	 * @throws EmptySetException
	 * @throws NoValueException
	 * @throws InstantiationException
	 */
	public Data(String tableName) throws ClassNotFoundException, DatabaseConnectionException, SQLException, EmptySetException, NoValueException, InstantiationException {

		DbAccess db = new DbAccess();
		DbAccess.initConnection();
		TableData table = new TableData(db);
		data = table.getDistinctTransazioni(tableName);

		numberOfExamples = data.size();

		TableSchema schema = new TableSchema(db, tableName);
		explanatorySet = new LinkedList<Attribute>();
		for(int i = 0; i < schema.getNumberOfAttributes(); i++) {
			if(schema.getColumn(i).isNumber()) {
				double min = Double.parseDouble(table.getAggregateColumnValue(tableName, schema.getColumn(i), QUERY_TYPE.MIN).toString());
				double max = Double.parseDouble(table.getAggregateColumnValue(tableName, schema.getColumn(i), QUERY_TYPE.MAX).toString());
				explanatorySet.add(new ContinuousAttribute(schema.getColumn(i).getColumnName(),i, min, max));
			}else {
				String[] values = (String[])table.getDistinctColumnValues(tableName, schema.getColumn(i)).toArray(new String[table.getDistinctColumnValues(tableName, schema.getColumn(i)).size()]);
				explanatorySet.add(new DiscreteAttribute(schema.getColumn(i).getColumnName(),i, values));
			}
		}
		DbAccess.closeConnection();
	}

	/**
	 * Metodo che restituisce la cardinalità dell'insieme di transazioni o tuple.
	 * @return Cardinalità dell'insieme di transazioni o tuple. 
	 */
	public int getNumberOfExamples() {
		return this.numberOfExamples;		
	}

	/**
	 * Metodo che restituisce la dimensione di explanatorySet (insieme degli attributi).
	 * @return Dimensione di explanatorySet (insimeme degli attributi).
	 */
	public int getNumberOfExplanatoryAttributes() {
		return this.explanatorySet.size();
	}

	/**
	 * Metodo che restituisce lo schema dei dati.
	 * @return Restituisce lo schema dei dati.
	 */
	List<Attribute> getAttributeSchema() {
		return explanatorySet;
	}

	/**
	 * Restituisce il valore dell'attributo in posizione i,j.
	 * @param exampleIndex Indice di riga.
	 * @param attributeIndex Indice di colonna.
	 * @return Valore assunto in data in posizione attributeIndex, nella riga in posizione exampleIndex.
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * Metodo che restituisce una stringa che descrive lo schema della tabella e le transazioni memorizzate in essa.
	 * @return str Stringa che visualizza lo schema della tabella e le transazioni memorizzate in essa.
	 */
	public String toString(){
		String str="";
		for(int i=0; i< getNumberOfExplanatoryAttributes(); i++) {
			str += getAttributeSchema().get(i) + ", ";
		}
		str += "\n";
		for(int i = 0; i < getNumberOfExamples(); i++){
			str += (i+1)+":";
			for (int j = 0; j < getNumberOfExplanatoryAttributes(); j++){
				str += getAttributeValue(i,j) + ", ";
			}
			str += "\n";
		}
		return str;
	}

	/**
	 * Crea e restituisce un oggetto Tuple che modella una sequenza di coppie Attributo-Valore la i-esima riga di data.
	 * @param index Riga dell'oggetto data.
	 * @return tuple.
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(explanatorySet.size());
		for(int i = 0; i<explanatorySet.size(); i++) {
			if(explanatorySet.get(i) instanceof DiscreteAttribute)
				tuple.add(new DiscreteItem(explanatorySet.get(i), (String)data.get(index).get(i)), i);
			else
				tuple.add(new ContinuousItem(explanatorySet.get(i), (Double)data.get(index).get(i)), i);
		}
		return tuple;
	}

	/**
	 * Restituisce un array di k interi rappresentanti gli indici di riga in data 
	 * per le tuple inizialmente scelte come centroidi (passo 1 del k-means). 
	 * @param k Numero di cluster da generare.
	 * @return centroidIndexes Vettore degli indici di riga.
	 * @throws OutOfRangeSampleSize
	 * @throws NegativeArraySizeException
	 */
	public int[] sampling(int k) throws OutOfRangeSampleSize, NegativeArraySizeException {
		if(k > data.size() || k == 0) {
			throw new OutOfRangeSampleSize();
		}	
		int centroidIndexes[] = new int[k];
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		for(int i = 0; i < k; i++) {
			boolean found = false;
			int c;
			do {
				found = false;
				c = rand.nextInt(getNumberOfExamples());
				for(int j = 0; j < i; j++) {
					if(compare(centroidIndexes[j],c)) {
						found = true;
						break;
					}
				}
			}while(found);
			centroidIndexes[i]=c;
		}
		return centroidIndexes;
	}

	/**
	 * Restituisce vero se le due righe di data contengono gli stessi valori.
	 * @param i Indice di riga
	 * @param j Indice di riga
	 * @return equals 
	 */
	private boolean compare(int i, int j) { 
		boolean equals = true;
		for(int k = 0; k < getNumberOfExplanatoryAttributes(); k++ ) {
			if(!getAttributeValue(i,k).equals(getAttributeValue(j,k))) {
				equals = false;
				break;
			}
		}
		return equals;
	}


	/**
	 * Restituisce computePrototype(idList, (DiscreteAttribute)attribute).
	 * @param idList Insieme di indici di riga
	 * @param attribute Attributo rispetto al quale calcolare il centroide
	 * @return restituisce computePrototype(idList, (DiscreteAttribute)attribute).
	 */
	Object computePrototype(Set<Integer> idList, Attribute attribute) {
		if(attribute instanceof ContinuousAttribute)
			return computePrototype(idList, (ContinuousAttribute)attribute);
		else
			return computePrototype(idList, (DiscreteAttribute)attribute);
	}

	/**
	 * Determina il valore che occorre più frequentemente per attribute nel sottoinsieme di dati individuato da idList.
	 * @param idList Insieme degli indici delle righe di data appartenenti ad un cluster
	 * @param attribute Attributo rispetto al quale calcolare il valore prototipo
	 * @return Centroide rispetto all'attributo.
	 */
	String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
		Iterator<String> iter = attribute.iterator();
		String first = iter.next();
		int currentFrequency = attribute.frequency(this, idList, first);
		String nomeAttributo = first;
		while(iter.hasNext()) {
			String currElem = iter.next();
			if(attribute.frequency(this, idList, currElem) > currentFrequency) {
				currentFrequency = attribute.frequency(this, idList, currElem);
				nomeAttributo = currElem;
			}
		}
		return nomeAttributo;
	}

	/**
	 * Determina il valore prototipo come media dei valori osservati per attribute
	 * osservati per attribute nelle transazioni di data aventi indice di riga in idList.
	 * @param idList Insieme degli indici delle righe di data appartenenti ad un cluster
	 * @param attribute Attributo rispetto al quale calcolare il valore prototipo
	 * @return valore prototipo di un attributo continuo.
	 */
	Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
		double sum = 0.0;
		Iterator<Integer> iter = idList.iterator();
		while(iter.hasNext()) {
			int currIndex = Integer.parseInt(iter.next().toString());
			sum += Double.parseDouble(this.getAttributeValue(currIndex, attribute.getIndex()).toString());
		}
		return new Double(sum/idList.size());
	}
}

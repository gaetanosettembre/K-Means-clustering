package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


import database.TableSchema.Column;

/**
 * Classe concreta che modella l'insieme di transazioni collezionate in una tabella.
 * @author Gaetano
 */
public class TableData {

	DbAccess db;
	
	/**
	 * Costruttore di classe che inizializza gli attributi.
	 * @param db Database.
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Metodo che ricava lo schema della tabella, esegue una interrogazione e restituisce una lista di transazioni della matrice.
	 * @param table Nome della tabella nel DB.
	 * @return list Lista di transazioni.
	 * @throws SQLException
	 * @throws EmptySetException
	 */
	@SuppressWarnings("static-access")
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
		TableSchema schema = new TableSchema(this.db, table);
		String query = "SELECT DISTINCT * FROM " + table;
		Statement s = db.getConnection().createStatement();
		ResultSet result = s.executeQuery(query);
		List<Example> list = new ArrayList<Example>();
		while(result.next()) {
			Example ex = new Example();
			for(int i = 0; i < schema.getNumberOfAttributes(); i++) {
				if(schema.getColumn(i).isNumber()) {
					ex.add(result.getDouble(i+1));
				} else {
					ex.add(result.getString(i+1));
				}
			}
			list.add(ex);
		}
		return list;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre i valori distinti ordinati di column e 
	 * popolare un insieme da restituire (scegliere opportunamente in Set da utilizzare).
	 * @param table Nome della tabella nel DB.
	 * @param column Nome della colonna della tabella.
	 * @return set Insieme di valori distinti per l'attributo identificato dal nome della colonna.
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	public  Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException{
		String query = "SELECT DISTINCT " + column.getColumnName() + " FROM " + table + " ORDER BY " + column.getColumnName() + " ASC";
		Set<Object> set = new TreeSet<Object>();
		Statement s = db.getConnection().createStatement();
		ResultSet result = s.executeQuery(query);
		while(result.next()) {
			set.add(result.getObject(1));
		}
		return set;
	}

	
	/**
	 * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato (valore minimo o valore massimo)
	 * cercato nella colonna di nome column della tabella di nome table.
	 * @param table Nome della tabella nel DB.
	 * @param column Nome della colonna della tabella.
	 * @param aggregate Operatore SQL di aggregazione (MIN, MAX).
	 * @return aggregato cercato.
	 * @throws SQLException
	 * @throws NoValueException
	 */
	@SuppressWarnings("static-access")
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException{
		String query = "SELECT " + aggregate.toString() +"(" + column.getColumnName() +")" + " FROM " + table;
		Statement s = db.getConnection().createStatement();
		ResultSet result = s.executeQuery(query);
		if(result.next())
			return result.getObject(1);
		else
			return null;
	}

}
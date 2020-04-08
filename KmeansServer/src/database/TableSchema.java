package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che modella lo schema di una tabella nel database.
 * @author Gaetano
 */
public class TableSchema {
	
	DbAccess db;
	
	/**
	 * Classe che modella una colonna nel db.
	 * @author Gaetano
	 */
	public class Column {
		private String name;
		private String type;

		/**
		 * Costruttore di classe che inizializza gli attributi.
		 * @param name Nome della colonna.
		 * @param type Tipologia del contenuto della colonna.
		 */
		Column(String name,String type){
			this.name = name;
			this.type = type;
		}
		
		/**
		 * Metodo che restituisce il nome della colonna.
		 * @return Nome della colonna.
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * Metodo che verifica se la colonna contiene valori numerici o meno.
		 * @return Valore booleano.
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * Metodo che restituisce una stringa che descrive lo stato di una colonna.
		 * @return Stringa che restituisce una stringa che descrive lo stato di una colonna.
		 */
		public String toString(){
			return name + ":" + type;
		}
	}


	List<Column> tableSchema = new ArrayList<Column>();
	
	@SuppressWarnings("static-access")
	public TableSchema(DbAccess db, String tableName) throws SQLException {
		this.db = db;

		HashMap<String,String> mapSQL_JAVATypes = new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");


		Connection con = db.getConnection();
		
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {

			if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(new Column(
						res.getString("COLUMN_NAME"),
						mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
						);
		}
		res.close();
	}

	/**
	 * Metodo che restituisce il numero di colonne della tabella.
	 * @return Numero di colonne della tabella.
	 */
	public int getNumberOfAttributes(){
		return tableSchema.size();
	}

	/**
	 * Metodo che restituisce la colonna della tabella indicata dall'indice.
	 * @param index Indice colonna.
	 * @return Colonna della tabella indicata dall'indice.
	 */
	public Column getColumn(int index){
		return tableSchema.get(index);
	}


}

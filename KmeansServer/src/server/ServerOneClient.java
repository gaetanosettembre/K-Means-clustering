package server;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import mining.KMeansMiner;

/**
 * Classe concreta che modella il comportamento del server a connessione avvenuta.
 * @author Gaetano
 */
public class ServerOneClient extends Thread{

	private Socket socket;
	private ObjectInputStream in; 
	private ObjectOutputStream out;
	private KMeansMiner kmeans;

	/**
//	 * Costuttore di classe che inizializza la socket, gli stream di input e output e avvia il thread.
	 * @param s Socket.
	 * @throws IOException
	 */
	public ServerOneClient(Socket s) throws IOException {
		this.socket = s;
		out = new ObjectOutputStream(socket.getOutputStream());	
		in = new ObjectInputStream(socket.getInputStream()) ;		
		this.start();		
	}

	/**
	 * Metodo che esegue la richiesta del client.
	 */
	@Override
	public void run() {
		try {
			int answerMenu = in.readInt();
			switch(answerMenu) {
			case 1:
				String fileName = in.readObject().toString();	
				this.kmeans = new KMeansMiner(fileName + ".dmp");
				out.writeObject(kmeans.getC().toString());
				out.writeObject("OK");
				break;	
			case 2:
				int k = Integer.parseInt(in.readObject().toString());
				String tableName = in.readObject().toString();
				String file = in.readObject().toString();
				Data data = new Data(tableName);
				this.kmeans = new KMeansMiner(k);
				kmeans.kmeans(data);
				kmeans.save(file + ".dmp");
				out.writeObject(kmeans.getC().toString(data));
				out.writeObject("OK");
				break;
			default:
				return;
			}
		}
		catch(FileNotFoundException e) {
			System.err.println("File non trovato!");
			try {
				out.writeObject("OPS, qualcosa è andato storto");
				out.writeObject("ERRORFILE");
			} catch (IOException e1) {
			}
		} catch(EOFException e) {
			System.err.println("Eccezione EOF");			
		} catch(IOException e) {
		} catch (ClassNotFoundException e) {
			System.err.println("Eccezione ClassNotFound");
		} catch (InstantiationException e) {
			System.err.println("Eccezione Instantiation");
		} catch (DatabaseConnectionException e) {
			System.err.println(e.toString());
		} catch (SQLException e) {
			System.err.println("Eccezione SQL");
		} catch (EmptySetException e) {
			System.err.println(e.toString());
		} catch (NoValueException e) {
			System.err.println(e.toString());
		} catch (OutOfRangeSampleSize e) {
			System.err.println(e.toString());
		} finally {
			try {
				socket.close();
			} catch(IOException e) {
				System.err.println("Socket non chiusa");
			}
		}

	}
}

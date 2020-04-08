package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe concreta che gestisce il comportamento di un server in attesa di rischiesta di connessioni da parte dei client.
 * @author Gaetano
 */
public class MultiServer {

	private int PORT = 8080;

	/**
	 * Costruttore di classe che assegna il numero di porta e avvia il thread.
	 * @param port Numero di porta.
	 * @throws IOException
	 */
	public MultiServer(int port) throws IOException {
		this.PORT = port;
		run();
	}


	/**
	 * Metodo che istanzia un oggetto di classe MultiServer con porta specifica.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new MultiServer(8080);
	}

	/**
	 * Metodo che rappresenza il punto di avvio del thread.
	 * @throws IOException
	 */
	private void run() throws IOException {
		ServerSocket SSocket = new ServerSocket(PORT);
		System.out.println("Server avviato!");
		try {
			while(true) {
				// Si blocca finchè non si verifica una connessione:
				Socket socket = SSocket.accept();
				try {
					new ServerOneClient(socket);
				} catch(IOException e) {
					// Se fallisce chiude il socket, altrimenti il thread la chiuderà:
					socket.close();
				}
			}
		} finally {
			SSocket.close();
		}

	}

}

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")

/**
 * Classe che genera una JApplet.
 * @author Gaetano
 */
public class KMeans extends JApplet {

	private ObjectOutputStream out;
	private ObjectInputStream in;
	private static final String SERVER = "127.0.0.1";
	private static final int PORT = 8080;
	private static InetAddress addr = null;
	private static Socket socket;
	private TabbedPane tab;

	/**
	 * Metodo che inizializza la componente grafica.
	 */
	public void init() {
		tab = new TabbedPane();
		getContentPane().setLayout(new GridLayout(1,1));
		getContentPane().add(tab);
	}


	/**
	 * Classe che estende JPanel e definisce la struttura del TabbedPane
	 * @author Gaetano
	 */
	class TabbedPane extends JPanel {

		private JPanelCluster panelDB;  
		private JPanelCluster panelFile;

		@SuppressWarnings("deprecation")
		/**
		 * Costruttore di classe che inizializza i membri panelDB e panelFile e li aggiunge ad un oggetto istanza della classe TabbedPane.
		 */
		TabbedPane() {
			super(new GridLayout(1,1));
			JTabbedPane jp = new JTabbedPane();
			panelDB = new JPanelCluster("Scopri cluster", new DBActionListener());			
			panelFile = new JPanelCluster("Carica dati", new FileActionListener());
			ImageIcon iconDB = new ImageIcon("../iconDB.png");
			ImageIcon iconFile = new ImageIcon("../iconFile.png");
			jp.addTab("Database", iconDB, panelDB, "Database");
			jp.addTab("File", iconFile, panelFile, "File");
			this.add(jp);			
			panelFile.kText.hide();
			panelFile.tableText.hide();
			panelFile.kLabel.hide();
			panelFile.tableLabel.hide();
			panelDB.fileLabel.setText("Salva in");
			panelFile.fileLabel.setText("Carica da");			
			jp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}

		/**
		 * Metodo che acquisisce il nome del file da caricare e visualizza il contenuto del file.
		 * @throws SocketException
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void learningFromFileAction() throws SocketException, IOException, ClassNotFoundException {
			try {
				addr = InetAddress.getByName(SERVER);
				System.out.println("addr = " + addr);
				socket = new Socket(addr, PORT);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());			
			}catch(UnknownHostException ex) {
				System.err.println("Errore nella connessione con il server");
			} catch (IOException e) {
				System.err.println("Errore istanziazione degli stream");
			}
			out.writeInt(1);
			String fileName = "";
			fileName = panelFile.fileText.getText();
			if(fileName.equals("")) {
				JOptionPane.showMessageDialog(this, "Nome file di salvataggio non inserito", "Attenzione", JOptionPane.ERROR_MESSAGE);
				return;
			}
			out.writeObject(fileName);
			String StringCluster = in.readObject().toString();
			panelFile.fileText.setText("");
			panelFile.clusterOutput.setText(StringCluster);
			String OKmsg = in.readObject().toString();
			if(OKmsg.equals("OK")) {
				JOptionPane.showMessageDialog(this, "File caricato con successo", "Avviso", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(OKmsg.equals("ERRORFILE")) {
				JOptionPane.showMessageDialog(this, "File inserito non esiste", "Avviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			socket.close();
		}


		/**
		 * Metodo che acquisisce il nome della tabella, il numero dei cluster e il nome del file in cui salvare la clusterizzazione e visualizza i risultati.
		 * @throws SocketException
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void learningFromDBAction() throws SocketException, IOException, ClassNotFoundException {
			try {
				addr = InetAddress.getByName(SERVER);
				System.out.println("addr = " + addr);
				socket = new Socket(addr, PORT);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());			
			}catch(UnknownHostException ex) {
				System.err.println("Errore nella connessione con il server");
			} catch (IOException e) {
				System.err.println("Errore istanziazione degli stream");
			}
			out.writeInt(2);
			int k = 0;	
			try{
				k = new Integer(panelDB.kText.getText()).intValue();
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this, "Inserire un valore numerico valido per numero di cluster", "Attenzione", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tableName = "";
			String result = "";
			String fileName = "";
			tableName = panelDB.tableText.getText();
			if(tableName.equals("")) {
				JOptionPane.showMessageDialog(this, "Nome tabella non inserito", "Attenzione", JOptionPane.ERROR_MESSAGE);
				return;
			}
			fileName = panelDB.fileText.getText();
			if(fileName.equals("")) {
				JOptionPane.showMessageDialog(this, "Nome file di salvataggio non inserito", "Attenzione", JOptionPane.ERROR_MESSAGE);
				return;
			}
			out.writeObject(k);
			out.writeObject(tableName);
			out.writeObject(fileName);
			result = in.readObject().toString();
			panelDB.clusterOutput.setText(result);
			panelDB.fileText.setText("");
			panelDB.kText.setText("");
			String OKmsg = in.readObject().toString();
			if(OKmsg.equals("OK")) {
				JOptionPane.showMessageDialog(this, "Clusterizzazzione e salvataggio terminati con successo", "Avviso", JOptionPane.INFORMATION_MESSAGE);
			}
			socket.close();
		}


		/**
		 * Classe che estende JPanel e definisce la struttura grafica del tab.
		 * @author Gaetano
		 */
		class JPanelCluster extends JPanel {

			private JTextField tableText = new JTextField(20);
			private JTextField kText = new JTextField(10);
			private JTextField fileText = new JTextField(20);
			private JTextArea clusterOutput = new JTextArea();
			private JButton executeButton;

			private JPanel upPanel;
			private JPanel centralPanel;
			private JPanel downPanel;

			private JLabel tableLabel;
			private JLabel kLabel;
			private JLabel fileLabel;
			private JLabel OutputLabel;

			/**
			 * Costruttore di classe che inizializza tutte le componenti grafico del pannello e aggiunge l'ascoltatore al bottone.
			 * @param buttonName Nome bottone.
			 * @param a Identificatore del Listener.
			 */
			JPanelCluster(String buttonName, java.awt.event.ActionListener a) {
				setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
				this.upPanel = new JPanel(new FlowLayout());
				this.centralPanel = new JPanel(new BorderLayout(1,1));
				this.downPanel = new JPanel(new FlowLayout());
				this.kLabel = new JLabel("N. cluster");
				this.tableLabel = new JLabel("Nome tabella");
				this.fileLabel = new JLabel();
				this.OutputLabel = new JLabel("Risultato");
				this.tableText.setText("playtennis");

				upPanel.add(tableLabel);
				upPanel.add(tableText);
				upPanel.add(kLabel);
				upPanel.add(kText);
				upPanel.add(fileLabel);
				upPanel.add(fileText);
				add(upPanel);

				clusterOutput.setEditable(false);
				JScrollPane scrollingArea = new JScrollPane(clusterOutput);
				centralPanel.add(OutputLabel, BorderLayout.NORTH);
				centralPanel.add(scrollingArea, BorderLayout.CENTER);
				add(centralPanel);
				this.executeButton = new JButton(buttonName);
				this.executeButton.addActionListener(a);
				downPanel.add(executeButton);
				add(downPanel);
			}


		}

		/**
		 * Classe che determina il comportamento del bottone al suo click.
		 * @author Gaetano
		 */
		class DBActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					learningFromDBAction();
				} catch (SocketException e1) {
					System.err.println("Errore SocketException");
				} catch (ClassNotFoundException e1) {
					System.err.println("Errore ClassNotFoundException");
				} catch (IOException e1) {
					System.err.println("Errore di IOException");
				}

			}

		}

		/**
		 * Classe che determina il comportamento del bottone al suo click.
		 * @author Gaetano
		 */
		class FileActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					learningFromFileAction();
				} catch (SocketException e1) {
					System.err.println("Errore SocketException");
				} catch (ClassNotFoundException e1) {
					System.err.println("Errore ClassNotFoundException");
				} catch (IOException e1) {
					System.err.println("Errore di IOException");
				}
			}
		}

	}

}

package position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import amak.Migrant;
import business.Couleur;

public class ConnectedClientAcceleration implements Runnable {

	private BufferedReader in;
	private PrintWriter out;
	private ServerThreadAcceleration server;
	private Migrant agent;
	private double[] cooInitiale;
	private Socket socket;
	private int tmp = 0;

	public ConnectedClientAcceleration(final Socket clientSocket, final ServerThreadAcceleration _server) {
		System.out.println("j'initialise le socket");
		socket = clientSocket;
		cooInitiale = new double[2];
		cooInitiale[0] = 2;
		cooInitiale[1] = 12.5;
		try {
			server = _server;
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			new Thread(this).start();
			// out.println("Vous �tes connect� z�ro !");
			// out.flush();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		double[] coo = new double[2];
		String line;
		try {
			while ((line = in.readLine()) != null) {
				if (true || tmp++ % 10 == 0) {
					System.out.println("Hey ! je viens de recevoir quelque chose !");
					final String[] res = line.split(";");

					if (res[0].equals("move")) {
						double[] resDouble = new double[6];

						for (int i = 1; i < res.length; i++)
							resDouble[i - 1] = Double.parseDouble(res[i]);
						System.out.println("Je recoie " + resDouble[0] + ";" + resDouble[1] + ";" + resDouble[2] + ";"
								+ resDouble[3] + ";" + resDouble[4] + ";" + resDouble[5]);

						if (agent == null) {
							System.out.println("Je n'ai pas encore de blobs. Je vais donc en prendre un");
							System.out.println("Que je place en " + cooInitiale[0] + ";" + cooInitiale[1]);
							agent = server.adopterBlob(cooInitiale);
						} else {
							// Les trois 1eres valeurs me donne l'info de l'orientation du t�l�phone

							// les 3 suivantes sur l'acc�l�ration dans ce rep�re.

							{
								coo = agent.getBlob().getCoordonnee().clone();
								coo[0] += resDouble[3];
								coo[1] += resDouble[4];
							}

							server.moveBlob(agent, coo);
						}

					}
					/*if (agent == null || agent.getBlob() == null) {
						out.println("");
						out.flush();
					} else {*/
						ArrayList<double[]> listePos = agent.getBlob().getGlobules_position();
						ArrayList<Couleur> listeCouleur = agent.getBlob().getGlobules_couleurs();
						String str = "" + listePos.get(0)[0] + ";" + listePos.get(0)[1] + ";"
								+ listeCouleur.get(0).toString();

						for (int i = 1; i < listePos.size(); i++) {
							str += ":" + listePos.get(i)[0] + ";" + listePos.get(i)[1] + ";"
									+ listeCouleur.get(i).toString();
						}
						out.println(str);
						out.flush();
					//}
				}
			}

			System.out.println("sortie de la boucle while ----");
			agent.rentrerBlob(server);

		} catch (final IOException e) {
			System.out.println("erreur Connexion");
			agent.rentrerBlob(server);
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

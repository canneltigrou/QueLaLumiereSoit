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

	public ConnectedClientAcceleration(final Socket clientSocket,
			final ServerThreadAcceleration _server) {
		System.out.println("j'initialise le socket");
		try {
			server = _server;
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			new Thread(this).start();
			out = new PrintWriter(clientSocket.getOutputStream());
	        //out.println("Vous êtes connecté zéro !");
	        //out.flush();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		cooInitiale = new double[2];
		cooInitiale[0] = 2;
		cooInitiale[1] = 12.5;

	}

	@Override
	public void run() {
		
		
		double[] coo = new double[2];
		String line;
		try {
			while ((line = in.readLine()) != null) {
				System.out.println("Hey ! je viens de recevoir quelque chose !");
				final String[] res = line.split(";");
				
				if(res[0].equals("move")) {
					double[] resDouble = new double[6];
					for (int i = 0; i < coo.length; i++)
						resDouble[i] = Double.parseDouble(res[i]);
					System.out.println("Je recoie " + resDouble[4] + ";" + resDouble[5]);
					
					if (agent ==  null) {
						System.out.println("Je n'ai pas encore de blobs. Je vais donc en prendre un");
						System.out.println("Que je place en " + cooInitiale[0] + ";" + cooInitiale[1]);
						agent = server.adopterBlob(cooInitiale);
					}
					else
					{
						// Les trois 1eres valeurs me donne l'info de l'orientation du téléphone
						
						
						// les 3 suivantes sur l'accélération dans ce repère.
						
						synchronized (agent.getBlob().lock) {
							coo = agent.getBlob().getCoordonnee().clone();
							coo[0] += resDouble[4];
							coo[1] += resDouble[5];
						}
					
						server.moveBlob(agent, coo);
					}
					
					
					
				}
				
				ArrayList<double[]> listePos = agent.getBlob().getGlobules_position();
				ArrayList<Couleur> listeCouleur = agent.getBlob().getGlobules_couleurs();
				String str = "" + listePos.get(0)[0] + ";" + listePos.get(0)[1] + ";" + listeCouleur.get(0).toString();
							
				for (int i = 1; i < listePos.size(); i++) {
					str += ":" + listePos.get(i)[0] + ";" + listePos.get(i)[1] + ";" + listeCouleur.get(i).toString();
				}
				out.println(str);
		        out.flush();
				
				
				
				
				
				
				/*
				 if (res[0].equals("adopter")) {
				 
					server.sortirBlob(agent);
					//.getChart(res[1], ChartType.valueOf(res[2]), true, Integer.valueOf(res[3]));
				} else if (res[0].equals("move")) {
					
					server.moveBlob(agent, coo);
					
					if (res[2].isEmpty()) {
						server.getChart(res[1], ChartType.LINE, true, -1).add(
								Double.parseDouble(res[3]),
								Double.parseDouble(res[4]));
					} else {
						server.getChart(res[1], ChartType.LINE, true, -1).add(res[2],
								Double.parseDouble(res[3]),
								Double.parseDouble(res[4]));
					}
				} else if (res[0].equals("close")) {
					server.getChart(res[1], ChartType.LINE, true, -1).close();
				}
				*/
			}
		} catch (final IOException e) {

		}
	}

}

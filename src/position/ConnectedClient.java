package position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import amak.Migrant;

public class ConnectedClient implements Runnable {

	private BufferedReader in;
	private ServerThread server;
	private Migrant agent;

	public ConnectedClient(final Socket clientSocket,
			final ServerThread _server) {
		System.out.println("j'initialise le socket");
		try {
			server = _server;
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			new Thread(this).start();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		String line;
		try {
			while ((line = in.readLine()) != null) {
				System.out.println("Hey ! je viens de recevoir quelque chose !");
				final String[] res = line.split(";");
				
				if(res[0].equals("move")) {
					if (agent ==  null) {
						System.out.println("Je n'ai pas encore de blobs. Je vais donc en prendre un");
						agent = server.adopterBlob();
					}
					
					double[] coo = new double[2];
					coo[0] = Double.parseDouble(res[1]);
					coo[1] = Double.parseDouble(res[2]);
					System.out.println("Je demande de sortir le blob à " + coo[0] + ";" + coo[1]);
					server.moveBlob(agent, server.cooGeolocToMetre(coo));
					
					
				}
				
				
				
				
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

package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {

	/**
	 * A lancer APRES le lancement du main du serveur (pour TESTER)
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 9876);
			s.setSoTimeout(1000);
			PrintWriter printer = new PrintWriter(s.getOutputStream());
			//printer.println("Vous allez voir ce que vous allez voir !!");
			printer.println("Comment ça?");
			printer.println("CA MARCHE ?");
			s.close();
		} catch (UnknownHostException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
	}

}

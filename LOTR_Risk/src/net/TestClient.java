package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import objects.Joueur;

public class TestClient {

	/**
	 * A lancer APRES le lancement du main du serveur (pour TESTER)
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 9876);
			s.setSoTimeout(4000);
			Scanner input = new Scanner(System.in);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			Joueur j = new Joueur("SEIKOMI");
			String entree = input.nextLine();
			do
			{
				if (entree != null && !entree.equals("")) {
					oos.writeObject((Object) entree);
					oos.flush();
				}
				if (entree != null && entree.equals("obj")) 
				{
					oos.writeObject(j);
					oos.flush();
				}
			} while (!(entree = input.nextLine()).equals("tg"));
			input.close();
			s.close();
		} catch (UnknownHostException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); 
		} 
		
	}

}

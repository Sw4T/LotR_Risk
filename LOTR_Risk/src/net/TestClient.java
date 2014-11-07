package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
			PrintWriter printer = new PrintWriter(s.getOutputStream(), true);
			String entree = input.nextLine();
			do
			{
				if (entree != null && entree != "")
					printer.println(entree);
				else 
					System.out.println("Tu vas te taire ?");
			} while (!(entree = input.nextLine()).equals("tg"));
			printer.println(entree);
			input.close();
			s.close();
		} catch (UnknownHostException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); 
		} 
		
	}

}

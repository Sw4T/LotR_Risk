package objects;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import net.Emission;
import net.EnvoiReception;
import net.Reception;

public class Client  
{
	private Socket socket;
	private Emission out; //Flux sortant vers le client
	private Reception in; //Flux entrant
	
	public Client(Socket s) throws IOException {
		this.socket = s;
		this.out = new Emission(socket.getOutputStream());
		this.in = new Reception(socket.getInputStream());
	}
	
	/**
	 * Exécute le traitement attendue par la constante de jeu passé en paramètre et retourne le Thread éxecutant le traitement.
	 * @param traitement
	 * 		constante définie par l'interface <b>InterfaceLOTR</b>
	 */
	public EnvoiReception definirTraitementEtExecuter(int traitement, ArrayList<Joueur> listJoueurs) {
		EnvoiReception envoiReseau = new EnvoiReception(in, out, listJoueurs, traitement);
		envoiReseau.start();
		return envoiReseau;
	}
	
	/** 
	 * Retourne la constante de jeu sous forme d'entier reçu par le client
	 */
	public int get_ConstanteJeu_FromClient() throws ClassNotFoundException, IOException {
		return ((int) this.in.getInt());
	}
	
	public void fermerConnexion() throws IOException {
		out.close();
		in.close();
		socket.close();
	}
}

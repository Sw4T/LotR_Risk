package com.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DonneesConnexion {

	private ObjectInputStream obj_in;
	private Emission bufferSortie; //TODO
	private Socket socket;
	private boolean isSendMode;
	
	
	public DonneesConnexion(String servAddr, int port) {
		Boolean reussi = this.connexionServeur(servAddr, port);
		if (reussi)
			this.isSendMode = true;
		else
			this.isSendMode = false;
	}

	private Boolean connexionServeur(String servAddr, int port)
	{
		try {
			this.socket = new Socket();
			this.socket.connect(new InetSocketAddress(servAddr, port), 5000);
			this.bufferSortie = new Emission(this.socket.getOutputStream(), this);
			this.obj_in = new ObjectInputStream(this.socket.getInputStream());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean get_Connexion_Reussi() {
		return this.isSendMode;
	}
	
}

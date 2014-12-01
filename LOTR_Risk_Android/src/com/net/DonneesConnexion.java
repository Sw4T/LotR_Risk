package com.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DonneesConnexion {

	private Reception in;
	private Emission out; 
	private Socket socket;
	private boolean connectionReussi;
	
	
	public DonneesConnexion(String servAddr, int port) {
		Boolean reussi = this.connexionServeur(servAddr, port);
		if (reussi)
			this.connectionReussi = true;
		else
			this.connectionReussi = false;
	}

	private Boolean connexionServeur(String servAddr, int port)
	{
		try {
			this.socket = new Socket();
			this.socket.setSoTimeout(5000);
			this.socket.connect(new InetSocketAddress(servAddr, port));
			this.out = new Emission(this.socket.getOutputStream(), this);
			this.in = new Reception(this.socket.getInputStream());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean get_Connexion_Reussi() {
		return this.connectionReussi;
	}

	public Emission getOutput() {
		return out;
	}
	
	public Reception getInput() {
		return this.in;
	}
	
	public void close() throws IOException {
		out.close();
		in.close();
	}
	
	public String getString() throws IOException, ClassNotFoundException {
		return this.in.getString();
	}
	
}

package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import objects.Joueur;

public class Emission {

	private ObjectOutputStream obj_out;
	private ThreadEnvoiReception T_Reception;
	
	public Emission(OutputStream os, ThreadEnvoiReception T_Read) throws IOException {
		this.obj_out = new ObjectOutputStream(os);	
		this.T_Reception = T_Read;
	}
	
	public void sendJoueur(Joueur j) throws IOException {
		if (this.T_Reception.isSendMode() == true) {
			this.obj_out.writeObject(j);
			this.obj_out.flush();
		}
	}
	
	public void sendString(String message) throws IOException {
		if (this.T_Reception.isSendMode() == true) {
			this.obj_out.writeObject(message);
			this.obj_out.flush();
		}
	}
	
	public void sendInt(int toSend) throws IOException {
		if (this.T_Reception.isSendMode() == true) {
			this.obj_out.writeObject(toSend);
			this.obj_out.flush();
		}
	}

}

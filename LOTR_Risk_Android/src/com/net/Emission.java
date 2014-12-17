package com.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import objects.Joueur;

public class Emission {

	private ObjectOutputStream obj_out;
	
	public Emission(OutputStream os, DonneesConnexion T_Read) throws IOException {
		this.obj_out = new ObjectOutputStream(os);	
	}
	
	public void sendJoueur(Joueur j) throws IOException {
		this.obj_out.writeObject(j);
		this.obj_out.flush();
	}
	
	public void sendString(String message) throws IOException {
		this.obj_out.writeObject(message);
		this.obj_out.flush();
	}
	
	public void sendInt(int toSend) throws IOException {
		this.obj_out.writeObject(toSend);
		this.obj_out.flush();
	}
	
	public void close() throws IOException {
		this.obj_out.close();
	}
	
	public void reset() throws IOException {
		this.obj_out.reset();
	}

}

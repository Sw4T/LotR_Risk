package com.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import objects.Joueur;

public class Reception {

	private ObjectInputStream obj_in;
	
	public Reception(InputStream in) throws IOException {
		this.obj_in = new ObjectInputStream(in);
	}
	
	public Joueur getJoueur() throws ClassNotFoundException, IOException
	{
		Object objRecu = this.obj_in.readObject();
		if (!(objRecu instanceof Joueur))
			return null;
		return ((Joueur) objRecu);		
	}
	
	public String getString() throws ClassNotFoundException, IOException 
	{
		Object objRecu = this.obj_in.readObject();
		if (!(objRecu instanceof String))
			return null;
		return ((String) objRecu);
	}
	
	public Integer getInt() throws ClassNotFoundException, IOException 
	{
		Object objRecu = this.obj_in.readObject();
		if (!(objRecu instanceof Integer))
			return null;
		return ((Integer) objRecu);
	}
	
	public void close() throws IOException {
		this.obj_in.close();
	}
}

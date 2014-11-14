package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import objects.Joueur;

public class Reception {

	private ObjectInputStream obj_in;
	
	public Reception(InputStream in) throws IOException {
		this.obj_in = new ObjectInputStream(in);
	}
	
	public Joueur getJoueur()
	{
		try {
			Object objRecu = this.obj_in.readObject();
			if (!(objRecu instanceof Joueur))
				return null;
			return ((Joueur) objRecu);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getString() 
	{
		try {
			Object objRecu = this.obj_in.readObject();
			if (!(objRecu instanceof String))
				return null;
			return ((String) objRecu);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getInt() {
		try {
			Object objRecu = this.obj_in.readObject();
			if (!(objRecu instanceof Integer))
				return null;
			return ((Integer) objRecu);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

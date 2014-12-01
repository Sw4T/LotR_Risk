package com.net;

import java.io.IOException;
import java.util.ArrayList;

import objects.Joueur;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.game.InterfaceLOTR;

public class TacheTransmission extends AsyncTask<Integer, Void, Boolean> implements InterfaceLOTR {

	private Context context;
	private DonneesConnexion connexion;
	private ArrayList<Joueur> listJoueurs;
	
	public TacheTransmission(Context context, DonneesConnexion con, ArrayList<Joueur> listJoueur) 
	{
		this.context = context;
		this.connexion = con;
		this.listJoueurs = listJoueur;
	}
	
	@Override
	protected Boolean doInBackground(Integer ... params) {
		Integer traitement = params[0];
		Integer nbJoueurs =  params[1];
		switch (traitement.intValue()) {
			case ENVOI_JOUEURS : 
				try {
					this.connexion.getOutput().sendInt(ENVOI_JOUEURS);
					if (!(this.connexion.getString().equals("#OK"))) //Vérification de la synchro du serveur
						return false;
					this.connexion.getOutput().sendInt(nbJoueurs.intValue());
					for (int i = 0; i < nbJoueurs.intValue(); i++) {
						Joueur toSend = listJoueurs.get(i);
						this.connexion.getOutput().sendJoueur(toSend);
					}
					return true;
				} 
				catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					return false;
				}
			default : return false;
		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {	
		if ((boolean) result)
			Toast.makeText(this.context, "Transmission OK", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this.context, "Transmission FAILED", Toast.LENGTH_SHORT).show();
    }
}

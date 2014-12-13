package com.net;

import java.io.IOException;
import java.util.ArrayList;

import objects.Joueur;
import android.content.Context;
import android.os.AsyncTask;

import com.game.InterfaceLOTR;

public class TacheTransmission extends AsyncTask<Integer, Void, ArrayList<Joueur>> implements InterfaceLOTR {

	//private Context context;
	private DonneesConnexion connexion;
	private ArrayList<Joueur> listJoueurs;
	
	public TacheTransmission(Context context, DonneesConnexion con, ArrayList<Joueur> listJoueur) 
	{
		//this.context = context;
		this.connexion = con;
		this.listJoueurs = listJoueur;
	}
	
	@Override
	protected ArrayList<Joueur> doInBackground(Integer ... params) {
		Integer traitement = params[0];
		Integer nbJoueurs = listJoueurs.size();
		switch (traitement.intValue()) 
		{
			case CREATION_JOUEURS : 
				try 
				{
					connexion.getOutput().sendInt(CREATION_JOUEURS);
					if (!(connexion.getString().equals("#OK"))) //Vérification de la synchro du serveur
						return null;
					connexion.getOutput().sendInt(nbJoueurs);
					for (int i = 0; i < nbJoueurs; i++) {
						Joueur toSend = listJoueurs.get(i);
						connexion.getOutput().sendJoueur(toSend);
					}
					return listJoueurs;
				} 
				catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			case SERVEUR_ENVOI_JOUEURS :
				try 
				{
					this.connexion.getOutput().sendInt(SERVEUR_ENVOI_JOUEURS);
					if (!(this.connexion.getString().equals("#OK"))) //Vérification de la synchro du serveur
						return null;
					for (int i = 0; i < nbJoueurs; i++) {
						Joueur joueurRecu = connexion.getInput().getJoueur();
						listJoueurs.set(i, joueurRecu);
					}
					return listJoueurs;
				} 
				catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			default : return null;
		}
	}
}

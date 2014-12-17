package com.net;

import java.io.IOException;
import java.util.ArrayList;

import objects.Joueur;
import android.os.AsyncTask;
import android.util.Log;

import com.game.InterfaceLOTR;

public class TacheTransmission extends AsyncTask<Integer, Void, ArrayList<Joueur>> implements InterfaceLOTR {

	private DonneesConnexion connexion;
	private ArrayList<Joueur> listJoueurs;
	
	public TacheTransmission(DonneesConnexion con, ArrayList<Joueur> listJoueur) 
	{
		this.connexion = con;
		this.listJoueurs = listJoueur;
	}
	
	@Override
	protected ArrayList<Joueur> doInBackground(Integer ... params) {
		if (this.connexion == null)
			return null;
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
					if (nbJoueurs == 2) { //Gestion du neutre
						nbJoueurs++;
						listJoueurs.add(new Joueur("Neutre", "#ff000000"));
					}
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
			case SERVEUR_RECEPTION_JOUEURS :
				try 
				{
					this.connexion.getOutput().sendInt(SERVEUR_RECEPTION_JOUEURS);
					if (!(this.connexion.getString().equals("#OK"))) //Vérification de la synchro du serveur
						return null;
					for (int i = 0; i < nbJoueurs; i++) {
						Joueur toSend = listJoueurs.get(i);
						Log.d("kiki", toSend.toString());
						connexion.getOutput().sendJoueur(toSend);
					}
					connexion.getOutput().reset();
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

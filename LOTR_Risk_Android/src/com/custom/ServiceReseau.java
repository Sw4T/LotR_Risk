package com.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import objects.Joueur;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.game.InterfaceLOTR;
import com.net.DonneesConnexion;
import com.net.TacheConnexion;
import com.net.TacheTransmission;

public class ServiceReseau extends Service implements InterfaceLOTR {

    private DonneesConnexion connexion;
    private final IBinder binder = new ReseauBinder();
    
    public boolean connexionServeur() 
    {
    	TacheConnexion t = (TacheConnexion) new TacheConnexion(getBaseContext()).execute("192.168.0.30", "9875");
		try {
			this.connexion = t.get();
		} catch (InterruptedException | ExecutionException e) {
			return false;
		}
		return (connexion.get_Connexion_Reussi() ? true : false);
    }

    public ArrayList<Joueur> envoyerTraitementServeur(ArrayList<Joueur> tabJoueur, Integer constanteLOTR)
    {
    	Integer [] paramsTache = new Integer[1];
    	paramsTache[0] = constanteLOTR; //Procédure envoi des joueurs au serveur
    	TacheTransmission transmission = new TacheTransmission(connexion, tabJoueur);
    	transmission.execute(paramsTache);
    	try {
    		return (transmission.get()); //Attente de la fin de tache et récupération de la liste	
    	} catch (InterruptedException | ExecutionException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    @Override
    public void onDestroy() {
        if (connexion != null)
			try {
				connexion.close();
			} catch (IOException e) {}
    }

    public class ReseauBinder extends Binder {
    	public ServiceReseau getService() {
    		return ServiceReseau.this;
    	}
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    public DonneesConnexion getConnexion() {
    	return this.connexion;
    }
}

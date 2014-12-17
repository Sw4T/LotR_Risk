package com.activities;

import java.util.ArrayList;

import objects.Joueur;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.custom.HelperCouleur;
import com.custom.Liste_Couleur;
import com.custom.ServiceReseau;
import com.custom.ServiceReseau.ReseauBinder;
import com.game.InterfaceLOTR;
import com.lotr_risk.R;

public class StartUpActivity extends Activity implements InterfaceLOTR {

	Dialog dialog;
	Button BT_Connexion, BT_Nb_Joueurs, BT_Envoi_Joueurs;
	EditText ET_addrServ, ET_numPort;
	Context context;
	ProgressDialog progressDialog;
	HelperCouleur helperCouleur;
	View [] tabLigneJoueur;
	ArrayList<Joueur> listJoueurs;
	ServiceReseau serviceReseau;
	boolean connexionActive = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_up);
		context = StartUpActivity.this;
		helperCouleur = new HelperCouleur(context);
		dialog = new Dialog(context);
		BT_Connexion = (Button) findViewById(R.id.BT_Connexion);
		ET_addrServ = (EditText) findViewById(R.id.ET_nomJoueur);
		ET_numPort = (EditText) findViewById(R.id.ET_portServeur);
		BT_Connexion.setOnClickListener(loggerListener);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(StartUpActivity.this, ServiceReseau.class);
		startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	private void connexion_Serveur() 
	{
		//String addresse = ET_addrServ.getText().toString();
		//String numPort = ET_numPort.getText().toString();
		/* VALEURS ARBITRAIRES D'ADRESSE POUR RAPIDITE DE TEST */
		if (!connexionActive) {
			Toast.makeText(context, "Service réseau non initialisé, veuillez relancer ou patienter...", Toast.LENGTH_SHORT).show();
			return;
		}
		if (serviceReseau.connexionServeur())
		{
			dialog.setTitle("Paramètres de jeu");
			dialog.setContentView(R.layout.layout_nombre_joueurs);
			BT_Nb_Joueurs = (Button) dialog.findViewById(R.id.BT_NB_Joueurs);
			BT_Nb_Joueurs.setOnClickListener(nb_JoueursListener);
			dialog.show();
		}
    } 
	
	private OnClickListener nb_JoueursListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			Integer nb_Joueurs;
			EditText entreeNb_Joueurs = (EditText) dialog.findViewById(R.id.ET_NB_Joueurs);
			nb_Joueurs = Integer.parseInt(entreeNb_Joueurs.getText().toString());
			if (nb_Joueurs < 2 || nb_Joueurs > 4)
				Toast.makeText(context, "Veuillez entrer un nombre entre 2 et 4", Toast.LENGTH_SHORT).show();
			else
				remplir_entree_joueurs(nb_Joueurs.intValue());
		}
	};
	
	@SuppressLint("InflateParams")
	private void remplir_entree_joueurs(int nbJoueurs) {
		//Initialisation des paramètres et des variables lié au layout principal 
		LinearLayout layoutPrincipal = new LinearLayout(context);
		layoutPrincipal.setOrientation(LinearLayout.VERTICAL);
		Liste_Couleur couleurAdapter = new Liste_Couleur(context, android.R.layout.simple_spinner_item, helperCouleur.getListCouleur());
		dialog.setTitle("Paramètres des joueurs");
		dialog.setContentView(layoutPrincipal);	
		
		//Création et enregistrement des lignes de saisies joueurs
		tabLigneJoueur = new View[nbJoueurs]; //Initialisation du tableau enregistrant les saisies joueur
		LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < nbJoueurs; i++) {
			View ligneJoueur = inflater.inflate(R.layout.ligne_entree_joueur, null); //FIX THIS TODO
			tabLigneJoueur[i] = ligneJoueur;
			Spinner spin = (Spinner) ligneJoueur.findViewById(R.id.listCouleurs);
			spin.setAdapter(couleurAdapter); //Remplissage de la liste avec les valeurs des couleurs
			layoutPrincipal.addView(ligneJoueur);
		}
		
		//Création du bouton d'envoi, ajout du listener et affichage
		Button BT_Envoi = new Button(context);
		BT_Envoi.setText("Envoyer");
		BT_Envoi.setOnClickListener(envoiJoueursListener);
		layoutPrincipal.addView(BT_Envoi);
		dialog.show();	
	}
	
	private OnClickListener envoiJoueursListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			dialog.dismiss();
			listJoueurs = new ArrayList<Joueur>(tabLigneJoueur.length);
			String selectionCouleur;
			EditText ET_nomJoueur;
			Spinner listeCouleurs;
			for (int i = 0; i < tabLigneJoueur.length; i++) {
				ET_nomJoueur = (EditText) tabLigneJoueur[i].findViewById(R.id.ET_nomJoueur);
				listeCouleurs = (Spinner) tabLigneJoueur[i].findViewById(R.id.listCouleurs);
				selectionCouleur = helperCouleur.getRGBFromColorName(listeCouleurs.getSelectedItem().toString());
				listJoueurs.add(new Joueur(ET_nomJoueur.getText().toString(), selectionCouleur));
			}
			envoi_Joueurs_Serveur(); //Envoi de la liste construite au serveur	
		}		
	};
	
	private void envoi_Joueurs_Serveur() 
	{
		setProgressDialog("Création des joueurs", "Envoi au serveur...", false);
		listJoueurs = serviceReseau.envoyerTraitementServeur(listJoueurs, CREATION_JOUEURS);
		if (listJoueurs == null) 
			Toast.makeText(context, "Erreur lors de la réception des joueurs", Toast.LENGTH_SHORT).show();
		else 
			reception_Joueurs_Cree();
	}
	
	private void reception_Joueurs_Cree()
	{
		setProgressDialog("Création des joueurs", "Réception des joueurs crées...", false);
		listJoueurs = serviceReseau.envoyerTraitementServeur(listJoueurs, SERVEUR_ENVOI_JOUEURS);
		if (listJoueurs == null) 
			Toast.makeText(context, "Erreur lors de la réception des joueurs", Toast.LENGTH_SHORT).show();
		else {
			Intent intent = new Intent(this, InitGameActivity.class);
			intent.putExtra("listJoueurs", listJoueurs);
			startActivity(intent);
		}
		setProgressDialog("", "", true);
	}
	
	
	private OnClickListener loggerListener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			connexion_Serveur();	
		}		
	};
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
        	ReseauBinder binder = (ReseauBinder) service;
            serviceReseau = binder.getService();
            if (serviceReseau != null)
            	connexionActive = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            connexionActive = false;
        }
    };
    
	public void setProgressDialog(final String titre, final String message, boolean close)
	{
		if (progressDialog == null)
			progressDialog = new ProgressDialog(context);
		else if (close && progressDialog.isShowing()) {
			progressDialog.dismiss();
			return;
		}
	    runOnUiThread(new Runnable()
	    {
	    	@Override
			public void run()
	        {
	    		if (!isFinishing())
	    		{
	    			progressDialog.setTitle(titre);
	    			progressDialog.setMessage(message);
	    			progressDialog.show();
	    		}
	        }
	    });   
	}
}

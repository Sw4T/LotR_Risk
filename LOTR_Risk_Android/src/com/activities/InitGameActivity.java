package com.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import objects.Joueur;
import objects.Territoire;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.Compteur;
import com.custom.ServiceReseau;
import com.custom.ServiceReseau.ReseauBinder;
import com.game.InterfaceLOTR;
import com.lotr_risk.R;

@SuppressLint("NewApi")
public class InitGameActivity extends Activity implements InterfaceLOTR {

	private ArrayList<Joueur> listJoueurs;
	private Context context;
	private LinearLayout layoutPrincipal;
	private HashMap<Territoire, Compteur> [] tabTerritoireCompteur;
	private Button BT_Valider;
	boolean connexionActive = false;
	ServiceReseau serviceReseau;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = InitGameActivity.this;
		Bundle params = getIntent().getExtras();
		if (params != null) {
			listJoueurs = (ArrayList<Joueur>) params.getSerializable("listJoueurs"); //FIX TODO
			if (listJoueurs.contains(new Joueur("Neutre", "#ff000000")))
				tabTerritoireCompteur = new HashMap[listJoueurs.size() - 1];
			else
				tabTerritoireCompteur = new HashMap[listJoueurs.size()];
			for (int i = 0; i < tabTerritoireCompteur.length; i++) { //Initialisation des structures de données pour la sauvegarde
				HashMap<Territoire, Compteur> mapTerritoireCompteur = new HashMap<Territoire, Compteur>();
				tabTerritoireCompteur[i] = mapTerritoireCompteur;
			}
			afficherJoueursTerritoires();
		} else
			Toast.makeText(context, "ERREUR : l'application n'a pas pu récupérer la liste des joueurs", Toast.LENGTH_SHORT).show();
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(this, ServiceReseau.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@SuppressLint("InflateParams")
	public void afficherJoueursTerritoires() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		View vuePrincipal = inflater.inflate(R.layout.activity_init_game, null); //FIX plz
		layoutPrincipal = (LinearLayout) vuePrincipal.findViewById(R.id.layout_init_game);
		
		/** Création du bouton d'envoi et association au listener **/
		BT_Valider = new Button(context);
		BT_Valider.setText("Envoyer");
		BT_Valider.setTextSize(30F);
		BT_Valider.setOnClickListener(validerListener);
		
		for (int i  = 0; i < listJoueurs.size(); i++) 
		{
			/** Affichage du nom de joueur et définition du style du texte **/
			Joueur joueurCourant = listJoueurs.get(i);
			if (joueurCourant.getNom().equals("Neutre"))
				break;
			TextView nomJoueur = new TextView(context);
			nomJoueur.setGravity(Gravity.CENTER);
			nomJoueur.setText(joueurCourant.getNom());
			nomJoueur.setTextSize(25F);
			nomJoueur.setTypeface(null, Typeface.BOLD_ITALIC);
			nomJoueur.setBackgroundColor(Color.parseColor(joueurCourant.getCouleurRGB()));
			layoutPrincipal.addView(nomJoueur);
			
			/** Pour chaque territoire possédé par le joueur **/
			for (Territoire t : joueurCourant.getListTerritoire()) 
			{
				/** Définition du layout qui affichera le nom du territoire et le compteur **/
				LinearLayout ligneTerritoire = new LinearLayout(context);
				ligneTerritoire.setOrientation(LinearLayout.HORIZONTAL);
				ligneTerritoire.setGravity(Gravity.CENTER);
				ligneTerritoire.setBackground(getDrawable(R.drawable.border));
				
				TextView territoireJoueur = new TextView(context);
				territoireJoueur.setText(t.getNom());
				territoireJoueur.setGravity(Gravity.CENTER);
				territoireJoueur.setTextSize(17F);
				
				/** Création d'un compteur et sauvegarde de l'objet **/
				View vueCompteur = inflater.inflate(R.layout.layout_compteur, null);
				Compteur compteur = new Compteur(context, vueCompteur);
				tabTerritoireCompteur[i].put(t, compteur); //Stockage du territoire associé à son compteur
				
				ligneTerritoire.addView(territoireJoueur);
				ligneTerritoire.addView(vueCompteur);
				layoutPrincipal.addView(ligneTerritoire);
			}
		}
		layoutPrincipal.addView(BT_Valider);
		setContentView(vuePrincipal); //Affichage de la vue initialisée
	}	
	
	private void envoiJoueursInitialisation() 
	{
		for (int i = 0; i < tabTerritoireCompteur.length; i++)
		{
			//Joueur joueurCourant = listJoueurs.get(i);
			HashMap<Territoire, Compteur> mapTerritoireCompteur  = tabTerritoireCompteur[i];
			for (Entry<Territoire, Compteur> entry : mapTerritoireCompteur.entrySet()) {
				Territoire t = entry.getKey();
				Compteur cpt = entry.getValue();
				t.setNB_Unite(cpt.getNombreCompteur().intValue());
				//joueurCourant.update_Territoire(t);
				//listJoueurs.set(i, joueurCourant);
			}
		}
		if (connexionActive) {
			listJoueurs = serviceReseau.envoyerTraitementServeur(listJoueurs, SERVEUR_RECEPTION_JOUEURS);
			if (listJoueurs == null) 
				Toast.makeText(context, "Erreur lors de l'envoi des joueurs", Toast.LENGTH_SHORT).show();
			else 
				Toast.makeText(context, "Joueur bien envoyé", Toast.LENGTH_SHORT).show();	
		}
	}
	
	private OnClickListener validerListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			envoiJoueursInitialisation();
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
}

package com.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import objects.Joueur;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import com.game.InterfaceLOTR;
import com.lotr_risk.R;
import com.net.DonneesConnexion;
import com.net.TacheConnexion;
import com.net.TacheTransmission;

public class StartUpActivity extends Activity implements InterfaceLOTR {

	Dialog dialog;
	Button BT_Connexion, BT_Nb_Joueurs, BT_Envoi_Joueurs;
	EditText ET_addrServ, ET_numPort;
	Context context;
	HelperCouleur helperCouleur;
	View [] tabLigneJoueur;
	private DonneesConnexion connexion;
	//private LOTR_Game donneesJeu;
	
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
	
	private void connexion_Serveur() 
	{
		//String addresse = ET_addrServ.getText().toString();
		//String numPort = ET_numPort.getText().toString();
		/* TEST AVEC DES VALEURS ARBITRAIRES -> LES MIENNES !::!!:D */
		TacheConnexion t = (TacheConnexion) new TacheConnexion(getBaseContext()).execute("192.168.0.30", "9876");
		try {
			this.connexion = t.get();
			if (this.connexion.get_Connexion_Reussi()) 
			{
				dialog.setTitle("Paramètres de jeu");
				dialog.setContentView(R.layout.dialog_nombre_joueurs);
				BT_Nb_Joueurs = (Button) dialog.findViewById(R.id.BT_NB_Joueurs);
				BT_Nb_Joueurs.setOnClickListener(nb_JoueursListener);
				dialog.show();
			}
		} catch (InterruptedException e) {e.printStackTrace();
		} catch (ExecutionException e) {e.printStackTrace();}
		dialog.setTitle("Paramètres de jeu");
		dialog.setContentView(R.layout.dialog_nombre_joueurs);
		BT_Nb_Joueurs = (Button) dialog.findViewById(R.id.BT_NB_Joueurs);
		BT_Nb_Joueurs.setOnClickListener(nb_JoueursListener);
		dialog.show();
	}
	
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
	
	private void envoi_Joueurs_Serveur(ArrayList<Joueur> listJoueurs) 
	{
		Integer [] paramsTache = new Integer[2];
		paramsTache[0] = ENVOI_JOUEURS; //Procédure envoi des joueurs au serveur
		paramsTache[1] = listJoueurs.size(); //Nombre de joueurs à envoyer
		TacheTransmission transmission = new TacheTransmission(context, connexion, listJoueurs);
		transmission.execute(paramsTache);
		try {
			transmission.get(); //Attente de la fin de tache (INUTILE VBTWWW)
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private OnClickListener nb_JoueursListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			Integer nb_Joueurs;
			EditText entreeNb_Joueurs = (EditText) dialog.findViewById(R.id.ET_NB_Joueurs);
			nb_Joueurs = Integer.parseInt(entreeNb_Joueurs.getText().toString());
			if ((int) nb_Joueurs < 1 || nb_Joueurs > 4)
				Toast.makeText(context, "Veuillez entrer un nombre entre 1 et 4", Toast.LENGTH_SHORT).show();
			else
				remplir_entree_joueurs(nb_Joueurs.intValue());
		}
	};
	
	private OnClickListener envoiJoueursListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			dialog.dismiss();
			ArrayList<Joueur> listJoueurs = new ArrayList<Joueur>(tabLigneJoueur.length);
			String selectionCouleur;
			EditText ET_nomJoueur;
			Spinner listeCouleurs;
			for (int i = 0; i < tabLigneJoueur.length; i++) {
				ET_nomJoueur = (EditText) tabLigneJoueur[i].findViewById(R.id.ET_nomJoueur);
				listeCouleurs = (Spinner) tabLigneJoueur[i].findViewById(R.id.listCouleurs);
				selectionCouleur = listeCouleurs.getSelectedItem().toString();
				listJoueurs.add(new Joueur(ET_nomJoueur.getText().toString(), selectionCouleur));
			}
			envoi_Joueurs_Serveur(listJoueurs); //Envoi de la liste construite au serveur	
		}		
	};
	
	private OnClickListener loggerListener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			connexion_Serveur();	
		}		
	};
}

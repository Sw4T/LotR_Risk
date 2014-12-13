package com.activities;

import java.util.ArrayList;
import objects.Joueur;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lotr_risk.R;


public class InitGameActivity extends Activity {

	private ArrayList<Joueur> listJoueurs;
	private Context context;
	private LinearLayout layoutPrincipal;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = InitGameActivity.this;
		Bundle params = getIntent().getExtras();
		if (params != null) {
			listJoueurs = (ArrayList<Joueur>) params.getSerializable("listJoueurs"); //FIX TODO
			Log.d("nbJoueursRecu", String.valueOf(listJoueurs.size()));
		}
		afficherJoueur();
	}
	
	@SuppressLint("InflateParams")
	public void afficherJoueur() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		View vuePrincipal = inflater.inflate(R.layout.activity_init_game, null); //FIX
		layoutPrincipal = (LinearLayout) vuePrincipal.findViewById(R.id.layout_init_game);
		for (int i  = 0; i < listJoueurs.size(); i++) 
		{
			Joueur joueurCourant = listJoueurs.get(i);
			TextView nomJoueur = new TextView(context);
			nomJoueur.setText(joueurCourant.getNom());
			
			TextView territoiresJoueur = new TextView(context);
			StringBuffer chaineTerritoire = new StringBuffer();
			for (int j = 0; j < joueurCourant.getListTerritoire().size(); j++) 
				chaineTerritoire.append("\t\t" + joueurCourant.getTerritoire_FromIndex(j) + "\n");
			territoiresJoueur.setText(chaineTerritoire);
			
			layoutPrincipal.addView(nomJoueur);
			layoutPrincipal.addView(territoiresJoueur);
		}
		setContentView(layoutPrincipal);
	}	
}

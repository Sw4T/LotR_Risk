package com.custom;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.lotr_risk.R;

public class Compteur {

	private Button BT_Plus, BT_Moins;
	private EditText compteur;
	private View vue;
	private int nbCompteur = 0;
	
	public Compteur(Context context, View vueCompteur) {
		this.vue = vueCompteur;
		inflateElements();
	}
	
	public void inflateElements() {
		BT_Plus = (Button) vue.findViewById(R.id.BT_Plus);
		BT_Moins = (Button) vue.findViewById(R.id.BT_Moins);
		compteur = (EditText) vue.findViewById(R.id.compteur);
		compteur.setClickable(false);
		BT_Plus.setOnClickListener(BT_Plus_Listener);
		BT_Moins.setOnClickListener(BT_Moins_Listener);
	}
	
	public Integer getNombreCompteur() {
		String valeurCompteur = compteur.getText().toString();
		if (valeurCompteur.equals(""))
			return Integer.valueOf(0);
		else
			return (Integer.parseInt(valeurCompteur));
	}

	private OnClickListener BT_Plus_Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			nbCompteur++;
			compteur.setText(String.valueOf(nbCompteur));
		}
	};
	
	private OnClickListener BT_Moins_Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (nbCompteur > 0)
				nbCompteur--;
			compteur.setText(String.valueOf(nbCompteur));
		}
	};

}

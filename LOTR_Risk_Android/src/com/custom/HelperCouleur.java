package com.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;

import com.lotr_risk.R;

public class HelperCouleur {
	
	private HashMap<String, String> mapCouleurs;
	private Context context;
	
	public HelperCouleur(Context context) {
		this.mapCouleurs = new HashMap<String, String>();
		mapCouleurs.put("VERT", context.getString(R.color.VERT));
		mapCouleurs.put("JAUNE", context.getString(R.color.JAUNE));
		mapCouleurs.put("NOIR", context.getString(R.color.NOIR));
		mapCouleurs.put("ROUGE", context.getString(R.color.ROUGE));
	}
	
	public List<String> getListCouleurRGB() {
		List<String> listCouleur = new ArrayList<String>();
		String [] tabCouleur = context.getResources().getStringArray(R.array.colors);
		for (String couleur : tabCouleur) {
			listCouleur.add(couleur);
		}
		return listCouleur;
	}
	
	public ArrayList<String> getListCouleur() {
		ArrayList<String> toReturn = new ArrayList<String>(this.mapCouleurs.size());
		for (Entry<String, String> setCouleur : this.mapCouleurs.entrySet()) {
			toReturn.add(setCouleur.getKey());
		}
		return toReturn;
		
	}
	
	public String getColorNameFromRGB(String colorRGB) {
		for (Entry<String, String> setCouleur : this.mapCouleurs.entrySet()) {
			if (setCouleur.getValue().equals(colorRGB))
				return setCouleur.getKey();
		}
		return null;
	}
	
	public String getRGBFromColorName(String color) {
		for (Entry<String, String> setCouleur : this.mapCouleurs.entrySet()) {
			if (setCouleur.getKey().equals(color))
				return setCouleur.getValue();
		}
		return null;
	}
	
}

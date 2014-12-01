package com.custom;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lotr_risk.R;

public class Liste_Couleur extends ArrayAdapter<String> {

	private List<String> listCouleur;
	private HelperCouleur helper;
	private Context context;
	
	public Liste_Couleur(Context context, int resource, List<String> objects) {
		super(context, resource, objects);
		this.listCouleur = objects;
		this.context = context;
		this.helper = new HelperCouleur(context);
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	row = inflater.inflate(R.layout.text_couleur_adapter, parent, false);
        	TextView textCouleur = (TextView) row.findViewById(R.id.textCouleur);
        	int couleur = Color.parseColor(this.helper.getRGBFromColorName(this.listCouleur.get(position)));
        	textCouleur.setBackgroundColor(couleur);
        }
		return row; 
	}
	
	
}

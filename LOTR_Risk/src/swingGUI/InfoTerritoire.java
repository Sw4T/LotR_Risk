package swingGUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Territoire;

public class InfoTerritoire extends Box{
	private ArrayList<Territoire> listeTerritoire;

	public InfoTerritoire(ArrayList<Territoire> listeTerritoire) {
		super(BoxLayout.PAGE_AXIS);
		this.listeTerritoire = listeTerritoire;
		
		for (Territoire territoire : listeTerritoire) {
			JLabel nomJoueurJLabel = new JLabel("  " + territoire.getNom());
			this.add(nomJoueurJLabel);
		}
		
		
	}
	
	
}

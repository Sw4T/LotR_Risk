package swingGUI;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import objects.Joueur;
import objects.Territoire;
import utils.LOTR_Data;

public class InfoJoueur extends Box {
	private ArrayList<Joueur> joueurData;
	private LOTR_Data data;
	private ChartPanel chartPanel;
 	
	public InfoJoueur() {
		this(null);
	}
	
	public InfoJoueur(ArrayList<Joueur> joueurData) {

		
		super(BoxLayout.PAGE_AXIS);
		
		this.setSize(20, 20);
		
		this.joueurData = joueurData;
		this.data = new LOTR_Data();
		
		for (Joueur joueur : joueurData) {
			JLabel nomJoueurJLabel = new JLabel(joueur.getNom());
			nomJoueurJLabel.setForeground(Color.decode(joueur.getCouleurRGB()));
			this.add(nomJoueurJLabel);
			
			JLabel nombreTotalUnitesJLabel = new JLabel("    Nombre total d'unités : " + String.valueOf(joueur.getNb_unites()));
			nombreTotalUnitesJLabel.setForeground(Color.decode(joueur.getCouleurRGB()));
			this.add(nombreTotalUnitesJLabel);
			
			JLabel nombreUnitesParTourJLabel = new JLabel("    Renforts/Tour : " + String.valueOf(data.calculer_Renforts(joueur)));
			nombreUnitesParTourJLabel.setForeground(Color.decode(joueur.getCouleurRGB()));
			this.add(nombreUnitesParTourJLabel);
			
			//this.add(new InfoTerritoire(joueur.getListTerritoire()));
		}
		
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("A", new Integer(75));
		pieDataset.setValue("B", new Integer(10));
		pieDataset.setValue("C", new Integer(10));
		pieDataset.setValue("D", new Integer(5));
		JFreeChart chart = ChartFactory.createPieChart
				("CSC408 Mark Distribution",    // Title
						pieDataset, 			// Dataset
						true,					// Show legend
						true,					// Use tooltips
						false					// Configure chart to generate URLs?
						);
		
		
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\chart.jpg"), chart, 500, 300);
		} catch (Exception e) {
			System.out.println("Problem occurred creating chart.");
		}
		
		
		
		this.chartPanel = new ChartPanel(chart);
		
		this.chartPanel.setVisible(true);
		
		this.add(this.chartPanel);

	}
	
	

}

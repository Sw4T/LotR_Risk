package swingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import objects.Joueur;

public class MainWindow extends JFrame {
	private ArrayList<Joueur> listeJoueur;
	
	public MainWindow(ArrayList<Joueur> listeJoueur) {
		super();
		this.listeJoueur = listeJoueur;
		
		this.setSize(400,400);                                                     
		this.setTitle("LotR_Risk");                           
		this.setLayout(new BorderLayout());                                                                        
		                                                      
		//Box listJoueur = Box.createVerticalBox();           
		
		
		InfoJoueur infoJoueurBox = new InfoJoueur(listeJoueur);
		
		
		Box log = Box.createHorizontalBox();
		
		
		JLabel str = new JLabel("Attente connexion...");
		log.add(str);
		
		ImageIcon img = new ImageIcon("map.jpg");
		
		Map mapPannel = new Map(img);
		
		
		this.add(infoJoueurBox, BorderLayout.WEST);
		this.add(mapPannel, BorderLayout.CENTER);
		this.add(log, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	@Override
	public void repaint() {
		
		super.repaint();
	}
	
	
	
}



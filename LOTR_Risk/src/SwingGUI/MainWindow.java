package SwingGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow extends JFrame {
	
	public MainWindow() {
		setTitle("LotR_Risk alpha0.1");
		setLayout(new BorderLayout());
		
		Dimension minSize = new Dimension(600,600);
		setMinimumSize(minSize);
		
		Box listJoueur = Box.createVerticalBox();
		Box log = Box.createHorizontalBox();
		
		
		JLabel joueurName = new JLabel("Seikomi");
		JLabel joueurName2 = new JLabel("Swat");
		JLabel joueurName3 = new JLabel("Marmotte");
		
		listJoueur.add(joueurName);
		listJoueur.add(joueurName2);
		listJoueur.add(joueurName3);
		
		JLabel str = new JLabel("Attente connexion...");
		log.add(str);
		
		ImageIcon img = new ImageIcon("map-resize.jpg");
		
		Map map = new Map(img);
		
		add(listJoueur, BorderLayout.WEST);
		add(map, BorderLayout.CENTER);
		add(log, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		MainWindow win = new MainWindow();
	}
	
}



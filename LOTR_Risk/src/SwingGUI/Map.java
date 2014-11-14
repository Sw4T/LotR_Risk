package SwingGUI;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class Map extends JPanel {
	private final ImageIcon mapImage;
	
	public Map(ImageIcon mapImage) {
		this.mapImage = mapImage;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(mapImage.getImage(), 0, 0, null);
	}
}
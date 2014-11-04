package objects;

public class Territoire {

	private String nom;
	private int nb_unite;
	private boolean has_Hero;
	private boolean has_Stronghold;
	
	public Territoire(String name, boolean has_Stronghold)
	{
		this.nom = name;
		this.has_Stronghold = has_Stronghold;
	}
	
	public String getNom() {
		return nom;
	}

	public int getNb_unite() {
		return nb_unite;
	}

	public void setNb_Unite(int nb_unite) {
		this.nb_unite = nb_unite;
	}

	public boolean isHas_Hero() {
		return has_Hero;
	}

	public void setHas_Hero(boolean has_Hero) {
		this.has_Hero = has_Hero;
	}

	public boolean has_Stronghold() {
		return has_Stronghold;
	}
}

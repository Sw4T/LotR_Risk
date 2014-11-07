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
		this.nb_unite = 0;
	}
	
	public void add_Units(int nbUnitToAdd) {
		this.nb_unite += nbUnitToAdd;
	}
	
	public void remove_Units(int nbUnitToRemove) {
		if (this.nb_unite - nbUnitToRemove > 0)
			this.nb_unite -= nbUnitToRemove;
	}
	
	public String getNom() {
		return nom;
	}

	public int getNb_Unite() {
		return nb_unite;
	}

	public void setNb_Unite(int nb_unite) {
		this.nb_unite = nb_unite;
	}

	public boolean has_Hero() {
		return has_Hero;
	}

	public void setHas_Hero(boolean has_Hero) {
		this.has_Hero = has_Hero;
	}

	public boolean has_Stronghold() {
		return has_Stronghold;
	}
}

package objects;

import java.io.Serializable;

public class Territoire implements Serializable {

	private static final long serialVersionUID = -7228052548724269283L;
	private String nom;
	private TypeTerritoire type_T;
	private int nb_unite;
	private boolean has_Hero;
	private boolean has_Stronghold;
	
	public Territoire(String name, boolean has_Stronghold, TypeTerritoire type)
	{
		this.nom = name;
		this.has_Stronghold = has_Stronghold;
		this.nb_unite = 0;
		this.type_T = type;
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

	public TypeTerritoire getType_Territoire() {
		return type_T;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof Territoire))
			return false;
		Territoire t = (Territoire) o;
		if (this.nom.equals(t.getNom()))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return this.nom;
	}
}

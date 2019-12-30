import java.util.ArrayList;

public class Corsaire extends Personnage {

	private String nom;
	private ArrayList<Lootable> inventaire;
	
	Corsaire(String nom) {
		this.setNom(nom);
		this.inventaire = new ArrayList<Lootable>();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ArrayList<Lootable> getInventaire() {
		return inventaire;
	}

}

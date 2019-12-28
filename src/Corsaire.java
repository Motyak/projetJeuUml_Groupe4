
public class Corsaire extends Personnage {

	private String nom;
//	ne pas oublier l'inventaire
	
	Corsaire(String nom) {
		this.setNom(nom);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}

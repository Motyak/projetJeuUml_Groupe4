import java.util.ArrayList;

public class Corsaire extends Personnage {

	private String nom;
	private ArrayList<Lootable> inventaire;
	private double probWinningFight;
	
	Corsaire(String nom) {
		this.setNom(nom);
		this.inventaire = new ArrayList<Lootable>();
		this.setProbWinningFight(0.0);
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

	public double getProbWinningFight() {
		return probWinningFight;
	}

	public void setProbWinningFight(double probWinningFight) {
		this.probWinningFight = probWinningFight;
	}

}

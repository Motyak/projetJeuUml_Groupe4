import java.util.ArrayList;

/**
 * @author Tom
 *
 */
public class Corsaire extends Personnage {

	private String nom;
	private ArrayList<Lootable> inventaire;
	private double probWinningFight;
	
	public Corsaire(String nom) {
		this.setNom(nom);
		this.inventaire = new ArrayList<Lootable>();
		this.probWinningFight=0.00;
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

	public double getProb() {
		return probWinningFight;
	}

	/**
	 * Adds probability amount to kill another pirate when facing him
	 * @param prob Probability amount to add
	 */
	public void addToProb(double prob) {
		this.probWinningFight = this.probWinningFight+prob;
		if(this.probWinningFight>1.00)
			this.probWinningFight=1.00;
	}

}

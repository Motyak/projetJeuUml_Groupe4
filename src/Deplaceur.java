import java.util.ArrayList;

public class Deplaceur {
	
	private Plateau plateau;
	
	Deplaceur(Plateau p)
	{
		this.plateau=p;
	}
	
	public void deplacer(Personnage p,Pair<Integer,Integer> direction)
	{
////		retirer le perso de la case ou il se trouve
//		this.plateau.getCases().get(Plateau.coordsToIndex(p.getCoords(), this.plateau.getDimension())).getPersos().remove(p);
////		calcul nouvelles coords
//		Pair<Integer,Integer> newCoords = new Pair<Integer,Integer>(p.getCoords().key+direction.key,p.getCoords().value+direction.value);
////		ajouter le perso dans la case cible
//		this.plateau.getCases().get(Plateau.coordsToIndex(newCoords, this.plateau.getDimension())).getPersos().add(p);
	}
	
	public void deplacerAleat(Personnage p, ArrayList<Pair<Integer,Integer>> dirPossibles, int nbMaxDeplacements)
	{
		
	}
}

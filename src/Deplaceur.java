import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Deplaceur {
	
	private Plateau plateau;
	
	Deplaceur(Plateau p)
	{
		this.plateau=p;
	}
	
	public void deplacer(Personnage p,Pair<Integer,Integer> direction)
	{
		int dimension = this.plateau.getDimension();
		int indexDepart = Plateau.coordsToIndex(p.getCoords(), dimension);
		Pair<Integer,Integer> coordsArrivee = new Pair<Integer,Integer>(p.getCoords().key+direction.key,p.getCoords().value+direction.value);
		int indexArrivee = Plateau.coordsToIndex(coordsArrivee, dimension);
		
//		retirer le perso de la case ou il se trouve
		if(this.plateau.getCases().get(indexDepart).getPersos().contains(p))
			this.plateau.getCases().get(indexDepart).getPersos().remove(p);
		
//		ajouter le perso dans la case cible
		this.plateau.getCases().get(indexArrivee).getPersos().add(p);
		
//		mettre a jour ses coordonnées
		p.setCoords(coordsArrivee);
	}
	
	public void deplacerAleat(Personnage p, ArrayList<Pair<Integer,Integer>> dirPossibles, int nbMaxDeplacements)
	{
//		ca va utiliser deplacer (deplacement manuel)
		
		
	}
	
	public static void main(String args[])
	{
		Plateau plateau;
		List<Corsaire> corsaires = Arrays.asList(new Corsaire("_J1_"),new Corsaire("_J2_"));
		List<Pirate> pirates = Arrays.asList(new Boucanier(),new Flibustier());
		
		try {
			plateau = new Plateau(corsaires,pirates,5);
			Deplaceur d = new Deplaceur(plateau);
			plateau.afficher();
			
			int a;
			Scanner sc = new Scanner(System.in);
			a = sc.nextInt();
			sc.close();
			Personnage persoADeplacer = plateau.getCases().get(a).getPersos().get(0);
			
			d.deplacer(persoADeplacer, Direction.HAUT);
			d.deplacer(persoADeplacer, Direction.GAUCHE);
			d.deplacer(persoADeplacer, Direction.DROITE);
			d.deplacer(persoADeplacer, Direction.BAS);
			
			plateau.afficher();		

		} catch (PlateauException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

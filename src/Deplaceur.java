import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Deplaceur {
	
	private Plateau plateau;
	
	Deplaceur(Plateau p)
	{
		this.plateau=p;
	}
	
	public boolean deplacer(Personnage p,Pair<Integer,Integer> direction)
	{
		int dimension = this.plateau.getDimension();
		int indexDepart = Plateau.coordsToIndex(p.getCoords(), dimension);
		Pair<Integer,Integer> coordsArrivee = new Pair<Integer,Integer>(p.getCoords().key+direction.key,p.getCoords().value+direction.value);
//		si coords en dehors du plateau => return false
		if(coordsArrivee.key<0 || coordsArrivee.key>dimension-1 || coordsArrivee.value<0 || coordsArrivee.value>dimension-1)
			return false;
		int indexArrivee = Plateau.coordsToIndex(coordsArrivee, dimension);
		
//		retirer le perso de la case ou il se trouve
		if(this.plateau.getCases().get(indexDepart).getPersos().contains(p))
			this.plateau.getCases().get(indexDepart).getPersos().remove(p);
		
//		ajouter le perso dans la case cible
		this.plateau.getCases().get(indexArrivee).getPersos().add(p);
		
//		mettre a jour ses coordonnées
		p.setCoords(coordsArrivee);
		
		return true;
	}
	
	public void deplacerAleat(Personnage p, List<Pair<Integer,Integer>> dirPossibles, int nbMaxDeplacements)
	{
		Random r = new Random();
		
//		generer nombre de deplacements
		int nbDeplacements = r.nextInt(nbMaxDeplacements)+1;	//genere un nb entre 1 (compris) et nbMaxDeplacements (compris)
		System.out.println(nbDeplacements);
		
		for(int i = 1;i<=nbDeplacements;++i)
		{
			boolean impossible=true;
			while(impossible)
			{
				Pair<Integer,Integer> direction = dirPossibles.get(r.nextInt(dirPossibles.size()));
				System.out.println(direction.key+";"+direction.value);
				impossible = !deplacer(p, direction);
			}
		}
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
			
//			d.deplacer(persoADeplacer, Direction.HAUT);
//			d.deplacer(persoADeplacer, Direction.GAUCHE);
//			d.deplacer(persoADeplacer, Direction.BAS_DROITE);
			
			d.deplacerAleat(persoADeplacer, Direction.LIGNES_DROITES_ET_DIAG, 3);
			
			plateau.afficher();		

		} catch (PlateauException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

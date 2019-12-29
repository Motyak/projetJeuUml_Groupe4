import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Partie {
	private Plateau plateau;
	private Deplaceur deplaceur;
	
	Partie(Plateau p)
	{
		this.plateau=p;
		this.deplaceur = new Deplaceur(this.plateau);
	}
	
	public Plateau getPlateau()
	{
		return this.plateau;
	}
	
	public Deplaceur getDeplaceur()
	{
		return this.deplaceur;
	}
	
	public static void main(String args[])
	{
		Plateau plateau;
		List<Corsaire> corsaires = Arrays.asList(new Corsaire("_J1_"),new Corsaire("_J2_"));
		List<Pirate> pirates = Arrays.asList(new Boucanier(),new Flibustier());
		
		try {
			plateau = new Plateau(corsaires,pirates,5);
			Partie p = new Partie(plateau);
			p.getPlateau().afficher();
////			demande 4 inputs pour coords du perso a deplacer et direction du deplacement
//			int a,b,c,d;
//			Scanner sc = new Scanner(System.in);
//			a = sc.nextInt();
//			b = sc.nextInt();
//			c = sc.nextInt();
//			d = sc.nextInt();
//			sc.close();
////			on le deplace
//			p.getDeplaceur().deplacer(p.getPlateau().getCases().get(Plateau.coordsToIndex(new Pair<Integer,Integer>(a,b), p.getPlateau().getDimension())).getPersos().get(0), new Pair<Integer,Integer>(c,d));
////			on affiche le plateau apres effet
//			p.getPlateau().afficher();

		} catch (PlateauException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}

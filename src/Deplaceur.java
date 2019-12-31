import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Deplaceur {
	
	private Plateau plateau;
	
	public static final double MOUSQUET_BONUS = 0.90;
	public static final double MACHETTE_BONUS = 0.40;
	public static final double ARMURE_BONUS = 0.10;
	
	Deplaceur(Plateau p)
	{
		this.plateau=p;
	}
	
	public boolean deplacer(Personnage p,Pair<Integer,Integer> direction) throws InterruptedException
	{
		int dimension = this.plateau.getDimension();
		int indexDepart = Plateau.coordsToIndex(p.getCoords(), dimension);
		Pair<Integer,Integer> coordsArrivee = new Pair<Integer,Integer>(p.getCoords().key+direction.key,p.getCoords().value+direction.value);
		int indexArrivee = Plateau.coordsToIndex(coordsArrivee, dimension);
		
//		si coords en dehors du plateau
		if(indexArrivee==-1)
			return false;
		
//		si case destination = case EAU => return false
		if(this.plateau.getCases().get(indexArrivee).getType()==TypeCase.EAU)
			return false;
		
//		si c'est un corsaire et que destination = case foret et qu'il n'a pas de machette => return false
		if(p instanceof Corsaire && this.plateau.getCases().get(indexArrivee).getType()==TypeCase.FORET && !((Corsaire)p).getInventaire().contains(Objet.MACHETTE))
			return false;
		
//		retirer le perso de la case ou il se trouve
		if(this.plateau.getCases().get(indexDepart).getPersos().contains(p))
			this.plateau.getCases().get(indexDepart).getPersos().remove(p);
		
//		ajouter le perso dans la case cible
		this.plateau.getCases().get(indexArrivee).getPersos().add(p);
		
//		mettre a jour ses coordonnées
		p.setCoords(coordsArrivee);
		
//		afficher map post deplacement pre ramassage (si c'est un corsaire)
		if(p instanceof Corsaire)
		{
			this.plateau.afficher(p);
			AnsiTerminal.sleep();
		}
		
//		si c'est un corsaire et qu'il y a un lootable sur case destination..
		Lootable lootCase = this.plateau.getCases().get(indexArrivee).getLoot();
		if(p instanceof Corsaire && lootCase != null && !((Corsaire) p).getInventaire().contains(lootCase))
		{
//			si c'est pas un trésor ou qu'il possede une pelle => l'ajoute à l'inventaire
			if(lootCase!=ConditionVictoire.TRESOR || ((Corsaire) p).getInventaire().contains(Objet.PELLE))
			{
				((Corsaire) p).getInventaire().add(lootCase);
				this.plateau.getCases().get(indexArrivee).setLoot(null);
				AnsiTerminal.afficherMessage(Case.CORS_TUI_COLOR+((Corsaire) p).getNom()+AnsiTerminal.RESET+" obtient "+Case.LOOT_TUI_COLOR+lootCase.toString()+AnsiTerminal.RESET);
//				afficher plateau post ramassage
				this.plateau.afficher(p);
				AnsiTerminal.sleep();
			}
			
			if(lootCase==Bonus.ARMURE)
				((Corsaire)p).addToProb(ARMURE_BONUS);
			else if(lootCase==Objet.MACHETTE)
				((Corsaire)p).addToProb(MACHETTE_BONUS);
			else if(lootCase==Bonus.MOUSQUET)
				((Corsaire)p).addToProb(MOUSQUET_BONUS);
			
		}
		
		return true;
	}
	
	public void deplacerAleat(Pirate p) throws InterruptedException
	{
		List<Pair<Integer,Integer>> dirPossibles = p.getDirPossibles();
		int nbMaxDeplacements = p.getNbMaxDeplacements();
		
		Random r = new Random();
		
//		generer nombre de deplacements
		int nbDeplacements = r.nextInt(nbMaxDeplacements)+1;	//genere un nb entre 1 (compris) et nbMaxDeplacements (compris)
//		System.out.println(nbDeplacements);
		
		for(int i = 1;i<=nbDeplacements;++i)
		{
			boolean impossible=true;
			while(impossible)
			{
				Pair<Integer,Integer> direction = dirPossibles.get(r.nextInt(dirPossibles.size()));
//				System.out.println(direction.key+";"+direction.value);
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
			plateau.afficherAll();
			
			int a;
			Scanner sc = new Scanner(System.in);
			a = sc.nextInt();
			sc.close();
			Personnage persoADeplacer = plateau.getCases().get(a).getPersos().get(0);
			
//			d.deplacer(persoADeplacer, Direction.HAUT);
			d.deplacer(persoADeplacer, Direction.GAUCHE);
			d.deplacer(persoADeplacer, Direction.BAS_DROITE);
			
//			d.deplacerAleat((Pirate)persoADeplacer);
			
			plateau.afficherAll();		

		} catch (PlateauException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

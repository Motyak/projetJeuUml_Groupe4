import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Partie {
	private List<Corsaire> corsaires;
	private List<Pirate> pirates;
	
	private Plateau plateau;
	private Deplaceur deplaceur;
	
	private int dimensionPlateau;
	private int tour;
	private String inputJoueur;
	private Corsaire joueurCourant;
	
	public static final Map<String,Pair<Integer,Integer>> inputMap = new TreeMap<String,Pair<Integer,Integer>>(){{
		put("haut",Direction.HAUT);
		put("bas",Direction.BAS);
		put("gauche",Direction.GAUCHE);
		put("droite",Direction.DROITE);
		put("haut_gauche",Direction.HAUT_GAUCHE);
		put("haut_droite",Direction.HAUT_DROITE);
		put("bas_gauche",Direction.BAS_GAUCHE);
		put("bas_droite",Direction.BAS_DROITE);
	}};
	
	Partie(List<Corsaire> corsaires, List<Pirate> pirates, int dimensionPlateau) throws Exception
	{
		if(corsaires==null || corsaires.size()<1)
			throw new PartieException("Corsaires non conformes");
		
		this.corsaires=corsaires;
		this.pirates=pirates;
		this.dimensionPlateau=dimensionPlateau;
		this.plateau=new Plateau(corsaires,pirates,dimensionPlateau);
		this.deplaceur = new Deplaceur(this.plateau);
		this.tour=0;
		this.inputJoueur=null;
		this.joueurCourant=this.corsaires.get(0);
	}
	
	public Plateau getPlateau()
	{
		return this.plateau;
	}
	
	public Deplaceur getDeplaceur()
	{
		return this.deplaceur;
	}
	
	public void afficherPlateau()
	{
		this.plateau.afficher(this.joueurCourant);
	}
	
	public void actionJoueur() throws InterruptedException
	{
		boolean inputCorrecte=false;
		boolean deplacementReussi=true;
	    
		this.annoncerTourJoueur();
		
		AnsiTerminal.clear();
		
//		boucle tant que deplacement impossible
		do {
//			boucle tant que input incorrecte
			do {
				this.afficherPlateau();
				if(!deplacementReussi && inputCorrecte)
					System.out.println("Déplacement impossible !");
				this.demanderInputJoueur();
				inputCorrecte = Arrays.asList("haut","bas","gauche","droite","haut_gauche","haut_droite","bas_gauche","bas_droite").contains(this.inputJoueur);
			}while(!inputCorrecte);
			deplacementReussi = this.traiterInput();
		}while(!deplacementReussi);
//		affichage plateau post deplacement
		this.afficherPlateau();
	}
	
	private void annoncerTourJoueur() throws InterruptedException
	{
		Pair<Integer,Integer> coords = this.joueurCourant.getCoords();
		char coordLettre = (char)(coords.key.intValue()+65);
		int coordNum = coords.value+1;
		AnsiTerminal.afficherMessage("C'est au tour de "+Case.CORS_TUI_COLOR+this.joueurCourant.getNom()+AnsiTerminal.RESET+" !");
	}
	
	private void demanderInputJoueur()
	{
		Pair<Integer,Integer> coords = this.joueurCourant.getCoords();
		char coordLettre = (char)(coords.key.intValue()+65);
		int coordNum = coords.value+1;

		System.out.println("Dans quelle direction souhaitez-vous vous déplacer ?");
		System.out.println("('haut','bas','gauche','droite','haut_gauche','haut_droite','bas_gauche','bas_droite')\n");
		System.out.print(Case.CORS_TUI_COLOR+this.joueurCourant.getNom()+AnsiTerminal.RESET+"("+coordLettre+";"+coordNum+")>");
		Scanner s = new Scanner(System.in);
		this.inputJoueur=s.next();
	}
	
	private boolean traiterInput() throws InterruptedException
	{		
//		deplacer le personnage du joueur courant dans la direction par rapport à l'input
		return this.deplaceur.deplacer(this.joueurCourant, Partie.inputMap.get(this.inputJoueur));
	}
	
	public void actionPirates() throws InterruptedException
	{
		AnsiTerminal.afficherMessage("Tour des pirates...");
		for(Pirate p : this.pirates)
		{
			this.afficherPlateau();
			Thread.sleep(1500);
			this.deplaceur.deplacerAleat(p);
		}
		
		this.afficherPlateau();
		Thread.sleep(1500);
		
//		attribution prochain joueur
		int indexJoueurCourant=this.corsaires.indexOf(this.joueurCourant);
		this.joueurCourant=this.corsaires.get((indexJoueurCourant+1)%this.corsaires.size());
	}
	
	public boolean verifierFinPartie() throws InterruptedException
	{
//		si un joueur possède le trésor dans son inventaire, il a gagné => fin de partie
		for(Corsaire c : this.corsaires)
		{
			if(c.getInventaire().contains(ConditionVictoire.TRESOR))
			{
				AnsiTerminal.afficherMessage("Le joueur "+Case.CORS_TUI_COLOR+c.getNom()+AnsiTerminal.RESET+" a trouvé le trésor !");
				AnsiTerminal.afficherMessage(Case.CORS_TUI_COLOR+c.getNom()+AnsiTerminal.RESET+" gagne la partie !");
				return true;
			}
		}
		return false;
	}
	
	public static void main(String args[])
	{
		List<Corsaire> corsaires = Arrays.asList(new Corsaire("_J1_"),new Corsaire("_J2_"));
		List<Pirate> pirates = Arrays.asList(new Boucanier(),new Flibustier());
		Partie partie;
		try {
			partie = new Partie(corsaires,pirates,5);
			boolean finPartie=false;
			while(!finPartie)
			{
				partie.actionJoueur();
				finPartie=partie.verifierFinPartie();
				if(!finPartie)
				{
					Thread.sleep(1500);
					partie.actionPirates();
				}
					
			}
				
		}
		catch (PartieException | PlateauException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}

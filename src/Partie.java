import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Tom
 *
 */
public class Partie {
	private ArrayList<Corsaire> corsaires;
	private ArrayList<Pirate> pirates;
	
	private Plateau plateau;
	private Deplaceur deplaceur;
	
	private int dimensionPlateau;
//	private int tour;
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
	
	Partie(ArrayList<Corsaire> corsaires,ArrayList<Pirate> pirates, int dimensionPlateau) throws Exception
	{
		if(corsaires==null || corsaires.size()<1)
			throw new PartieException("Corsaires non conformes");
		
		this.corsaires=corsaires;
		this.pirates=pirates;
		this.dimensionPlateau=dimensionPlateau;
		this.plateau=new Plateau(corsaires,pirates,dimensionPlateau);
		this.deplaceur = new Deplaceur(this.plateau);
//		this.tour=0;
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
	
	/**
	 * Prints the board in the console with current player's POV
	 */
	private void afficherPlateau()
	{
		this.plateau.afficher(this.joueurCourant);
	}
	
	/**
	 * Asks user for inputs
	 */
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
	
	/**
	 * Player action phase
	 * @throws InterruptedException because of thread.sleep
	 */
	private void actionJoueur() throws InterruptedException
	{
		boolean inputCorrecte=false;
		boolean deplacementReussi=true;
	    
//		annoncer tour du joueur
		AnsiTerminal.afficherMessage("C'est au tour de "+Case.CORS_TUI_COLOR+this.joueurCourant.getNom()+AnsiTerminal.RESET+" !");
		
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
	}
	

	

	
	/**
	 * Processes user input
	 * @return false if the movement was impossible, true otherwise
	 * @throws InterruptedException because of thread.sleep
	 */
	private boolean traiterInput() throws InterruptedException
	{		
//		deplacer le personnage du joueur courant dans la direction par rapport à l'input
		return this.deplaceur.deplacer(this.joueurCourant, Partie.inputMap.get(this.inputJoueur));
	}
	
	private void actionPirates() throws InterruptedException
	{
		if(this.pirates.isEmpty())
			return;
		AnsiTerminal.afficherMessage("Tour des pirates...");
		for(Pirate p : this.pirates)
		{
			this.afficherPlateau();
			AnsiTerminal.sleep();
			this.deplaceur.deplacerAleat(p);
		}
		
//		affichage plateau avec point de vue joueur precedent
		this.afficherPlateau();
		AnsiTerminal.sleep();
	}
	
	/**
	 * Check if someone won the game
	 * @return returns true if someone won the game, false otherwise
	 * @throws InterruptedException because of thread.sleep
	 */
	private boolean verifierFinPartie() throws InterruptedException
	{
//		s'il n'y a plus de corsaires/joueurs restants..
		if(this.corsaires.isEmpty())
		{
			AnsiTerminal.afficherMessage("Tous les corsaires ont péris !");
			AnsiTerminal.afficherMessage("Personne ne gagne la partie !");
			return true;
		}
			
		
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
	
	/**
	 * Makes characters fight if needed
	 * @throws InterruptedException because of thread.sleep
	 */
	private void faireCombattre() throws InterruptedException
	{
//		Faire la liste des combats
		ArrayList<Pair<Pirate,Corsaire>> combats = new ArrayList<Pair<Pirate,Corsaire>>();
		for(Pirate p : this.pirates)
		{
			Pair<Integer,Integer> coordsP = p.getCoords();
			Case casePirate = this.plateau.getCases().get(Plateau.coordsToIndex(coordsP, this.dimensionPlateau));
			
//			le pirate attaque tous les corsaires sur la meme case que lui
			ArrayList<Personnage> persosMemeCase = casePirate.getPersos();
			for(Personnage perso : persosMemeCase)
			{
				if(perso instanceof Corsaire)
					combats.add(new Pair<Pirate,Corsaire>(p,(Corsaire) perso));
			}
			
			
			if(p instanceof Flibustier)
			{
				for(int i=coordsP.key-1;i<=coordsP.key+1;++i)
				{
					for(int j=coordsP.value-1;j<=coordsP.value+1;++j)
					{
//						ne pas traiter le cas ou la case = la case du pirate car deja traitée
						if(i==coordsP.key && j==coordsP.value)
							continue;
						Pair<Integer,Integer> coordsTmp = new Pair<Integer,Integer>(i,j);
						int indexTmp = Plateau.coordsToIndex(coordsTmp, this.dimensionPlateau);
//						si on est en dehors du plateau, on ne traite pas
						if(indexTmp==-1)
							continue;

						Pair<Integer,Integer> coord = new Pair<Integer,Integer>(i,j);
						ArrayList<Personnage> pers = this.plateau.getCases().get(Plateau.coordsToIndex(coord, this.dimensionPlateau)).getPersos();
						for(Personnage perso : pers)
						{
							if(perso instanceof Corsaire)
								combats.add(new Pair<Pirate,Corsaire>(p,(Corsaire) perso));
						}
					}
				}
			}
		}
		
		for(Pair<Pirate,Corsaire> pair : combats)
		{
//			si pirate/corsaire déjà mort lors d'un précédent combat => on passe
			if(!this.pirates.contains(pair.key) || !this.corsaires.contains(pair.value))
				continue;
			AnsiTerminal.afficherMessage(Case.PIR_TUI_COLOR+pair.key.getClass().getSimpleName()+AnsiTerminal.RESET+" attaque "+Case.CORS_TUI_COLOR+pair.value.getNom()+AnsiTerminal.RESET);
			boolean pirateMeurt=Math.random()>=1.0-pair.value.getProb();
			if(pirateMeurt)
			{
				AnsiTerminal.afficherMessage(Case.CORS_TUI_COLOR+pair.value.getNom()+AnsiTerminal.RESET+" a vaincu "+Case.PIR_TUI_COLOR+pair.key.getClass().getSimpleName()+AnsiTerminal.RESET);
				int indexPirate = Plateau.coordsToIndex(pair.key.getCoords(), this.dimensionPlateau);
				this.plateau.getCases().get(indexPirate).getPersos().remove(pair.key);
				this.pirates.remove(pair.key);
			}
				
			else
			{
				AnsiTerminal.afficherMessage(Case.CORS_TUI_COLOR+pair.value.getNom()+AnsiTerminal.RESET+" a péri face à "+Case.PIR_TUI_COLOR+pair.key.getClass().getSimpleName()+AnsiTerminal.RESET);
				int indexCorsaire = Plateau.coordsToIndex(pair.value.getCoords(), this.dimensionPlateau);
				this.plateau.getCases().get(indexCorsaire).getPersos().remove(pair.value);
//				si le joueur courant s'apprete a mourrir, on désigne un nouveau joueur courant
//				if(pair.value==this.joueurCourant)
//				{
//					if(this.corsaires.size()>1)
//					{
//						int indexJoueurCourant=this.corsaires.indexOf(this.joueurCourant);
//						this.joueurCourant=this.corsaires.get((indexJoueurCourant+1)%this.corsaires.size());
//					}
//				}
				this.corsaires.remove(pair.value);
			}
		}
//		si y'a eu au moins un combat => on affiche le plateau pour voir résultats avant le tour des pirates
		if(!combats.isEmpty())
		{
			this.afficherPlateau();
			AnsiTerminal.sleep();
		}
	}
	
	/**
	 * Assign next current player
	 */
	private void designerProchainJoueur()
	{
//		le cas ou il n'y a plus de corsaires (div zero)
		if(this.corsaires.isEmpty())
			return;
		
		if(this.joueurCourant==null)
			return;
		
		int indexJoueurCourant=this.corsaires.indexOf(this.joueurCourant);

//		cas general, si joueur d'après est mort => pas grave car tassement de l'arraylist
		this.joueurCourant=this.corsaires.get((indexJoueurCourant+1)%this.corsaires.size());
	}
	
	/**
	 * Launches the game
	 * @throws InterruptedException because of thread.sleep
	 */
	public void lancer() throws InterruptedException
	{
		boolean finPartie=false;
		while(!finPartie)
		{
//			le joueur courant se déplace et ramasse automatiquement le loot
			this.actionJoueur();
			this.faireCombattre();
			finPartie=this.verifierFinPartie();
			if(!finPartie)
			{
				this.actionPirates();
				this.faireCombattre();
				this.designerProchainJoueur();
				finPartie=this.verifierFinPartie();
			}	
		}
	}
	
	public static void main(String args[])
	{
		ArrayList<Corsaire> corsaires = new ArrayList<Corsaire>();
		corsaires.add(new Corsaire("_J1_"));
		corsaires.add(new Corsaire("_J2_"));
		ArrayList<Pirate> pirates = new ArrayList<Pirate>();
		pirates.add(new Boucanier());
		pirates.add(new Flibustier());
		Partie partie;
		
//		création et lancement de la partie
		try {
			partie = new Partie(corsaires,pirates,5);
			partie.lancer();
		}
		catch (PartieException | PlateauException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}

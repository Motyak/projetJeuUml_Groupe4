import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Plateau {

//	régles établies par le CDC
	public static int NB_CORS_MIN = 1;
	public static int NB_PIR_MIN = 2;
	public static int NB_MACHETTES_PAR_CORS = 2;
	public static int NB_MOUSQUETS_PAR_CORS = 2;
	public static int NB_ARMURES_PAR_CORS = 2;
	public static int NB_PELLES_PAR_CORS = 1;
	public static int NB_TRESOR = 1;
	public static double PROPORTION_CASES_TERRE = 0.9;
	
	private ArrayList<Case> cases;
	
//	Dès qu'on aura fait les classes Corsaire et Pirate, on modifiera pour passer en parametre un ensemble de Corsaire et de Pirate
	Plateau(List<Corsaire> corsaires, List<Pirate> pirates, int dimension) throws Exception
	{
		int nbCorsaires = corsaires.size();
		int nbPirates = pirates.size();
		
		if(nbCorsaires<Plateau.NB_CORS_MIN)
			throw new PlateauException("Le nombre de corsaires min est "+Plateau.NB_CORS_MIN);
		if(nbPirates<Plateau.NB_PIR_MIN)
			throw new PlateauException("Le nombre de pirates min est "+Plateau.NB_PIR_MIN);
		
//		calcul du nombre de cases min necessaires en fonction du nb de corsaires et de pirates
		int nbCasesNecessaires=nbCorsaires+
				nbPirates+
				NB_TRESOR+
				(NB_MACHETTES_PAR_CORS+NB_MOUSQUETS_PAR_CORS+NB_ARMURES_PAR_CORS+NB_PELLES_PAR_CORS)*nbCorsaires;
				
//		calcul de la dimension min en fonction du nb de cases min necessaires
		int dimensionMin=(int) Math.round(Math.sqrt(nbCasesNecessaires));
		double tmp = Math.sqrt(nbCasesNecessaires);
//		si le nb de cases n'est pas un carré parfait..
		if(tmp-Math.floor(tmp)!=0)
			dimensionMin++;

		if(dimension<dimensionMin)
			throw new PlateauException("La dimension de plateau min, pour cette configuration, est "+dimensionMin);
		
		int nbCases = dimension*dimension;
		int nbCasesTerre = (int) Math.round(nbCases*PROPORTION_CASES_TERRE);
		int nbCasesForet = (int) Math.ceil((double)(nbCases-nbCasesTerre)/2);
		int nbCasesEau = nbCases-nbCasesTerre-nbCasesForet;
		
		
//		instanciation du tableau de cases avec comme capacité initiale dimension^2
		this.cases = new ArrayList<Case>(nbCases);
		
//		insertion des cases, avec leur type, dans le plateau
		for(int i=1;i<=nbCasesTerre;++i)
			this.cases.add(new Case(TypeCase.TERRE));
		for(int i=1;i<=nbCasesForet;++i)
			this.cases.add(new Case(TypeCase.FORET));
		for(int i=1;i<=nbCasesEau;++i)
			this.cases.add(new Case(TypeCase.EAU));
		
//		mélange du plateau
		Collections.shuffle(this.cases);
		
//		insertion du trésor, des lootables et des personnages dans le plateau
//		dabord inserer ce qui peut etre dans une case foret
		int i = 0;
		for(int j=1;j<=NB_TRESOR;++j)
		{
			while(this.cases.get(i).getType()==TypeCase.EAU)
				i++;	
			this.cases.get(i).setLoot(ConditionVictoire.TRESOR);
			i++;
		}
		for(Pirate p : pirates)
		{
			while(this.cases.get(i).getType()==TypeCase.EAU)
				i++;
			this.cases.get(i).getPersos().add(p);
			i++;
		}
		for(int j=1;j<=NB_MOUSQUETS_PAR_CORS*nbCorsaires;++j)
		{
			while(this.cases.get(i).getType()==TypeCase.EAU)
				i++;
				
			this.cases.get(i).setLoot(Bonus.MOUSQUET);
			i++;
		}
		for(int j=1;j<=NB_ARMURES_PAR_CORS*nbCorsaires;++j)
		{
			while(this.cases.get(i).getType()==TypeCase.EAU)
				i++;
				
			this.cases.get(i).setLoot(Bonus.ARMURE);
			i++;
		}
		for(int j=1;j<=NB_PELLES_PAR_CORS*nbCorsaires;++j)
		{
			while(this.cases.get(i).getType()==TypeCase.EAU)
				i++;
				
			this.cases.get(i).setLoot(Objet.PELLE);
			i++;
		}
		for(int j=1;j<=NB_MACHETTES_PAR_CORS*nbCorsaires;++j)
		{
			while(this.cases.get(i).getType()!=TypeCase.TERRE)
				i++;
				
			this.cases.get(i).setLoot(Objet.MACHETTE);
			i++;
		}
		for(Corsaire c : corsaires)
		{
			while(this.cases.get(i).getType()!=TypeCase.TERRE)
				i++;
			this.cases.get(i).getPersos().add(c);
			i++;
		}	
		
//		mélange du plateau
		Collections.shuffle(this.cases);
		
//		donner les coordonnées initiales à chaque personnage
		int j=0;
		for(Case c : this.cases)
		{
			if(!c.getPersos().isEmpty())
				c.getPersos().get(0).setCoords(Plateau.indexToCoords(j,dimension));
			j++;
		}
	}
	
	public static Pair<Integer,Integer> indexToCoords(int i,int dim)
	{
		return new Pair<Integer,Integer>(new Integer(i%dim),new Integer(i/dim));
	}
	
	public static int coordsToInt(Pair<Integer,Integer> coords,int dim)
	{
		return coords.value*dim+coords.key;
	}
	
//	faire méthode pour afficher le plateau (test)
	
	public static void main(String[] args) {
	
		Plateau p;
		List<Corsaire> corsaires = Arrays.asList(new Corsaire("Joueur1"),new Corsaire("Joueur2"));
		List<Pirate> pirates = Arrays.asList(new Boucanier(),new Flibustier());
		
		try {
			p = new Plateau(corsaires,pirates,6);
			
			for(int i = 0; i < p.cases.size(); i++)
		    {
		      System.out.println("donnée à l'indice " + i + " = " + p.cases.get(i)+" de type "+p.cases.get(i).getType().toString());
		      if(p.cases.get(i).getLoot()!=null)
		    	  System.out.println("__loot : "+p.cases.get(i).getLoot().toString());
		      if(!p.cases.get(i).getPersos().isEmpty()) {
		    	  System.out.println("__perso :"+p.cases.get(i).getPersos().get(0).getClass().getSimpleName());
		    	  System.out.println("____coords : "+p.cases.get(i).getPersos().get(0).getCoords().key+","+p.cases.get(i).getPersos().get(0).getCoords().value);
		    	  if(p.cases.get(i).getPersos().get(0) instanceof Corsaire)
		    		  System.out.println("____nom : "+((Corsaire) p.cases.get(i).getPersos().get(0)).getNom());
		      }
		      System.out.println("");
		    }
		} catch (PlateauException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}

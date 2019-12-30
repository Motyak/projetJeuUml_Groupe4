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
	public static int VISIBILITE_PERSO = 2;
	
	private ArrayList<Case> cases;
	
	private int dim;
	
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
		
		this.dim=dimension;
		
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
	
	public ArrayList<Case> getCases()
	{
		return this.cases;
	}
	
	public int getDimension()
	{
		return this.dim;
	}
	
	public static Pair<Integer,Integer> indexToCoords(int i,int dim)
	{
		if(i<0 || i>dim*dim-1)
			return null;
		
		return new Pair<Integer,Integer>(new Integer(i/dim),new Integer(i%dim));
	}
	
	public static int coordsToIndex(Pair<Integer,Integer> coords,int dim)
	{
		if(coords.key<0 || coords.key>dim-1 || coords.value<0 || coords.value>dim-1)
			return -1;
		
		return coords.key*dim+coords.value;
	}

	public void afficherAll()
	{
//		affichage avec seulement les numeros de colonne
//		ArrayList<String> headers = new ArrayList<String>();
//		for(int i=1;i<=this.dim;++i)
//			headers.add(new String("~"+String.valueOf(i)+"~"));
//		
//		String[] s_headers = new String[headers.size()];
//		s_headers = headers.toArray(s_headers);
//		
//		String[][] data = new String[this.dim][this.dim];
//		for(int i = 0;i<this.dim;++i)
//		{
//			for(int j = 0;j<this.dim;++j)
//			{
//				Case c = this.cases.get(Plateau.coordsToIndex(new Pair<Integer,Integer>(new Integer(i),new Integer(j)), this.dim));
//				data[i][j]=FlipTable.of(c.toTui().key, c.toTui().value);
//			}
//		}
//		
//		System.out.println(FlipTable.of(s_headers, data));
		
		
		
		ArrayList<String> headers = new ArrayList<String>();
		headers.add(new String(""));
		for(int i=1;i<=this.dim;++i)
			headers.add(new String("~"+String.valueOf(i)+"~"));
		
		String[] s_headers = new String[headers.size()];
		s_headers = headers.toArray(s_headers);
		
		String[][] data = new String[this.dim][this.dim+1];
		for(int i = 0;i<=this.dim-1;++i)
		{
			for(int j = 0;j<=this.dim;++j)
			{
				if(j==0)
					data[i][j]="~"+Character.toString((char)(i+1+64))+"~";
				else
				{
//					meme operation qu'en haut mais avec j-1
					Case c = this.cases.get(Plateau.coordsToIndex(new Pair<Integer,Integer>(new Integer(i),new Integer(j-1)), this.dim));
					data[i][j]=FlipTable.of(c.toTui().key, c.toTui().value);
				}

			}
		}
		AnsiTerminal.clear();
		System.out.println(FlipTable.of(s_headers, data));
	}
	
	public void afficher(Personnage p)
	{
		Pair<Integer,Integer> coordsP = p.getCoords();
		ArrayList<Integer> indexCasesVisibles = new ArrayList<Integer>();
		for(int i=coordsP.key-VISIBILITE_PERSO;i<=coordsP.key+VISIBILITE_PERSO;++i)
		{
			for(int j=coordsP.value-VISIBILITE_PERSO;j<=coordsP.value+VISIBILITE_PERSO;++j)
			{
				Pair<Integer,Integer> coordsTmp = new Pair<Integer,Integer>(i,j);
				int indexTmp = Plateau.coordsToIndex(coordsTmp, this.dim);
				if(indexTmp!=-1)
					indexCasesVisibles.add(new Integer(indexTmp));
			}
		}
		
		
		ArrayList<String> headers = new ArrayList<String>();
		headers.add(new String(""));
		for(int i=1;i<=this.dim;++i)
			headers.add(new String("~"+String.valueOf(i)+"~"));
		
		String[] s_headers = new String[headers.size()];
		s_headers = headers.toArray(s_headers);
		
		String[][] data = new String[this.dim][this.dim+1];
		for(int i = 0;i<=this.dim-1;++i)
		{
			for(int j = 0;j<=this.dim;++j)
			{
				if(j==0)
					data[i][j]="~"+Character.toString((char)(i+1+64))+"~";
				else
				{
					int indexTmp = Plateau.coordsToIndex(new Pair<Integer,Integer>(new Integer(i),new Integer(j-1)),this.dim);
					Case c = this.cases.get(indexTmp);
					Pair<String[], String[][]> c_tui;
					if(!indexCasesVisibles.contains(indexTmp))
					{
//						faire un clone de la case ou on enleve le loot avant de l'afficher
						Case caseSansLoot = new Case(c);
						caseSansLoot.setLoot(null);
						c_tui = caseSansLoot.toTui();
					}
					else
						c_tui = c.toTui();
					data[i][j]=FlipTable.of(c_tui.key, c_tui.value);
				}
			}
		}
		AnsiTerminal.clear();
		System.out.println(FlipTable.of(s_headers, data));
	}
	
	public void afficherText()
	{
		for(int i = 0; i < this.cases.size(); i++)
	    {
	      System.out.println("donnée à l'indice " + i + " = " + this.cases.get(i)+" de type "+this.cases.get(i).getType().toString());
	      if(this.cases.get(i).getLoot()!=null)
	    	  System.out.println("__loot : "+this.cases.get(i).getLoot().toString());
	      if(!this.cases.get(i).getPersos().isEmpty()) {
	    	  System.out.println("__perso :"+this.cases.get(i).getPersos().get(0).getClass().getSimpleName());
	    	  System.out.println("____coords : "+this.cases.get(i).getPersos().get(0).getCoords().key+","+this.cases.get(i).getPersos().get(0).getCoords().value);
	    	  if(this.cases.get(i).getPersos().get(0) instanceof Corsaire)
	    		  System.out.println("____nom : "+((Corsaire) this.cases.get(i).getPersos().get(0)).getNom());
	      }
	      System.out.println("");
	    }
	}
	
	public static void main(String[] args) {
	
		Plateau p;
		List<Corsaire> corsaires = Arrays.asList(new Corsaire("_J1_"),new Corsaire("_J2_"));
		List<Pirate> pirates = Arrays.asList(new Boucanier(),new Flibustier());
		
		try {
			p = new Plateau(corsaires,pirates,5);
//			p.afficherText();
//			p.afficher();
			p.afficher(corsaires.get(0));

		} catch (PlateauException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

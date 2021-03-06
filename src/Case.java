import java.util.ArrayList;

/**
 * @author Tom
 *
 */
public class Case{

	public static final String CORS_TUI_COLOR = AnsiTerminal.BLUE;
	public static final String PIR_TUI_COLOR = AnsiTerminal.RED;
	public static final String LOOT_TUI_COLOR = AnsiTerminal.PURPLE;
	
	public static final String CASE_VIDE_TUI = FlipTable.of(new String[]{"      "},new String[][]{});
	
	private TypeCase type;
	private ArrayList<Personnage> persos;
	private Lootable loot;
	
	
	public Case(TypeCase t) {
		this.type = t;
		this.persos = new ArrayList<Personnage>();
		this.loot=null;
	}
	
	public Case(Case c)
	{
		this.type=c.getType();
		this.persos = c.getPersos();
		this.loot = c.getLoot();
	}
	
	public TypeCase getType() {
		return this.type;
	}
	
	public Lootable getLoot() {
		return loot;
	}
	
	public void setLoot(Lootable l)
	{
		this.loot=l;
	}
	
	public ArrayList<Personnage> getPersos() {
		return persos;
	}
	
	/**
	 * @return Case in text format
	 */
	public Pair<String[],String[][]> toTui()
	{
		ArrayList<String[]> lignes = new ArrayList<String[]>();
		
		ArrayList<String> loot = new ArrayList<String>();
//		if(this.getLoot()==null)
//			loot.add("");
//		else
		if(this.getLoot()!=null && this.getLoot()!=ConditionVictoire.TRESOR)
		{
			loot.add("("+LOOT_TUI_COLOR+this.getLoot().toString().substring(0,4)+AnsiTerminal.RESET+")");
			String[] s_loot = new String[loot.size()];
			s_loot = loot.toArray(s_loot);
			lignes.add(s_loot);
		}
		
		ArrayList<String> pirate = new ArrayList<String>();
		for(Personnage p : this.getPersos())
		{
			if(p instanceof Pirate)
			{
				pirate.add("<"+PIR_TUI_COLOR+p.getClass().getSimpleName().substring(0,4)+AnsiTerminal.RESET+">");
				String[] s_pirate = new String[pirate.size()];
				s_pirate = pirate.toArray(s_pirate);
				lignes.add(s_pirate);
				pirate.clear();
			}
		}
		
		ArrayList<String> corsaire = new ArrayList<String>();
		for(Personnage p : this.getPersos())
		{
			if(p instanceof Corsaire)
			{
				corsaire.add("\""+CORS_TUI_COLOR+((Corsaire) p).getNom()+AnsiTerminal.RESET+"\"");
				String[] s_corsaire = new String[corsaire.size()];
				s_corsaire = corsaire.toArray(s_corsaire);
				lignes.add(s_corsaire);
				corsaire.clear();
			}
		}
		
		String[] header = {"["+this.getType().toString().charAt(0)+"]"};
		String[][] data = new String[lignes.size()][];
		data = lignes.toArray(data);
		
		return new Pair<String[],String[][]>(header,data);
	}
	
	static public void main(String[] args)
	{
		Case c = new Case(TypeCase.TERRE);
		
		c.setLoot(Objet.MACHETTE);
		c.getPersos().add(new Corsaire("Paul"));
//		c.getPersos().add(new Corsaire("J2"));
//		c.getPersos().add(new Boucanier());
//		c.getPersos().add(new Flibustier());
		
		Pair<String[],String[][]> p = c.toTui();
		
		System.out.println(FlipTable.of(p.key, p.value));
		
//		ArrayList<Case> arr = new ArrayList<Case>();
////		ajout de cases à l'arr
//		
//
//		
//		arr.add(new Case (TypeCase.EAU));
//		arr.add(new Case (TypeCase.FORET));
//		arr.add(new Case (TypeCase.TERRE));
//		
////		parcour de l'arr et affichage de l'id de la case + son type
//		 System.out.println("les type de cases");
//		
//		 int i=1;
//		 for(Case c : arr) {
//			 System.out.println("Case n°"+i+" est de type "+c.getType());
//			 i++;
//		 }
		 
	}

	


}

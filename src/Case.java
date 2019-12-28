import java.util.ArrayList;

public class Case {

	private TypeCase type;
	private ArrayList<Personnage> persos;
	private Lootable loot;
	
	
	public Case(TypeCase t) {
		this.type = t;
		this.persos = new ArrayList<Personnage>();
		this.loot=null;
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
	
	static public void main(String[] args)
	{
		ArrayList<Case> arr = new ArrayList<Case>();
//		ajout de cases à l'arr
		

		
		arr.add(new Case (TypeCase.EAU));
		arr.add(new Case (TypeCase.FORET));
		arr.add(new Case (TypeCase.TERRE));
		
//		parcour de l'arr et affichage de l'id de la case + son type
		 System.out.println("les type de cases");
		
		 int i=1;
		 for(Case c : arr) {
			 System.out.println("Case n°"+i+" est de type "+c.getType());
			 i++;
		 }
		 
	}

	


}

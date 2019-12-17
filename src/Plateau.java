import java.util.ArrayList;

public class Plateau {

	private ArrayList<Case> cases;
	
	public static void main(String[] args) {
	
		Plateau p = new Plateau();
		
		p.cases = new ArrayList<Case>();
		
		p.cases.add(new Case(TypeCase.TERRE));
		p.cases.add(new Case(TypeCase.FORET));
		p.cases.add(new Case(TypeCase.EAU));
		 
		
		 for(int i = 0; i < p.cases.size(); i++)
		    {
		      System.out.println("donnée à l'indice " + i + " = " + p.cases.get(i)+" de type "+p.cases.get(i).getType().toString());
		    }  
		
	}
	
	
	
	
}

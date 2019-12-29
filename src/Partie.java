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
		
		
		
	}
}

import java.util.ArrayList;

public class TU_Lootable {

	public static void main(String[] args) {
		System.out.println("Initialisation de la liste de Lootable..");
		ArrayList<Lootable> arr = new ArrayList<Lootable>();
		arr.add(Bonus.ARMURE);
		arr.add(Bonus.MOUSQUET);
		arr.add(Objet.MACHETTE);
		arr.add(Objet.PELLE);
		
		System.out.println("Affichage des Lootable..");
		int i = 1;
		for(Lootable l : arr)
		{
			String type = l.getClass().getSimpleName();
			System.out.println("Le lootable "+i+" est de type "+type+" : "+l.toString());
			i++;
		}
		
	}

}

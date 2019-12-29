import java.util.Arrays;
import java.util.List;

public class Direction {
	public static final Pair<Integer,Integer> HAUT = new Pair<Integer,Integer>(-1,0);
	public static final Pair<Integer,Integer> BAS = new Pair<Integer,Integer>(1,0);
	public static final Pair<Integer,Integer> GAUCHE = new Pair<Integer,Integer>(0,-1);
	public static final Pair<Integer,Integer> DROITE = new Pair<Integer,Integer>(0,1);
	public static final Pair<Integer,Integer> HAUT_GAUCHE = new Pair<Integer,Integer>(-1,-1);
	public static final Pair<Integer,Integer> HAUT_DROITE = new Pair<Integer,Integer>(-1,1);
	public static final Pair<Integer,Integer> BAS_GAUCHE = new Pair<Integer,Integer>(1,-1);
	public static final Pair<Integer,Integer> BAS_DROITE = new Pair<Integer,Integer>(1,1);
	
	public static final List<Pair<Integer, Integer>> LIGNES_DROITES = Arrays.asList(HAUT,BAS,GAUCHE,DROITE);
	public static final List<Pair<Integer, Integer>> DIAGONALES = Arrays.asList(HAUT_GAUCHE,HAUT_DROITE,BAS_GAUCHE,BAS_DROITE);
	public static final List<Pair<Integer, Integer>> LIGNES_DROITES_ET_DIAG = Arrays.asList(HAUT,BAS,GAUCHE,DROITE,HAUT_GAUCHE,HAUT_DROITE,BAS_GAUCHE,BAS_DROITE);
}

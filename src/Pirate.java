import java.util.List;

public abstract class Pirate extends Personnage{

	protected int nbMaxDeplacements;
	protected List<Pair<Integer,Integer>> dirPossibles;
	
	abstract void attaquer(Corsaire c);

	public int getNbMaxDeplacements() {
		return nbMaxDeplacements;
	}

	public List<Pair<Integer,Integer>> getDirPossibles() {
		return dirPossibles;
	}
}

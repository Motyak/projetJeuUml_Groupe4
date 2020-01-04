

/**
 * @author Tom
 *
 */
public class Boucanier extends Pirate {

	public static final int NB_DEPLACEMENTS_BOUC = 2;
	
	public Boucanier()
	{
		this.nbMaxDeplacements=NB_DEPLACEMENTS_BOUC;
		this.dirPossibles=Direction.LIGNES_DROITES_ET_DIAG;
	}

	@Override
	public void attaquer(Corsaire c) {
		// TODO Auto-generated method stub
		
	}

}

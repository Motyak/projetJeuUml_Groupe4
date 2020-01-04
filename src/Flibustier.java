

/**
 * @author Tom
 *
 */
public class Flibustier extends Pirate{

	public static final int NB_DEPLACEMENTS_FLIB = 1;
	
	public Flibustier()
	{
		this.nbMaxDeplacements=NB_DEPLACEMENTS_FLIB;
		this.dirPossibles=Direction.LIGNES_DROITES_ET_DIAG;
	}
	
	@Override
	public void attaquer(Corsaire c) {
		// TODO Auto-generated method stub
		
	}

}

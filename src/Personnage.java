
public abstract class Personnage implements Deplacable {

	private Pair<Integer,Integer> coords;
	
	public void setCoords(Pair<Integer,Integer> c)
	{
		this.coords=c;
	}
	
	@Override
	public Pair<Integer, Integer> getCoords() {
		return this.coords;
	}

}

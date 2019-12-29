
public abstract class Personnage {

	private Pair<Integer,Integer> coords;
	
	public Pair<Integer,Integer> getCoords()
	{
		return this.coords;
	}
	
	public void setCoords(Pair<Integer,Integer> c)
	{
		this.coords=c;
	}

}


public class PlateauException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg="";
	
	PlateauException(String msg)
	{
		this.msg=msg;
	}
	
	public String getMessage()
	{
		return this.msg;
	}
}


/**
 * @author Tom
 *
 */
public class PartieException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg="";
	
	PartieException(String msg)
	{
		this.msg=msg;
	}
	
	public String getMessage()
	{
		return this.msg;
	}

}

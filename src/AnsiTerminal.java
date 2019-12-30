
public class AnsiTerminal {
	
	public static final String RESET = "\033[0m";
	public static final String GREEN = "\033[1;32m";
	public static final String RED = "\033[1;31m";
	public static final String BLUE = "\033[1;34m";
	public static final String PURPLE = "\033[1;35m";
	
	public static void clear()
	{
		System.out.print("\033[H\033[2J");
	    System.out.flush();
	}
}

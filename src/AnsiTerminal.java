
/**
 * gives access to Ansi escape codes
 * @author Tom
 */
public class AnsiTerminal {
	
	public static final String RESET = "\033[0m";
	public static final String GREEN = "\033[1;32m";
	public static final String RED = "\033[1;31m";
	public static final String BLUE = "\033[1;34m";
	public static final String PURPLE = "\033[1;35m";
	
	public static final int MSG_SLEEP_TIME = 2000;
	
	/**
	 * clear the console
	 */
	public static void clear()
	{
		System.out.print("\033[H\033[2J");
	    System.out.flush();
	}
	
	/**
	 * Freeze the console for MSG_SLEEP_TIME ms (2000 by default)
	 * @throws InterruptedException because of thread.sleep
	 */
	public static void sleep() throws InterruptedException
	{
		Thread.sleep(MSG_SLEEP_TIME);
	}
	
	/**
	 * Print a message in the console
	 * @param msg Message to print
	 * @throws InterruptedException because of thread.sleep
	 */
	public static void afficherMessage(String msg) throws InterruptedException
	{
		AnsiTerminal.clear();
		System.out.println(msg);
		AnsiTerminal.sleep();
		AnsiTerminal.clear();
	}
	
	
}

package procBuilder.engine;

/**
 * Represents an item that can be used as a command for a ProcessBuilder. Basically just a wrapper that you can call toString on.
 * @author David
 *
 */
public abstract class AbstractProcessItem {
	//LATER - Split out template items and static items
	
	public abstract String toString();
}
/**
 * File: Values.java
 * Package: SO-J.barberos.Values
 * Creation: 10/10/2014 at 4:11:44 p. m.
 */

package barberos;

/**
 * @author camiloasc1
 *
 */
public enum Values
{
	BARBERS(2), CHAIRS(5), CUTTIME(10);
	private int value;
	
	/**
	 * @param value
	 */
	private Values(int value)
	{
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public int getValue()
	{
		return value;
	}
}

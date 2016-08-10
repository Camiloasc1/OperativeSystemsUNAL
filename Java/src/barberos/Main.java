/**
 * File: Barberos.java
 * Package: SO-J.barberos.Barberos
 * Creation: 10/10/2014 at 3:48:39 p. m.
 */

package barberos;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @author camiloasc1
 *
 */
public class Main
{
	public static Semaphore chairSmph = new Semaphore(Values.CHAIRS.getValue(), true);
	public static LinkedList<Barber> barbers = new LinkedList<Barber>();
	public static LinkedList<Client> chairs = new LinkedList<Client>();

	public static void main(String[] args)
	{
		for (int i = 0; i < Values.BARBERS.getValue(); i++)
		{
			Barber barber = new Barber(i);
			barbers.add(barber);
			barber.start();
		}

		// Generate clients
		final int min = Values.CUTTIME.getValue() / (Values.CHAIRS.getValue() * Values.BARBERS.getValue());
		final int max = (Values.CUTTIME.getValue() * Values.CUTTIME.getValue())
				/ (Values.CHAIRS.getValue() * Values.BARBERS.getValue());
		while (true)
		{
			Client client = new Client();
			client.start();
			try
			{
				Thread.sleep(RandomNum.getInt(min, max) * 1000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	static String chairStatus(int tab)
	{
		String str = "";
		for (int i = 0; i < tab; i++)
		{
			str += '\t';
		}
		return str + "Chairs status: " + Main.chairs.size() + "/" + Values.CHAIRS.getValue();
	}
}

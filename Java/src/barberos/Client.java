/**
 * File: Client.java
 * Package: SO-J.barberos.Client
 * Creation: 10/10/2014 at 4:38:09 p. m.
 */

package barberos;

/**
 * @author camiloasc1
 *
 */
public class Client extends Thread
{
	/**
	 * @param barberSmph
	 * @param chairSmph
	 * @param chairs2
	 */
	public Client()
	{
		super();
	}

	@Override
	public void run()
	{
		if (Main.chairSmph.tryAcquire())// Takes seat
		{
			Main.chairs.add(this);
			System.out.println("Client arrives" + Main.chairStatus(2));

			// Warn barber(s)
			synchronized (Main.chairs)
			{
				Main.chairs.notify();
			}

			// Wait for barber
			try
			{
				synchronized (this)
				{
					this.wait();
				}
			}
			catch (InterruptedException e)
			{
			}

			// Barber calls to cut
			Main.chairSmph.release();
		}
	}
}

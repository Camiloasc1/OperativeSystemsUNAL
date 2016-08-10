/**
 * File: Barber.java
 * Package: SO-J.barberos.Barber
 * Creation: 10/10/2014 at 4:11:05 p. m.
 */

package barberos;

/**
 * @author camiloasc1
 *
 */
public class Barber extends Thread
{
	private int id;
	
	/**
	 * @param id
	 * @param barberSmph
	 * @param chairSmph
	 * @param chairs2
	 */
	public Barber(int id)
	{
		super();
		this.id = id;
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			if (Main.chairs.isEmpty())
			{
				System.out.println("Barber(" + id + "): Sleep" + Main.chairStatus(1));
				try
				{
					synchronized (Main.chairs)
					{
						// Wait a client
						Main.chairs.wait();
					}
				}
				catch (InterruptedException e)
				{
				}
			}
			
			// Call client
			Client client;
			synchronized (Main.chairs)// Probably not needed
			{
				client = Main.chairs.poll();
			}
			synchronized (client)
			{
				client.notify();
			}
			// Cut
			cut();
		}
	}
	
	/**
	 *
	 */
	public void cut()
	{
		System.out.println("Barber(" + id + "): Start" + Main.chairStatus(1));
		try
		{
			Thread.sleep(Values.CUTTIME.getValue() * 1000);
		}
		catch (InterruptedException e)
		{
		}
		System.out.println("Barber(" + id + "): End" + Main.chairStatus(2));
	}
}

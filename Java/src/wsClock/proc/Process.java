
package wsClock.proc;

import java.util.Random;

import wsClock.Main;
import wsClock.WSClock;

public class Process extends Thread
{
	private int id;
	private Random rand;
	
	/**
	 * @param id
	 */
	public Process(int id)
	{
		super();
		this.id = id;
		rand = new Random();
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		while (true)
		{
			int p = rand.nextInt(Main.WORKSPACESIZE);
			WSClock.RequestPage(id, p).Access(rand.nextBoolean());
			try
			{
				Thread.sleep(rand.nextInt(Main.PROCCOUNT * 1000));
			}
			catch (InterruptedException e)
			{
				System.err.println(e);
			}
		}
	}
}

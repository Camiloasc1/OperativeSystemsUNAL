
package segmentacion;

import java.util.LinkedList;
import java.util.Random;

public class Process extends Thread
{
	private LinkedList<Integer> workSpace;
	private Random rand;

	/**
	 * @param workSpace
	 */
	public Process(LinkedList<Integer> workSpace)
	{
		super();
		this.workSpace = workSpace;
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
			int p = workSpace.get(rand.nextInt(workSpace.size()));
			WSClock.RequestPage(p).Access(rand.nextBoolean());
			try
			{
				Thread.sleep(rand.nextInt(10) * 1000);
			}
			catch (InterruptedException e)
			{
				System.err.println(e);
			}
		}
	}
}

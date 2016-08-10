
package wsClock.swap;

public class SwapThread extends Thread
{
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
			Swap.syncPage();
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				System.err.println(e);
			}
		}
	}
}

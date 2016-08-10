
package segmentacion;

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
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				System.err.println(e);
			}
		}
	}
}

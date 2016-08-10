
package segmentacion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Swap
{
	private static final int MAXSIZE = 10;
	private static Queue<Page> queue = new LinkedList<Page>();

	public static Page requestPage(int id)
	{
		try
		{
			return (Page) FileSerializator.deserializeObject("swap/" + id + ".pg");
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}
		return new Page(null, null, id);
	}

	public static void scheduleSync(Page page) throws IllegalStateException
	{
		if (queue.size() >= MAXSIZE)
			throw new IllegalStateException();
		if (queue.contains(page))
			return;
		queue.add(page);
		System.out.println("Page Scheduled to Sync #" + page.getId());
	}

	protected static void syncPage()
	{
		try
		{
			Page page = queue.remove();
			page.clearM();
			System.out.println("Page Synchronized in Swap #" + page.getId());
			System.out.println("Pending Pages: " + queue);
			try
			{
				FileSerializator.serializeObject(page, "swap/" + page.getId() + ".pg");
			}
			catch (FileNotFoundException e)
			{
				// e.printStackTrace();
				System.err.println(e);
			}
			catch (IOException e)
			{
				// e.printStackTrace();
				System.err.println(e);
			}
		}
		catch (NoSuchElementException e)
		{
		}
	}
}

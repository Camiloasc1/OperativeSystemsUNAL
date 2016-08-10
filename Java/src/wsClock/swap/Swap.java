
package wsClock.swap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import wsClock.Main;
import wsClock.Page;
import wsClock.util.FileSerializator;

public class Swap
{
	private static Queue<Page> queue = new LinkedList<Page>();
	
	public synchronized static Page requestPage(int id)
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
	
	public synchronized static void scheduleSync(Page page) throws IllegalStateException
	{
		if (queue.size() >= Main.MAXQUEUESIZE)
			throw new IllegalStateException();
		if (queue.contains(page))
			return;
		queue.add(page);
		System.out.println("!   !" + "Page Scheduled to Sync #" + page.getId());
	}
	
	protected static void syncPage()
	{
		try
		{
			Page page = queue.remove();
			try
			{
				FileSerializator.serializeObject(page, "swap/" + page.getId() + ".pg");
				page.clearM();
				
				String str = "!   !";
				str += "Page Synchronized in Swap #" + page.getId() + " - ";
				str += "Pending Pages: [";
				boolean first = true;
				for (Page p : queue)
				{
					if (first)
					{
						str += "" + p.getId();
						first = false;
					}
					else
					{
						str += ", " + p.getId();
					}
				}
				str += "]";
				System.out.println(str);
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

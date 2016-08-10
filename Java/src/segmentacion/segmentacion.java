
package segmentacion;

import java.util.ArrayList;

public class segmentacion
{
	private static final int PhysicalPages = 10;
	private static final long Tau = 1 * 1000;
	private static int id_segment = 0;
	private static Page current = null;
	private static ArrayList<Page> segments = new ArrayList<Page>(20);

	/**
	 * Segmentacion con paginacion
	 */
	public synchronized static Page RequestPage(int id)
	{
		System.out.print("Process request page #" + id + " ");
		{
			Page page = isPageAllocated(id);
			if (page != null)
			{
				System.out.println("(Page Already Allocated) in segment " + id_segment);
				return page;
			}
		}
		System.out.println("(Page fault) in segment " + id_segment);
		System.out.println("Segment : " + id_segment);
		print();
		if (id_segment == 0)// First Page
		{
			current = Swap.requestPage(id);
			current.setPrevious(current);
			current.setNext(current);
			segments.add(current);
			id_segment++;
		}
		else if (id_segment < segments.size())// Fill Physical Memory
		{
			id_segment = segments.indexOf(current);
			Page page = Swap.requestPage(id);
			Page p = current;
			Page n = current.getNext();
			page.setPrevious(p);
			page.setNext(n);
			p.setNext(page);
			n.setPrevious(page);
			current = page;
		}
		else
		{
			Page start = current;
			boolean writeScheduled = false;
			do
			{
				if (current.isR())
				{
					current.clearR();
					current = current.getNext();
				}
				else
				{
					if ((System.currentTimeMillis() - current.getTime()) > Tau)
					{
						if (current.isM())
						{
							try
							{
								Swap.scheduleSync(current);
							}
							catch (IllegalStateException e)
							{
								// Sync queue is full
							}
							writeScheduled = true;
							current = current.getNext();
						}
						else
						{
							replaceCurrentPage(id);

							current = current.getNext();
							return current.getPrevious();
						}
					}
					else
					{
						current = current.getNext();
					}
				}
			} while (current != start);
			// Turned Around
			if (writeScheduled)
			{
				while (true)
				{
					if (current.isR())
					{
						current.clearR();
						current = current.getNext();
					}
					else
					{
						if ((System.currentTimeMillis() - current.getTime()) > Tau)
						{
							if (current.isM())
							{
								try
								{
									Swap.scheduleSync(current);
								}
								catch (IllegalStateException e)
								{
									// Sync queue is full
								}
								writeScheduled = true;
								current = current.getNext();
							}
							else
							{
								replaceCurrentPage(id);

								current = current.getNext();
								return current.getPrevious();
							}
						}
						else
						{
							current = current.getNext();
						}
					}
				}

			}
			else
			{
				while (current.isM())
				{
					current = current.getNext();
				}
				replaceCurrentPage(id);
				current = current.getNext();
				return current;
			}
		}
		current = current.getNext();
		return current.getPrevious();
	}

	/**
	 * @param id
	 * @return
	 */
	public static Page isPageAllocated(int id)
	{
		if (id_segment == 0)
			return null;
		Page page = current;
		Page start = current;
		while (page.getId() != id)
		{
			page = page.getNext();
			if (page == start)
				return null;
		}
		return page;
	}

	/**
	 * @param id
	 */
	private static void replaceCurrentPage(int id)
	{
		System.out.println("DeAllocated Page " + current);

		Page loaded = Swap.requestPage(id);
		loaded.Access(false);
		Page p = current.getPrevious();
		Page n = current.getNext();
		loaded.setPrevious(p);
		loaded.setNext(n);
		p.setNext(loaded);
		n.setPrevious(loaded);
		current = loaded;
	}

	/**
	 *
	 */
	public static void print()
	{
		System.out.println(getStr());
	}

	private static String getStr()
	{
		String str = "";
		if (id_segment == 0)
			return str;
		Page page = current;
		Page start = current;
		do
		{
			str += page.toString() + " ";
			page = page.getNext();
		} while (page != start);
		return str;
	}
}

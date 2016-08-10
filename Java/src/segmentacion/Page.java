
package segmentacion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class Page implements Serializable
{
	private static final long serialVersionUID = 2599735678159241949L;
	// private static final int size = (int) Math.pow(2, 10);
	
	private int id;
	private boolean R;
	private boolean M;
	private Page previous;
	private Page next;
	private long time;
	
	/**
	 * @param previous
	 * @param next
	 */
	public Page(Page previous, Page next, int id)
	{
		super();
		this.previous = previous;
		this.next = next;
		this.id = id;
		time = 0;
		try
		{
			FileSerializator.serializeObject(this, "swap/" + id + ".pg");
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
	
	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * @return the previous
	 */
	public Page getPrevious()
	{
		return previous;
	}

	/**
	 * @param previous
	 *            the previous to set
	 */
	public void setPrevious(Page previous)
	{
		this.previous = previous;
	}

	/**
	 * @return the next
	 */
	public Page getNext()
	{
		return next;
	}
	
	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(Page next)
	{
		this.next = next;
	}

	/**
	 * @return the r
	 */
	public boolean isR()
	{
		return R;
	}

	/**
	 *
	 */
	public void clearR()
	{
		R = false;
	}
	
	/**
	 * @return the m
	 */
	public boolean isM()
	{
		return M;
	}
	
	/**
	 *
	 */
	public void clearM()
	{
		M = false;
	}

	/**
	 * @return the time
	 */
	public long getTime()
	{
		return time;
	}
	
	public void Access(boolean write)
	{
		time = System.currentTimeMillis();
		R = true;
		if (write)
		{
			M = true;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[#" + id + "," + (R ? 1 : 0) + "," + (M ? 1 : 0) + "," + (System.currentTimeMillis() - time) + "]";
	}
}

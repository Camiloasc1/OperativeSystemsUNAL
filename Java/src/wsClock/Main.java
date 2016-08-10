
package wsClock;

import java.util.LinkedList;

import wsClock.proc.Process;
import wsClock.swap.SwapThread;

public class Main
{
	public static final int PROCCOUNT = 10;
	public static final int WORKSPACESIZE = 10;
	public static final int PHYSICALPAGES = 10;
	public static final long TAU = 1 * 1000;
	public static final int MAXQUEUESIZE = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		SwapThread swapTh = new SwapThread();
		swapTh.start();

		LinkedList<Process> proc = new LinkedList<Process>();
		for (int i = 0; i < PROCCOUNT; i++)
		{
			LinkedList<Integer> workSpace = new LinkedList<Integer>();
			for (int p = 0; p < WORKSPACESIZE; p++)
			{
				workSpace.add((i * WORKSPACESIZE) + p);
			}
			Process pr = new Process(i);
			pr.start();
			proc.add(pr);
		}

	}
}


package segmentacion;

import java.util.LinkedList;

public class Main
{
	private static final int PROCCOUNT = 10;
	private static final int WORKSPACESIZE = 10;

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
			Process pr = new Process(workSpace);
			pr.start();
			proc.add(pr);
		}

	}
}

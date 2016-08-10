/**
 * File: Main.java
 * Package: S.O..consola.Main
 * Creation: 6/09/2014 at 1:55:27 p. m.
 */

package consola;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner is = new Scanner(System.in);
		String cmd;
		String[] cmds;
		args = null;
		run:
		while (true)
		{
			System.out.print("[" + LocationManager.user + "@" + LocationManager.os + ":" + LocationManager.cd() + "]$ ");
			cmd = "";
			cmds = null;
			try
			{
				cmd = is.nextLine().trim();

				if (cmd.isEmpty())
				{
					continue run;
				}

				cmds = cmd.split(" ");

				if (cmds.length > 1)
				{
					args = new String[cmds.length - 1];
					System.arraycopy(cmds, 1, args, 0, cmds.length - 1);
				}
			}
			catch (NoSuchElementException e) // ^D
			{
				cmd = "exit";
				System.out.println(cmd);
				break run;
			}

			if (cmds[0].compareToIgnoreCase("exit") == 0)
			{
				break run;
			}
			else if (cmds[0].compareToIgnoreCase("cd") == 0)
			{
				LocationManager.cd(args);
			}
			else
			{
				try
				{
					Exec.run(cmd);
				}
				catch (IllegalArgumentException e)
				{
					if (e.getMessage() == "Empty command")
					{
						continue run;
					}
				}
				catch (IOException e)
				{
					System.out.println(cmds[0] + ": no se encontró la orden");
				}
			}
		}
		is.close();
	}
}

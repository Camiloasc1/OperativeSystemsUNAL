/**
 * File: Exec.java
 * Package: S.O..consola.Exec
 * Creation: 6/09/2014 at 1:55:39 p. m.
 */

package consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @see <a href="http://alvinalexander.com/java/edu/pj/pj010016">http://alvinalexander.com/java/edu/pj/pj010016</a>
 */
public class Exec
{
	/**
	 * @param cmd
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	static void run(String cmd) throws IllegalArgumentException, IOException
	{
		if (cmd.isEmpty())
			throw new IllegalArgumentException("Empty command");

		String s = null;

		// using the Runtime exec method:
		Process p = Runtime.getRuntime().exec(cmd, null, new File(LocationManager.currDir));

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		// read the output from the command
		// System.out.println("Here is the standard output of the command:\n");
		while ((s = stdInput.readLine()) != null)
		{
			System.out.println(s);
		}

		// read any errors from the attempted command
		// System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null)
		{
			System.err.println(s);
		}
	}
}

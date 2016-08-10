/**
 * File: PWD.java
 * Package: S.O..consola.PWD
 * Creation: 6/09/2014 at 3:40:15 p. m.
 */

package consola;

import java.io.File;

/**
 *
 */
public class LocationManager
{
	static final String user = System.getProperty("user.name");
	static final String os = System.getProperty("os.name") + "-" + System.getProperty("os.arch");
	static final String fileSep = System.getProperty("file.separator");
	static final String homeDir = System.getProperty("user.dir");
	static String currDir = homeDir;
	static String[] dir = currDir.split(fileSep);

	static
	{
		updateDir();
	}

	/**
	 * @return
	 */
	static String cd()
	{
		if (dir.length > 0)
			return dir[dir.length - 1];
		else
			return fileSep;
	}

	/**
	 * @param args
	 */
	static void cd(String[] args)
	{
		if ((args.length > 0) && (args[0] != null))
		{
			switch (args[0])
			{
				case ".":
				{
					break;
				}
				case "..":
				{
					currDir = fileSep;
					for (int i = 1; i < (dir.length - 1); i++)
					{
						currDir += dir[i] + fileSep;
					}
					// System.setProperty("user.dir", currDir);
					break;
				}
				default:
				{
					String newDir = currDir;
					if (!newDir.endsWith(fileSep))
					{
						newDir += fileSep;
					}
					newDir += args[0];
					if (!new File(newDir).exists())
					{
						System.out.println("cd: " + args[0] + ": No existe el fichero o el directorio");
						break;
					}
					currDir = newDir;
					break;
				}
			}
		}
		updateDir();
		return;
	}

	/**
	 *
	 */
	private static void updateDir()
	{
		// String currDir = System.getProperty("user.dir");
		dir = currDir.split(fileSep);
	}
}


package segmentacion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSerializator
{
	public static void serializeObject(Object o, String file) throws FileNotFoundException, IOException
	{
		FileOutputStream fStream = new FileOutputStream(file);
		ObjectOutputStream oStream = new ObjectOutputStream(fStream);

		oStream.writeObject(o);

		fStream.close();
		oStream.close();
		return;
	}

	public static Object deserializeObject(String file) throws FileNotFoundException, IOException
	{
		Object obj = null;
		FileInputStream fStream = new FileInputStream(file);
		ObjectInputStream oStream = new ObjectInputStream(fStream);

		try
		{
			obj = oStream.readObject();
		}
		catch (ClassNotFoundException e)
		{
			// e.printStackTrace();
			System.err.println(e);
		}

		fStream.close();
		oStream.close();
		return obj;
	}
}

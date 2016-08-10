
package barberos;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Class to get a PseudoRandom number specified by
 * <p>
 * (Math.random() * ((max - min) + 1) + min)
 *
 * @author camiloasc1
 */
public class RandomNum
{
	/**
	 * @return
	 *         Random byte
	 */
	public static byte getByte()
	{
		return getByte((byte) (Byte.MAX_VALUE - 1));
	}

	/**
	 * @param max
	 *            Highest number
	 * @return
	 *         Random byte
	 */
	public static byte getByte(byte max)
	{
		return getByte((byte) 0, max);
	}

	/**
	 * @param min
	 *            Lowest number
	 * @param max
	 *            Highest number
	 * @return
	 *         Random byte
	 */
	public static byte getByte(byte min, byte max)
	{
		return (byte) ((Math.random() * ((max - min) + 1)) + min);
	}

	/**
	 * @return
	 *         Random short
	 */
	public static short getShort()
	{
		return getShort((short) (Short.MAX_VALUE - 1));
	}

	/**
	 * @param max
	 *            Highest number
	 * @return
	 *         Random short
	 */
	public static short getShort(short max)
	{
		return getShort((short) 0, max);
	}

	/**
	 * @param min
	 *            Lowest number
	 * @param max
	 *            Highest number
	 * @return
	 *         Random short
	 */
	public static short getShort(short min, short max)
	{
		return (short) ((Math.random() * ((max - min) + 1)) + min);
	}

	/**
	 * @return
	 *         Random integer
	 */
	public static int getInt()
	{
		return getInt(Integer.MAX_VALUE - 1);
	}

	/**
	 * @param max
	 *            Highest number
	 * @return
	 *         Random integer
	 */
	public static int getInt(int max)
	{
		return getInt(0, max);
	}

	/**
	 * @param min
	 *            Lowest number
	 * @param max
	 *            Highest number
	 * @return
	 *         Random integer
	 */
	public static int getInt(int min, int max)
	{
		return (int) ((Math.random() * ((max - min) + 1)) + min);
	}

	/**
	 * @return
	 *         Random long
	 */
	public static long getLong()
	{
		return getLong(Long.MAX_VALUE - 1);
	}

	/**
	 * @param max
	 *            Highest number
	 * @return
	 *         Random long
	 */
	public static long getLong(long max)
	{
		return getLong(0, max);
	}

	/**
	 * @param min
	 *            Lowest number
	 * @param max
	 *            Highest number
	 * @return
	 *         Random long
	 */
	public static long getLong(long min, long max)
	{
		return (long) ((Math.random() * ((max - min) + 1)) + min);
	}

	/**
	 * @return
	 *         Random long
	 */
	public static float getFloat()
	{
		return getFloat(Float.MAX_VALUE - 1);
	}

	/**
	 * @param max
	 *            Highest number
	 * @return
	 *         Random long
	 */
	public static float getFloat(float max)
	{
		return getFloat(0, max);
	}

	/**
	 * @param min
	 *            Lowest number
	 * @param max
	 *            Highest number
	 * @return
	 *         Random long
	 */
	public static float getFloat(float min, float max)
	{
		return (float) ((Math.random() * ((max - min) + 1)) + min);
	}

	/**
	 * @return
	 *         Random long
	 */
	public static double getDouble()
	{
		return getDouble(Double.MAX_VALUE - 1);
	}

	/**
	 * @param max
	 *            Highest number
	 * @return
	 *         Random long
	 */
	public static double getDouble(double max)
	{
		return getDouble(0, max);
	}

	/**
	 * @param min
	 *            Lowest number
	 * @param max
	 *            Highest number
	 * @return
	 *         Random long
	 */
	public static double getDouble(double min, double max)
	{
		return (Math.random() * ((max - min) + 1)) + min;
	}

	/**
	 * @return Random boolean
	 */
	public static boolean getBool()
	{
		return ((getByte((byte) 0, Byte.MAX_VALUE) % 2) == 0);
	}

	/**
	 * @param list
	 * @return Random element in List
	 */
	public static <E> E getListElement(List<E> list)
	{
		if (list.isEmpty())
			return null;
		return list.get(getInt(list.size() - 1));
	}

	/**
	 * @return Random date
	 */
	public static Calendar getDate()
	{
		return getDate(Calendar.getInstance());
	}

	/**
	 * @param max
	 *            Highest date
	 * @return Random date
	 */
	public static Calendar getDate(Calendar max)
	{
		return getDate(new GregorianCalendar(), max);
	}

	/**
	 * @param min
	 *            Lowest date
	 * @param max
	 *            Highest date
	 * @return Random date
	 */
	public static Calendar getDate(Calendar min, Calendar max)
	{
		GregorianCalendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, getInt(min.get(Calendar.YEAR), max.get(Calendar.YEAR)));
		date.set(
				Calendar.DAY_OF_YEAR,
				getInt(((date.get(Calendar.YEAR) == min.get(Calendar.YEAR)) ? min.get(Calendar.DAY_OF_YEAR) : date
						.getActualMinimum(Calendar.DAY_OF_YEAR)),
						((date.get(Calendar.YEAR) == max.get(Calendar.YEAR)) ? max.get(Calendar.DAY_OF_YEAR) : date
								.getActualMaximum(Calendar.DAY_OF_YEAR))));
		date.set(
				Calendar.HOUR_OF_DAY,
				getInt(((date.get(Calendar.DAY_OF_YEAR) == min.get(Calendar.DAY_OF_YEAR)) ? min.get(Calendar.HOUR_OF_DAY)
						: date.getActualMinimum(Calendar.HOUR_OF_DAY)),
						((date.get(Calendar.DAY_OF_YEAR) == max.get(Calendar.DAY_OF_YEAR)) ? max.get(Calendar.HOUR_OF_DAY)
								: date.getActualMaximum(Calendar.HOUR_OF_DAY))));
		date.set(
				Calendar.MINUTE,
				getInt(((date.get(Calendar.HOUR_OF_DAY) == min.get(Calendar.HOUR_OF_DAY)) ? min.get(Calendar.MINUTE) : date
						.getActualMinimum(Calendar.MINUTE)),
						((date.get(Calendar.HOUR_OF_DAY) == max.get(Calendar.HOUR_OF_DAY)) ? max.get(Calendar.MINUTE) : date
								.getActualMaximum(Calendar.MINUTE))));
		date.set(
				Calendar.SECOND,
				getInt(((date.get(Calendar.MINUTE) == min.get(Calendar.MINUTE)) ? min.get(Calendar.SECOND) : date
						.getActualMinimum(Calendar.SECOND)),
						((date.get(Calendar.MINUTE) == max.get(Calendar.MINUTE)) ? max.get(Calendar.SECOND) : date
								.getActualMaximum(Calendar.SECOND))));
		date.set(
				Calendar.MILLISECOND,
				getInt(((date.get(Calendar.SECOND) == min.get(Calendar.SECOND)) ? min.get(Calendar.MILLISECOND) : date
						.getActualMinimum(Calendar.MILLISECOND)),
						((date.get(Calendar.SECOND) == max.get(Calendar.SECOND)) ? max.get(Calendar.MILLISECOND) : date
								.getActualMaximum(Calendar.MILLISECOND))));
		return date;
	}

	/**
	 * @param year
	 *            year
	 * @return Random date
	 */
	public static Calendar getDate(int year)
	{
		return getDate(year, year);
	}

	/**
	 * @param yearFrom
	 *            Lowest year
	 * @param yearTo
	 *            Highest year
	 * @return Random date
	 */
	public static Calendar getDate(int yearFrom, int yearTo)
	{
		GregorianCalendar min = new GregorianCalendar();
		min.set(Calendar.YEAR, yearFrom);
		min.set(Calendar.DAY_OF_YEAR, min.getActualMinimum(Calendar.DAY_OF_YEAR));

		GregorianCalendar max = new GregorianCalendar();
		max.set(Calendar.YEAR, yearTo);
		max.set(Calendar.DAY_OF_YEAR, max.getActualMaximum(Calendar.DAY_OF_YEAR));

		return getDate(min, max);
	}
}

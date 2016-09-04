import java.util.*;
import java.io.*;

public class RandomWords
{
public static void main(Strings args[])
{
genRandomWords(1000000000,"/home/pelatro/Desktop/ganesha.txt");
}
	public static void genRandomWords(int size,String path)
	{
		Random rand = new Random();
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new FileWriter(path));
			for(int i = 0;i<size;i++)
			{
				char[] ca = new char[rand.nextInt(7)+3];
				for(int j=0;j<ca.length;j++)
				{
					ca[j] = (char)('a'+rand.nextInt(26));
				}
				String word = new String(ca);
				bw.write(word);
				bw.newLine();
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(bw!=null)
				try
				{
					bw.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		}		

	}

}

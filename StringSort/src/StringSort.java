import java.io.*;
import java.util.*;

public class StringSort {
    static int N = 1000000; // size of the file in disk
    static int M = 10000;// max items the memory buffer can hold


    public static void externalSort(String fileName) {
        File file = new File(fileName);
        String tfile = "temp-file-";
        String[] buffer = new String[M < N ? M : N];

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            int slices = (int) Math.ceil((double) N / M);

            int i, j;
            i = j = 0;
            // Iterate through the elements in the file
            for (i = 0; i < slices; i++) {
                // Read M-element chunk at a time from the file
                for (j = 0; j < (M < N ? M : N); j++) {
                    String t = br.readLine();
                    if (t != null)
                        buffer[j] = t;
                    else
                        break;
                }
                // Sort M elements
                Arrays.sort(buffer);


                // Write the sorted numbers to temp file
                FileWriter fw = new FileWriter(tfile + Integer.toString(i) + ".txt");
                PrintWriter pw = new PrintWriter(fw);
                for (int k = 0; k < j; k++)
                    pw.println(buffer[k]);

                pw.close();
                fw.close();
            }

            br.close();
            fr.close();

            // Now open each file and merge them, then write back to disk
            String[] topNums = new String[slices];
            BufferedReader[] brs = new BufferedReader[slices];

            for (i = 0; i < slices; i++) {
                brs[i] = new BufferedReader(new FileReader(tfile + Integer.toString(i) + ".txt"));
                String t = brs[i].readLine();
                if (t != null)
                    topNums[i] = t;
                else
                    topNums[i] = "ZZZZZZZZZZ";
            }

            FileWriter fw = new FileWriter("external-sorted.txt");
            PrintWriter pw = new PrintWriter(fw);

            for (i = 0; i < N; i++) {
                String min = topNums[0];
                int minFile = 0;
                Map<String, Integer> tempMap = new TreeMap<>();
                for (j = 0; j < slices; j++) {
                    tempMap.put(topNums[j], j);
                }
                List<String> wordList = new ArrayList<>(tempMap.keySet());
                Collections.sort(wordList);
                min = wordList.get(wordList.size() - 1);
                minFile = tempMap.get(min);

                pw.println(min);
                String t = brs[minFile].readLine();
                if (t != null)
                    topNums[minFile] = t;
                else
                    topNums[minFile] = "ZZZZZZZZZZ";

                tempMap.clear();
            }
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            for (i = 0; i < slices; i++)
                brs[i].close();

            pw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        externalSort("Listofwords.txt");
        System.out.println("*****");
    }
}

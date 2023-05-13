//Jake Li 1320187

import java.io.*;

public class KMPtable {
    public static void main(String[] args) {
        // Get the search string from command line arguments
        String searchStr = args[0];

        // Split the search string into an array of individual characters
        String[] searchStrArray = searchStr.split("");

        // Find the unique characters in the search string
        String uniqueCharStr = "";
        for (int i = 0; i < searchStrArray.length; i++) {
            if (!uniqueCharStr.contains(searchStrArray[i])) {
                uniqueCharStr += searchStrArray[i];
            }
        }
        String[] uniqueCharacters = uniqueCharStr.split("");

        // Create a 2D array to store the skip values
        int[][] skipArray = new int[searchStrArray.length][uniqueCharacters.length];

        // Calculate the skip values for each character in the search string
        for (int i = 0; i < searchStrArray.length; i++) {
            // Get the context of the current row
            String rowContext = searchStr.substring(0, i);
            // Get the pattern up to the current character
            String pattern = searchStr.substring(0, i + 1);
            for (int j = 0; j < uniqueCharacters.length; j++) {
                int skipValue;
                // If the current character matches the current unique character, skip value is
                // 0
                if (uniqueCharacters[j].compareTo(searchStrArray[i]) == 0) {
                    skipValue = 0;
                }
                // If the current character is the first character, skip value is 1
                else if (i == 0) {
                    skipValue = 1;
                }
                // Otherwise, calculate the skip value based on the context and pattern
                else {
                    String columnContext = rowContext + uniqueCharacters[j];
                    int skipCount = 0;
                    while (skipCount <= i) {
                        String tempContext = columnContext.substring(skipCount, columnContext.length());
                        String tempPattern = pattern.substring(0, pattern.length() - skipCount);
                        if (!tempContext.equals(tempPattern)) {
                            skipCount++;
                        } else
                            break;
                    }
                    skipValue = skipCount;
                }
                // Store the skip value in the skip array
                skipArray[i][j] = skipValue;
            }
        }

        // Output the skip array in a tabular format
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            BufferedWriter writer2 = new BufferedWriter(new FileWriter("output.kmp"));
            String out = "\t";
            for (String s : searchStrArray) {
                out += s + " ";
            }
            writer.write(out);
            writer.newLine();
            writer2.write(out);
            writer2.newLine();
            for (int j = 0; j < uniqueCharacters.length; j++) {
                out = uniqueCharacters[j] + "\t";
                for (int i = 0; i < searchStrArray.length; i++) {
                    out += Integer.toString(skipArray[i][j]) + " ";
                }
                writer.write(out);
                writer.newLine();
                writer2.write(out);
                writer2.newLine();
            }
            out = "*\t";
            for (int p = 0; p < searchStrArray.length; p++) {
                out += (p + 1) + " ";
            }
            writer.write(out);
            writer.newLine();
            writer.flush();
            writer.close();
            writer2.write(out);
            writer2.newLine();
            writer2.flush();
            writer2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
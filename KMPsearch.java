//Jake Li 1320187
import java.io.*;

public class KMPsearch {

    public static void main(String[] args) {
        // Check that the program was called with the correct number of arguments
        if (args.length != 2) {
            System.err.println("Usage: java KMPsearch <skipArrayFile> <textFile>");
            System.exit(1);
        }

        // Get the names of the skip array and text files from the command line arguments
        String skipArrayFile = args[0];
        String textFile = args[1];

        // Read the skip array from the skip array file
        int[][] skipArray = readSkipArray(skipArrayFile);

        // Search for the pattern in the text file using the skip array
        search(skipArray, textFile);
    }

    private static int[][] readSkipArray(String skipArrayFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(skipArrayFile))) {
            String line;
            int[][] skipArray = new int[256][];
            int count = 0;
            // Read each line of the skip array file
            while ((line = br.readLine()) != null) {
                count++;
                if (count == 1) {
                    // Skip the first line
                    continue;
                }
    
                // Split the line into an array of skip values
                String[] splitLine = line.split(" ");
                int[] skipValues = new int[splitLine.length - 1];
                for (int i = 1; i < splitLine.length; i++) {
                    skipValues[i - 1] = Integer.parseInt(splitLine[i]);
                }
                // Set the skip values for the character corresponding to the first character of the line
                skipArray[splitLine[0].charAt(0)] = skipValues;
                count++;
            }
            // Set skip values for all other characters to skipArray["*"]
            skipArray['*'] = skipArray[0];
            return skipArray;
        } catch (IOException e) {
            System.err.println("Error reading skip array file: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static void search(int[][] skipArray, String textFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;
            int lineNumber = 0;
            // Read each line of the text file
            while ((line = br.readLine()) != null) {
                lineNumber++;
                int lineIndex = 0;
                // Search for the pattern in the line
                while (lineIndex < line.length()) {
                    int i = 0;
                    // Compare characters in the text to the pattern
                    while (i < skipArray.length && lineIndex + i < line.length() && 
                            skipArray[line.charAt(lineIndex + i)][i] == i + 1) {
                        i++;
                    }
                    if (i == skipArray.length) {
                        // Found a match
                        System.out.println(lineNumber + " " + (lineIndex + 1));
                        break;
                    } else {
                        // Skip ahead in the text based on the skip array
                        lineIndex += Math.max(1, i - skipArray[line.charAt(lineIndex + i)][i]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading text file: " + e.getMessage());
            System.exit(1);
        }
    }
}
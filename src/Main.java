import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        String wordsFile = "src/data/exampleThree.txt";
        String testFile  = "src/data/testThree.txt";

        BufferedReader r =
                new BufferedReader(new InputStreamReader(new FileInputStream(wordsFile)));

        ArrayList<String> words = new ArrayList<>();
        while (true) {
            String word = r.readLine();
            if (word == null) break;
            if (word.length() != 5) {
                throw new IllegalArgumentException("Word must be 5 characters: " + word);
            }
            words.add(word);
        }

        WordGraphPaths solver = new WordGraphPaths(words);

        r = new BufferedReader(new InputStreamReader(new FileInputStream(testFile)));
        while (true) {
            String line = r.readLine();
            if (line == null) break;
            if (line.length() != 11) {
                throw new IllegalArgumentException("Test words must be 11 characters (including space in between): " + line);
            }

            String start = line.substring(0, 5);
            String goal  = line.substring(6, 11);

            System.out.println(solver.Dijkstra(start, goal));
        }
    }
}

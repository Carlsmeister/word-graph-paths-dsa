import edu.princeton.cs.algs4.*;
import java.util.*;

public class WordGraphPaths {
    private HashMap<String, List<String>> combinations = new HashMap<>();
    private Map<String, Integer> idOf = new HashMap<>();
    private EdgeWeightedDigraph graph;

    public WordGraphPaths(List<String> words) {
        int v = words.size();
        this.graph = new EdgeWeightedDigraph(v);

        for (int i = 0; i < v; i++) {
            idOf.put(words.get(i), i);
        }

        for (String word : words) {
            char[] keyLetters = word.substring(1, 5).toCharArray();
            Arrays.sort(keyLetters);
            String sortedkey = new String(keyLetters);
            combinations
                    .computeIfAbsent(sortedkey, k -> new ArrayList<>())
                    .add(word);
        }
        buildGraph(words);
    }

    private void buildGraph(List<String> words) {
        for (String targetWord : words) {
            int targetId = idOf.get(targetWord);
            char[] targetLetters = targetWord.toCharArray();

            for (int i = 0; i < 5; i++) {

                StringBuilder subsetBuilder = new StringBuilder(4);
                for (int j = 0; j < 5; j++) {
                    if (j != i) subsetBuilder.append(targetLetters[j]);
                }
                char[] subsetLetters = subsetBuilder.toString().toCharArray();
                Arrays.sort(subsetLetters);
                String subsetKey = new String(subsetLetters);

                if (combinations.containsKey(subsetKey)) {
                    for (String sourceWord : combinations.get(subsetKey)) {
                        if (!sourceWord.equals(targetWord)) {
                            int sourceId = idOf.get(sourceWord);
                            int weight = 1 + Math.abs(targetWord.charAt(4) - sourceWord.charAt(4));
                            DirectedEdge edge = new DirectedEdge(sourceId, targetId, weight);
                            graph.addEdge(edge);
                        }
                    }
                }
            }
        }
    }

    public int Dijkstra(String start, String goal) {
        if (!idOf.containsKey(start) || !idOf.containsKey(goal)) {
            return -1;
        }

        int startWord = idOf.get(start);
        int goalWord = idOf.get(goal);

        DijkstraSP dijkstra = new DijkstraSP(graph, startWord);

        if (!dijkstra.hasPathTo(goalWord)) {
            return -1; // No path found
        }
        return (int) dijkstra.distTo(goalWord);
    }

}

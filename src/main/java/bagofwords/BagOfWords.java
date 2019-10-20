package bagofwords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BagOfWords {
    private List<List<String>> wordsFromText;
    private List<List<Integer>> bagOfWords;
    private Map<String, Integer> bagKeeper = new HashMap<String, Integer>();

    public BagOfWords(List<List<String>> wordsFromTexts) {
        this.wordsFromText = wordsFromTexts;
    }

    public List<List<Integer>> createBag() {
        List<List<Integer>> bag = new ArrayList<List<Integer>>();
        Map<String, Integer> bagCounter = new HashMap<String, Integer>();
        fillMaxBag();
        for (List<String> file : wordsFromText) {
            for (String word : file) {
                bagKeeper.put(word, bagKeeper.get(word) + 1);
            }
            bag.add(new ArrayList<Integer>());
            for (String key : bagKeeper.keySet()) {
                bag.get(bag.size() - 1).add(bagKeeper.get(key));
            }
            //empty Keeper before next file iteration
            for (String key : bagKeeper.keySet()) {
                bagKeeper.put(key, 0);
            }
        }
        return bag;
    }

    private void fillMaxBag() {
        for (List<String> file : wordsFromText) {
            for (String word : file) {
                bagKeeper.put(word, 0);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("erwer");
        List<List<String>> inputData = new ArrayList<List<String>>();
        inputData.add(new ArrayList<String>());
        inputData.get(0).add("мама");
        inputData.add(new ArrayList<String>());
        inputData.get(1).add("мама");
        inputData.get(1).add("мыла");
        inputData.get(1).add("мыла");
        inputData.get(1).add("мама");
        inputData.add(new ArrayList<String>());
        inputData.get(2).add("вася");

        BagOfWords test = new BagOfWords(inputData);
        List<List<Integer>> outputData = test.createBag();
        for (List<Integer> row : outputData) {
            for (Integer value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

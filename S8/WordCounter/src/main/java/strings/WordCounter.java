package strings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {

    private static HashMap<String, Integer> wordCount;

    public WordCounter() {
        wordCount = new HashMap<>();
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        if (wordCount.containsKey(word)) {
            wordCount.put(word, wordCount.get(word) + 1);
        } else {
            wordCount.put(word, 1);
        }
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
        if (!wordCount.containsKey(word)) {
            return 0;
        }
        return wordCount.get(word);
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
        //To iterate over the HashMap in ascending order, we need to "sort" it, so we can just go over it, since TreeMap is a sorted HashMap, we can just use what Java offers
        Map<String, Integer> sortedMap = new TreeMap<>(wordCount);
        return sortedMap.keySet().iterator();
    }
}

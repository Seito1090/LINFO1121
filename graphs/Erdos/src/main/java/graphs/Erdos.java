package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * The erdos number is a "collaborative distance" metric to Paul Erdos (a prolific mathematician)
 * based on co-authorship of mathematical articles.
 * It is computed as follows:
 * - Erdos has, by definition an erdos-number of 0.
 * - For each other author, we look at all his/her co-authors in each article.
 *   If n is the minimum erdos-number from all his co-authors, then this author has an erdos-number of n+1.
 *
 * For example:
 *
 * Given this set of co-authors relations:
 *
 * 		{ "Paul Erdös", "Edsger W. Dijkstra" }
 * 		{ "Edsger W. Dijkstra", "Alan M. Turing" }
 * 		{ "Edsger W. Dijkstra", "Donald Knuth" }
 * 		{ "Donald Knuth", "Stephen Cook", "Judea Pearl" }
 *
 * 	The erdos number of Paul Erdos is 0, of Edsger W. Dijkstra is 1, of Alan M. Turing is 2, of Donald Knuth is 2, of Stephen Cook is 3.
 *
 * 	Debug your code on the small examples in the test suite.
 */
public class Erdos {

	public static final String erdos = "Paul Erdös";

	public HashMap<String, Integer> distances = new HashMap<>();

	class ErdosAuthor{
		String name;
		int distance;

		ErdosAuthor(String name, int distance){
			this.name = name;
			this.distance = distance;
		}
	}
	/**
	 * Constructs an Erdos object and computes the Erdős numbers for each author.
	 *
	 * The constructor should run in O(n*m^2) where n is the number of co-author relations,
	 * and m the maximum number of co-authors in one article.
	 *
	 * @param articlesAuthors An ArrayList of String arrays, where each array represents the list of authors of a single article.
	 */
	public Erdos(ArrayList<String []> articlesAuthors) {
		HashMap<String, HashSet<String>> temp = new HashMap<>();

		for (String[] line : articlesAuthors){
			for (int x = 0; x < line.length; x++){
				for (int y = x+1; y < line.length; y++){
					if (!temp.containsKey(line[x])) temp.put(line[x], new HashSet<>());
					if (!temp.containsKey(line[y])) temp.put(line[y], new HashSet<>());
					temp.get(line[x]).add(line[y]);
					temp.get(line[y]).add(line[x]);
				}
			}
		}

		/* compute the distances */
		LinkedList<ErdosAuthor> authors = new LinkedList<>();
		authors.add(new ErdosAuthor(erdos, 0));

		while(!authors.isEmpty()){
			/* get the author on top */
			ErdosAuthor current = authors.pop();
			if (!distances.containsKey(current.name)){
				this.distances.put(current.name, current.distance);
				/* add the co authors to be checked */
				for (String author : temp.get(current.name)){
					if (!distances.containsKey(author)){
						authors.add(new ErdosAuthor(author, current.distance+1));
					}
				}
			}
		}

	}

	/**
	 * Returns the Erdős number of a given author.
	 * This method is expected to run in O(1).
	 * @param author The name of the author whose Erdős number is to be found.
	 * @return The Erdős number of the specified author. If the author is not in the network, returns -1.
	 */
	public int findErdosNumber(String author) {
		/* Since he wants a O(1) lookup time a hashmap it is */
		return this.distances.get(author);
	}

}

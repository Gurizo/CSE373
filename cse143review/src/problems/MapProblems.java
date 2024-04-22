package problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        if (list.isEmpty()) {
            return false;
        }
        Map<String, Integer> count = new HashMap<>();
        for (String key : list) {
            if (!count.containsKey(key)) {
                count.put(key, 1);
            } else {
                count.put(key, count.get(key)+1);
            }
        }
        for (String words: count.keySet()) {
            if (count.get(words) >= 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> result = new HashMap<>();
        //for same key, compare value, if same put in the result
        for (String keys : m1.keySet()) {
            if (m2.containsKey(keys) && (m1.get(keys) == m2.get(keys))) {
                result.put(keys, m1.get(keys));
            }
        }
        return result;
    }
}

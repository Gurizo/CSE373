package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        list.front.next.next.next = list.front.next;
        ListNode temp = list.front.next.next;
        temp.next.next = list.front;
        list.front.next = null;
        list.front = temp;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null && list.front.next != null) {
            ListNode temp = list.front;
            list.front = temp.next;
            ListNode current = list.front;
            while (current.next != null) {
                current = current.next;
            }
            temp.next = null;
            current.next = temp;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        // Hint: you'll need to use the 'new' keyword to construct new objects.
        if (a.front == null) {
            return b;
        } else if (b.front == null) {
            return a;
        } else {
            LinkedIntList result = new LinkedIntList();
            ListNode current = a.front;
            while (current != null) {
                result = append(result, current);
                current = current.next;
            }
            current = b.front;
            while (current != null) {
                result = append(result, current);
                current = current.next;
            }
            return result;
        }
    }

    public static LinkedIntList append(LinkedIntList result, ListNode input) {
        ListNode temp = new ListNode(input.data);
        if (result.front == null) {
            result.front = temp;
        } else {
            ListNode temp2 = result.front;
            while (temp2.next != null) {
                temp2 = temp2.next;
            }
            temp2.next = temp;
        }
        return result;
    }
}

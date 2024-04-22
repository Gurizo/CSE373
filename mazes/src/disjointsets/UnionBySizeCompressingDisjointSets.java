package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.List;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers; //either a negative number if the element is a root, or it’s the index of the parent
    HashMap<T, Integer> indexPointer; //The id of a node is the index of its root node.
    int size;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        this.pointers = new ArrayList<>(15);
        indexPointer = new HashMap<>();
        size = 0;
    }

    @Override
    public void makeSet(T item) {
        if (!indexPointer.containsKey(item)) {
            pointers.add(-1); //index of each item in int array
            indexPointer.put(item, size); //index of each item that directs to pointers
            this.size++;
        }
    }

    @Override
    public int findSet(T item) {
        if (indexPointer.containsKey(item)) {
            //List<Integer> indexToCompress = new ArrayList<>(this.pointers.size());
            int currentIndex = indexPointer.get(item); //get index of the item
            while (pointers.get(currentIndex) > -1) {
                //indexToCompress.add(currentIndex); //add index to compress when +
                currentIndex = pointers.get(currentIndex);
                // if (pointers.get(currentIndex) < 0) {
                //     return pathCompress(indexToCompress, currentIndex);
                // }
            }
            int rootIndex = currentIndex;
            currentIndex = indexPointer.get(item);
            while (pointers.get(currentIndex) > -1) {
                int temp = pointers.get(currentIndex); //next thing
                this.pointers.set(currentIndex, rootIndex); //setting root to current index
                currentIndex = temp;
            }
            //pathCompress(indexToCompress, currentIndex);
            return currentIndex;
        } else {
            throw new IllegalArgumentException(); //or return -1;
        }
    }

    // private void pathCompress(List<Integer> indexToCompress, int rootIndex) {
    //     for (int index : indexToCompress) {
    //         this.pointers.set(index, rootIndex);
    //     }
    //     //return rootIndex;
    // }

    @Override
    public boolean union(T item1, T item2) { //here we compress the trees + change the weights
        int parent1 = findSet(item1);
        int parent2 = findSet(item2);
        if (parent1 != parent2) {
            if (Math.abs(pointers.get(parent1)) >= Math.abs(pointers.get(parent2))) { //weight comparison
                unionHelper(parent1, parent2);
            } else {
                unionHelper(parent2, parent1);
            }
            return true;
        }
        return false;
    }

    private void unionHelper(int largerIndex, int smallerIndex) {
        //int largerIndex = indexPointer.get(largerIndex);
        //int smallerIndex = indexPointer.get(smallerIndex);
        // add weight on largerItem
        pointers.set(largerIndex, pointers.get(largerIndex) + pointers.get(smallerIndex));
        // change the index of the parent of the smallerItem
        //indexPointer.put(smallerItem, largerIndex);
        pointers.set(smallerIndex, largerIndex);
    }
}

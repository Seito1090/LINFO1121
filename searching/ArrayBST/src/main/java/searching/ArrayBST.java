package searching;

import java.util.ArrayList;


/**
 *  We study a BST representation using an arrayList internal representation.
 *  Rather than using a Linked-Node Data-Structure, the left/right children
 *  will be encoded with indices in array lists.
 *  More exactly, in this data-structure each node
 *  is represented by an index i (that correspond to the ith added element)
 *  The left  node of node i, if any, is located
 *        at index idxLeftNode.get(i) otherwise idxLeftNode.get(i) == NONE
 *  The right node of node i, if any is located
 *       at index idxRightNode.get(i) otherwise idxRightNode.get(i) == NONE
 *
 *  The tree below is the result of putting (key,value) pairs (12,A),(15,B),(5,C),(8,d),(1,E) (in this order)
 *
 *                12(A)
 *                / \
 *               /   \
 *             5(C)  15(B)
 *             / \
 *          1(E)  8(D)
 *
 *   The state of internal array list after those put operations is
 *    values:        A, B, C, D, E
 *    keys:        12, 15, 5, 8, 1
 *    idxLeftNode:  2, -1, 4,-1,-1
 *    idxRightNode: 1, -1, 3,-1,-1
 *
 *  You can implement the get method before the put method if you prefer since
 *  the two methods will be graded separately.
 *
 *  You cannot add of change the fields already declared, nor change
 *  the signatures of the methods in this
 *  class but feel free to add methods if needed.
 *
 */
public class ArrayBST<Key extends Comparable<Key>, Value> {

    // The next four array lists should always have exactly equal size after a put

    public ArrayList<Key> keys;
    public ArrayList<Value> values;

    public ArrayList<Integer> idxLeftNode; // idxLeftNode[i] = index of left node of i
    public ArrayList<Integer> idxRightNode; // idxRightNode[i] = index of right node of i

    final int NONE = -1;

    public ArrayBST() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxLeftNode = new ArrayList<>();
        idxRightNode = new ArrayList<>();
    }

    /**
     * Insert the entry in the BST, replace the value if the key is already present
     * in O(h) where h is the height of the tree
     * @param key a key that is present or not in the BST
     * @param val the value that must be attached to this key
     * @return true if the key was added, false if already present and the value has simply been erased
     */
    public boolean put(Key key, Value val) {
        if (this.keys.size() == 0) {
            this.values.add(val);
            this.keys.add(key);
            this.idxLeftNode.add(NONE);
            this.idxRightNode.add(NONE);
            return true;
        }
        return myput(key, val, this.keys.get(0));
    }

    private boolean myput(Key key, Value val, Key root){
        int idx = this.keys.indexOf(root);
        int cmp = key.compareTo(root);
        if (cmp == 0) {
            this.values.set(idx, val);
            return false;
        } else if (cmp < 0){ 
            /* Check if there is a node on the left, if not create it */
            if (this.idxLeftNode.get(idx) != NONE) return myput(key, val, this.keys.get(this.idxLeftNode.get(idx)));
            else {
                int newIdx = createNew(key, val, idx);
                this.idxLeftNode.set(idx, newIdx);
            }
        } else {
            if (this.idxRightNode.get(idx) != NONE) return myput(key, val, this.keys.get(this.idxRightNode.get(idx)));
            else {
                int newIdx = createNew(key, val, idx);
                this.idxRightNode.set(idx, newIdx);
            }
        }
        return true;
    }

    private int createNew(Key key, Value val, int idx){
        this.values.add(val);
        this.keys.add(key);
        this.idxLeftNode.add(NONE);
        this.idxRightNode.add(NONE);
        int newIdx = this.values.indexOf(val);
        return newIdx;
    }

    private void swapKeysVals(int idx0, int idx1){
        Key tmpKey = this.keys.get(idx0);
        Value tmpVal = this.values.get(idx0);
        this.keys.set(idx0, this.keys.get(idx1));
        this.values.set(idx0, this.values.get(idx1));
        this.keys.set(idx1, tmpKey);
        this.values.set(idx0, tmpVal);
    }

    /**
     * Return the value attached to this key, null if the key is not present
     * in O(h) where h is the height of the tree
     * @param key
     * @return the value attached to this key, null if the key is not present
     */
    public Value get(Key key) {
        int idx = 0;
        Key currentKey = this.keys.get(idx); // Root 
        while (this.idxLeftNode.get(idx) != NONE && this.idxRightNode.get(idx) != NONE){   
            if (key.equals(currentKey)) return this.values.get(idx);
            else if (key.compareTo(currentKey) < 0 && this.idxLeftNode.get(idx) != NONE) {
                idx = this.idxLeftNode.get(idx);
                currentKey = this.keys.get(idx);
            } else {
                if (this.idxRightNode.get(idx) != NONE) {
                idx = this.idxRightNode.get(idx);
                currentKey = this.keys.get(idx);
                }
            }
        }
        if (key.equals(currentKey)) return this.values.get(idx);
        if (key.compareTo(currentKey) < 0 && this.idxLeftNode.get(idx) != NONE) return key.equals(this.values.get(this.idxLeftNode.get(idx))) ? this.values.get(this.idxLeftNode.get(idx)) : null; 
        if (key.compareTo(currentKey) > 0 && this.idxRightNode.get(idx) != NONE) return key.equals(this.values.get(this.idxRightNode.get(idx))) ? this.values.get(this.idxRightNode.get(idx)) : null; 
        return null;
    }
}

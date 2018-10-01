/**
 * 	Implementation of Binary Search Tree
 *  @author Sai Krishna Reddy Syamala (sxs169430)
 *  @author Sreyas Reddy Dadi (sxd180049)
 *  Ver 1.0: 09/30/2018
 */

package sxs169430;

import java.util.Iterator;
/**
 * Implementation of the Binary Search Tree data structure.
 * Doesn't store duplicate elements
 * @param <T> Type of the element to be stored.
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;
    
        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
	    this.left = left;
	    this.right = right;
        }
    }
    
    Entry<T> root,curr;
    int size;

    public BinarySearchTree() {
	root = null;
	size = 0;
    }

    /** TO DO: Is x contained in tree?
     * 
     * @param x - element to be searched for
     * @return - true if element x is present in the tree, false otherwise.
     */
    public boolean contains(T x) {
    	Entry<T> temp = find(root,x);
    	return((temp != null) && (temp.element.compareTo(x) == 0));
    }
    
    
    /** TO DO: Is there an element that is equal to x in the tree?
     *  @param x - element to be searched for
     *  Element in tree that is equal to x is returned, null otherwise.
     */
    
    public T get(T x) {
     	Entry<T> temp = find(root,x);
    	if(temp != null){
    		return (temp.element == x)?temp.element:null;
    	} else{
    		return null;
    	}
    }
    
    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
    	Entry<T> node = new Entry<T>(x, null, null);
    	if(root== null) {
    		root = node;
			size = 1;
			return true;
    	}
    	T ele = node.element;
    	Entry<T> temp = find(root,ele);
		 if(ele.compareTo(temp.element) == 0){
			 temp.element = ele; // replace
			 return false; // return false as the element already exists
		 } else if(ele.compareTo(temp.element) == -1){ 
			 		temp.left = node;
		 }else{
			 temp.right = node;
		 }
		 	size++; 
		return true;
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
    	Entry<T> temp = remove(root, x);
    	if(temp == null) {
    		return null;
    	}
    	size--;
    	return temp.element;
    }
    /**
     * 
     * @param root - search for x from root 
     * @param x - element to be removed
     * @return - entry removed
     */
    private Entry<T> remove(Entry<T> root, T x){
    	
    	if(root==null) {
    	return null;
    }
    Entry<T> curr = root;
    Entry<T> parent = null;
    while(curr != null && curr.element != x) {
    	//store the parent node
    	parent = curr;
    	if(x.compareTo(curr.element) < 0) {
    		curr =curr.left;
    	}
    	else {
    		curr= curr.right;
    	}
    }
    // if key is not found, return null
    if(curr == null) {
    	return null;
    }
    // if the node to be deleted has no children
    if(curr.left == null && curr.right == null) {
    	// if the node to be deleted is a root node
    	if(curr == root) {
    		root = null;
    	}
    	else
    	{
    		if(parent.left==curr) {
    			parent.left=null;
    		}
    		else {
    			parent.right=null;
    		}
    	}
    }
    // if the node to be deleted has two children
    else if(curr.left != null && curr.right != null) {
    	//find the successor node
    	Entry<T> successor = minKey(curr.right);
    	T val = successor.element;
    	//delete the successor
    	remove(root,successor.element);
    	curr.element=val;
    }
    // if the node to be deleted has only one child
    else {
    	Entry<T> child = (curr.left != null) ? curr.left : curr.right;
    	if(curr != root) {
    		if(curr == parent.left) {
    			parent.left=child;
    		}else {
    			parent.right=child;
    		}
    	}
    	else {
    		root = child;
    	}
    }
    return curr;
    }
    //function to find the minimum element in the left sub tree
    private Entry<T> minKey(Entry<T> p)
    {
       while (p.left != null)
    	   p = p.left;

       return p;
    }
    /**
     * find the minimum element in the tree
     * @return minimum element in the tree, null if the tree is empty
     */
    public T min() {
    	if(root == null) {
			return null;
		}
		Entry<T> temp = root;
		while(temp.left != null){
			temp = temp.left;
		}
		return temp.element;  	    	
    }
    /**
     * find the maximum element in the tree
     * @return maximum element in the tree, null if the tree is empty
     */
    public T max() {
    	if(root == null){
			return null;
		}
		Entry<T> temp = root;
		while(temp.right != null){
			temp = temp.right;
		}
		return temp.element;
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
	Comparable[] arr = new Comparable[size];
	/* write code to place elements in array here */
	int index = 0;
	inOrder(root, arr, index);
	return arr;
    }
    /**
     * Recursive in-order traversal of tree rooted at "node".
     * "index" is next element of array to be written.
     * Returns index of next entry of arr to be written.
     */
    private int inOrder(Entry<T> root, Comparable<T>[] arr, int index) {
		if (root != null) {
			index = inOrder(root.left, arr, index);
			arr[index++] = (Comparable) root.element;
			index = inOrder(root.right, arr, index);
		}
		return index;
	}
    /**
     * helper function to find the element x in the tree
     * @param root - search from root of the tree
     * @param x - element to be searched for
     * @return - Entry if element is found and null otherwise
     */
    private Entry<T> find(Entry<T> root,T x) {
    	
    	Entry<T> temp = root;
			if (temp == null || temp.element == x){
				return temp;
			}
		 while(true){
			 if(x.compareTo(temp.element) < 0){
				 if (temp.left == null){
					 break;
				 }else {
					 temp = temp.left;
					 }
				 } else if(x.compareTo(temp.element) == 0){
				  break;
			 } else{
				 // x > t.element
				 if(temp.right == null){
					 break;
				 } else { 
					 temp = temp.right;
					 }
				 }
		 	}
		 	return temp;
    }

    

    public void printTree() {
	System.out.print("[" + size + "]");
	printTree(root);
	System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
	if(node != null) {
	    printTree(node.left);
	    System.out.print(" " + node.element);
	    printTree(node.right);
	}
    }


	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}

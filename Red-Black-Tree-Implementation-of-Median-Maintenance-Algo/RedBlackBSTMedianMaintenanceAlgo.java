/*
 *	Red Black Tree.java
 *
 *  javac RedBlackBSTMedianMaintenanceAlgo.java
 *  java RedBlackTreeMedianMaintenanceAlgo
 *	The answer is 1213.
 */

import java.util.*;
import java.io.*;
import java.math.*;

class RedBlackBST{

    Node root;
    Node nil;

    public RedBlackBST(){
        root = new Node();
         nil = new Node();
    }

    // number of nodes in the Red black tree
    public int size(){
        return size(root);
    }

    /*
     *	overloading - number of nodes in the subtree of node X
     *
     *	The size method is needed for Selecting the i^th Order Statistics
     */

    public int size(Node x){
        if(x == null){
            return 0;
        } else{
            return x.N;

        }
    }

    /*
     *	Selection method in BST
     *
     * input parameter i^th order statistic
     *
     */
    public Node select(int i){
        return select(root, i);
    }

    public Node select(Node x, int i){
        Node y = x.left;
        Node z = x.right;

        if(x.isNull() || x.equals(nil)) return null;
        int a = size(y);			// a = 0 if x has no left child
        if(a == i-1){
            return x;
        } else if(a >= i){
            return select(y,i);
        } else if(a < i - 1){
            return select(z, i-(a+1));
        }
        return null;
    }

    /*
     *	Inorder Tree Walk
     *
     *	prints the key of the root of a subtree between printing the
     *	values in its left subtree and printing those in its right subtree
     */
       public void inorderTreeWalk(Node x){
           if(!x.equals(nil)){
               inorderTreeWalk(x.left);
               System.out.println("x.key = " + x.key + ", x.color = " + x.color + ", size = " + size(x) + ", x.left = " + x.left + ", x.right = " + x.right + ", x.p = " + x.p);
               inorderTreeWalk(x.right);
           }
    }

    // Querying a BST

    /*
     *	Recursive Tree Search
     *
     *	Tree Search returns a pointer to a node with key k if one exists;
     *	otherwise, it returns nil.
     *
     */
    public Node treeSearch(Node x, Long k){
        if(x.equals(nil) || k == x.key){
            return x;
        }
        if(k < x.key){
            return treeSearch(x.left, k);
        } else{
            return treeSearch(x.right, k);
        }
    }

    public void UpdateSize(Node x, Long k){

        if(k < x.key && x.left != null){
            UpdateSize(x.left,k);
        }
        if(k > x.key && x.right != null){
            UpdateSize(x.right, k);
        }
        x.N = size(x.left) + size(x.right) + 1;

        //  System.out.println("x = " +  x + ", size(x) = " + size(x) + ", x.left = " + x.left + ", size(left) = " + size(x.left) + ", right = " + x.right + ", size(right) = " + size(x.right) + ", size(root) = " + size());
        //System.out.println("");
    }
    /*
     *	Find an element in a BST whose key is minimum by
     *	following left child pointers from the root until we encounter
     *	a NIL
     *
     */
    public Node treeMinimum(Node x){
        while(!x.left.equals(nil)){
            x = x.left;
        }
        return x;
    }

    // Tree Maximum is symmetric
    public Node treeMaximum(Node x){
        while(!x.right.equals(nil)){
            x = x.right;
        }
        return x;
    }

    // Only pointers are changed by a rotation, all other attributes remain the same.
    public void leftRotate(Node x){
        Node y = x.right;			// set y
        x.right = y.left;		// turn y's left subtree into x's right subtree

        if( !y.left.equals(nil)){
        y.left.p = x;
        }
        y.p = x.p;			// link x's parent to y
        if(x.p.equals(nil)){
            root = y;
        } else if(x.equals(x.p.left)){	// if x is its parents' left child
            x.p.left = y;
        } else{
            x.p.right = y;
        }
        y.left = x;			// put x on y's left
        x.p = y;

        y.N = x.N;
        x.N = size(x.left) + size(x.right) + 1;
    }

    public void rightRotate(Node x){
        Node y = x.left;			// define y
        x.left = y.right;		// turn y's right subtree into x's left subtree

        if( !y.right.equals(nil) ){
            y.right.p = x;
        }
        y.p = x.p;			// link x's parent to y
        if(x.p.equals(nil)){
            root = y;
        } else if(x.equals(x.p.right)){
            x.p.right = y;
        } else{
            x.p.left = y;
        }
        y.right = x;			// put x on y's right
        x.p = y;

        y.N = x.N;
        x.N = size(x.left) + size(x.right) + 1;
    }

    /*
     *	Insertion begins at the root of the tree
     *  and the pointer x traces a simple path downward
     *  looking for a nil to replace with the input item z.
     *
     *	The method also maintains a trailing pointer y as
     * 	the parent of x.
     *
     *	Within the while loop,these two pointers will
     *	move down the tree, going left or right depending on
     *	the comparison of z.key with x.key, until x becomes nil.
     *
     *	This nil occupies the position where we wish to place the
     *	input item z.
     *
     * 	The trailing pointer is needed because by the time we find
     *	the NIL where z belongs, the search has proceeded one step
     * 	beyond the node that needs to be changed.
     *
     */
    public void rBInsert(Node z){
        Node y = nil;
        Node x = root;
        while(!x.equals(nil)){
            y = x;
            if(z.key < x.key){
                x = x.left;
            } else{
                x = x.right;
            }
        }
        z.p = y;
        if(y.equals(nil)){
            root = z;
        } else if(z.key < y.key){
            y.left = z;
        } else {
            y.right = z;
        }

        /*	So far, the above is exactly the same as BST.
         *	Below are the new things for Red Black BST.
         *
         *	Set them to nil to maintain the proper tree structure.
         *	Coloring node z RED may cause a violation of exactly one
         *	of the red-black properties, we call rbInsert-FixedUp to
         *	restore red-black properties.
         */
        z.left = nil;
        z.right = nil;
        z.color = Color.RED;
        // maintain size invariant
        rBInsertFixUP(z);

        z.N = size(z.left) + size(z.right) + 1;
        UpdateSize(root, z.key);
    }

    public void rBInsertFixUP(Node z){

        while(z.p.color.equals(Color.RED)){
            /*
             *	if z's parent (z.p) is left child of z's grandparent (z.p.p)
             *
             *	In all 3 cases, z.p.p's color = black, since z.p's color = Color.RED
             *	and property 4 violated (z.p and z are Color.RED)
             */
            if(z.p.equals(z.p.p.left)){
                Node y = z.p.p.right;

                if(y.color.equals(Color.RED)){
                    /*
                     *	Case 1 -
                     *
                     *	differentiate case 1 from case 2 and 3 by
                     *	color of z's parent's sibling "uncle" (z.p.p.right)
                     */
                    z.p.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    z = z.p.p;

                } else{
                    if(z.equals(z.p.right)){
                        /*
                         *	Case 2 -
                         *
                         *	if z is right child of z's parent (z.p),
                         *	do left rotation to transform to Case 3
                         */
                        z = z.p;
                        leftRotate(z);
                    }
                    /*
                     *	Case 3 -
                     *
                     *	Right rotation to preserve property 5
                     */
                    z.p.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    rightRotate(z.p.p);
                }
            } else{
                /*
                 *	if z's parent(z.p) is right child of z's grandparent (z.p.p)
                 */
                Node y = z.p.p.left;

                if(y.color.equals(Color.RED)){
                    /*
                     *	Case 1 -
                     *
                     *	differentiate case 1 from case 2 and 3 by
                     *	color of z's parent's sibling "uncle" (z.p.p.left)
                     */
                    z.p.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    z = z.p.p;
                } else {
                    if(z.equals(z.p.left)){
                        /*
                         *	Case 2 - (this case 2 is different from the one above)
                         *		(Draw it out to observe differences)
                         *
                         *	if z is LEFT child of z's parent (z.p),
                         *	do RIGHT rotation to transform to Case 3
                         */
                        z = z.p;
                        rightRotate(z);
                    }
                    /*
                     *	Case 3 - (this case 3 is different from that above)
                     *		(Draw it out to observe differences)
                     *
                     *	Left rotation to preserve property 5
                     */
                    z.p.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    leftRotate(z.p.p);
                }
            }
        }
        root.color = Color.BLACK;
    }
}


class RedBlackTreeMedianMaintenanceAlgo{
    public static void main(String[] args){
        try{
            RedBlackBST rbBST = new RedBlackBST();
            Long[] medians = read_file_and_populate(rbBST, "Median.txt");

            long sum = 0;
            for(int i = 0; i < medians.length; i++){
                if(medians[i] != null){
                    sum += medians[i];
                }
            }
            System.out.println("The answer = " + (sum % 10000));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static Long[] read_file_and_populate(RedBlackBST rbBST, String file_loc) throws IOException{

        Long[] medians = new Long[10000];
        int counter = 0;
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fil));
        String element = null;
        while( (element = br.readLine()) != null){
            String[] line = element.split("//s+");
            Node newNode = new Node(Long.parseLong(line[0]), Color.RED);
            rbBST.rBInsert(newNode);
            counter++;
            //  System.out.println("counter = " + counter + ", size = " + rbBST.size());

            int s = rbBST.size();
            long m = 0;
            if(s % 2 == 0){	       		// even
                m = rbBST.select(s/ 2).key;
            } else{				// odd
                m = rbBST.select((s+1)/2).key;
            }
            medians[s-1] = m;
        }
        //  System.out.println("Root = " + rbBST.root + ", root.size = " + rbBST.root.N + ", root.p = " + rbBST.root.p);

        //  rbBST.inorderTreeWalk(rbBST.root);
        return medians;
    }
}

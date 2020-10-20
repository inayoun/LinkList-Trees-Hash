import java.io.*;
import java.util.*;

class Node {
    String guide;
    int value;
}

class InternalNode extends Node {
    Node child0, child1, child2;
}

class LeafNode extends Node {
}

class TwoThreeTree {
   Node root;
   int height;

   TwoThreeTree() {
      root = null;
      height = -1;
   }
}

class WorkSpace {
    // this class is used to hold return values for the recursive doInsert
    // routine (see below)
    
       Node newNode;
       int offset;
       boolean guideChanged;
       Node[] scratch;
    }
    
    public class Solution {
       static BufferedWriter STDOUT;
    
       public static void main(String[] args) throws Exception{
          TwoThreeTree mainTree = new TwoThreeTree();
          Scanner STDIN = new Scanner (System.in);
    
          STDOUT = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
    
          int querySize = STDIN.nextInt();
          for (int i = 0; i<querySize; i++){
              int qNum = STDIN.nextInt();
              if (qNum == 1){
                String key = STDIN.next();
                int value = STDIN.nextInt();
                insert(key, value, mainTree);
              }
              else if (qNum == 2){
                String x = STDIN.next();
                String y = STDIN.next();
                int delta = STDIN.nextInt();
                if (x.compareTo(y)>0){
                    addRange(y,x,delta,mainTree);
                 }
                 else {
                    addRange(x,y,delta,mainTree);
                 }
              }
              else if (qNum == 3){
                String x = STDIN.next();
                printLeaf(x, mainTree);
              }
          }
          STDOUT.flush();
          STDIN.close();
        }  
    
       static void insert(String key, int value, TwoThreeTree tree){
       // insert a key value pair into tree (overwrite existsing value
       // if key is already present)
    
          int h = tree.height;
    
          if (h == -1) {
              LeafNode newLeaf = new LeafNode();
              newLeaf.guide = key;
              newLeaf.value = value;
              tree.root = newLeaf; 
              tree.height = 0;
          }
          else {
             WorkSpace ws = doInsert(key, value, tree.root, h);
    
             if (ws != null && ws.newNode != null) {
             // create a new root
    
                InternalNode newRoot = new InternalNode();
                if (ws.offset == 0) {
                   newRoot.child0 = ws.newNode; 
                   newRoot.child1 = tree.root;
                }
                else {
                   newRoot.child0 = tree.root; 
                   newRoot.child1 = ws.newNode;
                }
                resetGuide(newRoot);
                tree.root = newRoot;
                tree.height = h+1;
             }
          }
       }
    
       static WorkSpace doInsert(String key, int value, Node p, int h) {
       // auxiliary recursive routine for insert
    
          if (h == 0) {
             // we're at the leaf level, so compare and 
             // either update value or insert new leaf
    
             LeafNode leaf = (LeafNode) p; //downcast
             int cmp = key.compareTo(leaf.guide);
    
             if (cmp == 0) {
                leaf.value = value; 
                return null;
             }
    
             // create new leaf node and insert into tree
             LeafNode newLeaf = new LeafNode();
             newLeaf.guide = key; 
             newLeaf.value = value;
    
             int offset = (cmp < 0) ? 0 : 1;
             // offset == 0 => newLeaf inserted as left sibling
             // offset == 1 => newLeaf inserted as right sibling
    
             WorkSpace ws = new WorkSpace();
             ws.newNode = newLeaf;
             ws.offset = offset;
             ws.scratch = new Node[4];
    
             return ws;
          }
          else {
             InternalNode q = (InternalNode) p; // downcast
             int pos;
             WorkSpace ws;

             if (q.value != 0){
                q.child0.value += q.value;
                q.child1.value += q.value;
                if (q.child2 != null){
                    q.child2.value += q.value;
                }
                p.value = 0;
            }
    
             if (key.compareTo(q.child0.guide) <= 0) {
                pos = 0; 
                ws = doInsert(key, value, q.child0, h-1);
             }
             else if (key.compareTo(q.child1.guide) <= 0 || q.child2 == null) {
                pos = 1;
                ws = doInsert(key, value, q.child1, h-1);
             }
             else {
                pos = 2; 
                ws = doInsert(key, value, q.child2, h-1);
             }
    
             if (ws != null) {
                if (ws.newNode != null) {
                   // make ws.newNode child # pos + ws.offset of q
    
                   int sz = copyOutChildren(q, ws.scratch);
                   insertNode(ws.scratch, ws.newNode, sz, pos + ws.offset);
                   if (sz == 2) {
                      ws.newNode = null;
                      ws.guideChanged = resetChildren(q, ws.scratch, 0, 3);
                   }
                   else {
                      ws.newNode = new InternalNode();
                      ws.offset = 1;
                      resetChildren(q, ws.scratch, 0, 2);
                      resetChildren((InternalNode) ws.newNode, ws.scratch, 2, 2);
                   }
                }
                else if (ws.guideChanged) {
                   ws.guideChanged = resetGuide(q);
                }
             }
    
             return ws;
          }
       }
    
    
       static int copyOutChildren(InternalNode q, Node[] x) {
       // copy children of q into x, and return # of children
    
          int sz = 2;
          x[0] = q.child0; x[1] = q.child1;
          if (q.child2 != null) {
             x[2] = q.child2; 
             sz = 3;
          }
          return sz;
       }
    
       static void insertNode(Node[] x, Node p, int sz, int pos) {
       // insert p in x[0..sz) at position pos,
       // moving existing extries to the right
    
          for (int i = sz; i > pos; i--)
             x[i] = x[i-1];
    
          x[pos] = p;
       }
    
       static boolean resetGuide(InternalNode q) {
       // reset q.guide, and return true if it changes.
    
          String oldGuide = q.guide;
          if (q.child2 != null)
             q.guide = q.child2.guide;
          else
             q.guide = q.child1.guide;
    
          return q.guide != oldGuide;
       }
    
    
       static boolean resetChildren(InternalNode q, Node[] x, int pos, int sz) {
       // reset q's children to x[pos..pos+sz), where sz is 2 or 3.
       // also resets guide, and returns the result of that
    
          q.child0 = x[pos]; 
          q.child1 = x[pos+1];
    
          if (sz == 3) 
             q.child2 = x[pos+2];
          else
             q.child2 = null;
    
          return resetGuide(q);
       }
    
    
       static void addAll (Node p, int delta){
        p.value += delta;
       }

       static void printLeaf (String x, TwoThreeTree tree) throws Exception{
        Node curr = tree.root;
        int val = 0;

        while (curr instanceof InternalNode){
            val += ((InternalNode)curr).value;

            if (x.compareTo(((InternalNode)curr).child0.guide) <= 0){
                curr = ((InternalNode)curr).child0;
             }
             else if (x.compareTo(((InternalNode)curr).child1.guide) <= 0 || ((InternalNode)curr).child2 == null){
                curr = ((InternalNode)curr).child1;
             }
             else {
                curr = ((InternalNode)curr).child2;
             }
        }
        if ((curr.guide).compareTo(x) == 0){
            val += curr.value;
            STDOUT.write(val+"\n");
        }
        else{
            STDOUT.write("-1\n");
        }


       }
    
       static void addRange (String x, String y, int delta, TwoThreeTree tree) throws Exception{
        Node p = tree.root;
        Node q = tree.root;
        int h = tree.height;
        Node [] pathX = new Node [h+1];
        Node [] pathY = new Node [h+1];
        int checkPoint = 0;
        int divPoint = -1;

        // get path of X as array of nodes
        for (int i = 0; i<h; i++){
         pathX[i] = (InternalNode) p;
         if (x.compareTo(((InternalNode)p).child0.guide) <= 0){
            p = ((InternalNode)p).child0;
         }
         else if (x.compareTo(((InternalNode)p).child1.guide) <= 0 || ((InternalNode)p).child2 == null){
            p = ((InternalNode)p).child1;
         }
         else {
            p = ((InternalNode)p).child2;
         }
        }
        pathX[h] = (LeafNode) p;

        // get path of Y as array of nodes
        for (int i = 0; i<h; i++){
         pathY[i] = (InternalNode)q;
         if (y.compareTo(((InternalNode)q).child0.guide) <= 0){
            q = ((InternalNode)q).child0;
         }
         else if (y.compareTo(((InternalNode)q).child1.guide) <= 0 || ((InternalNode)q).child2 == null){
            q = ((InternalNode)q).child1;
         }
         else {
            q = ((InternalNode)q).child2;
         }
        }
        pathY[h] = (LeafNode) q;

        // compute divPoint
        for (int i = 0; i<=h; i++){
          checkPoint = i;
         if ((pathY[i].guide).compareTo(pathX[i].guide) != 0){
            divPoint = i;
            checkPoint = i;
            break;
         }
        }

        // conditionally add to x
        if (x.compareTo(p.guide) <= 0 && y.compareTo(p.guide) >= 0){
          addAll (p, delta);
        }
        
        // add trees hanging right from x to divPoint
        for (int i = pathX.length-2; i>=checkPoint; i--){
        if ((((InternalNode)pathX[i]).child0.guide).compareTo((pathX[i+1]).guide) > 0){
            addAll (((InternalNode)pathX[i]).child0, delta);
         }
         if ((((InternalNode)pathX[i]).child1.guide).compareTo((pathX[i+1]).guide) > 0){
            addAll (((InternalNode)pathX[i]).child1, delta);
        }
          if (((InternalNode)pathX[i]).child2 != null && (((InternalNode)pathX[i]).child2.guide).compareTo((pathX[i+1]).guide) > 0){
            addAll (((InternalNode)pathX[i]).child2, delta);
         }
         
        }

        // process divergence point
        if (divPoint >= 0){
         InternalNode divP = (InternalNode) pathX[divPoint-1]; //divP is parent
         if (divP.child2 != null){
            if (!(Arrays.asList(pathX).contains(divP.child1)) && !(Arrays.asList(pathY).contains(divP.child1))){
               addAll (divP.child1, delta);
            }
         }
        }

        // add trees hanging left from divPoint to y
        for (int j = checkPoint; j<=h-1; j++){
            if ((((InternalNode)pathY[j]).child0.guide).compareTo((pathY[j+1]).guide) < 0){
            addAll (((InternalNode)pathY[j]).child0, delta);
            }
            if ((((InternalNode)pathY[j]).child1.guide).compareTo((pathY[j+1]).guide) < 0){
            addAll (((InternalNode)pathY[j]).child1, delta);
            }
            if ((((InternalNode)pathY[j]).child2 != null) && ((((InternalNode)pathY[j]).child2.guide).compareTo((pathY[j+1]).guide) < 0)){
            addAll (((InternalNode)pathY[j]).child2, delta);
            }
        }

        // conditionally add to y leaves
        if ((x.compareTo(q.guide) < 0) && (y.compareTo(q.guide) >= 0) && p.guide.compareTo(q.guide) != 0){
            addAll (q, delta);
        }
        //STDOUT.write("added\n");
    }
}
    
    
    
    
    
    

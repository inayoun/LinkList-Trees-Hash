import java.io.*;
import java.util.*;

class Node {
   String guide;
   // guide points to max key in subtree rooted at node
}

class InternalNode extends Node {
   Node child0, child1, child2;
   // child0 and child1 are always non-null
   // child2 is null iff node has only 2 children
}

class LeafNode extends Node {
   // guide points to the key

   int value;
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

      int dataSize = STDIN.nextInt();
      for (int i = 0; i<dataSize; i++){
         String key = STDIN.next();
         int value = STDIN.nextInt();
         insert(key, value, mainTree);
      }
      int querySize = STDIN.nextInt();
      for (int j = 0; j<querySize; j++){
         //STDOUT.write("Query #"+j+"\n");

         String x = STDIN.next();
         String y = STDIN.next();
         if (x.compareTo(y)>0){
            printRange(y,x,mainTree);
         }
         else {
            printRange(x,y,mainTree);
         }
      }
      STDOUT.flush();
      STDIN.close();

}

   static void insert(String key, int value, TwoThreeTree tree) {
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



   static void printAll (Node p, int h) throws Exception{
      if (h == 0) { // at leaf
         LeafNode temp = (LeafNode) p;
         String out = temp.guide + " " + temp.value;
         STDOUT.write(out+"\n");
      }
      else { // at internal node
         InternalNode temp = (InternalNode) p;
         printAll (temp.child0, h-1);
         printAll (temp.child1, h-1);
         if (temp.child2 != null){
            printAll (temp.child2, h-1);
         }
      }
   }

   static void printRange (String x, String y, TwoThreeTree tree) throws Exception{
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

      // conditionally print out x
      if (x.compareTo(p.guide) <= 0 && y.compareTo(p.guide) >= 0){
          printAll (p, 0);
      }
      //STDOUT.write("conditionally printed x\n");

      // print trees hanging right from x to divPoint
      for (int i = pathX.length-2; i>=checkPoint; i--){
        if ((((InternalNode)pathX[i]).child0.guide).compareTo((pathX[i+1]).guide) > 0){
            printAll (((InternalNode)pathX[i]).child0, h-(i+1));
         }
         if ((((InternalNode)pathX[i]).child1.guide).compareTo((pathX[i+1]).guide) > 0){
            printAll (((InternalNode)pathX[i]).child1, h-(i+1));
        }
          if (((InternalNode)pathX[i]).child2 != null && (((InternalNode)pathX[i]).child2.guide).compareTo((pathX[i+1]).guide) > 0){
            printAll (((InternalNode)pathX[i]).child2, h-(i+1));
         }
         
      }
      //STDOUT.write("printed trees right of x\n");


      // process divergence point
      if (divPoint >= 0){
         InternalNode divP = (InternalNode) pathX[divPoint-1]; //divP is parent
         if (divP.child2 != null){
            if (!(Arrays.asList(pathX).contains(divP.child1)) && !(Arrays.asList(pathY).contains(divP.child1))){
               printAll (divP.child1, h-checkPoint);
            }
         }
      }
      //STDOUT.write("processed divergence point\n");


      // print trees hanging left from divPoint to y
      for (int j = checkPoint; j<=h-1; j++){
          if ((((InternalNode)pathY[j]).child0.guide).compareTo((pathY[j+1]).guide) < 0){
            printAll (((InternalNode)pathY[j]).child0, h-(j+1));
          }
           if ((((InternalNode)pathY[j]).child1.guide).compareTo((pathY[j+1]).guide) < 0){
            printAll (((InternalNode)pathY[j]).child1, h-(j+1));
        }
         if ((((InternalNode)pathY[j]).child2 != null) && ((((InternalNode)pathY[j]).child2.guide).compareTo((pathY[j+1]).guide) < 0)){
            printAll (((InternalNode)pathY[j]).child2, h-(j+1));
        }
    }
    //STDOUT.write("printed trees left of y\n");

      // conditionally print out y leaves
      if ((x.compareTo(q.guide) < 0) && (y.compareTo(q.guide) >= 0)){
        printAll (q, 0);
    }
    //STDOUT.write("conditionally printed y\n");


   }
}






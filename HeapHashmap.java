import java.io.*;
import java.util.*;

class Spartan{
    String name;
    long score;

    Spartan(String n, long s){
        name = n;
        score = s;
    }
}

class minHeap {
    int maxSize;
    int size;
    Spartan[] heap;
    HashMap<String, Integer> map;
    public int FRONT = 1;

    minHeap(int maxSize){
        this.maxSize = maxSize;
        this.size = 0;
        heap = new Spartan[maxSize*3];
        map = new HashMap<String, Integer>();
        heap[0] = null;
    }
    
    Spartan parent (Spartan c){
        if (c != null){
            int pos = map.get(c.name);
            int ppos = pos/2;
            return (heap[ppos]);
        }
        else {
            return null;
        }
    }
    Spartan leftChild(Spartan c){
        int pos = map.get(c.name);
        if (heap[2*pos] != null){
            return (heap[2*pos]);
        }
        else{
            return null;
        }
    }
    Spartan rightChild (Spartan c){
        int pos = map.get(c.name);
        if (heap[2*pos + 1] != null){
            return (heap[(2 * pos) + 1]);
        }
        else{
            return null;
        }
    }
    boolean isLeaf (Spartan c){
        int pos = map.get(c.name);
        int s2 = (int) size/2;
        if (pos>=s2 && pos <= size){
            System.out.println("t"+c.name);
            return true;
        }
        else {
            System.out.println("f"+c.name);
            return false;
        }
    }

     long getMin (){
        return (heap[FRONT].score);
    }

     void swap (Spartan pos1, Spartan pos2){
        int ptemp1 = map.get(pos1.name);
        int ptemp2 = map.get(pos2.name);
        heap [ptemp1] = pos2;
        heap [ptemp2] = pos1;

        map.put(pos1.name, ptemp2);
        map.put(pos2.name, ptemp1);
    }

     void heapify (Spartan c){
        System.out.println(c.name);
        int pos = map.get(c.name);
        if (!isLeaf(c)){
            if (rightChild(c) == null){
                swap (c, leftChild(c));
                heapify(leftChild(c));
            }
            else if (c.score>leftChild(c).score || c.score > rightChild(c).score){
                if (leftChild(c).score < rightChild(c).score){
                    swap (c, leftChild(c));
                    heapify(leftChild(c));
                }
                else {
                    swap (c, rightChild(c));
                    heapify(rightChild(c));
                }
            }
        }
    }

     void update (String name, long delta){
        int pos = map.get(name);
        heap[pos].score += delta;
        heapify (heap[pos]);
    }

     void insert (String name, long score){
        Spartan item = new Spartan(name, score);
        heap[++size] = item;
        map.put(name, size);
        while (parent(item) != null && item.score < parent(item).score){
            if (item.score < parent(item).score){
                swap (item, parent(item));
            }
            item = parent(item);
        }
    }

    Spartan deleteMin(){
        Spartan pop = heap[FRONT];
        heap [FRONT] = heap[size--];
        map.remove(pop.name);
        map.put(heap[size].name, size);
        heapify(heap[FRONT]);
        return pop;
    }
}

public class Solution {
    static BufferedWriter STDOUT;

    public static void main(String[] args) throws Exception{
        Scanner STDIN = new Scanner (System.in);
        STDOUT = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
    
        int arrSize = STDIN.nextInt();
        minHeap heap = new minHeap (arrSize);
        
        for (int i = 0; i < arrSize; i++){ // insert
            String name = STDIN.next();
            long score = STDIN.nextLong();

            heap.insert (name, score);     
        }

        int qSize = STDIN.nextInt();
        for (int i = 0; i<qSize; i++){
            int qType = STDIN.nextInt();
            if (qType == 1){
                String name = STDIN.next();
                long delta = STDIN.nextLong();
                heap.update(name, delta);
            }
            else if (qType == 2){
                long std = STDIN.nextLong();
                while (heap.getMin() < std){
                    heap.deleteMin();
                }
                STDOUT.write(heap.size+"\n");
            }
        }

        STDOUT.flush();
        STDOUT.close();
    }
}

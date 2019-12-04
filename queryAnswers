public class queryAnswers { //this class contains the methods needed for answering the queries
	public LinkList list; 
	public CustNode head;
	public CustNode tail;
	public int size;
	
	public queryAnswers (LinkList list) { //constructor takes linklist. head, tail, size variables also declared for convenience
		this.list = list;
		this.head = list.getFirst();
		this.tail = list.getLast();
		this.size = list.size();
	}
	
	public int waitTime (int cID) { //given customer ID
		CustNode curr = head;
		while (curr != null) {
			if (curr.getId() == cID) { //search for Customer with this ID
				break; //when found, break out of search loop
			}
			curr = curr.getNext();
		}
		if (curr == null) { //In case query file gave an invalid customer ID, return -1
			return -1;
		}
		
		return (curr.getAcc()-curr.getArr()); //return current's wait time (accepted time - arrival time)
	}
	
	public int numServed() {
		return size; //size was incremented only when a SERVED customer was added. size = number of customers served
	}
	
	public int longestBreak() {
		int longest = head.getAcc() - 32400; //this is the first break. from 9 AM until the first customer
		CustNode curr = head.getNext();
		
		while (curr.getNext() != null) { //for the length of all customer nodes
			int brk = curr.getNext().getAcc() - curr.getLeft(); //current break is time between the current customer's leaving and next customer's arrival
			if (brk > longest) { //if current break is greater than longest, current break becomes longest
				longest = brk;
			}
			curr = curr.getNext(); //move on to next node
		}
		if ((61200 - tail.getLeft()) > longest) { //the last possible break. time between last customer until 5 PM
			longest = 61200 - tail.getLeft();
		}
		
		return longest;
		
	}
	
	public int totalIdle() {		
		int tot = 0;
		//check for break between 9 and first customer
		if (head.getAcc()>32400) {
			tot += (32400-head.getAcc());
		}
		CustNode curr = head;
		while (curr.getNext() != null) { //for remaining nodes, add times between current customer's leaving and next customers arrival
			if (curr.getNext().getAcc() > curr.getLeft()) { //add when there is a break
				tot += (curr.getNext().getAcc() - curr.getLeft());	
			}
			curr = curr.getNext();
		}
		//check for break between last customer and 5
		if (tail.getLeft() < 61200) {
			tot+=(61200-tail.getLeft());
		}
		
		return tot;
		
	}

	public int queue() {
		CustNode curr = head;
		CustNode next = head.getNext();
		int q = 0; //the queue while one customer is being served (number of people already in line + number of people that walked in while this customer was being served)
		int max = 0;
		
		while (next.getNext() != null) {
			while (next.getNext() != null && next.getArr() < curr.getLeft()) { //if the next customer comes before current customer leaves
				q++; //increase queue
				next = next.getNext();
			}
			if (q>max) {
				max = q; //if queue formed during this customer is larger, it becomes new max
			}
			q--; //since current customer has left, next customer is served and queue decreases by 1
			curr = curr.getNext(); //move on to calculate queue formed while next customer is being served
		}
		return max;
	}

}

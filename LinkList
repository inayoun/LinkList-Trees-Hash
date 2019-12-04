public class LinkList {
	private CustNode head;
	private CustNode tail;
	private int size;
	final int SERVICE;
	
	public LinkList(int S) { //accepts constant service time as parameter
		SERVICE = S;
		head = null;
		tail = null;
		this.size = 0;
	}
	
	public LinkList (int S, CustNode head) { //service time and head
		SERVICE = S;
		this.head = head;
		this.tail = this.head;
		this.size = 0;
	}
	
	public int size() { //size is not number of nodes here. it is number of customers served
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	public CustNode getFirst() {
		return this.head;
	}
	
	public CustNode getLast() {
		return this.tail;
	}
	
	public void add(int id, int arr) {
		CustNode newNode = new CustNode (id, arr);
		if (isEmpty()) {//if LinkedList is empty and this is first node, add this node as head
			this.head = newNode;
			this.size++;	//size increased by one
			if (arr<32400) { //if first customer came before 9
				newNode.setAcc(32400);
				newNode.setLeft(32400+SERVICE);
			}
			else { //otherwise came after 9, and first customer so no queue
				newNode.setAcc(arr); //accepted as soon as arrived
				newNode.setLeft(arr+SERVICE); //customer leaves after service
			}
		}
		else { //otherwise there is a previous node
			this.tail.setNext(newNode);	//set previous tail's next to this new node
			if (arr >= 61200) { //customer comes after 5. not served
				newNode.setAcc(arr); //wait time is zero, so accepted=arrived
				newNode.setLeft(arr); //no service given, so left=arrived. will not increment size since this customer was not served
			}
			else if (this.tail.getLeft() >= 61200) { //this customer came before 5, but previous customer's service went until after 5
				newNode.setAcc(61200); //wait time is arrived ~ 5 PM
				newNode.setLeft(61200); //not served, so left at 5 PM. size not incremented since not served
			}
			else if (tail.getLeft() > arr) { //served, but there is a queue. previous customer has not left by time of arrival
				newNode.setAcc(tail.getLeft()); //accepted once previous customer has left
				newNode.setLeft(tail.getLeft()+SERVICE); //this customer leaves once service is done
				this.size++;	//size increased by one
			}
			else { //no queue.
				newNode.setAcc(arr); //accepted as soon as arrived
				newNode.setLeft(arr+SERVICE); //customer leaves after service
				this.size++;	//size increased by one
			}
		}
		this.tail = newNode; //new node is now last element in list
		
	}
	
	
}

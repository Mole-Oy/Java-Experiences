/**
 * COMPSCI 1027B Assignment 3
 * DLPriorityQueue.java
 * Due: March 21, 2023
 * 
 * @author Matthew Molloy
 * 
 * Implements the PriorityQueueADT Interface to create objects of PriorityQueue type
 */
public class DLPriorityQueue<T> implements PriorityQueueADT<T>{
	private DLinkedNode<T> front;
	private DLinkedNode<T> rear;
	private int count;
	
	// DLPriorityQueue Constructor
	public DLPriorityQueue() {
		front = null;
		rear = null;
	}
	/**
	 * 
	 * @param DataItem of generic type T
	 * @param Priority of the dataItem
	 */
	public void add(T dataItem, double priority) {
		DLinkedNode<T> newNode = new DLinkedNode<T>(dataItem, priority);
		DLinkedNode<T> currNode = front, prev = null;
		while (currNode != null && priority > currNode.getPriority()) {
			prev = currNode;
			currNode = currNode.getNext();
		}
		// If the queue is empty, make front and rear point to the new node
		if (front == null && rear == null) front = rear = newNode;
		
		else {
			// For inserting as the new front node
			if (currNode == front) {
				newNode.setNext(front);
				front.setPrev(newNode);
				front = newNode;
			}
			// For inserting as new rear node
			else if (prev == rear) {
				newNode.setPrev(rear);
				rear.setNext(newNode);
				rear = newNode;
			}
			// For inserting into the middle of the queue
			else {
				newNode.setNext(currNode);
				newNode.setPrev(prev);
				prev.setNext(newNode);
				currNode.setPrev(newNode);
			}
		}
		count++;
	}
	
	/**
	 * 
	 * @param DataItem of generic type T
	 * @param Priority of the dataItem
	 * 
	 * @throws InvalidElementException when dataItem is not found in the linked list
	 */
	public void updatePriority(T dataItem, double priority) throws InvalidElementException{
		// First check if dataItem is in queue
		boolean dataItemExists = false;
		DLinkedNode<T> curr = front;
		DLinkedNode<T> oldNode = null;	
		while (curr != null){
			// Check if the dataItem matches the current Node's
			if (dataItem == curr.getDataItem()) {
				oldNode = curr;
				dataItemExists = true;
				break;
			}
			else {
				curr = curr.getNext();
			}
		}
		// Throw InvalidElementException if dataItem is not in the queue
		if (!dataItemExists) throw new InvalidElementException("Element does not exist");
		
		// If this node is the only one in the queue, simply update the priority
		if (oldNode == front && oldNode == rear) {
			oldNode.setPriority(priority);
		}
		
		// If not, we need can remove the node temporarily and add it back in afterwards
		// Code executes if queue is 2 elements or longer in length
		else {
			T oldNodeData = oldNode.getDataItem();
			DLinkedNode<T> oldNodePrev = oldNode.getPrev();
			DLinkedNode<T> oldNodeNext = oldNode.getNext();
			
			// Case where there are 2 elements and oldNode is front
			if (oldNode == front && oldNodeNext == rear) {
				rear.setPrev(null);
				oldNode.setNext(null);
				oldNode = null;
				front = rear;
			}
			
			// Case where there are 3+ elements in queue and oldNode is the front
			else if (oldNode == front && oldNodeNext != rear) {
				oldNodeNext.setPrev(null);
				oldNode.setNext(null);
				oldNode = null;
				front = oldNodeNext;
			}
			
			// Case where there are 2 elements and oldNode is rear
			else if (oldNodePrev == front && oldNode == rear) {
				front.setNext(null);
				oldNode.setPrev(null);
				oldNode = null;
				rear = front;
			}
			
			// Case where there are 3+ elements in queue and oldNode is the rear
			else if (oldNodePrev != front && oldNode == rear){
				oldNodePrev.setNext(null);
				oldNode.setPrev(null);
				oldNode = null;
				rear = oldNodePrev;
			}
			// Case where there are 3+ elements in queue and oldNode is in middle
			else {
				oldNodePrev.setNext(oldNodeNext);
				oldNodeNext.setPrev(oldNodePrev);
				oldNode = null;
			}
			// Re-add oldNode to the list with the updated priority
			add(oldNodeData, priority);
		}	
	}
	
	
	/**
	 * 
	 * @return The data of the Node to be removed
	 * 
	 * @throws EmptyPriorityQueueException is the priority queue is empty
	 */
	public T removeMin() {
		// Throw an EmptyPriorityQueueException if the priority queue is empty
		if (front == null) throw new EmptyPriorityQueueException("Priority Queue is empty");
		
		DLinkedNode<T> RmvdDataItem = front;
		// If only one element in queue, front and rear should be set to null
		if (front == rear) {
			front = rear = null;
			return RmvdDataItem.getDataItem();
		}
		RmvdDataItem.getNext().setPrev(null);
		front = RmvdDataItem.getNext();
		RmvdDataItem.setNext(null);
		return RmvdDataItem.getDataItem();
	}
	/**
	 * @return Boolean value true if no node are in queue
	 */
	public boolean isEmpty() {
		return (front == null);
	}
	
	/**
	 * @return Size of the priority queue
	 */
	public int size() {
		int counter = 0;
		DLinkedNode<T> curr = front;
		while (curr != null || curr != null) {
			counter++;
			curr = curr.getNext();
		}
		return counter;
	}
	/**
	 * @return String representation of the priority queue
	 */
	public String toString() {
		DLinkedNode curr = front;
		String s = "";
		while (curr != null) {
			 T dataItem = (T) curr.getDataItem();
			 s += dataItem.toString();
			 curr = curr.getNext();
		}
		return s;
	}

	/**
	 * 
	 * @return Rear node in the queue
	 */
	public DLinkedNode<T> getRear(){
		return rear;
	}
}

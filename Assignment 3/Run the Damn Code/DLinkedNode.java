/**
 * COMPSCI 1027B Assignment 3
 * DLinkedNode.java
 * Due: March 21, 2023
 * 
 * @author Matthew Molloy
 * 
 * Creates the data structure for a doubly linked list
 */
public class DLinkedNode<T> {
	private T dataItem;
	private double priority;
	private DLinkedNode<T> next;
	private DLinkedNode<T> prev;
	
	/**
	 * 
	 * @param data item of generic type T to be inserted into doubly linked list
	 * @param priority of the node compared to other nodes in linked list
	 */
	public DLinkedNode (T data, double prio){
		dataItem = data;
		priority = prio;
	}
	// Empty class constructor
	public DLinkedNode() {
		dataItem = null;
		priority = 0;
	}
	
	/**
	 * 
	 * @return dataItem
	 */
	public T getDataItem() {
		return dataItem;
	}
	
	/**
	 * 
	 * @param nextNode is the node in the list after the current node
	 */
	public void setDataItem(DLinkedNode<T> nextNode) {
		next = nextNode;
	}

	/**
	 * 
	 * @return priority of the node
	 */
	public double getPriority() {
		return priority;
	}
	/**
	 * 
	 * @param change elements priority
	 */
	public void setPriority(double prio) {
		priority = prio;
	}
	
	/**
	 * 
	 * @return next node after current node
	 */
	public DLinkedNode<T> getNext(){
		return next;
	}
	
	/**
	 * 
	 * @param new "next node" to current
	 */
	public void setNext(DLinkedNode<T> next) {
		this.next = next;
	}
	/**
	 * 
	 * @return previous node
	 */
	public DLinkedNode<T> getPrev(){
		return prev;
	}
	/**
	 * 
	 * @param new "prev node" to current
	 */
	public void setPrev(DLinkedNode<T> prev ){
		this.prev = prev;
	}
}

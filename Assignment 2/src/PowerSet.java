/**
 * COMPSCI 1027B Assignment 2
 * PowerSet.java
 * Due: February 27, 2023
 * 
 * @author Matthew Molloy
 * 
 * Creates a power set from a given set of generic type T
 */


public class PowerSet<T> {
	private Set<T>[] set;
	
	/**
	 * 
	 * @param elements
	 * 
	 * Creates a power set for the inputed array
	 */
	public PowerSet(T[] elements) {
		double numElements = Math.pow(2.00, elements.length);
		Set<T>[] set = (Set<T>[]) new Set[(int)numElements];
		this.set = set;
		for (int i = 0; i < (int)numElements; i++) {
			// Create a new subSet
			set[i] = new Set<T>();
			// Convert i into a binary string, and pad with zeros equivalent to elements.length - 1
			String binString = String.format("%"+elements.length+"s", Integer.toBinaryString(i));
			binString = binString.replace(" ", "0");
			
			for (int j = 0; j < elements.length; j++) {
				if (binString.charAt(j) == '1') set[i].add(elements[j]);;
			}
		}
	}
	
	/**
	 * 
	 * @return length of the power set (Should be 2^n)
	 */
	public int getLength() {
		//System.out.println(set.length);
		return set.length;
	}
	
	/**
	 * 
	 * @param integer index
	 * @return the Set stored at the specified index
	 */
	public Set<T> getSet(int index){
		return set[index];
	}
}

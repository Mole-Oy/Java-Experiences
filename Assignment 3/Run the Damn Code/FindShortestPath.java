/**
 * COMPSCI 1027B Assignment 3
 * FindShortestPath.java
 * Due: March 21, 2023
 * 
 * @author Matthew Molloy
 * 
 * Reads the dungeon input file and computes the shortest 
 * path from the initial chamber to the exit using a priority 
 * queue of the hexagonal chambers
 */
public class FindShortestPath {
	public static void main(String[] args) {
		
		// Ensure that the dungeon text file is not empty. Otherwise, set args[0] to be the file 
		// name used as the parameter for initializing the dungeon object
		try {
			if (args.length < 1) throw new Exception("No input file was specified");
			
			// Initialize the dungeon constructor with dungeonFileName as inFile parameter
			Dungeon d = new Dungeon("dungeon1.txt");
			// Create an empty Priority queue to store the chambers (Hexagon object)
			DLPriorityQueue<Hexagon> ChamberPQueue = new DLPriorityQueue<Hexagon>();
			
			// Add the starting chamber to the priority queue, and mark as enqueued
			ChamberPQueue.add(d.getStart(), 0);
			d.getStart().markEnqueued();
			
			System.out.println(d.getExit());
			while (!ChamberPQueue.isEmpty() && d.getExit() == null) {
				
			}
		}
		
		catch (Exception e){
			
		}
		
	}	
}

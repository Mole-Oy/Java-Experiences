/**
 * COMPSCI 1027B Assignment 1
 * MarkovChain.java
 * Due: February 8, 2023
 * 
 * @author Matthew Molloy
 *
 */
public class MarkovChain {
	private Vector stateVector;
	private Matrix transitionMatrix;
	
	public MarkovChain(Vector sVector, Matrix tMatrix) {
		stateVector = sVector;
		transitionMatrix = tMatrix;
	}
	/**
	 * @return boolean value true if all 3 are true:
	 * 		   1. If Matrix is n * n
	 * 		   2. If number of vector cols match number of matrix rows
	 * 		   3. If sum of the values in the state vector totals 1.0
	 */
	public boolean isValid() {
		// System.out.println(stateVector.getNumCols() == transitionMatrix.getNumRows());
		// is the matrix a square?
        if (transitionMatrix.getNumRows() != transitionMatrix.getNumCols()) return false;
        // Does number of vector cols match the number of matrix rows?
        if (stateVector.getNumCols() != transitionMatrix.getNumCols()) return false;

        double vectorSum = 0.0;
        for (int col = 0; col < stateVector.getNumCols(); col++) {
            vectorSum += stateVector.getElement(col);
            // System.out.println("Vector Sum: " + vectorSum);
        }
        // does the sum of the values in the sVector approx. equal 1.0?
        if (!(vectorSum > 0.99 && vectorSum < 1.01)) return false;
        // Resent sum
        vectorSum = 0.0;
        
        double matrixRowSum = 0.0;
        // does the sum of the values in the tMatrix approx equal 1.0?
        for (int row = 0; row < transitionMatrix.getNumRows(); row++) {
        	for (int col = 0; col < transitionMatrix.getNumCols(); col++) {
        		matrixRowSum += transitionMatrix.getElement(row, col);
        		// System.out.println("Matrix row sum: " + matrixRowSum);
        	}
        	// Check if sum is 1.0
        	// System.out.println("Final Matrix row sum: " + matrixRowSum);
        	if (!(matrixRowSum > 0.99 && matrixRowSum < 1.01)) return false;
        	// Reset sum
        	matrixRowSum = 0.0;
        }
        
        return true;
    }
	
	/**
	 * @param	numSteps
	 * @return  Resuting state vector after n iterations of matrix multiplication
	 */
	public Matrix computeProbabilityMatrix(int numSteps) {
		// first call isValid() to see if state vector and Matrix are valid for Markov chain
		if (!isValid()) {
			//System.out.println("NULL");
			return null;
		}
		// Create a second matrix object to store the multiplication of the 
		Matrix Px = transitionMatrix;
		// Multiply the Transition Matrix by numSteps-1 times
		for (int i = 0; i < numSteps - 1; i++) {
			Px = Px.multiply(transitionMatrix);
		}
		
		// Multiply the new transition Matrix by the state vector
		//System.out.println(stateVector.multiply(Px));
		return stateVector.multiply(Px);
	}
	
}

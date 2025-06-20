/**
 * COMPSCI 1027B Assignment 1
 * Matrix.java
 * Due: February 8, 2023
 * 
 * @author Matthew Molloy
 *
 */

public class Matrix {
	// Declare private variables
	private int numRows;
	private int numCols;
	private double[][] data;
	
	/**
	 * @param	The number of rows the matrix will have
	 * @param	The number of columns the matrix will have
	 */
	public Matrix(int r, int c){
		numRows = r;
		numCols = c;
		data = new double[r][c];
	}
	
	/**
	 * @param	The number of rows the matrix will have
	 * @param	The number of columns the matrix will have
	 * @param	an 1D array containing the values of the matrix
	 */
	public Matrix(int r, int c, double[] linArr) { // ***START OF 3 PARAMETER CONSTRUCTOR***
		numRows = r;
		numCols = c;
		data = new double[r][c];
		/*
		 * Index will track the amount of iterations that have occurred.
		 * Index will be incremented by one before a new element is added to data[].
		 * If index exceeds linArr.length-1, the remaining spaces in 
		 * data[r][c]will fill with zero
		 */
		int index = -1;
		// Nest for-loops to iterate along a row, then column
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numCols; j++) {
				// Increment the count variable by one
				index++;
				// Checks if the count is over the index of linArr
				if (index < linArr.length){
					data[i][j] = linArr[index];
					//System.out.println(data[i][j]);
				}
				else {
					data[i][j] = 0.000;
					//System.out.println(data[i][j]);
				}
			}
		}
		
	} // ***END OF 3-PARAMETER CONSTRUCTOR***

	
	/**
	 * @return	the integer value of the private variable numRows
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * @return	the integer value of the private variable numCols
	 */
	public int getNumCols() {
		return numCols;
	}
	
	/**
	 * @return	the 2D matrix "data"
	 */
	public double[][] getData(){
		return data;
	}
	
	/**
	 * @param   integer value for row
	 * @param	integer value for column
	 * 
	 * @return	double value of an element in the array at the R'th row
	 * 			and C'th column 
	 */
	public double getElement(int r, int c) {
		return data[r][c];
	}
	
	/**
	 * @param   integer value for row
	 * @param   integer value for column
	 * @param   double value of new element replacing old element
	 */
	public void setElement(int r, int c, double value) {
		data[r][c] = value;
	}
	// Transposes the matrix
	public void transpose() { // ***START OF TRANSPOSE METHOD***
		// create a new 2D array for the transpose
		double[][] transpose = new double[numCols][numRows];
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				// transpose reverses rows and cols, hence data[j][i]
				transpose[i][j] = data[j][i];
			}
		}
		// replace data instance variable with transpose
		data = transpose;
		// Use an interim variable for temporary storage of original # of cols
		int interimCols = getNumCols();
		// Swap # of rows and cols
		numCols = getNumRows();
		numRows = interimCols;
	}// ***END OF TRANSPOSE METHOD***
	
	/**
	 * 
	 * @param	each element in data[][] will be multiplied by this scalar value
	 * @return  a copy of Matrix object with scalar multiplication applied
	 */
	public Matrix multiply(double scalar) {
		// Create new matrix object that stores the scalar-multiplied matrix
		Matrix scalarMatrix = new Matrix(numRows, numCols);
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				// declare new variable to multiply data[i][j] by the scalar value
				double multElement = data[i][j] * scalar;
				// System.out.println(data[i][j] * scalar);
				
				// set scalarMatrix element i,j equal to multElement
				scalarMatrix.setElement(i, j, multElement);
			}
		}
		return scalarMatrix;
	}
	/**
	 * @param	the 'other' matrix of compatible size for performing matrix multiplication
	 * @return	a matrix that contains the results of matrix multiplication between 'this' and 'other'
	 */
	public Matrix multiply(Matrix other) { // ***START OF MATRIX MULTIPLY***
		// first must check if the no. rows == no. cols
		if (this.numCols != other.numRows) {
			return null;
		}
		// create the multiplication matrix
		Matrix MatrixMult = new Matrix(this.numRows, other.numCols);
		// Stores the value of an element after matrix multiplication
		double multiply = 0.0;
		// i controls rows for 'this', j controls cols for 'other', k is used to 
		// iterate though 'this' along its row, and iterate through 'other' along its col
		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < other.numCols; j++) {
				for (int k = 0; k < other.numRows; k++) {
					//System.out.println(this.getElement(i, k) +  "*" +  other.getElement(k, j));
					multiply = multiply + (this.data[i][k] * other.data[k][j]);
				}
				// add the element to the separate Matrix object
				//System.out.println("Multiplied: "+ multiply);
				MatrixMult.setElement(i, j, multiply);
				// reset the value of multiply for the next element
				multiply = 0;
			}
		}
		return MatrixMult;
	} // ***END OF MATRIX MULTIPLY***
	
	/**
	 * @return Returns an m * n matrix with all elements truncated to 3 decimal points
	 */
	public String toString() {
		String mainString = "";
		// first, check if Matrix object is empty
		if (numRows == 0 && numCols == 0) {
			return "Empty matrix";
		}
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				// String.format returns a string representation of each element in the matrix
				String formatStr = String.format("%8.3f", data[i][j]);
				mainString = mainString.concat(formatStr);
				// check if this element is in the last col. If so, newline required
				if (j == (numCols - 1)) {
					mainString = mainString.concat("\n");
				}
			}
		}
		//System.out.println(mainString);
		return mainString;
	}
}

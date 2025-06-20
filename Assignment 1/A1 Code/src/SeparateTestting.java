
public class SeparateTestting {

	public static void main(String[] args) {
		Matrix Mat = new Matrix(3, 3, new double[] {0.2, 0.6, 0.2, 0.2, 0.5, 0.3, 0.2, 0.7, 0.1});
		
		Vector v1 = new Vector(3, new double[] {0.8, 0.1, 0.1});
		
		MarkovChain MC1 = new MarkovChain(v1, Mat);		
		MC1.computeProbabilityMatrix(3);
		MC1.toString();
	}

}

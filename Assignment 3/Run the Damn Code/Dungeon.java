import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Dungeon class creates a window that shows a hexagon-tile based Dungeon.
 * <p>
 * 
 * The Dungeon is built from a file with the following specifications:
 * <ul>
 * <li>The first line has the number of rows and cols and an optional size of
 * each hexagon in pixels</li>
 * 
 * <li>Each subsequent line (there will be the same number of lines as rows)
 * contains a string with the characters 'U', 'D', 'L', 'C', 'W', 'S', 'E'
 * separated by spaces. (Note: because this Dungeon is based on hexagons, each
 * alternating row is offset from the left side by half a hexagon, indicated by
 * a space in the input file)
 * </ul>
 * 
 * @author CS1027
 *
 */
public class Dungeon extends JFrame {

	// Serialization UID
	private static final long serialVersionUID = 1L;

	/**
	 * Default time delay when repainting the Dungeon to reflect hexagon changes
	 */
	public static final int DEFAULT_TIME_DELAY = 250;
	public static boolean visible;
	private static Hexagon[][] hexDungeonBuilder;

	// Attributes
	private int timeDelay; // Delay before marking the next chamber
	public static Hexagon start; // Initial chamber
	public static Hexagon exit = null; // Exit
	private int cellSize; // Size of each chamber when displayed on the screen
	private int numberChambers;

	/**
	 * Constructor to build a Graphical Dungeon with hexagonal tiles from a file
	 * containing a Dungeon specification
	 * 
	 * @param inFile
	 *             The name of the input file
	 * @throws InvalidDungeonCharacterException
	 *             An invalid character was found in the input file
	 * @throws FileNotFoundException
	 *             Inexistent file
	 * @throws IOException
	 *             An error occurred when trying to access the file.
	 */
	public Dungeon(String inFile) throws InvalidDungeonCharacterException, FileNotFoundException, IOException {
		// set up GUI aspects of the Dungeon component
		super("Dungeon");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel();
		p.setBackground(Color.LIGHT_GRAY.darker());

		// Get monitor resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = screenSize.height;

		// set up the file reader and skip the first line
		BufferedReader in;
		String line = "";
		in = new BufferedReader(new FileReader(inFile));
		line = in.readLine(); // Ignore first line
		line = in.readLine();

		// Tokenize the first line to get the row and column
		StringTokenizer lineTokens = new StringTokenizer(line);
		// First line is the number of rows then the number of columns
		int row = Integer.parseInt(lineTokens.nextToken());
		if (inFile.charAt(0) == 'x') timeDelay = 0;
		else timeDelay = 1000 - 40 * row; // delay inversely proportional to number of chambers

		int col = Integer.parseInt(lineTokens.nextToken());
		numberChambers = row * col;
		cellSize = screenHeight / (row + 2);
		if (lineTokens.hasMoreTokens()) {
			timeDelay = Integer.parseInt(lineTokens.nextToken());

			if (lineTokens.hasMoreTokens()) {
				cellSize = Integer.parseInt(lineTokens.nextToken());
				if (cellSize > (screenHeight / (row + 2)))
					cellSize = screenHeight / (row + 2);
			}

		}

		// To build the Dungeon we will make temporary use of a 2D array
		// Once built, the hexagons themselves know all of their neighbors, so
		// we do not need the 2D array anymore.
		// Add a row and col of nulls around the "edges" of the builder matrix
		// (+2's)
		// This will greatly simplify the neighbor building process below
		hexDungeonBuilder = new Hexagon[row + 2][col + 2];

		// HexLayout will arrange the Hexagons in the window
		p.setLayout(new HexLayout(row, col, 2));

		for (int r = 1; r < row + 1; r++) {
			line = in.readLine();
			lineTokens = new StringTokenizer(line);
			// for each token on the line (col in the Dungeon)
			for (int c = 1; c < col + 1; c++) {

				// read the token and generate the hexagon type
				char token = lineTokens.nextToken().charAt(0);
				switch (token) {
				case 'W':
					hexDungeonBuilder[r][c] = new Hexagon(Hexagon.HexType.WALL, timeDelay);
					break;
				case 'S':
					hexDungeonBuilder[r][c] = new Hexagon(Hexagon.HexType.START, timeDelay);
					start = hexDungeonBuilder[r][c];
					break;
				case 'E':
					hexDungeonBuilder[r][c] = new Hexagon(Hexagon.HexType.END, timeDelay);
					exit = hexDungeonBuilder[r][c];
					break;
				case 'U':
					hexDungeonBuilder[r][c] = new Hexagon(Hexagon.HexType.EMPTY, timeDelay);
					break;
				case 'D':
					hexDungeonBuilder[r][c] = new Hexagon(Hexagon.HexType.DRAGON, timeDelay);
					break;
				case 'L':
					hexDungeonBuilder[r][c] = new Hexagon(Hexagon.HexType.LAVA, timeDelay);
					break;
				case 'C':
					hexDungeonBuilder[r][c] = new Hexagon(Hexagon.HexType.CACTI, timeDelay);
					break;
				default:
					throw new InvalidDungeonCharacterException(token);
				}

				// add to the GUI layout
				p.add(hexDungeonBuilder[r][c]);
			} // end for cols
		} // end for rows

		// go through the 2D matrix again and build the neighbors
		int offset = 0;
		for (int r = 1; r < row + 1; r++) {
			for (int c = 1; c < col + 1; c++) {
				// on even rows(insert from left side) need to add one to the
				// upper and lower neighbors
				// on odd, do not add anything (offset should be 0)
				offset = 1 - r % 2;

				// set the neighbors for this hexagon in the builder
				hexDungeonBuilder[r][c].setNeighbour(hexDungeonBuilder[r - 1][c + offset], 0);
				hexDungeonBuilder[r][c].setNeighbour(hexDungeonBuilder[r][c + 1], 1);
				hexDungeonBuilder[r][c].setNeighbour(hexDungeonBuilder[r + 1][c + offset], 2);
				hexDungeonBuilder[r][c].setNeighbour(hexDungeonBuilder[r + 1][c - 1 + offset], 3);
				hexDungeonBuilder[r][c].setNeighbour(hexDungeonBuilder[r][c - 1], 4);
				hexDungeonBuilder[r][c].setNeighbour(hexDungeonBuilder[r - 1][c - 1 + offset], 5);
			} // end for cols
		} // end for rows

		// close the file
		in.close();

		// set up the GUI window
		this.add(p);
		this.pack();
		this.setSize(cellSize * row, cellSize * col);

		// New code for TA testing
		if (inFile.charAt(0) == 'x') {
			this.setVisible(false);
			visible = false;
			timeDelay = 0;			
		} else this.setVisible(true);

	}

	/**
	 * Method will return a reference to the hexagon that is the start of the
	 * Dungeon.
	 * 
	 * @return A reference to the hexagon that is the start of the Dungeon
	 */
	public Hexagon getStart() {
		return start;
	}

	/**
	 * Method will return a reference to the hexagon that is the exit of the
	 * Dungeon.
	 * 
	 * @return A reference to the hexagon that is the exit of the Dungeon
	 */
	public Hexagon getExit() {
		return exit;
	}

	/**
	 * Get the current time delayed used when repainting the Dungeon to reflect
	 * changes made to the hexagon tiles
	 * 
	 * @return the timeDelay
	 */
	public int getTimeDelay() {
		return timeDelay;
	}

	/**
	 * Set the amount of time to wait when repainting the Dungeon to reflect
	 * changes made to the hexagon tiles
	 * 
	 * @param timeDelay
	 *            the timeDelay to set
	 */
	public void setTimeDelay(int timeDelay) {
		this.timeDelay = timeDelay;
	}

	/**
	 * Get the size in pixels of each cell when drawn on the screen
	 * 
	 * @return the timeDelay
	 */
	public int getCellSize() {
		return cellSize;
	}

	@Override
	/**
	 * This method will update the Dungeon to reflect any changes to the
	 * hexagonal tiles it shows. The method includes a time delay, which can be
	 * changed with the setDelay method.
	 */
	public void repaint() {
		try {
			Thread.sleep(this.timeDelay);
		} catch (Exception e) {
			System.err.println("Error while issuing time delay\n" + e.getMessage());
		}
		super.repaint();
	}

	/**
	 * Returns the number of chambers in the dungeon
	 * 
	 * @return number of chambers in the dungeon
	 */
	public int numChambers() {
		return numberChambers;
	}

}

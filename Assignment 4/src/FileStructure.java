
/**
 * COMPSCI 1027B Assignment 4
 * FileStructure.java
 * Due: April 6, 2023
 * 
 * @author Matthew Molloy
 * 
 * 
 */
import java.util.Iterator;
import java.util.ArrayList;

public class FileStructure {

	private NLNode<FileObject> root;

	/**
	 * 
	 * @param fileObjectName; Name of file/directory that will be stored as the root
	 *                        node of the file system
	 * @throws FileObjectException; Thrown if there is an error directly related to
	 *                              creating the file/directory
	 */
	public FileStructure(String fileObjectName) throws FileObjectException {
		// Create a new file object with the argument fileObjectName
		FileObject refFileObj = new FileObject(fileObjectName);
		root = new NLNode<FileObject>(refFileObj, null);
		// If the FileObject does exist somewhere in the directory, run the auxillary
		// recursive method
		// create the file structure
		if (fileObjectName == refFileObj.getName() && refFileObj.isDirectory()) {
			createStructure(root);
		}
	}

	/**
	 * 
	 * @return the selected root
	 */
	public NLNode<FileObject> getRoot() {
		return root;
	}

	/**
	 * 
	 * @param type; String representing the desired file type
	 * @return An Iterator object using the ArrayList<String> received from the
	 *         findFilesOfType() method
	 */
	public Iterator<String> filesOfType(String type) {
		return findFilesOfType(root, type).iterator();
	}

	/**
	 * 
	 * @param name; Name of desired file
	 * @return If desired file exists, return its absolute path. Otherwise, return
	 *         and empty string
	 */
	public String findFile(String name) {
		return findFileName(root, name);
	}

	/**
	 * A recursive algorithm responsible for storing the files/directories in the
	 * correct structure
	 * 
	 * @param r; First represents the Root folder in the File structure, but for
	 *           further recursion, it represents sub folders
	 */
	private void createStructure(NLNode<FileObject> r) {
		// If r is a file, this is a leaf node and therefore has no children. No action
		// required
		if (r.getData().isFile())
			;
		// Otherwise, r is a folder, and may contain children nodes
		else {
			Iterator<FileObject> children = r.getData().directoryFiles();
			while (children.hasNext()) {
				FileObject child = children.next();
				NLNode<FileObject> childNode = new NLNode<FileObject>(child, r);
				r.addChild(childNode);
				createStructure(childNode);
			}
		}
	}

	/**
	 * 
	 * @param r;    Represnts the root folder in the file structure
	 * @param type; The string suffix of the desired file extension (eg. ".java")
	 * @return A String ArrayList of all files found with the desired file extension
	 */
	private ArrayList<String> findFilesOfType(NLNode<FileObject> r, String type) {
		ArrayList<String> filesOfType = new ArrayList<String>();

		// If r is a file, get the its absoulute path
		if (r.getData().isFile()) {
			// Stores the FileObject Type of r
			FileObject file = r.getData();
			if (file.getLongName().contains(type)) {
				filesOfType.add(file.getLongName());
				return filesOfType;
			}
			else {
				filesOfType.add(null);
				return filesOfType;
			}
		}
		else {
			// Otherwise, r is a directory, and may have child nodes of the desired type
			Iterator<NLNode<FileObject>> children = r.getChildren();
			while (children.hasNext()) {
				NLNode<FileObject> child = children.next();
				ArrayList<String> tempArray = findFilesOfType(child, type);
				// Get elements from temp Array
				if (tempArray.size() == 1 ) {
					if (tempArray.get(0) != null) {
						filesOfType.add(tempArray.get(0));
					}
				}
				
				// return findFilesOfType(child, type);
			}
		}
		return filesOfType;
		
	}

	/**
	 * The private recursive method used in findFile()
	 * 
	 * @param r;    root folder to navigate through the file structure
	 * @param Name; Name of the desired file
	 * @return If desired file exists, return its absolute path. Otherwise, return
	 *         and empty string
	 */
	private String findFileName(NLNode<FileObject> r, String name) {
		String s = "";
		// if r is a file, check if its file name matches the desired file
		if (r.getData().isFile()) {
			String fileName = r.getData().getName();
			if (fileName.contentEquals(name)) {
				return r.getData().getLongName();
			} else
				return "";
		}
		// Otherwise, r is a directory
		else {
			Iterator<NLNode<FileObject>> children = r.getChildren();
			while (children.hasNext()) {
				NLNode<FileObject> child = children.next();
				s = findFileName(child, name);
				if (s.contains(name)) return s;
			}
			return s;
		}
	}
}

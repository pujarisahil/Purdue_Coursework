import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TreeParser {
	static int position;
	
	static String treeString;
	
	public static String getCleanedString(String text) {
		return text.replaceAll("\\s","");
	}
	
	private static Node parseAndCreateTree() {
		if (treeString.charAt(position) == '(') {
			if (treeString.charAt(position + 1) == ')') {
				position += 2;
				return null;
			}
			char val = treeString.charAt(position + 1);
			Node curNode = new Node(String.valueOf(val));
			position += 3;
			if (treeString.charAt(position - 1) == ')')
				return curNode;
			Node leftRoot = parseAndCreateTree();
			position += 1;
			Node rightRoot = parseAndCreateTree();
			position += 1;
			curNode.setLeftChild(leftRoot);
			curNode.setRightChild(rightRoot);
			return curNode;
		}
		return null;

	}
	
	public static Node createTree(String treeRepresentationText) {
		treeString = treeRepresentationText;
		position = 0;
		return parseAndCreateTree();
	}


	/**
	 * Set the name of each node of the tree accordingly to its order,
	 * for example, the root will have the name ROOT, the second left child will have the name LL
	 * @param  temp the tree in which, the name will be set
	 * @return void
	 */
	public static void setName(Node temp){
		if(temp.getRightChild() != null){
			temp.getRightChild().setname(temp.getname() + 'R');
			setName(temp.getRightChild());
		}
		if(temp.getLeftChild() != null){
			temp.getLeftChild().setname(temp.getname() + 'L');
			setName(temp.getLeftChild());
		}		
	}	
	
	/**
	 * Check the node if it matches with non branching tree, iterate through the string, if L, check left child
	 * else check right child, recursively, check the next left/right child if they are matches until the whole tree
	 * is checked
	 * @param  haystack the input node
	 * @param needle the input non branching tree
	 * @param listMatches, every time a node matches the non branching tree, add it to the list
	 * @return void
	 */
	public static void checkNode(Node haystack, String needle, List<String> listMatches){
		int counter = 0; boolean flag = true;
		Node temp = haystack; //temp node that can be played with
		while(counter < needle.length() && flag){ //checks if flag is true and counter is less than the string length
			if(needle.charAt(counter) == 'L' && temp.getLeftChild() != null){ //for the case where current char is L and left is not null
					counter += 1;	//we increase the counter
					temp = temp.getLeftChild(); //set the value of temp to it's left node
			}
			else if(needle.charAt(counter) == 'R' && temp.getRightChild() != null){ //in case the current char is R and right child is not null
					counter += 1; //we increase the counter
					temp = temp.getRightChild(); //set the value of temp to it's right node
			}
			else{
				flag = false; //otherwise flag is false indicating the wrong condition
			}
		}
		if(flag){
			listMatches.add(haystack.getname()); //add the names to the list whenever flag is true
		}
		if(haystack.getLeftChild() != null){
			checkNode(haystack.getLeftChild(), needle, listMatches); //we call the method again with the new arguments (left)
		}
		if(haystack.getRightChild() != null){
			checkNode(haystack.getRightChild(), needle, listMatches); //we call the method again with the new arguments (right)
		}
	}
	
	/**
	 * Check the node if it matches with the branching tree recursively
	 * @param  haystack the input node
	 * @param needle the input branching tree
	 * @return true if the current node matches with the branching tree, false otherwise
	 */	
	public static boolean checkNode(Node haystack, Node needle){
		//variables to check for the two possible conditions
		boolean temp1 = true; 
		boolean temp2 = true;
		if(needle == null){
			return true; //if needle is null we return true accounting for null needle arguments (Base case)
		}
		if(needle.getLeftChild() == null && needle.getRightChild() == null){
			return true;//if both left and right are null we return true for just a single root node (Base case)
		}
		if(needle.getLeftChild() != null && haystack.getLeftChild() != null){
			temp1 = checkNode(haystack.getLeftChild(), needle.getLeftChild());//if lefts of both are not null we call the method again with the new arguements (recursive step)
		}
		else if(needle.getLeftChild() != null && haystack.getLeftChild() == null){
			return false; //checking for the particular case and returning false since they are clearly not the same
		}
		
		if(needle.getRightChild() != null && haystack.getRightChild() != null){
			temp2 = checkNode(haystack.getRightChild(), needle.getRightChild());//if rights of both are not null we call the method again with the new arguements (recursive step)
		}		
		else if(needle.getRightChild() != null && haystack.getRightChild() == null){
			return false;//checking for the particular case and returning false since they are clearly not the same
		}	
		
		if(temp1 && temp2){
			return true; //we return true only when both of the flags are true
		}
		else{
			return false; //else we return false
		}
	}	
	
	/**
	 * Recursively go through tree's node one by one, calling the checkNode to see if it matches
	 * if it matches, add to the listMatches
	 * @param haystack the input node
	 * @param needle the input branching tree
	 * @param listMatches the list of the matched nodes
	 * @return void
	 */
	public static void recursiveTree(Node haystack, Node needle, List<String> listMatches){
		//Traversing through the trees appropriately and storing the names
		if(checkNode(haystack, needle)){
			listMatches.add(haystack.getname());
		}
		if(haystack.getLeftChild() != null){
			recursiveTree(haystack.getLeftChild(), needle, listMatches);
		}
		if(haystack.getRightChild() != null){
			recursiveTree(haystack.getRightChild(), needle, listMatches);
		}
	}
	/**
	 * Search for the matches, firstly, set the name of the root to be ROOT, and other nodes correspondingly
	 * then call recursiveTree function to add the matches to the list
	 * @param haystack the input node
	 * @param needle the input branching tree
	 * @return listMatches as list of the matches
	 */	
	public static List<String> searchForNeedleInHaystack(Node haystack, Node needle) {
		List<String> listMatches = new ArrayList<String>(); 
		haystack.setname("ROOT");
		if(haystack.getLeftChild() != null){
			haystack.getLeftChild().setname("L");
			setName(haystack.getLeftChild());
		}
		if(haystack.getRightChild() != null){
			haystack.getRightChild().setname("R");
			setName(haystack.getRightChild());			
		}
		
		recursiveTree(haystack, needle, listMatches);
		
		if (listMatches.isEmpty()){
			listMatches.add("NONE");
		}
		return listMatches;
	}
	
	/**
	 * Search for the matches, firstly, set the name of the root to be ROOT, and other nodes correspondingly
	 * then call checkNode function to add the matches to the list
	 * @param haystack the input node
	 * @param needle the input non branching tree
	 * @return listMatches as list of the matches
	 */	
	public static List<String> searchForNeedleInHaystack(Node haystack, String needle) {
		List<String> listMatches = new ArrayList<String>(); 
		haystack.setname("ROOT");
		if(haystack.getLeftChild() != null){
			haystack.getLeftChild().setname("L");
			setName(haystack.getLeftChild());
		}
		if(haystack.getRightChild() != null){
			haystack.getRightChild().setname("R");
			setName(haystack.getRightChild());			
		}

		
		checkNode(haystack, needle, listMatches);
		if (listMatches.isEmpty()){
			listMatches.add("NONE");
		}
		return listMatches;
	}


	/**
	 * Search for the matches, firstly, set the name of the root to be ROOT, and other nodes correspondingly
	 * using substring to get all the combination of the input at 3/4
	 * then call checkNode function on each one to add the matches to the list
	 * @param haystack the input node
	 * @param needle the input non branching tree
	 * @return listMatches as list of the matches
	 */		
	public static List<String> approximateSearch(Node haystack, String needle) {
		List<String> listMatches = new ArrayList<String>(); 
		haystack.setname("ROOT");
		if(haystack.getLeftChild() != null){
			haystack.getLeftChild().setname("L");
			setName(haystack.getLeftChild());
		}
		if(haystack.getRightChild() != null){
			haystack.getRightChild().setname("R");
			setName(haystack.getRightChild());			
		}
		
		int length = needle.length() * 3/4;
		int counter = 0;
		while(counter + length < needle.length()){
			checkNode(haystack, needle.substring(counter, length), listMatches);
			counter ++;
		}
		if (listMatches.isEmpty()){
			listMatches.add("NONE");
		}
		return listMatches;
	}

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		int nCases = in.nextInt();
		in.nextLine();

		for(int i = 0; i < nCases; ++i) {
			String treeRepresentationText = in.nextLine();
			treeRepresentationText = getCleanedString(treeRepresentationText);
			System.out.println("Testcase " + (i + 1) + ": " + treeRepresentationText);
			
			Node rootSearchTree = createTree(treeRepresentationText);
			
			int nPaths = in.nextInt();
			in.nextLine();

			System.out.println("Output for testcase " + (i+1));
			for(int j = 0; j < nPaths; ++j) {
				String searchInput = in.nextLine();
				searchInput = searchInput.trim();
				
				String searchOption = searchInput.substring(0, 1);
				String searchPattern = searchInput.substring(2);
				
				searchPattern = getCleanedString(searchPattern);
				
				System.out.println("Query " + (j+1) + ": " + searchPattern);

				List<String> occurences;
				if(searchOption.equals("t")) { // Tree search
					Node rootPatternTree = createTree(searchPattern);
					occurences = searchForNeedleInHaystack(rootSearchTree, rootPatternTree);
				} else if(searchOption.equals("p")) { // Path search
					occurences = searchForNeedleInHaystack(rootSearchTree, searchPattern);
				} else { // Approximate Search
					occurences = approximateSearch(rootSearchTree, searchPattern);
				}
				Collections.sort(occurences);
				for(String position: occurences) {
					System.out.println(position);
				}
			}
		}
		in.close();
	}
}

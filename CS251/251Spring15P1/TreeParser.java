import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeParser {
	static int position;

	static String treeString;

	public static String getCleanedString(String text) {
		String result = "";
		for (int i = 0; i < text.length(); i++) {
			if ((text.charAt(i) + "").equals(" ")) {
				;
			} else {
				result = result + text.charAt(i);
			}
		}
		return result;
		/* TODO: strip blanks in 'text' */
	}

	public static String getWithoutComma(String text) {
		String result = "";
		for (int i = 0; i < text.length(); i++) {
			if ((text.charAt(i) + "").equals(","))
				;
			else
				result += text.charAt(i);
		}
		return result;
	}

	/*
	 * public static Node createTree(String treeRepresentationText) {
	 * //"(1,(2,(4),(5,,)),(3,,(6,(7),)))". Node result = null;
	 * result.setid(treeRepresentationText.charAt(1));
	 * 
	 * for(int i = 0; i < treeRepresentationText.length(); i++) {
	 * 
	 * } return result; }
	 */

	public static Node createTree(String text) {
		String result = "";
		Node root = new Node();
		int lBracket = 0;
		int rBracket = 0;
		int count = 0;
		int index = 0;
		int myNum = 0;

		Matcher matcher = Pattern.compile("\\d+").matcher(text);
		matcher.find();
		myNum = Integer.valueOf(matcher.group());
		root.setid(myNum);

		for (int i = index; i < text.length(); i++) {
			// if(text.charAt(i) == '(')
			// lBracket = i;
			if (text.charAt(i) == ',') {
				if (text.charAt(i + 1) == ',') {
					root.setLeftChild(null);
				} else if (text.charAt(i + 1) == '(') {
					root.setLeftChild(createTree(text.substring(i + 1)));
					break;
				} else if (text.charAt(i + 1) == ')') {
					return root;
				}
				else
					root.setRightChild(createTree(text.substring(i+1)));
			}
		}

		return root;
	}

	public static String traversePath(Node root, String direction) {
		Node currentNode = new Node();
		String result = "" + root.getid();
		for(int i = 0; i < direction.length(); i++) {
			if(direction.charAt(i) == 'L') {
				try {
					currentNode = currentNode.getLeftChild();
					result = result + " " + currentNode.getid();
				} catch(NullPointerException e) {
					result += " *"; 
				}
			}
			if(direction.charAt(i) == 'R') {
				try {
					currentNode = currentNode.getRightChild();
					result = result + " " + currentNode.getid();
				} catch(NullPointerException e) {
					result += " *"; 
				}
			}
		}
		
		return result;
	}

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		int nCases = in.nextInt();
		in.nextLine();

		for (int i = 0; i < nCases; ++i) {
			String treeRepresentationText = in.nextLine();
			treeRepresentationText = getCleanedString(treeRepresentationText);
			System.out.println("Testcase " + (i + 1) + ": "
					+ treeRepresentationText);

			Node root = createTree(treeRepresentationText);

			int nPaths = in.nextInt();
			in.nextLine();

			System.out.println("Output for testcase " + (i + 1));
			for (int j = 0; j < nPaths; ++j) {
				String path = in.nextLine();
				path = getCleanedString(path);

				String output = traversePath(root, path);

				System.out.println(path.trim() + ": " + output.trim());
			}
		}
		in.close();
	}
}

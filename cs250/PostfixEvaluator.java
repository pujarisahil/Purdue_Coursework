import java.util.Stack;

public class PostfixEvaluator {
	public static double evaluate(String[] arg) {
		double resultValue = 0;
		String operators = "+-*/";
 
		Stack<String> stack = new Stack<String>();
 
		for (String t : arg) {
			if (!operators.contains(t)) {
				stack.push(t);
			} else {
				int a = Integer.valueOf(stack.pop());
				int b = Integer.valueOf(stack.pop());
				switch (t) {
				case "+":
					stack.push(String.valueOf(a + b));
					break;
				case "-":
					stack.push(String.valueOf(b - a));
					break;
				case "*":
					stack.push(String.valueOf(a * b));
					break;
				case "/":
					stack.push(String.valueOf(b / a));
					break;
				}
			}
		}
 
		resultValue = Integer.valueOf(stack.pop());
 
		return resultValue;
	}
	
	public static void main(String[] args){
		String[] tokens = new String[] { "8", "7", "+", "2", "*" };
		System.out.println(evaluate(tokens));
	}
}


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

public class CopyOfPolynomial {
	List<ExpressionAtom> infixExpression = new ArrayList<ExpressionAtom>();
	
	List<ExpressionAtom> finalExpression;

	ListRepresentation listRepresentation;
	
	int leaves = 0 ;
	
	private static Map<String, Operators> operatorsMap = new HashMap<>();
	private static final int LEFT_ASSOCIATIVITY = 0;
	private static final int RIGHT_ASSOCIATIVITY = 1 ;
	
	static {
		operatorsMap.put("+", Operators.ADD);
		operatorsMap.put("-", Operators.SUBTRACT);
		operatorsMap.put("*", Operators.MULTIPLY);
		operatorsMap.put("/", Operators.DIVIDE);
		operatorsMap.put("^", Operators.EXP);
	}

	private enum Operators {
		ADD {
			@Override
			public int getAssociativity() {
				return LEFT_ASSOCIATIVITY;
			}

			@Override
			public int getPrecedence() {
				return 13;
			}

			@Override
			public double evaluate(double d1, double d2) {
				return d1 + d2;
			}
		},
		SUBTRACT {
			@Override
			public int getAssociativity() {
				return LEFT_ASSOCIATIVITY;
			}

			@Override
			public int getPrecedence() {
				return 13;
			}

			@Override
			public double evaluate(double d1, double d2) {
				return d1 - d2;
			}
		},
		MULTIPLY {
			@Override
			public int getAssociativity() {
				return LEFT_ASSOCIATIVITY;
			}

			@Override
			public int getPrecedence() {
				return 14;
			}

			@Override
			public double evaluate(double d1, double d2) {
				return d1 * d2;
			}
		},
		DIVIDE

		{
			@Override
			public int getAssociativity() {
				return LEFT_ASSOCIATIVITY;
			}

			@Override
			public int getPrecedence() {
				return 14;
			}

			@Override
			public double evaluate(double d1, double d2) {
				return d1 / d2;
			}
		},EXP{
			@Override
			public int getAssociativity() {
				return LEFT_ASSOCIATIVITY;
			}

			@Override
			public int getPrecedence() {
				return 15;
			}

			@Override
			public double evaluate(double d1, double d2) {
				return Math.pow(d1, d2);
			}
		};
		public abstract int getAssociativity();

		public abstract int getPrecedence();

		public abstract double evaluate(double d1, double d2);
	}
	
	private String identifyUnaryMinuses(String expression) {
		if(expression.startsWith("-")) {
			expression = "%" + expression.substring(1); 
		}
		
		char[] expressionAtoms = expression.toCharArray();
		String returnExpression = String.valueOf(expressionAtoms[0]);
		for(int i = 1; i < expressionAtoms.length; ++i) {
			if(expressionAtoms[i] == '-' && expressionAtoms[i-1] == '(')
				returnExpression += "%";
			else
				returnExpression += String.valueOf(expressionAtoms[i]);
		}
		
		return returnExpression;
	}
	
	private String insertMultiplicationSigns(String expression) {
		char[] expressionAtoms = expression.toCharArray();
		String returnExpression = String.valueOf(expressionAtoms[0]);
		
		for(int i = 1; i < expressionAtoms.length; ++i) {
			if(!isOperator(expressionAtoms[i]) && !Character.isDigit(expressionAtoms[i]) 
					&& Character.isDigit(expressionAtoms[i-1]))
				returnExpression += "*";
			returnExpression +=  String.valueOf(expressionAtoms[i]);
		}
		
		return returnExpression;
	}
	
	private boolean isOperator(char token) {
		return token == '+' || token == '-' || token == '*' || token == '^'
				|| token == '(' || token == ')';
	}
	
	private List<ExpressionAtom> parseInputPolynomial(String inputExpression) {
		inputExpression = identifyUnaryMinuses(inputExpression);
		inputExpression = insertMultiplicationSigns(inputExpression);
		
		List<ExpressionAtom> inputExpressionTokens = new ArrayList<ExpressionAtom>();
		
		char[] inputChars = inputExpression.toCharArray();
		for(int i = 0; i < inputChars.length; ++i) {
			if(isOperator(inputChars[i]) || inputChars[i] == '%') {
				inputExpressionTokens.add(new ExpressionAtom(String.valueOf(inputChars[i]), 
						AtomType.OPERATOR, 1));
			} else {
				int lastIndex = inputExpressionTokens.size() - 1;
				if(lastIndex >= 0 && inputExpressionTokens.get(lastIndex).getAtomType() 
						== AtomType.OPERAND) {
					ExpressionAtom lastElement = inputExpressionTokens.remove(lastIndex);
					if(Character.isDigit(inputChars[i])) {
						lastElement.setCoefficient(lastElement.getCoefficient() * 10 + 
								Character.getNumericValue(inputChars[i]));
					} else {
						lastElement.setVariablesOrOperator(lastElement.getVariablesOrOperator() + 
								String.valueOf(inputChars[i]));
					}
					inputExpressionTokens.add(lastElement);
				} else if(Character.isDigit(inputChars[i])) {
					inputExpressionTokens.add(new ExpressionAtom("", AtomType.OPERAND, 
							Character.getNumericValue(inputChars[i])));
				} else {
					inputExpressionTokens.add(new ExpressionAtom(String.valueOf(
							inputChars[i]), AtomType.OPERAND, 1));
				}
			}
		}
		
		return inputExpressionTokens;
	}
	
	private ListRepresentation convertToListRepresentation() {
//		for(ExpressionAtom atom : infixExpression){
//			System.out.println("Co : " + atom.getCoefficient()) ;
//			System.out.println("VarOrOpe : " + atom.getVariablesOrOperator()) ;
//			System.out.println("Type : " + atom.getAtomType().name() + "\n");
//		}
		
		SimpleQueue<ExpressionAtom> output = new SimpleQueue<ExpressionAtom>();
		SimpleStack<ExpressionAtom> stack = new SimpleStack<ExpressionAtom>();
		
		for(ExpressionAtom atom : infixExpression){
			if (atom.getAtomType() == AtomType.OPERAND) {
				output.enqueue(atom);
			} else if (atom.getAtomType() == AtomType.OPERATOR && !atom.getVariablesOrOperator().equals("(") && !atom.getVariablesOrOperator().equals(")")) {
				while (!stack.isEmpty()) {
					ExpressionAtom peek = stack.peek();
					if (isTopwithHigerPrecedence(peek, atom)) {
						output.enqueue(stack.pop());
					} else
						break;
				}
				stack.push(atom);
			} else if (atom.getVariablesOrOperator().equals("(")) {
				stack.push(atom);
			} else if (atom.getVariablesOrOperator().equals(")")) {
				boolean isLeftFound = false;
				while (!stack.isEmpty()) {
					ExpressionAtom top = stack.pop();
					if (!top.getVariablesOrOperator().equals("(")) {
						output.enqueue(top);
					} else {
						isLeftFound = true;
						break;
					}
				}
				if (!isLeftFound)
					throw new RuntimeException("Parantheses are not balanced");

			}

		}

		while (!stack.isEmpty()) {
			ExpressionAtom top = stack.pop();
			if (top.getVariablesOrOperator().equals("(") || top.getVariablesOrOperator().equals(")")) {
				throw new RuntimeException("Parantheses are not balanced");
			}
			output.enqueue(top);
		}
		
		boolean rep = false ;
		ListRepresentation temp = new ListRepresentation() ;
		temp.setNodeVal(new ExpressionAtom("" , null , 0));
		SimpleStack<ListRepresentation> operands = new SimpleStack<ListRepresentation>();
		while(!output.isEmpty()){
			ExpressionAtom atom = output.dequeue() ;
			if(atom.getAtomType() == AtomType.OPERAND){
				ListRepresentation operand = new ListRepresentation() ;
				operand.setNodeVal(atom);
				operands.push(operand);
			}else{
				
				if(output.size() != 0){
					String atomS = atom.getVariablesOrOperator() ;
					String peek = output.peek().getVariablesOrOperator() ;
					
					if(atomS.equals(peek) || (atomS.equals("+") && peek.equals("-"))
					   || (atomS.equals("-") && peek.equals("+"))) rep = true ;
					else rep = false ;
						
				}
				
//				if(atom.getVariablesOrOperator().equals("-")){
//					ListRepresentation peek = operands.peek() ;
//					if(peek.operands.size() == 0){
//						peek.getNodeVal().setCoefficient(peek.getNodeVal().getCoefficient() * -1);
//					}else{
//						peek.setNegative(true);
//					}
//					
//					atom = new ExpressionAtom("+", AtomType.OPERATOR, 1) ;
//				}
				
//				boolean canProceed;
//				if(atom.getVariablesOrOperator().equals("+")){
//					canProceed = rep ;
//				}else{
//					canProceed = true ;
//				}
//				
//				if(temp.getNodeVal().getVariablesOrOperator().equals(atom.getVariablesOrOperator()) && !atom.getVariablesOrOperator().equals("^")){
//					temp.operands.add(operands.pop()) ;
//					System.out.println("we");
//				}else{
					ListRepresentation operator = new ListRepresentation() ;
					operator.setNodeVal(atom);
					
					ListRepresentation op = operands.pop() ;

					operator.operands.add(operands.pop()) ;
					operator.operands.add(op) ;
					
					operands.push(operator);
					temp = operator ;
					leaves++ ;
					
//				}
				
				
			}
		}
		
		List<ListRepresentation> myList = new ArrayList<ListRepresentation>() ;
		while(leaves != 0){
			
		}
		
		listRepresentation = operands.pop();
		
//		String all = "" ;
//		while(!output.isEmpty()){
//			ExpressionAtom atom = output.dequeue() ;
//			all += ((atom.getAtomType() == AtomType.OPERAND) ? ((atom.getVariablesOrOperator().length() == 0) ? atom.getCoefficient() : atom.getVariablesOrOperator()) : atom.getVariablesOrOperator()) + " " ;
//		}
		
		
//		String sum = "" ;
//		
//		while(!output.isEmpty()){
//			ExpressionAtom atom = output.dequeue() ;
//			System.out.println("Co : " + atom.getCoefficient()) ;
//			System.out.println("VarOrOpe : " + atom.getVariablesOrOperator()) ;
//			System.out.println("Type : " + atom.getAtomType().name() + "\n");
//	
//		}
//		
		
		return listRepresentation ;
		/*
		 * TODO: Write code here to operate on this.infixExpression and obtain a ListRepresentation
		 * object that contains a list representation of the the original expression given
		 * 
		 * NOTE THAT WE WILL BE PRINTING THE ListRepresentation object immediately after
		 * this function is called, so make sure it is in the right form.
		 */
	}
	
	private static boolean isTopwithHigerPrecedence(ExpressionAtom peek, ExpressionAtom token) {
		if (!(peek.getAtomType() == AtomType.OPERATOR) || peek.getVariablesOrOperator().equals("(") || peek.getVariablesOrOperator().equals(")"))
			return false;

		Operators op1 = operatorsMap.get(peek.getVariablesOrOperator());
		Operators op2 = operatorsMap.get(token.getVariablesOrOperator());
		System.out.println(peek.getVariablesOrOperator() + " " + token.getVariablesOrOperator()) ;

		if (op2.getAssociativity() == LEFT_ASSOCIATIVITY
				&& op1.getPrecedence() >= op2.getPrecedence())
			return true;
		else if (op1.getPrecedence() >= op2.getPrecedence())
			return true;
		else
			return false;

	}
	
	
			
	private List<ExpressionAtom> evaluateExpression() {
		return null ;
		/*
		 * TODO: Write code here to operate on this.listRepresentation and obtain a List of
		 * ExpressionAtom objects which represent the atoms of the final expression. Note that you
		 * are allowed to have repeated variables here. This is not printed anywhere in the code, so
		 * you are free to return the ExpressionAtom objects in whatever form you wish.
		 */
	}

	private List<ExpressionAtom> simplifyAndNormalize(List<ExpressionAtom> evaluatedExpression) {
		return null ;
		/*
		 * TODO: Write code here to operate on the List<ExpressionAtom> object obtaind from
		 * 'evaluateExpression'. Specifically, ensure that you combine terms with same variables and
		 * modify coefficients appropriately.
		 */

	}
	
	public CopyOfPolynomial(String inputPolynomial) {
		this.infixExpression = parseInputPolynomial(inputPolynomial);
		
		this.listRepresentation = convertToListRepresentation();
	}
	
	private String sortString(String termVars) {
		char[] ar = termVars.toCharArray();
		Arrays.sort(ar);
		return String.valueOf(ar);
	}

	public void evaluate() {
		List<ExpressionAtom> evaluatedExpression = evaluateExpression();
		
		for(int i = 0; i < evaluatedExpression.size(); ++i) {
			evaluatedExpression.get(i).setVariablesOrOperator((sortString(
					evaluatedExpression.get(i).getVariablesOrOperator())));
		}
		
		this.finalExpression = simplifyAndNormalize(evaluatedExpression);
	}
}
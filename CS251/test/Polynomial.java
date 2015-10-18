
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Polynomial {
	List<ExpressionAtom> infixExpression = new ArrayList<ExpressionAtom>();
	List<ExpressionAtom> finalExpression;

	final SimpleStack<ExpressionAtom> simplyfiedExpression = new SimpleStack<ExpressionAtom>();
	final SimpleStack<ExpressionAtom> operandsStack = new  SimpleStack<ExpressionAtom>() ;
	
	ListRepresentation listRepresentation;

	private static Map<String, Operators> operatorsMap = new HashMap<>();
	private static final int LEFT_ASSOCIATIVITY = 0;
	
	static {
		operatorsMap.put("+", Operators.ADD);
		operatorsMap.put("-", Operators.SUBTRACT);
		operatorsMap.put("*", Operators.MULTIPLY);
		operatorsMap.put("^", Operators.EXP);
		operatorsMap.put("%", Operators.NEGATION);
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
			public List<ExpressionAtom> evaluate(List<ExpressionAtom> loperands,
												 List<ExpressionAtom> roperands) {
				return null ;
			}
		},NEGATION{

			@Override
			public int getAssociativity() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getPrecedence() {
				// TODO Auto-generated method stub
				return 15;
			}

			@Override
			public List<ExpressionAtom> evaluate(
					List<ExpressionAtom> loperands,
					List<ExpressionAtom> roperands) {
				// TODO Auto-generated method stub
				return null;
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
			public List<ExpressionAtom> evaluate(
					List<ExpressionAtom> loperands,
					List<ExpressionAtom> roperands) {
				// TODO Auto-generated method stub
				return null;
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
			public List<ExpressionAtom> evaluate(List<ExpressionAtom> loperands,List<ExpressionAtom> roperands) {
				return null ;
			}
		},EXP{
			@Override
			public int getAssociativity() {
				return LEFT_ASSOCIATIVITY;
			}

			@Override
			public int getPrecedence() {
				return 16;
			}

			@Override
			public List<ExpressionAtom> evaluate(
					List<ExpressionAtom> loperands,
					List<ExpressionAtom> roperands) {
				return null ;
			}
		};
		public abstract int getAssociativity();

		public abstract int getPrecedence();

		public abstract List<ExpressionAtom> evaluate(List<ExpressionAtom> loperands , List<ExpressionAtom> roperands) ;
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
		SimpleQueue<ExpressionAtom> output = new SimpleQueue<ExpressionAtom>();
		SimpleStack<ExpressionAtom> stack = new SimpleStack<ExpressionAtom>();
		
		for(ExpressionAtom atom : infixExpression){
			//Utils.printAtom(atom);
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
		
		ListRepresentation temp = new ListRepresentation() ;
		temp.setNodeVal(new ExpressionAtom("" , null , 0));
		SimpleStack<ListRepresentation> operands = new SimpleStack<ListRepresentation>();
		List<ListRepresentation> copy = new ArrayList<ListRepresentation>() ;
		
		//System.out.println();
		
		while(!output.isEmpty()){
			ExpressionAtom atom = output.dequeue() ;
			//Utils.printAtom(atom);
			operandsStack.push(atom) ;
			if(atom.getAtomType() == AtomType.OPERAND){
				ListRepresentation operand = new ListRepresentation() ;
				operand.setNodeVal(atom);
				operands.push(operand);
			}else{
				if(atom.getVariablesOrOperator().equals("-") || atom.getVariablesOrOperator().equals("%")){
					ListRepresentation peek = operands.peek() ;
					if(peek.operands.size() == 0){
						peek.getNodeVal().setCoefficient(peek.getNodeVal().getCoefficient() * -1);
					}else if(peek.getNodeVal().getVariablesOrOperator().equals("*")){
						peek.operands.get(0).getNodeVal().setCoefficient(peek.operands.get(0).getNodeVal().getCoefficient() * -1);
					}else{
						peek.setNegative(true);
					}

					if(atom.getVariablesOrOperator().equals("%")){
						if(peek.operands.size() == 0) continue ;
						else{
							continue;
						}
					}
					atom = new ExpressionAtom("+", AtomType.OPERATOR, 1) ;
				}
					ListRepresentation operator = new ListRepresentation() ;
					operator.setNodeVal(atom);
					
					ListRepresentation op = operands.pop() ;

					operator.operands.add(operands.pop()) ;
					operator.operands.add(op) ;
					
					copy.add(operator);
					operands.push(operator);
					temp = operator ;
			}
		}
		
		ListRepresentation cop = operands.pop() ;
		validate(cop , cop);
		listRepresentation = cop ;
		
		return listRepresentation ;
	}
	
	public static void iterate(ListRepresentation listExpression) {
		if(listExpression.operands.isEmpty()) {
			
		} else {
			
			for(int i = 0 ; i < listExpression.operands.size(); ++i) {
				iterate(listExpression.operands.get(i)) ;
			}
			printMe(listExpression) ;
		}
	}
	
	public static void printMe(ListRepresentation listExpression){
		for(int x = 0 ; x < listExpression.operands.size() ; x++){
			ExpressionAtom atom = listExpression.operands.get(x).getNodeVal() ;
			//Utils.printAtom(atom);
		}
	}
	
	
	public static void validate(ListRepresentation listExpression , ListRepresentation parent) {
		if(listExpression.operands.isEmpty()) {
			validateChilds(listExpression) ;
			validateParent(listExpression) ;
		} else {
			validateParent(listExpression) ;
			for(int i = 0 ; i < listExpression.operands.size(); ++i) {
				validate(listExpression.operands.get(i) , listExpression);
			}
			validateParent(listExpression) ;
		}
	}

	private static void validateParent(ListRepresentation list){
		for(int x = 0 ; x < list.operands.size() ; x++){
			ListRepresentation current = list.operands.get(x) ;
			if(current.getNodeVal().getAtomType() == AtomType.OPERATOR){
				if(current.getNodeVal().getVariablesOrOperator().equals(list.getNodeVal().getVariablesOrOperator())){
					list.operands.addAll(0 , current.operands) ;
					list.operands.remove(current) ;
				}
			}
		}
	}
	
	private static void validateChilds(ListRepresentation list){
		for(int i = 0 ; i < list.operands.size() ; i++){
			if(i + 1 >= list.operands.size()){
				break ;
			}else{
				if(list.operands.get(i).getNodeVal().getAtomType() == AtomType.OPERATOR){
					String lo = list.operands.get(i).getNodeVal().getVariablesOrOperator();
					String high = list.operands.get(i + 1).getNodeVal().getVariablesOrOperator();
					
					if(lo.equals(high)){
						list.operands.get(i).operands.addAll(0 , list.operands.get(i + 1).operands) ;
						list.operands.remove(i + 1);
					}
				}	
			}
		}
	}
	
	private static boolean isTopwithHigerPrecedence(ExpressionAtom peek, ExpressionAtom token) {
		if (!(peek.getAtomType() == AtomType.OPERATOR) || peek.getVariablesOrOperator().equals("(") || peek.getVariablesOrOperator().equals(")"))
			return false;

		//if(peek.getVariablesOrOperator().equals("%")) return true ;
		Operators op1 = operatorsMap.get(peek.getVariablesOrOperator());		
		Operators op2 = operatorsMap.get(token.getVariablesOrOperator());
		
		if (op2.getAssociativity() == LEFT_ASSOCIATIVITY
				&& op1.getPrecedence() >= op2.getPrecedence())
			return true;
		else if (op1.getPrecedence() >= op2.getPrecedence())
			return true;
		else
			return false;

	}
	
	
			
	private List<ExpressionAtom> evaluateExpression() {
		return listRepresentation.simplify() ;
	}

	private List<ExpressionAtom> simplifyAndNormalize(List<ExpressionAtom> evaluatedExpression) {
		for(int x = 0 ; x < evaluatedExpression.size() - 1; x++){
			ExpressionAtom left = evaluatedExpression.get(x) ;
			for(int y = x + 1 ; y  < evaluatedExpression.size() ; y++){
				ExpressionAtom right = evaluatedExpression.get(y) ;
				
				if(left.getVariablesOrOperator().equals(right.getVariablesOrOperator())){
					left.setCoefficient(left.getCoefficient() + right.getCoefficient());
					evaluatedExpression.remove(right) ;
				}
			}
		}
		return evaluatedExpression; 
	}
	
	public Polynomial(String inputPolynomial) {
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
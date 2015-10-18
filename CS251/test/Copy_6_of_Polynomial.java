
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Copy_6_of_Polynomial {
	List<ExpressionAtom> infixExpression = new ArrayList<ExpressionAtom>();
	List<ExpressionAtom> finalExpression;

	final static ArrayList<ExpressionAtom> simplyfiedExpression = new ArrayList<ExpressionAtom>();
	final static SimpleStack<ExpressionAtom> computationStack = new SimpleStack<ExpressionAtom>();
	final static SimpleQueue<List<ExpressionAtom>> expressionQueue = new SimpleQueue<List<ExpressionAtom>>();
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
				List<ExpressionAtom> match = match(loperands) ;
				while(match != null){
					ExpressionAtom loperand = match.get(0) ;
					ExpressionAtom roperand = match.get(1) ;
					String degree = roperand.getVariablesOrOperator() ;
					int coefficient = loperand.getCoefficient() + roperand.getCoefficient() ;
					ExpressionAtom sum = new ExpressionAtom(degree, AtomType.OPERAND, coefficient);
					loperands.remove(loperand) ;
					loperands.remove(roperand) ;
					loperands.add(sum) ;
					
					match = match(loperands) ;
				}

				return loperands;
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
				return 14;
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
				return 15;
			}

			@Override
			public List<ExpressionAtom> evaluate(List<ExpressionAtom> loperands,List<ExpressionAtom> roperands) {
				if(roperands == null){
					List<ExpressionAtom> product = new ArrayList<ExpressionAtom>() ;
					for(int x = 0 ; x < loperands.size() ; x++){
						if(product.size() == 0){
							product.add(loperands.get(x)) ;
						}else{
							ExpressionAtom atom = product.get(0) ;
							atom.setCoefficient(atom.getCoefficient() * loperands.get(x).getCoefficient());
							String var = incrementDegree(loperands.get(x).getVariablesOrOperator() , atom.getVariablesOrOperator()) ;
							atom.setVariablesOrOperator(var);
						}
					}
					return product ;
				}else{
					return multiple(loperands, roperands) ;
				}
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
				if(roperands == null){
					return multiple(loperands, loperands) ;
				}else{
					return multiple(loperands, roperands) ;
				}
				
			}
		};
		public abstract int getAssociativity();

		public abstract int getPrecedence();

		public abstract List<ExpressionAtom> evaluate(List<ExpressionAtom> loperands , List<ExpressionAtom> roperands) ;
	}
	
	public static List<ExpressionAtom> multiple(List<ExpressionAtom> loperands , List<ExpressionAtom> roperands){
		List<ExpressionAtom> product = new ArrayList<ExpressionAtom>() ;
		for(int left = 0 ; left < loperands.size() ; left++){
			for(int right = 0 ; right < roperands.size() ; right++){
				ExpressionAtom atomLeft = loperands.get(left) ;
				ExpressionAtom atomRight = roperands.get(right) ;
				
				int coefficient = atomLeft.getCoefficient() * atomRight.getCoefficient() ;
				String leftVar = atomLeft.getVariablesOrOperator() ;
				String rightVar = atomRight.getVariablesOrOperator() ;
				String var = incrementDegree(leftVar , rightVar) ;
				product.add(new ExpressionAtom(var, AtomType.OPERATOR , coefficient)) ;
			}
		}
		return product ;
	}
	
	private static String incrementDegree(String left , String right){
		ArrayList<String> leftList = formatTerms(left) ;
		ArrayList<String> rightList = formatTerms(right) ;
		
		for(int x = 0 ; x < leftList.size() ; x++){
			String myLeft = leftList.get(x) ;
			for(int y = 0 ; y < rightList.size() ; y++){
				String myRight = rightList.get(y) ;
				if(myLeft.charAt(0) == myRight.charAt(0)){
					String newOperand = myLeft.charAt(0) + "^" + (Integer.parseInt(myLeft.substring(2 , myLeft.length())) + Integer.parseInt(myRight.substring(2 , myRight.length()))) ;
					leftList.add(newOperand) ;
					leftList.remove(x) ;
					rightList.remove(y) ;
				}
			}
		}
		
		String temp = "" ;
		for(int x = 0 ; x < leftList.size() ; x ++){
			if(leftList.get(x).length() == 3 && leftList.get(x).charAt(leftList.get(x).length() - 1) == '1'){
				temp += leftList.get(x).charAt(0) + "" ;
			}else{
				temp += leftList.get(x) ;
			}
		}
		for(int x = 0 ; x < rightList.size() ; x ++){
			if(rightList.get(x).length() == 3 && rightList.get(x).charAt(rightList.get(x).length() - 1) == '1'){
				temp += rightList.get(x).charAt(0) + "" ;
			}else{
				temp += rightList.get(x) ;
			}
		}
		return temp ;
	}
	
	private static ArrayList<String> formatTerms(String operand){
		ArrayList<String> temp = new ArrayList<String>() ;
		for(int x = 0 ; x < operand.length() ; x++){
			if(Character.isDigit(operand.charAt(x)) || operand.charAt(x) == '^') continue ;
			if(x + 1 >= operand.length()){
				temp.add(operand.charAt(x) + "^" + 1) ;
			}else if(operand.charAt(x + 1) != '^'){
				temp.add(operand.charAt(x) + "^" + 1) ;
			}else{
				int y = 0 ;
				for(y = x + 2 ; y < operand.length() ; y++){
					if(!Character.isDigit(operand.charAt(y))) break ;
				}
				temp.add(operand.substring(x , y)) ;
			}
		}
		return temp;
	}
	
	public static List<ExpressionAtom> match(List<ExpressionAtom> operands){
		List<ExpressionAtom> binomial = new ArrayList<ExpressionAtom>() ;
		for(int l = 0 ; l < operands.size() - 1 ; l++){
			ExpressionAtom left = operands.get(l) ;
			if(left.getAtomType() == AtomType.OPERATOR) continue ;
			for(int r = l + 1 ; r < operands.size() ; r++){
				ExpressionAtom right = operands.get(r) ;
				if(right.getAtomType() == AtomType.OPERATOR) continue ;
				else{
					if(right.getVariablesOrOperator().equals(left.getVariablesOrOperator())){
						binomial.add(left) ;
						binomial.add(right) ;
						return binomial ;
					}
				}
			}
		}
		return null ;
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
			Utils.printAtom(atom);
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
		
		System.out.println();
		
		while(!output.isEmpty()){
			ExpressionAtom atom = output.dequeue() ;
			Utils.printAtom(atom);
			if(atom.getAtomType() == AtomType.OPERAND){
				ListRepresentation operand = new ListRepresentation() ;
				operand.setNodeVal(atom);
				operands.push(operand);
			}else{
				if(atom.getVariablesOrOperator().equals("-") || atom.getVariablesOrOperator().equals("%")){
					ListRepresentation peek = operands.peek() ;
					if(peek.operands.size() == 0){
						peek.getNodeVal().setCoefficient(peek.getNodeVal().getCoefficient() * -1);
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
		evaluateExpr(listRepresentation) ;
		while(!expressionQueue.isEmpty()){
			List<ExpressionAtom> atoms = expressionQueue.dequeue() ;
			for(int x = atoms.size() - 1 ; x >= 0 ; x--){
				simplyfiedExpression.add(atoms.get(x)) ;
			}
		}
		
		//TODO
		List<ExpressionAtom> sample = new ArrayList<ExpressionAtom>() ;
		sample.add(new ExpressionAtom("aa" , AtomType.OPERAND , 1)) ;
		
		return sample ;
	}
	
	public void evaluateExpr(ListRepresentation listExpression) {
		if(listExpression.operands.isEmpty()) {
		} else {
			for(int i = 0 ; i < listExpression.operands.size(); ++i) {
				evaluateExpr(listExpression.operands.get(i));
			}
			insertOperandToStack(listExpression) ;
			processOperator(listExpression) ;
		}
	}
	
	public void processOperator(ListRepresentation listExpression){
		String operator = listExpression.getNodeVal().getVariablesOrOperator() ;
		if(computationStack.isEmpty()){
				List<List<ExpressionAtom>> all = new ArrayList<List<ExpressionAtom>>() ;
				while(!expressionQueue.isEmpty()){
					all.add(expressionQueue.dequeue()) ;
				}
				List<ExpressionAtom> main = null ;
				if(operator.equals("*")){
					for(int x = 0 ; x < all.size() - 1; x++){
						if(x + 1 >= all.size()){
							System.out.println("ERROR @ Multiplicaiton NO DATA") ;
						}else{
							if(main == null){
								main = operatorsMap.get(operator).evaluate(all.get(x), all.get(x + 1)) ;
							}else{
								main = operatorsMap.get(operator).evaluate(main, all.get(x + 1)) ;
							}
						}
					}
					expressionQueue.enqueue(main);
				}else if(operator.equals("+")){
					main = new ArrayList<ExpressionAtom>() ;
 					for(int x = 0 ; x < all.size(); x++){
						for(int y = 0 ; y < all.get(x).size() ; y++){
							main.add(all.get(x).get(y)) ;
						}
					}
					expressionQueue.enqueue(operatorsMap.get(operator).evaluate(main , null));
				}
		}
		
		while(!computationStack.isEmpty()){
			if(computationStack.size() == 1){
				List<ExpressionAtom> prev = expressionQueue.dequeue() ;
				List<ExpressionAtom> loperands = new ArrayList<ExpressionAtom>();
				while(!prev.isEmpty()){
					loperands.add(prev.get(0)) ;
					prev.remove(0) ;
				}
				List<ExpressionAtom> roperands = new ArrayList<ExpressionAtom>();
				while(!computationStack.isEmpty()){
					roperands.add(computationStack.pop()) ;
				}
				if(operator.equals("^")){
					ExpressionAtom degree = roperands.get(0) ;
					List<ExpressionAtom> copy = null ;
					for(int x = 1 ; x < degree.getCoefficient() ; x++){
						if(x == 1){
							copy = operatorsMap.get(operator).evaluate(loperands , loperands);
						}else{
							operatorsMap.get(operator).evaluate(copy , loperands);
						}
					}
					expressionQueue.enqueue(copy);
				}else{
					expressionQueue.enqueue(operatorsMap.get(operator).evaluate(loperands , roperands));
				}

			}else{
				List<ExpressionAtom> loperands = new ArrayList<ExpressionAtom>();
				while(!computationStack.isEmpty()){
					loperands.add(computationStack.pop()) ;
				}
				if(operator.equals("^")){
					int degree = loperands.get(0).getCoefficient() ;
					List<ExpressionAtom> main = new ArrayList<ExpressionAtom>();
					for(int ope = 1 ; ope < loperands.size() ; ope++){
						main.add(loperands.get(ope)) ;
					}
					
					List<ExpressionAtom> copy = null ;
					for(int c = 1 ; c < degree ; c++){
						if(c == 1){
							copy = operatorsMap.get(operator).evaluate(main , null) ;
						}else{
							copy = operatorsMap.get(operator).evaluate(main , copy) ;
						}
					}
					
					if(listExpression.isNegative()){
						List<ExpressionAtom> mult = new ArrayList<ExpressionAtom>() ;
						mult.add(new ExpressionAtom("", AtomType.OPERAND, -1)) ;
						copy = operatorsMap.get("*").evaluate(copy , mult) ;
					}
					
					expressionQueue.enqueue(copy);
				}else{
					expressionQueue.enqueue(operatorsMap.get(operator).evaluate(loperands , null));
				}
			}
		}
		
		Utils.printExpression(expressionQueue.peek());
	}
	
	public void insertOperandToStack(ListRepresentation listExpression){

		for(int x = 0 ; x < listExpression.operands.size() ; x++){
			ListRepresentation current = listExpression.operands.get(x) ;
			if(current.getNodeVal().getAtomType() == AtomType.OPERAND){
				computationStack.push(current.getNodeVal()) ;
			}
		}
	}
	
	private String fixVariablesOrOperator(String var){
		ArrayList<String> term = formatTerms(var);
		term.sort(new Comparator<String>(){
			@Override
			public int compare(String arg0, String arg1) {
				if(arg0.charAt(0) == arg1.charAt(0)) return 0 ;
				else if(arg0.charAt(0) > arg1.charAt(0)) return 1 ;
				else return -1 ;
			}
			
		});
		String temp = "" ;
		for(int x = 0 ; x < term.size() ; x++){
			if(term.get(x).length() == 3 && term.get(x).charAt(term.get(x).length() - 1) == '1'){
				temp += term.get(x).charAt(0) + "" ;
			}else{
				temp+=term.get(x) ;
			}
		}
		return temp ;
	}

	private List<ExpressionAtom> simplifyAndNormalize(List<ExpressionAtom> evaluatedExpression) {
		
		for(int x = 0 ; x < evaluatedExpression.size() ; x++){
			evaluatedExpression.get(x).setVariablesOrOperator(new StringBuilder(evaluatedExpression.get(x).getVariablesOrOperator()).reverse().toString());
			String newVar = fixVariablesOrOperator(evaluatedExpression.get(x).getVariablesOrOperator());
			evaluatedExpression.get(x).setVariablesOrOperator(newVar);
		}
		
		for(int x = 0 ; x < evaluatedExpression.size() - 1; x++){
			ExpressionAtom first = evaluatedExpression.get(x) ;
			for(int y = x + 1 ; y <  evaluatedExpression.size() ; y++){
				ExpressionAtom second = evaluatedExpression.get(y) ;
				if(first.getVariablesOrOperator().equals(second.getVariablesOrOperator())){
					first.setCoefficient(first.getCoefficient() + second.getCoefficient());
					evaluatedExpression.remove(second);
				}
			}	
		}
		
		return evaluatedExpression; 
		/*
		 * TODO: Write code here to operate on the List<ExpressionAtom> object obtaind from
		 * 'evaluateExpression'. Specifically, ensure that you combine terms with same variables and
		 * modify coefficients appropriately.
		 */

	}
	
	public Copy_6_of_Polynomial(String inputPolynomial) {
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
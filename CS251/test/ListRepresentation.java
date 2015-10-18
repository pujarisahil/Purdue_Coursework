
import java.util.ArrayList;
import java.util.List;

public class ListRepresentation {
	private ExpressionAtom nodeVal = null;
	public List<ListRepresentation> operands = new ArrayList<ListRepresentation>();
	private boolean isNegative = false;
	
	public void setNodeVal(ExpressionAtom inputNodeVal) {
		this.nodeVal = inputNodeVal;
	}
	
	public void setNegative(boolean inputIsNegative) {
		this.isNegative = inputIsNegative;
	}
		
	public ExpressionAtom getNodeVal() {
		return this.nodeVal;
	}
	
	public boolean isNegative() {
		return this.isNegative;
	}
	
	public List<ExpressionAtom> simplify(){
		List<ExpressionAtom> temp = new ArrayList<ExpressionAtom>() ;
		List<List<ExpressionAtom>> sim = solve() ;
		for(List<ExpressionAtom> value : sim){
			temp.addAll(value) ;
		}
		return temp ;
	}
	
	private List<List<ExpressionAtom>> solve() {
		List<List<ExpressionAtom>> count = new ArrayList<List<ExpressionAtom>>() ;
		if(nodeVal.getAtomType() == AtomType.OPERAND) {
			List<ExpressionAtom> node = new ArrayList<ExpressionAtom>() ;
			node.add(nodeVal) ;
			count.add(node) ;
			return count ;
		} else {
			for(int i = 0 ; i < operands.size(); ++i) {
				count.addAll(operands.get(i).solve()) ;
			}
			return ((isNegative) ? negate(calculate(count)) : calculate(count)) ;
		}
	}
	
	private List<List<ExpressionAtom>> negate(List<List<ExpressionAtom>> list){
		List<ExpressionAtom> negativeList = new ArrayList<ExpressionAtom>() ;
		negativeList.add(new ExpressionAtom("" , AtomType.OPERAND , -1)) ;
		list.add(negativeList) ;
		return multiply(list, false) ;
	}
	
	private List<List<ExpressionAtom>> calculate(List<List<ExpressionAtom>> list){
		int size = list.size() ;
		if(nodeVal.getVariablesOrOperator().equals("+")){
			List<ExpressionAtom> all = new ArrayList<ExpressionAtom>() ;
			for(List<ExpressionAtom> node : list){
				all.addAll(node) ;
			}
			for(int l = 0 ; l < all.size(); l++){
				ExpressionAtom left = all.get(l) ;
				for(int r = 0 ; r < all.size() ; r++){
					ExpressionAtom right = all.get(r) ;
					if(left.equals(right)) continue ;
				
					if(left.getVariablesOrOperator().equals(right.getVariablesOrOperator())){
						left.setCoefficient((left.getCoefficient() + right.getCoefficient()));
						all.remove(right) ;
					}
				}
			}
			
			List<List<ExpressionAtom>> samp = new ArrayList<List<ExpressionAtom>>() ;
			samp.add(all) ;
			
			return samp ;
		}else if(nodeVal.getVariablesOrOperator().equals("*")){
			return multiply(list , false) ;
		}else if(nodeVal.getVariablesOrOperator().equals("^")){
			int degree = list.get(list.size() - 1).get(0).getCoefficient() ;
			list.remove(list.size() - 1) ;
			
			List<List<ExpressionAtom>> samp = null ;
			for(int l = 0 ; l < list.size() ; l++){
				List<ExpressionAtom> currentList = list.get(l) ;
				for(int x = 1 ; x < degree ; x++){
					if(samp == null){
						samp = new ArrayList<List<ExpressionAtom>>() ;
						samp.add(currentList) ;
						samp = multiply(samp, true) ;
					}else{
						samp.add(currentList) ;
						samp = multiply(samp , false);
					}
				}
				list = samp ;
				samp = null ;
			}
			
		}
		
		return list ;
	}

	private List<List<ExpressionAtom>> multiply(List<List<ExpressionAtom>> list ,boolean canRepeat){
		for(int l = 0 ; l < list.size(); l++){
			List<ExpressionAtom> left = list.get(l) ;
			for(int r = 0 ; r < list.size() ; r++){
				List<ExpressionAtom> right = list.get(r) ;
				if(left.equals(right) && !canRepeat) continue ;
				
				List<ExpressionAtom> all = new ArrayList<ExpressionAtom>() ;

				for(int l1 = 0 ; l1 < left.size() ; l1++){
					ExpressionAtom left1 = left.get(l1) ;
					for(int r1 = 0 ; r1 < right.size() ; r1++){
						ExpressionAtom right1 = right.get(r1) ;
						if(left1 == right1 && !canRepeat) continue ;
						ExpressionAtom newAtom = new ExpressionAtom(left1.getVariablesOrOperator() + right1.getVariablesOrOperator() ,
																    AtomType.OPERAND , (left1.getCoefficient() * right1.getCoefficient())) ;
						
						all.add(newAtom) ;
					}
				}
				list.remove(left) ;
				list.remove(right) ;
				list.add(all) ;
			}
		}
		
		if(isNegative){
			
		}
		
		return list;
	}
}
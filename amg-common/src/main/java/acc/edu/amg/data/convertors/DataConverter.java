package acc.edu.amg.data.convertors;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import acc.edu.amg.data.Equation;
import acc.edu.amg.data.EquationData;
import acc.edu.amg.exceptions.CalculationException;

public class DataConverter {

	public static EquationData convertEquation(Equation eq, double[] xVector) throws CalculationException{
		if(eq == null)
			throw new CalculationException("Cannot convert data as equation is null");
		if(xVector == null || xVector.length == 0)
			throw new CalculationException("Cannot convert data as x vector is null or empty");
		EquationData result = new EquationData();
		result.setB(eq.getB());
		result.setaVector(Arrays.copyOf(eq.getaVector(), eq.getaVector().length));
		int index;
		double[] xVec = new double[eq.getaVector().length];
		result.setxVector(xVec);
		for(int i = 0; i < eq.getiVector().length; i++) {
			index = eq.getiVector()[i];
			if(index == eq.getiIndex()) {
				result.setiIndex(i);
			}
			try {
				xVec[i] = xVector[index];
			}catch(IndexOutOfBoundsException e) {
				throw new CalculationException("x vector size does not match index stored in equation");
			}
		}
		return result;
	}
	
	public static EquationData[] convertEquations(Equation[] equations, double[] xVector) throws CalculationException {
		if(equations == null || equations.length == 0)
			throw new CalculationException("Equation array is null or empty");
		EquationData[] result = new EquationData[equations.length];
		for(int i = 0; i < equations.length; i++)
			result[i] = convertEquation(equations[i], xVector);
		return result;
	}
	
	public static List<EquationData[]> convertEquations(Equation[] equations, double[] xVector, int bulkSize) throws CalculationException {
		if(equations == null || equations.length == 0)
			throw new CalculationException("Equation array is null or empty");
		List<EquationData[]> result = new LinkedList<>();
		int start = 0, end;
		while(start < equations.length) {
			end = start + bulkSize;
			if(end > equations.length)
				end = equations.length;
			result.add(convertEquations(Arrays.copyOfRange(equations, start, end), xVector));
			start = end;
		}
		return result;
	}

}

package acc.edu.amg.data.convertors;

import java.util.Arrays;

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
}

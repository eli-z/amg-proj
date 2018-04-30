package acc.edu.amg.smothers.concrete;

import acc.edu.amg.exceptions.CalculationException;

public class JacobiSmoother {
	public static double smooth(double[] aVec, double[] xVec, double b, int varIndex) throws CalculationException {
		if(checkArrays(aVec, xVec) && inBounds(varIndex, aVec.length))
		{
			double sum = 0d;
			for(int i = 0; i < aVec.length; i++) 
			{
				if(i == varIndex)
					continue;
				sum += aVec[i] * xVec[i];
			}
			xVec[varIndex] = 1d / aVec[varIndex] * (b - sum);
			return xVec[varIndex];
				
		}
		throw new CalculationException("Not passes preliminary checks");
	}

	private static boolean inBounds(int varIndex, int length) {
		return varIndex >= 0 && varIndex < length;
	}

	private static boolean checkArrays(double[] aVec, double[] xVec) {
		return aVec != null && xVec != null && aVec.length == xVec.length;
	}
}

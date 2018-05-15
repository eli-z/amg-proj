package acc.edu.matri.generator;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import acc.edu.amg.data.Equation;
import acc.edu.amg.data.MatrixData;
import acc.edu.kube.comm.RestTemplateTool;
import acc.edu.kube.comm.responses.SmootherAlgoResponse;
import acc.edu.kube.exceptions.RestException;

public class MatrixDataGenerator {
	public static void main(String[] args) throws RestException {
		int dimention = 10;
		RestTemplateTool rt = new RestTemplateTool();
		rt.setRestTemplate(new RestTemplate());
		while(dimention <= 1_000_000) {
		//String json = new Gson().toJson(generate1m21Pattern(10000));
		MatrixData md = generate1m21Pattern(dimention);
		SmootherAlgoResponse sar = rt.doExchange(HttpMethod.POST, "smoother-algo", "/smoother-algo/jacobi/3", md, SmootherAlgoResponse.class);
		System.out.println(dimention + "," +sar.getTimeTookMilli() + "," + ((double)sar.getAvgIterationsTime() / 1_000_000d));
		dimention *= 10;
		}
//		System.out.println(new Gson().toJson(generate1m21Pattern(3)));
				
	}
	
	public static MatrixData generate1m21Pattern(int dimention) {
		MatrixData data = new MatrixData();
		data.setbVector(new double[dimention]);
		data.setxVector(makeOnesVector(dimention));
		double[] aVector = new double[3];
		aVector[0] = 1;
		aVector[1] = -2;
		aVector[2] = 1;
		int iIndex = 1;
		Equation tmp;
		Equation[] eqs = new Equation[dimention];
		data.setEquations(eqs);
		int[] iVector;
		for(int i = 0; i < dimention; i++) {
			tmp = new Equation();
			tmp.setaVector(aVector);
			tmp.setB(0);
			tmp.setiIndex(iIndex);
			iVector = new int[3];
			iVector[0] = i == 0 ? (dimention - 1) : (i - 1);
			iVector[1] = i;
			iVector[2] = i == (dimention -1 ) ? 0 : (i + 1);  
			tmp.setiVector(iVector);
			eqs[i] = tmp;
		}
		return data;
	}

	private static double[] makeOnesVector(int dimention) {
		double[] result = new double[dimention];
		for(int i =0; i < dimention; i++)
			result[i] = 1d;
		return result;
	}
}

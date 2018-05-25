package business;

import java.util.ArrayList;

public enum Forme {
	simple, carre;

	public ArrayList<double[]> creerPosition(Forme f) {
		ArrayList<double[]> res = new ArrayList<double[]>();
		double[] coo;
		switch(f)
		{
			case simple:
				coo = new double[2];
				coo[0] = 6;
				coo[1] = 6;	
				res.add(coo);
				break;
			case carre:
				coo = new double[2];
				coo[0] = 4;
				coo[1] = 4;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 4;
				coo[1] = 8;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 8;
				coo[1] = 8;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 8;
				coo[1] = 4;	
				res.add(coo);
				break;
		}
		return res;
	}
	
	
	
	

}

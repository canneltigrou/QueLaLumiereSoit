package business;

import java.util.ArrayList;

public enum Forme {
	simple, carre, ethane, propane, butane, methanol, ethan1ol, ethan2ol, propan1ol, propan2ol, propan3ol;

	// l'ensemble se fait sur 
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
			case ethane:
				coo = new double[2];
				coo[0] = 4;
				coo[1] = 6;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 7;
				coo[1] = 6;	
				res.add(coo);
				break;
			case propane:
				coo = new double[2];
				coo[0] = 3;
				coo[1] = 6;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 6;
				coo[1] = 6;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 9;
				coo[1] = 6;	
				res.add(coo);
				break;
			case butane:
				coo = new double[2];
				coo[0] = 4;
				coo[1] = 6;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 7;
				coo[1] = 6;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 1;
				coo[1] = 6;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 10;
				coo[1] = 6;	
				res.add(coo);
				break;
			case methanol:
				coo = new double[2];
				coo[0] = 6;
				coo[1] = 4;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 6;
				coo[1] = 7;	
				res.add(coo);
				break;
			case ethan1ol:
				coo = new double[2];
				coo[0] = 4;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 7;
				coo[1] = 7;	
				res.add(coo);
				coo[0] = 4;
				coo[1] = 4;	
				res.add(coo);
				break;
			case ethan2ol:
				coo = new double[2];
				coo[0] = 4;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 7;
				coo[1] = 7;	
				res.add(coo);
				coo[0] = 7;
				coo[1] = 4;	
				res.add(coo);
				break;
			case propan1ol:
				coo = new double[2];
				coo[0] = 3;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 6;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 9;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 3;
				coo[1] = 4;	
				res.add(coo);
				break;
			case propan2ol:
				coo = new double[2];
				coo[0] = 3;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 6;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 9;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 6;
				coo[1] = 4;	
				res.add(coo);
				break;
			case propan3ol:
				coo = new double[2];
				coo[0] = 3;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 6;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 9;
				coo[1] = 7;	
				res.add(coo);
				coo = new double[2];
				coo[0] = 9;
				coo[1] = 4;	
				res.add(coo);
				break;
				
		}
		return res;
	}
	
	
	
	

}

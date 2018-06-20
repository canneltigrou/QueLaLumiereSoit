package business;

import javafx.scene.paint.Color;

public enum Couleur { RED, BLUE, YELLOW, GREEN, AQUA, DARKORANGE, BISQUE, BLUEVIOLET, DARKSALMON, CYAN, BURLYWOOD, CORAL;
	
	
	public Color getColor(Couleur clr){
		if (clr == Couleur.YELLOW)
			return(Color.YELLOW);
		if (clr == RED )
			return(Color.RED);
		if (clr == BLUE )
			return(Color.BLUE);
		if (clr == GREEN )
			return(Color.GREEN);
		if (clr == AQUA)
			return(Color.AQUA);
		if (clr == BISQUE)
			return(Color.BISQUE);
		if (clr == BLUEVIOLET )
			return(Color.BLUEVIOLET);
		if (clr == BURLYWOOD)
			return(Color.BURLYWOOD);
		if (clr == CORAL)
			return(Color.CORAL);
		if (clr == CYAN)
			return(Color.CYAN);
		if (clr == DARKSALMON )
			return(Color.DARKSALMON);
		if (clr == DARKORANGE)
			return(Color.DARKORANGE);
		
		return(Color.GRAY);
	}

	/*
	public void getNewCouleur(){
		Couleur[] couleurListe = Couleur.values();
		int indiceCouleur = (int) (Math.random() * ( couleurListe.length ));
		this = couleurListe[indiceCouleur];
	}*/
}



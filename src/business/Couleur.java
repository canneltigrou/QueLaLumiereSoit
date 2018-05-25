package business;

import javafx.scene.paint.Color;

public enum Couleur { WHITE, RED, BLUE, YELLOW, GREEN;
	
	
	public Color getColor(Couleur clr){
	if (clr == Couleur.YELLOW)
		return(Color.YELLOW);
	if (clr == RED )
		return(Color.RED);
	if (clr == WHITE )
		return(Color.WHITE);
	if (clr == BLUE )
		return(Color.BLUE);
	if (clr == GREEN )
		return(Color.GREEN);
	
	return(Color.GRAY);
}
}



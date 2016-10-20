

//enum för kortens värde
public enum Face {
	TVÅ(2),
	TRE(3),
	FYRA(4),
	FEM(5),
	SEX(6),
	SJU(7),
	ÅTTA(8),
	NIO(9),
	TIO(10),
	KNEKT(10),
	DAM(10),
	KUNG(10),
	ESS(11);

	//instansvariabel
	private int kortVärde;
	
	//konstruktor
	private Face(int kortVärde){
		this.kortVärde = kortVärde;
	}
	//getter
	private int getCardValue(){
		return kortVärde;
	}
	
}

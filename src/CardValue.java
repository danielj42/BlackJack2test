

//enum för kortens värde
public enum CardValue {
	TVÅ(2),
	TRE(3),
	FYRA(4),
	FEM(5),
	SEX(6),
	SJU(7),
	ÅTTA(8),
	NIO(9),
	TIO(10),
	KNÄCKT(11),
	DAM(12),
	KUNG(13),
	ÄSS(14);

	//instansvariabel
	private int kortVärde;
	
	//konstruktor
	private CardValue(int kortVärde){
		this.kortVärde = kortVärde;
	}
	//getter
	private int getCardValue(){
		return kortVärde;
	}
	
}

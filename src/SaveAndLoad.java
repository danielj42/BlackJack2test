import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveAndLoad {
	File fil = new File("src/forTest/save.txt");
	private int money;
	
	
	public int getMoney() {
		return money;
	}


	public void setMoney(int money) {
		this.money = money;
	}


	public void Save() throws IOException{
		
			try {
			//ska bort, instansvari money används sedan
			String money2 = "pengar lolol2";
			
			File file = new File("src/forTest/save.txt");
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(money2);
			bw.close();

			
			System.out.println("Pengarna är sparade");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void Load() throws FileNotFoundException{
		Scanner sc = new Scanner(new File("src/forTest/save.txt"));
		String s = "";
		while(sc.hasNext()){
			s = sc.nextLine();
			System.out.println(s);
			}
			
		}
}

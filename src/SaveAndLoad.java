import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class SaveAndLoad {
	File file = new File("src/save.txt");
	private int money;

	// hämta pengara
	public int getMoney() {
		return money;
	}

	// sätta pengar
	public void setMoney(int money) {
		this.money = money;
	}

	

	public void Save(ArrayList<Player> al) throws IOException {

		File file = new File("save.txt");
		Iterator<Player> pIterator = al.iterator();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		// om filen inte finns, skapa den
		if (!file.exists())
			file.createNewFile();

		while (pIterator.hasNext()) {
			Player p = (Player) pIterator.next();
			bw.write(p.getName() + " " + Integer.toString(p.getCredit()));
			if (pIterator.hasNext())
				bw.write("\n");
		}

		bw.close();
	}

	public ArrayList<Player> LoadFromFile() throws FileNotFoundException {
		File file = new File("save.txt");
		ArrayList<Player> al = new ArrayList<Player>();
		try {
			Scanner sc = new Scanner(file);
			Player p = new Player();
			while(sc.hasNextLine()){
				String name = sc.next();
				String credit = sc.next();
				p.setName(name);
				p.setCredit(Integer.parseInt(credit));
				al.add(p);
					
			}
			
			sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
		
	}
}
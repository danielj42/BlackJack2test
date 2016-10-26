import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveAndLoad {
    File file = new File("src/save.txt");
    private int money;
    
    //hämta pengara
    public int getMoney() {
        return money;
    }

    //sätta pengar
    public void setMoney(int money) {
        this.money = money;
    }

    //sparar pengarna til en fil
    public void Save() throws IOException{
        
            try {
            // om filen inte finns, skapa den
            if (!file.exists()) {
                money = 1000;
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(money));
            bw.close();

            
            System.out.println("Pengarna är sparade");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //laddar in filen
    public void Load() throws FileNotFoundException{
        Scanner sc = new Scanner(file);
        String s = "";
        while(sc.hasNext()){
            s = sc.nextLine();
            System.out.println(s);
            }
        
        this.money = Integer.parseInt(s);
        }
}
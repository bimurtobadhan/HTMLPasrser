import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by bimurto on 25-Nov-15.
 */
public class Main {



    public static void main(String args[]){
        Scanner input = null;
        try {
            input = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Please create a file with the same name of input.txt and write company names one in a line");
        }
        while(input.hasNext()){
            String compnayName = input.next();
            new Parser(compnayName);
        }
        //new Parser("BATBC");
    }

}

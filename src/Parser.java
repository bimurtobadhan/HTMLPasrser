import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bimurto on 25-Nov-15.
 */
public class Parser {

    public static final int ALLTABLES = 339;
    public static final int TABLE1FULL = 342;
    public static final int COMPANYNAME = 340;
    public static final int TRADINGCODE = 341;
    public static final int TABLE1HALF1 = 343;
    public static final int TABLE1HALF2 = 345;
    public static final int BASICINFO = 346;
    public static final int AGM = 349;
    public static final int INTERIM = 352;
    public static final int TABLE9 = 342;
    public static final int TABLE10 = 342;


    private final String FILE_PATH = "STOCKMARKET.xlsx";
    private String url = "http://www.dsebd.org/displayCompany.php?name=";
    Workbook workbook = new XSSFWorkbook();

    Sheet studentsSheet;// = workbook.createSheet("BATBC");


    Document doc = null;
    int rowIndex = 0;
    int colIndex = 0;
    Row row = null;

    public Parser(String companyName){
        url = url + companyName;
        studentsSheet = workbook.createSheet("BATBC");
        try {
            doc = Jsoup.connect(url).get();
            func1();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("URL NOT FOUND");
        }
        //String title = doc.title();
        //printSource();

    }

    private void func1() {
        Elements alltables = doc.getElementsByTag("table");
        Element reqTable = alltables.get(334);
        printTable(reqTable);

        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

            System.out.println(FILE_PATH + " is successfully written");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printTable(Element table){
//        Elements ts = table.getElementsByTag("table");
//        if(!ts.isEmpty()){
//            for(int i =0;i<ts.size();i++){
//                printChild(ts.get(i));
//            }
//        }
        Elements children = table.children();
        //System.out.println(table.tagName().toString());
        if(table.tagName().toString().equals("td")) {
            System.out.print("td: ");
            //colIndex++;
        }
        else if(table.tagName().toString().equals("tr")) {
            System.out.print("\ntr:");
            row = studentsSheet.createRow(rowIndex++);
            colIndex = 0;
        }
        else if(table.tagName().toString().equals("table")) {
            System.out.print("table: \n");
        }
        if(children.size() == 0 ){
            System.out.print(table.text());
            row.createCell(colIndex++).setCellValue(table.text());
            return;
        }
        for(int i=0;i<children.size();i++){
            Element child = children.get(i);

            printTable(child);
        }
    }

    public void printSource(){
        System.out.println(doc.body().text());
    }
}

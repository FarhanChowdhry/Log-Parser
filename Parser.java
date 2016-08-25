import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Farhan on 04/08/2016.
 */

public class Parser{

    protected String type;
    protected String path = "log.txt";
    protected Scanner sc;
    protected String display = "";
    protected Boolean both = false;
    public Parser(String type){

        //assigning values to variables (housekeeping)
        this.type = type;
        if (type.contains("all")){
            this.type = "";
        }


        //if file is invalid it ends
        if(!loadFile())
            return;


        //deciding how to parse the file
        if (type.contains("both")){
            both = true;
            parse();
        }
        else
            parse();

    }

    private Boolean loadFile(){

        //loads file
        path = path.replace("\\", "\\\\");
        File raw = new File(path);

        //attempts to create Scanner of file
        try{
            sc = new Scanner(raw);
        }catch(FileNotFoundException e){
            e.printStackTrace();
            display = "File not found";
            return false;
        }

        Scanner check = sc;
        String p = check.next();
        if(!p.contains("INFO")| p.contains("WARN") | p.contains("ERROR") | p.contains("THYMELEAF")){
            display = "Invalid log File Has been selected";
            return false;
        }else
            return true;

    }

    private void parse(){
        while (sc.hasNext()){
            String line = sc.nextLine();
            if (both) add(line);
            else add(line);
        }
    }

    private void add(String l){
        String line = l;
        if ((both)? line.contains("ERROR") | line.contains("WARN") : line.contains(type)) {
            display += l + "\n";
                check();
        }
    }

    private void check(){
        if (sc.hasNext()){
            String next = sc.nextLine();
            if(!next.contains("|")){
                display += next + "\n";
                check();
            }else{
                add(next);
            }
        }
    }
}
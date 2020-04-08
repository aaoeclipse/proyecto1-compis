package lexicalanalyzer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Errors {
    public int count;
    public ArrayList<String> allerrors;

    public Errors(){
        count = 0;
        allerrors = new ArrayList<>();
    }

    @Override
    public String toString(){
        String toReturn = "";
        for (String s:
             allerrors) {
            toReturn += "" + s + "\n";
        }
        return toReturn;
    }
}

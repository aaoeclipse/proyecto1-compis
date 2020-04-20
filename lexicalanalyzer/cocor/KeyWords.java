package lexicalanalyzer.cocor;

import java.util.ArrayList;

public class KeyWords {
    private ArrayList<String> keywords;

    public KeyWords(){
        keywords = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "KeyWords{" +
                "keywords=" + keywords +
                '}';
    }
}

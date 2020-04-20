/**
 * Characters
 * This clase focuse con parsing the character secction of the cocor
 */
package lexicalanalyzer.cocor;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Characters {
    private ArrayList<Integer> availableChar;

    public Characters(){
        availableChar = new ArrayList<>();
    }

    public void addChars(String name, String charsToAdd){
        if (charsToAdd.contains("CHR")) {
            // get content between chr
            this.availableChar.add(Integer.parseInt(charsToAdd.split("[\\(\\)]")[1]));
            return;
        }
        for (char c: charsToAdd.toCharArray()) {
            availableChar.add((int) c);
        }
    }

    @Override
    public String toString(){
        String toReturn = "";

        for (int ch: availableChar) {
            toReturn += "" + (char) ch;
        }

        return toReturn;
    }


}

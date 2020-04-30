/**
 * Characters
 * This clase focuse con parsing the character secction of the cocor
 */
package lexicalanalyzer.cocor;


import lexicalanalyzer.tokens.Token;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Characters {
    private ArrayList<Token<Integer>> availableChar;

    public Characters(){
        availableChar = new ArrayList<>();
    }

    public void addChars(String name, String charsToAdd){
        charsToAdd = charsToAdd.replaceAll("\"|\"|\\.", "");
        ArrayList<Integer> currChars = new ArrayList<>();
        if (charsToAdd.contains("CHR")) {
            // get content between chr
            currChars.add(-(Integer.parseInt(charsToAdd.split("[\\(\\)]")[1])));
        }else {
            for (char c: charsToAdd.toCharArray()) {
                currChars.add((int) c);
            }
        }
        availableChar.add(new Token(name, currChars));
    }

    public void addNFA(){
        for (Token t:
             this.availableChar) {
            t.addNFA("character");
        }
    }

    public void printNFA(){
        for (Token t:
             this.availableChar) {
            System.out.println(t.printNFA());
        }
    }

    public void test(String[] s){
        System.out.println("Testing: ");
        for (String ss:
             s) {
            System.out.print(" * " + ss + " : ");
            for (Token t:
                 this.availableChar) {
                System.out.print(t.getName() + " = ");
                if (t.testNFA(ss)){
                    System.out.println("True!");
                } else {
                    System.out.println("False!");
                }
            }
        }


    }

    @Override
    public String toString(){
        String toReturn = "";
        if (availableChar.size() == 0){
            return "Not initialized with values";
        }
        for (Token ch: availableChar) {
            toReturn += "" + ch + "\n";
        }
        return toReturn;
    }


    public Token<Integer> getName(String s) {
        for (Token<Integer> token:
             this.availableChar) {
            if (token.getName().contains(s)){
                return token;
            }
        }
        return null;
    }
}

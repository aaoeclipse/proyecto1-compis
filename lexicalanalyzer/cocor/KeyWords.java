package lexicalanalyzer.cocor;

import lexicalanalyzer.tokens.Token;

import java.util.ArrayList;

public class KeyWords {
    private ArrayList<Token<ArrayList<Integer>>> keywords;

    public KeyWords(){
        keywords = new ArrayList<>();
    }

    public void addKeyWord(String name, String keywordToAdd){
        keywordToAdd = keywordToAdd.replaceAll("\"|\"|\\.", "");
        ArrayList<Integer> currChars = new ArrayList<>();

        for (char c: keywordToAdd.toCharArray()) {
            currChars.add((int) c);
        }

        keywords.add(new Token<ArrayList<Integer>>(name, currChars));
    }


    @Override
    public String toString(){
        String toReturn = "";

        if (keywords.size() == 0){
            return "Not initialized with values";
        }

        for (Token<ArrayList<Integer>> ch: keywords) {
            toReturn += "" + ch + "\n";
        }

        return toReturn;
    }

    public void addNFA() {
        for (Token<ArrayList<Integer>> t:
                this.keywords) {
            t.addNFA("keyword");
        }
    }

    public void printNFA(){
        for (Token<ArrayList<Integer>> t:
                this.keywords) {
            System.out.println(t.printNFA());
        }
    }

    public void test(String[] s) {
        System.out.println("Testing: ");
        for (String ss :
                s) {
            System.out.print(" * [" + ss + "] : ");
            for (Token<ArrayList<Integer>> t :
                    this.keywords) {
                System.out.print(t.getName() + " = ");
                if (t.testNFA(ss)) {
                    System.out.println("True!");
                } else {
                    System.out.println("False!");
                }
            }
        }
    }

    public Token<ArrayList<Integer>> getName(String s) {
        for (Token<ArrayList<Integer>> token:
                this.keywords) {
            if (token.getName().contains(s)){
                return token;
            }
        }
        return null;
    }
}

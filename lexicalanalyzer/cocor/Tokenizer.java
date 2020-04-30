/**
 * TOKENIZER
 * Aqui se van a hagar
 */

package lexicalanalyzer.cocor;

import lexicalanalyzer.DefaultValues;
import lexicalanalyzer.automatas.NFA;
import lexicalanalyzer.tokens.Token;

import java.util.ArrayList;
import java.util.Stack;

public class Tokenizer {
    ArrayList<Token<NFA>> tokens;
    Characters characters;
    KeyWords keywords;
    char[] SymbolsCocor;
    ArrayList<String> nameOfTokens;

    public Tokenizer(){
        tokens = new ArrayList<>();
        nameOfTokens = new ArrayList<>();
        SymbolsCocor = new char[] {
                '}',
                '{',
                '|',
                '&',
                '\"'
        };
    }

    /**
     * This transforms the str_before_adf to adf and stores them in the token
     * Also the str_before_adf contains logic like EXPECT Keywords that may change
     * example input letter{letter|digit} EXCEPT KEYWORDS
     * @param name
     * @param str_before_adf
     */
    public void addToken(String name, String str_before_adf){
        if (str_before_adf.charAt(0) == ' '){
            str_before_adf = str_before_adf.substring( 1, str_before_adf.length() );
        }
        int relativePos = 0;
        int position = 0;
        ArrayList<String> characters = new ArrayList<>();
        System.out.println("str_before_adf = " + str_before_adf);
        // split by space
        String[] str_splitted = str_before_adf.split(" ");

        // logic is going to be in [0]
        // iterate on each char, if it's a symbol then pick the entire word before the symbol
        // example: hello|test
        // In here, we are going to have hello and test as well as a '|' in the stack
        // hello is going to be searched for in the characters as well as test and after
        // is going to use the stack '|' to make the logic of the automata
        while ((position+relativePos) < str_splitted[0].length()){
//            System.out.println("size: " + str_splitted[0].length() + " relativepos: " + relativePos + " position: " + position);
            if (isSymbol(str_splitted[0].charAt(relativePos+position))){
                nameOfTokens.add( str_splitted[0].substring( position, ((position)+relativePos) ) );
                nameOfTokens.add( ""+str_splitted[0].charAt( relativePos+position) );

                position += relativePos+1;
                relativePos = 0;
            }
            relativePos++;
        }

        if (relativePos > 1){
            nameOfTokens.add(str_splitted[0].substring( position, ( position+relativePos) - 1 ) );
        }

        // TODO: check str_splitted has more than 1 word

        if (DefaultValues.DEBUG || true) {
            System.out.println("letters:");
            for (String s :
                    nameOfTokens) {
                System.out.println("s = " + s);
            }
        }

        createNFA(name);
    }

    private void createNFA(String name) {
        // TODO: Create Auotmatas
        Token first = searchToken(nameOfTokens.get(0));
        for (int i = 0; i < nameOfTokens.size(); i++) {

        }
        nameOfTokens = new ArrayList<>();
    }

    private Token searchToken(String s) {
        // check character
        Token t;
        t = characters.getName(s);
        if (t == null)
            t = keywords.getName(s);
        if (t == null)
            System.err.println("[-] Tokenizer: searchToken (no such token in characters or keyword)");
        return t;
    }

    private ArrayList<Token> postfix(String s){
        ArrayList<Token> toReturn = new ArrayList<>();
        char c = ' ';
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
//            if()
        }
        return toReturn;
    }


    private boolean isSymbol(char c){
        for (char symbol:
             SymbolsCocor) {
            if (c == symbol)
                return true;
        }
        return false;
    }



    public void setValues(Characters characters, KeyWords keywords) {
        this.characters = characters;
        this.keywords = keywords;
    }

    public void test(String[] s) {
        System.out.println("Testing: ");
        for (String ss :
                s) {
            System.out.print(" * [" + ss + "] : ");
            for (Token t :
                    this.tokens) {
                System.out.print(t.getName() + " = ");
                if (t.testNFA(ss)) {
                    System.out.println("True!");
                } else {
                    System.out.println("False!");
                }
            }
        }
    }

}

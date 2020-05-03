/**
 * TOKENIZER
 * Aqui se van a hagar
 */

package lexicalanalyzer.cocor;

import lexicalanalyzer.DefaultValues;
import lexicalanalyzer.automatas.NFA;
import lexicalanalyzer.automatas.NFA2;
import lexicalanalyzer.tokens.Token;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class Tokenizer {
    ArrayList<Token<NFA2>> tokens;
    Characters characters;
    KeyWords keywords;
    char[] SymbolsCocor;
    ArrayList<String> nameOfTokens;
    boolean expection;


    public Tokenizer(){
        expection = false;
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
        expection = false;
        if (str_before_adf.charAt(0) == ' '){
            str_before_adf = str_before_adf.substring( 1, str_before_adf.length() );
        }
        int relativePos = 0;
        int position = 0;

        ArrayList<String> characters = new ArrayList<>();

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
                nameOfTokens.add( str_splitted[0].substring( position, ( (position)+relativePos) ) );
                nameOfTokens.add( "" + str_splitted[0].charAt( relativePos+position) );

                position += relativePos+1;
                relativePos = 0;
            }
            relativePos++;
        }

        if (relativePos > 1){
            nameOfTokens.add(str_splitted[0].substring( position, ( position+relativePos) - 1 ) );
        }

        if (str_splitted.length > 1){
            if (str_splitted[1].contains("EXCEPT")){
                expection = true;
            }
        }

        if (DefaultValues.DEBUG) {
            System.out.println("str_before_adf = " + str_before_adf);

            System.out.println("letters:");
            for (String s : nameOfTokens) {
                System.out.println("s = " + s);
            }
        }

        createNFA(name);
    }

    private void createNFA(String name) {
        int counter = 0;
        // Find {
        ArrayList<String> betweenbrackets = new ArrayList<>();
        ArrayList<NFA2> nfa2s = new ArrayList<>();
        for (int i = 0; i < nameOfTokens.size(); i++) {
            if (nameOfTokens.get(i).contains("{")) {
                nameOfTokens.remove(i);
                while(!nameOfTokens.get(i).contains("}")){
                    betweenbrackets.add(nameOfTokens.get(i));
                    nameOfTokens.remove(i);
                }
                nameOfTokens.set(i,""+counter);
                counter++;
                nfa2s.add(addNFA2(betweenbrackets));
                betweenbrackets = new ArrayList<>();
            }}

        boolean first = true;
        NFA2 mainDFA = null;
        int numToken = 0;
        int option = 0;

        for (int i = 0; i < nameOfTokens.size(); i++) {
            if (i == 0){
                if (isNumeric(nameOfTokens.get(0))){
                    mainDFA = nfa2s.get(numToken);
                    numToken++;
                }else{
                    mainDFA = new NFA2(searchToken(nameOfTokens.get(0)), expection);
                }
            }else{
                if (isNumeric(nameOfTokens.get(i))){
                    mainDFA.Thompson(nfa2s.get(numToken));
                    numToken++;
                }else {
                    if(nameOfTokens.get(i).contains("|")){
                        option = 1;
                    }else{
                        mainDFA.Thompson(searchToken(nameOfTokens.get(0)), option);
                        option = 0;
                    }
                }
            }
        }

        System.out.println("mainDFA = " + mainDFA);
        tokens.add(new Token<>(name, mainDFA));
//        System.out.println("=== TEST ===");

        nameOfTokens = new ArrayList<>();
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    private NFA2 addNFA2(ArrayList<String> betweenbrackets) {
        if (betweenbrackets.size() == 0)
            return null;

        System.out.println("betweenbrackets.get(0)) = " + betweenbrackets);
        NFA2 toReturn = new NFA2(searchToken(betweenbrackets.get(0)), expection);
        if (betweenbrackets.size() == 1) {
            toReturn.Thompson(searchToken(betweenbrackets.get(0)), 2);
            return toReturn;
        }

        int option = 0;
        Token curr = null;

        for (int i = 1; i < betweenbrackets.size(); i++) {
            // get symbol then get operand
            if ("|".equals(betweenbrackets.get(i))) {
                option = 1;
            } else {
                curr = searchToken(betweenbrackets.get(i));
                toReturn.Thompson(curr, option);
                option = 0;
            }
        }

        toReturn.Thompson(curr, 2);
        return toReturn;
    }

    public void test(String[] tetingcases) {
        // true, true, false, false, true
        for (String s : tetingcases) {
            System.out.println("TESTING STRING ==> " + s);
            for (Token<NFA2> t: tokens) {
                System.out.print(" * Token Test: " + t.getName() + " => ");
                if(t.getValue().Simulate(s)){
                    System.out.println("Success!");
                }else
                    System.out.println("Fail!");
            }
        }
        System.out.println();
    }

    public boolean Simulate(String s){
        for (Token<NFA2> t: this.tokens) {
            if (t.getValue().Simulate(s)) {
                if (t.getValue().keyword) {
                    for (Token keyword : this.keywords.keywords) {
                        if (keyword.testNFA(s)) {
                            System.out.println("[Error] Token recognized " + t.getName() + "but is KEYWORD");
                            return true;
                        }
                    }
                }
                System.out.println(t.getName());
                return true;
            }
        }
        return false;
    }

    public ArrayList<Token> extraSimulate(String s){
        ArrayList<Token> toReturn = new ArrayList<>();
        String[] ss = s.split(" ");
        int max_length = tokens.size();
        if (keywords.keywords.size() > tokens.size())
            max_length = keywords.keywords.size();
        boolean keywordFlag = false;
        for (String se:ss) {
            keywordFlag = false;

            for (int i = 0; i < keywords.keywords.size(); i++) {
                // check if keyword
                    if (keywords.keywords.get(i).testNFA(se)) {
                        keywordFlag = true;
                        toReturn.add(keywords.keywords.get(i));
                    }
            }
            if (!keywordFlag) {
                for (Token<NFA2> t : tokens) {
                    // check if token
                    if (t.getValue().Simulate(se)) {
                        toReturn.add(t);
                    }
                }
            }

        }
        return toReturn;

    }

    private ArrayList<String> postfix(ArrayList<String> test){
        boolean flag = false;

        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> operand = characters.getAllNames();

        Stack<String> stack = new Stack<>();

        for (String s: test) {
            if (operand.contains(s)) {
                result.add(s);
            }
            else if (s.contains("{")){
                stack.push(s);

            }else if (s.contains("}")){

                while( ! stack.isEmpty() && !stack.peek().contains("{")) {
                    System.out.println("} stack.peek() = " + stack.peek());
                    result.add(stack.pop());
                }
                
                stack.pop();
            }else{
                while( ! stack.isEmpty() && !stack.peek().contains("{")) {
                    System.out.println("operation stack.peek() = " + stack.peek());
                    result.add(stack.pop());
                }
                stack.push(s);
            }
        }
        while( !stack.isEmpty() ){
            result.add(stack.pop());
        }
        return result;
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

    @Override
    public String toString() {
        String toReturn = "";
        for (Token t:
             tokens) {
            toReturn += "[" + t.getName() + ", " + t.getValue();
        }
        return toReturn;
    }
}

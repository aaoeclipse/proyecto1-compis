/**
 * Class: Token.java
 * Generic version of the Token class.
 * @param <T> the type of the value
 * | char | int value |
 *    /        47
 *    *        42
 *    +        43
 *    |        124
 *    (        40
 *    )        41
 *    ?        
 */
package lexicalanalyzer.tokens;

import lexicalanalyzer.automatas.DFA;
import lexicalanalyzer.automatas.NFA;

import java.util.ArrayList;

public class Token <T>{
    boolean hasValue = false;
    String name = "";
    private T value;
    public int kind;
    private NFA nfaToken;

    public DFA DFA;

    @SuppressWarnings("rawtypes")
    public Token(String name, T value){
        this.name = name;
        this.value = value;
        this.hasValue = true;
    }

    public Token(String name){
        this.name = name;
        this.hasValue = false;
    }

    // GETTERS
    public String getName(){
        return this.name;
    }

    public T getValue(){
        return this.value;
    }
    
    // SETTERS
    public void setName(String name){
        this.name = name;
    }
    
    public void setValue(T value){
        this.value = value;
        this.hasValue = true;
    }

    public void addNFA(String option){
        if (option.equalsIgnoreCase("character")){
            nfaToken = new NFA((ArrayList<Integer>) value, 0);
        } else if (option.equalsIgnoreCase("keyword")){
            nfaToken = new NFA((ArrayList<Integer>) value, 1);
        }
    }

    public NFA getNFA(){
        return this.nfaToken;
    }

    public String printNFA(){
        return nfaToken.toString();
    }

    public boolean testNFA(String s){
        return nfaToken.SimulateString(s);
    }

    @Override
    public String toString(){
        String toReturn = "";
        toReturn += "<" + this.name ;

//        if (characters != null){
//            toReturn += "" + this.characters;
//        }

        if (this.hasValue){
            toReturn += ", " + this.value;
        }

        toReturn += ">";
        return toReturn;
    }
}
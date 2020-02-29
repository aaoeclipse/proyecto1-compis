package lexicalanalyzer.tokens;

import java.util.ArrayList;

public class SymbolTable<T>{
    ArrayList<Token> sytable = new ArrayList<>();

    public SymbolTable(){
    }

    // add to table the symbol
    @SuppressWarnings("rawtypes")
    public void addToTable(String name, T value){
        Token newToken = new Token(name, value);
        sytable.add(newToken);
    }

    // add to table the symbol
    @SuppressWarnings("rawtypes")
    public void addToTable(String name){
        Token newToken = new Token(name);
        sytable.add(newToken);
    }

    @SuppressWarnings("unchecked")
    public void alterValue(int id, T newValue){
        sytable.get(id).setValue(newValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String toString(){
        String toReturn = "** Symbol Table **\n";
        int counter = 1;
        for (Token<T> t: sytable){
            toReturn += counter + ": ";
            toReturn += t.toString();
            toReturn += "\n";
            counter++;
        }
        return toReturn;
    }


}
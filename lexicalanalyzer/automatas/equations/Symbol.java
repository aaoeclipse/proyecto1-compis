package lexicalanalyzer.automatas.equations;

import java.util.HashSet;
import java.util.Set;

public class Symbol {
    private Set<Integer> symbols;
    
    public Symbol(){
        symbols = new HashSet<Integer>();
    }

    public void symbolAdd(int symbol){
        this.symbols.add(symbol);
    }

    public Set<Integer> getSymbols(){
        return this.symbols;
    }
}
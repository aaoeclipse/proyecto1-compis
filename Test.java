import java.util.HashSet;
import java.util.Set;

import lexicalanalyzer.automatas.equations.*;

public class Test{
    public static void main(String... args){
        Transition transitionTest;
        Symbol symbols = new Symbol();
        Set<State> states = new HashSet<>();

        for (int i=0; i < 10; i++){
            symbols.symbolAdd((char) (97+i));
        }

        for (int j=0; j < 5; j++){
            states.add(new State(j));
        }

        transitionTest = new Transition(symbols, states);
    }
}
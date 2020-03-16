package lexicalanalyzer.automatas.equations;

import java.util.ArrayList;

public class State{

    private int id;
    private int symbol;

    private boolean initialState;
    private boolean finalState;


    public State(int id, boolean initialState, boolean finalState){
        this.id = id;
        this.initialState = initialState;
        this.finalState = finalState;
        this.symbol = 0;
    }

    public State(int id){
        this.id = id;
        this.initialState = false;
        this.finalState = false;
        this.symbol = 0;
    }

    public int getId(){
        return this.id;
    }

    public void setInitialState(boolean initialState){
        this.initialState = initialState;
    }

    public void setFinalState(boolean finalState){
        this.finalState = finalState;
    }

    public boolean getFinalState(){
        return finalState;
    }
    public boolean getInitialState(){
        return initialState;
    }

    public int getSymbol(){
        return this.symbol;
    }

    public void setSymbol(int symbol){
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        String toReturn = "State{" +
                "id=" + id;
        if (initialState){
            toReturn += ", I";
        }if (finalState){
            toReturn += ", F";
        }
        toReturn += '}';
        return toReturn;
    }
}
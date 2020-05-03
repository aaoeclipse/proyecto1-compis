package lexicalanalyzer.automatas.equations;

import lexicalanalyzer.DefaultValues;

import java.util.Objects;

public class Trans {
    private int character;
    private State state;
    private State nextState;


    public State getState(){
        return this.state;
    }

    public int getCharacter() { return character; }

    public State getNextState(){
        return nextState;
    }

    public int getStateId(){
        return this.state.getId();
    }


    public Trans(State s, int sym, State sprime){
        if (sprime == null || s == null){
            System.err.println("Cannnot have a null s: " + s + " sym: " + sym+ " sprime: " + sprime);
        }
        this.state = s;
        this.character = sym;
        this.nextState = sprime;
    }

    @Override
    public String toString() {
        String toReturn = "";
        if (DefaultValues.letter.contains(this.character)){
            toReturn += "[" + state.getId() + ", " + (char) this.character + ", " + this.nextState.getId() + "]";

        }else{
            toReturn += "[" + state.getId() + ", " + (Integer) this.character + ", " + this.nextState.getId() + "]";

        }
        if(state.getInitialState())
            toReturn += "(I)";
        if(nextState.getFinalState())
            toReturn += "(F)";
        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trans trans = (Trans) o;
        return character == trans.character &&
                state.equals(trans.state) &&
                Objects.equals(nextState, trans.nextState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, state, nextState);
    }
}

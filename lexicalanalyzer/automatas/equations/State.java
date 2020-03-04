package lexicalanalyzer.automatas.equations;

public class State{
    private int id;
    private boolean initialState;
    private boolean finalState;

    public State(int id, boolean initialState, boolean finalState){
        this.id = id;
        this.initialState = initialState;
        this.finalState = finalState;
    }

    public State(int id){
        this.id = id;
    }

    public void setInitialState(boolean initialState){
        this.initialState = initialState;
    }

    public void setFinalState(boolean finalState){
        this.finalState = finalState;
    }



}
package lexicalanalyzer.automatas.equations;

public class TransitionV2 {
    private int state;
    private int symbol;
    private int nextState;

    public TransitionV2(int state, int symbol, int nextState) {
        this.state = state;
        this.symbol = symbol;
        this.nextState = nextState;
    }
}

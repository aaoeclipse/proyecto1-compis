package lexicalanalyzer.cocor.Reader;

import lexicalanalyzer.Errors;
import lexicalanalyzer.cocor.ScannerCocor;
import lexicalanalyzer.tokens.Token;

import java.util.Scanner;

public class Parser {
    private ScannerCocor scanner;
    public Errors errors;

    // Look ahead token
    Token la;

    public Parser(ScannerCocor scanner){
        this.scanner = scanner;
        this.errors = new Errors();
    }

    void Sample(){
        if (la.kind == 1){
            Get();
            Expect(2);
        } else if (la.kind == 3){
            Get();
        } else
            SynErr(5);
    }

    private void SynErr(int i) {
        errors.count++;
        errors.allerrors.add(""+i);
    }

    public void Get(){
        la = scanner.Scan();
    }

    public void Expect(int n){
        if (la.kind == n)
            Get();
        else
            SynErr(n);
    }

    public void Parse(){
        Get();
        Sample();
    }


}

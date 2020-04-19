package lexicalanalyzer.cocor;

import lexicalanalyzer.tokens.Token;

import java.util.Scanner;

public class ScannerCocor {
    Scanner scanner;

    public ScannerCocor(String filepath){
        this.scanner = new Scanner(filepath);
    }

    // public ScannerCocor(Stream s){}


    /**
     * Reads token by token of the codes
     * @return
     */
    public Token<Integer> Scan(){
        return null;
    }

    public Token<Integer> Peek(){
        return null;
    }

    /**
     * resets peeking to the current scanner position
     */
    public void ResetPeek(){

    }
}

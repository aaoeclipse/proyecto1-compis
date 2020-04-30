package lexicalanalyzer.cocor;

import lexicalanalyzer.tokens.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ScannerCocor {
    public List<String> listBlocks;
    String currBlock;

    public String[] blocksStructure = new String []{
        "COMPILER",
        "CHARACTERS",
        "KEYWORDS",
        "TOKENS",
        "PRODUCTIONS"
    };

    Scanner scanner;
    public boolean finishblock;


    public ScannerCocor(String filepath){
        this.listBlocks = Arrays.asList(blocksStructure);
        this.finishblock = false;
        System.out.println("filepath = " + filepath);

        try {
            this.scanner = new Scanner(new File(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.currBlock = "COMPILER";
    }


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

    public String readTitle() {
        String s = scanner.nextLine();

        while (scanner.hasNext()) {

            if (s.contains("COMPILER")) {
                return s.split("COMPILER ")[1];
            }
        }
        return "";
    }

    public void gotTo(String blockName) {

        if (!blockName.equalsIgnoreCase( this.currBlock )){
            String nextLine = scanner.nextLine();
            nextLine = nextLine.replaceAll(" ", "");


            while (!blockName.equalsIgnoreCase(nextLine)){
                nextLine = scanner.nextLine();
                nextLine = nextLine.replaceAll(" ", "");

            }
            this.currBlock = nextLine;
            this.finishblock = false;
        }else {
            this.finishblock = false;
        }

    }

    private boolean contains(String test) {
        for (String s: this.blocksStructure) {
            if (test.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public boolean finishBlock() {
        return false;
    }

    public String nextEq() {

        String nextLine = scanner.nextLine();
        nextLine = nextLine.replaceAll(" ", "");

        if (checkIfBlockChange(nextLine))
            return "EBO";


        // check if next line is good
        while (nextLine.length() < 2 || ! nextLine.contains("=")){
            nextLine = scanner.nextLine();
            if (checkIfBlockChange(nextLine))
                return "EBO";
        }
        return nextLine;
    }

    private boolean checkIfBlockChange(String nextLine) {
        // if is nxt block, then change block
        if (this.listBlocks.contains(nextLine)){
            this.currBlock = nextLine;
            this.finishblock = true;
            return true;
        }
        return false;
    }

    public boolean getFinishblock() {
        return this.finishblock;
    }
}

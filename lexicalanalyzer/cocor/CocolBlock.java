package lexicalanalyzer.cocor;

import lexicalanalyzer.DefaultValues;

public class CocolBlock {

    public String titleOfCompiler;

    Characters characters;
    KeyWords keywords;
    Tokenizer tokenizer;
    Production production;
    ScannerCocor scanner;

    public CocolBlock(ScannerCocor scanner) {
        this.characters = new Characters();
        this.keywords = new KeyWords();
        this.tokenizer = new Tokenizer();
        this.production = new Production();
        this.scanner = scanner;
    }

    /**
     * reads all the blocks in the cocor file, and parse them into characters,
     *  keywords, tokens and production
     */
    public void readBlocks() {
        char c = ' ';
        String[] nextLine;

        // Read Title
        titleOfCompiler = scanner.readTitle();

        System.out.println("titleOfCompiler = " + titleOfCompiler);

        // Read CHARACTERS
        scanner.gotTo("CHARACTERS");

        while (! scanner.getFinishblock() )
        {
            // scanner nextEq gets the equation usualy in this format:
            // something = everything else
            // so we use split and clean to separate it into
            // [0] something [1] something else
            nextLine = splitAndClean(scanner.nextEq());
            // if it isn't size 2 ([0],[1]) then it means it wasn't an equation
            if (nextLine.length == 2){
                this.characters.addChars(nextLine[0], nextLine[1]);
            }
        }
        this.characters.addNFA();
        System.out.println("CocolBlock.readBlocks: characters: " + this.characters);

        if (DefaultValues.DEBUG) {
            this.characters.printNFA();
            String[] toTest = new String[] {
              "hello", "a", "4", "Z"
            };
            // EXPECTED RESULT: False, True, False, True
            this.characters.test(toTest);
        }

        // Read keywords
        scanner.gotTo("KEYWORDS");

        while (! scanner.finishblock )
        {
            nextLine = splitAndClean(scanner.nextEq());
            if (nextLine.length == 2){
                this.keywords.addKeyWord(nextLine[0], nextLine[1]);
            }

        }
        this.keywords.addNFA();
        System.out.println("CocolBlock.readBlocks: keywords: " + this.keywords);

        if (DefaultValues.DEBUG) {
            this.keywords.printNFA();

            String[] toTest = new String[] {
              "if", "switch", "erg", "test"
            };
            // EXPECTED RESULT: True, True, False, False
            this.keywords.test(toTest);
        }

        // Read Tokens
        scanner.gotTo("TOKENS");

        this.tokenizer.setValues(characters, keywords);

        while (! scanner.finishblock )
        {
            nextLine = splitAndClean(scanner.nextEq());
            // Creates token <Name, adf>
            if (nextLine.length == 2){
                this.tokenizer.addToken(nextLine[0], nextLine[1]);
            }

        }
        String[] testCases = new String[] {
          "aa", "a", "a2", "2a", "2", "222", "a2a", " "
        };

        this.tokenizer.test(testCases);

        // Read line by line and check if it's token


    }

    /**
     * Separates a string by the equal sign, leaving only the left and right side of
     * without the equals
     * @param strChange
     * @return
     */
    public String[] splitAndClean(String strChange){
//        System.out.println("strChange = " + strChange);
        String[] splited = strChange.split("=");
        return splited;
    }


}

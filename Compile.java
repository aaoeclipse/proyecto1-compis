import lexicalanalyzer.DefaultValues;
import lexicalanalyzer.cocor.Characters;
import lexicalanalyzer.cocor.CocolBlock;
import lexicalanalyzer.cocor.ScannerCocor;
import lexicalanalyzer.tokens.Token;


public class Compile {
    public static void main(String... args){
        CocolBlock cocolblock = new CocolBlock(new ScannerCocor(args[0]));

        // blocks: (Characters, Keywords, Tokens, Production)
        cocolblock.readBlocks();

        cocolblock.readFile("P2/test.txt");
    }

}

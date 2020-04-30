import lexicalanalyzer.cocor.Characters;
import lexicalanalyzer.cocor.CocolBlock;
import lexicalanalyzer.cocor.ScannerCocor;
import lexicalanalyzer.tokens.Token;


public class Compile {
    public static void main(String... args){

        CocolBlock cocolblock = new CocolBlock(new ScannerCocor(args[0]));

        // blocks: (Characters, Keywords, Tokens, Production)
        cocolblock.readBlocks();


//        Characters AvlChar = new Characters();
//        AvlChar.addChars("letters","ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
//        AvlChar.addChars("indent","CHR(9)");


//        System.out.println(AvlChar);


        // Parse Output file
//        ScannerCocor scanner = new ScannerCocor(args[0]);
//        Parser parser = new Parser(scanner);
//        parser.Parse();
//        System.out.println(parser.errors.count + " errors detected");

    }

}

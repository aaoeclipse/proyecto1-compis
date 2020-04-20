import lexicalanalyzer.Parser;
import lexicalanalyzer.cocor.Characters;
import lexicalanalyzer.cocor.ScannerCocor;


public class Compile {
    public static void main(String... args){
        Characters AvlChar = new Characters();
        AvlChar.addChars("nombre","ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

//        ScannerCocor scanner = new ScannerCocor(args[0]);
//        Parser parser = new Parser(scanner);
//        parser.Parse();
//        System.out.println(parser.errors.count + " errors detected");
    }

}

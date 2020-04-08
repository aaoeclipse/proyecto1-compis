import lexicalanalyzer.Parser;

import java.util.Scanner;

public class Compile {
    public static void main(String... args){
        Scanner scanner = new Scanner(args[0]);
        Parser parser = new Parser(scanner);
        parser.Parse();
        System.out.println(parser.errors.count + " errors detected");
    }

}

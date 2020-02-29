/** ReadSourceCode
 *  Created by: Santiago Paiz
 *  
 */
package lexicalanalyzer.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadSourceCode {
    private String filePath="";
    private BufferedReader reader;
    // Esta posicion dice en la posicion de la linea del source code
    private int positionInLine;
    // currentLine tiene la linea en la cual se esta trabajando, esto 
    // solo sirve para poder despues mostrarle al usuario si hay algun problema
    private int currentLinePos;
    // leemos todo el archivo y lo separamos en un array de string
    private ArrayList<String> currentLineString;

    public ReadSourceCode(){
        this.positionInLine = 0;
        this.currentLinePos = 0;
    }

    public boolean runFile(String[] args){
        // Check if they called the source file while executing the java program
        if(args.length != 1){
            System.err.println("[-] Incorrect Format");
            printUse();
            return false;
        }
        try{
            currentLineString = new ArrayList<>();
            // Open file
            reader = new BufferedReader(new FileReader(args[0]));
            System.out.println("[+] File found!");


            // Read fisrt line
            currentLineString.add(reader.readLine());

            String temp;
            
            while (true) {
                temp = reader.readLine();
                if (temp == null)
                    break;
                else {
                    currentLineString.add(temp);
                    currentLinePos++;
                }
            }
            // for (String s: currentLineString){
            //     System.out.println(s);
            // }
            currentLinePos = 0;
            return true;

        }catch(Exception e){
            System.err.println("[-] File not found, please insert an existing file");
            e.printStackTrace();
        }
        return false;
    }

    public int readNextChar(){
        // if end of line change the line 
        if ( positionInLine >= currentLineString.get(currentLinePos).length() ){
            // next line
            positionInLine = 0;

            // add new line
            currentLinePos++;

            // there is no more on the file EOF
            if (currentLinePos >= currentLineString.size()){
                return -2; // EOF
            }
        }
        // position changes +1
        positionInLine++;
        
        return (int) currentLineString.get(currentLinePos).charAt(positionInLine-1);
    }

    private void printUse(){
        System.out.println("[*] Please use $java main [name of file]");
    }

}
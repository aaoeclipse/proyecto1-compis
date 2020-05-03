/** ReadSourceCode
 *  Created by: Santiago Paiz
 *  Objectives: 
 *      1. Finds file and gets content
 *      2. parese content into postfix
 *      3. can be accessed to get char by char of postfix
 */
package lexicalanalyzer.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import lexicalanalyzer.DefaultValues;

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
    // =========== POSTFIX ====================
    private Queue<Integer> postfix;
    private Stack<Integer> stack;
    private boolean isConcat = false;

    static int Importance(int ch1) 
    { 
        char ch = (char) ch1;
        switch (ch) 
        { 
        case '+': 
        case '|': 
        case '?':
        case '*':
        case '&':
            return 1; 
       
        case '-': 
        case '/': 
            return 2; 
       
        case '^': 
            return 3; 
        } 
        return -1; 
    }
    

    public ReadSourceCode(){
        this.positionInLine = 0;
        this.currentLinePos = 0;
    }

    /** reads entire file into a matrix for string 
    * @return true if the file was found */
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
            currentLinePos = 0;
            return true;

        }catch(Exception e){
            System.err.println("[-] File not found, please insert an existing file");
            e.printStackTrace();
        }
        return false;
    }

    /** change the string to postfix
     * @return creates the this.postfix
      */
    public void changeToPostfix(){
        this.postfix = new LinkedList<>();
        this.stack = new Stack<>();
        this.isConcat = false;

        int curr = readNextCharInfile();
        while (curr >= 0){
            if ((DefaultValues.letter.contains(curr) || curr == (int) '(') && isConcat){
                isConcat = false;
                parsePostfix((int) '&');
            } else {
                parsePostfix(curr);
                curr = readNextCharInfile();
            }

        }

        // pop all the operators from the stack 
        while (!stack.isEmpty()){ 
            if(stack.peek() == '(') 
            System.out.println("[-] ERROR: ReadSourceCode: changeToPostfix: invalid expression (isempty)"); 
            this.postfix.add(stack.pop()); 
         } 

    }

    private void parsePostfix(int curr){
                    // if curr is a letter or a number, then add it to the queue
                    if (DefaultValues.letter.contains(curr) || curr==(int)'*'|| curr==(int)'+'|| curr==(int)'?'){
                        this.postfix.add(curr);

                        // if (DefaultValues.letter.contains(curr) )
                        isConcat = true;

                    }
                    // if curr is opern parentesis then we will have to push it on to stack
                    else if ((char) curr == '('){
                        stack.push(curr);
                    }
                    // if it's a close bracket then it has to pop until it finds the last open parentesis
                    else if ((char) curr == ')'){
                        while (!stack.isEmpty() && stack.peek() != '(')
                            this.postfix.add(stack.pop());
                        
                        if (!stack.isEmpty() && stack.peek() != '(') 
                            System.out.println("[-] ERROR: ReadSourceCode: changeToPostfix: invalid expression (in parenteces)"); // invalid expression                 
                        else
                            stack.pop(); 
                    } 
                    // else, it means there is an operator
                    else if (DefaultValues.operators.contains(curr)){

                        while (!stack.isEmpty() && Importance((char) curr) <= Importance(stack.peek())){ 
                            if(stack.peek() == '(') 
                                System.out.println("[-] ERROR: ReadSourceCode: changeToPostfix: invalid expression (else)"); 
                            this.postfix.add(stack.pop());
                        }
                        isConcat = false;
                        stack.push(curr);

                    } else {
                        System.err.println("[-] ERROR: (ReadSourceCode:changeToPostfix) - cannot interpret sign: " + (char) curr);
                    }
    }

    /**
     * reads next char in postfix and returns its content
     * @return int of char, -2 if it's end of file (EOF)
     */
    public int readNextChar(){
        if (postfix.size() == 0){
            return DefaultValues.EOF; 
        }

        return this.postfix.remove();
    }

    /**
     * reads character by character of the file
     * @return int number of char
     */
    public int readNextCharInfile(){
        // if end of line change the line 
        if ( positionInLine >= currentLineString.get(currentLinePos).length() ){
            // next line
            positionInLine = 0;

            // add new line
            currentLinePos++;

            // there is no more on the file EOF
            if (currentLinePos >= currentLineString.size()){
                return DefaultValues.EOF; // EOF
            }
        }
        // position changes +1
        positionInLine++;
        
        return (int) currentLineString.get(currentLinePos).charAt(positionInLine-1);
    }

    private void printUse(){
        System.out.println("[*] Please use $java main [OPTION] [automata] [name of file]");

        System.out.println("-- AUTOMATA --");
        System.out.println("-nfa");
        System.out.println("-dfa");
        System.out.println("-dfa2");

        System.out.println("-- OPTIONS --");
        System.out.println("-b\tbuild the automata to run");
        System.out.println("-s\tsimulate");
    }


	public void printPostfix() {
        System.out.println("POSTFIX");
        System.out.println(postfix);
        for (int i : this.postfix) {
            System.out.print((char) i);
        }
        System.out.println();
	}

}
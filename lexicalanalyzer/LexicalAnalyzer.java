package lexicalanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/*
Class Lexical Analyzer
Esta clase se enfoca en escanear caracter por caracter para despues separarlos por tokens.
Esto se hace de esta forma
1. Remover los comentarios y espacios en blanco
2. Cada palabra que estaba separada por el espacio en blanco lo vuelve en un token
3. Manda el token a el SyntaxAnalyzer:
    3.1 Si el token es invalido retornar error
*/

public class LexicalAnalyzer{

    public LexicalAnalyzer(File sourceCode){
        // TODO: Read character by char

        // Transform to <id>
    }


}
package lexicalanalyzer;

import java.util.ArrayList;

public class DefaultValues {
    public static final boolean DEBUG = false;
    public static ArrayList<Integer> operators = new ArrayList<Integer>(){
        {
            add((int) '|');
            add((int) '*');
            add((int) '+');
            add((int) '?');
            add((int) '/');
            add((int) '&');
        }
    };
    public static ArrayList<Integer> digits = new ArrayList<Integer>(){
        {
            add((int) '0');
            add((int) '1');
            add((int) '2');
            add((int) '3');
            add((int) '4');
            add((int) '5');
            add((int) '6');
            add((int) '7');
            add((int) '8');
            add((int) '9');
        }
    };
    public static ArrayList<Integer> letter = new ArrayList<Integer>(){
        {
            for (int i=0;i<26;i++){
                // UPPER CASE
                add(65+i);
                // LOWER CASE
                add(97+i);
            }
            for (int i=0; i<10;i++){
                add(48+i);
            }
            add(EPSILON);
            add((int) '"');
            add((int) '=');
            add((int) '.');
            add((int) ' ');
        }
    };
    public static int EOF = -2;
    public static int EPSILON = 0;
}
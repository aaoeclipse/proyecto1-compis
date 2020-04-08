/**
 * Class: Token.java
 * Generic version of the Token class.
 * @param <T> the type of the value
 * | char | int value |
 *    /        47
 *    *        42
 *    +        43
 *    |        124
 *    (        40
 *    )        41
 *    ?        
 */
package lexicalanalyzer.tokens;

public class Token <T>{
    boolean hasValue = false;
    String name = "";
    private T value;
    public int kind;

    @SuppressWarnings("rawtypes")
    public Token(String name, T value){
        this.name = name;
        this.value = value;
        this.hasValue = true;
    }

    public Token(String name){
        this.name = name;
        this.hasValue = false;
    }

    // GETTERS
    public String getName(){
        return this.name;
    }

    public T getValue(){
        return this.value;
    }
    
    // SETTERS
    public void setName(String name){
        this.name = name;
    }
    
    public void setValue(T value){
        this.value = value;
        this.hasValue = true;
    }

    @Override
    public String toString(){
        String toReturn = "";
        toReturn += "<" + this.name;
        if (this.hasValue){
            toReturn += ", " + this.value;
        }
        toReturn += ">";
        return toReturn;
    }
}
package lexicalanalyzer.automatas;

import java.util.ArrayList;

public class Estado{
    private Estado output;
    private int previousValueChange = -1;
    private int id;

    private boolean inicial = false;
    private boolean final2 = false;

    public Estado(){
        
    }

    // Value that changed to this state
    public int getPreviousValueChange(){
        return this.previousValueChange;
    }
    public void setPreviousValueChange(int previousValueChange){
        this.previousValueChange = previousValueChange;
    }


    public int getid(){
        return this.id;
    }

    public void addEstadoOutput(char value){
        // Componer, debe de tener el id pero tambien el rango que se necesito para poderse cambiar
        this.output = new Estado();
        this.output.setPreviousValueChange(value);
    }

    @Override
    public String toString(){
        String retuningString = " ";
        if (output == null){
            return ""+this.id;
        }
        if (previousValueChange == -1){
            retuningString = "-> ("+this + this.id + "\n"+this.id+")";
            return "";
        }
        retuningString += "{"+ this.previousValueChange +"} -> ("+this.id+")";
        
        return retuningString;
    }

}
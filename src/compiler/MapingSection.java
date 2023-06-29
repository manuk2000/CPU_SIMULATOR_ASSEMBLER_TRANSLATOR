package compiler;


import java.util.HashMap;
import java.util.Map;

public class MapingSection  {
    Map<String,Integer> registres = new HashMap<>();
    Map<String,Integer> instractions = new HashMap<>();

    //reg     -> 0
    //lable   -> 1
    //[]      -> 2
    //literal -> 3


    //set before registres
    {
        registres.put("AYB" , 0 );
        registres.put("BEN" , 1 );
        registres.put("GIM" , 2);
        registres.put("DA" , 3 );
        registres.put("ECH" , 4);
        registres.put("ZA" , 5 );
    }

    //set before instraction
    {
        instractions.put("MOV" , 0);
        instractions.put("ADD" , 1);
        instractions.put("SUB" , 2);
        instractions.put("MUL" , 3);
        instractions.put("DIV" , 4);
        instractions.put("OR" , 5);
        instractions.put("NOT" , 6);
        instractions.put("CMP" ,7);
        instractions.put("JMP" , 8);
        instractions.put("JG" , 9);
        instractions.put("JL" , 10);
        instractions.put("JE" , 11);
    }

    public Integer getRegistrIndex(String regName) {
        return registres.get(regName);
    }

    public Integer getInstractionIndex(String instName) {
        return instractions.get(instName);
    }

}

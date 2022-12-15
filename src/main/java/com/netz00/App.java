package com.netz00;

import com.netz00.structure.CFGrammar;
import com.netz00.structure.Terminal;
import com.netz00.structure.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("Context-free grammar");


        List<Variable> V = new ArrayList<>(
                Arrays.asList(
                        new Variable('S'),
                        new Variable('A'),
                        new Variable('B'),
                        new Variable('C')
                )
        );

        List<Terminal> T = new ArrayList<>(
                Arrays.asList(
                        new Terminal('a'),
                        new Terminal('b'),
                        new Terminal('c'),
                        new Terminal('d')
                )
        );


        Variable S = new Variable('S');

        CFGrammar CFGrammar = new CFGrammar(V, T, S);
        CFGrammar.addProduction("1 S->aABS")
                .addProduction("2 S->bCACd")
                .addProduction("3 B->bAB")
                .addProduction("4 A->cSA")
                .addProduction("5 A->cCC")
                .addProduction("6 A->bAB")
                .addProduction("7 B->cSB")
                .addProduction("8 C->cS")
                .addProduction("9 C->c");


        System.out.println("Grammar");
        System.out.println(CFGrammar.toString());

        System.out.println("Dead variables");
        System.out.println(CFGrammar.removeDeadVariables().toString());

        System.out.println("Grammar");
        System.out.println(CFGrammar.toString());


        System.out.println("Unreachable characters");
        System.out.println(CFGrammar.removeUnreachableCharacters().toString());

        System.out.println("Grammar");
        System.out.println(CFGrammar.toString());




    }

}

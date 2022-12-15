package com.netz00.frontend;

import com.netz00.structure.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrammarParser {

    private CFGrammar cfGrammar;

    public GrammarParser() {
        cfGrammar = new CFGrammar();
    }

    public GrammarParser addVariables(String variables) {

        if (variables.contains(", ") || variables.length() == 1) {
            String[] parts = variables.split(", ");

            LinkedHashSet<Variable> V = new LinkedHashSet<>();

            for (String ch : parts)
                V.add(new Variable(ch.charAt(0)));

            cfGrammar.setV(V);

        } else
            throw new IllegalArgumentException("Invalid variables: " + variables);

        return this;
    }

    public GrammarParser addTerminals(String terminals) {

        if (terminals.contains(", ") || terminals.length() == 1) {
            String[] parts = terminals.split(", ");

            LinkedHashSet<Terminal> T = new LinkedHashSet<>();

            for (String ch : parts)
                T.add(new Terminal(ch.charAt(0)));

            cfGrammar.setT(T);

        } else
            throw new IllegalArgumentException("Invalid terminals: " + terminals);

        return this;
    }

    public GrammarParser addStart(Character S) {

        if (cfGrammar.getV() == null)
            throw new IllegalArgumentException("Starting variable can be defined only after variables.");

        Variable variable = new Variable(S);

        if (!cfGrammar.getV().contains(variable))
            throw new IllegalArgumentException("Starting variable can be defined only of existing variables.");

        cfGrammar.setS(variable);
        return this;
    }


    public GrammarParser addProduction(String production) {

        if (cfGrammar.getV() == null || cfGrammar.getT() == null)
            throw new IllegalArgumentException("Productions can be defined only after variables and terminals.");


        if (production.contains("->")) {
            String[] parts = production.split(" |->");
            int index = Integer.parseInt(parts[0]);
            Character part1 = parts[1].charAt(0);
            String part2 = parts[2];


            // build left side of production

            Variable leftSide = cfGrammar.getV().stream().parallel()
                    .filter(c -> c.getaChar() == part1).findAny()
                    .orElseThrow(() ->
                            new RuntimeException("Invalid production, character is not defined: " + part1)
                    );


            // build right side of production

            List<GrammarCharacter> rightSide = new ArrayList<>();

            List<GrammarCharacter> union = Stream.concat(cfGrammar.getV().stream(), cfGrammar.getT().stream())
                    .distinct()
                    .collect(Collectors.toList());

            for (Character ch : part2.toCharArray()) {
                GrammarCharacter variable = union.stream().parallel()
                        .filter(c -> c.getaChar() == ch).findAny()
                        .orElseThrow(() ->
                                new RuntimeException("Invalid production, character is not defined: " + ch)
                        );

                rightSide.add(variable);

            }

            Production P = new Production(index, leftSide, rightSide);

            // save new production
            cfGrammar.addProduction(P);

        } else {
            throw new IllegalArgumentException("Invalid production: " + production);
        }

        return this;
    }

    public CFGrammar build() {
        return cfGrammar;
    }

}

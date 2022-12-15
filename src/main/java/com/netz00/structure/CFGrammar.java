package com.netz00.structure;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Context-free grammar
 */
public class CFGrammar {
    private LinkedHashSet<Variable> V;
    private LinkedHashSet<Terminal> T;

    private LinkedHashSet<Production> P;
    private Variable S;


    public CFGrammar() {

        V = new LinkedHashSet<>();
        T = new LinkedHashSet<>();
        P = new LinkedHashSet<>();

    }


    public CFGrammar addVariables(String variables) {

        if (variables.contains(", ") || variables.length() == 1) {
            String[] parts = variables.split(", ");

            for (String ch : parts)
                this.V.add(new Variable(ch.charAt(0)));

        } else
            throw new IllegalArgumentException("Invalid variables: " + variables);

        return this;
    }

    public CFGrammar addTerminals(String terminals) {

        if (terminals.contains(", ") || terminals.length() == 1) {
            String[] parts = terminals.split(", ");

            for (String ch : parts)
                this.T.add(new Terminal(ch.charAt(0)));

        } else
            throw new IllegalArgumentException("Invalid terminals: " + terminals);

        return this;
    }

    public CFGrammar addStart(Character S) {

        if (V == null)
            throw new IllegalArgumentException("Starting variable can be defined only after variables.");

        Variable variable = new Variable(S);

        if (!V.contains(variable))
            throw new IllegalArgumentException("Starting variable can be defined only of existing variables.");

        this.S = variable;
        return this;
    }


    public CFGrammar addProduction(String production) {

        if (V == null || T == null)
            throw new IllegalArgumentException("Productions can be defined only after variables and terminals.");


        if (production.contains("->")) {
            String[] parts = production.split(" |->");
            int index = Integer.parseInt(parts[0]);
            Character part1 = parts[1].charAt(0);
            String part2 = parts[2];


            // build left side of production

            Variable leftSide = V.stream().parallel()
                    .filter(c -> c.getaChar() == part1).findAny()
                    .orElseThrow(() ->
                            new RuntimeException("Invalid production, character is not defined: " + part1)
                    );


            // build right side of production

            List<GrammarCharacter> rightSide = new ArrayList<>();

            List<GrammarCharacter> union = Stream.concat(V.stream(), T.stream())
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

            // save new production
            P.add(new Production(index, leftSide, rightSide));

        } else {
            throw new IllegalArgumentException("Invalid production: " + production);
        }

        return this;
    }

    @Override
    public String toString() {
        return "CFGrammar{" +
                "\n\tV = " + V +
                "\n\tT = " + T +
                "\n\tP = " + P +
                "\n\tS = " + S +
                "\n}";
    }


    public List<GrammarCharacter> removeUselessCharacters() {

        return Stream.concat(removeDeadVariables().stream(), removeUnreachableCharacters().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<GrammarCharacter> removeUselessCharactersWrong() {

        return Stream.concat(removeDeadVariables().stream(), removeUnreachableCharacters().stream())
                .distinct()
                .collect(Collectors.toList());
    }


    public List<Variable> removeDeadVariables() {


        /**
         * U listu živih znakova stave se lijeve strane produkcija
         * koje na desnoj nemaju nezavršnih znakova
         */

        List<Variable> alive = P
                .stream()
                .filter(p -> p.areRightSideOnlyTerminals())
                .collect(Collectors.toList())
                .stream()
                .map(p -> p.getLeftSide())
                .collect(Collectors.toList());


        /**
         * Ako su s desne strane produkcije isključivo
         * živi znakovi, onda se u listu doda znak s lijeve strane
         */
        for (Production production : P) {
            List<Variable> finalAlive = alive;
            alive = P
                    .stream()
                    .filter(p -> p.rightSideOnlyTerminalsOrAliveVariables(finalAlive))
                    .collect(Collectors.toList()).stream()
                    .map(p -> p.getLeftSide())
                    .collect(Collectors.toList());
        }

        /**
         * Find dead variables
         */

        List<Variable> finalAlive1 = alive;

        List<Variable> dead = V.stream()
                .filter(variable -> !finalAlive1.contains(variable))
                .collect(Collectors.toList());


        /**
         * Remove productions with dead variable at left or right side
         */
        List<Production> aliveProductions = P
                .stream()
                .filter(p -> !p.leftOrRightSideContainsVariables(dead))
                .collect(Collectors.toList());

        /**
         * Update productions and variables
         */

        // remove duplicates
        V = new LinkedHashSet<>(alive);

        P = new LinkedHashSet<>(aliveProductions);


        return dead;
    }


    public List<GrammarCharacter> removeUnreachableCharacters() {


        List<GrammarCharacter> reachable = new ArrayList<>();

        /**
         * U listu dohvatljivih znakova stavi se početni
         * nezavršni znak gramatike
         */
        reachable.add(this.S);

        /**
         * Ako je znak s lijeve strane produkcije dohvatljiv,
         * u listu se dodaju svi znakovi s desne strane produkcije
         */


        for (Production production : P)
            if (reachable.contains(production.getLeftSide()))
                reachable.addAll(production.getRightSide());

        // UNION variables and terminals

        List<GrammarCharacter> union = Stream.concat(V.stream(), T.stream())
                .distinct()
                .collect(Collectors.toList());

        // find
        List<GrammarCharacter> unreachableCharacters = union.stream()
                .filter(character -> !reachable.contains(character))
                .collect(Collectors.toList());

        // find
        List<Production> aliveProductions = P
                .stream()
                .filter(p -> !p.leftOrRightSideContainsCharacters(unreachableCharacters))
                .collect(Collectors.toList());


        for (GrammarCharacter c : unreachableCharacters)
            if (c.isVariable())
                V.remove((Variable) c);
            else
                T.remove((Terminal) c);


        P = new LinkedHashSet<>(aliveProductions);


        return unreachableCharacters;
    }

}

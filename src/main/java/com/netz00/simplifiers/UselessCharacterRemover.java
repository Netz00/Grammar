package com.netz00.simplifiers;

import com.netz00.structure.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UselessCharacterRemover {

    public CFGrammar result;
    public List<GrammarCharacter> uselessCharacters;
    public List<Variable> deadVariables;
    public List<GrammarCharacter> unreachableCharacters;

    public UselessCharacterRemover(CFGrammar cfGrammar) {
        this.result = cfGrammar;
    }

    public UselessCharacterRemover removeUselessCharacters() {

        this.uselessCharacters = Stream.concat(removeDeadVariables().deadVariables.stream(), removeUnreachableCharacters().unreachableCharacters.stream())
                .distinct()
                .collect(Collectors.toList());
        return this;
    }

    public UselessCharacterRemover removeDeadVariables() {


        /**
         * U listu živih znakova stave se lijeve strane produkcija
         * koje na desnoj nemaju nezavršnih znakova
         */

        List<Variable> alive = result.getP()
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
        for (Production production : result.getP()) {
            List<Variable> finalAlive = alive;
            alive = result.getP()
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

        List<Variable> dead = result.getV().stream()
                .filter(variable -> !finalAlive1.contains(variable))
                .collect(Collectors.toList());


        /**
         * Remove productions with dead variable at left or right side
         */
        List<Production> aliveProductions = result.getP()
                .stream()
                .filter(p -> !p.leftOrRightSideContainsVariables(dead))
                .collect(Collectors.toList());

        /**
         * Update productions and variables
         */

        // remove duplicates
        result.setV(new LinkedHashSet<>(alive));
        result.setP(new LinkedHashSet<>(aliveProductions));

        this.deadVariables = dead;
        return this;
    }


    public UselessCharacterRemover removeUnreachableCharacters() {


        List<GrammarCharacter> reachable = new ArrayList<>();

        /**
         * U listu dohvatljivih znakova stavi se početni
         * nezavršni znak gramatike
         */
        reachable.add(result.getS());

        /**
         * Ako je znak s lijeve strane produkcije dohvatljiv,
         * u listu se dodaju svi znakovi s desne strane produkcije
         */


        for (Production production : result.getP())
            if (reachable.contains(production.getLeftSide()))
                reachable.addAll(production.getRightSide());

        // UNION variables and terminals

        List<GrammarCharacter> union = Stream.concat(result.getV().stream(), result.getT().stream())
                .distinct()
                .collect(Collectors.toList());

        // find
        List<GrammarCharacter> unreachableCharacters = union.stream()
                .filter(character -> !reachable.contains(character))
                .collect(Collectors.toList());

        // find
        List<Production> aliveProductions = result.getP()
                .stream()
                .filter(p -> !p.leftOrRightSideContainsCharacters(unreachableCharacters))
                .collect(Collectors.toList());


        for (GrammarCharacter c : unreachableCharacters)
            if (c.isVariable())
                result.getV().remove((Variable) c);
            else
                result.getT().remove((Terminal) c);


        result.setP(new LinkedHashSet<>(aliveProductions));

        this.unreachableCharacters = unreachableCharacters;
        return this;
    }
}

package com.netz00.converter;

import com.netz00.structure.CFGrammar;
import com.netz00.structure.Production;
import com.netz00.structure.Variable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

// TODO epsilon production remover

public class EpsilonProductionRemover {

    public CFGrammar result;

    public EpsilonProductionRemover(CFGrammar cfGrammar) {
        this.result = cfGrammar;
    }

    public EpsilonProductionRemover removeEpsilonProductions() {


        /**
         * U listu praznih znakova stave se lijeve strane produkcija
         * koje na desnoj imaju samo prazne znakove ili su epsilon produkcije
         */

        boolean repeatSearch = true;
        List<Variable> empty = new ArrayList<>();
        while (repeatSearch) {
            repeatSearch = false;
            for (Production p : result.getP())
                if (p.isEpsilonProduction() || p.rightSideContainsOnlyVariables(empty)) {
                    empty.add(p.getLeftSide());
                    repeatSearch = true;
                }
        }


        /**
         * Za svaku produkciju koja na desnoj strani ima prazne znakove grade se nove produkcije
         */
        LinkedHashSet<Production> resultingProductions = new LinkedHashSet<>();

        for (Production p : result.getP())
            System.out.println("to...");

        return this;
    }

}

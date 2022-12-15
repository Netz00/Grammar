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


    @Override
    public String toString() {
        return "CFGrammar{" +
                "\n\tV = " + V +
                "\n\tT = " + T +
                "\n\tP = " + P +
                "\n\tS = " + S +
                "\n}";
    }

    public CFGrammar addProduction(Production p) {
        P.add(p);
        return this;
    }


    // --------------- SETTERS AND GETTERS ---------------
    public LinkedHashSet<Variable> getV() {
        return V;
    }

    public LinkedHashSet<Terminal> getT() {
        return T;
    }

    public LinkedHashSet<Production> getP() {
        return P;
    }

    public Variable getS() {
        return S;
    }


    public void setV(LinkedHashSet<Variable> v) {
        V = v;
    }

    public void setT(LinkedHashSet<Terminal> t) {
        T = t;
    }

    public void setP(LinkedHashSet<Production> p) {
        P = p;
    }

    public void setS(Variable s) {
        S = s;
    }
}

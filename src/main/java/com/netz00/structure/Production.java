package com.netz00.structure;

import java.util.List;

public class Production {

    // TODO enable Context dependent grammar, List to left side too
    private final int index;
    private Variable leftSide;
    private List<GrammarCharacter> rightSide;

    public Production(int index) {
        this.index = index;
    }

    public Production(int index, Variable leftSide, List<GrammarCharacter> rightSide) {
        this.index = index;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        return "\n\t\t\t" + index + ")" + leftSide.toString() + " -> " + rightSide.toString();
    }

    public boolean areRightSideOnlyTerminals() {
        for (GrammarCharacter c : rightSide)
            if (c.isVariable())
                return false;

        return true;
    }

    public boolean rightSideOnlyTerminalsOrAliveVariables(List<Variable> alive) {
        for (GrammarCharacter c : rightSide)
            if (c.isVariable() && !alive.contains(c)) // variable and dead variable
                return false;

        return true;
    }

    public Variable getLeftSide() {
        return leftSide;
    }

    public List<GrammarCharacter> getRightSide() {
        return rightSide;
    }

    public boolean leftOrRightSideContainsVariables(List<Variable> variables) {
        if (variables.contains(leftSide))
            return true;
        for (GrammarCharacter c : rightSide)
            if (variables.contains(c))
                return true;

        return false;
    }

    public boolean leftOrRightSideContainsCharacters(List<GrammarCharacter> characters) {
        if (characters.contains(leftSide))
            return true;

        for (GrammarCharacter c : rightSide)
            if (characters.contains(c))
                return true;

        return false;
    }

}

package com.netz00.structure;

public class Variable extends GrammarCharacter {
    private static final int start = 'A';
    private static final int end = 'S';


    public Variable(Character c) {
        setaChar(c);
    }

    @Override
    boolean checkCharacter(char aChar) {
        return start <= (int) aChar && end >= (int) aChar;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Variable)) return false;
        Variable fc = (Variable) other;
        return super.aChar.equals(fc.aChar);
    }
}

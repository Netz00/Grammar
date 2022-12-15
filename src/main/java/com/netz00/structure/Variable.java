package com.netz00.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Variable extends GrammarCharacter {
    private static List<Character> allowedChars;

    static {
        allowedChars = new ArrayList<Character>(Arrays.asList('A', 'B', 'C', 'D', 'E','S'));
    }


    public Variable() {
    }

    public Variable(Character c) {
        setaChar(c);
    }

    @Override
    boolean checkCharacter(char aChar) {
        return allowedChars.contains(aChar);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Variable))
            return false;
        Variable fc = (Variable) other;
        return super.aChar.equals(fc.aChar);
    }
}

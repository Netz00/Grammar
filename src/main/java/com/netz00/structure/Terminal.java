package com.netz00.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Terminal extends GrammarCharacter {
    private static List<Character> allowedChars;

    static {
        allowedChars = new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));
    }

    public Terminal() {
    }

    public Terminal(Character c) {
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
        if (!(other instanceof Terminal))
            return false;
        Terminal fc = (Terminal) other;
        return super.aChar.equals(fc.aChar);
    }
}

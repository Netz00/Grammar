package com.netz00.structure;

public class Terminal extends GrammarCharacter {
    private static final int start1 = 'a';
    private static final int end1 = 'z';
    private static final int start2 = '0';
    private static final int end2 = '9';


    public Terminal(Character c) {
        setaChar(c);
    }

    @Override
    boolean checkCharacter(char aChar) {

        return start1 <= (int) aChar && end1 >= (int) aChar
                || start2 <= (int) aChar && end2 >= (int) aChar;
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

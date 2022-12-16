package com.netz00.structure;

public abstract class GrammarCharacter {
    protected Character aChar = null;
    abstract boolean checkCharacter(char aChar);
    public void setaChar(Character aChar) {
        if (checkCharacter(aChar))
            this.aChar = aChar;
        else
            throw new RuntimeException("Invalid character");
    }
    public Character getaChar() {
        return aChar;
    }
    public boolean isVariable() {
        if (this instanceof Variable) return true;
        return false;
    }
    @Override
    public String toString() {
        return " " + aChar + " ";
    }
    public abstract boolean equals(Object other);
    @Override
    public int hashCode() {
        return aChar;
    }
}

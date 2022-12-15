package com.netz00;

import com.netz00.structure.CFGrammar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadCharactersTests {

    @Test
    public void Grammar1() {

        // Given
        CFGrammar CFGrammar = new CFGrammar()
                .addVariables("S, A, B, C")
                .addTerminals("a, b, c, d")
                .addProduction("1 S->aABS")
                .addProduction("2 S->bCACd")
                .addProduction("3 B->bAB")
                .addProduction("4 A->cSA")
                .addProduction("5 A->cCC")
                .addProduction("6 A->bAB")
                .addProduction("7 B->cSB")
                .addProduction("8 C->cS")
                .addProduction("9 C->c")
                .addStart('S');

        CFGrammar CFGrammarRes = new CFGrammar()
                .addVariables("S, A, C")
                .addTerminals("a, b, c, d")
                .addProduction("2 S->bCACd")
                .addProduction("4 A->cSA")
                .addProduction("5 A->cCC")
                .addProduction("8 C->cS")
                .addProduction("9 C->c")
                .addStart('S');

        // When
        CFGrammar.removeDeadVariables().toString();

        // Then
        assertEquals(CFGrammar.toString(), CFGrammarRes.toString());
    }

    @Test
    public void Grammar2() {

        // Given
        CFGrammar CFGrammar = new CFGrammar()
                .addVariables("S, A, B, C, D, E")
                .addTerminals("a, b, c, d, e, f, g")
                .addProduction("1 S->aAB")
                .addProduction("2 S->E")
                .addProduction("3 A->dDA")
                .addProduction("4 A->e")
                .addProduction("5 B->bE")
                .addProduction("6 B->f ")
                .addProduction("7 C->cAB ")
                .addProduction("8 C->dSD ")
                .addProduction("9 C->a")
                .addProduction("10 D->eA")
                .addProduction("11 E->fA")
                .addProduction("12 E->g")
                .addStart('S');


        CFGrammar CFGrammarRes = new CFGrammar()
                .addVariables("S, A, B, C, D, E")
                .addTerminals("a, b, c, d, e, f, g")
                .addProduction("1 S->aAB")
                .addProduction("2 S->E")
                .addProduction("3 A->dDA")
                .addProduction("4 A->e")
                .addProduction("5 B->bE")
                .addProduction("6 B->f ")
                .addProduction("7 C->cAB ")
                .addProduction("8 C->dSD ")
                .addProduction("9 C->a")
                .addProduction("10 D->eA")
                .addProduction("11 E->fA")
                .addProduction("12 E->g")
                .addStart('S');

        // When
        CFGrammar.removeDeadVariables().toString();

        // Then
        assertEquals(CFGrammar.toString(), CFGrammarRes.toString());
    }


    @Test
    public void Grammar3() {

        // Given
        CFGrammar CFGrammar = new CFGrammar()
                .addVariables("S, A, B")
                .addTerminals("a")
                .addProduction("1 S->AB")
                .addProduction("2 S->a")
                .addProduction("3 A->a")
                .addStart('S');

        CFGrammar CFGrammarRes = new CFGrammar()
                .addVariables("S, A")
                .addTerminals("a")
                .addProduction("2 S->a")
                .addProduction("3 A->a")
                .addStart('S');

        // When
        CFGrammar.removeDeadVariables().toString();

        // Then
        assertEquals(CFGrammar.toString(), CFGrammarRes.toString());
    }

}

package com.netz00;

import com.netz00.structure.CFGrammar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnreachableCharactersTests {

    @Test
    public void Grammar1() {

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
                .addVariables("S, A, B, D, E")
                .addTerminals("a, b, d, e, f, g")
                .addProduction("1 S->aAB")
                .addProduction("2 S->E")
                .addProduction("3 A->dDA")
                .addProduction("4 A->e")
                .addProduction("5 B->bE")
                .addProduction("6 B->f")
                .addProduction("10 D->eA")
                .addProduction("11 E->fA")
                .addProduction("12 E->g")
                .addStart('S');

        // When
        CFGrammar.removeUnreachableCharacters().toString();

        // Then
        assertEquals(CFGrammar.toString(), CFGrammarRes.toString());
    }

}

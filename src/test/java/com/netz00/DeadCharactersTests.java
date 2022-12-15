package com.netz00;

import com.netz00.structure.CFGrammar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadCharactersTests {

    @Test
    public void shouldAnswerWithTrue() {

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

}

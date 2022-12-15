package com.netz00;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.netz00.structure.CFGrammar;
import org.junit.jupiter.api.Test;

public class UselessCharactersTests {

    @Test
    public void shouldAnswerWithTrue() {

        // Given
        CFGrammar CFGrammar = new CFGrammar()
                .addVariables("S, A, B")
                .addTerminals("a")
                .addProduction("1 S->AB")
                .addProduction("2 S->a")
                .addProduction("3 A->a")
                .addStart('S');

        CFGrammar CFGrammarRes = new CFGrammar()
                .addVariables("S")
                .addTerminals("a")
                .addProduction("2 S->a")
                .addStart('S');

        // When
        CFGrammar.removeUselessCharacters().toString();

        // Then
        assertEquals(CFGrammar.toString(), CFGrammarRes.toString());
    }

}

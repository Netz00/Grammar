package com.netz00;

import com.netz00.converter.UselessCharacterRemover;
import com.netz00.frontend.GrammarParser;
import com.netz00.structure.CFGrammar;
import com.netz00.structure.GrammarCharacter;
import com.netz00.structure.Variable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UselessCharactersTests {

    @Test
    public void Grammar1() {

        // Given
        CFGrammar cfGrammar = new GrammarParser()
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
                .addStart('S')
                .build();

        CFGrammar CFGrammarRes = new GrammarParser()
                .addVariables("S, A, C")
                .addTerminals("b, c, d")
                .addProduction("2 S->bCACd")
                .addProduction("4 A->cSA")
                .addProduction("5 A->cCC")
                .addProduction("8 C->cS")
                .addProduction("9 C->c")
                .addStart('S')
                .build();


        // When
        CFGrammar result = new UselessCharacterRemover(cfGrammar).removeUselessCharacters().result;

        // Then
        assertEquals(result.toString(), CFGrammarRes.toString());
    }

    @Test
    public void Grammar2() {

        // Given
        CFGrammar cfGrammar = new GrammarParser()
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
                .addStart('S')
                .build();


        CFGrammar CFGrammarRes = new GrammarParser()
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
                .addStart('S')
                .build();


        // When
        CFGrammar result = new UselessCharacterRemover(cfGrammar).removeUselessCharacters().result;

        // Then
        assertEquals(result.toString(), CFGrammarRes.toString());
    }

    @Test
    public void Grammar3() {

        // Given
        CFGrammar cfGrammar = new GrammarParser()
                .addVariables("S, A, B")
                .addTerminals("a")
                .addProduction("1 S->AB")
                .addProduction("2 S->a")
                .addProduction("3 A->a")
                .addStart('S')
                .build();

        CFGrammar CFGrammarRes = new GrammarParser()
                .addVariables("S")
                .addTerminals("a")
                .addProduction("2 S->a")
                .addStart('S')
                .build();


        // When
        CFGrammar result = new UselessCharacterRemover(cfGrammar).removeUselessCharacters().result;

        // Then
        assertEquals(result.toString(), CFGrammarRes.toString());
    }

    @Test
    public void Grammar4() {

        // Given
        CFGrammar cfGrammar = new GrammarParser()
                .addVariables("S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R")
                .addTerminals("0, 1")
                .addProduction("1 S->A")
                .addProduction("2 S->E")
                .addProduction("3 A->0BA")
                .addProduction("4 A->0CD")
                .addProduction("5 E->0BE")
                .addProduction("6 B->0CF")
                .addProduction("7 B->0BB ")
                .addProduction("8 B->0CG ")
                .addProduction("9 E->0BC")
                .addProduction("10 E->0CH")
                .addProduction("11 B->1G")
                .addProduction("12 C->1H")
                .addProduction("13 G->1I")
                .addProduction("14 H->1J")
                .addProduction("15 I->1")
                .addProduction("16 A->ε")
                .addStart('S')
                .build();

        CFGrammar CFGrammarRes = new GrammarParser()
                .addVariables("S, A, B, G, I")
                .addTerminals("0, 1")
                .addProduction("1 S->A")
                .addProduction("3 A->0BA")
                .addProduction("7 B->0BB")
                .addProduction("11 B->1G")
                .addProduction("13 G->1I")
                .addProduction("15 I->1")
                .addProduction("16 A->ε")
                .addStart('S')
                .build();

        // When
        UselessCharacterRemover result = new UselessCharacterRemover(cfGrammar).removeUselessCharacters();

        // Then
        assertEquals(result.result.toString(), CFGrammarRes.toString());

        List<Variable> deadVariablesExpected = Arrays.asList(
                new Variable('C'),
                new Variable('D'),
                new Variable('E'),
                new Variable('F'),
                new Variable('H'),
                new Variable('J'),
                new Variable('K'),
                new Variable('L'),
                new Variable('M'),
                new Variable('N'),
                new Variable('O'),
                new Variable('P'),
                new Variable('Q'),
                new Variable('R')
        );
        assertEquals(result.deadVariables.toString(), deadVariablesExpected.toString());

        List<GrammarCharacter> unreachableCharactersExpected = new ArrayList<>();
        assertEquals(result.unreachableCharacters.toString(), unreachableCharactersExpected.toString());

        List<GrammarCharacter> uselessCharactersExpected = Arrays.asList(
                new Variable('C'),
                new Variable('D'),
                new Variable('E'),
                new Variable('F'),
                new Variable('H'),
                new Variable('J'),
                new Variable('K'),
                new Variable('L'),
                new Variable('M'),
                new Variable('N'),
                new Variable('O'),
                new Variable('P'),
                new Variable('Q'),
                new Variable('R')
        );
        assertEquals(result.uselessCharacters.toString(), uselessCharactersExpected.toString());
    }

}

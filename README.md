# Context free grammar project

### Features
- Parsing grammar defined as string
  - read variables, terminals, productions and start
- Grammar structure
  - define CFG
- Actions
  - removing dead variables
  - removing unreachable characters
  - removing useless characters
- Actions are covered with JUnit tests

### Usage Eample
```java
    ...

    CFGrammar cfGrammar = new GrammarParser()
            .addVariables("S, A, B")
            .addTerminals("a")
            .addProduction("1 S->AB")
            .addProduction("2 S->a")
            .addProduction("3 A->a")
            .addStart('S')
            .build();
            
    // build new grammar with converter and run desired action    
    UselessCharacterRemover uselessCharacterRemover = new UselessCharacterRemover(cfGrammar).removeUselessCharacters();
    
    // get new grammar
    CFGrammar result = uselessCharacterRemover.result;
    
    // get any part of result
    List<GrammarCharacter> uselessCharacters = uselessCharacterRemover.uselessCharacters;
    List<Variable> deadVariables = uselessCharacterRemover.deadVariables;
    List<GrammarCharacter> unreachableCharacters = uselessCharacterRemover.unreachableCharacters;
    
    ...
    
```
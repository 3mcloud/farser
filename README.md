Farser
========

*Formula paser/lexer with focus on logical formulas*

------

*Farser* is a very simplistic lexer library which splits a formula string into tokens.

The purpose of a lexer is simply to separate the input into tokens. No validation of the input is performed (this would be the task of a parser).

At the time of this writing, the lexer recognized the following token types:
  
  * **"("** - Left parenthesis
  * **")"** - Right parenthesis
  * **"&"** - Logical and
  * **"|"** - Logical or
  * **"~"** - Logical not
  * **","** - Comma
  * ATOM - Anything that is not a token symbol from above
  
  -> See the *TokenType* enumeration for a complete list of recognized symbols.
  
In addition to splitting a formula into tokens it also supports a prefix for any ATOM tokens. If it finds a string, separated by colon ":" (see *LexerToken#PREFIX_SEPARATOR_CHAR* it splits this into a value and a prefix (e.g. "PFX:val" is split into prefix "PFX" and value "val").
 

Example
---------

**Formula:**

`"A | (~PFX:someValue & ~otherValue) & aFunction(param1, param2)"`

**This will result in a list of LexerTokens with the following content:**

  1. `TokenType.ATOM`, value = "A"
  2. `TokenType.OR`, value = "|"
  3. `TokenType.LPAREN`, value = "("
  4. `TokenType.NOT`, value = "~"
  5. `TokenType.ATOM`, value = "someValue", prefix = "PFX"
  6. `TokenType.AND`, value = "&"
  7. `TokenType.NOT`, value = "~"
  8. `TokenType.ATOM`, value = "otherValue"
  9. `TokenType.RPAREN`, value = ")"
  10. `TokenType.AND`, value = "&"
  11. `TokenType.ATOM`, value = "aFunction"
  12. `TokenType.LPAREN`, value = "("
  13. `TokenType.ATOM`, value = "param1"
  14. `TokenType.COMMA`, value = ","
  15. `TokenType.ATOM`, value = "param2"
  16. `TokenType.RPAREN`, value = ")"

**Code example:**

```java
    List<LexerToken> lexed = DrgFormulaLexer.lex("... your formula ...");

```



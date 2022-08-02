package com.mmm.his.cer.utility.farser.lexer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import com.mmm.his.cer.utility.farser.lexer.domain.DomainCodeLexerToken;
import com.mmm.his.cer.utility.farser.lexer.domain.DomainCodeToken;
import java.util.List;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * farser
 *
 * @author a30w4zz
 */
public class DomainCodeLexerTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();


  @Test
  public void testLexFormula1() throws Exception {
    String input = "larynx or(not PDX:dxlarx and not otlarynx)";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("larynx"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.LPAREN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.LPAREN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.NOT.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("PDX:dxlarx"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.NOT.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("otlarynx"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.RPAREN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.RPAREN.getValue().orElseThrow(Exception::new)));

    assertThat(lex.size(), is(index + 1));

  }


  @Test
  public void testLexFormula2() throws Exception {
    // Testing some weird spacing
    String input = " larynx or(not PDX:dxlarx   and not  otlarynx ) ";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("larynx"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.LPAREN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.LPAREN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.NOT.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("PDX:dxlarx"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.NOT.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("otlarynx"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.RPAREN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.RPAREN.getValue().orElseThrow(Exception::new)));

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testLexFormulaEqual() throws Exception {
    // Equals test and variable assigning - the variable assigning does not make sense in this setup
    // but it is ok for testing. The DomainCodeLexer does not validate, it just splits into tokens.
    String input = " a = 1 or b := 2";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.EQUAL));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.EQUAL.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("1"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ASSIGN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.ASSIGN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("2"));

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testLexFormulaLtGtEq() throws Exception {
    // Equals test and greather-than and less-than.
    String input = " a >= 1 or b <= 2 or c > 3 or d < 4";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.GT_EQUAL));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.GT_EQUAL.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("1"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.LT_EQUAL));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.LT_EQUAL.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("2"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("c"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.GREATER_THAN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.GREATER_THAN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("3"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("d"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.LESS_THAN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.LESS_THAN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("4"));

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testLexFormulaSingleMultiCharacterToken() throws Exception {
    // Test to ensure that a single character token is properly recognized even when the character
    // is used in a multi character token as well.
    // ">" also appears within ">=".
    String input = " a >= 1 or c > 3";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.GT_EQUAL));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.GT_EQUAL.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("1"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("c"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.GREATER_THAN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.GREATER_THAN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("3"));

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testNoTokenBetweenAtoms() {
    String input = " a b";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testLexFormulaWithMethod() throws Exception {
    String input = "A or B and someFunction ( 1, 2,3) and C";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("A"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("B"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("someFunction"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.LPAREN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.LPAREN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("1"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.COMMA));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.COMMA.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("2"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.COMMA));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.COMMA.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("3"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.RPAREN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.RPAREN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("C"));

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testOperandAndOperatorWithVariousSpacing() throws Exception {
    // Tests that operators are recognized properly even though there is no spacing.
    // And test that multiple spaces do not interfere.
    String input = "someAtom and otherAtom or anAtomWith_oror and anAtomWith_andandand "
        + "and a   =   2 or(not operatorWithoutSpacing)and something "
        + "and not    somethingNegated and>invalidAndGreaterThanButLexingDoesNotCare a:=b";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("someAtom"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("otherAtom"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("anAtomWith_oror"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("anAtomWith_andandand"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.EQUAL));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.EQUAL.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("2"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.LPAREN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.LPAREN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.NOT.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("operatorWithoutSpacing"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.RPAREN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.RPAREN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("something"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.NOT.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("somethingNegated"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.GREATER_THAN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.GREATER_THAN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("invalidAndGreaterThanButLexingDoesNotCare"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ASSIGN));
    assertThat(lex.get(index).getValue(),
        is(DomainCodeToken.ASSIGN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    assertThat(lex.size(), is(index + 1));

  }


  @Test
  public void testGetValues1() {
    String input = "larynx or(PDX:dxlarx and not otlarynx)";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);
    String[] expected = new String[]{"larynx", "PDX:dxlarx", "otlarynx"};

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    List<String> listNames = DomainCodeLexer.getTokens(lex, DomainCodeToken.ATOM);
    assertThat(listNames, IsIterableContainingInOrder.contains(expected));
  }


  @Test
  public void testGetValues2() {
    String input = " larynx or ( PDX:dxlarx and not otlarynx ) ";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);
    String[] expected = new String[]{"larynx", "PDX:dxlarx", "otlarynx"};

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    List<String> listNames = DomainCodeLexer.getTokens(lex, DomainCodeToken.ATOM);
    assertThat(listNames, IsIterableContainingInOrder.contains(expected));
  }


  @Test
  public void testMalformedFormula1() {
    // Even a malformed formula should just get lexed fine - the lexer does not care about proper
    // formatting
    String input = "(a (b)";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.LPAREN));
    assertThat(lex.get(index).getValue(), is("("));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.LPAREN));
    assertThat(lex.get(index).getValue(), is("("));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.RPAREN));
    assertThat(lex.get(index).getValue(), is(")"));

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testMalformedFormula2() {
    // Even a malformed formula should just get lexed fine - the lexer does not care about proper
    // formatting
    String input = ":= a <-> b)";
    List<DomainCodeLexerToken> lex = DomainCodeLexer.lex(input);

    System.out.println();
    System.out.println("Input: "
        + input);
    System.out.println("Lexed: "
        + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DomainCodeToken.ASSIGN));
    assertThat(lex.get(index).getValue(), is(":="));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.LESS_THAN));
    assertThat(lex.get(index).getValue(), is("<"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.POINTER));
    assertThat(lex.get(index).getValue(), is("->"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    index++;
    assertThat(lex.get(index).getType(), is(DomainCodeToken.RPAREN));
    assertThat(lex.get(index).getValue(), is(")"));

    assertThat(lex.size(), is(index + 1));

  }

}

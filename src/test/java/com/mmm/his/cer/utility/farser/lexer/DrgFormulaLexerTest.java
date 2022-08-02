package com.mmm.his.cer.utility.farser.lexer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import com.mmm.his.cer.utility.farser.lexer.drg.DrgFormulaToken;
import com.mmm.his.cer.utility.farser.lexer.drg.DrgLexerToken;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * farser
 *
 * @author a30w4zz
 */
public class DrgFormulaLexerTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();


  @Test
  public void testLexFormula1() throws Exception {
    String input = "larynx |(~PDX:dxlarx & ~otlarynx)";
    List<DrgLexerToken> lex = DrgFormulaLexer.lex(input);

    System.out.println();
    System.out.println("Input: " + input);
    System.out.println("Lexed: " + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("larynx"));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.OR.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.LPAREN));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.LPAREN.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.NOT.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("dxlarx"));
    assertThat(lex.get(index).prefix, is("PDX"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.AND.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.NOT.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("otlarynx"));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.RPAREN));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.RPAREN.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    assertThat(lex.size(), is(index + 1));

  }


  @Test
  public void testLexFormula2() throws Exception {
    // Testing some weird spacing
    String input = " larynx|(~ PDX:dxlarx&~otlarynx ) ";
    List<DrgLexerToken> lex = DrgFormulaLexer.lex(input);

    System.out.println();
    System.out.println("Input: " + input);
    System.out.println("Lexed: " + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("larynx"));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.OR.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.LPAREN));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.LPAREN.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.NOT.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("dxlarx"));
    assertThat(lex.get(index).prefix, is("PDX"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.AND.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.NOT.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("otlarynx"));
    assertTrue(lex.get(index).prefix == null);

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.RPAREN));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.RPAREN.getValue().orElseThrow(Exception::new)));
    assertTrue(lex.get(index).prefix == null);

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testNoTokenBetweenAtoms() {
    String input = " a b";
    List<DrgLexerToken> lex = DrgFormulaLexer.lex(input);

    System.out.println();
    System.out.println("Input: " + input);
    System.out.println("Lexed: " + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    assertThat(lex.size(), is(index + 1));

  }

  @Test
  public void testOperandAndOperatorWithVariousSpacing() throws Exception {
    // Tests that operators are recognized properly even though there is no spacing.
    // And test that multiple spaces do not interfere.
    String input = "someAtom & otherAtom | anAtomWith_oror & anAtomWith_andandand "
        + "& a      2 |(~ operatorWithoutSpacing)& something "
        + "& ~    somethingNegated & invalidAndGreaterThanButLexingDoesNotCare a b";
    List<DrgLexerToken> lex = DrgFormulaLexer.lex(input);

    System.out.println();
    System.out.println("Input: " + input);
    System.out.println("Lexed: " + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("someAtom"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("otherAtom"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("anAtomWith_oror"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("anAtomWith_andandand"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("2"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.OR));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.OR.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.LPAREN));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.LPAREN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.NOT.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("operatorWithoutSpacing"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.RPAREN));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.RPAREN.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("something"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.NOT));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.NOT.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("somethingNegated"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.AND));
    assertThat(lex.get(index).getValue(),
        is(DrgFormulaToken.AND.getValue().orElseThrow(Exception::new)));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("invalidAndGreaterThanButLexingDoesNotCare"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    assertThat(lex.size(), is(index + 1));

  }


  @Test
  public void testGetValues1() {
    String input = "larynx |(PDX:dxlarx & ~otlarynx)";
    List<DrgLexerToken> lex = DrgFormulaLexer.lex(input);
    String[] expected = new String[] {"larynx", "dxlarx", "otlarynx"};

    System.out.println();
    System.out.println("Input: " + input);
    System.out.println("Lexed: " + lex);

    List<String> listNames = DrgFormulaLexer.getListNames(lex);
    assertThat(listNames, IsIterableContainingInOrder.contains(expected));
  }


  @Test
  public void testGetValues2() {
    String input = " larynx|( PDX:dxlarx&~otlarynx ) ";
    List<DrgLexerToken> lex = DrgFormulaLexer.lex(input);
    String[] expected = new String[] {"larynx", "dxlarx", "otlarynx"};

    System.out.println();
    System.out.println("Input: " + input);
    System.out.println("Lexed: " + lex);

    List<String> listNames = DrgFormulaLexer.getListNames(lex);
    assertThat(listNames, IsIterableContainingInOrder.contains(expected));
  }

  @Test
  public void testInvalidDrgFormulaPrefix() {
    // Prefix has to be with no spaces in combination with value
    String input = "PDX : dxlarx";

    exception.expect(InvalidParameterException.class);
    exception.expectMessage("Invalid ATOM ':'. Only 'prefix:value' or 'value' are allowed.");
    DrgFormulaLexer.lex(input);
  }


  @Test
  public void testMalformedFormula1() {
    // Even a malformed formula should just get lexed fine - the lexer does not care about proper
    // formatting
    String input = "(a (b)";
    List<DrgLexerToken> lex = DrgFormulaLexer.lex(input);

    System.out.println();
    System.out.println("Input: " + input);
    System.out.println("Lexed: " + lex);

    // Rather than checking equality on lists, make sure values from Lex are what we expect

    int index = 0;

    assertThat(lex.get(index).getType(), is(DrgFormulaToken.LPAREN));
    assertThat(lex.get(index).getValue(), is("("));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("a"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.LPAREN));
    assertThat(lex.get(index).getValue(), is("("));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.ATOM));
    assertThat(lex.get(index).getValue(), is("b"));

    index++;
    assertThat(lex.get(index).getType(), is(DrgFormulaToken.RPAREN));
    assertThat(lex.get(index).getValue(), is(")"));

    assertThat(lex.size(), is(index + 1));

  }


}

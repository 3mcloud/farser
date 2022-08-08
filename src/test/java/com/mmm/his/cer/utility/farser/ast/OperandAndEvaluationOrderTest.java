package com.mmm.his.cer.utility.farser.ast;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import com.mmm.his.cer.utility.farser.ast.AstTest.StringOperandSupplier;
import com.mmm.his.cer.utility.farser.ast.parser.DescentParser;
import com.mmm.his.cer.utility.farser.ast.setup.MaskedContext;
import com.mmm.his.cer.utility.farser.ast.setup.TestContext;
import com.mmm.his.cer.utility.farser.lexer.DrgFormulaLexer;
import com.mmm.his.cer.utility.farser.lexer.drg.DrgLexerToken;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 *
 *
 * @author Thomas Naeff
 *
 */
public class OperandAndEvaluationOrderTest {


  @Test
  public void testOperandAndEvaluationOrder1_noParenthesis() throws Exception {

    String formula = "A & B | C";
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex(formula);
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), Collections.emptyMap());

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();

    // Ensure that evaluation is left-to-right and only the relevant parts of the formula get
    // evaluated until 'true' is determined.
    List<String> mask = Arrays.asList("A", "B", "C");
    TestContext<String> context = new TestContext<>(mask);
    ast.evaluateExpression(context);
    System.out.println("Evaluated:" + context.getEvaluatedValuesInOrderOfEvaluation());
    assertThat(context.getEvaluatedValuesInOrderOfEvaluation(), contains("A", "B"));

  }

  @Test
  public void testOperandAndEvaluationOrder1_withParenthesisForAnd() throws Exception {

    String formula = "(A & B) | C";
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex(formula);
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), Collections.emptyMap());

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();

    // Ensure that evaluation is left-to-right and only the relevant parts of the formula get
    // evaluated until 'true' is determined.
    List<String> mask = Arrays.asList("A", "B", "C");
    TestContext<String> context = new TestContext<>(mask);
    ast.evaluateExpression(context);
    System.out.println("Evaluated:" + context.getEvaluatedValuesInOrderOfEvaluation());
    assertThat(context.getEvaluatedValuesInOrderOfEvaluation(), contains("A", "B"));

  }

  @Test
  public void testOperandAndEvaluationOrder1_withParenthesisForOr() throws Exception {

    String formula = "A & (B | C)";
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex(formula);
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), Collections.emptyMap());

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();

    // Ensure that evaluation is left-to-right and only the relevant parts of the formula get
    // evaluated until 'true' is determined.
    List<String> mask = Arrays.asList("A", "B", "C");
    TestContext<String> context = new TestContext<>(mask);
    ast.evaluateExpression(context);
    System.out.println("Evaluated:" + context.getEvaluatedValuesInOrderOfEvaluation());
    assertThat(context.getEvaluatedValuesInOrderOfEvaluation(), contains("A", "B"));

  }

  @Test
  public void testOperandAndEvaluationOrder2_noParenthesis() throws Exception {

    String formula = "C | A & B";
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex(formula);
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), Collections.emptyMap());

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();

    // Ensure that evaluation is left-to-right and only the relevant parts of the formula get
    // evaluated until 'true' is determined.
    List<String> mask = Arrays.asList("A", "B", "C");
    TestContext<String> context = new TestContext<>(mask);
    ast.evaluateExpression(context);
    System.out.println("Evaluated:" + context.getEvaluatedValuesInOrderOfEvaluation());
    assertThat(context.getEvaluatedValuesInOrderOfEvaluation(), contains("C"));

  }

  @Test
  public void testOperandAndEvaluationOrder2_withParenthesisForAnd() throws Exception {

    String formula = "C | (A & B)";
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex(formula);
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), Collections.emptyMap());

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();

    // Ensure that evaluation is left-to-right and only the relevant parts of the formula get
    // evaluated until 'true' is determined.
    List<String> mask = Arrays.asList("A", "B", "C");
    TestContext<String> context = new TestContext<>(mask);
    ast.evaluateExpression(context);
    System.out.println("Evaluated:" + context.getEvaluatedValuesInOrderOfEvaluation());
    assertThat(context.getEvaluatedValuesInOrderOfEvaluation(), contains("C"));

  }

  @Test
  public void testOperandAndEvaluationOrder2_withParenthesisForOr() throws Exception {

    String formula = "(C | A) & B";
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex(formula);
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), Collections.emptyMap());

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();

    // Ensure that evaluation is left-to-right and only the relevant parts of the formula get
    // evaluated until 'true' is determined.
    List<String> mask = Arrays.asList("A", "B", "C");
    TestContext<String> context = new TestContext<>(mask);
    ast.evaluateExpression(context);
    System.out.println("Evaluated:" + context.getEvaluatedValuesInOrderOfEvaluation());
    assertThat(context.getEvaluatedValuesInOrderOfEvaluation(), contains("C", "B"));

  }

}

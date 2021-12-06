package com.mmm.his.cer.utility.farser.ast;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.mmm.his.cer.utility.farser.ast.node.type.BooleanExpression;
import com.mmm.his.cer.utility.farser.ast.node.type.NodeSupplier;
import com.mmm.his.cer.utility.farser.ast.parser.DescentParser;
import com.mmm.his.cer.utility.farser.ast.parser.ExpressionResult;
import com.mmm.his.cer.utility.farser.lexer.DrgFormulaLexer;
import com.mmm.his.cer.utility.farser.lexer.drg.DrgLexerToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mike Funaro
 */
public class AstTest {

  final Map<String, NodeSupplier<DrgLexerToken, MaskedContext<String>>> suppliers = new HashMap<>();
  final Map<String, NodeSupplier<DrgLexerToken, MaskedContext<CustomTestOperand>>> customOperandSuppliers
      = new HashMap<>();

  @Before
  public void setUp() {
    suppliers.put("BILATERAL", new MsdrgGrouperFunctionSupplier());
  }

  @Test
  public void testTrueEval() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("(BILATERAL) & (D|E)");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();

    List<String> mask = Collections.singletonList("E");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();

    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(new Object[]{"luck", "E"}));
  }

  @Test
  public void testFalseEval() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("(A|B) & (D|E)");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(Collections.singletonList("A")));

    assertThat(evaluation.isMatched(), is(false));
  }

  @Test
  public void testComplexTrueEval() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("A | ((B & C) & (D | E | (F & G)))");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Arrays.asList("B", "C", "F", "G");
    ExpressionResult<MaskedContext<String>> evaluation = ast
        .evaluateExpression(new TestContext<>(mask));

    MaskedContext<String> context = evaluation.getContext();
    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(mask.toArray()));
  }

  @Test
  public void testComplexFalseEval() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("A | ((B & C) & (D | E | (F & G)))");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(Arrays.asList("B", "C", "G")));

    assertThat(evaluation.isMatched(), is(false));
  }

  @Test
  public void testLeftSideComplexLeftSideEval() {
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("((B & C) & (D | E | (F & G))) | A");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Arrays.asList("G", "F", "C", "B");
    ExpressionResult<MaskedContext<String>> evaluation = ast
        .evaluateExpression(new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();

    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(new Object[]{"B", "C", "F", "G"}));
  }

  @Test
  public void testLeftSideComplexRightSideEval() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("((B & C) & (D | E | (F & G))) | A");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Collections.singletonList("A");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();

    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(mask.toArray()));
  }

  @Test
  public void testWeirdTrueEval() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("(A | B| C| (D & (G & (F|H)))))");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Arrays.asList("D", "G", "H");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();

    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(mask.toArray()));
  }

  @Test
  public void testWeirdFalseEval() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("(A | B| C| (D & (G & (F|H)))))");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(Arrays.asList("D", "H")));

    assertThat(evaluation.isMatched(), is(false));
  }

  @Test
  public void testNegation() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("A | ~B");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Collections.singletonList("A");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();

    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(mask.toArray()));
  }

  @Test
  public void testNegationPresent() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("A | ~B");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Collections.singletonList("B");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));

    assertThat(evaluation.isMatched(), is(false));
  }

  @Test
  public void testNegationOtherThanTrue() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("A | ~B");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Collections.singletonList("G");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();
    assertThat(evaluation.isMatched(), is(true));

    // empty matches means that we satisfied the ~B part of the formula.
    assertThat(context.getMatches().isEmpty(), is(true));
  }

  @Test
  public void testCustomObjectOperandTrueEval() {
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("A | C");
    DescentParser<MaskedContext<CustomTestOperand>> parser = new DescentParser<>(
        lexerTokens.listIterator(),
        new CustomOperandSupplier(), customOperandSuppliers);

    DrgSyntaxTree<MaskedContext<CustomTestOperand>> ast = parser.buildExpressionTree();
    List<CustomTestOperand> mask = Collections
        .singletonList(new CustomTestOperand("A", "NOT_NEEDED"));
    ExpressionResult<MaskedContext<CustomTestOperand>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));

    MaskedContext<CustomTestOperand> context = evaluation.getContext();
    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(mask.toArray()));
  }

  @Test
  public void testCustomObjectOperandFalseEval() {
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("PDX:A | C");
    DescentParser<MaskedContext<CustomTestOperand>> parser = new DescentParser<>(
        lexerTokens.listIterator(),
        new CustomOperandSupplier(), customOperandSuppliers);
    DrgSyntaxTree<MaskedContext<CustomTestOperand>> ast = parser.buildExpressionTree();
    ExpressionResult<MaskedContext<CustomTestOperand>> evaluation = ast
        .evaluateExpression(
            new TestContext<>(Collections.singletonList(new CustomTestOperand("A", "NOT_NEEDED"))));

    assertThat(evaluation.isMatched(), is(false));
  }

  @Test
  public void testEvalOfAnotherAst() {
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("A | ~B");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();

    parser.setTokenIterator(DrgFormulaLexer.lex("A | (B & C").listIterator());

    DrgSyntaxTree<MaskedContext<String>> ast2 = parser.buildExpressionTree();

    List<String> mask = Collections.singletonList("A");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();

    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(mask.toArray()));

    List<String> mask2 = Arrays.asList("B", "C");
    ExpressionResult<MaskedContext<String>> evaluation2 = ast2.evaluateExpression(
        new TestContext<>(mask2));
    context = evaluation2.getContext();
    assertThat(evaluation2.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(mask2.toArray()));
  }

  @Test
  public void testSingleListEval() {
    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("A");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);
    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Collections.singletonList("A");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();
    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(mask.toArray()));
  }

  @Test
  public void testMatchesContainsOnlyOneSideOfOr() {

    List<DrgLexerToken> lexerTokens = DrgFormulaLexer.lex("(A|B) & (C|D)");
    DescentParser<MaskedContext<String>> parser = new DescentParser<>(lexerTokens.listIterator(),
        new StringOperandSupplier(), suppliers);

    DrgSyntaxTree<MaskedContext<String>> ast = parser.buildExpressionTree();
    List<String> mask = Arrays.asList("A", "C", "B");
    ExpressionResult<MaskedContext<String>> evaluation = ast.evaluateExpression(
        new TestContext<>(mask));
    MaskedContext<String> context = evaluation.getContext();
    assertThat(evaluation.isMatched(), is(true));
    assertThat(context.getMatches().size(), is(2));
    assertThat(context.getMatches().toArray(),
        Matchers.arrayContaining(new Object[]{"A", "C"}));
  }


  /**
   * Test implementation.
   */
  private static class CustomTestOperand {

    private final String value;
    private final String prefix;

    public CustomTestOperand(String value, String prefix) {
      this.value = value;
      this.prefix = prefix;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof CustomTestOperand)) {
        return false;
      }
      CustomTestOperand that = (CustomTestOperand) o;
      return value.equals(that.value) &&
          prefix.equals(that.prefix);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, prefix);
    }
  }

  private static class StringOperandSupplier implements
      NodeSupplier<DrgLexerToken, MaskedContext<String>> {

    @Override
    public BooleanExpression<MaskedContext<String>> createNode(DrgLexerToken token) {
      return new ContainsNodeForContext<>(token.value);
    }
  }

  private static class CustomOperandSupplier implements
      NodeSupplier<DrgLexerToken, MaskedContext<CustomTestOperand>> {

    @Override
    public BooleanExpression<MaskedContext<CustomTestOperand>> createNode(
        DrgLexerToken token) {
      return new ContainsNodeForContext<>(
          new CustomTestOperand(token.getValue(), token.getPrefix().orElse("NOT_NEEDED")));
    }
  }

  private static class MsdrgGrouperFunctionSupplier implements
      NodeSupplier<DrgLexerToken, MaskedContext<String>> {

    private final List<String> otherInformation;

    public MsdrgGrouperFunctionSupplier() {
      otherInformation = new ArrayList<>();
      otherInformation.add("luck");
    }

    @Override
    public BooleanExpression<MaskedContext<String>> createNode(DrgLexerToken token) {
      return new GrouperFunctionNode(otherInformation);
    }
  }

  private static class GrouperFunctionNode implements BooleanExpression<MaskedContext<String>> {

    final List<String> otherInformation;

    GrouperFunctionNode(List<String> otherInformation) {
      this.otherInformation = otherInformation;
    }

    @Override
    public boolean evaluate(MaskedContext<String> context) {
      if (otherInformation.contains("luck")) {
        context.accumulate("luck");
        return true;
      }
      return false;
    }
  }

  public static class TestContext<T> implements MaskedContext<T> {

    private List<T> mask;
    private Set<T> accumulator;

    public TestContext(List<T> mask) {
      this.mask = mask;
      this.accumulator = new HashSet<>();
    }

    public List<T> getMask() {
      return mask;
    }

    public void setMask(List<T> mask) {
      this.mask = mask;
    }

    public Set<T> getAccumulator() {
      return accumulator;
    }

    public void setAccumulator(Set<T> accumulator) {
      this.accumulator = accumulator;
    }

    public void accumulate(T value) {
      this.accumulator.add(value);
    }

    @Override
    public Set<T> getMatches() {
      return accumulator;
    }
  }

  public static class ContainsNodeForContext<T> implements BooleanExpression<MaskedContext<T>> {

    private T value;

    public ContainsNodeForContext(T value) {
      this.value = value;
    }

    @Override
    public boolean evaluate(MaskedContext<T> context) {
      if (context.getMask().contains(value)) {
        context.accumulate(value);
        return true;
      }
      return false;
    }
  }

  public interface MaskedContext<T> {

    List<T> getMask();

    void accumulate(T value);

    Set<T> getMatches();
  }
}

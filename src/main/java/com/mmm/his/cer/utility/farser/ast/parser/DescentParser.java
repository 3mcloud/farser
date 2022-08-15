package com.mmm.his.cer.utility.farser.ast.parser;

import com.mmm.his.cer.utility.farser.ast.DrgSyntaxTree;
import com.mmm.his.cer.utility.farser.ast.node.operator.And;
import com.mmm.his.cer.utility.farser.ast.node.operator.Not;
import com.mmm.his.cer.utility.farser.ast.node.operator.Or;
import com.mmm.his.cer.utility.farser.ast.node.type.BooleanExpression;
import com.mmm.his.cer.utility.farser.ast.node.type.NodeSupplier;
import com.mmm.his.cer.utility.farser.lexer.FarserException;
import com.mmm.his.cer.utility.farser.lexer.drg.DrgFormulaToken;
import com.mmm.his.cer.utility.farser.lexer.drg.DrgLexerToken;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

/**
 * Recursive descent parser that will build an Abstract syntax tree from a formula (list of formula
 * tokens).
 *
 * @author Mike Funaro
 *
 * @param <T> The type of the context used when evaluating the AST
 */
public class DescentParser<T> {

  private BooleanExpression<T> root;
  private DrgLexerToken currentToken;
  private Iterator<DrgLexerToken> tokenIterator;
  private final NodeSupplier<DrgLexerToken, T> defaultSupplier;
  private final Map<String, NodeSupplier<DrgLexerToken, T>> suppliers;

  /**
   * Ctor.
   *
   * @param tokenIterator   list of tokens to parse into the Abstract syntax tree.
   * @param defaultSupplier a factory which creates nodes for the tree. This supplier is used by
   *                        default when <code>suppliers</code> does not contain a node specific
   *                        supplier
   * @param suppliers       A map with node suppliers specific to certain tokens (token value as map
   *                        key)
   */
  public DescentParser(Iterator<DrgLexerToken> tokenIterator,
      NodeSupplier<DrgLexerToken, T> defaultSupplier,
      Map<String, NodeSupplier<DrgLexerToken, T>> suppliers) {
    this.tokenIterator = tokenIterator;
    this.currentToken = tokenIterator.next();
    if (defaultSupplier == null) {
      throw new FarserException(
          "Please provide at least a default supplier argument to DescentParser constructor");
    }
    this.defaultSupplier = defaultSupplier;

    // If there is no map, instantiate new map to avoid NPEs. If nothing is in the map the
    // defaultSupplier takes over.
    if (suppliers == null) {
      this.suppliers = new HashMap<>();
    } else {
      this.suppliers = suppliers;
    }
  }

  /**
   * Set a new tokenIterator so that we can build another AST using the same setup parser. Uses the
   * same {@link NodeSupplier}s which were set when the {@link DescentParser} was created.
   */
  public void setTokenIterator(ListIterator<DrgLexerToken> tokenIterator) {
    this.tokenIterator = tokenIterator;
    this.currentToken = tokenIterator.next();
  }

  /**
   * Build the abstract syntax tree.
   */
  public DrgSyntaxTree<T> buildExpressionTree() {
    expression();
    return this.getAst();
  }

  /**
   * Expression method which will build the OR after parsing a term.
   */
  private void expression() {
    term();
    while (currentToken.getType() == DrgFormulaToken.OR) {
      this.eat(DrgFormulaToken.OR);
      Or<T> or = new Or<>();
      or.setLeft(root);
      term();
      or.setRight(root);
      root = or;
    }
  }

  /**
   * Term method which will build the AND after parsing the factors or operands.
   */
  private void term() {
    factor();
    while (currentToken.getType() == DrgFormulaToken.AND) {
      this.eat(DrgFormulaToken.AND);
      And<T> and = new And<>();
      and.setLeft(root);
      factor();
      and.setRight(root);
      root = and;
    }
  }

  /**
   * Factor out a single the operands.
   */
  private void factor() {
    if (currentToken.getType() == DrgFormulaToken.ATOM) {

      NodeSupplier<DrgLexerToken, T> nodeSupplier = suppliers.getOrDefault(
          currentToken.value, defaultSupplier);
      root = nodeSupplier.createNode(currentToken);
      this.eat(DrgFormulaToken.ATOM);
    } else if (currentToken.getType() == DrgFormulaToken.LPAREN) {
      this.eat(DrgFormulaToken.LPAREN);
      this.expression();
      this.eat(DrgFormulaToken.RPAREN);
    } else if (currentToken.getType() == DrgFormulaToken.NOT) {
      this.eat(DrgFormulaToken.NOT);
      Not<T> not = new Not<>();
      factor();
      not.setChild(root);
      root = not;
    } else {
      throw new FarserException("Expression malformed on token " + currentToken);
    }
  }

  /**
   * Move the iterator forward if the current token matches the one passed in.
   *
   * @param type the type of the token to eat.
   */
  private void eat(DrgFormulaToken type) {
    if (currentToken.getType() == type && this.tokenIterator.hasNext()) {
      currentToken = this.tokenIterator.next();
    }
  }

  /**
   * Helper method that creates a new {@link DrgSyntaxTree} from the root.
   *
   * @return new DrgSyntaxTree
   */
  private DrgSyntaxTree<T> getAst() {
    return new DrgSyntaxTree<>(this.root);
  }
}

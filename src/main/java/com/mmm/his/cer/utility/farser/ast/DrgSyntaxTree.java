package com.mmm.his.cer.utility.farser.ast;

import com.mmm.his.cer.utility.farser.ast.node.type.BooleanExpression;
import com.mmm.his.cer.utility.farser.ast.parser.ExpressionResult;

/**
 * Class that wraps a {@link BooleanExpression} and provides methods to evaluate it.
 *
 * @author Mike Funaro
 */
public class DrgSyntaxTree<C> {

  private BooleanExpression<C> ast;

  public DrgSyntaxTree(BooleanExpression<C> ast) {
    this.ast = ast;
  }

  public void setAst(BooleanExpression<C> ast) {
    this.ast = ast;
  }

  /**
   * Evaluate an expression that was previously built by the parser.
   *
   * @param context the context object that will be used in the evaluation.
   * @return {@link ExpressionResult}
   *     ExpressionResult object which will have the data about the outcome of the evaluation.
   */
  public ExpressionResult<C> evaluateExpression(C context) {
    boolean evaluate = this.ast.evaluate(context);
    return new ExpressionResult<>(evaluate, context);
  }
}

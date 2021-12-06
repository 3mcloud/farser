package com.mmm.his.cer.utility.farser.ast.node.operator;

import com.mmm.his.cer.utility.farser.ast.node.type.NonTerminal;

/**
 * Implementation of a non-terminal node for use in the AST. This class represents a logical AND
 * operation.
 *
 * @param <C> The context type used in the terminal nodes.
 * @author Mike Funaro
 */
public class And<C> extends NonTerminal<C> {

  @Override
  public boolean evaluate(C context) {
    return left.evaluate(context) && right.evaluate(context);
  }

  @Override
  public String toString() {
    return "And{" + "left=" + left + ", right=" + right + '}';
  }
}

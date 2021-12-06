package com.mmm.his.cer.utility.farser.ast.node.operator;

import com.mmm.his.cer.utility.farser.ast.node.type.NonTerminal;

/**
 * Implementation of a non-terminal node for use in the AST. This class represents a logical OR
 * operation.
 *
 * @param <C> The context type used in the terminal nodes.
 * @author Mike Funaro
 */
public class Or<C> extends NonTerminal<C> {

  @Override
  public boolean evaluate(C context) {
    boolean leftTrue = left.evaluate(context);

    if (leftTrue) {
      return true;
    }

    return right.evaluate(context);
  }

  @Override
  public String toString() {
    return "Or{" + "left=" + left + ", right=" + right + '}';
  }
}

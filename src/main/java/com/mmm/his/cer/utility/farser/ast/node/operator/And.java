package com.mmm.his.cer.utility.farser.ast.node.operator;

import com.mmm.his.cer.utility.farser.ast.node.type.NonTerminal;

/**
 * Implementation of a non-terminal node for use in the AST. This class represents a logical AND
 * operation.
 *
 * @param <T> The context type used in the terminal nodes.
 * @author Mike Funaro
 */
public class And<T> extends NonTerminal<T> {

  @Override
  public boolean evaluate(T context) {
    return left.evaluate(context) && right.evaluate(context);
  }

  @Override
  public String toString() {
    return "And{" + "left=" + left + ", right=" + right + '}';
  }
}

package com.mmm.his.cer.utility.farser.ast.node.type;

/**
 * This class represents a non-terminal node in the AST. These types of nodes will have a left and a
 * right child.
 *
 * @param <C> The type used in the terminal nodes.
 * @author Mike Funaro
 */
public abstract class NonTerminal<C> implements BooleanExpression<C> {

  protected BooleanExpression<C> left;
  protected BooleanExpression<C> right;

  public void setLeft(BooleanExpression<C> left) {
    this.left = left;
  }

  public void setRight(BooleanExpression<C> right) {
    this.right = right;
  }

  @Override
  public String toString() {
    return "NonTerminal{" + "left=" + left + ", right=" + right + '}';
  }
}

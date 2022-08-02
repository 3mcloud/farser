package com.mmm.his.cer.utility.farser.ast.node.type;

/**
 * Interface for calling applications to implement so that they can provide custom terminal nodes
 * for special logic to be evaluated within the AST.
 *
 * @param <T> the token type that will be used to create the node, based on the list of lexed tokens
 *            that are used to create the AST
 * @param <C> the parametric type on {@link BooleanExpression} - the context data passed in when the
 *            AST is evaluated
 *
 * @author Mike Funaro
 */
public interface NodeSupplier<T, C> {

  /**
   * Create a terminal node. This is type defined on the class. The input will be a token of a
   * particular type which is used in the body of the method to create an instance of
   * {@link BooleanExpression} that is type defined again using the types on the class.
   *
   * @param token The formula token/operand for which to create the node for
   * @return BooleanExpression that was instantiated in this method.
   */
  BooleanExpression<C> createNode(T token);
}

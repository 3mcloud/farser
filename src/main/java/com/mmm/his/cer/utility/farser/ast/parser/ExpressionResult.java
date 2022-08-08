package com.mmm.his.cer.utility.farser.ast.parser;

/**
 * Class that contains all information about a expression match from {@link DescentParser}.
 *
 * @param <T> the type of context
 * @author Mike Funaro
 */
public class ExpressionResult<T> {

  private final boolean matched;
  private final T context;

  /**
   * Ctor.
   *
   * @param matched boolean true if the expression evaluated to true, false otherwise. When there is
   *                true in the matched field the contents of the matches field will have the
   *                terminal objects that were matched.
   * @param context the context used in evaluation.
   */
  public ExpressionResult(boolean matched, T context) {
    this.matched = matched;
    this.context = context;
  }

  public boolean isMatched() {
    return matched;
  }

  public T getContext() {
    return context;
  }
}

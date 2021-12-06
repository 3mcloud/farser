package com.mmm.his.cer.utility.farser.ast.parser;

/**
 * Class that contains all information about a expression match from {@link DescentParser}.
 *
 * @param <C> the type of context
 * @author Mike Funaro
 */
public class ExpressionResult<C> {

  private final boolean matched;
  private final C context;

  /**
   * Ctor.
   *
   * @param matched boolean true if the expression evaluated to true, false otherwise. When there is
   *                true in the matched field the contents of the matches field will have the
   *                terminal objects that were matched.
   * @param context the context used in evaluation.
   */
  public ExpressionResult(boolean matched, C context) {
    this.matched = matched;
    this.context = context;
  }

  public boolean isMatched() {
    return matched;
  }

  public C getContext() {
    return context;
  }
}

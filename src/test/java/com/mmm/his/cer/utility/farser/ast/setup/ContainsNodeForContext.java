package com.mmm.his.cer.utility.farser.ast.setup;

import com.mmm.his.cer.utility.farser.ast.node.type.BooleanExpression;

public class ContainsNodeForContext<T> implements BooleanExpression<MaskedContext<T>> {

  private T value;

  public ContainsNodeForContext(T value) {
    this.value = value;
  }

  @Override
  public boolean evaluate(MaskedContext<T> context) {
    context.evaluating(value);
    if (context.getMask().contains(value)) {
      context.accumulate(value);
      return true;
    }
    return false;
  }
}

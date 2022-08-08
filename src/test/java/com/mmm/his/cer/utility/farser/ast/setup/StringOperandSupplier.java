package com.mmm.his.cer.utility.farser.ast.setup;

import com.mmm.his.cer.utility.farser.ast.node.type.BooleanExpression;
import com.mmm.his.cer.utility.farser.ast.node.type.NodeSupplier;
import com.mmm.his.cer.utility.farser.lexer.drg.DrgLexerToken;

public class StringOperandSupplier implements
    NodeSupplier<DrgLexerToken, MaskedContext<String>> {

  @Override
  public BooleanExpression<MaskedContext<String>> createNode(DrgLexerToken token) {
    return new ContainsNodeForContext<>(token.value);
  }
}

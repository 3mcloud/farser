package com.mmm.his.cer.utility.farser.lexer;

import java.util.Optional;

/**
 * Token class which represents the pieces of a lexed string.
 *
 * @param <T> The type of tokens used
 * @author Mike Funaro
 */
public interface LexerToken<T extends TokenType<?>> {


  /**
   * The token.
   *
   * @return The token
   */
  T getType();

  /**
   * Returns the {@link CommonTokenType} for this token.
   *
   * @return The common token type
   */
  default Optional<CommonTokenType> getCommonType() {
    T type = getType();
    // Delegation for simpler access
    return type == null ? Optional.empty() : type.getCommonTokenType();
  }

  /**
   * The value of the token.<br />
   * Is the same as {@link TokenType#getValue()} for any {@link TokenType} other than the ones
   * marked with {@link CommonTokenType#ATOM}.
   *
   * @return The token value
   */
  String getValue();

}

package com.mmm.his.cer.utility.farser.lexer.drg;

import com.mmm.his.cer.utility.farser.lexer.CommonTokenType;
import com.mmm.his.cer.utility.farser.lexer.LexerToken;
import com.mmm.his.cer.utility.farser.lexer.TokenType;
import java.util.Optional;

/**
 * All recognized types of tokens that we need to be concerned with when lexing a DRG formula.
 *
 * @author Mike Funaro
 */
public enum DrgFormulaToken implements TokenType<DrgFormulaToken> {

  /**
   * Any substring which is not in a set of defined token characters here in {@link TokenType}. This
   * can be a list name in the application of our DRG formulas, it can be a method name etc.<br />
   * The atom token type here has no defined value. It will be available as {@link LexerToken} with
   * the value set as the non-token substring.
   */
  ATOM(null, CommonTokenType.ATOM),

  /**
   * A space in the formula string. Gets filtered out in {@link DrgFormulaTokenFactory}.
   */
  SPACE(" ", CommonTokenType.SPACE),

  /**
   * Left parenthesis.
   */
  LPAREN("(", CommonTokenType.LPAREN),

  /**
   * Right parenthesis.
   */
  RPAREN(")", CommonTokenType.RPAREN),

  /**
   * Logical AND.
   */
  AND("&"),

  /**
   * Logical OR.
   */
  OR("|"),

  /**
   * Logical NOT.
   */
  NOT("~", CommonTokenType.NOT);

  private final String value;
  private final CommonTokenType commonType;

  /**
   * A new token type.
   *
   * @param value      The token value, or <code>null</code> if not used
   * @param commonType The common token type, or <code>null</code> if not needed
   */
  DrgFormulaToken(String value, CommonTokenType commonType) {
    this.value = value;
    this.commonType = commonType;

  }

  /**
   * A new token type.
   *
   * @param value The token value
   */
  DrgFormulaToken(String value) {
    this(value, null);

  }

  @Override
  public Optional<String> getValue() {
    return Optional.ofNullable(value);
  }

  @Override
  public Optional<CommonTokenType> getCommonTokenType() {
    return Optional.ofNullable(commonType);
  }


}

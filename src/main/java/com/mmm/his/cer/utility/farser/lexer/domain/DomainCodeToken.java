package com.mmm.his.cer.utility.farser.lexer.domain;

import com.mmm.his.cer.utility.farser.lexer.CommonTokenType;
import com.mmm.his.cer.utility.farser.lexer.LexerToken;
import com.mmm.his.cer.utility.farser.lexer.TokenType;
import java.util.Optional;

/**
 * All recognized types of tokens that we need to be concerned with when lexing a string.
 *
 * @author Mike Funaro
 */
public enum DomainCodeToken implements TokenType<DomainCodeToken> {

  /**
   * Any substring which is not in a set of defined token characters here in {@link TokenType}. This
   * can be a list name in the application of our DRG formulas, it can be a method name etc.<br />
   * The atom token type here has no defined value. It will be available as {@link LexerToken} with
   * the value set as the non-token substring.
   */
  ATOM(null, CommonTokenType.ATOM),

  /**
   * A space in the domain code string. Gets filtered out in {@link DomainCodeTokenFactory}.
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
   * Assigning a value to a variable.
   */
  ASSIGN(":="),

  /**
   * A logical less-than-equal check (e.g. in an if-statement).
   */
  LT_EQUAL("<="),

  /**
   * A logical greater-than-equal check (e.g. in an if-statement).
   */
  GT_EQUAL(">="),

  /**
   * Logical AND.
   */
  AND("and"),

  /**
   * Logical OR.
   */
  OR("or"),

  /**
   * Logical NOT.
   */
  NOT("not", CommonTokenType.NOT),

  /**
   * A comma separating multiple function parameters.
   */
  COMMA(","),

  /**
   * A logical is-equal check (e.g. in an if-statement).
   */
  EQUAL("="),

  /**
   * A logical less-than check (e.g. in an if-statement).
   */
  GREATER_THAN(">"),

  /**
   * A logical greater-than check (e.g. in an if-statement).
   */
  LESS_THAN("<"),

  /**
   * A pointer.
   */
  POINTER("->");

  private final String value;
  private final CommonTokenType commonType;

  /**
   * A new token type.
   *
   * @param value      The token value, or <code>null</code> if not used
   * @param commonType The common token type, or <code>null</code> if not needed
   */
  DomainCodeToken(String value, CommonTokenType commonType) {
    this.value = value;
    this.commonType = commonType;

  }

  /**
   * A new token type.
   *
   * @param value The token value
   */
  DomainCodeToken(String value) {
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

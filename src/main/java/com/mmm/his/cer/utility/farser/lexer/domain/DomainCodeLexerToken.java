package com.mmm.his.cer.utility.farser.lexer.domain;


import com.mmm.his.cer.utility.farser.lexer.LexerToken;
import com.mmm.his.cer.utility.farser.lexer.TokenType;
import java.util.Objects;

/**
 * Token class that can be used to represent the pieces of a lexed string.
 *
 * @author Mike Funaro
 */
public class DomainCodeLexerToken implements LexerToken<DomainCodeToken> {

  public final DomainCodeToken type;
  public final String value;

  /**
   * A new token with the value from the {@link TokenType}.
   *
   * @param type The token type
   */
  public DomainCodeLexerToken(DomainCodeToken type) {
    this.type = type;
    this.value = type.getValue().orElse(null);
  }

  /**
   * A new token with a provided value.
   *
   * @param type  The token type
   * @param value The token value
   */
  public DomainCodeLexerToken(DomainCodeToken type, String value) {
    this.type = type;
    this.value = value;
  }

  @Override
  public DomainCodeToken getType() {
    return type;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof DomainCodeLexerToken)) {
      return false;
    }
    DomainCodeLexerToken other = (DomainCodeLexerToken) obj;
    return type == other.type && Objects.equals(value, other.value);
  }

  @Override
  public String toString() {
    if (type == DomainCodeToken.ATOM) {
      return type.name() + "<" + value + ">";
    }
    return type.toString();
  }
}

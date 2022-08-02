package com.mmm.his.cer.utility.farser.lexer.drg;


import com.mmm.his.cer.utility.farser.lexer.LexerToken;
import com.mmm.his.cer.utility.farser.lexer.TokenType;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Token class that can be used to represent the pieces of a lexed string.
 *
 * @author Mike Funaro
 */
public class DrgLexerToken implements LexerToken<DrgFormulaToken> {

  public static final String PREFIX_SEPARATOR_STRING = ":";
  /**
   * The regex pattern for {@link #PREFIX_SEPARATOR_STRING}.
   */
  public static final Pattern PREFIX_SEPARATOR_PATTERN =
      Pattern.compile(DrgLexerToken.PREFIX_SEPARATOR_STRING);
  public final DrgFormulaToken type;
  public final String value;
  public final String prefix;

  /**
   * A new token with the value from the {@link TokenType}.
   *
   * @param type The token type
   */
  public DrgLexerToken(DrgFormulaToken type) {
    this.type = type;
    this.value = type.getValue().orElse(null);
    this.prefix = null;
  }

  /**
   * A new token with a provided value.
   *
   * @param type  The token type
   * @param value The token value
   */
  public DrgLexerToken(DrgFormulaToken type, String value) {
    this.type = type;
    this.value = value;
    this.prefix = null;
  }

  /**
   * A new token.
   *
   * @param type   The token type
   * @param value  The token value
   * @param prefix The prefix (if there is one)
   */
  public DrgLexerToken(DrgFormulaToken type, String value, String prefix) {
    this.type = type;
    this.value = value;
    this.prefix = prefix;
  }


  @Override
  public DrgFormulaToken getType() {
    return type;
  }

  @Override
  public String getValue() {
    return value;
  }

  public Optional<String> getPrefix() {
    return Optional.ofNullable(prefix);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prefix, type, value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof DrgLexerToken)) {
      return false;
    }
    DrgLexerToken other = (DrgLexerToken) obj;
    return Objects.equals(prefix, other.prefix)
        && type == other.type
        && Objects.equals(value, other.value);
  }

  @Override
  public String toString() {
    if (type == DrgFormulaToken.ATOM) {
      String prefixString = "";
      if (prefix != null && !prefix.equals("")) {
        prefixString = " with prefix " + prefix;
      }
      return type.name() + "<" + value + ">" + prefixString;
    }
    return type.toString();
  }
}

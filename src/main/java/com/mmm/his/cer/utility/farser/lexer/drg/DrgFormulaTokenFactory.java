package com.mmm.his.cer.utility.farser.lexer.drg;

import com.mmm.his.cer.utility.farser.lexer.LexerTokenFactory;
import java.security.InvalidParameterException;

/**
 * The factory which creates a {@link DrgLexerToken}.
 *
 * @author Thomas Naeff
 */
public class DrgFormulaTokenFactory implements LexerTokenFactory<DrgLexerToken, DrgFormulaToken> {

  @Override
  public DrgLexerToken create(DrgFormulaToken tokenType, String value) {
    if (tokenType == DrgFormulaToken.SPACE) {
      // Ignore spaces
      return null;
    } else if (tokenType == DrgFormulaToken.ATOM) {
      return buildTokenFromAtom(value);
    } else {
      return new DrgLexerToken(tokenType, value);
    }
  }


  /**
   * Splits an atom string into its prefix and value if both are present, or just creates a token
   * with the value if no prefix is present.
   *
   * @param atom the atom string
   * @return The token with value and with or without prefix
   */
  private static DrgLexerToken buildTokenFromAtom(String atom) {
    String[] split = DrgLexerToken.PREFIX_SEPARATOR_PATTERN.split(atom);
    if (split.length == 2) {
      // Prefix and value
      return new DrgLexerToken(DrgFormulaToken.ATOM, split[1].trim(), split[0].trim());
    } else if (split.length == 1) {
      // Only value
      return new DrgLexerToken(DrgFormulaToken.ATOM, split[0].trim());
    } else {
      throw new InvalidParameterException("Invalid "
          + DrgFormulaToken.ATOM.name()
          + " '"
          + atom
          + "'. Only 'prefix"
          + DrgLexerToken.PREFIX_SEPARATOR_STRING
          + "value' or 'value' are allowed.");
    }
  }


}

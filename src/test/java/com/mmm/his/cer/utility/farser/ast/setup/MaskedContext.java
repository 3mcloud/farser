package com.mmm.his.cer.utility.farser.ast.setup;

import java.util.List;
import java.util.Set;

public interface MaskedContext<T> {

  List<T> getMask();

  void accumulate(T value);

  void evaluating(T value);

  Set<T> getMatches();
}

package com.mmm.his.cer.utility.farser.ast.setup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestContext<T> implements MaskedContext<T> {

  private List<T> mask;
  private Set<T> accumulator;
  private List<T> evaluatedValuesInOrderOfEvaluation;

  public TestContext(List<T> mask) {
    this.mask = mask;
    this.accumulator = new HashSet<>();
    this.evaluatedValuesInOrderOfEvaluation = new ArrayList<>();
  }

  @Override
  public List<T> getMask() {
    return mask;
  }

  public void setMask(List<T> mask) {
    this.mask = mask;
  }

  public Set<T> getAccumulator() {
    return accumulator;
  }

  public void setAccumulator(Set<T> accumulator) {
    this.accumulator = accumulator;
  }

  @Override
  public void accumulate(T value) {
    this.accumulator.add(value);
  }

  @Override
  public Set<T> getMatches() {
    return accumulator;
  }

  @Override
  public void evaluating(T value) {
    this.evaluatedValuesInOrderOfEvaluation.add(value);
  }

  public List<T> getEvaluatedValuesInOrderOfEvaluation() {
    return evaluatedValuesInOrderOfEvaluation;
  }

}

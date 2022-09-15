package org.frcteam4146.common.util;

public interface InverseInterpolable<T> {
  double inverseInterpolate(T upper, T query);
}

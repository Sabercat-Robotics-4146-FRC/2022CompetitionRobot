package org.frcteam4146.common.util;

public interface Interpolable<T> {
  T interpolate(T other, double t);
}

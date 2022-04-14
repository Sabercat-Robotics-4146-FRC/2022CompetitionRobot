package org.frcteam2910.common.util;

import org.frcteam2910.common.math.Vector2;

public class HolonomicDriveSignal {
  private final Vector2 translation;
  private final double rotation;
  private final boolean fieldOriented;

  public HolonomicDriveSignal(
      Vector2 translationalVelocity, double rotation, boolean fieldOriented) {
    this.translation = translationalVelocity;
    this.rotation = rotation;
    this.fieldOriented = fieldOriented;
  }

  public Vector2 getTranslation() {
    return translation;
  }

  public double getRotation() {
    return rotation;
  }

  public boolean isFieldOriented() {
    return fieldOriented;
  }
}

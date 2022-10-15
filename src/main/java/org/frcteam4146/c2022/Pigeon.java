package org.frcteam4146.c2022;

import com.ctre.phoenix.sensors.Pigeon2;
import org.frcteam4146.common.drivers.Gyroscope;
import org.frcteam4146.common.math.Rotation2;

public class Pigeon extends Gyroscope {
  private final Pigeon2 pigeon;
  private double offset;

  public Pigeon(int id) {

    this.pigeon = new Pigeon2(id);
  }

  @Override
  public void calibrate() {
    offset = pigeon.getYaw();
  }

  @Override
  public Rotation2 getUnadjustedAngle() {
    return Rotation2.fromDegrees(pigeon.getYaw() - offset);
  }

  @Override
  public double getUnadjustedRate() {
    return 0.0;
  }
}

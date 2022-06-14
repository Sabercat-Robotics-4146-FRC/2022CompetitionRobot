package org.frc.common.robot.drivers;

import org.frc.common.drivers.Gyroscope;
import org.frc.common.math.Rotation2;

import edu.wpi.first.wpilibj.AnalogGyro;

public final class AnalogGyroscope extends Gyroscope {

  private final AnalogGyro gyro;

  public AnalogGyroscope(int analogPort) {
    this(new AnalogGyro(analogPort));
  }

  public AnalogGyroscope(AnalogGyro gyro) {
    this.gyro = gyro;
  }

  @Override
  public void calibrate() {
    gyro.calibrate();
  }

  @Override
  public Rotation2 getUnadjustedAngle() {
    return Rotation2.fromDegrees(gyro.getAngle());
  }

  @Override
  public double getUnadjustedRate() {
    return gyro.getRate();
  }
}

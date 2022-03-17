package org.frcteam2910.c2020.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
  public NetworkTable mLime;

  public double limelightHeight = 29.75;

  public double targetHeight = 104;

  public double Kp = -0.1;

  public double minCommand = 0.05;

  public Limelight() {
    mLime = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public boolean getSeesTarget() {
    return mLime.getEntry("tv").getBoolean(true);
  }

  public double getHorzontalOffset() {
    return mLime.getEntry("tx").getDouble(0);
  }

  public double getVerticalOffset() {
    return mLime.getEntry("ty").getDouble(0);
  }

  public double adjustHeading() {
    double steeringAdjust = 0.0;
    double headingError = -getHorzontalOffset();

    if (getHorzontalOffset() > 1.0) {
      steeringAdjust = Kp * headingError - minCommand;
    } else if (getHorzontalOffset() < 1.0) {
      steeringAdjust = Kp * headingError + minCommand;
    }
    return steeringAdjust;
  }

  public double getDistanceFromTarget() {
    double verticalOffset = getVerticalOffset() * (Math.PI / 180.0);

    return (targetHeight - limelightHeight) / Math.tan(verticalOffset);
  }
}

package org.frcteam2910.c2020.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class limelight {
  public static NetworkTable mLime;

  public double limelightHeight = 29.75;

  public double targetHeight = 104;

  public double Kp = -0.1;

  public double minCommand = 0.05;

  public limelight() {
    mLime = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public boolean getSeesTarget() {
    return mLime.getEntry("tv").getBoolean(true);
  }

  public static double getHorizontalOffset() {
    return mLime.getEntry("tx").getDouble(0);
  }

  public double getVerticalOffset() {
    return mLime.getEntry("ty").getDouble(0);
  }

  public double adjustHeading() {
    double steeringAdjust = 0.0;
    double headingError = -getHorizontalOffset();

    if (getHorizontalOffset() > 1.0) {
      steeringAdjust = Kp * headingError - minCommand;
    } else if (getHorizontalOffset() < 1.0) {
      steeringAdjust = Kp * headingError + minCommand;
    }
    return steeringAdjust;
  }

  public double getDistanceFromTarget() {
    double verticalOffset = getVerticalOffset() * (Math.PI / 180.0);

    return (targetHeight - limelightHeight) / Math.tan(verticalOffset);
  }
}

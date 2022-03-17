package org.frcteam2910.c2020.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
  public NetworkTable mLime;

  public double limelightHeight = 29.75;

  public double targetHeight = 104;

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

  public double getDistanceFromTarget() {
    double verticalOffset = getVerticalOffset() * (Math.PI/ 180.0);
    
    return (targetHeight-limelightHeight)/Math.tan(verticalOffset);
  }
}

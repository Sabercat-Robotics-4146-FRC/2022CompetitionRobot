package org.frcteam4146.c2022.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Limelight implements Subsystem {
  public static NetworkTable mLime;

  public double limelightHeight = 29.75 / 100.0;
  public double targetHeight = 104 / 100.0 + 0.2;
  public double ballSpeed = 15; // TODO: Recalculate if necessary
  public double cameraAng =
      40; // TODO: calculate the angle the limelight is at, set this to that angle.

  public Servos servos;

  public boolean tracking = true;

  public Limelight(Servos servos) {

    this.servos = servos;
    mLime = NetworkTableInstance.getDefault().getTable("limelight");

    SmartDashboard.putNumber("Camera Angle", cameraAng);

    SmartDashboard.putNumber("Ball Exit Speed", ballSpeed);
  }

  public boolean getSeesTarget() {
    return mLime.getEntry("tv").getBoolean(true);
  }

  public double getHorizontalOffset() {
    return mLime.getEntry("tx").getDouble(0);
  }

  public double getVerticalOffset() {
    return mLime.getEntry("ty").getDouble(0) + cameraAng;
  }

  public double getDistanceFromTarget() {
    double verticalOffset = getVerticalOffset() * (Math.PI / 180.0);
    return (targetHeight - limelightHeight) / Math.tan(verticalOffset);
  }

  public double calculateShootingAngle() {
    double g = 9.81;
    double x = getDistanceFromTarget();
    double y = targetHeight - limelightHeight;
    double s = ballSpeed;
    // This calculation may return nan if the radicand is less than 0. This means the shot is
    // impossible.
    double theta =
        Math.atan((s * s + Math.sqrt(s * s * s * s - g * (g * x * x + 2 * y * s * s))) / (g * x));
    SmartDashboard.putNumber("Theta", theta);
    return theta;
  }

  public boolean isTracking() {
    return tracking;
  }

  public void toggleTracking() {
    tracking = !tracking;
  }

  public void toggleTracking(boolean state) {
    tracking = state;
  }

  @Override
  public void periodic() {

    SmartDashboard.putBoolean("Limelight Tracking", tracking);
    SmartDashboard.putNumber("ty", mLime.getEntry("ty").getDouble(0.0));
    ballSpeed = SmartDashboard.getNumber("Ball Exit Speed", 0);
    cameraAng = SmartDashboard.getNumber("Camera Angle", 0);

    if (tracking) {
      if (getSeesTarget()) {
        double desAng = calculateShootingAngle();
        servos.setServos(desAng);
      } else {
        System.out.println("Cannot see target");
        servos.setServos(0.5);
      }
    }
  }
}

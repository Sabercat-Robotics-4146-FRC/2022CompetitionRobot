package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.LimelightConstants;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Limelight implements Subsystem {
  public static NetworkTable mLime;

  public double ballSpeed = LimelightConstants.BALL_SPEEDS[0];
  public double cameraAng = LimelightConstants.CAMERA_ANG;

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
    return (LimelightConstants.HEIGHT_DIFF) / Math.tan(verticalOffset)
        + LimelightConstants.HOOP_AIM_OFFSET;
  }

  public double calculateShootingAngle() {
    double g = 9.81;
    double x = getDistanceFromTarget();
    double y = LimelightConstants.HEIGHT_DIFF;
    double s = ballSpeed;
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

  public double calculateRotation() {
    double rotationSpeed = 0;
    if (getSeesTarget()) {
      double horizontal_angle = getHorizontalOffset();
      if (Math.abs(horizontal_angle) > 0.03) {
        rotationSpeed = 0.3 * Math.sin(Math.PI * horizontal_angle / 54);
      }
    } else {
      rotationSpeed = 0.1;
    }
    return rotationSpeed;
  }

  @Override
  public void periodic() {

    SmartDashboard.putBoolean("Limelight Tracking", tracking);
    SmartDashboard.putNumber("ty", mLime.getEntry("ty").getDouble(0.0));
    // ballSpeed = SmartDashboard.getNumber("Ball Exit Speed", 0);
    cameraAng = SmartDashboard.getNumber("Camera Angle", 0);

    SmartDashboard.putNumber("Distance", getDistanceFromTarget());

    double desAng = calculateShootingAngle();
    double d = getDistanceFromTarget();
    ballSpeed = 4.75;
    if (d < LimelightConstants.DIST_CUTOFF[1]) ballSpeed = LimelightConstants.BALL_SPEEDS[2]; //
    else if (d < LimelightConstants.DIST_CUTOFF[0])
      ballSpeed = LimelightConstants.BALL_SPEEDS[1]; //
    else ballSpeed = LimelightConstants.BALL_SPEEDS[0];

    if (tracking && getSeesTarget()) servos.setServos(desAng);
  }
}

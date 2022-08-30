package org.frcteam2910.c2020.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Limelight implements Subsystem {
  public static NetworkTable mLime;

  // Servos are a part of limelight

  private Servo servoLeft;
  private Servo servoRight;

  public double limelightHeight = 29.75 / 100.0;

  public double targetHeight = 104 / 100.0;

  public double ballSpeed =
      7; // TODO: Recalculate if necessary

  public double Kp = -0.1;

  public double minCommand = 0.05;

  public double cameraAng =
      20
          * (Math.PI
              / 180); // TODO: calculate the angle the limelight is at, set this to that angle.

  public Limelight() {

    mLime = NetworkTableInstance.getDefault().getTable("limelight");
    servoLeft = new Servo(0);
    servoRight = new Servo(1);
  }

  public boolean getSeesTarget() {
    return mLime.getEntry("tv").getBoolean(true);
  }

  public static double getHorizontalOffset() {
    return mLime.getEntry("tx").getDouble(0);
  }

  public double getVerticalOffset() {
    return mLime.getEntry("ty").getDouble(0) + cameraAng;
  }

  public double adjustHeading() {
    double steeringAdjust = 0.0;
    double headingError = -getHorizontalOffset();
    SmartDashboard.putBoolean("tv", mLime.getEntry("tv").getBoolean(true));
    SmartDashboard.putNumber("tx", mLime.getEntry("tx").getDouble(0.0));
    SmartDashboard.putNumber("ty", mLime.getEntry("ty").getDouble(0.0));

    if (getSeesTarget()) {
      if (getHorizontalOffset() > 1.0) {
        steeringAdjust = Kp * headingError - minCommand;
      } else if (getHorizontalOffset() < 1.0) {
        steeringAdjust = Kp * headingError + minCommand;
      }
    }
    // return 0.0;
    return steeringAdjust;
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
  /*
  Will calculate the extension of the servo from 0-1 necessary to achieve target angle
   */
  public double calculateServoPosition() {
    double theta = calculateShootingAngle();

    // TODO: Roughly measured initial and final arc points from cad file, remeasure if needed
    double circleRadius = 25 / 100.0;
    double initx = 21.5 / 100.0;
    double inity = 9.5 / 100.0;
    double finx = 14.5 / 100.0;
    double finy = 20.5 / 100.0;
    double maxExtension = 15 / 100.0;

    double arcLength =
        circleRadius
            * Math.acos(
                1
                    - Math.pow(
                            Math.sqrt(Math.pow(initx - finx, 2) + Math.pow(inity - finy, 2))
                                / circleRadius,
                            2)
                        / 2);
    double m = Math.tan(theta);
    double extension = arcLength / m / maxExtension - Math.sqrt(initx * initx + inity * inity);
    SmartDashboard.putNumber("Extension", extension);
    // Scaled from 0 to 1, if outside this range, the shot is unobtainable at the current robot
    // position
    return extension;
  }

  public boolean varyServos() {
    double ext = calculateServoPosition();
    if (0 <= ext && ext <= 1) {
      varyServos(ext);
      return true;
    }
    return false;
  }

  public void varyServos(double ext) {
    servoLeft.set(ext);
    servoRight.set(ext);
  }

  public void periodic() {
    if (getSeesTarget()) {
      varyServos();
    } else {
      varyServos(0.5);
    }
  }
}

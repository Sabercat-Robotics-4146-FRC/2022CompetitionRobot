package org.frcteam4146.c2022.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.c2022.subsystems.Limelight;
import org.frcteam4146.common.math.Vector2;

public class AimRobotCommand extends CommandBase {
  public final DrivetrainSubsystem drivetrainSubsystem;
  public final Limelight limelight;

  private double pastRotationSpeed = 0;

  public AimRobotCommand(DrivetrainSubsystem drivetrainSubsystem, Limelight limelight) {
    this.drivetrainSubsystem = drivetrainSubsystem;
    this.limelight = limelight;

    addRequirements(drivetrainSubsystem);
  }

  @Override
  public void execute() {
    /*TODO: Find out which direction is negative and which is positive from limelight
     This may be more than what is necessary. I considered just doing an inverse kinematics for
     the wheel velocity, but, this allows for time to be ignored, which is not possible with
     inverse kinematics.
    */
    double rotationSpeed = 0;
    double min = 0.05;
    double max = 0.25;
    if (limelight.getSeesTarget()) {
      double horizontal_angle = -limelight.getHorizontalOffset();
      if (Math.abs(horizontal_angle) <= 0.03) {
        end(true);
      } else {
        rotationSpeed =
            (max - min) * (Math.pow((horizontal_angle / 27), 2))
                + min; // Max angle, I think it is 27
        rotationSpeed = Math.copySign(rotationSpeed, horizontal_angle);
        // I think 1 is the upper bound for rotational speed, hence, if not, scaling, and ceiling is
        // unnecessary
      }
    } else {
      rotationSpeed = 1.5 * max;
    }
    if (Math.abs((rotationSpeed - pastRotationSpeed) / (pastRotationSpeed)) >= 0.1) {
      rotationSpeed += (0.25) * (pastRotationSpeed - rotationSpeed);
    }
    if (rotationSpeed < -2 * max || rotationSpeed > 2 * max)
      end(true); // This should never happen, but, is a safety measure
    drivetrainSubsystem.drive(Vector2.ZERO, rotationSpeed, false);
    pastRotationSpeed = rotationSpeed;
  }

  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.drive(Vector2.ZERO, 0, false);
  }

  @Override
  public boolean isFinished() {
    return (limelight.getSeesTarget() && (limelight.getHorizontalOffset()) <= 0.3);
  }
}

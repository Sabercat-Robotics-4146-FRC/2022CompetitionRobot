package org.frcteam4146.c2022.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.c2022.subsystems.Limelight;
import org.frcteam4146.common.math.Vector2;

public class AimRobotCommand extends CommandBase {
  public final DrivetrainSubsystem drivetrainSubsystem;
  public final Limelight limelight;

  private double initialRotationPos = 0;

  private double min = 0;
  private double max = 0.25;

  private double rotationSpeed = 0;
  private double pastRotationSpeed = 0;

  public AimRobotCommand(DrivetrainSubsystem drivetrainSubsystem, Limelight limelight) {
    this.drivetrainSubsystem = drivetrainSubsystem;
    this.limelight = limelight;

    addRequirements(drivetrainSubsystem);
  }

  @Override
  public void initialize() {
      // TODO Auto-generated method stub
      initialRotationPos = drivetrainSubsystem.getPose().rotation.toRadians();
  }

  @Override
  public void execute() {
    rotationSpeed = limelight.calculateRotation(min, max, pastRotationSpeed);
    pastRotationSpeed = rotationSpeed;

    if (Math.abs(rotationSpeed) > 2*max)
      end(true); // This should never happen, but, is a safety measure

    drivetrainSubsystem.drive(Vector2.ZERO, rotationSpeed, false);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.returnAngle = initialRotationPos - drivetrainSubsystem.getPose().rotation.toRadians();
    drivetrainSubsystem.drive(Vector2.ZERO, 0, false);
  }

  @Override
  public boolean isFinished() {
    return (limelight.getSeesTarget() && (limelight.getHorizontalOffset()) <= 0.3);
  }
}

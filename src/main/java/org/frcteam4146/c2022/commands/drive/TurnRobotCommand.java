package org.frcteam4146.c2022.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.drivers.Gyroscope;
import org.frcteam4146.common.math.Rotation2;

public class TurnRobotCommand extends CommandBase {
  public final DrivetrainSubsystem drivetrainSubsystem;
  public final Gyroscope gyroscope;
  public Rotation2 degrees;

  public Rotation2 currDegrees;
  public double driveTurn = 0;

  public double initChange;

  public double multiplier = 1;

  public boolean sweep;

  public TurnRobotCommand(
      DrivetrainSubsystem drivetrainSubsystem, Gyroscope gyroscope, double degrees) {
    this.drivetrainSubsystem = drivetrainSubsystem;
    this.initChange = degrees;
    this.gyroscope = gyroscope;

    addRequirements(drivetrainSubsystem);
  }

  public TurnRobotCommand(
      DrivetrainSubsystem drivetrainSubsystem, Gyroscope gyroscope, double degrees, boolean sweep) {
    this(drivetrainSubsystem, gyroscope, degrees);
    this.sweep = true;
  }

  @Override
  public void initialize() {
    degrees = Rotation2.fromDegrees(initChange).rotateBy(gyroscope.getAngle());
    multiplier = 1;
  }

  @Override
  public void execute() {
    this.currDegrees = gyroscope.getAngle();
    driveTurn =
        -Rotation2.fromDegrees(0).rotateBy(degrees).rotateBy(currDegrees.inverse()).toDegrees();
    if (!sweep) {
      if (driveTurn > 180) driveTurn = 360 - driveTurn;
      if (driveTurn < -180) driveTurn = 360 + driveTurn;
    }

    if (Math.abs(driveTurn) < 10) {
      multiplier = 0.7;
      if (Math.abs(driveTurn) < 5) {
        multiplier = 0.5;
      }
      if (Math.abs(driveTurn) < 3) {
        multiplier = 0.2;
      }
    }

    SmartDashboard.putNumber("Driveturn", driveTurn);

    drivetrainSubsystem.drive(
        drivetrainSubsystem.getTranslation(), Math.copySign(multiplier * 0.01, driveTurn), true);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(degrees.toDegrees() - currDegrees.toDegrees()) <= 0.5;
  }

  @Override
  public void end(boolean interrupted) {
    initialize();
    drivetrainSubsystem.drive(drivetrainSubsystem.getTranslation(), 0, false);
  }
}

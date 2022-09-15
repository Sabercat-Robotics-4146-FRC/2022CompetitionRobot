package org.frcteam4146.c2022.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.math.Vector2;

/*
  Very simple drive command. Used in cases where we want to 
  perform a simple translation and rotation
*/

public class BasicDriveCommand extends CommandBase {
  private final DrivetrainSubsystem drivetrain;
  private final Vector2 translation;
  private final double rotation;
  private final boolean fieldOriented;

  public BasicDriveCommand(
      DrivetrainSubsystem drivetrain, Vector2 translation, double rotation, boolean fieldOriented) {
    this.drivetrain = drivetrain;
    this.translation = translation;
    this.rotation = rotation;
    this.fieldOriented = fieldOriented;

    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    drivetrain.drive(translation, rotation, fieldOriented);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(Vector2.ZERO, 0.0, false);
  }
}
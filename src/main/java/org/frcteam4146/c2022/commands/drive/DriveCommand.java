package org.frcteam4146.c2022.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.math.Vector2;
import org.frcteam4146.common.robot.input.Axis;

/*
  Used with joystick components.
*/
public class DriveCommand extends CommandBase {
  private DrivetrainSubsystem drivetrainSubsystem;
  private Axis forward;
  private Axis strafe;
  private Axis rotation;

  public DriveCommand(DrivetrainSubsystem drivetrain, Axis forward, Axis strafe, Axis d) {
    this.forward = forward;
    this.strafe = strafe;
    this.rotation = d;

    drivetrainSubsystem = drivetrain;

    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    // TODO: add sensitivity customization in SmartDashboard
    drivetrainSubsystem.drive(
        new Vector2(-forward.get(true) / 2, strafe.get(true) / 2), rotation.get(true) / 3);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.drive(Vector2.ZERO, 0, false);
  }
}

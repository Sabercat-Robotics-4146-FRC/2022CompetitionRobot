package org.frcteam4146.c2022.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.ArrayList;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.math.Vector2;

public class ReturnRobotCommand extends CommandBase {

  DrivetrainSubsystem drivetrain;

  ArrayList<Double> speeds;
  double rotationSpeed = 0;

  public ReturnRobotCommand(DrivetrainSubsystem drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    speeds = drivetrain.speeds;
  }

  @Override
  public void execute() {
    if (speeds.size() == 0) {
      end(true);
    }
    rotationSpeed = -speeds.remove(0);

    drivetrain.drive(Vector2.ZERO, rotationSpeed, false);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(Vector2.ZERO, rotationSpeed, false);
  }
}

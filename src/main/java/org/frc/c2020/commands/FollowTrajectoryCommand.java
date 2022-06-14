package org.frc.c2020.commands;

import org.frc.c2020.subsystems.DrivetrainSubsystem;
import org.frc.common.control.Trajectory;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FollowTrajectoryCommand extends CommandBase {
  private final DrivetrainSubsystem drivetrain;
  private final Trajectory trajectory;

  public FollowTrajectoryCommand(DrivetrainSubsystem drivetrain, Trajectory trajectory) {
    this.drivetrain = drivetrain;
    this.trajectory = trajectory;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    drivetrain.getFollower().follow(trajectory);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.getFollower().cancel();
  }

  @Override
  public boolean isFinished() {
    return drivetrain.getFollower().getCurrentTrajectory().isEmpty();
  }
}

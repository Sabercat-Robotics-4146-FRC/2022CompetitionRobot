package org.frcteam4146.c2022.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.control.Trajectory;

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

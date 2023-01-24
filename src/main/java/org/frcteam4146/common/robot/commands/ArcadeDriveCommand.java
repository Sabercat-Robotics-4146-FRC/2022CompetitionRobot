package org.frcteam4146.common.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.common.robot.input.Axis;
import org.frcteam4146.common.robot.subsystems.TankDrivetrain;

@Deprecated
public final class ArcadeDriveCommand extends CommandBase {
  private final TankDrivetrain drivetrain;

  private final Axis forwardAxis;
  private final Axis turnAxis;

  public ArcadeDriveCommand(TankDrivetrain drivetrain, Axis forwardAxis, Axis turnAxis) {
    this.drivetrain = drivetrain;
    this.forwardAxis = forwardAxis;
    this.turnAxis = turnAxis;

    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    drivetrain.arcadeDrive(forwardAxis.get(true), turnAxis.get(true));
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}

package org.frc.common.robot.commands;

import org.frc.common.robot.subsystems.HolonomicDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

@Deprecated
public class ZeroFieldOrientedCommand extends Command {
  private final HolonomicDrivetrain drivetrain;

  public ZeroFieldOrientedCommand(HolonomicDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  protected void initialize() {
    drivetrain.getGyroscope().setAdjustmentAngle(drivetrain.getGyroscope().getUnadjustedAngle());
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}

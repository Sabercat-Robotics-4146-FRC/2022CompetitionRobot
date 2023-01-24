package org.frcteam4146.common.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.common.robot.subsystems.ShiftingTankDrivetrain;

@Deprecated
public class SetDrivetrainGearCommand extends CommandBase {
  private final ShiftingTankDrivetrain drivetrain;
  private final boolean highGear;

  public SetDrivetrainGearCommand(ShiftingTankDrivetrain drivetrain, boolean highGear) {
    this.drivetrain = drivetrain;
    this.highGear = highGear;
  }

  @Override
  public void initialize() {
    drivetrain.setHighGear(highGear);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}

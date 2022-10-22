package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Climb;

public class ToggleClimbBrakes extends CommandBase {
  private final Climb climb;
  private final boolean state;

  public ToggleClimbBrakes(Climb climb, boolean state) {
    this.climb = climb;
    this.state = state;
  }

  @Override
  public void initialize() {
    climb.setClimbBrakes(state);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) climb.setClimbBrakes(false);
  }
}

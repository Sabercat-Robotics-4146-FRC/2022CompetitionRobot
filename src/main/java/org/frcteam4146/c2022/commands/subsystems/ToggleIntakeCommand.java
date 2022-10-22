package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Intake;

public class ToggleIntakeCommand extends CommandBase {

  private final Intake intake;
  private final boolean extend;
  private final boolean enable;

  public ToggleIntakeCommand(Intake intake, boolean enable, boolean extend) {
    this.intake = intake;
    this.extend = extend;
    this.enable = enable;
  }

  @Override
  public void initialize() {

    intake.setIntake(enable);
    intake.extendIntake(extend);
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      if (enable) intake.setIntake(false);
      if (extend) intake.extendIntake(false);
    }
  }
}

package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Intake;

public class ToggleIntakeCommand extends CommandBase {

  private final Intake intake;

  private final boolean enable;

  public ToggleIntakeCommand(Intake intake, boolean enable) {
    this.intake = intake;

    this.enable = enable;
  }

  @Override
  public void initialize() {
    intake.extendIntake(true);
    intake.setIntake(enable);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      intake.setIntake(false);
    }
  }
}

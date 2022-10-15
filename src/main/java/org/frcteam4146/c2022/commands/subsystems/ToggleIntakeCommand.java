package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Intake;

public class ToggleIntakeCommand extends CommandBase {

  private final Intake intake;
  private final boolean state;

  public ToggleIntakeCommand(Intake intake, boolean state) {
    this.intake = intake;
    this.state = state;
  }

  @Override
  public void initialize() {
    intake.setIntake(state);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntake(false);
  }
}

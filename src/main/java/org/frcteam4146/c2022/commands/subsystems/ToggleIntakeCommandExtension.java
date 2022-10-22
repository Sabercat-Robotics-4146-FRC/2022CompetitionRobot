package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Intake;

public class ToggleIntakeCommandExtension extends CommandBase {

  private final Intake intake;
  private final boolean extend;

  public ToggleIntakeCommandExtension(Intake intake, boolean extend) {
    this.intake = intake;
    this.extend = extend;
  }

  @Override
  public void initialize() {

    intake.extendIntake(extend);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      intake.extendIntake(false);
    }
  }
}

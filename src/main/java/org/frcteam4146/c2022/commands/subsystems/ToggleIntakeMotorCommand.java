package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Intake;

public class ToggleIntakeMotorCommand extends CommandBase {
  public final Intake intake;
  public final boolean state;

  public ToggleIntakeMotorCommand(Intake intake, boolean state) {
    this.intake = intake;
    this.state = state;
  }

  @Override
  public void initialize() {
    intake.toggleIntake(state);
  }

  @Override
  public boolean isFinished() {
    // TODO Auto-generated method stub
    return true;
  }
  @Override
  public void end(boolean interrupted) {
    intake.toggleIntake(false);
  }
}

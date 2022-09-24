package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Intake;

public class ToggleIntakeExtensionCommand extends CommandBase {
  public final Intake intake;
  public final boolean state;

  public ToggleIntakeExtensionCommand(Intake intake, boolean state) {
    this.intake = intake;
    this.state = state;
  }

  @Override
  public void initialize() {
    intake.toggleExtension(state);
  }

  @Override
  public void end(boolean interrupted) {
    intake.toggleExtension(false);
  }
}

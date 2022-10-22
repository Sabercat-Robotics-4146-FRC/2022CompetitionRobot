package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Indexer;
import org.frcteam4146.c2022.subsystems.Intake;

public class ToggleIntakeCommand extends CommandBase {

  private final Intake intake;
  private final Indexer indexer;

  private final boolean enable;

  public ToggleIntakeCommand(Intake intake, Indexer indexer, boolean enable) {
    this.intake = intake;
    this.indexer = indexer;
    this.enable = enable;
  }

  @Override
  public void initialize() {
    intake.extendIntake(true);
    intake.setIntake(enable);
    indexer.loadBottomBall();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      intake.setIntake(false);
      indexer.indexerOff();
    }
  }
}

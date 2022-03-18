package org.frcteam2910.c2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam2910.c2020.subsystems.IntakeAndIndexer;

public class ShootBallCommand extends CommandBase {
  final IntakeAndIndexer intakeAndIndexer;

  public ShootBallCommand(IntakeAndIndexer intakeAndIndexer) {
    this.intakeAndIndexer = intakeAndIndexer;

    addRequirements(intakeAndIndexer);
  }

  @Override
  public void execute() {
    intakeAndIndexer.toggleFlywheel();
  }

  @Override
  public void end(boolean interrupted) {
    intakeAndIndexer.toggleFlywheel();
  }
}

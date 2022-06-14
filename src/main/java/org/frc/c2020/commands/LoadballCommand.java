package org.frc.c2020.commands;

import org.frc.c2020.subsystems.IntakeAndIndexer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class LoadballCommand extends CommandBase {
  final IntakeAndIndexer intakeAndIndexer;

  public LoadballCommand(IntakeAndIndexer intakeAndIndexer) {
    this.intakeAndIndexer = intakeAndIndexer;

    addRequirements(intakeAndIndexer);
  }

  @Override
  public void execute() {
    intakeAndIndexer.loadTopBall();
  }

  @Override
  public void end(boolean interrupted) {
    intakeAndIndexer.indexerOff();
  }
}

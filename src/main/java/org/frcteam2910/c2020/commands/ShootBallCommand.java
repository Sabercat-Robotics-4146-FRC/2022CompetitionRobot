package org.frcteam2910.c2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam2910.c2020.subsystems.Flywheel;

public class ShootBallCommand extends CommandBase {
  final Flywheel flywheel;


  public ShootBallCommand(Flywheel flywheel) {
    this.flywheel = flywheel;

    addRequirements(flywheel);
  }

  @Override
  public void execute() {
    flywheel.toggleFlywheel(true);
  }

  @Override
  public void end(boolean interrupted) {
    flywheel.toggleFlywheel(false);
  }
}

package org.frcteam2910.c2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.concurrent.TimeUnit;
import org.frcteam2910.c2020.subsystems.Flywheel;
import org.frcteam2910.c2020.subsystems.Limelight;

public class ShootBallCommand extends CommandBase {
  final Flywheel flywheel;
  final Limelight limelight;

  public ShootBallCommand(Flywheel flywheel, Limelight limelight) {
    this.flywheel = flywheel;
    this.limelight = limelight;

    addRequirements(flywheel);
  }

  @Override
  public void execute() {
    limelight.varyServos();
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    flywheel.toggleFlywheel(true);
  }

  @Override
  public void end(boolean interrupted) {
    flywheel.toggleFlywheel(false);
  }
}

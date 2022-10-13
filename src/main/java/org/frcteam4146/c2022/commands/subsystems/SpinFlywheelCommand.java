package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Flywheel;
import org.frcteam4146.c2022.subsystems.Limelight;

public class SpinFlywheelCommand extends CommandBase {
  private final Flywheel flywheel;
  private final Limelight limelight;
  private final boolean state;

  public SpinFlywheelCommand(Flywheel flywheel, Limelight limelight, boolean state) {
    this.flywheel = flywheel;
    this.limelight = limelight;

    this.state = state;
  }

  @Override
  public void initialize() {
    flywheel.toggleFlywheel(state);
  }

  @Override
  public void execute() {
    flywheel.determineSetpoint(limelight.getDistanceFromTarget());
  }

  @Override
  public boolean isFinished() {
    return flywheel.reachedSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) flywheel.toggleFlywheel(false);
  }
}

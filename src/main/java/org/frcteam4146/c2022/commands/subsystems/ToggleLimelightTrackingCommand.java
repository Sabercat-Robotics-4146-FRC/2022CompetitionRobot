package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Limelight;

public class ToggleLimelightTrackingCommand extends CommandBase {
  private final Limelight limelight;
  private final boolean state;

  public ToggleLimelightTrackingCommand(Limelight limelight, boolean state) {
    this.limelight = limelight;
    this.state = state;
  }

  public ToggleLimelightTrackingCommand(Limelight limelight) {
    this.limelight = limelight;
    this.state = !limelight.isTracking();
  }

  @Override
  public void initialize() {
    limelight.toggleTracking(state);
  }

  @Override
  public void end(boolean interrupted) {
    limelight.toggleTracking(false);
  }
}

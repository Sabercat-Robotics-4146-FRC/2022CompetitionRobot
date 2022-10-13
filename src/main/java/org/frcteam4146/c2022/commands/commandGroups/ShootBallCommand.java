package org.frcteam4146.c2022.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.*;
import org.frcteam4146.c2022.commands.subsystems.SpinFlywheelCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleLimelightTrackingCommand;
import org.frcteam4146.c2022.subsystems.Flywheel;
import org.frcteam4146.c2022.subsystems.Indexer;
import org.frcteam4146.c2022.subsystems.Limelight;

public class ShootBallCommand extends SequentialCommandGroup {
  private final Flywheel flywheel;
  private final Limelight limelight;
  private final Indexer indexer;

  public ShootBallCommand(Limelight limelight, Flywheel flywheel, Indexer indexer) {
    this.flywheel = flywheel;
    this.limelight = limelight;
    this.indexer = indexer;
    addCommands(
        new ParallelCommandGroup(
            new ToggleLimelightTrackingCommand(limelight, true),
            new ParallelRaceGroup(
                new SpinFlywheelCommand(flywheel, limelight, true), new WaitCommand(3))),
        new ToggleLimelightTrackingCommand(limelight, false),
        new LoadBallCommand(indexer),
        new ParallelCommandGroup(
            new ToggleLimelightTrackingCommand(limelight, true),
            new SpinFlywheelCommand(flywheel, limelight, false)));
  }

  @Override
  public void end(boolean interrupted) {
    flywheel.toggleFlywheel(false);
    limelight.toggleTracking(false);
    indexer.toggleIndexer(false);
  }
}

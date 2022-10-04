package org.frcteam4146.c2022.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frcteam4146.c2022.commands.subsystems.ToggleFlywheelCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleLimelightTrackingCommand;
import org.frcteam4146.c2022.subsystems.Flywheel;
import org.frcteam4146.c2022.subsystems.Indexer;
import org.frcteam4146.c2022.subsystems.Limelight;

public class ShootBallCommand extends SequentialCommandGroup {
  public ShootBallCommand(Limelight limelight, Flywheel flywheel, Indexer indexer) {
    addCommands(
        new ParallelCommandGroup(
            new ToggleLimelightTrackingCommand(limelight, true), // toggles limelight tracking indefinitely
            new ToggleFlywheelCommand(flywheel, true, limelight.getDistanceFromTarget())), // toggles flywheel on for entire duration of the command
        new WaitCommand(2),
        new ToggleLimelightTrackingCommand(limelight, false), // disables limelight while ball is being loaded only
        new LoadBallCommand(indexer), // note: there is a wait command inside loadballcommand
        new ParallelCommandGroup( // restore limelight and flywheel to original state
            new ToggleLimelightTrackingCommand(limelight, true),
            new ToggleFlywheelCommand(flywheel, false, limelight.getDistanceFromTarget())));

  }
}

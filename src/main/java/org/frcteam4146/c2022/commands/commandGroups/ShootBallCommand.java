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
                new ToggleLimelightTrackingCommand(limelight, true),
                new ToggleFlywheelCommand(flywheel, true)
            ),
            new WaitCommand(10),
            new ToggleLimelightTrackingCommand(limelight, false),
            new LoadBallCommand(indexer),
            new ParallelCommandGroup(
                new ToggleLimelightTrackingCommand(limelight, true),
                new ToggleFlywheelCommand(flywheel, false)
            )
        );
    }
}

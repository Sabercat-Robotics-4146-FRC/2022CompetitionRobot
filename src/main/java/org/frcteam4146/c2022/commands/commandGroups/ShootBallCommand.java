package org.frcteam4146.c2022.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frcteam4146.c2022.commands.subsystems.SpinFlywheelCommand;
import org.frcteam4146.c2022.subsystems.Flywheel;
import org.frcteam4146.c2022.subsystems.Indexer;
import org.frcteam4146.c2022.subsystems.Limelight;

public class ShootBallCommand extends SequentialCommandGroup {
    public ShootBallCommand(Limelight limelight, Flywheel flywheel, Indexer indexer) {
        addCommands(
            new ParallelCommandGroup(
                new InstantCommand(()-> limelight.toggleTracking(true)),
                new SpinFlywheelCommand(flywheel, true, limelight.getDistanceFromTarget())),
            new ParallelRaceGroup(
                new WaitUntilCommand(flywheel::reachedSetpoint),
                new WaitCommand(5)),
            new InstantCommand(()-> limelight.toggleTracking(false)),
            new LoadBallCommand(indexer),
            new ParallelCommandGroup(
                new InstantCommand(()-> limelight.toggleTracking(true)),
                new SpinFlywheelCommand(flywheel, false, limelight.getDistanceFromTarget())));
    }
}

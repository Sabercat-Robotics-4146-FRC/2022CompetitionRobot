package org.frcteam4146.c2022.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import org.frcteam4146.c2022.subsystems.Indexer;

public class LoadBallCommand extends SequentialCommandGroup {
    public LoadBallCommand(Indexer indexer) {
        addCommands(
                new InstantCommand(()->indexer.toggleIndexer(true)),
                new WaitUntilCommand(indexer::getTopSensor),
                new WaitCommand(0.5),
                new InstantCommand(()->indexer.toggleIndexer(false)));
    }
}
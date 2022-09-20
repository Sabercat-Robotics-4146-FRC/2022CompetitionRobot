package org.frcteam4146.c2022.commands.commandGroups;

import org.frcteam4146.c2022.commands.subsystems.ToggleIndexerCommand;
import org.frcteam4146.c2022.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class LoadBallCommand extends SequentialCommandGroup {
    public LoadBallCommand (Indexer indexer) {
        addCommands(
            new ToggleIndexerCommand(indexer, true),
            new WaitCommand(3.0), //TODO: Determine time
            new ToggleIndexerCommand(indexer, false)
        );
    }
}

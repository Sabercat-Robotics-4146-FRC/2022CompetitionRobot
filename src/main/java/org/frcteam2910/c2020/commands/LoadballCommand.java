package org.frcteam2910.c2020.commands;

import org.frcteam2910.c2020.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class LoadballCommand extends SequentialCommandGroup {

    public LoadballCommand (Indexer indexer) {
        addCommands(
            new ToggleIndexerCommand(indexer, true), 
            new WaitCommand(3.0), //TODO: Determine time
            new ToggleIndexerCommand(indexer, false)
        );
    }
}

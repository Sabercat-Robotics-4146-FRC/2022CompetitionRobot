package org.frcteam4146.c2022.commands.subsystems;

import org.frcteam4146.c2022.subsystems.Indexer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleIndexerCommand extends CommandBase {
    private final Indexer indexer;
    private final boolean state;
    public ToggleIndexerCommand(Indexer indexer, boolean state) {
        this.indexer = indexer;
        this.state = state;
    }
    @Override
    public void initialize() {
        indexer.toggleIndexer(state);
    }
}

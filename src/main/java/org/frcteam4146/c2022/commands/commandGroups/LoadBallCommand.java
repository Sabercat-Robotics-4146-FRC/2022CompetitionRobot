package org.frcteam4146.c2022.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleIndexerCommand;
import org.frcteam4146.c2022.subsystems.Indexer;

public class LoadBallCommand extends SequentialCommandGroup {
  public LoadBallCommand(Indexer indexer) {
    addCommands(
        new ToggleIndexerCommand(indexer, true),
        // new WaitUntilCommand(indexer::getTopSensor),
        new WaitCommand(4),
        new ToggleIndexerCommand(indexer, false));
  }
}

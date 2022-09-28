package org.frcteam4146.c2022.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import org.frcteam4146.c2022.commands.subsystems.ToggleIndexerCommand;
import org.frcteam4146.c2022.subsystems.Indexer;

public class LoadBallCommand extends SequentialCommandGroup {
  public LoadBallCommand(Indexer indexer) {
    addCommands(
        new ToggleIndexerCommand(indexer, true),
        new WaitUntilCommand(indexer::getTopSensor),
        new WaitCommand(0.5), // because there is a significant amount of room between the sensor and the flywheel, add a slight time delay
        new ToggleIndexerCommand(indexer, false));
  }
}

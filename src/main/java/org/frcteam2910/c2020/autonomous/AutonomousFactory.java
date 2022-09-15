package org.frcteam2910.c2020.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.frcteam2910.c2020.RobotContainer;
import org.frcteam2910.common.control.Trajectory;
import org.frcteam2910.common.control.TrajectoryConstraint;
import org.frcteam2910.common.io.PathReader;

public class AutonomousFactory {

  /*
   * begins subsystems that will stay activated throughout the autonomous
   */
  public SequentialCommandGroup toggleSubsystems(RobotContainer container) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    // add commands to toggle

    return command;
  }

  /*
   * creates an autonomous path.
   */

  public SequentialCommandGroup createPath(RobotContainer container, String pathDirectory) {
    File root = new File(pathDirectory);
    SequentialCommandGroup command = new SequentialCommandGroup();

    File[] files = root.listFiles();

    for (File f : files) {

        if (f.isFile()) {
        Trajectory trajectory;

        try {
          // create trajectory from JSON file
          trajectory =
              new Trajectory(
                  new PathReader(new FileReader(f)).read(), // read JSON file and convert to Path
                  new TrajectoryConstraint[0], // N/A (for the foreseeable future)
                  10); // TODO find this value

          // TODO: add commands

        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    return command;
  }
}
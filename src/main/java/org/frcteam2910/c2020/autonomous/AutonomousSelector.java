package org.frcteam2910.c2020.autonomous;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.frcteam2910.c2020.RobotContainer;

public class AutonomousSelector {
  private SendableChooser<String> pathChooser = new SendableChooser<>();
  private RobotContainer container;

  public AutonomousSelector(RobotContainer container) {
    this.container = container;

    File directory = Filesystem.getDeployDirectory();

    // List of all autonomous paths folders
    List<File> paths =
        new ArrayList<File>() {
          {
            /*
             * populate List
             */
            File pathsFile = null;

            for (File f : directory.listFiles()) {
              if (f.getPath().contains("paths")) {
                pathsFile = f;
              }
            }

            // get all contents of directory
            try {
              File[] contents = pathsFile.listFiles();

              // go through files
              for (File f : contents) {

                // determine which are directories and
                // only add those to paths.
                // This is just a failsafe.
                if (f.isDirectory()) {
                  add(f);
                }
              }
            } catch (Exception e) {
            }
          }
        };

    // add each path
    paths.forEach(
        path -> {
          // get the path
          String pathname = path.getName();

          // replace '_' with ' ' for readability purposes
          pathChooser.addOption(pathname.replace('_', ' '), path.getPath());
        });

    SmartDashboard.putData("Autonomous Paths", pathChooser);
  }

  public Command getCommand() {
    SequentialCommandGroup command = new SequentialCommandGroup();
    AutonomousFactory auto = new AutonomousFactory();

    /* TODO toggle necessary subsystems */
    command.addCommands(
        auto.createPath(container, pathChooser.getSelected())); // creates path command

    return command;
  }
}
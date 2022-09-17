package org.frcteam4146.c2022.autonomous;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.frcteam4146.c2022.RobotContainer;

public class AutonomousSelector {
    private SendableChooser<String> pathChooser = new SendableChooser<>();
    private RobotContainer container;

    public AutonomousSelector(RobotContainer container) {
        this.container = container;

        File directory = Filesystem.getDeployDirectory();

        // List of all autonomous paths folders
        List<File> paths = new ArrayList<File>() {
            {
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

                        // only add directories to exclude miscellaneous files
                        if (f.isDirectory()) {
                            add(f);
                        }
                    }
                } catch (Exception e) {}
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
        AutonomousFactory auto = new AutonomousFactory(container);
        Command command = auto.createPath(pathChooser.getSelected()); // creates path command

        return command;
    }
}
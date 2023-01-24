package org.frcteam4146.c2022.commands.auto;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.frcteam4146.c2022.RobotContainer;

public class AutonomousSelector {
  private SendableChooser<String> pathChooser = new SendableChooser<>();
  private List<File> paths;
  private RobotContainer container;

  public AutonomousSelector(RobotContainer container) {
    this.container = container;

    File directory =
        Filesystem.getDeployDirectory().listFiles((dir, name) -> name.contains("output"))[0];

    // List of all autonomous paths folders
    paths = new ArrayList<>();
    for (File f : directory.listFiles()) paths.add(f);

    // add each path
    paths.forEach(path -> {
      // get the path
      String pathName = path.getName();

      // replace '_' with ' ' for readability purposes
      pathChooser.addOption(pathName.replace('_', ' '), path.getPath());
    });
    //pathChooser.setDefaultOption("Nothing", "");
  }

  public SendableChooser<String> getChooser() {
    return pathChooser;
  }

  public Command getCommand() {
    AutonomousFactory auto = new AutonomousFactory(container);
    SequentialCommandGroup command = new SequentialCommandGroup();


    if(pathChooser.getSelected() != null) {
      SmartDashboard.putString("TEST2", pathChooser.getSelected()!=null?pathChooser.getSelected():"Nothing");

      command.addCommands(
          auto.createPath(pathChooser.getSelected()) // creates path command
          );
    }
    return command;
  }
}

package org.frcteam2910.c2020.util.paths;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import java.io.FileReader;
import java.io.IOException;
import org.frcteam2910.c2020.RobotContainer;
import org.frcteam2910.c2020.commands.FollowTrajectoryCommand;
import org.frcteam2910.common.control.Path;
import org.frcteam2910.common.control.Trajectory;
import org.frcteam2910.common.control.TrajectoryConstraint;
import org.frcteam2910.common.io.PathReader;

public class Path1 extends CommandGroup {
  public Trajectory trajectory1;
  public Trajectory trajectory2;
  public Trajectory trajectory3;

  public String trajectoryJSON = "pathweaverout/output/Unnamed.wiplib.json";
  public String trajectoryJSON1 = "pathweaverout/output/Unnamed_0.wiplib.json";
  public String trajectoryJSON2 = "pathweaverout/output/Unnamed_1.wiplib.json";

  private PathReader reader;
  private Path path;

  public Path1(RobotContainer container) {
    loadTrajectory();

    new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory1);
    new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory2);
    new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory3);
  }

  public void loadTrajectory() {

    try {
      reader = new PathReader(new FileReader(trajectoryJSON));
      path = reader.read();
      trajectory1 =
          new Trajectory(path, new TrajectoryConstraint[] {new TrajectoryConstraint() {}}, 10);

      reader = new PathReader(new FileReader(trajectoryJSON1));
      path = reader.read();
      trajectory2 =
          new Trajectory(path, new TrajectoryConstraint[] {new TrajectoryConstraint() {}}, 10);

      reader = new PathReader(new FileReader(trajectoryJSON2));
      path = reader.read();
      trajectory2 =
          new Trajectory(path, new TrajectoryConstraint[] {new TrajectoryConstraint() {}}, 10);
      ;

    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
    }
  }
}

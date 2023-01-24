package org.frcteam4146.c2022.commands.auto;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.io.FileWriter;
import java.io.IOException;

import org.frcteam4146.c2022.commands.FollowTrajectoryCommand;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.control.Path;
import org.frcteam4146.common.control.SimplePathBuilder;
import org.frcteam4146.common.control.Trajectory;
import org.frcteam4146.common.io.PathWriter;
import org.frcteam4146.common.math.Rotation2;
import org.frcteam4146.common.math.Vector2;

public class TrajectoryTest extends SequentialCommandGroup {

  public TrajectoryTest(DrivetrainSubsystem drivetrain) {
    Path path = new SimplePathBuilder(new Vector2(0.0, 0.0), new Rotation2(0.0, 0.0, false))
    .lineTo(new Vector2(0.0, -1.0)).build();

    try (PathWriter w = new PathWriter(new FileWriter(
    Filesystem.getDeployDirectory().listFiles((dir, name) -> name.contains("output"))[0].getPath() + "test.json"))) {
      w.write(path);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    addCommands(
        new FollowTrajectoryCommand(
            drivetrain,
            new Trajectory(
                path,
                DrivetrainSubsystem.TRAJECTORY_CONSTRAINTS,
                0.1)));
  }
}

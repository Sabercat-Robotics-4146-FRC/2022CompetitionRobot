package org.frcteam4146.c2022.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frcteam4146.c2022.commands.FollowTrajectoryCommand;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.control.SimplePathBuilder;
import org.frcteam4146.common.control.Trajectory;
import org.frcteam4146.common.math.Rotation2;
import org.frcteam4146.common.math.Vector2;

public class TrajectoryTest extends SequentialCommandGroup {

  public TrajectoryTest(DrivetrainSubsystem drivetrain) {
    addCommands(
        new FollowTrajectoryCommand(
            drivetrain,
            new Trajectory(
                new SimplePathBuilder(new Vector2(0.0, 0.0), new Rotation2(0.0, 0.0, false))
                    .lineTo(new Vector2(0.0, -5.0))
                    .build(),
                DrivetrainSubsystem.TRAJECTORY_CONSTRAINTS,
                0.0)));
  }
}

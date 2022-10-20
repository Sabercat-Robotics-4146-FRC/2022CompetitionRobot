package org.frcteam4146.c2022.commands.drive;

import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.control.SimplePathBuilder;
import org.frcteam4146.common.control.Trajectory;
import org.frcteam4146.common.control.TrajectoryConstraint;
import org.frcteam4146.common.math.Rotation2;
import org.frcteam4146.common.math.Vector2;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class StraightDriveCommand extends SequentialCommandGroup {
    
    public StraightDriveCommand(DrivetrainSubsystem drivetrain, Vector2 start, Vector2 end) {
        SimplePathBuilder path = new SimplePathBuilder(start, new Rotation2(0.0, 0.0, false));
        path.lineTo(end);

        addCommands(new FollowTrajectoryCommand(drivetrain, new Trajectory(path.build(), new TrajectoryConstraint[0] , 0.0)));
    }

    public StraightDriveCommand(DrivetrainSubsystem drivetrain, Vector2 end) {
        SimplePathBuilder path = new SimplePathBuilder(new Vector2(0.0,0.0), new Rotation2(0.0, 0.0, false));
        path.lineTo(end);

        addCommands(new FollowTrajectoryCommand(drivetrain, new Trajectory(path.build(), new TrajectoryConstraint[0] , 0.0)));
    }
}

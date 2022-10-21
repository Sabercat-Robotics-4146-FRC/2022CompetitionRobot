package org.frcteam4146.c2022.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frcteam4146.c2022.RobotContainer;
import org.frcteam4146.c2022.commands.commandGroups.ShootBallCommand;
import org.frcteam4146.c2022.commands.drive.AimRobotCommand;
import org.frcteam4146.c2022.commands.drive.StraightDriveCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleIntakeCommand;
import org.frcteam4146.c2022.subsystems.*;
import org.frcteam4146.common.drivers.Gyroscope;
import org.frcteam4146.common.math.Vector2;

public class OneBallAutoCommand extends SequentialCommandGroup {
  public OneBallAutoCommand(RobotContainer robotContainer) {
    DrivetrainSubsystem drivetrain = robotContainer.getDrivetrain();
    Gyroscope gyro = robotContainer.getGyroscope();
    addCommands(
        new ToggleIntakeCommand(robotContainer.getIntake(), true),
        new ParallelRaceGroup(
            new StraightDriveCommand(
                drivetrain,
                new Vector2(
                    -3 * Math.cos(gyro.getAngle().toRadians()),
                    -3 * Math.sin(gyro.getAngle().toRadians())))),
        new WaitCommand(0.5),
        new AimRobotCommand(drivetrain, robotContainer.getLimelight()),
        new WaitCommand(0.5),
        new ShootBallCommand(
            robotContainer.getLimelight(),
            robotContainer.getFlywheel(),
            robotContainer.getIndexer()));
  }
}

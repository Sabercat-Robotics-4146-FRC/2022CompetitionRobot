package org.frcteam4146.c2022.commands.autos;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frcteam4146.c2022.RobotContainer;
import org.frcteam4146.c2022.commands.commandGroups.ShootBallCommand;
import org.frcteam4146.c2022.commands.commandGroups.ShootNoLimelightCommand;
import org.frcteam4146.c2022.commands.drive.AimRobotCommand;
import org.frcteam4146.c2022.commands.drive.StraightDriveCommand;
import org.frcteam4146.common.drivers.Gyroscope;
import org.frcteam4146.common.math.Vector2;

public class DriveOutAutoLime extends SequentialCommandGroup {
    public DriveOutAutoLime(RobotContainer robotContainer) {
        Gyroscope gyro = robotContainer.getGyroscope();
        addCommands(
                new AimRobotCommand(robotContainer.getDrivetrain(), robotContainer.getLimelight()),
                new WaitCommand(2),
                new ShootBallCommand(robotContainer.getLimelight(), robotContainer.getFlywheel(), robotContainer.getIndexer()),
                new StraightDriveCommand(robotContainer.getDrivetrain(), new Vector2(-3*Math.cos(gyro.getAngle().toRadians()), -3*Math.sin(gyro.getAngle().toRadians())))
        );
    }
}

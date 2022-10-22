package org.frcteam4146.c2022.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frcteam4146.c2022.commands.commandGroups.ShootBallCommand;
import org.frcteam4146.c2022.commands.drive.BasicDriveCommand;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.c2022.subsystems.Flywheel;
import org.frcteam4146.c2022.subsystems.Indexer;
import org.frcteam4146.c2022.subsystems.Limelight;
import org.frcteam4146.common.drivers.Gyroscope;
import org.frcteam4146.common.math.Vector2;

public class DriveOutAuto extends SequentialCommandGroup {
  public DriveOutAuto(
      DrivetrainSubsystem drive,
      Gyroscope gyro,
      Limelight limelight,
      Flywheel flywheel,
      Indexer indexer) {
    addCommands(
        new ShootBallCommand(limelight, flywheel, indexer),
        new ParallelRaceGroup(
            new BasicDriveCommand(
                drive,
                new Vector2(
                    -0.2 * Math.abs(Math.cos(gyro.getAngle().toRadians())),
                    -0.2 * Math.abs(Math.sin(gyro.getAngle().toRadians()))),
                0,
                false),
            new WaitCommand(3)));
  }
}

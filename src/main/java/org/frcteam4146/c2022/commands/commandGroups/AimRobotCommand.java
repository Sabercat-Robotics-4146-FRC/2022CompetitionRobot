package org.frcteam4146.c2022.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import java.util.function.BooleanSupplier;
import org.frcteam4146.c2022.commands.drive.TurnRobotCommand;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.c2022.subsystems.Limelight;
import org.frcteam4146.common.drivers.Gyroscope;

public class AimRobotCommand extends SequentialCommandGroup {
  public AimRobotCommand(
      DrivetrainSubsystem drivetrainSubsystem, Limelight limelight, Gyroscope gyroscope) {

    addCommands(
        new ParallelRaceGroup(
            new TurnRobotCommand(drivetrainSubsystem, gyroscope, 359, true),
            new WaitUntilCommand((BooleanSupplier) limelight::getSeesTarget)),
        new WaitCommand(1),
        new TurnRobotCommand(drivetrainSubsystem, gyroscope, limelight.getHorizontalOffset()));
  }
}

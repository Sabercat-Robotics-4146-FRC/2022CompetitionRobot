package org.frcteam4146.c2022.commands.commandGroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frcteam4146.c2022.RobotContainer;
import org.frcteam4146.c2022.commands.subsystems.SpinFlywheelCommand;
//Limelight not working shoot ball assuming right against target
public class ShootNoLimelightCommand extends SequentialCommandGroup {
  public ShootNoLimelightCommand(RobotContainer robotContainer) {
    addCommands(

        new InstantCommand(() -> robotContainer.getServos().setPosition(0.3)),
        new SpinFlywheelCommand(robotContainer.getFlywheel(),true),
        new WaitCommand(4),
        new LoadBallCommand(robotContainer.getIndexer()),
        new WaitCommand(4),
        new SpinFlywheelCommand(robotContainer.getFlywheel(), false));
  }
}


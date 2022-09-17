package org.frcteam4146.c2022;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.frcteam4146.c2022.commands.drive.DriveCommand;
import org.frcteam4146.c2022.subsystems.*;
import org.frcteam4146.common.robot.input.XboxController;

public class RobotContainer {

  private final XboxController primaryController =
      new XboxController(Constants.PRIMARY_CONTROLLER_PORT);

  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();

  public RobotContainer() {

    // primaryController.getLeftXAxis().setInverted(true);
    // primaryController.getRightXAxis().setInverted(true);

    CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);

    CommandScheduler.getInstance()
        .setDefaultCommand(
            drivetrainSubsystem,
            new DriveCommand(
                drivetrainSubsystem,
                primaryController.getLeftXAxis(),
                primaryController.getLeftYAxis(),
                primaryController.getRightXAxis()));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // TODO: Configure Button Bindings

  }

  // public Command getAutonomousCommand() {
  //   return autonomousChooser.getCommand(this);
  // }

  public DrivetrainSubsystem getDrivetrainSubsystem() {
    return drivetrainSubsystem;
  }
}

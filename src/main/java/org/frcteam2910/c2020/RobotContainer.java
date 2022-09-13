package org.frcteam2910.c2020;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.frcteam2910.c2020.commands.drive.DriveCommand;
import org.frcteam2910.c2020.subsystems.*;
import org.frcteam2910.common.robot.input.XboxController;

public class RobotContainer {

  private final XboxController primaryController =
      new XboxController(Constants.PRIMARY_CONTROLLER_PORT);

  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();

  public RobotContainer() {

    primaryController.getLeftXAxis().setInverted(true);
    primaryController.getRightXAxis().setInverted(true);

    CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);

    CommandScheduler.getInstance().setDefaultCommand(drivetrainSubsystem,
            new DriveCommand(
                drivetrainSubsystem,
                primaryController.getLeftYAxis(),
                primaryController.getLeftXAxis(),
                primaryController.getRightXAxis()));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    //TODO: Configure Button Bindings


  }

  // public Command getAutonomousCommand() {
  //   return autonomousChooser.getCommand(this);
  // }

  public DrivetrainSubsystem getDrivetrainSubsystem() {
    return drivetrainSubsystem;
  }
  
}
